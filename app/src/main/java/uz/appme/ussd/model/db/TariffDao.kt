package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Tariff

@Dao
interface TariffDao {

    @Query("SELECT * FROM tariff")
    fun getData(): Single<List<Tariff>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Tariff)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Tariff>)

    @Query("DELETE  FROM tariff")
    fun deleteAll()

}