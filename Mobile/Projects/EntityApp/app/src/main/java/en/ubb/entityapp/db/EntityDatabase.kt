package en.ubb.entityapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import en.ubb.entityapp.domain.Entity

@Database(entities = [Entity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EntityDatabase : RoomDatabase() {
    abstract val entityDao: EntityDao
}