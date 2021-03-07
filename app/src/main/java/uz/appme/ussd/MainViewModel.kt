package uz.appme.ussd

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import uz.appme.ussd.model.data.*
import uz.appme.ussd.model.BaseRepository
import java.io.Serializable
import java.util.*


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
        val l = if (BaseRepository.preference.lang == "uz") Lang.UZ else Lang.RU
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

    fun changeLang(lang: Lang, context: Context) {
        val l: String = if (lang == Lang.UZ) "uz" else "ru"
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
            Log.e("token_", BaseRepository.preference.token)
            getDataFromDB()
            updateVersion()
        }
    }

    private fun getDataFromDB() {
        CompositeDisposable().add(BaseRepository.roomDatabase.operatorDao().getData()
            .flatMap {
                val selectedId = BaseRepository.preference.operatorId
                val operators = it.map { o -> o.copy(selected = o.id == selectedId) }
                operatorsData.postValue(operators.sortedBy { d -> d.id })
                BaseRepository.roomDatabase.bannerDao().getData()
            }
            .flatMap {
                bannersData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.contactDao().getData()
            }.flatMap {
                it.firstOrNull()?.let { c ->
                    contactData.postValue(c)
                }
                BaseRepository.roomDatabase.packageDao().getData()
            }.flatMap {
                packagesData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.categoryDao().getData()
            }.flatMap {
                categoriesData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.tariffDao().getData()
            }.flatMap {
                tariffsData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.serviceDao().getData()
            }.flatMap {
                servicesData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.codesDao().getData()
            }.flatMap {
                codesData.postValue(it.sortedBy { d -> d.priority })
                BaseRepository.roomDatabase.salesDao().getData()
            }.flatMap {
                salesData.postValue(it.sortedBy { d -> d.priority })
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
        val current = Calendar.getInstance().timeInMillis
        val lastUpdate = BaseRepository.preference.lastUpdate
        if (current - lastUpdate < 12L * 1000 * 36000)
            return
        BaseRepository.preference.lastUpdate = current
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

                val banners = it.banners
                BaseRepository.roomDatabase.bannerDao().deleteAll()
                BaseRepository.roomDatabase.bannerDao().insertAll(banners)
                bannersData.postValue(banners.sortedBy { d -> d.priority })

                val tariffs = it.tariffs
                BaseRepository.roomDatabase.tariffDao().deleteAll()
                BaseRepository.roomDatabase.tariffDao().insertAll(tariffs)
                tariffsData.postValue(tariffs.sortedBy { d -> d.priority })

                val categories = it.categories
                BaseRepository.roomDatabase.categoryDao().deleteAll()
                BaseRepository.roomDatabase.categoryDao().insertAll(categories)
                categoriesData.postValue(categories.sortedBy { d -> d.priority })

                val services = it.services
                BaseRepository.roomDatabase.serviceDao().deleteAll()
                BaseRepository.roomDatabase.serviceDao().insertAll(services)
                servicesData.postValue(services.sortedBy { d -> d.priority })

                val packs = it.packages
                BaseRepository.roomDatabase.packageDao().deleteAll()
                BaseRepository.roomDatabase.packageDao().insertAll(packs)
                packagesData.postValue(packs.sortedBy { d -> d.priority })

                val codes = it.codes
                BaseRepository.roomDatabase.codesDao().deleteAll()
                BaseRepository.roomDatabase.codesDao().insertAll(codes)
                codesData.postValue(codes.sortedByDescending { d -> d.id })

                val news = it.news
                BaseRepository.roomDatabase.codesDao().deleteAll()
                BaseRepository.roomDatabase.codesDao().insertAll(codes)
                newsData.postValue(news.sortedByDescending { d -> d.date })

                val sales = it.sales
                BaseRepository.roomDatabase.salesDao().deleteAll()
                BaseRepository.roomDatabase.salesDao().insertAll(sales)
                salesData.postValue(sales.sortedByDescending { d -> d.end })

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
        Log.e("auth__", "")
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
                    getDataFromDB()
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