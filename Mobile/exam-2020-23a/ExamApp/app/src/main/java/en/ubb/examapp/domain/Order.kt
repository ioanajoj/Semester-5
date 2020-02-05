package en.ubb.examapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(@field:PrimaryKey(autoGenerate = false)
                  var id: Int, var table: String?, var details: String?, var status: String?,
                 var time: Int?, var type: String?)