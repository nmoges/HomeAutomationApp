package com.homeautomationapp.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeautomationapp.model.Device
import com.homeautomationapp.model.User
import com.homeautomationapp.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelData @Inject constructor(private val repository: Repository): ViewModel() {

    val userLiveData: MutableLiveData<User> = MutableLiveData()

    val devicesLiveData: MutableLiveData<List<Device>> = MutableLiveData()

    /**
     * Initializes database at launch.
     */
    fun initializeDB(context: Context) {
        viewModelScope.launch {
            if (repository.getAllDevices().isEmpty()) {
                repository.initializeDbWithJSONData(context)
            }
        }.invokeOnCompletion { getListOfDevices() }
    }

    private fun getListOfDevices() {
        viewModelScope.launch {

            devicesLiveData.postValue(repository.getAllDevices())
        }
    }
}