package uz.appme.ussd.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.appme.ussd.model.data.*
import uz.appme.ussd.model.db.*
import uz.appme.ussd.model.data.LimitConverter

@Database(
    entities = [
        Provider::class,
        Banner::class,
        Category::class,
        Tariff::class,
        Service::class,
        Pack::class,
        Code::class,
        News::class,
        Sale::class,
        Contact::class
    ], exportSchema = false, version = 2
)
@TypeConverters(LimitConverter::class)
abstract class StockDatabase : RoomDatabase() {

    abstract fun operatorDao(): ProviderDao

    abstract fun categoryDao(): CategoryDao

    abstract fun bannerDao(): BannerDao

    abstract fun contactDao(): ContactDao

    abstract fun newsDao(): NewsDao

    abstract fun packageDao(): PackageDao

    abstract fun serviceDao(): ServiceDao

    abstract fun tariffDao(): TariffDao

    abstract fun codesDao(): CodesDao

    abstract fun salesDao(): SalesDao

}

