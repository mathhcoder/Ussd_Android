package tech.appme.ussd.io

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import java.security.Provider
import java.io.Serializable



class StockPreference(val context: Context) {

    private val mode = MODE_PRIVATE

    var lang: String
        get() = context.getSharedPreferences("app", mode).getString("lang", "uz") ?: "uz"
        set(value) {
            context.getSharedPreferences("app", mode).edit().putString("lang", value).apply()
        }
    lateinit var defProvider: Provider

    var provider : Provider
        get() =  Gson().fromJson(context.getSharedPreferences("app" , mode).getString("provider" , ""), Provider::class.java) as Provider
        set(value) {
            var json :String =Gson().toJson(provider)
            context.getSharedPreferences("app" , mode).edit().putString("provider" , json).commit()
        }
}