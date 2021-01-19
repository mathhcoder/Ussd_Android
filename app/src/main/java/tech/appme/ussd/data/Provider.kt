package tech.appme.ussd.data

import android.renderscript.RenderScript
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "provider", primaryKeys = ["id"])

data class
Provider (
    val id : Long? = 0,
    val image : String? = null,
    val color : String? = null,
    val nameUz  :String? = null,
    val nameRu : String? =null ,
    val priority: String? = null,
    var selected : Boolean = false
) : Serializable