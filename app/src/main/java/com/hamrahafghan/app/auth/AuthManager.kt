package com.hamrahafghan.app.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

object AuthManager {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    suspend fun signInAnonymously(): String {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            return currentUser.uid
        }

        val result = auth.signInAnonymously().await()

        return result.user?.uid
            ?: throw IllegalStateException("Firebase user UID is null")
    }

    fun currentUserId(): String? {
        return auth.currentUser?.uid
    }
}
