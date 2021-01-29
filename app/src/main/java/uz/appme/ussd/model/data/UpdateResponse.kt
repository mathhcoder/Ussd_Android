package uz.appme.ussd.model.data

data class UpdateResponse(
    val news: List<News> = emptyList(),
    val banners: List<Banner> = emptyList()
)