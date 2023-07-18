package com.jyoti.auth.domain.use_case

import com.jyoti.auth.domain.mapper.SignUpModelMapper
import com.jyoti.auth.domain.repo.AuthRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpModelMapper: SignUpModelMapper,
    private val signUpRepo: AuthRepo,
) {

    operator fun invoke(
        email: String,
        password: String,
    ) = signUpRepo.doSignUp(
        email,
        password
    ).map {
        signUpModelMapper.mapSealedResult(it)
    }
}
