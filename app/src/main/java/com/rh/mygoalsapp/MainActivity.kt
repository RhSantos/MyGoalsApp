package com.rh.mygoalsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.rh.mygoalsapp.data.db.AppDatabase
import com.rh.mygoalsapp.domain.models.User
import com.rh.mygoalsapp.ui.AddressViewModel
import com.rh.mygoalsapp.domain.repository.UserRepositoryImpl
import com.rh.mygoalsapp.ui.UserViewModel
import com.rh.mygoalsapp.domain.repository.AddressRepositoryImpl
import com.rh.mygoalsapp.ui.OutsideNavigation
import com.rh.mygoalsapp.ui.theme.MyGoalsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel: UserViewModel by viewModels {
            val database = AppDatabase.getDatabase(applicationContext)
            UserViewModel.UserViewModelFactory(
                userRepository = UserRepositoryImpl(
                    database.userDao(),
                    database.addressDao()
                )
            )
        }

        val addressViewModel: AddressViewModel by viewModels {
            val database = AppDatabase.getDatabase(applicationContext)
            AddressViewModel.AddressViewModelFactory(
                addressRepository = AddressRepositoryImpl(
                    database.addressDao()
                )
            )
        }

        val loggedUser = mutableStateOf(User.emptyUser(applicationContext))

        setContent {
            MyGoalsAppTheme {
                OutsideNavigation(
                    MaterialTheme.colorScheme,
                    MaterialTheme.typography,
                    userViewModel,
                    addressViewModel,
                    loggedUser
                )
            }
        }
    }
}
