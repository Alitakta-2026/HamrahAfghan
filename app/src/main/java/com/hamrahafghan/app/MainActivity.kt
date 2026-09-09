package com.hamrahafghan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.unit.LayoutDirection

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    var page by remember { mutableStateOf("home") }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = page == "home",
                            onClick = { page = "home" },
                            icon = { Text("🏠") },
                            label = { Text("خانه") }
                        )
                        NavigationBarItem(
                            selected = page == "wallet",
                            onClick = { page = "wallet" },
                            icon = { Text("💰") },
                            label = { Text("کیف پول") }
                        )
                        NavigationBarItem(
                            selected = page == "jobs",
                            onClick = { page = "jobs" },
                            icon = { Text("💼") },
                            label = { Text("کار") }
                        )
                        NavigationBarItem(
                            selected = page == "more",
                            onClick = { page = "more" },
                            icon = { Text("☰") },
                            label = { Text("بیشتر") }
                        )
                    }
                }
            ) { padding ->
                Box(Modifier.fillMaxSize().padding(padding)) {
                    when (page) {
                        "home" -> Home { page = it }
                        "wallet" -> Wallet()
                        "jobs" -> Jobs()
                        else -> More()
                    }
                }
            }
        }
    }
}

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

@Composable
fun Wallet() {
    var balance by remember { mutableStateOf(500000) }
    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("💰 کیف پول همراه", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp)) {
                    Text("موجودی آزمایشی")
            Text("موجودی: $balance تومان", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = { balance += 50000 },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("➕ افزایش موجودی آزمایشی") }
                }
            }
        }
        item { Text("خدمات پرداخت", fontWeight = FontWeight.Bold) }
        items(listOf("📱 شارژ و اینترنت", "⚡ برق", "💧 آب", "🔥 گاز", "🚕 خدمات آنلاین")) {
            Card(Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = { Text(it) },
                    trailingContent = { Text("آزمایشی") }
                )
            }
        }
        item {
            Text(
                "⚠️ این نسخه هنوز به پرداخت واقعی متصل نیست.",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun Jobs() {
    var selectedJob by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val jobs = listOf(
        "کارگر ساده — تهران | حقوق: توافقی | ساعت: ۸ تا ۱۷ | توضیحات: کار در مجموعه خدماتی",
        "کمک‌آشپز — مشهد | حقوق: توافقی | ساعت: ۹ تا ۱۸ | توضیحات: کمک در آشپزخانه",
        "شاگرد مکانیکی — کرج | حقوق: توافقی | ساعت: ۸ تا ۱۷ | توضیحات: کمک به مکانیک و یادگیری کار",
        "نیروی خدماتی — قم | حقوق: توافقی | ساعت: ۷ تا ۱۶ | توضیحات: نظافت و خدمات مجموعه"
    )
    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("💼 کاریابی", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("آگهی‌های نمونه برای نسخه آزمایشی")
        }
        items(jobs) { job ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Button(onClick = { selectedJob = job }) { Text("مشاهده") }
                    Text("برای اطلاعات بیشتر و ثبت آگهی، نسخه بعدی تکمیل می‌شود.")
                }
            }
        }
    }
    if (selectedJob != null) {
        AlertDialog(
            onDismissRequest = { selectedJob = null },
            title = { Text("جزئیات آگهی") },
            text = { Text("عنوان شغل: $selectedJob\n\nبرای اطلاعات بیشتر با آگهی‌دهنده تماس بگیرید.") },
            confirmButton = {
                Button(onClick = { selectedJob = null }) { Text("بستن") }
            }
        )
    }
}

@Composable
fun More() {
    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Text("☰ بیشتر", fontSize = 28.sp, fontWeight = FontWeight.Bold) }
        items(
            listOf(
                "🌍 مهاجرت و ویزا", "📱 سیم‌کارت", "💳 خدمات بانکی",
                "📄 مدارک و اقامت", "🏠 خانه و اجاره", "⚖️ حقوق و قوانین",
                "🏥 درمان", "📍 مراکز مهم", "🆘 کمک فوری",
                "💬 پرسش و پاسخ", "🔐 حریم خصوصی"
            )
        ) {
            Card(Modifier.fillMaxWidth()) {
                ListItem(headlineContent = { Text(it) }, trailingContent = { Text("›") })
            }
        }
        item {
            Text("همراه افغان — نسخه ۶.۰",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
