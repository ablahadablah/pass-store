package com.pxl.pixelstore.domain.usecase

import com.pxl.pixelstore.data.db.PasswordRecordRepository
import com.pxl.pixelstore.domain.entity.PasswordRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(
    private val passwordRecordRepository: PasswordRecordRepository,
) {
    suspend fun deletePassword(entity: PasswordRecord) = withContext(Dispatchers.IO) {
        val deletedTime = Calendar.getInstance().timeInMillis
        passwordRecordRepository.deletePassword(entity, deletedTime)
    }

    suspend fun purgeDeletedRecords() {
        // remove everything that was deleted more than 3 months ago
        val dt = Calendar.getInstance().timeInMillis - DELETED_STORAGE_INTERVAL;

        passwordRecordRepository.purgeDeletedRecords(dt)
    }

    companion object {
        private const val DELETED_STORAGE_INTERVAL: Long = 1000 * 60 * 60 * 24 * 90L;
    }
}