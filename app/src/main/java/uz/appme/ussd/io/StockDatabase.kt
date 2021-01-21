package uz.appme.ussd.io

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.appme.ussd.data.*
import uz.appme.ussd.io.db.*
import uz.appme.ussd.modal.LimitConverter


@Database(
    entities = [
        //Provider::class ,
        Category::class,
        Banner::class,
        Contact::class,
        News::class,
        Packages::class,
        Service::class,
        Tariff::class
    ], exportSchema = false, version = 1
)
@TypeConverters(LimitConverter::class)
abstract class StockDatabase : RoomDatabase() {

    //abstract fun providerDao() : ProviderDao

    abstract fun categoryDao(): CategoryDao

    abstract fun bannerDao(): BannerDao

    abstract fun contactDao(): ContactDao

    abstract fun newsDao(): NewsDao

    abstract fun packageDao(): PackageDao

    abstract fun serviceDao(): ServiceDao

    abstract fun tariffDao(): TariffDao




}

