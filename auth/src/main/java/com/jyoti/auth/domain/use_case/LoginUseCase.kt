package com.jyoti.auth.domain.use_case

import com.jyoti.auth.domain.mapper.LoginModelMapper
import com.jyoti.auth.domain.repo.AuthRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginModelMapper: LoginModelMapper,
    private val loginRepository: AuthRepo,
) {

    operator fun invoke(
        email: String,
        password: String,
    ) = loginRepository.doLogin(
        email,
        password
    ).map {
        loginModelMapper.mapSealedResult(it)
    }
}
