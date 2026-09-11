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

data class GameQuestion(
    val question: String,
    val answers: List<String>,
    val correctAnswer: String
)

@Composable
fun EducationalGamesScreen() {
    val questions = listOf(
        GameQuestion(
            "🔤 کدام حرف اول کلمه «سیب» است؟",
            listOf("س", "م", "ک"),
            "س"
        ),
        GameQuestion(
            "🔢 حاصل ۲ + ۳ چند است؟",
            listOf("۴", "۵", "۶"),
            "۵"
        ),
        GameQuestion(
            "🐱 کدام حیوان می‌گوید «میو»؟",
            listOf("گربه", "سگ", "گاو"),
            "گربه"
        ),
        GameQuestion(
            "🌱 گیاه برای رشد به چه چیزی نیاز دارد؟",
            listOf("آب", "سنگ", "اسباب‌بازی"),
            "آب"
        ),
        GameQuestion(
            "🔢 حاصل ۵ - ۲ چند است؟",
            listOf("۲", "۳", "۴"),
            "۳"
        ),
        GameQuestion(
            "🐘 کدام حیوان خرطوم دارد؟",
            listOf("فیل", "گربه", "ماهی"),
            "فیل"
        ),
        GameQuestion(
            "☀️ خورشید چه چیزی به زمین می‌دهد؟",
            listOf("نور و گرما", "برف", "باران"),
            "نور و گرما"
        ),
        GameQuestion(
            "🔤 کدام کلمه با «ب» شروع می‌شود؟",
            listOf("بابا", "سیب", "مادر"),
            "بابا"
        ),
        GameQuestion(
            "🔢 کدام عدد بزرگ‌تر است؟",
            listOf("۳", "۷", "۵"),
            "۷"
        ),
        GameQuestion(
            "🌍 ما روی کدام سیاره زندگی می‌کنیم؟",
            listOf("زمین", "ماه", "مریخ"),
            "زمین"
        )
    )

    var questionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var streak by remember { mutableStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var lastCorrect by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }

    fun restartGame() {
        questionIndex = 0
        score = 0
        streak = 0
        answered = false
        lastCorrect = false
        finished = false
    }

    if (finished) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                "🏆 پایان بازی",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "⭐ امتیاز نهایی: $score از 10",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text("🔥 بهترین زنجیره پاسخ درست: $streak")

            val message = when {
                score >= 9 -> "🌟 عالی بود! تو یک قهرمان یادگیری هستی!"
                score >= 7 -> "👏 خیلی خوب بود! ادامه بده."
                score >= 5 -> "😊 خوب بود! با تمرین بهتر هم می‌شوی."
                else -> "💪 دوباره امتحان کن؛ با تمرین موفق می‌شوی."
            }

            Text(
                message,
                fontSize = 20.sp
            )

            Button(
                onClick = { restartGame() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔄 بازی دوباره")
            }
        }
        return
    }

    val current = questions[questionIndex]

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

        Text(
            "🎯 سؤال ${questionIndex + 1} از ${questions.size}",
            fontWeight = FontWeight.Bold
        )

        Text(
            "⭐ امتیاز: $score",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    current.question,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                current.answers.forEach { answer ->
                    Button(
                        onClick = {
                            if (!answered) {
                                lastCorrect = answer == current.correctAnswer

                                if (lastCorrect) {
                                    score++
                                    streak++
                                } else {
                                    streak = 0
                                }

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
                        if (lastCorrect) {
                            "✅ آفرین! جواب درست است."
                        } else {
                            "❌ جواب درست: ${current.correctAnswer}"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            if (questionIndex == questions.lastIndex) {
                                finished = true
                            } else {
                                questionIndex++
                                answered = false
                                lastCorrect = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (questionIndex == questions.lastIndex) {
                                "🏆 دیدن نتیجه"
                            } else {
                                "➡️ سؤال بعدی"
                            }
                        )
                    }
                }
            }
        }

        OutlinedButton(
            onClick = { restartGame() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔄 شروع دوباره")
        }
    }
}
