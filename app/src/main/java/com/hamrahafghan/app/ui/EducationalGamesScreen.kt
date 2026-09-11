package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
fun EducationalGamesScreen() {
    var score by remember { mutableStateOf(0) }
    var question by remember { mutableStateOf(0) }

    val questions = listOf(
        Triple("🔤 کدام حرف اول کلمه «سیب» است؟", listOf("س", "م", "ک"), "س"),
        Triple("🔢 حاصل ۲ + ۳ چند است؟", listOf("۴", "۵", "۶"), "۵"),
        Triple("🐱 کدام حیوان می‌گوید «میو»؟", listOf("گربه", "سگ", "گاو"), "گربه"),
        Triple("🌱 گیاه برای رشد به چه چیزی نیاز دارد؟", listOf("آب", "سنگ", "اسباب‌بازی"), "آب")
    )

    val current = questions[question]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "🎮 بازی‌های آموزشی",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text("با جواب دادن به سؤال‌ها یاد بگیر و امتیاز بگیر!")

        Text(
            "⭐ امتیاز: $score",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    current.first,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                current.second.forEach { answer ->
                    Button(
                        onClick = {
                            if (answer == current.third) {
                                score++
                            }

                            question = (question + 1) % questions.size
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(answer)
                    }
                }
            }
        }

        Text("🎯 به سؤال‌ها جواب بده و رکورد خودت را بهتر کن!")
    }
}
