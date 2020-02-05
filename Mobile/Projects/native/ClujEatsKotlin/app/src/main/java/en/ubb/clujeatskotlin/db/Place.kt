package en.ubb.clujeatskotlin.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "place_table",
    indices = [Index(value = ["name"], unique = true)]
)
data class Place(@Expose var name: String,
                 @Expose var address: String,
                 @Expose var photo: String) : Serializable
{
    @SerializedName("id")
    var remoteId: Long = -1
    @PrimaryKey(autoGenerate = true)
    var dbId: Long = 0
}