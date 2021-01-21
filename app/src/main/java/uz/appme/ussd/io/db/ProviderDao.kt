package uz.appme.ussd.io.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.data.Operator

interface ProviderDao {
    @Query("SELECT * FROM provider ORDER BY id DESC")
    fun getData(): Single<List<Operator>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(operator: Operator)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Operator>)

    @Query("DELETE  FROM provider")
    fun deleteAll()

    @Query("SELECT * FROM provider WHERE selected == 'true'  ")
    fun getSelected()

}