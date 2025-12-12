package com.asude.animekeep.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.asude.animekeep.AnimeApplication
import com.asude.animekeep.ui.detail.DetailViewModel
import com.asude.animekeep.ui.home.HomeViewModel
import com.asude.animekeep.ui.login.LoginViewModel
import com.asude.animekeep.ui.login.SignUpViewModel
import com.asude.animekeep.ui.mylist.MyListViewModel
import com.asude.animekeep.ui.profile.ProfileViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            HomeViewModel(animeApplication().container.animeRepository)
        }

        initializer {
            DetailViewModel(animeApplication().container.animeRepository)
        }

        initializer {
            MyListViewModel(animeApplication().container.animeRepository)
        }

        initializer {
            ProfileViewModel(animeApplication().container.animeRepository)
        }

        initializer {
            LoginViewModel()
        }

        initializer {
            SignUpViewModel()
        }
    }
}

fun CreationExtras.animeApplication(): AnimeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AnimeApplication)