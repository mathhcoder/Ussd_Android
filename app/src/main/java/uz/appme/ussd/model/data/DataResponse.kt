package uz.appme.ussd.model.data

data class DataResponse(
    val providers: List<Provider> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val news: List<News> = emptyList(),
    val tariffs: List<Tariff> = emptyList(),
    val categories: List<Category> = emptyList(),
    val packages: List<Pack> = emptyList(),
    val services: List<Service> = emptyList(),
    val codes: List<Code> = emptyList(),
    val sales: List<Sale> = emptyList(),
    val contacts: Contact? = null,
)