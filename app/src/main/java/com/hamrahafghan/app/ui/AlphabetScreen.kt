package com.hamrahafghan.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
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
fun AlphabetScreen() {
    var searchText by remember { mutableStateOf("") }

    val letters = listOf(
        "ا" to "الف — اَبر ☁️",
        "ب" to "ب — بابا 👨",
        "پ" to "پ — پروانه 🦋",
        "ت" to "ت — توپ ⚽",
        "ث" to "ث — ثروت 💰",
        "ج" to "ج — جوجه 🐥",
        "چ" to "چ — چتر ☂️",
        "ح" to "ح — حیاط 🏡",
        "خ" to "خ — خانه 🏠",
        "د" to "د — درخت 🌳",
        "ذ" to "ذ — ذرت 🌽",
        "ر" to "ر — روباه 🦊",
        "ز" to "ز — زنبور 🐝",
        "ژ" to "ژ — ژاله 💧",
        "س" to "س — سیب 🍎",
        "ش" to "ش — شیر 🦁",
        "ص" to "ص — صابون 🧼",
        "ض" to "ض — ضبط 🎵",
        "ط" to "ط — طوطی 🦜",
        "ظ" to "ظ — ظرف 🍽️",
        "ع" to "ع — عینک 👓",
        "غ" to "غ — غاز 🪿",
        "ف" to "ف — فیل 🐘",
        "ق" to "ق — قورباغه 🐸",
        "ک" to "ک — کتاب 📖",
        "گ" to "گ — گلابی 🍐",
        "ل" to "ل — لیمو 🍋",
        "م" to "م — ماه 🌙",
        "ن" to "ن — نان 🍞",
        "و" to "و — ورزش 🏃",
        "ه" to "ه — هوا 🌤️",
        "ی" to "ی — یخ 🧊"
    )

    val filteredLetters = letters.filter {
        it.first.contains(searchText, ignoreCase = true) ||
        it.second.contains(searchText, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "🔤 الفبا و خواندن",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text("حروف فارسی را همراه با کلمه و تصویر یاد بگیر")

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجوی حرف یا کلمه") },
                singleLine = true
            )
        }

        items(filteredLetters) { letter ->
            Card(modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = {
                        Text(
                            letter.first,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    supportingContent = {
                        Text(letter.second, fontSize = 18.sp)
                    }
                )
            }
        }

        if (filteredLetters.isEmpty()) {
            item {
                Text("موردی پیدا نشد.")
            }
        }
    }
}
