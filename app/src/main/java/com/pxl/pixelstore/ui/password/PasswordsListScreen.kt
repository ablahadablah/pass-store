package com.pxl.pixelstore.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.viewmodel.PasswordsListViewModel
import com.pxl.pixelstore.viewmodel.PasswordsScreenState

@Composable
fun PasswordsListScreen(
    onNavToEdit: (PasswordRecord) -> Unit
) {
    val viewModel = hiltViewModel<PasswordsListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PasswordsListForm(
        state,
        onSearch = { viewModel.searchPasswords(it) },
        onPasswordSelected = {
            onNavToEdit(it)
        },
    )
}

@Composable
fun PasswordsListForm(
    state: PasswordsScreenState,
    onSearch: (String) -> Unit,
    onPasswordSelected: (PasswordRecord) -> Unit
) {
    var searchValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SearchForm(searchValue) {
            searchValue = it
            onSearch(it)
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        if (state is PasswordsScreenState.Data) {
            PasswordsList(
                state.passwords,
                state.selectedEntity
            ) { e ->
                onPasswordSelected(e)
            }
        } else if (state is PasswordsScreenState.Empty) {
            Text("No passwords")
        }
    }
}
