package com.example.explore.sync

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
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
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val countryRepository: CountryRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val channelId = "sync_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Sync Operations",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(R.drawable.stat_notify_sync)
            .setContentTitle("Syncing Data")
            .setContentText("Fetching country data")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        return ForegroundInfo(1, notification)
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.d("SyncWorker", "Starting network sync")
        val result = countryRepository.syncWithNetwork()
        if (result.isSuccess) {
            Log.d("SyncWorker", "Network sync successful")
            Result.success()
        } else {
            Log.e("SyncWorker", "Network sync failed", result.exceptionOrNull())
            Result.retry()
        }
    }
}
