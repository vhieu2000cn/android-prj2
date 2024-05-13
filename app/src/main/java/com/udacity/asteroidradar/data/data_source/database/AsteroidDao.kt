package com.udacity.asteroidradar.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.model.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM databaseAsteroid ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAsteroids(vararg asteroids: DatabaseAsteroid)

    @Query("DELETE FROM databaseAsteroid WHERE closeApproachDate < :today")
    fun deletePreviousDayAsteroids(today: String): Int

    @Query("SELECT * FROM databaseAsteroid WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
     fun getAsteroidsByCloseApproachDate(startDate: String, endDate: String): List<Asteroid>
}