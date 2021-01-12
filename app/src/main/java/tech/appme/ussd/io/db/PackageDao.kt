package tech.appme.ussd.io.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface PackageDao {

    @Query("SELECT * FROM package")
    fun getData(): Single<List<tech.appme.ussd.data.Package>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: tech.appme.ussd.data.Package)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<tech.appme.ussd.data.Package>)

    @Query("DELETE  FROM package")
    fun deleteAll()

}