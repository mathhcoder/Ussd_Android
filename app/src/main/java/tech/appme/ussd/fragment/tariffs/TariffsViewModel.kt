package tech.appme.ussd.fragment.tariffs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel
import tech.appme.ussd.data.Limit
import tech.appme.ussd.data.Category
import tech.appme.ussd.data.Tariff

class TariffsViewModel : BaseViewModel(){

    private val tariffData = MutableLiveData<ArrayList<Tariff>>()
    var tariffs: LiveData<ArrayList<Tariff>> = tariffData

    private val sectionData = MutableLiveData<ArrayList<Category>>()
    var sections : LiveData<ArrayList<Category>> = sectionData

}