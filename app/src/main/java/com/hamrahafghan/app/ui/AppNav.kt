package com.hamrahafghan.app.ui

import com.hamrahafghan.app.ui.theme.HamrahTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun App() {
    var page by remember { mutableStateOf("home") }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        HamrahTheme {
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
                    "children" -> ChildrenEducationScreen(onAlphabetClick = { page = "alphabet" }, onMathClick = { page = "math" }, onScienceClick = { page = "science" }, onGamesClick = { page = "games" })
                    "alphabet" -> AlphabetScreen()
                        "math" -> MathScreen()
                        "science" -> ScienceScreen()
                        "games" -> EducationalGamesScreen()
                        "migration" -> MigrationScreen()
                        "jobs" -> Jobs()
                        else -> More { page = "migration" }
                    }
                }
            }
        }
    }
}
