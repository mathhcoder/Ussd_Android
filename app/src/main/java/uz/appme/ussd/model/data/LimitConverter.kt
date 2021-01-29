package uz.appme.ussd.model.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class LimitConverter {

    @TypeConverter
    fun fromLimit(limits: List<Limit>): String? {
        return limits.joinToString(";") { Gson().toJson(it).toString() }
    }

    @TypeConverter
    fun fromString(string: String): List<Limit> {
        return string.split(";").map { Gson().fromJson(it,Limit::class.java) }
    }
}