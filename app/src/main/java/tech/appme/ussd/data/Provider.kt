package tech.appme.ussd.data

import java.io.Serializable

data class Provider (
    val id : Long? = 0,
    val icon : String,
    val color : String,
    val name  :String,
    val priopirity  : Int,
    var selected : Boolean = false
) : Serializable