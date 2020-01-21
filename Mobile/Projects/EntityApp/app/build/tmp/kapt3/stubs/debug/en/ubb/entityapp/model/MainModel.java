package en.ubb.entityapp.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0006J\u0016\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0006J\u000e\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Len/ubb/entityapp/model/MainModel;", "Landroidx/lifecycle/ViewModel;", "()V", "entities", "Landroidx/lifecycle/LiveData;", "", "Len/ubb/entityapp/domain/Entity;", "getEntities", "()Landroidx/lifecycle/LiveData;", "loading", "", "getLoading", "manager", "Len/ubb/entityapp/Manager;", "mutableEntities", "Landroidx/lifecycle/MutableLiveData;", "mutableLoading", "service", "Len/ubb/entityapp/service/EntityService;", "addEntity", "", "app", "Len/ubb/entityapp/EntityApp;", "entity", "deleteEntity", "fetchEntities", "fetchEntitiesFromNetwork", "app_debug"})
public final class MainModel extends androidx.lifecycle.ViewModel {
    private en.ubb.entityapp.Manager manager;
    private final en.ubb.entityapp.service.EntityService service = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<en.ubb.entityapp.domain.Entity>> mutableEntities = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> mutableLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Entity>> entities = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> loading = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Entity>> getEntities() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getLoading() {
        return null;
    }
    
    public final void fetchEntitiesFromNetwork(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app) {
    }
    
    public final void fetchEntities(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app) {
    }
    
    public final void addEntity(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app, @org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Entity entity) {
    }
    
    public final void deleteEntity(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app, @org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Entity entity) {
    }
    
    public MainModel() {
        super();
    }
}