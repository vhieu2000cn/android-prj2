package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.data_source.database.AsteroidDatabase
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.data.model.PictureOfDay
import com.udacity.asteroidradar.data.repository.AsteroidRepository
import com.udacity.asteroidradar.utils.getAfterSevenDaysString
import com.udacity.asteroidradar.utils.getTodayString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.IllformedLocaleException

class MainViewModel(application: Application) : ViewModel() {

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment: LiveData<Asteroid?>
        get() = _navigateToDetailFragment

    private val asteroidRepository = AsteroidRepository(AsteroidDatabase.getDatabase(application))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            asteroidRepository.refreshAsteroids(getTodayString(), getAfterSevenDaysString())
            _pictureOfDay.postValue(asteroidRepository.getPictureOfDay())
            _asteroids.postValue(
                asteroidRepository.getAsteroidsByCloseApproachDate(
                    getTodayString(),
                    getAfterSevenDaysString()
                )
            )
        }
    }

    fun onClickAsteroid(asteroid: Asteroid) {
        _navigateToDetailFragment.postValue(asteroid)
    }

    fun onNavigatedToDetailFragment() {
        _navigateToDetailFragment.value = null
    }

    class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java))
                return MainViewModel(application) as T
            throw IllformedLocaleException("unable constructor viewmodel")
        }
    }

}