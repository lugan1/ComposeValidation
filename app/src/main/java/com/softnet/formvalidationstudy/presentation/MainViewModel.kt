package com.softnet.formvalidationstudy.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softnet.formvalidationstudy.domain.use_case.ValidateEmail
import com.softnet.formvalidationstudy.domain.use_case.ValidatePassword
import com.softnet.formvalidationstudy.domain.use_case.ValidatePasswordCheck
import com.softnet.formvalidationstudy.domain.use_case.ValidateTerms
import com.softnet.formvalidationstudy.domain.use_case.ValidationResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validatePasswordCheck: ValidatePasswordCheck = ValidatePasswordCheck(),
    private val validateTerms: ValidateTerms = ValidateTerms()
): ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.PasswordCheckChanged -> {
                state = state.copy(passwordCheck = event.passwordCheck)
            }
            is RegistrationFormEvent.AcceptedTermsChanged -> {
                state = state.copy(acceptedTerms = event.acceptedTerms)
            }
            RegistrationFormEvent.RegisterClicked -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val passwordCheckResult = validatePasswordCheck.execute(state.password, state.passwordCheck)
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hashError = listOf(
            emailResult,
            passwordResult,
            passwordCheckResult,
            termsResult
        ).any { it.isValid.not() }

        if(hashError) {
            state = state.copy(
                emailError = emailResult.error,
                passwordError = passwordResult.error,
                passwordCheckError = passwordCheckResult.error,
                termsError = termsResult.error
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed interface ValidationEvent {
        object Success : ValidationEvent
    }
}