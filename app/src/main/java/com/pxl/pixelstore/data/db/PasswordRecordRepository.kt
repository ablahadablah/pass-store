package com.pxl.pixelstore.data.db

import app.cash.sqldelight.coroutines.asFlow
import com.pxl.pixelstore.domain.entity.PasswordRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class PasswordRecordRepository @Inject constructor(
    private val db: PasswordDatabase
) {
    private val dbQuery = db.database.passwordDatabaseQueries

    fun passwords(): Flow<List<PasswordRecord>> {
        return dbQuery.selectAllPasswordsInfo(::mapPasswordEntity).asFlow()
            .map { q ->
                q.executeAsList()
            }
    }

    fun savePassword(e: PasswordRecord) {
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

    fun deletePassword(e: PasswordRecord, deletedTime: Long) {
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
    }

    fun getUnsyncedPasswords(dt: Long): List<PasswordRecord> =
        dbQuery.selectUnsyncedPasswords(dt, ::mapPasswordEntity).executeAsList()

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