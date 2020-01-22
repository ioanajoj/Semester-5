package en.ubb.entityapp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, d2 = {"Len/ubb/entityapp/EntityApp;", "Landroid/app/Application;", "()V", "db", "Len/ubb/entityapp/db/EntityDatabase;", "getDb", "()Len/ubb/entityapp/db/EntityDatabase;", "setDb", "(Len/ubb/entityapp/db/EntityDatabase;)V", "model", "Len/ubb/entityapp/model/MainModel;", "getModel", "()Len/ubb/entityapp/model/MainModel;", "setModel", "(Len/ubb/entityapp/model/MainModel;)V", "onCreate", "", "EchoWebSocketListener", "app_debug"})
public final class EntityApp extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    public en.ubb.entityapp.db.EntityDatabase db;
    @org.jetbrains.annotations.NotNull()
    public en.ubb.entityapp.model.MainModel model;
    
    @org.jetbrains.annotations.NotNull()
    public final en.ubb.entityapp.db.EntityDatabase getDb() {
        return null;
    }
    
    public final void setDb(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.db.EntityDatabase p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final en.ubb.entityapp.model.MainModel getModel() {
        return null;
    }
    
    public final void setModel(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.model.MainModel p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    public EntityApp() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ$\u0010\u000b\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\"\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001c\u0010\u0015\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u000fH\u0016J\u001c\u0010\u0015\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u000e\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u000fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u001c"}, d2 = {"Len/ubb/entityapp/EntityApp$EchoWebSocketListener;", "Lokhttp3/WebSocketListener;", "(Len/ubb/entityapp/EntityApp;)V", "webSocket", "Lokhttp3/WebSocket;", "getWebSocket", "()Lokhttp3/WebSocket;", "setWebSocket", "(Lokhttp3/WebSocket;)V", "close", "", "onClosing", "code", "", "reason", "", "onFailure", "t", "", "response", "Lokhttp3/Response;", "onMessage", "text", "bytes", "Lokio/ByteString;", "onOpen", "send", "message", "app_debug"})
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