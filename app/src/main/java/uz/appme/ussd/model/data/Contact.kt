package uz.appme.ussd.model.data

import androidx.room.Entity
import java.io.Serializable


@Entity(tableName = "contact", primaryKeys = ["id"])
data class Contact(
    val id: Long = 0,
    val phone: String? = null,
    val instagram: String? = null,
    val telegram: String? = null,
    val facebook: String? = null,
    val aboutUz: String? = null,
    val aboutRu: String? = null,
    val speed: String? = null,
    val balanceUssd: String? = null,
    val residueUssd: String? = null,
    val cabinet: String? = null,
    val googlePlay : String? = null
):Serializable
