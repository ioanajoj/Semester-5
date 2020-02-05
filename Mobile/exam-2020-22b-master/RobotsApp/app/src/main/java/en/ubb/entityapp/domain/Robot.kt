package en.ubb.entityapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "robots")
data class Robot(@field:PrimaryKey var id: Int,
                 var name: String?, var specs: String?,
                 var height: Int?, var type: String?, var age: Int?)