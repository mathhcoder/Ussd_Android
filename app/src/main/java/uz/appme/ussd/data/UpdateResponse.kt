package uz.appme.ussd.data

data class UpdateResponse(
    val news: List<News> = emptyList(),
    val banners: List<Banner> = emptyList()
)