package com.pxl.pixelstore.ui.password

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pxl.pixelstore.domain.entity.PasswordRecord

@Composable
fun PasswordsListItem(
    entity: PasswordRecord,
    selected: Boolean,
    onItemClicked: (PasswordRecord) -> Unit
) {
    Row(modifier = Modifier
        .padding(bottom = 8.dp)
        .height(52.dp)
        .fillMaxWidth()
        .clickable {
            onItemClicked(entity)
        }
    ) {
        val selectorColor = if (selected) {
            Color.Cyan
        } else {
            Color.Transparent
        }

        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(5.dp)
                .background(selectorColor),
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            ItemLabel(entity.url)
            ItemLabel(entity.username)
        }
    }
}

@Composable
fun ItemLabel(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun PasswordsList(
    passwordsList: List<PasswordRecord>,
    selectedEntity: PasswordRecord?,
    onItemClicked: (PasswordRecord) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
        state = listState
    ) {
        items(
            items = passwordsList,
            key = { e -> e.id.toString() }
        ) { pass ->
            PasswordsListItem(pass, pass == selectedEntity, onItemClicked)
        }
    }
}
