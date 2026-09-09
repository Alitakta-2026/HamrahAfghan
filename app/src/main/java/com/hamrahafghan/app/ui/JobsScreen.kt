package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private fun normalizeText(text: String): String {
    return text
        .replace("ي", "ی")
        .replace("ى", "ی")
        .replace("ك", "ک")
        .replace("ۀ", "ه")
        .replace("ة", "ه")
        .trim()
}

@Composable
fun Jobs() {
    var selectedJob by remember { mutableStateOf<String?>(null) }
    var showAddJob by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("همه شهرها") }
    var cityMenuExpanded by remember { mutableStateOf(false) }

    val jobs = remember {
        mutableStateListOf(
            "کارگر ساده — تهران | حقوق: توافقی | ساعت: ۸ تا ۱۷ | توضیحات: کار در مجموعه خدماتی",
            "کمک‌آشپز — مشهد | حقوق: توافقی | ساعت: ۹ تا ۱۸ | توضیحات: کمک در آشپزخانه",
            "شاگرد مکانیکی — کرج | حقوق: توافقی | ساعت: ۸ تا ۱۷ | توضیحات: کمک به مکانیک و یادگیری کار",
            "نیروی خدماتی — قم | حقوق: توافقی | ساعت: ۷ تا ۱۶ | توضیحات: نظافت و خدمات مجموعه"
        )
    }

    val normalizedSearch = normalizeText(searchText)
    val cityOptions = listOf("همه شهرها", "تهران", "مشهد", "کرج", "قم")

    val filteredJobs = jobs.filter {
        val matchesSearch =
            normalizedSearch.isBlank() ||
                normalizeText(it).contains(normalizedSearch, ignoreCase = true)

        val matchesCity =
            selectedCity == "همه شهرها" ||
                normalizeText(it).contains(normalizeText(selectedCity), ignoreCase = true)

        matchesSearch && matchesCity
    }

    LazyColumn(
        Modifier.fillMaxSize().padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("💼 کاریابی", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("آگهی‌های کاری برای کاربران")

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجوی شغل یا شهر") },
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            Box {
                OutlinedButton(
                    onClick = { cityMenuExpanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("📍 شهر: $selectedCity")
                }

                DropdownMenu(
                    expanded = cityMenuExpanded,
                    onDismissRequest = { cityMenuExpanded = false }
                ) {
                    cityOptions.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(city) },
                            onClick = {
                                selectedCity = city
                                cityMenuExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { showAddJob = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("➕ ثبت آگهی کار")
            }
        }

        items(filteredJobs) { job ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(job, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Button(onClick = { selectedJob = job }) {
                        Text("مشاهده")
                    }
                }
            }
        }

        if (filteredJobs.isEmpty()) {
            item {
                Text(
                    "آگهی‌ای برای جستجوی شما پیدا نشد.",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    if (selectedJob != null) {
        JobDetailDialog(job = selectedJob ?: "", onDismiss = { selectedJob = null })
    }

    if (showAddJob) {
        AddJobDialog(
            onDismiss = { showAddJob = false },
            onSubmit = { entry -> jobs.add(entry) }
        )
    }
}

@Composable
private fun JobDetailDialog(job: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("جزئیات آگهی") },
        text = { Text(job) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("بستن")
            }
        }
    )
}

@Composable
private fun AddJobDialog(onDismiss: () -> Unit, onSubmit: (String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("ثبت آگهی کار") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("عنوان شغل") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("نوع کار") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("شهر") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = hours,
                    onValueChange = { hours = it },
                    label = { Text("ساعت کاری") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("توضیحات") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && city.isNotBlank()) {
                        onSubmit(
                            "$title — $type — $city | حقوق: توافقی | ساعت: $hours | توضیحات: $description"
                        )
                        onDismiss()
                    }
                }
            ) {
                Text("ثبت آگهی")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("انصراف")
            }
        }
    )
}
