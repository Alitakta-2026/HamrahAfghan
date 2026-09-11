package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamrahafghan.app.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun More(onMigrationClick: () -> Unit = {}) {
    var searchText by remember { mutableStateOf("") }
    var testResult by remember { mutableStateOf<String?>(null) }
    var testing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val moreItems = listOf(
        "🌍 مهاجرت و ویزا",
        "📱 سیم‌کارت",
        "💳 خدمات بانکی",
        "📄 مدارک و اقامت",
        "🏠 خانه و اجاره",
        "⚖️ حقوق و قوانین",
        "🏥 درمان",
        "📍 مراکز مهم",
        "🆘 کمک فوری",
        "💬 پرسش و پاسخ",
        "🔐 حریم خصوصی"
    ).filter { it.contains(searchText, ignoreCase = true) }

    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("☰ بیشتر", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجو در خدمات") },
                singleLine = true
            )
        }

        items(moreItems) {
            Card(
                onClick = {
                    if (it == "🌍 مهاجرت و ویزا") onMigrationClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                ListItem(
                    headlineContent = { Text(it) },
                    trailingContent = { Text("›") }
                )
            }
        }

        item {
            Button(
                onClick = {
                    testing = true
                    scope.launch {
                        testResult = try {
                            withContext(Dispatchers.IO) {
                                NetworkClient.getTestData()
                            }
                            "اتصال اینترنت موفق است ✅"
                        } catch (e: Exception) {
                            "اتصال ناموفق است ❌"
                        }
                        testing = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (testing) {
                    CircularProgressIndicator()
                } else {
                    Text("🌐 تست اتصال اینترنت")
                }
            }
        }

        item {
            Text(
                "همراه افغان — نسخه ۶.۰",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    if (testResult != null) {
        AlertDialog(
            onDismissRequest = { testResult = null },
            title = { Text("🌐 وضعیت اتصال") },
            text = { Text(testResult!!) },
            confirmButton = {
                Button(onClick = { testResult = null }) {
                    Text("باشه")
                }
            }
        )
    }
}
