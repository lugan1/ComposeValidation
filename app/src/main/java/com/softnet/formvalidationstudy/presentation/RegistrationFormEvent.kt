package com.softnet.formvalidationstudy.presentation

sealed interface RegistrationFormEvent {
    data class EmailChanged(val email: String) : RegistrationFormEvent
    data class PasswordChanged(val password: String) : RegistrationFormEvent
    data class PasswordCheckChanged(val passwordCheck: String) : RegistrationFormEvent
    data class AcceptedTermsChanged(val acceptedTerms: Boolean) : RegistrationFormEvent
    object RegisterClicked : RegistrationFormEvent
}