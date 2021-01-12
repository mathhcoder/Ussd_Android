package tech.appme.ussd.fragment.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel
import tech.appme.ussd.data.Category
import tech.appme.ussd.data.Service

class ServiceViewModel  : BaseViewModel(){



    private val serviceData = MutableLiveData<ArrayList<Service>>()
    var services : LiveData<ArrayList<Service>> = serviceData

    private val sectionData = MutableLiveData<ArrayList<Category>>()
    var sections : LiveData<ArrayList<Category>> = sectionData




}