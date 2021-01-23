package uz.appme.ussd.io

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.UssdApp

import java.util.concurrent.TimeUnit

open class BaseRepository {

    companion object {

        val roomDatabase by lazy {
            Room.databaseBuilder(UssdApp.instance, StockDatabase::class.java, "ussd.db")
                .fallbackToDestructiveMigration().build()
        }

        val authApi: UssdApi by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API)
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
                .build().create(UssdApi::class.java)
        }

        val mainApi: UssdApi by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API)
                .client(OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addNetworkInterceptor { chain ->

                        val original = chain.request()

                        val request = original.newBuilder()
                            .method(original.method, original.body)
                            .addHeader("Content-Type", "application/json;charset=utf-8")
                            .addHeader("Authorization", "Bearer ${preference.token}")
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
                .build().create(UssdApi::class.java)
        }

        val preference by lazy {
            StockPreference(UssdApp.instance)
        }

        fun getLang(context: Context) = StockPreference(context).lang

        fun setLang(lang: String, context: Context) {
            StockPreference(context).lang = lang
        }

    }

}