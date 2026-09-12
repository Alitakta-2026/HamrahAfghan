package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
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

data class ExerciseQuestion(
    val question: String,
    val answers: List<String>,
    val correctAnswer: String
)

@Composable
fun ExerciseTestScreen() {
    val questions = listOf(
        ExerciseQuestion("🔤 کدام حرف اول «بابا» است؟", listOf("ب", "م", "س"), "ب"),
        ExerciseQuestion("🔢 حاصل ۲ + ۲ چند است؟", listOf("۳", "۴", "۵"), "۴"),
        ExerciseQuestion("🍎 کدام مورد میوه است؟", listOf("سیب", "سنگ", "مداد"), "سیب"),
        ExerciseQuestion("🐟 کدام حیوان در آب زندگی می‌کند؟", listOf("ماهی", "گربه", "گوسفند"), "ماهی"),
        ExerciseQuestion("🌱 گیاه برای رشد به چه چیزی نیاز دارد؟", listOf("آب", "سنگ", "اسباب‌بازی"), "آب"),
        ExerciseQuestion("🔢 حاصل ۵ - ۳ چند است؟", listOf("۱", "۲", "۳"), "۲"),
        ExerciseQuestion("🌍 ما روی کدام سیاره زندگی می‌کنیم؟", listOf("زمین", "ماه", "مریخ"), "زمین"),
        ExerciseQuestion("☀️ خورشید چه چیزی دارد؟", listOf("نور و گرما", "یخ", "برف"), "نور و گرما")
    )

    var index by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var correct by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }

    fun restart() {
        index = 0
        score = 0
        answered = false
        correct = false
        finished = false
    }

    if (finished) {
        Column(
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("📝 نتیجه آزمون", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(
                "⭐ امتیاز: $score از ${questions.size}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                when {
                    score >= 7 -> "🌟 عالی بود! خیلی خوب یاد گرفتی."
                    score >= 5 -> "👏 خوب بود! کمی بیشتر تمرین کن."
                    else -> "💪 اشکالی ندارد؛ دوباره تمرین کن."
                },
                fontSize = 20.sp
            )

            Button(
                onClick = { restart() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔄 آزمون دوباره")
            }
        }
        return
    }

    val current = questions[index]

    Column(
        modifier = Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("📝 تمرین و آزمون", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("🎯 سؤال ${index + 1} از ${questions.size}", fontWeight = FontWeight.Bold)
        Text("⭐ امتیاز: $score", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(current.question, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                current.answers.forEach { answer ->
                    Button(
                        onClick = {
                            if (!answered) {
                                correct = answer == current.correctAnswer
                                if (correct) score++
                                answered = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(answer)
                    }
                }

                if (answered) {
                    Text(
                        if (correct) "✅ آفرین! جواب درست است."
                        else "❌ جواب درست: ${current.correctAnswer}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            if (index == questions.lastIndex) {
                                finished = true
                            } else {
                                index++
                                answered = false
                                correct = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (index == questions.lastIndex) "🏆 دیدن نتیجه" else "➡️ سؤال بعدی")
                    }
                }
            }
        }

        OutlinedButton(
            onClick = { restart() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔄 شروع دوباره")
        }
    }
}
