package uz.appme.ussd.data

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "service", primaryKeys = ["id"])
data class Service(
    val id: Long = 0,
    val operatorId : Long? = null,
    val categoryId: Long? = null,
    val nameUz: String? = null,
    val nameRu: String? = null,
    val descriptionUz: String? = null,
    val descriptionRu: String? = null,
    val subscriptionPriceUz: String? = null,
    val subscriptionPriceRu: String? = null,
    val firstTimePriceUz: String? = null,
    val firstTimePriceRu: String? = null,
    val termsUz: String? = null,
    val termsRu: String? = null,
    val ussd: String? = null,
    val priority: String? = null
) : Serializable