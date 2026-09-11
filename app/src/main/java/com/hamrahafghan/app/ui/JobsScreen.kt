package com.hamrahafghan.app.ui

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Job(val title: String, val type: String, val city: String, val salary: String, val phone: String, val hours: String, val description: String)


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
    var selectedJob by remember { mutableStateOf<Job?>(null) }
    var showAddJob by remember { mutableStateOf(false) }
    var editingJob by remember { mutableStateOf<Job?>(null) }
    var showMyJobs by remember { mutableStateOf(false) }
    val myJobs = remember { mutableStateListOf<Job>() }
    var searchText by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("همه شهرها") }
    var cityMenuExpanded by remember { mutableStateOf(false) }

    val jobs = remember {
        mutableStateListOf(
            Job("کارگر ساده","خدماتی","تهران","توافقی","","۸ تا ۱۷","کار در مجموعه خدماتی"),
            Job("کمک‌آشپز","آشپزی","مشهد","توافقی","","۹ تا ۱۸","کمک در آشپزخانه"),
            Job("شاگرد مکانیکی","مکانیکی","کرج","توافقی","","۸ تا ۱۷","کمک به مکانیک و یادگیری کار"),
            Job("نیروی خدماتی","خدماتی","قم","توافقی","","۷ تا ۱۶","نظافت و خدمات مجموعه")
        )
    }
    val normalizedSearch = normalizeText(searchText)
    val cityOptions = listOf("همه شهرها", "تهران", "مشهد", "کرج", "قم")

    val filteredJobs = jobs.filter {
        val matchesSearch =
            normalizedSearch.isBlank() ||
                normalizeText(it.title+" "+it.type+" "+it.city+" "+it.description).contains(normalizedSearch, ignoreCase = true)

        val matchesCity =
            selectedCity == "همه شهرها" ||
                normalizeText(it.city).contains(normalizeText(selectedCity), ignoreCase = true)

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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(Modifier.weight(1f)) {
                    OutlinedButton(onClick = { cityMenuExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Text("📍 شهر")
                    }
                    DropdownMenu(expanded = cityMenuExpanded, onDismissRequest = { cityMenuExpanded = false }) {
                        cityOptions.forEach { city ->
                            DropdownMenuItem(text = { Text(city) }, onClick = {
                                selectedCity = city
                                cityMenuExpanded = false
                            })
                        }
                    }
                }
                Button(onClick = { showAddJob = true }, modifier = Modifier.weight(1f)) {
                    Text("➕ ثبت آگهی")
                }
                OutlinedButton(onClick = { showMyJobs = true }, modifier = Modifier.weight(1f)) {
                    Text("📋 آگهی‌های من")
                }
            }
            Spacer(Modifier.height(8.dp))

        items(filteredJobs) { job ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(job.title, fontWeight = FontWeight.Bold)
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
        JobDetailDialog(job = selectedJob!!, onDismiss = { selectedJob = null })
    }

    if (showMyJobs) {
        AlertDialog(
            onDismissRequest = { showMyJobs = false },
            title = { Text("📋 آگهی‌های من") },
            text = {
                if (myJobs.isEmpty()) {
                    Text("هنوز آگهی‌ای ثبت نکرده‌اید.")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        myJobs.forEach { job ->
                            Column { Text("${job.title} — ${job.city}") ; Button(onClick = { editingJob = job; showMyJobs = false }) { Text("✏️ ویرایش") }; Button(onClick = { myJobs.remove(job); jobs.remove(job) }) { Text("🗑️ حذف") } }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showMyJobs = false }) { Text("بستن") }
            }
        )
    }
    if (editingJob != null) { AddJobDialog(existingJob = editingJob, onDismiss = { editingJob = null }, onSubmit = { entry -> val i = myJobs.indexOf(editingJob); if (i >= 0) myJobs[i] = entry; val j = jobs.indexOf(editingJob); if (j >= 0) jobs[j] = entry; editingJob = null }) }
    if (showAddJob) {
        AddJobDialog(
            onDismiss = { showAddJob = false },
            onSubmit = { entry ->
                jobs.add(entry)
                myJobs.add(entry)
            }
        )
    }
}

@Composable
private fun JobDetailDialog(job: Job, onDismiss: () -> Unit) {
    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current

    if (showMessage) {
        AlertDialog(
            onDismissRequest = { showMessage = false },
            title = { Text("💬 پیام به صاحب‌کار") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("آگهی: ${job.title}")
                    if (job.phone.isBlank()) {
                        Text("⚠️ برای این آگهی شماره تماسی ثبت نشده، امکان ارسال پیام نیست.")
                    } else {
                        OutlinedTextField(value = message, onValueChange = { message = it }, label = { Text("متن پیام") })
                        Text("با زدن «ارسال»، اپ پیام‌رسان پیامک گوشی‌ات باز می‌شود تا پیام را برای صاحب آگهی بفرستی.")
                    }
                }
            },
            confirmButton = {
                if (job.phone.isNotBlank()) {
                    Button(onClick = {
                        if (message.isNotBlank()) {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("smsto:${job.phone}")
                                putExtra("sms_body", message)
                            }
                            context.startActivity(intent)
                            showMessage = false
                            message = ""
                        }
                    }) { Text("ارسال") }
                }
            },
            dismissButton = { Button(onClick = { showMessage = false }) { Text("بستن") } }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("جزئیات آگهی") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("عنوان شغل: ${job.title}")
                    Text("نوع کار: ${job.type}")
                    Text("شهر: ${job.city}")
                    Text("حقوق: ${job.salary}")
                    Text("ساعت کاری: ${job.hours}")
                    Text("توضیحات: ${job.description}")
                    if (job.phone.isNotBlank()) Text("📞 شماره تماس: ${job.phone}")
                }
            },
            confirmButton = {
                Column {
                    Button(onClick = { showMessage = true }) { Text("💬 پیام به صاحب‌کار") }
                    if (job.phone.isNotBlank()) {
                        Button(onClick = { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${job.phone}"))) }) { Text("📞 تماس با صاحب‌کار") }
                    }
                    Button(onClick = onDismiss) { Text("بستن") }
                }
            }
        )
    }
}
@Composable
private fun AddJobDialog(existingJob: Job? = null,
    onDismiss: () -> Unit,
    onSubmit: (Job) -> Unit
) {
    var title by remember { mutableStateOf(existingJob?.title ?: "") }
    var type by remember { mutableStateOf(existingJob?.type ?: "") }
    var city by remember { mutableStateOf(existingJob?.city ?: "") }
    var salary by remember { mutableStateOf(existingJob?.salary ?: "توافقی") }
    var phone by remember { mutableStateOf(existingJob?.phone ?: "") }
    var hours by remember { mutableStateOf(existingJob?.hours ?: "") }
    var description by remember { mutableStateOf(existingJob?.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("ثبت آگهی کار") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان شغل") }, singleLine = true)
                OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("نوع کار") }, singleLine = true)
                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("شهر") }, singleLine = true)
                OutlinedTextField(value = salary, onValueChange = { salary = it }, label = { Text("حقوق") }, singleLine = true)
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("شماره تماس صاحب آگهی") }, singleLine = true)
                OutlinedTextField(value = hours, onValueChange = { hours = it }, label = { Text("ساعت کاری") }, singleLine = true)
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("توضیحات") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && type.isNotBlank() && city.isNotBlank()) {
                        onSubmit(Job(title, type, city, salary.ifBlank { "توافقی" }, phone, hours, description))
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
