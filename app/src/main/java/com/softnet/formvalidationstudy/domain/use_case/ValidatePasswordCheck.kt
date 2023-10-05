package com.softnet.formvalidationstudy.domain.use_case

class ValidatePasswordCheck {
    fun execute(password: String, passwordCheck: String): ValidationResult {
        if(passwordCheck.isBlank()){
            return ValidationResult(isValid = false, error = "비밀번호 확인은 비어있으면 안됩니다.")
        }
        if(password != passwordCheck){
            return ValidationResult(isValid = false, error = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
        }
        return ValidationResult(isValid = true)
    }
}