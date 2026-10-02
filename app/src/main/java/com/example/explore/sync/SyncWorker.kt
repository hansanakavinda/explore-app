package com.example.explore.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.explore.core.data.repository.CountryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val countryRepository: CountryRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        // Return a dummy ForegroundInfo or construct a proper notification
        // if your app targets older APIs. For now, expedited jobs require this override.
        throw IllegalStateException("Foreground info required for expedited work")
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val result = countryRepository.syncWithNetwork()
        if (result.isSuccess) {
            Result.success()
        } else {
            Result.retry()
        }
    }
}
