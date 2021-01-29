package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getData(): Single<List<Category>>

    @Query("SELECT * FROM category WHERE type=:categoryType")
    fun getData(categoryType : Int) : Single<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Category>)


    @Query("DELETE  FROM category")
    fun deleteAll()

}