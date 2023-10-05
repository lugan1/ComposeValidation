package com.softnet.formvalidationstudy.presentation

import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softnet.formvalidationstudy.ui.theme.FormValidationStudyTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormValidationStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MainViewModel = viewModel()
                    val state = viewModel.state
                    val context = LocalContext.current
                    LaunchedEffect(key1 = context) {
                        viewModel.validationEvent.collect { event ->
                            when(event) {
                                MainViewModel.ValidationEvent.Success -> {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.email,
                            placeholder = { Text("Email") },
                            onValueChange = { viewModel.onEvent(RegistrationFormEvent.EmailChanged(it)) },
                            isError = state.emailError != null,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            )
                        )

                        if(state.emailError != null) {
                            Text(
                                modifier = Modifier.align(alignment = Alignment.End),
                                text = state.emailError,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.password,
                            placeholder = { Text("Password") },
                            onValueChange = { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
                            isError = state.passwordError != null,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        if(state.passwordError != null) {
                            Text(
                                modifier = Modifier.align(alignment = Alignment.End),
                                text = state.passwordError,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.passwordCheck,
                            placeholder = { Text("Password Check") },
                            onValueChange = { viewModel.onEvent(RegistrationFormEvent.PasswordCheckChanged(it)) },
                            isError = state.passwordError != null,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        if(state.passwordCheckError != null) {
                            Text(
                                modifier = Modifier.align(alignment = Alignment.End),
                                text = state.passwordCheckError,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Checkbox(
                                checked = state.acceptedTerms,
                                onCheckedChange = { viewModel.onEvent(RegistrationFormEvent.AcceptedTermsChanged(it)) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Accept Terms")
                        }
                        if(state.termsError != null) {
                            Text(
                                modifier = Modifier.align(alignment = Alignment.End),
                                text = state.termsError,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        Button(
                            modifier = Modifier.align(alignment = Alignment.End),
                            onClick = { viewModel.onEvent(RegistrationFormEvent.RegisterClicked) }
                        ) {
                            Text(text = "Register")
                        }
                    }
                }
            }
        }
    }
}
