package uz.appme.ussd.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.appme.ussd.BaseViewModel
import uz.appme.ussd.data.Banner
import uz.appme.ussd.data.Provider

class HomeViewModel : BaseViewModel() {

    private var providersData = MutableLiveData<ArrayList<Provider>>()
    var providers: LiveData<ArrayList<Provider>> = providersData

    var providerData = MutableLiveData<Provider>()

    var provider: LiveData<Provider> = providerData
        set(value){
            value.value?.selected = true
            field = value
            providers.value?.forEach {
                if(it.id == value.value?.id){
                    it.selected = true
                }
                else
                    it.selected = false
            }
        }

    private val bannersData = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = bannersData

    init {
        val beeline = Provider(0 , "", "#edeb6b", "beeline", 0)
        val ums = Provider(1, "", "#ff0000", "mobiuz", 0)
        val uzmobile = Provider(2, "", "#3c9ade", "uzmobile", 0)
        val ucell = Provider(3, "" , "#9034c9" , "ucell" , 0,true)



        providersData.postValue(arrayListOf(beeline, ums, uzmobile , ucell))
        providerData.postValue(ucell)

        bannersData.postValue(getBanners())
    }


    private fun getBanners(): ArrayList<Banner> {
        val banners = ArrayList<Banner>()

        banners.add(
            Banner(
                0, "https://azon.uz/uploads/images/600x400/201226142755.png",
                "https://kun.uz/news/2020/12/25/1-yanvardan-toshkentda-yolkiraga-naqd-pulda-haq-tolab-bolmaydi-nega"
            )
        )

        banners.add(
            Banner(
                1, "https://azon.uz/uploads/images/600x400/201226142755.png",
                "https://azon.uz/content/views/tushida-otasini-jannatda-kurdi111"
            )
        )

        banners.add(
            Banner(
                2, "https://azon.uz/uploads/images/600x400/201226162346.png",
                "https://azon.uz/content/views/toshkent-metropolitenining-sergeli-yunal"
            )
        )
        banners.add(
            Banner(
                3, "https://azon.uz/uploads/images/600x400/201226142755.png",
                "https://azon.uz/content/views/tushida-otasini-jannatda-kurdi111"
            )
        )


        return banners

    }

}

