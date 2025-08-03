package com.pxl.pixelstore.ui.password

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pxl.pixelstore.R
import com.pxl.pixelstore.domain.entity.PasswordRecord
import com.pxl.pixelstore.viewmodel.password.PasswordsListViewModel
import com.pxl.pixelstore.viewmodel.password.PasswordsScreenState

@Composable
fun PasswordsListScreen(
    navigateToEdit: (PasswordRecord) -> Unit
) {
    val viewModel = hiltViewModel<PasswordsListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PasswordsListForm(
        state,
        onSearch = { viewModel.searchPasswords(it) },
        onPasswordSelected = {
            navigateToEdit(it)
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
                .background(MaterialTheme.colorScheme.primary)
        )

        val transition = updateTransition(targetState = state)
        val progressOffsetY by transition.animateDp(
            transitionSpec = { tween(durationMillis = 500) },
        ) { st ->
            if (st is PasswordsScreenState.Data) {
                600.dp
            } else {
                0.dp
            }
        }

        if (state is PasswordsScreenState.Loading || progressOffsetY < 600.dp) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .offset(y = progressOffsetY),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        }

        if (state is PasswordsScreenState.Data /*&& listAlpha > 0f*/) {
            PasswordsList(
                state.passwords,
                state.selectedEntity,
                modifier = Modifier
                    .fillMaxSize()
            ) { e ->
                onPasswordSelected(e)
            }
        }

        if (state is PasswordsScreenState.Empty) {
            Text(
                text = stringResource(R.string.no_passwords_label),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
