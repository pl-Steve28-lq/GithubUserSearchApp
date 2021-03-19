package com.steve28.composepractice.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*

import com.steve28.composepractice.client.data.ProfileData
import com.steve28.composepractice.client.data.UserInfo
import com.steve28.composepractice.theme.SteveTheme
import com.steve28.composepractice.ui.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val color = SteveTheme.colors()

@Composable
fun MainContent(vm: MainViewModel) {
    val vm = remember { vm }
    val (query, setQuery)
        = remember { mutableStateOf("") }
    val (list, setList)
        = remember { mutableStateOf(listOf<ProfileData>()) }
    val (selected, setSelected)
        = remember { mutableStateOf(UserInfo(null, "", null)) }
    val isDialog
        = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    SteveTheme {
        Column {
            SearchComponent(
                query = query,
                setQuery = setQuery,
                setList = setList,
                scope = scope,
                vm = vm
            )

            ColumnComponent(list) { item ->
                scope.launch {
                    setSelected(vm.getUserInfo(item.name))
                    isDialog.value = true
                }
            }
        }

        if (isDialog.value)
            AlertComponent(
                selected = selected,
                onDismiss = { isDialog.value = false }
            )
    }
}

@Composable
fun SearchComponent(
    query: String, setQuery: (String) -> Unit,
    setList: (List<ProfileData>) -> Unit,
    scope: CoroutineScope, vm: MainViewModel
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = color.primaryVariant,
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = query,
                onValueChange = setQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text(text = "Search", color = Color.White) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (query.isNotBlank()) {
                            scope.launch {
                                setList(vm.getUserList(query))
                            }
                        }
                    }
                ),
                leadingIcon = { Icon(Icons.Filled.Search, null) },
                singleLine = true
            )
        }
    }
}

@Composable
fun ColumnComponent(
    list: List<ProfileData>,
    onClick: (item: ProfileData) -> Unit
) {
    LazyColumn {
        items(items = list) { item ->
            Card(
                shape = RoundedCornerShape(10.dp),
                backgroundColor = color.primary,
                modifier = Modifier
                    .padding(10.dp)
                    .fillParentMaxWidth()
                    .clickable { onClick(item) }
            ) { UserCardComponent(item = item) }
        }
    }
}

@Composable
fun UserCardComponent(item: ProfileData) {
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        CircularImage(image = item.image, size = 64, modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = item.name,
                fontSize = 25.sp,
                color = Color.White
            )
            Text(
                text = "https://www.github.com/${item.name}",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun AlertComponent(selected: UserInfo, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(text = selected.name ?: selected.username, fontSize = 25.sp)
                selected.name?.run { Text(text = selected.username, fontSize = 19.sp) }
            }
        },
        text = { Text(text = selected.bio ?: "") },
        buttons = {}
    )
}