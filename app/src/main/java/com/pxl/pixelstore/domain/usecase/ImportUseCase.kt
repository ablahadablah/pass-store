package com.pxl.pixelstore.domain.usecase

import com.pxl.pixelstore.data.db.PasswordRecordRepository
import com.pxl.pixelstore.domain.entity.PasswordRecord
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.UUID
import javax.inject.Inject

class ImportUseCase @Inject constructor(
    private val passwordRecordRepository: PasswordRecordRepository
) {
    suspend fun importFromFile(path: String) {
        try {
            val passwords = readData(path).map { lineData ->
                var timeCreated: Long
                var timeLastUsed: Long
                var timePasswordChanged: Long

                try {
                    timeCreated = lineData[6].toLong()
                    timeLastUsed = lineData[7].toLong()
                    timePasswordChanged = lineData[8].toLong()
                } catch (e: Throwable) {
                    timeCreated = 0
                    timeLastUsed = 0
                    timePasswordChanged = 0
                }

                PasswordRecord(
                    id = UUID.randomUUID(),
                    url = lineData[0],
                    username = lineData[1],
                    password = lineData[2],
                    timeCreated = timeCreated,
                    timeLastUsed = timeLastUsed,
                    timePasswordChanged = timePasswordChanged
                )
            }

            for (r in passwords) {
                passwordRecordRepository.savePassword(r)
            }
        } catch (e: Throwable) {
            println("couldn't import from file $path, ${e.message}")
        }
    }

    private fun readData(path: String): List<List<String>> {
        val file = File(path)
        val lines = mutableListOf<List<String>>()

        try {
            BufferedReader(FileReader(file)).use { reader ->
                var line: String?

                // skip first line
                reader.readLine()

                while (reader.readLine().also { line = it } != null) {

                    val data = line!!.replace("\"", "").split(",")
                    lines.add(data)
                }
            }
        } catch (e: Throwable) {
            println("error reading data file $path, ${e.message}")
        }

        return lines
    }
}