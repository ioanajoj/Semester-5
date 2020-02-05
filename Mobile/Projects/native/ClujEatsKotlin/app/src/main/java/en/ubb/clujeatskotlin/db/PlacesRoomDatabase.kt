package en.ubb.clujeatskotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Place::class), version = 1)
public abstract class PlacesRoomDatabase : RoomDatabase() {

    abstract fun placeDao() : PlaceDAO

    companion object {
        @Volatile
        private var INSTANCE: PlacesRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PlacesRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlacesRoomDatabase::class.java,
                    "places_database")
                    .addCallback(PlacesDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PlacesDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                scope.launch {
//                    populateDatabase(database.placeDao())
                }
            }
        }

        suspend fun populateDatabase(placeDao: PlaceDAO) {
            placeDao.deleteAll()

            var place = Place("A", "a", "link")
            placeDao.insert(place)
            place = Place("B", "b", "link")
            placeDao.insert(place)
        }
    }
}