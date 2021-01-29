package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY date DESC")
    fun getData(): Single<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<News>)

    @Query("DELETE  FROM news")
    fun deleteAll()

}