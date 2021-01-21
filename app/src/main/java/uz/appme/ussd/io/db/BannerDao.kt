package uz.appme.ussd.io.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.data.Banner

@Dao
interface BannerDao {

    @Query("SELECT * FROM banner")
    fun getData(): Single<List<Banner>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Banner)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Banner>)

    @Query("DELETE  FROM banner")
    fun deleteAll()
}