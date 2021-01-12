package tech.appme.ussd.data

import androidx.room.Entity

@Entity(tableName = "category", primaryKeys = ["id"])
data class Category(
    val id : Long = 0,
    val provideId : Int = 0 ,
    val nameUz: String? = null,
    val nameRu: String? = null,
    val type: Int? = null,
    val priority: Int? = null,
    var selected: Boolean? = null
)