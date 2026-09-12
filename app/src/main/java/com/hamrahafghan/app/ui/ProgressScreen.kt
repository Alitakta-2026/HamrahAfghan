package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressScreen() {
    var searchText by remember { mutableStateOf("") }

    val progress = listOf(
        "⭐ امتیاز کودک" to "امتیاز فعلی: 120",
        "🔤 الفبا و خواندن" to "پیشرفت: 40٪",
        "🔢 ریاضی" to "پیشرفت: 30٪",
        "🔬 علوم و دانستنی‌ها" to "پیشرفت: 20٪",
        "🎮 بازی‌های آموزشی" to "بازی‌های انجام‌شده: 5",
        "🏆 سطح کودک" to "سطح فعلی: شروع‌کننده"
    )

    val filteredProgress = progress.filter {
        it.first.contains(searchText, ignoreCase = true) ||
        it.second.contains(searchText, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                "⭐ پیشرفت کودک",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text("پیشرفت و فعالیت‌های آموزشی کودک را دنبال کنید")

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجو در پیشرفت") },
                singleLine = true
            )
        }

        items(filteredProgress) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = {
                        Text(
                            item.first,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )
                    },
                    supportingContent = {
                        Text(item.second)
                    }
                )
            }
        }
    }
}
