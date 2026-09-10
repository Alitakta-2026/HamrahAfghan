package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.rememberCoroutineScope
import com.hamrahafghan.app.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun More() {
    var testResult by remember { mutableStateOf<String?>(null) }
    var testing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Text("☰ بیشتر", fontSize = 28.sp, fontWeight = FontWeight.Bold) }
        items(
            listOf(
                "🌍 مهاجرت و ویزا", "📱 سیم‌کارت", "💳 خدمات بانکی",
                "📄 مدارک و اقامت", "🏠 خانه و اجاره", "⚖️ حقوق و قوانین",
                "🏥 درمان", "📍 مراکز مهم", "🆘 کمک فوری",
                "💬 پرسش و پاسخ", "🔐 حریم خصوصی"
            )
        ) {
            Card(Modifier.fillMaxWidth()) {
                ListItem(headlineContent = { Text(it) }, trailingContent = { Text("›") })
            }
        }
        item {
            Button(onClick = {
                testing = true
                scope.launch {
                    testResult = try {
                        withContext(Dispatchers.IO) { NetworkClient.getTestData() }
                        "اتصال اینترنت موفق است ✅"
                    } catch (e: Exception) {
                        "اتصال ناموفق است ❌"
                    }
                    testing = false
                }
            }, modifier = Modifier.fillMaxWidth()) {
                if (testing) CircularProgressIndicator()
                else Text("🌐 تست اتصال اینترنت")
            }
        }
        if (testResult != null) {
            item {
                AlertDialog(
                    onDismissRequest = { testResult = null },
                    title = { Text("🌐 وضعیت اتصال") },
                    text = { Text(testResult!!) },
                    confirmButton = {
                        Button(onClick = { testResult = null }) { Text("باشه") }
                    }
                )
            }
        }
        item {
            Text("همراه افغان — نسخه ۶.۰",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
