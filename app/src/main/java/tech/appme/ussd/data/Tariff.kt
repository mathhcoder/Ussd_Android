package tech.appme.ussd.data

data class Tariff(
    var nameUz : String ,
    var nameRu : String ,
    var limits : ArrayList<Limit> ,
    var priceUz : String,
    var priceRu : String
)