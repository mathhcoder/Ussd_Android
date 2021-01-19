package uz.appme.ussd.data

data class Section(
    val id : Long,
    val nameUz: String,
    val nameRu: String,
    var selected: Boolean
)