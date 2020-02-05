package en.ubb.entityapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import en.ubb.entityapp.domain.Robot
import en.ubb.entityapp.domain.Type

@Dao
interface RobotDao {

    @get:Query("select * from robots")
    val robots: LiveData<MutableList<Robot>>

    @get:Query("select count(*) from robots")
    val numberOfEntities: Int

    @Query("select * from robots where type = :type")
    fun getRobotsByType(type: String): LiveData<MutableList<Robot>>

    @Insert
    fun addRobot(robot: Robot)

    @Insert
    fun addRobots(entities: List<Robot>)

    @Delete
    fun deleteRobot(entity: Robot)

    @Update
    fun updateRobot(robot: Robot)

//    @Query("delete from robots")
//    fun deleteRobots()

    @Query("delete from robots where type = :type")
    fun deleteRobots(type: String)
}