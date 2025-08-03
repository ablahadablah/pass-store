package com.pxl.pixelstore.ui.password

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pxl.pixelstore.R
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.viewmodel.password.PasswordDetailsViewModel
import com.pxl.pixelstore.viewmodel.password.UiState

@Composable
fun PasswordDetailsScreen(
    passwordRecordId: String?,
    onEditClicked: (PasswordRecord) -> Unit,
    onRecordDeleted: () -> Unit
) {
    val viewModel = hiltViewModel<PasswordDetailsViewModel>()
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(passwordRecordId) {
        viewModel.loadPasswordRecord(passwordRecordId)
    }

    if (uiState is UiState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        }
    }

    if (uiState is UiState.Data) {
        val entity = (uiState as UiState.Data).record

        PasswordDetailsForm(
            entity,
            onEditClicked = onEditClicked,
            onRemoveClicked = {
                viewModel.deleteEntity(entity)
                onRecordDeleted()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetailsForm(
    entity: PasswordRecord,
    onEditClicked: (PasswordRecord) -> Unit,
    onRemoveClicked: (PasswordRecord) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.confirm_delete_message))

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { showSheet = false }) {
                        Text(stringResource(R.string.cancel_label))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            // TODO: perform deletion
                            onRemoveClicked(entity)
                            showSheet = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(stringResource(R.string.delete_label))
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = entity.url,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .height(48.dp)
                .widthIn(min = 5.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Mail,
                    contentDescription = "email icon",

                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(24.dp),
                )

                Column {
                    Text(
                        text = stringResource(R.string.email_label),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = entity.username,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.Key,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(24.dp),
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.password_label),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = if (passwordVisible) {
                            entity.password
                        } else {
                            stringResource(R.string.hidden_password_label)
                        },
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(
                    onClick = { passwordVisible = !passwordVisible},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(24.dp),
                ) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = "",
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = { onEditClicked(entity) }
            ) {
                Text(stringResource(R.string.edit_label))
            }

            Spacer(Modifier.width(16.dp))

            TextButton(
                modifier = Modifier.weight(1f),
                onClick = { showSheet = true }
            ) {
                Text(stringResource(R.string.delete_label))
            }
        }
    }
}

@Preview
@Composable
fun PasswordDetailsScreenPreview() {
    PasswordDetailsForm(
        PasswordRecord(
            url = "google.com",
            username = "r.egrv",
            password = "asdf"
        ),
        {},
        {}
    )
}