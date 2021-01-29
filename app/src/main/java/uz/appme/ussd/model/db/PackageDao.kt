package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Pack

@Dao
interface PackageDao {

    @Query("SELECT * FROM packages")
    fun getData(): Single<List<Pack>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Pack)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Pack>)

    @Query("DELETE FROM packages")
    fun deleteAll()

}