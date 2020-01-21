package en.ubb.entityapp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2 = {"Len/ubb/entityapp/EntityApp;", "Landroid/app/Application;", "()V", "db", "Len/ubb/entityapp/db/EntityDatabase;", "getDb", "()Len/ubb/entityapp/db/EntityDatabase;", "setDb", "(Len/ubb/entityapp/db/EntityDatabase;)V", "onCreate", "", "app_debug"})
public final class EntityApp extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    public en.ubb.entityapp.db.EntityDatabase db;
    
    @org.jetbrains.annotations.NotNull()
    public final en.ubb.entityapp.db.EntityDatabase getDb() {
        return null;
    }
    
    public final void setDb(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.db.EntityDatabase p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    public EntityApp() {
        super();
    }
}