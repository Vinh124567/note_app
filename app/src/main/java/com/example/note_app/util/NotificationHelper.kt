package com.example.note_app.util

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object NotificationHelper {
    fun scheduleNotification(
        context: Context,
        noteId: Int,
        title: String,
        content: String,
        reminderTime: Long
    ) {
        val currentTime = System.currentTimeMillis()
        val delay = reminderTime - currentTime

        if (delay <= 0) {
            return // 如果提醒时间已过，不安排通知
        }

        val inputData = Data.Builder()
            .putInt(NotificationWorker.NOTE_ID_KEY, noteId)
            .putString(NotificationWorker.TITLE_KEY, title)
            .putString(NotificationWorker.CONTENT_KEY, content)
            .build()

        // Tạo constraints để đảm bảo work chạy ngay cả khi app đóng
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // Không cần mạng
            .setRequiresBatteryNotLow(false) // Không cần pin cao
            .setRequiresCharging(false) // Không cần sạc
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(inputData)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag("note_reminder_$noteId")
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    fun cancelNotification(context: Context, noteId: Int) {
        WorkManager.getInstance(context).cancelAllWorkByTag("note_reminder_$noteId")
    }
}

