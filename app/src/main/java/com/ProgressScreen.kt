package com.hamrahafghan.app.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressScreen() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("hamrah_prefs", Context.MODE_PRIVATE)
    val attempts = prefs.getInt("quiz_attempts", 0)
    val bestScore = prefs.getInt("quiz_best_score", 0)
    val lastScore = prefs.getInt("quiz_last_score", 0)
    val lastTotal = prefs.getInt("quiz_last_total", 0)

    Column(
        modifier = Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("⭐ پیشرفت کودک", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        if (attempts == 0) {
            Text(
                "هنوز هیچ آزمونی انجام نشده. برای دیدن پیشرفت، اول یک بار «تمرین و آزمون» را انجام بده.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("📊 تعداد آزمون‌های انجام‌شده", fontWeight = FontWeight.Bold)
                    Text("$attempts بار", fontSize = 20.sp)
                }
            }
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("🏆 بهترین نتیجه", fontWeight = FontWeight.Bold)
                    Text("$bestScore از $lastTotal", fontSize = 20.sp)
                }
            }
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("🕓 آخرین نتیجه", fontWeight = FontWeight.Bold)
                    Text("$lastScore از $lastTotal", fontSize = 20.sp)
                }
            }
        }
    }
}
