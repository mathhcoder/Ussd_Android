package tech.appme.ussd.io.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

import tech.appme.ussd.data.Provider

interface ProviderDao {
    @Query("SELECT * FROM provider ORDER BY id DESC")
    fun getData(): Single<List<Provider>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(provider: Provider)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Provider>)

    @Query("DELETE  FROM provider")
    fun deleteAll()

    @Query("SELECT * FROM provider WHERE selected == 'true'  ")
    fun getSelected()

}