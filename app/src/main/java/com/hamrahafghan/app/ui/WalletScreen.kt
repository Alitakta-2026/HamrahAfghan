package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
fun Wallet() {
    var searchText by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf(500000) }
    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("💰 کیف پول همراه", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = searchText, onValueChange = { searchText = it }, modifier = Modifier.fillMaxWidth(), label = { Text("🔎 جستجوی خدمات کیف پول") }, singleLine = true)
            Spacer(Modifier.height(8.dp))
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
        val filteredServices = listOf("📱 شارژ و اینترنت", "⚡ برق", "💧 آب", "🔥 گاز", "🚕 خدمات آنلاین").filter { it.contains(searchText, ignoreCase = true) }
        items(filteredServices) {
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
