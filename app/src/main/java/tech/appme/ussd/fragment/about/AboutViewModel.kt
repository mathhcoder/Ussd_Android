package tech.appme.ussd.fragment.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel

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

}