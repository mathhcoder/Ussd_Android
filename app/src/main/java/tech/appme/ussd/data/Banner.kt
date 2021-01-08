package uz.appme.ussd

import java.io.Serializable

data class Banner(
    val id: Long? = null,
    val image: String? = null,
    val url: String? = null
): Serializable