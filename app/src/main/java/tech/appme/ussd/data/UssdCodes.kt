package tech.appme.ussd.data

import java.io.Serializable

data class UssdCodes(
    var id : Int ? = 0,
    val name : String? = null,
    val number : String ? = null
) :Serializable