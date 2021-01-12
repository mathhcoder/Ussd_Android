package tech.appme.ussd.fragment.Packages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tech.appme.ussd.BaseViewModel
import tech.appme.ussd.data.Package
import tech.appme.ussd.data.Category


class PackagesViewModel : BaseViewModel(){
    private val packageData = MutableLiveData<ArrayList<Package>>()
    var packages: LiveData<ArrayList<Package>> = packageData

    private val sectionData = MutableLiveData<ArrayList<Category>>()
    var sections : LiveData<ArrayList<Category>> = sectionData


    init{

    }



}