package com.ernest.bible

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    viewState: BookViewState,
    onBookClicked: (bookName: String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(context = context)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("KJV Offline Bible") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding)) {

                item {
                    BookSectionHeader("Old Testament")
                }

                items(viewState.oldTestament) { bookName ->
                    BookListItem(bookName = bookName) {
                        onBookClicked(bookName)
                    }
                }

                item {
                    BookSectionHeader("New Testament")
                }

                items(viewState.newTestament) { bookName ->
                    BookListItem(bookName = bookName) {
                        onBookClicked(bookName)
                    }
                }
            }
        }
    }
}

@Composable
fun BookSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
    HorizontalDivider()
}

@Composable
fun BookListItem(bookName: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = bookName,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    HorizontalDivider(thickness = 0.5.dp)
}

@Composable
fun DrawerContent(context: Context) {
    ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "KJV Bible (Ernest)",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Tech House",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))

            DrawerItem(
                text = "Join our WhatsApp",
                icon = "💬",
                onClick = {
                    openUrl(context, "https://whatsapp.com/channel/0029VayK4ty7DAWr0jeCZx0i")
                }
            )
            DrawerItem(
                text = "Join our Telegram",
                icon = "✈️",
                onClick = {
                    openUrl(context, "https://t.me/ernesttechhouse")
                }
            )
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            DrawerItem(
                text = "Check for Updates",
                icon = "⬆️",
                onClick = {
                    checkForUpdates(context)
                }
            )
            DrawerItem(
                text = "About",
                icon = "ℹ️",
                onClick = {
                    showAboutDialog(context)
                }
            )
        }
    }
}

@Composable
fun DrawerItem(text: String, icon: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 20.sp)
        Spacer(Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

private fun openUrl(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun checkForUpdates(context: Context) {
    openUrl(context, "https://github.com/Ernest12287/Bible/releases/latest")
}

private fun showAboutDialog(context: Context) {
    openUrl(context, "https://github.com/Ernest12287/Bible")
}