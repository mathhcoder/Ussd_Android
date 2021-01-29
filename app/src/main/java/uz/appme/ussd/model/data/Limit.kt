package uz.appme.ussd.model.data

import java.io.Serializable

data class Limit(
    val id : Long = 0,
    var nameUz : String? = null,
    var nameRu : String? = null,
    var valueUz : String? = null,
    var valueRu : String? = null,
    var image : String? = null,
    val priority: Int? = null,
    val isMain: Boolean? = null
) :Serializable