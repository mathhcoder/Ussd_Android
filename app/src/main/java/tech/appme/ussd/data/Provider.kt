package tech.appme.ussd.data

import java.io.Serializable

data class Provider (
    val id : Long? = 0,
    val providerId : Int = 0,
    val image : String? = null,
    val color : String? = null,
    val nameUz  :String? = null,
    val nameRu : String? =null ,
    val priopirity  : Int = 0,
    var selected : Boolean = false
) : Serializable