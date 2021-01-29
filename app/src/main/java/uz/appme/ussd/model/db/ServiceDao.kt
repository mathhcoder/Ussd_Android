package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ServiceDao {

    @Query("SELECT * FROM service")
    fun getData(): Single<List<uz.appme.ussd.model.data.Service>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: uz.appme.ussd.model.data.Service)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<uz.appme.ussd.model.data.Service>)

    @Query("DELETE  FROM service")
    fun deleteAll()

}