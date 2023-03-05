package com.example.pipapplication.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pipapplication.data.repositories.MoviesRepository
import javax.inject.Inject

class MoviesViewModelFactory @Inject constructor(private val repository: MoviesRepository   ):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesMainViewModel(repository) as T
    }
}