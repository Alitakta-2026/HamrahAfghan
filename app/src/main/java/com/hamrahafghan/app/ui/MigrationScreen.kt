package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hamrahafghan.app.network.MigrationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp

@Composable
fun MigrationScreen() {
    var searchText by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("🌍 مهاجرت و ویزا") }
    var notice by remember { mutableStateOf("") }
    var source by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        try {
            val data = withContext(Dispatchers.IO) { MigrationApi.load() }
            title = data.optString("title", title)
            notice = data.optString("notice", "")
        } catch (e: Exception) {
            error = true
        }
        loading = false
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(value = searchText, onValueChange = { searchText = it }, modifier = Modifier.fillMaxWidth(), label = { Text("🔎 جستجو در مهاجرت و ویزا") }, singleLine = true)
        Text(title)
        if (loading) {
            CircularProgressIndicator()
            Text("در حال دریافت اطلاعات آنلاین...")
        } else if (error) {
            Text("❌ دریافت اطلاعات ناموفق بود.")
            Text("لطفاً اتصال اینترنت را بررسی کنید.")
        } else {
            Text(notice)
            Text("📌 منبع: ${source.ifBlank { "منبع رسمی" }}")
        }
    }
}
