package uz.appme.ussd

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import uz.appme.ussd.model.data.*
import uz.appme.ussd.model.BaseRepository
import java.io.Serializable


class MainViewModel : ViewModel() {

    private val isDarkData = MutableLiveData<Boolean>()
    val isDark: LiveData<Boolean> = isDarkData

    private val langData = MutableLiveData<Lang>()
    val lang: LiveData<Lang> = langData


    private val operatorsData = MutableLiveData<List<Provider>>()
    val operators: LiveData<List<Provider>> = operatorsData

    private val bannersData = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = bannersData

    private val categoriesData = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = categoriesData

    private val packagesData = MutableLiveData<List<Pack>>()
    val packs: LiveData<List<Pack>> = packagesData

    private val tariffsData = MutableLiveData<List<Tariff>>()
    val tariffs: LiveData<List<Tariff>> = tariffsData

    private val servicesData = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> = servicesData

    private val newsData = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = newsData

    private val codesData = MutableLiveData<List<Code>>()
    val codes: LiveData<List<Code>> = codesData

    private val salesData = MutableLiveData<List<Sale>>()
    val sales: LiveData<List<Sale>> = salesData

    private val contactData = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = contactData


    private var databaseDisposable: Disposable? = null
    private var networkDisposable: Disposable? = null
    private var authDisposable: Disposable? = null


    init {
        val l = if(BaseRepository.preference.lang == "uz") Lang.UZ else Lang.RU
        langData.postValue(l)
        start()
    }

    fun setTheme(isDark: Boolean) {
        BaseRepository.preference.isDark = if (isDark) 1 else 0
        isDarkData.postValue(isDark)
    }

    fun changeTheme() {
        val isDark = BaseRepository.preference.isDark == 1
        BaseRepository.preference.isDark = if (isDark) 0 else 1
        isDarkData.postValue(isDark)
    }

    fun isDark(): Int {
        return BaseRepository.preference.isDark
    }

    fun changeLang(lang : Lang , context: Context){
        val l : String = if(lang == Lang.UZ) "uz" else "ru"
        BaseRepository.setLang(l , context)
        langData.postValue(lang)
    }

    fun selectOperator(provider: Provider) {
        BaseRepository.preference.operatorId = provider.id ?: 1
        operatorsData.value?.let {
            val newData = it.map { a -> a.copy(selected = a.id == provider.id) }
            operatorsData.postValue(newData)
        }
    }

    private fun start() {
        if (BaseRepository.preference.token.isEmpty()) {
            auth()
        } else {
            getDataFromNetwork()
            // updateVersion()
        }
    }

    private fun getDataFromDB() {
        databaseDisposable?.dispose()
        databaseDisposable = BaseRepository.roomDatabase.bannerDao().getData()
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
    }

    private fun getDataFromNetwork() {
        networkDisposable?.dispose()
        networkDisposable = BaseRepository.mainApi.getData().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnError {

            }
            .subscribe({

                val selected = BaseRepository.preference.operatorId
                val operators = it.providers.map { o -> o.copy(selected = o.id == selected) }
                BaseRepository.roomDatabase.operatorDao().deleteAll()
                BaseRepository.roomDatabase.operatorDao().insertAll(operators)
                operatorsData.postValue(operators.sortedBy { d -> d.priority })
                Log.e("operators_" , operators.toString())

                val banners = it.banners
                BaseRepository.roomDatabase.bannerDao().deleteAll()
                BaseRepository.roomDatabase.bannerDao().insertAll(banners)
                bannersData.postValue(banners.sortedBy { d -> d.priority })
                Log.e("bannesrs_" , banners.toString())

                val tariffs = it.tariffs
                BaseRepository.roomDatabase.tariffDao().deleteAll()
                BaseRepository.roomDatabase.tariffDao().insertAll(tariffs)
                tariffsData.postValue(tariffs.sortedBy { d -> d.priority })
                Log.e("tariffs" , tariffs.toString())

                val categories = it.categories
                BaseRepository.roomDatabase.categoryDao().deleteAll()
                BaseRepository.roomDatabase.categoryDao().insertAll(categories)
                categoriesData.postValue(categories.sortedBy { d -> d.priority })
                Log.e("categoiries_" , categories.toString())

                val services = it.services
                BaseRepository.roomDatabase.serviceDao().deleteAll()
                BaseRepository.roomDatabase.serviceDao().insertAll(services)
                servicesData.postValue(services.sortedBy { d -> d.priority })
                Log.e("services_" , services.toString())

                val packs = it.packages
                BaseRepository.roomDatabase.packageDao().deleteAll()
                BaseRepository.roomDatabase.packageDao().insertAll(packs)
                packagesData.postValue(packs.sortedBy { d -> d.priority })
                Log.e("packs_" , packs.toString())

                val codes = it.codes
                BaseRepository.roomDatabase.codesDao().deleteAll()
                BaseRepository.roomDatabase.codesDao().insertAll(codes)
                codesData.postValue(codes.sortedByDescending { d -> d.id })
                Log.e("codes_" , codes.toString())

                val news = it.news
                BaseRepository.roomDatabase.codesDao().deleteAll()
                BaseRepository.roomDatabase.codesDao().insertAll(codes)
                newsData.postValue(news.sortedByDescending { d -> d.date })
                Log.e("news_" , news.toString())

                val sales = it.sales
                BaseRepository.roomDatabase.salesDao().deleteAll()
                BaseRepository.roomDatabase.salesDao().insertAll(sales)
                salesData.postValue(sales.sortedByDescending { d -> d.end })
                Log.e("sales_" , sales.toString())

                val contact = it.contacts
                BaseRepository.roomDatabase.contactDao().deleteAll()
                contact?.let { c ->
                    BaseRepository.roomDatabase.contactDao().insert(c)
                    contactData.postValue(c)
                }


            }, {
                Timber.e(it)
            })
    }

    private fun auth() {
        val device = Device(
            os = "1",
            appVersion = BuildConfig.VERSION_NAME,
            osVersion = Build.VERSION.RELEASE,
            model = Build.MODEL,
            dealer = BuildConfig.DEALER
        )
        authDisposable?.dispose()
        authDisposable = BaseRepository.authApi.auth(device)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                if (it.code() == 200) {
                    BaseRepository.preference.token = it.body()?.token ?: ""
                    getDataFromNetwork()
                }
            }, {

            })
    }

    private fun updateVersion() {

        val device = Device(
            os = "1",
            appVersion = BuildConfig.VERSION_NAME,
            osVersion = Build.VERSION.RELEASE,
            model = Build.MODEL,
            dealer = BuildConfig.DEALER
        )

        authDisposable?.dispose()
        authDisposable = BaseRepository.mainApi.updateVersion(device)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                Timber.e(it.code().toString())
                when (it.code()) {
                    200 -> {
                        getDataFromNetwork()
                    }
                    401 -> {
                        BaseRepository.preference.token = ""
                        start()
                    }
                }
            }, {

            })
    }

    override fun onCleared() {
        super.onCleared()
        databaseDisposable?.dispose()
        networkDisposable?.dispose()
        authDisposable?.dispose()
    }
}