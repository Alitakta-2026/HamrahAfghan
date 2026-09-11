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
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
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
fun ChildrenEducationScreen(onAlphabetClick: () -> Unit = {}) {
    var searchText by remember { mutableStateOf("") }

    val lessons = listOf(
        "🔤 الفبا و خواندن" to "یادگیری حروف، کلمات و خواندن ساده",
        "🔢 ریاضی" to "اعداد، جمع، تفریق و تمرین‌های ساده",
        "🔬 علوم و دانستنی‌ها" to "آشنایی با طبیعت، بدن و دنیای اطراف",
        "🎮 بازی‌های آموزشی" to "بازی‌های سرگرم‌کننده برای یادگیری بهتر",
        "📝 تمرین و آزمون" to "تمرین‌های کوتاه و آزمون‌های آموزشی",
        "⭐ پیشرفت کودک" to "پیگیری درس‌ها و تمرین‌های انجام‌شده"
    )

    val filteredLessons = lessons.filter {
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
                "📚 آموزش کودکان",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text("یادگیری ساده، مفید و سرگرم‌کننده برای کودکان")

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجو در آموزش") },
                singleLine = true
            )
        }

        items(filteredLessons) { lesson ->
            Card(onClick = { if (lesson.first.startsWith("🔤")) onAlphabetClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            lesson.first,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    supportingContent = {
                        Text(lesson.second)
                    }
                )
            }
        }

        if (filteredLessons.isEmpty()) {
            item {
                Text("درسی برای جستجوی شما پیدا نشد.")
            }
        }
    }
}
