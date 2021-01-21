package uz.appme.ussd.io.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ServiceDao {

    @Query("SELECT * FROM service")
    fun getData(): Single<List<uz.appme.ussd.data.Service>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: uz.appme.ussd.data.Service)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<uz.appme.ussd.data.Service>)

    @Query("DELETE  FROM service")
    fun deleteAll()

}