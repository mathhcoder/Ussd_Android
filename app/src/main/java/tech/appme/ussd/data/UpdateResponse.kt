package tech.appme.ussd.data
import tech.appme.ussd.data.Banner

data class UpdateResponse(
    val news: List<News> = emptyList(),
    val banners: List<Banner> = emptyList()
)