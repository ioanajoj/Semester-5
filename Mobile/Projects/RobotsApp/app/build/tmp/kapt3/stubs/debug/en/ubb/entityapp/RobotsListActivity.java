package en.ubb.entityapp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0017\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0002J\u0012\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u000fH\u0014J\u0010\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J.\u0010\u001e\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u001f*\b\u0012\u0004\u0012\u0002H\u001f0 2\u0014\u0010\u001e\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u0001H\u001f\u0012\u0004\u0012\u00020\u000f0!H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Len/ubb/entityapp/RobotsListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Len/ubb/entityapp/adapter/RobotsAdapter;", "manager", "Len/ubb/entityapp/Manager;", "model", "Len/ubb/entityapp/model/MainModel;", "receivedRobots", "", "Len/ubb/entityapp/domain/Robot;", "type", "", "displayLoading", "", "loading", "", "(Ljava/lang/Boolean;)V", "displayRobots", "entities", "loadEntities", "observeModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStop", "setupRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "observe", "T", "Landroidx/lifecycle/LiveData;", "Lkotlin/Function1;", "app_debug"})
public final class RobotsListActivity extends androidx.appcompat.app.AppCompatActivity {
    private en.ubb.entityapp.adapter.RobotsAdapter adapter;
    private en.ubb.entityapp.Manager manager;
    private en.ubb.entityapp.model.MainModel model;
    private java.lang.String type;
    private java.util.List<en.ubb.entityapp.domain.Robot> receivedRobots;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void observeModel() {
    }
    
    private final <T extends java.lang.Object>void observe(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LiveData<T> $this$observe, kotlin.jvm.functions.Function1<? super T, kotlin.Unit> observe) {
    }
    
    private final void displayRobots(java.util.List<en.ubb.entityapp.domain.Robot> entities) {
    }
    
    private final void displayLoading(java.lang.Boolean loading) {
    }
    
    private final void loadEntities() {
    }
    
    private final void setupRecyclerView(androidx.recyclerview.widget.RecyclerView recyclerView) {
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    public RobotsListActivity() {
        super();
    }
}