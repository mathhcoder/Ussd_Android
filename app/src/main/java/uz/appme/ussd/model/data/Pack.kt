package uz.appme.ussd.model.data

import androidx.room.Entity


@Entity(tableName = "packages", primaryKeys = ["id"])
data class Pack(
    val id: Long? = 1,
    val categoryId: Long? = null,
    val amountUz: String? = null,
    val amountRu: String? = null,
    val priceUz: String? = null,
    val priceRu: String? = null,
    val order: String? = null,
    val descriptionUz: String? = null,
    val descriptionRu: String? = null,
    val ussd: String? = null,
    val priority: Int? = null,
    var type: Int? = 0
)