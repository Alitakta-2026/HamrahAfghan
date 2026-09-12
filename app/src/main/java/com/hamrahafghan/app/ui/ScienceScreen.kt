package com.hamrahafghan.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class ScienceTopic(
    val title: String,
    val description: String,
    val lessons: List<String>
)

@Composable
fun ScienceScreen() {
    var searchText by remember { mutableStateOf("") }
    var selectedTopic by remember { mutableStateOf<ScienceTopic?>(null) }

    val topics = listOf(
        ScienceTopic(
            "🌱 طبیعت و گیاهان",
            "گیاهان چگونه رشد می‌کنند؟",
            listOf(
                "🌱 گیاهان برای رشد به آب، نور خورشید و خاک مناسب نیاز دارند.",
                "🌿 ریشه آب و مواد موردنیاز گیاه را از خاک جذب می‌کند.",
                "🌳 برگ‌ها با کمک نور خورشید برای گیاه غذا می‌سازند."
            )
        ),
        ScienceTopic(
            "🐾 حیوانات",
            "آشنایی با حیوانات و محل زندگی آن‌ها",
            listOf(
                "🐘 فیل یکی از بزرگ‌ترین حیوانات خشکی است.",
                "🐟 ماهی‌ها در آب زندگی می‌کنند و با آبشش نفس می‌کشند.",
                "🦁 شیر یک حیوان گوشت‌خوار است و بیشتر در گروه زندگی می‌کند."
            )
        ),
        ScienceTopic(
            "🧍 بدن انسان",
            "آشنایی ساده با اعضای بدن",
            listOf(
                "❤️ قلب خون را در سراسر بدن به گردش درمی‌آورد.",
                "🫁 ریه‌ها به ما کمک می‌کنند نفس بکشیم.",
                "🧠 مغز به فکر کردن، یادگیری و کنترل بدن کمک می‌کند."
            )
        ),
        ScienceTopic(
            "🌍 زمین و جهان",
            "زمین، آب، هوا و دنیای اطراف ما",
            listOf(
                "🌍 زمین سیاره‌ای است که ما روی آن زندگی می‌کنیم.",
                "💧 بیشتر سطح زمین را آب پوشانده است.",
                "🌬️ هوا اطراف ماست و انسان‌ها و حیوانات برای زندگی به آن نیاز دارند."
            )
        ),
        ScienceTopic(
            "☀️ خورشید و فضا",
            "آشنایی با خورشید، ماه و ستاره‌ها",
            listOf(
                "☀️ خورشید یک ستاره بسیار بزرگ و منبع مهم نور و گرما برای زمین است.",
                "🌙 ماه به دور زمین می‌چرخد و نور خورشید را بازتاب می‌دهد.",
                "⭐ ستاره‌ها اجرام بسیار بزرگی هستند که از خود نور و گرما تولید می‌کنند."
            )
        ),
        ScienceTopic(
            "💡 دانستنی‌های جالب",
            "دانستنی‌های علمی کوتاه و سرگرم‌کننده",
            listOf(
                "🦋 پروانه‌ها با پاهای خود می‌توانند مزه بعضی مواد را تشخیص دهند.",
                "🌈 رنگین‌کمان زمانی ایجاد می‌شود که نور خورشید با قطره‌های آب برخورد کند.",
                "🐝 زنبورها برای پیدا کردن گل‌ها از بو، رنگ و نشانه‌های محیط استفاده می‌کنند."
            )
        )
    )

    val filteredTopics = topics.filter {
        it.title.contains(searchText, ignoreCase = true) ||
            it.description.contains(searchText, ignoreCase = true) ||
            it.lessons.any { lesson ->
                lesson.contains(searchText, ignoreCase = true)
            }
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

            Text(
                "دنیای علم را با زبان ساده کشف کن",
                modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("🔎 جستجو در علوم") },
                singleLine = true
            )
        }

        items(filteredTopics) { topic ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedTopic = topic
                    }
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            topic.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    supportingContent = {
                        Text(topic.description)
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

    selectedTopic?.let { topic ->
        AlertDialog(
            onDismissRequest = {
                selectedTopic = null
            },
            title = {
                Text(
                    topic.title,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    topic.lessons.forEach { lesson ->
                        Text(
                            lesson,
                            fontSize = 16.sp
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedTopic = null
                    }
                ) {
                    Text("بستن")
                }
            }
        )
    }
}
