package en.ubb.entityapp.db;

import java.lang.System;

@androidx.room.TypeConverters(value = {en.ubb.entityapp.db.Converters.class})
@androidx.room.Database(entities = {en.ubb.entityapp.domain.Entity.class}, version = 1, exportSchema = false)
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Len/ubb/entityapp/db/EntityDatabase;", "Landroidx/room/RoomDatabase;", "()V", "entityDao", "Len/ubb/entityapp/db/EntityDao;", "getEntityDao", "()Len/ubb/entityapp/db/EntityDao;", "app_debug"})
public abstract class EntityDatabase extends androidx.room.RoomDatabase {
    
    @org.jetbrains.annotations.NotNull()
    public abstract en.ubb.entityapp.db.EntityDao getEntityDao();
    
    public EntityDatabase() {
        super();
    }
}