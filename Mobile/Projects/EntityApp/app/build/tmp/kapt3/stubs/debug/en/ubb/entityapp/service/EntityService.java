package en.ubb.entityapp.service;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ\u001b\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J\u001b\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000f"}, d2 = {"Len/ubb/entityapp/service/EntityService;", "", "addEntity", "Len/ubb/entityapp/domain/Entity;", "e", "(Len/ubb/entityapp/domain/Entity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEntity", "", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEntities", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public abstract interface EntityService {
    public static final en.ubb.entityapp.service.EntityService.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SERVICE_ENDPOINT = "http://192.168.3.137:3000";
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "/books")
    public abstract java.lang.Object getEntities(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<en.ubb.entityapp.domain.Entity>> p0);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.POST(value = "/book")
    public abstract java.lang.Object addEntity(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    en.ubb.entityapp.domain.Entity e, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super en.ubb.entityapp.domain.Entity> p1);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.DELETE(value = "/book/{id}/")
    public abstract java.lang.Object deleteEntity(@retrofit2.http.Path(value = "id")
    int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> p1);
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Len/ubb/entityapp/service/EntityService$Companion;", "", "()V", "SERVICE_ENDPOINT", "", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String SERVICE_ENDPOINT = "http://192.168.3.137:3000";
        
        private Companion() {
            super();
        }
    }
}