package uz.appme.ussd.model.data

import androidx.room.Entity

@Entity(tableName = "news", primaryKeys = ["id"])
data class News(
    val id: Long? = null,
    val date: Long? = null,
    val titleUz: String? = null,
    val titleRu: String? = null,
    val bodyUz: String? = null,
    val bodyRu: String? = null,
    val link: String? = null,
    val image: String? = null

)