package uz.appme.ussd.io.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.data.Packages

@Dao
interface PackageDao {

    @Query("SELECT * FROM packages")
    fun getData(): Single<List<Packages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Packages)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Packages>)

    @Query("DELETE FROM packages")
    fun deleteAll()

}