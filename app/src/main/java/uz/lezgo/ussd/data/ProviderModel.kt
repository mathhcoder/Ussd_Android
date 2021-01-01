package uz.lezgo.ussd.data

data class ProviderModel (
    val id : Long? = 0,
    val icon : String,
    val color : String,
    val name  :String,
    val priopirity  : Int,
    var selected : Boolean = false
)