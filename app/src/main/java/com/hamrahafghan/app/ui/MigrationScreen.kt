package com.hamrahafghan.app.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamrahafghan.app.network.MigrationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private data class MigrationLink(val name: String, val url: String)
private data class MigrationCategory(
    val id: String,
    val title: String,
    val summary: String,
    val links: List<MigrationLink>
)

@Composable
fun MigrationScreen() {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    var pageTitle by remember { mutableStateOf("🌍 مهاجرت و ویزا") }
    var notice by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf<List<MigrationCategory>>(emptyList()) }
    var selectedCategory by remember { mutableStateOf<MigrationCategory?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val data = withContext(Dispatchers.IO) { MigrationApi.load() }
            pageTitle = data.optString("title", pageTitle)
            notice = data.optString("notice", "")
            val arr = data.optJSONArray("categories")
            val list = mutableListOf<MigrationCategory>()
            if (arr != null) {
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    val linksArr = obj.optJSONArray("links")
                    val links = mutableListOf<MigrationLink>()
                    if (linksArr != null) {
                        for (j in 0 until linksArr.length()) {
                            val l = linksArr.getJSONObject(j)
                            links.add(MigrationLink(l.optString("name"), l.optString("url")))
                        }
                    }
                    list.add(
                        MigrationCategory(
                            id = obj.optString("id"),
                            title = obj.optString("title"),
                            summary = obj.optString("summary"),
                            links = links
                        )
                    )
                }
            }
            categories = list
        } catch (e: Exception) {
            error = true
        }
        loading = false
    }

    val filteredCategories = categories.filter {
        searchText.isBlank() || it.title.contains(searchText, ignoreCase = true) || it.summary.contains(searchText, ignoreCase = true)
    }

    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(pageTitle, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجو در مهاجرت و ویزا") },
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))
            if (loading) {
                CircularProgressIndicator()
                Text("در حال دریافت اطلاعات آنلاین...")
            } else if (error) {
                Text("❌ دریافت اطلاعات ناموفق بود.")
                Text("لطفاً اتصال اینترنت را بررسی کنید.")
            } else if (notice.isNotBlank()) {
                Card(Modifier.fillMaxWidth()) {
                    Text(
                        "⚠️ $notice",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(filteredCategories) { category ->
            Card(
                onClick = { selectedCategory = category },
                modifier = Modifier.fillMaxWidth()
            ) {
                ListItem(
                    headlineContent = { Text(category.title, fontWeight = FontWeight.Bold) },
                    trailingContent = { Text("›") }
                )
            }
        }

        if (!loading && !error && filteredCategories.isEmpty()) {
            item { Text("چیزی پیدا نشد.") }
        }
    }

    if (selectedCategory != null) {
        val cat = selectedCategory!!
        AlertDialog(
            onDismissRequest = { selectedCategory = null },
            title = { Text(cat.title) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(cat.summary)
                    if (cat.links.isNotEmpty()) {
                        Text("منابع رسمی:", fontWeight = FontWeight.Bold)
                        cat.links.forEach { link ->
                            OutlinedButton(
                                onClick = {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link.url)))
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("🔗 ${link.name}")
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedCategory = null }) { Text("بستن") }
            }
        )
    }
}
