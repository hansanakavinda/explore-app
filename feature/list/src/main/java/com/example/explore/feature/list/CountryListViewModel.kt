package com.example.explore.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.explore.core.domain.GetCountriesUseCase
import com.example.explore.feature.list.mapper.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TempListState>(TempListState.Loading)
    val state: StateFlow<TempListState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        loadCountries()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        loadCountries(newQuery)
    }

    private fun loadCountries(query: String = "") {
        _state.update { TempListState.Loading }
        getCountriesUseCase(query)
            .onEach { countries ->
                if (countries.isEmpty()) {
                    _state.update { TempListState.Empty }
                } else {
                    _state.update { TempListState.Success(countries.map { it.asUiModel() }) }
                }
            }
            .catch { error ->
                _state.update { TempListState.Error(error.message ?: "Unknown error") }
            }
            .launchIn(viewModelScope)
    }
}
