package uz.appme.ussd.io

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tech.appme.ussd.io.StockPreference
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.UssdApp

import java.util.concurrent.TimeUnit

open class BaseRepository {

    companion object {

        private const val baseUrl = "http://ussd.appme.uz/api/v1/"
        const val imageUrl = "http://ussd.appme.uz/${BuildConfig.APPLICATION_ID}/images/"

        val roomDatabase by lazy {
            Room.databaseBuilder(UssdApp.instance, StockDatabase::class.java, "ussd.db")
                .fallbackToDestructiveMigration().build()
        }

        val api: UssdApi by lazy {
            retrofit.create(UssdApi::class.java)
        }

        fun getLang(context: Context) = StockPreference(context).lang

        fun setLang(lang: String, context: Context) {
            StockPreference(context).lang = lang
        }

        private val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addNetworkInterceptor { chain ->

                        val original = chain.request()

                        val request = original.newBuilder()
                            .header("Content-Type", "application/json;charset=utf-8")
                            .method(original.method, original.body)
                            .build()

                        chain.proceed(request)
                    }
                    .hostnameVerifier { _, _ -> true }
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60 / 2, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .cache(null)
                    .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

}