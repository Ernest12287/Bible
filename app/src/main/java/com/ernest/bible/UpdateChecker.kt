package com.ernest.bible

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

/**
 * Checks for app updates from GitHub Releases
 * Uses the GitHub API to fetch the latest release info
 */
object UpdateChecker {

    private const val GITHUB_API_URL = "https://api.github.com/repos/Ernest12287/Bible/releases/latest"

    /**
     * Fetches the latest version from GitHub
     * Returns the version tag (e.g., "v1.0.1") or null if error
     */
    suspend fun getLatestVersion(): String? = withContext(Dispatchers.IO) {
        try {
            val response = URL(GITHUB_API_URL).readText()
            val json = JSONObject(response)
            json.getString("tag_name") // e.g., "v1.0.1"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Gets the download URL for the latest APK
     */
    suspend fun getDownloadUrl(): String? = withContext(Dispatchers.IO) {
        try {
            val response = URL(GITHUB_API_URL).readText()
            val json = JSONObject(response)
            val assets = json.getJSONArray("assets")

            // Find the .apk file in the release assets
            for (i in 0 until assets.length()) {
                val asset = assets.getJSONObject(i)
                val name = asset.getString("name")
                if (name.endsWith(".apk")) {
                    return@withContext asset.getString("browser_download_url")
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Compares current version with latest version
     * Returns true if update is available
     */
    fun isUpdateAvailable(currentVersion: String, latestVersion: String): Boolean {
        // Remove 'v' prefix if present
        val current = currentVersion.removePrefix("v")
        val latest = latestVersion.removePrefix("v")

        return try {
            val currentParts = current.split(".").map { it.toInt() }
            val latestParts = latest.split(".").map { it.toInt() }

            for (i in 0 until maxOf(currentParts.size, latestParts.size)) {
                val c = currentParts.getOrNull(i) ?: 0
                val l = latestParts.getOrNull(i) ?: 0
                if (l > c) return true
                if (l < c) return false
            }
            false
        } catch (e: Exception) {
            false
        }
    }
}

/**
 * Composable that shows an update dialog when new version is available
 */
@Composable
fun UpdateDialog(
    currentVersion: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var latestVersion by remember { mutableStateOf<String?>(null) }
    var downloadUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val latest = UpdateChecker.getLatestVersion()
        val download = UpdateChecker.getDownloadUrl()

        if (latest != null && UpdateChecker.isUpdateAvailable(currentVersion, latest)) {
            latestVersion = latest
            downloadUrl = download
            showDialog = true
        }
    }

    if (showDialog && latestVersion != null) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onDismiss()
            },
            title = { Text("Update Available") },
            text = {
                Text("A new version ($latestVersion) is available!\n\nCurrent version: $currentVersion")
            },
            confirmButton = {
                TextButton(onClick = {
                    downloadUrl?.let { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                    showDialog = false
                    onDismiss()
                }) {
                    Text("Download")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDismiss()
                }) {
                    Text("Later")
                }
            }
        )
    }
}