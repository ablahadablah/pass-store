package com.pxl.pixelstore.ui.password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pxl.pixelstore.R.string.password_label
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.navigation.Destination
import com.pxl.pixelstore.viewmodel.EditPasswordState
import com.pxl.pixelstore.viewmodel.EditPasswordViewModel

@Composable
fun EditPasswordScreen(dest: Destination.EditPassword) {
    val viewModel = hiltViewModel<EditPasswordViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
//        viewModel.init(dest.record)
    }

    EditPasswordForm(
        null,
//            dest.record,
        state,
        onSave = { url, login, password ->
            viewModel.saveEntity(url, login, password)
        },
        onCancel = {
//                router.back()
        }
    )
}

@Composable
fun EditPasswordForm(
    entityToEdit: PasswordRecord?,
    state: EditPasswordState,
    onSave: (String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var urlText by remember { mutableStateOf(entityToEdit?.url ?: "") }
    var loginText by remember { mutableStateOf(entityToEdit?.url ?: "") }
    var passwordText by remember { mutableStateOf(entityToEdit?.password ?: "") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = urlText,
            onValueChange = { urlText = it },
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .fillMaxWidth(),
            label = { Text(text = "Website address") },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            singleLine = true
        )

        TextField(
            value = loginText,
            onValueChange = { loginText = it },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            label = { Text("Login") },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            singleLine = true
        )

        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
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
            }
        )

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

        when (state) {
            EditPasswordState.Empty -> {}
            is EditPasswordState.Error -> {
                Text(state.msg)
            }
            EditPasswordState.SaveInProgress -> {
                Text("In progress...")
            }
            EditPasswordState.Success -> {
                // router.back()
            }
        }
    }
}
