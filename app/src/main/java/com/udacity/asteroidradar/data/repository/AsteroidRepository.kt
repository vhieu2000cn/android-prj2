package com.udacity.asteroidradar.data.repository

import com.udacity.asteroidradar.data.data_source.api.AsteroidApi
import com.udacity.asteroidradar.data.data_source.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.data_source.database.AsteroidDatabase
import com.udacity.asteroidradar.data.data_source.database.DatabaseAsteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import com.udacity.asteroidradar.utils.getTodayString
import okhttp3.ResponseBody
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    suspend fun getAllAsteroids() = database.asteroidDao.getAllAsteroids()
    suspend fun refreshAsteroids(
        startDate: String,
        endDate: String
    ) {
        val asteroidResponseBody: ResponseBody = AsteroidApi.retrofitService.getAsteroids(
            startDate, endDate
        )
        val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody.string()))

        database.asteroidDao.insertAsteroids(*asteroidList.map {
            DatabaseAsteroid(
                id = it.id, codename = it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
            )
        }.toTypedArray())
    }

    suspend fun deletePreviousDayAsteroids() {
        database.asteroidDao.deletePreviousDayAsteroids(getTodayString())
    }

    suspend fun getAsteroidsByCloseApproachDate(startDate: String, endDate: String) =
        database.asteroidDao.getAsteroidsByCloseApproachDate(startDate, endDate)

    suspend fun getPictureOfDay(): PictureOfDay = AsteroidApi.retrofitService.getPictureOfDay()

}