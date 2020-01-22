package en.ubb.entityapp.db;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\'J\u0016\u0010\u000f\u001a\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u0011H\'J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\tH\'J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016H\'J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00072\u0006\u0010\u0015\u001a\u00020\u0016H\'J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\'R\u0014\u0010\u0002\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R \u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00078gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Len/ubb/entityapp/db/RobotDao;", "", "numberOfEntities", "", "getNumberOfEntities", "()I", "robots", "Landroidx/lifecycle/LiveData;", "", "Len/ubb/entityapp/domain/Robot;", "getRobots", "()Landroidx/lifecycle/LiveData;", "addRobot", "", "robot", "addRobots", "entities", "", "deleteRobot", "entity", "deleteRobots", "type", "", "getRobotsByType", "updateRobot", "app_debug"})
public abstract interface RobotDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "select * from robots")
    public abstract androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Robot>> getRobots();
    
    @androidx.room.Query(value = "select count(*) from robots")
    public abstract int getNumberOfEntities();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "select * from robots where type = :type")
    public abstract androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Robot>> getRobotsByType(@org.jetbrains.annotations.NotNull()
    java.lang.String type);
    
    @androidx.room.Insert()
    public abstract void addRobot(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Robot robot);
    
    @androidx.room.Insert()
    public abstract void addRobots(@org.jetbrains.annotations.NotNull()
    java.util.List<en.ubb.entityapp.domain.Robot> entities);
    
    @androidx.room.Delete()
    public abstract void deleteRobot(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Robot entity);
    
    @androidx.room.Update()
    public abstract void updateRobot(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Robot robot);
    
    @androidx.room.Query(value = "delete from robots where type = :type")
    public abstract void deleteRobots(@org.jetbrains.annotations.NotNull()
    java.lang.String type);
}