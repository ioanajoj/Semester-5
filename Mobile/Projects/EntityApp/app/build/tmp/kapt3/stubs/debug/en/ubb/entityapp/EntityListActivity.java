package en.ubb.entityapp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0017\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002\u00a2\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\nH\u0002J\b\u0010\u0013\u001a\u00020\nH\u0002J\u0012\u0010\u0014\u001a\u00020\n2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J.\u0010\u001a\u001a\u00020\n\"\u0004\b\u0000\u0010\u001b*\b\u0012\u0004\u0012\u0002H\u001b0\u001c2\u0014\u0010\u001a\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u0001H\u001b\u0012\u0004\u0012\u00020\n0\u001dH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Len/ubb/entityapp/EntityListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Len/ubb/entityapp/adapter/MyAdapter;", "manager", "Len/ubb/entityapp/Manager;", "model", "Len/ubb/entityapp/model/MainModel;", "displayEntities", "", "entities", "", "Len/ubb/entityapp/domain/Entity;", "displayLoading", "loading", "", "(Ljava/lang/Boolean;)V", "loadEntities", "observeModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "observe", "T", "Landroidx/lifecycle/LiveData;", "Lkotlin/Function1;", "EchoWebSocketListener", "app_debug"})
public final class EntityListActivity extends androidx.appcompat.app.AppCompatActivity {
    private en.ubb.entityapp.adapter.MyAdapter adapter;
    private en.ubb.entityapp.Manager manager;
    private en.ubb.entityapp.model.MainModel model;
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
    
    private final void displayEntities(java.util.List<en.ubb.entityapp.domain.Entity> entities) {
    }
    
    private final void displayLoading(java.lang.Boolean loading) {
    }
    
    private final void loadEntities() {
    }
    
    private final void setupRecyclerView(androidx.recyclerview.widget.RecyclerView recyclerView) {
    }
    
    public EntityListActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ$\u0010\u000b\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\"\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001c\u0010\u0015\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u000fH\u0016J\u001c\u0010\u0015\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u000e\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u000fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u001c"}, d2 = {"Len/ubb/entityapp/EntityListActivity$EchoWebSocketListener;", "Lokhttp3/WebSocketListener;", "(Len/ubb/entityapp/EntityListActivity;)V", "webSocket", "Lokhttp3/WebSocket;", "getWebSocket", "()Lokhttp3/WebSocket;", "setWebSocket", "(Lokhttp3/WebSocket;)V", "close", "", "onClosing", "code", "", "reason", "", "onFailure", "t", "", "response", "Lokhttp3/Response;", "onMessage", "text", "bytes", "Lokio/ByteString;", "onOpen", "send", "message", "app_debug"})
    public final class EchoWebSocketListener extends okhttp3.WebSocketListener {
        @org.jetbrains.annotations.NotNull()
        public okhttp3.WebSocket webSocket;
        
        @org.jetbrains.annotations.NotNull()
        public final okhttp3.WebSocket getWebSocket() {
            return null;
        }
        
        public final void setWebSocket(@org.jetbrains.annotations.NotNull()
        okhttp3.WebSocket p0) {
        }
        
        @java.lang.Override()
        public void onOpen(@org.jetbrains.annotations.NotNull()
        okhttp3.WebSocket webSocket, @org.jetbrains.annotations.NotNull()
        okhttp3.Response response) {
        }
        
        @java.lang.Override()
        public void onMessage(@org.jetbrains.annotations.Nullable()
        okhttp3.WebSocket webSocket, @org.jetbrains.annotations.Nullable()
        java.lang.String text) {
        }
        
        @java.lang.Override()
        public void onMessage(@org.jetbrains.annotations.Nullable()
        okhttp3.WebSocket webSocket, @org.jetbrains.annotations.Nullable()
        okio.ByteString bytes) {
        }
        
        @java.lang.Override()
        public void onClosing(@org.jetbrains.annotations.Nullable()
        okhttp3.WebSocket webSocket, int code, @org.jetbrains.annotations.Nullable()
        java.lang.String reason) {
        }
        
        @java.lang.Override()
        public void onFailure(@org.jetbrains.annotations.NotNull()
        okhttp3.WebSocket webSocket, @org.jetbrains.annotations.NotNull()
        java.lang.Throwable t, @org.jetbrains.annotations.Nullable()
        okhttp3.Response response) {
        }
        
        public final void send(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        public final void close() {
        }
        
        public EchoWebSocketListener() {
            super();
        }
    }
}