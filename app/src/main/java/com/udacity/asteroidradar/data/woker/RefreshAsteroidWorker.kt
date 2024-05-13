package com.udacity.asteroidradar.data.woker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.data_source.database.AsteroidDatabase
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import com.udacity.asteroidradar.utils.getAfterSevenDaysString
import com.udacity.asteroidradar.utils.getTodayString

class RefreshAsteroidWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val asteroidRepository: AsteroidRepository =
        AsteroidRepository(AsteroidDatabase.getDatabase(context.applicationContext))
    override suspend fun doWork(): Result {
        return try {
            asteroidRepository.refreshAsteroids(getTodayString(), getAfterSevenDaysString())
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORKER_NAME = "Refresh_Asteroid"
    }
}