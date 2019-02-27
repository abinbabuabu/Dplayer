package com.emilda.dplayer.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emilda.dplayer.ViewModels.AllSongsViewModel
import com.emilda.dplayer.ViewModels.FavoritesViewModel
import com.emilda.dplayer.ViewModels.sharedViewModel

@Suppress("Unchecked cast")
class SharedViewModelFactory(private val userId: String,private val newUser:Boolean) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return sharedViewModel(userId,newUser) as T
    }

}

class AllsongsFactory(private val userId: String):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AllSongsViewModel(userId) as T
    }
}

class FavFactory(private val userId: String):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoritesViewModel(userId) as T
    }
}

