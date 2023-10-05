package com.softnet.formvalidationstudy.domain.use_case

class ValidateTerms {
    fun execute(terms: Boolean): ValidationResult {
        if(terms.not()){
            return ValidationResult(isValid = false, error = "약관에 동의해주세요.")
        }
        return ValidationResult(isValid = true)
    }
}