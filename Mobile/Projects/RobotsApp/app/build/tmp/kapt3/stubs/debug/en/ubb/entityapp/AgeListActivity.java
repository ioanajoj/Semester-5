package en.ubb.entityapp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0017\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002\u00a2\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\nH\u0002J\b\u0010\u0013\u001a\u00020\nH\u0002J\u0012\u0010\u0014\u001a\u00020\n2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\b\u0010\u0017\u001a\u00020\nH\u0014J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J.\u0010\u001b\u001a\u00020\n\"\u0004\b\u0000\u0010\u001c*\b\u0012\u0004\u0012\u0002H\u001c0\u001d2\u0014\u0010\u001b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u0001H\u001c\u0012\u0004\u0012\u00020\n0\u001eH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Len/ubb/entityapp/AgeListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Len/ubb/entityapp/adapter/AgeRobotsAdapter;", "manager", "Len/ubb/entityapp/Manager;", "model", "Len/ubb/entityapp/model/MainModel;", "displayEntities", "", "entities", "", "Len/ubb/entityapp/domain/Robot;", "displayLoading", "loading", "", "(Ljava/lang/Boolean;)V", "loadEntities", "observeModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "setupRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "observe", "T", "Landroidx/lifecycle/LiveData;", "Lkotlin/Function1;", "app_debug"})
public final class AgeListActivity extends androidx.appcompat.app.AppCompatActivity {
    private en.ubb.entityapp.adapter.AgeRobotsAdapter adapter;
    private en.ubb.entityapp.Manager manager;
    private en.ubb.entityapp.model.MainModel model;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    private final void setupRecyclerView(androidx.recyclerview.widget.RecyclerView recyclerView) {
    }
    
    private final void observeModel() {
    }
    
    private final <T extends java.lang.Object>void observe(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.LiveData<T> $this$observe, kotlin.jvm.functions.Function1<? super T, kotlin.Unit> observe) {
    }
    
    private final void displayLoading(java.lang.Boolean loading) {
    }
    
    private final void displayEntities(java.util.List<en.ubb.entityapp.domain.Robot> entities) {
    }
    
    private final void loadEntities() {
    }
    
    public AgeListActivity() {
        super();
    }
}