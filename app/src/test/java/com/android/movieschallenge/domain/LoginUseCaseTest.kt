package com.android.movieschallenge.domain

import com.android.movieschallenge.domain.model.Login
import com.android.movieschallenge.domain.usecase.LoginUseCase
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        loginUseCase = LoginUseCase()
    }

    @Test
    fun `when the login not give access`() {
        //Given
        val login = Login("otro", "otro")
        val response = loginUseCase.login(login)
        //Then
        assert(!response)
    }

    @Test
    fun `when the login give access`() {
        //Given
        val login = Login("Admin", "Password*123")
        val access = loginUseCase.login(login)
        //Then
        assert(access)
    }
}