package com.pxl.pixelstore.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pxl.pixelstore.R
import com.pxl.pixelstore.R.string.password_label
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.ui.ObserveEvent
import com.pxl.pixelstore.viewmodel.password.EditPasswordUiEvent
import com.pxl.pixelstore.viewmodel.password.EditPasswordUiState
import com.pxl.pixelstore.viewmodel.password.EditPasswordViewModel
import java.util.UUID

@Composable
fun EditPasswordScreen(
    recordId: String? = null,
    onSaved: () -> Unit
) {
    val viewModel = hiltViewModel<EditPasswordViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(recordId) {
        viewModel.loadRecord(recordId)
    }

    ObserveEvent(viewModel.events) { e ->
        when (e) {
            EditPasswordUiEvent.PasswordSaved -> onSaved()
            is EditPasswordUiEvent.Error -> {}
        }
    }

    when (state) {
        is EditPasswordUiState.Loading -> {}
        is EditPasswordUiState.SaveInProgress -> {}
        is EditPasswordUiState.Data -> {
            EditPasswordForm(
                (state as EditPasswordUiState.Data).record,
                onSave = { url, login, password ->
                    viewModel.saveEntity(url, login, password)
                },
            )
        }
    }
}

@Composable
fun EditPasswordForm(
    entityToEdit: PasswordRecord?,
    onSave: (String, String, String) -> Unit,
) {
    var urlText by rememberSaveable { mutableStateOf(entityToEdit?.url ?: "") }
    var loginText by rememberSaveable { mutableStateOf(entityToEdit?.url ?: "") }
    var passwordText by rememberSaveable { mutableStateOf(entityToEdit?.password ?: "") }

    Column(
        modifier = Modifier
            .padding( 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = urlText,
                onValueChange = { urlText = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.title_label)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { urlText = "" } ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear the title"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(Modifier.height(16.dp))

        Card() {
            TextField(
                value = loginText,
                onValueChange = { loginText = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.username_label)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "Email or username")
                },
                trailingIcon = {
                    IconButton(onClick = { loginText = "" } ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear the title"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black)
            )

            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            TextField(
                value = passwordText,
                onValueChange = { it: String -> passwordText = it },
                modifier = Modifier
                    .padding(paddingValues = PaddingValues(vertical = 8.dp))
                    .width(500.dp),
                label = { Text(text = stringResource(password_label)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Key, contentDescription = "Password")
                },
                trailingIcon = {
                    val icon = if (passwordVisible) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible} ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "hide password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onSave(urlText, loginText, passwordText)
                },
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Save",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun EditPasswordFormPreview() {
    val record = PasswordRecord(
        id = UUID.randomUUID(),
        url = "google.com",
        username = "someStuff@gmail.com",
        password = "qwer",
    )

    EditPasswordForm(record, { _, _, _ ->})
}