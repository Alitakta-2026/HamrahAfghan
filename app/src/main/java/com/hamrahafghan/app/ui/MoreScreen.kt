package com.hamrahafghan.app.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamrahafghan.app.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private data class MoreItem(
    val title: String,
    val ready: Boolean,
    val action: (() -> Unit)? = null
)

@Composable
fun More(onMigrationClick: () -> Unit = {}, onChildrenClick: () -> Unit = {}) {
    var searchText by remember { mutableStateOf("") }
    var testResult by remember { mutableStateOf<String?>(null) }
    var testing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val moreItems = listOf(
        MoreItem("🌍 مهاجرت و ویزا", ready = true, action = onMigrationClick),
        MoreItem("📚 آموزش کودکان", ready = true, action = onChildrenClick),
        MoreItem("📱 سیم‌کارت", ready = false),
        MoreItem("💳 خدمات بانکی", ready = false),
        MoreItem("📄 مدارک و اقامت", ready = false),
        MoreItem("🏠 خانه و اجاره", ready = false),
        MoreItem("⚖️ حقوق و قوانین", ready = false),
        MoreItem("🏥 درمان", ready = false),
        MoreItem("📍 مراکز مهم", ready = false),
        MoreItem("🆘 کمک فوری", ready = false),
        MoreItem("💬 پرسش و پاسخ", ready = false),
        MoreItem("🔐 حریم خصوصی", ready = true, action = {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://alitakta-2026.github.io/HamrahAfghan/privacy.html")
                )
            )
        })
    ).filter { it.title.contains(searchText, ignoreCase = true) }

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

        items(moreItems) { moreItem ->
            Card(
                onClick = { moreItem.action?.invoke() },
                modifier = Modifier.fillMaxWidth(),
                colors = if (moreItem.ready) {
                    CardDefaults.cardColors()
                } else {
                    CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                }
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            moreItem.title,
                            color = if (moreItem.ready) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    },
                    trailingContent = {
                        if (moreItem.ready) {
                            Text("›")
                        } else {
                            Text(
                                "به‌زودی",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.secondaryContainer,
                                        RoundedCornerShape(50)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    },
                    colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
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
