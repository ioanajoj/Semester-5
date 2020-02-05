package en.ubb.entityapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import en.ubb.entityapp.domain.Robot
import en.ubb.entityapp.domain.Type

@Database(entities = [Type::class, Robot::class], version = 1, exportSchema = false)
abstract class EntityDatabase : RoomDatabase() {
    abstract val typesDao: TypesDao
    abstract val robotsDao: RobotDao
}