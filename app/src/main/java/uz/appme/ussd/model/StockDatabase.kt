package uz.appme.ussd.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.appme.ussd.model.data.*
import uz.appme.ussd.model.db.*
import uz.appme.ussd.model.data.LimitConverter


@Database(
    entities = [
        Operator::class ,
        Category::class,
        Banner::class,
        Contact::class,
        News::class,
        Pack::class,
        Service::class,
        Tariff::class
    ], exportSchema = false, version = 2
)
@TypeConverters(LimitConverter::class)
abstract class StockDatabase : RoomDatabase() {

    abstract fun operatorDao() : OperatorDao

    abstract fun categoryDao(): CategoryDao

    abstract fun bannerDao(): BannerDao

    abstract fun contactDao(): ContactDao

    abstract fun newsDao(): NewsDao

    abstract fun packageDao(): PackageDao

    abstract fun serviceDao(): ServiceDao

    abstract fun tariffDao(): TariffDao




}
