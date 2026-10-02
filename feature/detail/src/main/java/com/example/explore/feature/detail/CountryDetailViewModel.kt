package com.example.explore.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val countryCode: String = checkNotNull(savedStateHandle["countryCode"])

    private val _state = MutableStateFlow(
        CountryDetailUi(code = countryCode) // Initialize with basic code, more details could be fetched
    )
    val state: StateFlow<CountryDetailUi> = _state.asStateFlow()
}
