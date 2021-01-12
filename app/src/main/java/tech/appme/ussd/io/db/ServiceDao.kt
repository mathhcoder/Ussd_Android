package tech.appme.ussd.io.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import tech.appme.ussd.data.Service

@Dao
interface ServiceDao {

    @Query("SELECT * FROM service")
    fun getData(): Single<List<Service>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Service)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Service>)

    @Query("DELETE  FROM service")
    fun deleteAll()

}