package uz.appme.ussd.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.appme.ussd.BaseViewModel

class AboutViewModel : BaseViewModel(){

    var discriptionData = MutableLiveData<String>()
    var discription : LiveData<String> = discriptionData

    var telephoneData = MutableLiveData<String>()
    var telephone : LiveData<String> = telephoneData

    var instagramData = MutableLiveData<String>()
    var instagram : LiveData<String> = instagramData

    var facebookData = MutableLiveData<String>()
    var facebook : LiveData<String> = facebookData

    var telegramData = MutableLiveData<String>()
    var telegram : LiveData<String> = telegramData


    init{
        var textDescription = getDiscription()
        discriptionData.postValue(textDescription)

        var telephoneNumber = getNumber()
        telephoneData.postValue(telephoneNumber)

        var instagramProfile = getInstagarm()
        instagramData.postValue(instagramProfile)

        var telegramProfile = getTelegram()
        telephoneData.postValue(telegramProfile)

        var facebookProfile = getFacebook()
        facebookData.postValue(facebookProfile)

    }


    fun getDiscription():String{
        return "«Unitel» kompaniyasi 1996 yilda asos topgan. Kompaniyaning tijorat faoliyati 1997 yilning 25 sentyabr kuni boshlangan. 2006 yilning 12 sentyabr kunidan «Unitel» MChJ «Beeline» savdo belgisi ostida xizmat ko‘rsatib kelmoqda. \n" +
                " \n" +
                "Bugungi kunda «Beeline» O‘zbekistondagi eng yirik aloqa operatori. 2017 yilning natijalariga ko‘ra kompaniyaning abonent bazasi 10 millionga yaqin kishini tashkil etdi. \n" +
                " \n" +
                "10 yil avval, 2008 yilning 2 dekabr kuni «Unitel» kompaniyasi O’zbekistonda birinchi bo’lib 3G tarmog‘i ishga tushirilishi haqida e’lon qildi. 2014 yilning 4 sentyabr kuni «Beeline» O‘zbekistonda ilk bor 4G LTE tarmog‘ini ishga tushirdi.  Uch yil mobaynida kompaniya 4G tarmog‘ini O‘zbekistonning 20dan ziyod shaharlarida kengaytirdi. \n" +
                " \n" +
                "«Unitel» MChJ va «Buzton» QK MChJ kompaniyalari «VEON» Kompaniyalar guruhi tarkibiga kiradi."
    }

    fun getNumber() : String{
        return "+998979253809"
    }

    fun getInstagarm () : String{
        return ".."
    }

    fun getFacebook():String{
        return "."
    }
    fun getTelegram():String{
        return "https://t.me/math_coder"
    }

}