package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Code
import uz.appme.ussd.model.data.News
import uz.appme.ussd.model.data.Sale

@Dao
interface SalesDao {

    @Query("SELECT * FROM sales ORDER BY id DESC")
    fun getData(): Single<List<Sale>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Sale)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Sale>)

    @Query("DELETE  FROM sales")
    fun deleteAll()

}