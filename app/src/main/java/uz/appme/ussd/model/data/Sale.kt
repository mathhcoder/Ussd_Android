package uz.appme.ussd.model.data

import androidx.room.Entity

@Entity(tableName = "sales", primaryKeys = ["id"])
data class Sale(
    val id: Long? = null,
    val operatorId: Long? = null,
    val start : Long? = null,
    val end : Long? = null,
    val nameUz: String? = null,
    val nameRu: String? = null,
    val bodyUz: String? = null,
    val bodyRu: String? = null,
    val link: String? = null,
    val image: String? = null,
    val ussd: String? = null,
    val priority: Int? = null
)