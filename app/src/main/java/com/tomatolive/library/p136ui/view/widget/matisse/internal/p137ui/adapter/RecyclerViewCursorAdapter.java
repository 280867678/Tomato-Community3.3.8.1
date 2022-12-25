package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter;

import android.database.Cursor;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.RecyclerView.ViewHolder;
import com.j256.ormlite.field.FieldType;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.adapter.RecyclerViewCursorAdapter */
/* loaded from: classes4.dex */
public abstract class RecyclerViewCursorAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private Cursor mCursor;
    private int mRowIDColumn;

    protected abstract int getItemViewType(int i, Cursor cursor);

    protected abstract void onBindViewHolder(VH vh, Cursor cursor);

    /* JADX INFO: Access modifiers changed from: package-private */
    public RecyclerViewCursorAdapter(Cursor cursor) {
        setHasStableIds(true);
        swapCursor(cursor);
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(VH vh, int i) {
        if (!isDataValid(this.mCursor)) {
            throw new IllegalStateException("Cannot bind view holder when cursor is in invalid state.");
        }
        if (!this.mCursor.moveToPosition(i)) {
            throw new IllegalStateException("Could not move cursor to position " + i + " when trying to bind view holder");
        }
        onBindViewHolder((RecyclerViewCursorAdapter<VH>) vh, this.mCursor);
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (!this.mCursor.moveToPosition(i)) {
            throw new IllegalStateException("Could not move cursor to position " + i + " when trying to get item view type.");
        }
        return getItemViewType(i, this.mCursor);
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (isDataValid(this.mCursor)) {
            return this.mCursor.getCount();
        }
        return 0;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        if (!isDataValid(this.mCursor)) {
            throw new IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
        }
        if (!this.mCursor.moveToPosition(i)) {
            throw new IllegalStateException("Could not move cursor to position " + i + " when trying to get an item id");
        }
        return this.mCursor.getLong(this.mRowIDColumn);
    }

    public void swapCursor(Cursor cursor) {
        if (cursor == this.mCursor) {
            return;
        }
        if (cursor != null) {
            this.mCursor = cursor;
            this.mRowIDColumn = this.mCursor.getColumnIndexOrThrow(FieldType.FOREIGN_ID_FIELD_SUFFIX);
            notifyDataSetChanged();
            return;
        }
        notifyItemRangeRemoved(0, getItemCount());
        this.mCursor = null;
        this.mRowIDColumn = -1;
    }

    public Cursor getCursor() {
        return this.mCursor;
    }

    private boolean isDataValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }
}
