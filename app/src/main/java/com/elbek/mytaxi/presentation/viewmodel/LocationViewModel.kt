package com.elbek.mytaxi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elbek.mytaxi.data.model.LocationModel
import com.elbek.mytaxi.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {
    // UPDATE
    fun onUpdateLocation(model: LocationModel) {
        viewModelScope.launch {
            repository.updateLocation(
                model
            )
        }
    }
}