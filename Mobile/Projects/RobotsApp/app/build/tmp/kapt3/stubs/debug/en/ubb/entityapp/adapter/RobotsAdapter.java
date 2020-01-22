package en.ubb.entityapp.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0014B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0016J\u001c\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\f\u001a\u00020\bH\u0016J\u001c\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0014\u0010\u0011\u001a\u00020\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0013R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Len/ubb/entityapp/adapter/RobotsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Len/ubb/entityapp/adapter/RobotsAdapter$ViewHolder;", "()V", "mValues", "", "Len/ubb/entityapp/domain/Robot;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setData", "books", "", "ViewHolder", "app_debug"})
public final class RobotsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<en.ubb.entityapp.adapter.RobotsAdapter.ViewHolder> {
    private java.util.List<en.ubb.entityapp.domain.Robot> mValues;
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.List<en.ubb.entityapp.domain.Robot> books) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public en.ubb.entityapp.adapter.RobotsAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    en.ubb.entityapp.adapter.RobotsAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public RobotsAdapter() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\fX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\fX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2 = {"Len/ubb/entityapp/adapter/RobotsAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mView", "Landroid/view/View;", "(Len/ubb/entityapp/adapter/RobotsAdapter;Landroid/view/View;)V", "mItem", "Len/ubb/entityapp/domain/Robot;", "getMItem$app_debug", "()Len/ubb/entityapp/domain/Robot;", "setMItem$app_debug", "(Len/ubb/entityapp/domain/Robot;)V", "mRobotId", "Landroid/widget/TextView;", "getMRobotId$app_debug", "()Landroid/widget/TextView;", "mRobotName", "getMRobotName$app_debug", "mRobotType", "getMRobotType$app_debug", "getMView$app_debug", "()Landroid/view/View;", "toString", "", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView mRobotId = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView mRobotType = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView mRobotName = null;
        @org.jetbrains.annotations.Nullable()
        private en.ubb.entityapp.domain.Robot mItem;
        @org.jetbrains.annotations.NotNull()
        private final android.view.View mView = null;
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getMRobotId$app_debug() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getMRobotType$app_debug() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getMRobotName$app_debug() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final en.ubb.entityapp.domain.Robot getMItem$app_debug() {
            return null;
        }
        
        public final void setMItem$app_debug(@org.jetbrains.annotations.Nullable()
        en.ubb.entityapp.domain.Robot p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.view.View getMView$app_debug() {
            return null;
        }
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View mView) {
            super(null);
        }
    }
}