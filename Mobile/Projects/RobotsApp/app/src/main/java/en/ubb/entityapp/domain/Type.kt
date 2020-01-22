package en.ubb.entityapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class Type(@field:PrimaryKey var name: String)