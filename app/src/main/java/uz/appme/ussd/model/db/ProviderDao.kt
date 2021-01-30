package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Provider

@Dao
interface ProviderDao {

    @Query("SELECT * FROM provider ORDER BY id DESC")
    fun getData(): Single<List<Provider>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(provider: Provider)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Provider>)

    @Query("DELETE  FROM provider")
    fun deleteAll()

}