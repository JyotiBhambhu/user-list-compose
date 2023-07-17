package com.jyoti.core.network

import com.google.gson.Gson
import com.google.gson.JsonParser
import retrofit2.Response
import java.io.IOException

object ErrorUtils {
    fun parseError(response: Response<*>): APIError {
        return try {
            val mJson = JsonParser.parseString(response.errorBody()?.string())
            Gson().fromJson(mJson, APIError::class.java)
        } catch (e: IOException) {
            APIError()
        }
    }
}
