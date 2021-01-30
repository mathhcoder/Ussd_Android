package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Code
import uz.appme.ussd.model.data.News

@Dao
interface CodesDao {

    @Query("SELECT * FROM codes ORDER BY priority DESC")
    fun getData(): Single<List<Code>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Code)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Code>)

    @Query("DELETE  FROM codes")
    fun deleteAll()

}