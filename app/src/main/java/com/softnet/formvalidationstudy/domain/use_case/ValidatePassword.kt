package com.softnet.formvalidationstudy.domain.use_case

class ValidatePassword {
    private val emailRegex = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}".toRegex()

    fun execute(password: String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult(isValid = false, error = "비밀번호는 비어있으면 안됩니다.")
        }
        if(password.matches(emailRegex).not()){
            return ValidationResult(isValid = false, error = "길이 8~20, 영문, 숫자, 특수문자를 포함해야 합니다.")
        }
        return ValidationResult(isValid = true)
    }
}