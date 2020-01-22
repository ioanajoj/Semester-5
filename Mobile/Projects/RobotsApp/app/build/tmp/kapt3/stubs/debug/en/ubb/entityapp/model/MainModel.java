package en.ubb.entityapp.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u000eJ\u000e\u0010%\u001a\u00020!2\u0006\u0010\"\u001a\u00020#J\u000e\u0010&\u001a\u00020!2\u0006\u0010\"\u001a\u00020#J\u0016\u0010\'\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010(\u001a\u00020\u0015J\u0016\u0010)\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010(\u001a\u00020\u0015J\u000e\u0010*\u001a\u00020!2\u0006\u0010\"\u001a\u00020#J\u000e\u0010+\u001a\u00020!2\u0006\u0010\"\u001a\u00020#J\u001e\u0010,\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.J\u001e\u00100\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010-\u001a\u00020.2\u0006\u00101\u001a\u00020.R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\r0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0007R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\r0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0007\u00a8\u00062"}, d2 = {"Len/ubb/entityapp/model/MainModel;", "Landroidx/lifecycle/ViewModel;", "()V", "loading", "Landroidx/lifecycle/LiveData;", "", "getLoading", "()Landroidx/lifecycle/LiveData;", "manager", "Len/ubb/entityapp/Manager;", "mutableLoading", "Landroidx/lifecycle/MutableLiveData;", "mutableOldRobots", "", "Len/ubb/entityapp/domain/Robot;", "mutableRobots", "mutableTypes", "Len/ubb/entityapp/domain/Type;", "oldRobots", "getOldRobots", "received_types", "", "getReceived_types", "()Ljava/util/List;", "setReceived_types", "(Ljava/util/List;)V", "robots", "getRobots", "service", "Len/ubb/entityapp/service/EntityService;", "types", "getTypes", "addRobot", "", "app", "Len/ubb/entityapp/EntityApp;", "robot", "fetchOldRobots", "fetchOldRobotsFromNetwork", "fetchRobots", "type", "fetchRobotsFromNetwork", "fetchTypes", "fetchTypesFromNetwork", "updateRobot", "id", "", "height", "updateRobotAge", "age", "app_debug"})
public final class MainModel extends androidx.lifecycle.ViewModel {
    private en.ubb.entityapp.Manager manager;
    private final en.ubb.entityapp.service.EntityService service = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<en.ubb.entityapp.domain.Type>> mutableTypes = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<en.ubb.entityapp.domain.Robot>> mutableRobots = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> mutableLoading = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<en.ubb.entityapp.domain.Robot>> mutableOldRobots = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Type>> types = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Robot>> robots = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> loading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Robot>> oldRobots = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> received_types;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Type>> getTypes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Robot>> getRobots() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> getLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<en.ubb.entityapp.domain.Robot>> getOldRobots() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getReceived_types() {
        return null;
    }
    
    public final void setReceived_types(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> p0) {
    }
    
    public final void fetchTypesFromNetwork(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app) {
    }
    
    public final void fetchTypes(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app) {
    }
    
    public final void fetchRobotsFromNetwork(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app, @org.jetbrains.annotations.NotNull()
    java.lang.String type) {
    }
    
    public final void fetchRobots(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app, @org.jetbrains.annotations.NotNull()
    java.lang.String type) {
    }
    
    public final void addRobot(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app, @org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.domain.Robot robot) {
    }
    
    public final void updateRobot(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app, int id, int height) {
    }
    
    public final void fetchOldRobotsFromNetwork(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app) {
    }
    
    public final void fetchOldRobots(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app) {
    }
    
    public final void updateRobotAge(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.EntityApp app, int id, int age) {
    }
    
    public MainModel() {
        super();
    }
}