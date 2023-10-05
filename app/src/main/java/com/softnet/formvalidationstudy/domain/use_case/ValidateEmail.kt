package com.softnet.formvalidationstudy.domain.use_case

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(isValid = false, error = "이메일은 비어있으면 안됩니다.")
        }
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches().not()){
            return ValidationResult(isValid = false, error = "이메일 형식이 아닙니다.")
        }
        return ValidationResult(isValid = true)
    }
}