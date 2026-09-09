package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Home(go: (String) -> Unit) {
    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("همراه افغان", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("در کنار شما، در هر قدم از راه",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(18.dp)) {
                    Text("💰 کیف پول همراه", fontWeight = FontWeight.Bold)
                    Text("پرداخت شارژ، قبض و خدمات")
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { go("wallet") },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("ورود به کیف پول") }
                }
            }
        }
        item { Section("💼 کاریابی", "پیدا کردن کار و ثبت آگهی", go, "jobs") }
        item { Section("🌍 مهاجرت و ویزا", "اطلاعات و منابع معتبر", go, "more") }
        item { Section("📚 آموزش کودکان", "آموزش و سرگرمی", go, "more") }
        item { Section("🏠 خانه و خدمات", "اطلاعات کاربردی", go, "more") }
        item { Section("🆘 کمک فوری", "شماره‌ها و مراکز مهم", go, "more") }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("🔐 امنیت شما مهم است", fontWeight = FontWeight.Bold)
                    Text("رمز کارت، CVV2 و رمز یک‌بارمصرف را در اختیار هیچ‌کس قرار ندهید.")
                }
            }
        }
        item {
            Text("نسخه ۶.۰ — نسخه آماده‌سازی انتشار",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun Section(t: String, s: String, go: (String) -> Unit, target: String) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(t, fontWeight = FontWeight.Bold)
                Text(s, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TextButton(onClick = { go(target) }) { Text("ورود") }
        }
    }
}
