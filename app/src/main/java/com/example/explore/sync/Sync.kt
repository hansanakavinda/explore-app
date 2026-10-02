package com.example.explore.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object Sync {
    private const val SYNC_WORK_NAME = "SyncCountriesWork"
    private const val INITIAL_SYNC_WORK_NAME = "InitialSyncCountriesWork"

    fun initialize(context: Context) {
        val workManager = WorkManager.getInstance(context)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // 1. Setup the immediate, one-time initial sync
        val initialSyncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            INITIAL_SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            initialSyncRequest
        )

        // 2. Setup the periodic background sync (runs daily)
        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
    }
}
