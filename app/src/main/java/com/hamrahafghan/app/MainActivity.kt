package com.hamrahafghan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.hamrahafghan.app.auth.AuthManager
import com.hamrahafghan.app.ui.App
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            try {
                AuthManager.signInAnonymously()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setContent {
            App()
        }
    }
}
