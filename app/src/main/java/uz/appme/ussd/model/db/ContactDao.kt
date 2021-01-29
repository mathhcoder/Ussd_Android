package uz.appme.ussd.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import uz.appme.ussd.model.data.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getData(): Single<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Contact)

    @Query("DELETE  FROM contact")
    fun deleteAll()


}