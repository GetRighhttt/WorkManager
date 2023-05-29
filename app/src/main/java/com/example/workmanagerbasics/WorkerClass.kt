package com.example.workmanagerbasics

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

/*
Steps 1: Create worker class.
 */
class WorkerClass(
    context: Context, workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    companion object {
        const val WORKER = "WORKER"
    }

    init {
        Log.d(WORKER, "Worker class created...")
    }

    override fun doWork(): Result = try {
        for (i in 1..100) {
            Log.d(WORKER, "doWork: $i")
        }
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }
}
