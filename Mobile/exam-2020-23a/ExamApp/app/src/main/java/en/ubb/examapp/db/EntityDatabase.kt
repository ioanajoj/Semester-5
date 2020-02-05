package en.ubb.examapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import en.ubb.examapp.domain.Entity
import en.ubb.examapp.domain.Order

//    TODO("Add to list of entities")
@Database(entities = [Entity::class, Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EntityDatabase : RoomDatabase() {
    abstract val entityDao: EntityDao
//    TODO("Add Dao s")
}