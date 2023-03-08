package com.cyberwalker.fashionstore.login

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: String = " ",
    val isError: String? = ""
)
