package uz.appme.ussd.data

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "operator", primaryKeys = ["id"])
data class Operator(
    val id: Long? = 0,
    val image: String? = null,
    val color: String? = null,
    val name: String? = null,
    val priority: Int? = null,
    val selected: Boolean = false
) : Serializable