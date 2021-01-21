package uz.appme.ussd

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.appme.ussd.data.*
import uz.appme.ussd.io.BaseRepository


class MainViewModel :BaseViewModel() {

    private val providersData = MutableLiveData<List<Operator>>()
    val providers : LiveData<List<Operator>> = providersData

    private val bannersData = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = bannersData

    private val categoriesData = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = categoriesData

    private val packagesData = MutableLiveData<List<Packages>>()
    val packages: LiveData<List<Packages>> = packagesData

    private val tariffsData = MutableLiveData<List<Tariff>>()
    val tariffs: LiveData<List<Tariff>> = tariffsData

    private val servicesData = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> = servicesData

    private val newsData = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = newsData

    private val contactData = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = contactData

    private val disposable = CompositeDisposable()

    var lang = "uz"
    init{
        getDataFromDB()
    }
    private fun getDataFromDB() {
        disposable.add(BaseRepository.roomDatabase.bannerDao().getData()
            .flatMap {
                bannersData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.contactDao().getData()
            }.flatMap {
                it.firstOrNull()?.let { c ->
                    contactData.postValue(c)
                }
                BaseRepository.roomDatabase.packageDao().getData()
            }.flatMap {
               // packagesData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.categoryDao().getData()
            }.flatMap {
                categoriesData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.tariffDao().getData()
            }.flatMap {
                tariffsData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.serviceDao().getData()
            }.flatMap {
               // servicesData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.newsDao().getData()
            }.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                newsData.postValue(it.sortedByDescending { d -> d.date })
                getDataFromNetwork()
            }, {

            })
        )
    }
    private fun getDataFromNetwork() {
        disposable.add(
            BaseRepository.api.getData().subscribeOn(Schedulers.io())
                .doOnSuccess {

                    BaseRepository.roomDatabase.tariffDao().deleteAll()
                  //  BaseRepository.roomDatabase.tariffDao().insertAll(it.tariffs)
                    //tariffsData.postValue(it.tariffs.sortedBy { d -> d.priority })

                    BaseRepository.roomDatabase.serviceDao().deleteAll()
                  //  BaseRepository.roomDatabase.serviceDao().insertAll(it.services)
                   // servicesData.postValue(it.services.sortedBy { d -> d.priority })

                    BaseRepository.roomDatabase.categoryDao().deleteAll()
                  //  BaseRepository.roomDatabase.categoryDao().insertAll(it.categories)
                   // categoriesData.postValue(it.categories.sortedBy { d -> d.priority })

                    BaseRepository.roomDatabase.packageDao().deleteAll()
                 //   BaseRepository.roomDatabase.packageDao().insertAll(it.packages)
                  //  packagesData.postValue(it.packages.sortedBy { d -> d.priority })

                    BaseRepository.roomDatabase.contactDao().deleteAll()
//                    it.contacts?.let { c ->
//                        BaseRepository.roomDatabase.contactDao().insert(c)
//                        contactData.postValue(c)
//                    }

                }
                .flatMap {
                    BaseRepository.api.getUpdate()
                }
                .doOnSuccess {
                    BaseRepository.roomDatabase.bannerDao().deleteAll()
                 //   BaseRepository.roomDatabase.bannerDao().insertAll(it.banners)
                  //  bannersData.postValue(it.banners.sortedBy { d -> d.priority })

                    BaseRepository.roomDatabase.newsDao().deleteAll()
                   // BaseRepository.roomDatabase.newsDao().insertAll(it.news)
                  //  newsData.postValue(it.news.sortedByDescending { d -> d.date })
                }
                .doOnError {

                }
                .observeOn(Schedulers.io())
                .subscribe({

                }, {

                })
        )
    }





    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }

    fun LogOut(string : String){
        Log.e("DATA_" , string)
    }
}