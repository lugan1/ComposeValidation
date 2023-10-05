package com.softnet.formvalidationstudy.domain.use_case

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)