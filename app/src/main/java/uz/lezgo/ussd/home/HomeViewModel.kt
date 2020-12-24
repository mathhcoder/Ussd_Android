package uz.lezgo.ussd.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.lezgo.ussd.Banner
import uz.lezgo.ussd.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val bannerData = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = bannerData

    init {
        val banner = Banner(id = 1, image = "image", "url")
        bannerData.postValue(arrayListOf(banner))
    }


}