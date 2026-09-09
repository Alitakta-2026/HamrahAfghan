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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun More() {
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
            Text("همراه افغان — نسخه ۶.۰",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
