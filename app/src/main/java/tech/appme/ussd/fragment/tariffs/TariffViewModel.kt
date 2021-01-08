package tech.appme.ussd.fragment.tariffs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel
import tech.appme.ussd.data.Limit
import tech.appme.ussd.data.Section
import tech.appme.ussd.data.Tariff

class TariffViewModel : BaseViewModel(){

    private val tariffData = MutableLiveData<ArrayList<Tariff>>()
    var tariffs: LiveData<ArrayList<Tariff>> = tariffData

    private val sectionData = MutableLiveData<ArrayList<Section>>()
    var sections : LiveData<ArrayList<Section>> = sectionData


    init{
        tariffData.postValue(getTariff())
        sectionData.postValue(getSections())
    }

    fun getSections() : ArrayList<Section>{
        var sections = arrayListOf<Section>()
        sections.addAll(
            arrayListOf(
                Section(0 , "Units Tariflari" ,"Унитс Тарифлари" , false ),
                Section(1 , "Boshqa Tariflar" ,"Бошқа тарифлар" , false ),
                Section(2 , "3 - Bo'lim " , "3 - Бўлим" , false),
                Section(3 , "4 - Bo'lim " , "4 - Бўлим" , false),
                Section(4 , "5 - Bo'lim " , "5 - Бўлим" , false),
                Section(5 , "6 - Bo'lim " , "6 - Бўлим" , false),

            )
        )
        return sections
    }
    fun getTariff() : ArrayList<Tariff>{
        var tariffs = ArrayList<Tariff>()
        var limits = ArrayList<Limit>()
        limits.add(
            Limit(
                "6500 MB" ,
                "",
                "Oyiga",
                "",
                "",
            )
        )
        limits.add(
            Limit(
                "750 Daqiqa" ,
                "",
                "Oyiga",
                "",
                "",
            )
        )

        limits.add(
            Limit(
                "750 SMS" ,
                "",
                "Oyiga",
                "",
                "",
            )
        )

        tariffs.add(
            Tariff("Street" , "" ,limits , "39 900 so'm" ,"")
        )
        tariffs.add(
            Tariff("Street" , "" ,limits , "39 900 so'm" ,"")
        )
        tariffs.add(
            Tariff("Street" , "" ,limits , "39 900 so'm" ,"")
        )
        tariffs.add(
            Tariff("Street" , "" ,limits , "39 900 so'm" ,"")
        )
        return tariffs
    }
}