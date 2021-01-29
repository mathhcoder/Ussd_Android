package uz.appme.ussd.model.data

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "banner", primaryKeys = ["id"])
data class Banner(
    val id: Long = 0,
    val operatorId: Long? = null,
    val image: String? = null,
    val link: String? = null,
    val priority: Int? = null
) : Serializable