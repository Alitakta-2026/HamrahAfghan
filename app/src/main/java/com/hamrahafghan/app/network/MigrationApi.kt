package com.hamrahafghan.app.network

import org.json.JSONObject
import java.net.URL

object MigrationApi {
    private const val DATA_URL = "https://raw.githubusercontent.com/Alitakta-2026/HamrahAfghan/main/data/migration.json"
    fun load(): JSONObject = JSONObject(URL(DATA_URL).readText())
}
