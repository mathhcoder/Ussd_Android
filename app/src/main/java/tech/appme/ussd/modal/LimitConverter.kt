package tech.appme.ussd.modal

import androidx.room.TypeConverter
import com.google.gson.Gson
import tech.appme.ussd.data.Limit

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