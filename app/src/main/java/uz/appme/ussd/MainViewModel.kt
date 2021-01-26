package uz.appme.ussd

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uz.appme.ussd.data.*
import uz.appme.ussd.io.BaseRepository


class MainViewModel : BaseViewModel() {

    private val isDarkData = MutableLiveData<Boolean>()
    val isDark: LiveData<Boolean> = isDarkData

    private val operatorData = MutableLiveData<Operator>()
    val operator: LiveData<Operator> = operatorData

    private val operatorsData = MutableLiveData<List<Operator>>()
    val operators: LiveData<List<Operator>> = operatorsData

    private val bannersData = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = bannersData

    private val categoriesData = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = categoriesData

    private val packagesData = MutableLiveData<List<Pack>>()
    val pack: LiveData<List<Pack>> = packagesData

    private val tariffsData = MutableLiveData<List<Tariff>>()
    val tariffs: LiveData<List<Tariff>> = tariffsData

    private val servicesData = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> = servicesData

    private val newsData = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = newsData

    private val contactData = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = contactData

    private val langData = MutableLiveData<Lang>()
    val lang: LiveData<Lang> = langData


    private var databaseDisposable: Disposable? = null
    private var networkDisposable: Disposable? = null
    private var authDisposable: Disposable? = null


    init {
        start()
        val beeline = Operator(0, "", "#fff", "Beeline")
        val mobiuz = Operator(1, "", "#fff", "Mobiuz")
        val uzmobile = Operator(2, "", "#fff", "Uzmobile")
        operatorsData.postValue(arrayListOf(beeline, mobiuz, uzmobile))
    }

    fun selectOperator(operator: Operator) {
        operators.value?.let {
            it.map { o ->
                o.copy(
                    selected = o.id == operator.id
                )
            }
        }?.let {
            operatorsData.postValue(it)
        }
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

    private fun start() {
        if (BaseRepository.preference.token.isEmpty()) {
            auth()
        } else {
            getDataFromDB()
            updateVersion()
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
        networkDisposable = BaseRepository.mainApi.getData().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                BaseRepository.roomDatabase.tariffDao().deleteAll()
                BaseRepository.roomDatabase.tariffDao().insertAll(it.tariffs)
                tariffsData.postValue(it.tariffs.sortedBy { d -> d.priority })

                BaseRepository.roomDatabase.serviceDao().deleteAll()
                BaseRepository.roomDatabase.serviceDao().insertAll(it.services)
                servicesData.postValue(it.services.sortedBy { d -> d.priority })

                BaseRepository.roomDatabase.categoryDao().deleteAll()
                BaseRepository.roomDatabase.categoryDao().insertAll(it.categories)
                categoriesData.postValue(it.categories.sortedBy { d -> d.priority })

                BaseRepository.roomDatabase.packageDao().deleteAll()
                BaseRepository.roomDatabase.packageDao().insertAll(it.aPackages)
                packagesData.postValue(it.aPackages.sortedBy { d -> d.priority })

                BaseRepository.roomDatabase.contactDao().deleteAll()
                it.contacts?.let { c ->
                    BaseRepository.roomDatabase.contactDao().insert(c)
                    contactData.postValue(c)
                }

                BaseRepository.roomDatabase.bannerDao().deleteAll()
                BaseRepository.roomDatabase.bannerDao().insertAll(it.banners)
                bannersData.postValue(it.banners.sortedBy { d -> d.priority })

                BaseRepository.roomDatabase.newsDao().deleteAll()
                BaseRepository.roomDatabase.newsDao().insertAll(it.news)
                newsData.postValue(it.news.sortedByDescending { d -> d.date })
            }, {

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