package uz.appme.ussd.model.data

import androidx.room.Entity

@Entity(tableName = "codes", primaryKeys = ["id"])
data class Code(
    val id: Long? = null,
    val providerId: Long? = null,
    val nameUz: String? = null,
    val nameRu: String? = null,
    val icon: String? = null,
    val ussd: String? = null,
    val priority: Int? = null
)