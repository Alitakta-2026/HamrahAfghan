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
import androidx.compose.material3.OutlinedTextField
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
fun ScienceScreen() {
    var searchText by remember { mutableStateOf("") }

    val topics = listOf(
        "🌱 طبیعت و گیاهان" to "گیاهان چگونه رشد می‌کنند؟",
        "🐾 حیوانات" to "آشنایی با حیوانات و محل زندگی آن‌ها",
        "🧍 بدن انسان" to "آشنایی ساده با اعضای بدن",
        "🌍 زمین و جهان" to "زمین، آب، هوا و دنیای اطراف ما",
        "☀️ خورشید و فضا" to "آشنایی با خورشید، ماه و ستاره‌ها",
        "💡 دانستنی‌های جالب" to "دانستنی‌های علمی کوتاه و سرگرم‌کننده"
    )

    val filteredTopics = topics.filter {
        it.first.contains(searchText, ignoreCase = true) ||
        it.second.contains(searchText, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                "🔬 علوم و دانستنی‌ها",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text("دنیای علم را با زبان ساده کشف کن")

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجو در علوم") },
                singleLine = true
            )
        }

        items(filteredTopics) { topic ->
            Card(modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = {
                        Text(
                            topic.first,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    supportingContent = {
                        Text(topic.second)
                    }
                )
            }
        }

        if (filteredTopics.isEmpty()) {
            item {
                Text("موضوعی برای جستجوی شما پیدا نشد.")
            }
        }
    }
}
