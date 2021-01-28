package uz.appme.ussd.data

data class DataResponse(
    val operators: List<Operator> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val news: List<News> = emptyList(),
    val tariffs: List<Tariff> = emptyList(),
    val categories: List<Category> = emptyList(),
    val packages: List<Pack> = emptyList(),
    val services: List<Service> = emptyList(),
    val contacts: Contact? = null
)