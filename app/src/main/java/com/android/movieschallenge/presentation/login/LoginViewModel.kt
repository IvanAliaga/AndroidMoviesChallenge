package com.android.movieschallenge.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieschallenge.domain.model.Login
import com.android.movieschallenge.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel(){
    val access = MutableLiveData<Boolean>()
    fun login(username: String, password: String){
        viewModelScope.launch() {
            access.postValue(loginUseCase.login(Login(username, password)))
        }
    }
}