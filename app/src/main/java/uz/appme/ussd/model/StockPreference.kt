package uz.appme.ussd.model

import android.content.Context
import android.content.Context.MODE_PRIVATE

class StockPreference(val context: Context) {

    private val mode = MODE_PRIVATE

    var lang: String
        get() = context.getSharedPreferences("app", mode).getString("lang", "uz") ?: "uz"
        set(value) {
            context.getSharedPreferences("app", mode).edit().putString("lang", value).apply()
        }

    var operatorId: Long = 1
        get() = context.getSharedPreferences("app", mode).getLong("operator", 1L)
        set(value) {
            context.getSharedPreferences("app", mode).edit().putLong("operator", value).apply()
            field = value
        }

    var token: String
        get() = context.getSharedPreferences("app", mode).getString("token", "") ?: ""
        set(value) {
            context.getSharedPreferences("app", mode).edit().putString("token", value).apply()
        }

    var isDark: Int
        get() = context.getSharedPreferences("app", mode).getInt("isDark", -1)
        set(value) {
            context.getSharedPreferences("app", mode).edit().putInt("isDark", value).apply()
        }

    var lastUpdate: Long
       // get() = context.getSharedPreferences("app", mode).getLong("lastUpdate", 0L)
        get() = 0L
        set(value) {
            context.getSharedPreferences("app", mode).edit().putLong("lastUpdate", value).apply()
        }
}