package com.hamrahafghan.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
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

private data class LessonItem(
    val title: String,
    val description: String,
    val ready: Boolean,
    val action: (() -> Unit)? = null
)

@Composable
fun ChildrenEducationScreen(
    onAlphabetClick: () -> Unit = {},
    onMathClick: () -> Unit = {},
    onScienceClick: () -> Unit = {},
    onGamesClick: () -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }

    val lessons = listOf(
        LessonItem("🔤 الفبا و خواندن", "یادگیری حروف، کلمات و خواندن ساده", ready = true, action = onAlphabetClick),
        LessonItem("🔢 ریاضی", "اعداد، جمع، تفریق و تمرین‌های ساده", ready = true, action = onMathClick),
        LessonItem("🔬 علوم و دانستنی‌ها", "آشنایی با طبیعت، بدن و دنیای اطراف", ready = true, action = onScienceClick),
        LessonItem("🎮 بازی‌های آموزشی", "بازی‌های سرگرم‌کننده برای یادگیری بهتر", ready = true, action = onGamesClick),
        LessonItem("📝 تمرین و آزمون", "تمرین‌های کوتاه و آزمون‌های آموزشی", ready = false),
        LessonItem("⭐ پیشرفت کودک", "پیگیری درس‌ها و تمرین‌های انجام‌شده", ready = false)
    )

    val filteredLessons = lessons.filter {
        it.title.contains(searchText, ignoreCase = true) ||
            it.description.contains(searchText, ignoreCase = true)
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
            Card(
                onClick = { lesson.action?.invoke() },
                modifier = Modifier.fillMaxWidth(),
                colors = if (lesson.ready) {
                    CardDefaults.cardColors()
                } else {
                    CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                }
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            lesson.title,
                            fontWeight = FontWeight.Bold,
                            color = if (lesson.ready) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    },
                    supportingContent = { Text(lesson.description) },
                    trailingContent = {
                        if (!lesson.ready) {
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

        if (filteredLessons.isEmpty()) {
            item {
                Text("درسی برای جستجوی شما پیدا نشد.")
            }
        }
    }
}
