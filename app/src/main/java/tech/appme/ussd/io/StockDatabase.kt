package tech.appme.ussd.io.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.appme.ussd.data.*
import tech.appme.ussd.io.db.*
import tech.appme.ussd.modal.LimitConverter


@Database(
    entities = [
        Banner::class,
        Category::class,
        Contact::class,
        News::class,
        Package::class,
        Service::class,
        Tariff::class
    ], exportSchema = false, version = 1
)
@TypeConverters(LimitConverter::class)
abstract class StockDatabase : RoomDatabase() {

    abstract fun bannerDao(): BannerDao

    abstract fun categoryDao(): CategoryDao

    abstract fun contactDao(): ContactDao

    abstract fun newsDao(): NewsDao

    abstract fun packageDao(): PackageDao

    abstract fun serviceDao(): ServiceDao

    abstract fun tariffDao(): TariffDao

}

