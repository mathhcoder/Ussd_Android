package tech.appme.ussd.fragment.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel
import tech.appme.ussd.data.Banner
import tech.appme.ussd.data.Provider


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


    }




}

