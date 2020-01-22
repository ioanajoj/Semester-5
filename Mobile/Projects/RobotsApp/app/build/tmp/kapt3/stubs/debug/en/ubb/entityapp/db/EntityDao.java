package en.ubb.entityapp.db;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0016\u0010\f\u001a\u00020\r2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eH\'J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0005H\'J\b\u0010\u0011\u001a\u00020\rH\'J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0005H\'R \u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0013"}, d2 = {"Len/ubb/entityapp/db/EntityDao;", "", "entities", "Landroidx/lifecycle/LiveData;", "", "Len/ubb/entityapp/domain/Entity;", "getEntities", "()Landroidx/lifecycle/LiveData;", "numberOfEntities", "", "getNumberOfEntities", "()I", "addEntities", "", "", "addEntity", "entity", "deleteEntities", "deleteEntity", "app_debug"})
public abstract interface EntityDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "select * from entities")
    public abstract androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Entity>> getEntities();
    
    @androidx.room.Query(value = "select count(*) from entities")
    public abstract int getNumberOfEntities();
    
    @androidx.room.Insert()
    public abstract void addEntity(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Entity entity);
    
    @androidx.room.Insert()
    public abstract void addEntities(@org.jetbrains.annotations.NotNull()
    java.util.List<en.ubb.entityapp.domain.Entity> entities);
    
    @androidx.room.Delete()
    public abstract void deleteEntity(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Entity entity);
    
    @androidx.room.Query(value = "delete from entities")
    public abstract void deleteEntities();
}