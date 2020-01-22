package en.ubb.entityapp.db;

import java.lang.System;

@androidx.room.Database(entities = {en.ubb.entityapp.domain.Type.class, en.ubb.entityapp.domain.Robot.class}, version = 1, exportSchema = false)
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Len/ubb/entityapp/db/EntityDatabase;", "Landroidx/room/RoomDatabase;", "()V", "robotsDao", "Len/ubb/entityapp/db/RobotDao;", "getRobotsDao", "()Len/ubb/entityapp/db/RobotDao;", "typesDao", "Len/ubb/entityapp/db/TypesDao;", "getTypesDao", "()Len/ubb/entityapp/db/TypesDao;", "app_debug"})
public abstract class EntityDatabase extends androidx.room.RoomDatabase {
    
    @org.jetbrains.annotations.NotNull()
    public abstract en.ubb.entityapp.db.TypesDao getTypesDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract en.ubb.entityapp.db.RobotDao getRobotsDao();
    
    public EntityDatabase() {
        super();
    }
}