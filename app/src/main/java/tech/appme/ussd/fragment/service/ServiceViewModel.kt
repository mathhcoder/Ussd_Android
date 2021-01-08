package tech.appme.ussd.fragment.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel
import tech.appme.ussd.data.Section
import tech.appme.ussd.data.Service

class ServiceViewModel  : BaseViewModel(){



    private val serviceData = MutableLiveData<ArrayList<Service>>()
    var services : LiveData<ArrayList<Service>> = serviceData

    private val sectionData = MutableLiveData<ArrayList<Section>>()
    var sections : LiveData<ArrayList<Section>> = sectionData


    init{
        serviceData.postValue(getServices())
        sectionData.postValue(getSections())
    }

    fun getServices() : ArrayList<Service>{
        var services = ArrayList<Service>()
        services.addAll(
            arrayListOf(
                Service(
                "Foydali almashinuv",
                "",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Scelerisque id adipiscing in tempus. Nisl, inter...",
                "",
                "8400 so'm/oyiga",
                ""
                ) ,
                Service(
                    "Limitsiz ovoz",
                    "",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Scelerisque id adipiscing in tempus. Nisl, inter...",
                    "",
                    "8400 so'm/oyiga",
                    ""
                ) ,
                Service(
                    "Qo’llab yubor",
                    "",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Scelerisque id adipiscing in tempus. Nisl, inter...",
                    "",
                    "8400 so'm/oyiga",
                    ""
                )
            )
        )

        return services
    }

    fun getSections():ArrayList<Section>{
        var sections = ArrayList<Section>()
        sections.addAll(
            arrayListOf(
                Section(
                    0,
                    "Units paketlar",
                    "",
                    false
                ),
                Section(
                    1,
                    "Boshqa paketlar",
                    "",
                    false,
                ),
                Section(
                    2,
                    "Bo'lim nomi",
                    "",
                            false
                ),
                Section(
                    3,
                    "UBo'lim nomi",
                    "",
                    false
                ), Section(
                    4,
                    "Bo'lim nomi",
                    "",
                    false
                )
            )
        )
        return sections
    }


}