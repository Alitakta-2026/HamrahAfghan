package com.hamrahafghan.app.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class QuizQuestion(val question: String, val options: List<String>, val correctIndex: Int)

private val quizQuestions = listOf(
    QuizQuestion("«الف» مربوط به کدام کلمه است؟", listOf("ابر", "ماه", "سیب", "توپ"), 0),
    QuizQuestion("«ب» مربوط به کدام کلمه است؟", listOf("درخت", "بابا", "زنبور", "فیل"), 1),
    QuizQuestion("۲ + ۳ = ؟", listOf("۴", "۵", "۶", "۷"), 1),
    QuizQuestion("۵ − ۲ = ؟", listOf("۱", "۲", "۳", "۴"), 2),
    QuizQuestion("«س» مربوط به کدام کلمه است؟", listOf("سیب", "ماه", "نان", "لیمو"), 0),
    QuizQuestion("۴ + ۴ = ؟", listOf("۶", "۷", "۸", "۹"), 2),
    QuizQuestion("کدام شکل گرد است؟", listOf("دایره", "مربع", "مثلث", "ستاره"), 0),
    QuizQuestion("«م» مربوط به کدام کلمه است؟", listOf("ماه", "ابر", "توپ", "جوجه"), 0),
    QuizQuestion("۱۰ − ۳ = ؟", listOf("۵", "۶", "۷", "۸"), 2),
    QuizQuestion("«ش» مربوط به کدام کلمه است؟", listOf("شیر", "نان", "یخ", "درخت"), 0)
)

private fun saveQuizResult(context: Context, score: Int, total: Int) {
    val prefs = context.getSharedPreferences("hamrah_prefs", Context.MODE_PRIVATE)
    val attempts = prefs.getInt("quiz_attempts", 0) + 1
    val bestScore = maxOf(prefs.getInt("quiz_best_score", 0), score)
    prefs.edit()
        .putInt("quiz_attempts", attempts)
        .putInt("quiz_best_score", bestScore)
        .putInt("quiz_last_score", score)
        .putInt("quiz_last_total", total)
        .apply()
}

@Composable
fun ExerciseScreen() {
    val context = LocalContext.current
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var finished by remember { mutableStateOf(false) }

    fun restart() {
        currentIndex = 0
        score = 0
        selectedOption = null
        finished = false
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("📝 تمرین و آزمون", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        if (!finished) {
            val question = quizQuestions[currentIndex]
            Text("سوال ${currentIndex + 1} از ${quizQuestions.size}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(question.question, fontSize = 22.sp, fontWeight = FontWeight.Bold)

            question.options.forEachIndexed { index, option ->
                val isSelected = selectedOption == index
                val isCorrect = index == question.correctIndex
                Card(
                    onClick = {
                        if (selectedOption == null) {
                            selectedOption = index
                            if (isCorrect) score++
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        option,
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        color = when {
                            selectedOption == null -> MaterialTheme.colorScheme.onSurface
                            isCorrect -> MaterialTheme.colorScheme.primary
                            isSelected -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        fontWeight = if (isSelected || (selectedOption != null && isCorrect)) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            if (selectedOption != null) {
                Button(
                    onClick = {
                        if (currentIndex + 1 < quizQuestions.size) {
                            currentIndex++
                            selectedOption = null
                        } else {
                            saveQuizResult(context, score, quizQuestions.size)
                            finished = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (currentIndex + 1 < quizQuestions.size) "سوال بعدی" else "پایان آزمون")
                }
            }
        } else {
            Text("🎉 آزمون تمام شد!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("نتیجه‌ی تو: $score از ${quizQuestions.size}", fontSize = 20.sp)
            OutlinedButton(onClick = { restart() }, modifier = Modifier.fillMaxWidth()) {
                Text("🔁 دوباره امتحان کن")
            }
        }
    }
}
