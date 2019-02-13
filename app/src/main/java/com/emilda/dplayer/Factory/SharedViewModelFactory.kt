package com.emilda.dplayer.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emilda.dplayer.ViewModels.sharedViewModel


class SharedViewModelFactory(private val userId: String) : ViewModelProvider.NewInstanceFactory() {
     @Suppress("Unchecked cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return sharedViewModel(userId) as T
    }

}