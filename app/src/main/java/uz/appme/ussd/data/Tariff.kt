package uz.appme.ussd.data

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "tariff", primaryKeys = ["id"])
data class Tariff(
    val id  : Long,
    val categoryId : Long ,
    val providerId: Long = 1,
    val nameUz : String? = null,
    val nameRu : String? = null,
    val onPriceUz: String? = null,
    val onPriceRu: String? = null,
    val subscriptionPriceUz : String,
    val subscriptionPriceRu : String,
    val descriptionUz: String? = null,
    val descriptionRu: String? = null,
    val image: String? = null,
    val priority: Int? = null,
    val ussd : String? = null,
    val limits : List<Limit> = emptyList(),
    val overLimits : List<Limit> = emptyList()
) : Serializable