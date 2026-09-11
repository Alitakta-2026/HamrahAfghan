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
fun MathScreen() {
    var searchText by remember { mutableStateOf("") }

    val topics = listOf(
        "🔢 شمارش ۱ تا ۱۰" to "یادگیری اعداد با مثال‌های ساده",
        "➕ جمع ساده" to "جمع اعداد کوچک با مثال‌های تصویری",
        "➖ تفریق ساده" to "یادگیری کم کردن با مثال‌های کودکانه",
        "🔷 شکل‌ها و رنگ‌ها" to "آشنایی با دایره، مربع، مثلث و رنگ‌ها",
        "📏 اندازه‌گیری" to "بلند، کوتاه، بزرگ و کوچک",
        "🧠 تمرین ریاضی" to "تمرین‌های کوتاه برای تقویت مهارت ریاضی"
    )

    val filteredTopics = topics.filter {
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
                "🔢 ریاضی",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text("ریاضی را ساده و سرگرم‌کننده یاد بگیر")

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجو در ریاضی") },
                singleLine = true
            )
        }

        items(filteredTopics) { topic ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            topic.first,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    supportingContent = {
                        Text(topic.second)
                    }
                )
            }
        }

        if (filteredTopics.isEmpty()) {
            item {
                Text("موضوعی برای جستجوی شما پیدا نشد.")
            }
        }
    }
}
