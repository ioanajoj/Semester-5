package en.ubb.entityapp

import android.app.Application
import androidx.room.Room
import en.ubb.entityapp.db.EntityDatabase
import en.ubb.entityapp.utils.logi

class EntityApp : Application() {

    lateinit var db: EntityDatabase

    override fun onCreate() {
        super.onCreate()
        logi("EntityApp created")
        db = Room.databaseBuilder<EntityDatabase>(applicationContext,
            EntityDatabase::class.java, "entity-database").build()
    }
}