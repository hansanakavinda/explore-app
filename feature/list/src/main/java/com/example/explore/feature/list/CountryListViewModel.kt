package com.example.explore.feature.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<TempListState>(TempListState.Loading)
    val state: StateFlow<TempListState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        // Simulate network loading
        _state.value = TempListState.Success(fakeCountries)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        val filtered = if (newQuery.isBlank()) {
            fakeCountries
        } else {
            fakeCountries.filter {
                it.name.contains(newQuery, ignoreCase = true)
            }
        }
        
        _state.update {
            if (filtered.isEmpty()) {
                TempListState.Empty
            } else {
                TempListState.Success(filtered)
            }
        }
    }
}
