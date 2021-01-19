package tech.appme.ussd.io.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import tech.appme.ussd.data.Category
import tech.appme.ussd.data.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getData(): Single<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Contact)

    @Query("DELETE  FROM contact")
    fun deleteAll()


}