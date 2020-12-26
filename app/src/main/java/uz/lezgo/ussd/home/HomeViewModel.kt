package uz.lezgo.ussd.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.lezgo.ussd.Banner
import uz.lezgo.ussd.BaseViewModel
import uz.lezgo.ussd.data.ProviderModel

class HomeViewModel : BaseViewModel() {


    var provider: LiveData<ProviderModel>
    var banners : LiveData<ArrayList<Banner>>

    init{
        var provider1 = ProviderModel(1 , ""  , "#edeb6b" , "belline" , 0)
        var datas = getBanners()

        provider = MutableLiveData<ProviderModel>(provider1)
        banners = MutableLiveData<ArrayList<Banner>>(datas)
    }

    var rcolor  : String = ""


    fun getBanners() : ArrayList<Banner>{
        var banners  = ArrayList<Banner>()

        banners.add(Banner(0 , "https://storage.kun.uz/source/6/SKm9q2dwDOJW2oVpaTNkUwiKrZNPcsMT.jpg" ,
            "https://kun.uz/news/2020/12/25/1-yanvardan-toshkentda-yolkiraga-naqd-pulda-haq-tolab-bolmaydi-nega"))

        banners.add(
            Banner(1 , "https://azon.uz/uploads/images/600x400/201226142755.png" ,
            "https://azon.uz/content/views/tushida-otasini-jannatda-kurdi111"))

        banners.add(Banner(2 , "https://azon.uz/uploads/images/600x400/201226162346.png",
            "https://azon.uz/content/views/toshkent-metropolitenining-sergeli-yunal"))

        return banners

    }

}

