package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Operator

@Dao
interface OperatorDao {

    @Query("SELECT * FROM operator ORDER BY id DESC")
    fun getData(): Single<List<Operator>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(operator: Operator)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<Operator>)

    @Query("DELETE  FROM operator")
    fun deleteAll()

    @Query("SELECT * FROM operator WHERE selected == 'true'  ")
    fun getSelected():  Single<List<Operator>>

}