package com.android.movieschallenge.domain.usecase

import com.android.movieschallenge.domain.model.Login
import javax.inject.Inject

class LoginUseCase @Inject constructor(){
    fun login(login: Login): Boolean{
        return login.user == "Admin" && login.password == "Password*123"
    }
}