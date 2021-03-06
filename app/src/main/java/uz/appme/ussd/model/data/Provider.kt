package uz.appme.ussd.model.data

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "provider", primaryKeys = ["id"])
data class Provider(
    val id: Long? = 0,
    val icon: String? = null,
    val color: String? = null,
    val name: String? = null,
    val priority: Int? = null,
    val selected: Boolean = false
) : Serializable