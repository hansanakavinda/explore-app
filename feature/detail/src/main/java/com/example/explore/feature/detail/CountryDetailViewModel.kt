package com.example.explore.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explore.core.domain.GetCountryUseCase
import com.example.explore.feature.detail.mapper.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCountryUseCase: GetCountryUseCase
) : ViewModel() {

    private val countryCode: String = checkNotNull(savedStateHandle["countryCode"])

    private val _state = MutableStateFlow(CountryDetailUi())
    val state: StateFlow<CountryDetailUi> = _state.asStateFlow()

    init {
        getCountryUseCase(countryCode)
            .onEach { country ->
                _state.value = country.asUiModel()
            }
            .catch {
                // Handle error
            }
            .launchIn(viewModelScope)
    }
}
