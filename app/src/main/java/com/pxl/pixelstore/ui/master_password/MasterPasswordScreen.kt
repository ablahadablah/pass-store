package com.pxl.pixelstore.ui.master_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pxl.pixelstore.R
import com.pxl.pixelstore.R.string.password_label
import com.pxl.pixelstore.viewmodel.AuthEvent
import com.pxl.pixelstore.viewmodel.MasterPasswordViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MasterPasswordScreen(
    snackbarHostState: SnackbarHostState,
    onMasterPasswordEntered: () -> Unit
) {
    val vm = hiltViewModel<MasterPasswordViewModel>()

    var passwordValue by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        vm.events.collectLatest { e ->
            when (e) {
                is AuthEvent.Success -> {
                    onMasterPasswordEntered()
                }
                is AuthEvent.Error.InvalidMasterPassword -> {
                    snackbarHostState.showSnackbar("Invalid master password.")
                }
                is AuthEvent.Error.Unknown -> {
                    snackbarHostState.showSnackbar("Some error, please try again later.")
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(160.dp))

        Text(
            text = stringResource(R.string.master_password_label),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedTextField(
            value = passwordValue,
            onValueChange = { it: String -> passwordValue = it },
            modifier = Modifier
                .padding(paddingValues = PaddingValues(bottom = 16.dp))
                .width(500.dp),
            label = { Text(text = stringResource(password_label)) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                    vm.verifyMasterPassword(passwordValue)
                }
            ),
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
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !passwordValue.isBlank(),
            onClick = {
                vm.verifyMasterPassword(passwordValue)
            },
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.enter_master_password_label),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}
