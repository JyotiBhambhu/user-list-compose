package com.jyoti.auth.util

fun String.isValidEmail(): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return matches(emailRegex)
}

fun String.isValidPassword(): Boolean {
    return trim().length > 4
}

fun String.isPasswordMatch(confirmPassword: String): Boolean {
    return this == confirmPassword
}