package com.homeautomationapp.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeautomationapp.model.Device
import com.homeautomationapp.model.User
import com.homeautomationapp.repositories.Repository
import com.homeautomationapp.utils.NameDeviceComparator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * View model containing user and list of devices LiveData.
 */
@HiltViewModel
class ViewModelData @Inject constructor(private val repository: Repository): ViewModel() {

    /**
      * Contains user information.
      */
    val userLiveData: MutableLiveData<User> = MutableLiveData()

    /**
     * Contains list of devices.
     */
    val devicesLiveData: MutableLiveData<List<Device>> = MutableLiveData()

    /**
     * Contains filter results of the list of devices.
     */
    val devicesFilteredLiveData: MutableLiveData<List<Device>> = MutableLiveData()


    lateinit var selectedDevice: Device

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
            val list = repository.getAllDevices()
            Collections.sort(list, NameDeviceComparator)
            devicesLiveData.postValue(list)
        }
    }

    fun getUser() {
        viewModelScope.launch {
            userLiveData.postValue(repository.getUser())
        }
    }

    fun updateUserData(user: User) {
        viewModelScope.launch {
            repository.updateUserData(user)
        }
    }

    fun updateDevice(device: Device) {
        viewModelScope.launch {
            repository.updateDeviceData(device)
        }
    }

    fun deleteDevice(device: Device) {
        viewModelScope.launch {
            repository.deleteDeviceData(device)
            devicesLiveData.postValue(repository.getAllDevices())
        }
    }

    /**
     * Sends filter information to the repository
     */
    fun filterDevices(filters: BooleanArray, isListFiltered: Boolean) {
        viewModelScope.launch {
            // If at least one is selected, a query is sent to the database to retrieve results
            // else the non-filtered list is posted on LiveData
            if (isListFiltered) devicesFilteredLiveData.postValue(repository.getFilteredDevices(filters))
            else getListOfDevices()
        }
    }
}