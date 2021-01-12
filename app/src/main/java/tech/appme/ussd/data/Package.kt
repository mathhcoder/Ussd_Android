package tech.appme.ussd.data

import androidx.room.Entity

@Entity(tableName = "package", primaryKeys = ["id"])
data class Package(
    val id : Long? = 1,
    val providerId : Int = 0 ,
    val amountUz : String? = null,
    val amountRu : String? = null,
    val priceUz : String? = null,
    val priceRu : String? = null,
    val order: String? = null,
    val descriptionUz: String? = null,
    val descriptionRu: String? = null,
    val categoryId: Long? = null,
    val ussd: String? = null,
    val priority: Int? = null,
    var type : Int? = 0
)