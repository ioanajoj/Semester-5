package en.ubb.entityapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "entities")
data class Entity(@field:PrimaryKey(autoGenerate = true)
                var id: Int, var title: String?, var date: Date?)