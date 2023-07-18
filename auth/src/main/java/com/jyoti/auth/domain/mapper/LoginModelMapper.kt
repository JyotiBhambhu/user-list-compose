package com.jyoti.auth.domain.mapper

import com.jyoti.auth.data.model.AuthResponseModel
import com.jyoti.auth.login.ui.model.LoginUIModel
import com.jyoti.core.base.BaseMapper
import javax.inject.Inject

class LoginModelMapper @Inject constructor(): BaseMapper<AuthResponseModel, LoginUIModel>() {

    override fun mapper(inValue: AuthResponseModel?): LoginUIModel? =
        inValue?.let { data ->
            LoginUIModel(
                token = data.data ?: "",
                error = data.error ?: ""
            )
        }
}
