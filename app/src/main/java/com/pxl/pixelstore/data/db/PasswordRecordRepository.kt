package com.pxl.pixelstore.data.db

import app.cash.sqldelight.coroutines.asFlow
import com.pxl.pixelstore.domain.encryption.PasswordCipher
import com.pxl.pixelstore.domain.entity.PasswordRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class PasswordRecordRepository @Inject constructor(
    private val db: PasswordDatabase,
    private val passwordCipher: PasswordCipher
) {
    private val dbQuery = db.database.passwordDatabaseQueries

    fun passwords(): Flow<List<PasswordRecord>> {
        return dbQuery.selectAllPasswordsInfo(::mapPasswordEntity).asFlow()
            .map { q ->
                q.executeAsList()
            }
    }

    fun savePassword(p: PasswordRecord) {
        val dt = Calendar.getInstance().timeInMillis
        val passwordRecord = p.copy(
            timeCreated = if (p.timeCreated == 0L) dt else p.timeCreated,
            timePasswordChanged = if (p.timePasswordChanged == 0L) dt else p.timePasswordChanged,
            timeLastUsed = if (p.timeLastUsed == 0L) dt else p.timeLastUsed
        )
        val encrypted = passwordCipher.encrypt(passwordRecord.password)
        val e = passwordRecord.copy(password = encrypted)

        dbQuery.transaction {
            dbQuery.updatePassword(
                id = e.id.toString(),
                url = e.url,
                username = e.username,
                password = e.password,
                time_created = e.timeCreated,
                time_last_used = e.timeLastUsed,
                time_password_changed = e.timePasswordChanged,
                deleted_time = e.deletedTime,
            )
        }
    }

    fun movePasswordToTrash(e: PasswordRecord) {
        val deletedTime = Calendar.getInstance().timeInMillis

        dbQuery.transaction {
            if (e.id != null) {
                dbQuery.removePassword(dt = deletedTime, id = e.id.toString())
            }
        }
    }

    fun findById(id: String): PasswordRecord? {
        return try {
            dbQuery.findById(id, ::mapPasswordEntity).executeAsOneOrNull()
        } catch (e: IllegalStateException) {
            null
        }
    }

    fun findByString(string: String): List<PasswordRecord> {
        return dbQuery.selectAllByString(string, ::mapPasswordEntity).executeAsList()
//            .map { r -> r.copy(password = passwordCipher.decrypt(r.password)) }
    }

    fun purgeDeletedRecords(dt: Long) {
        dbQuery.transaction {
            dbQuery.purgePasswords(dt = dt)
        }
    }
}

fun mapPasswordEntity(
    id: String,
    url: String,
    username: String,
    password: String,
    timeCreated: Long,
    timePasswordChanged: Long,
    timeLastUsed: Long,
    deletedTime: Long,
    timeLastSynced: Long
): PasswordRecord = PasswordRecord(
    id = UUID.fromString(id),
    url = url,
    username = username,
    password = password,
    timeCreated = timeCreated,
    timePasswordChanged = timePasswordChanged,
    timeLastUsed = timeLastUsed,
    deletedTime = deletedTime,
    timeLastSynced = timeLastSynced
)