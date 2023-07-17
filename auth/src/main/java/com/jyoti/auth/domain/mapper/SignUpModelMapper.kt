package com.jyoti.auth.domain.mapper

import com.jyoti.auth.data.model.AuthResponseModel
import com.jyoti.auth.signup.ui.model.SignUpUIModel
import com.jyoti.core.base.BaseMapper
import javax.inject.Inject

class SignUpModelMapper @Inject constructor(): BaseMapper<AuthResponseModel, SignUpUIModel>() {

    override fun mapper(inValue: AuthResponseModel?): SignUpUIModel? = inValue?.let { data ->
        SignUpUIModel(
            id = data.id ?: 0, token = data.data ?: "", error = data.error ?: ""
        )
    }
}
