package uz.appme.ussd.model.data

import androidx.room.Entity

@Entity(tableName = "category", primaryKeys = ["id"])
data class Category(
    val id: Long = 0,
    val operatorId: Long? = null,
    val nameUz: String? = null,
    val nameRu: String? = null,
    val type: Int? = null,
    val priority: Int? = null,
    var selected: Boolean? = null
)