package com.tomatolive.library.p136ui.view.widget.matisse.internal.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.app.LoaderManager;
import android.support.p002v4.content.Loader;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Album;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.loader.AlbumMediaLoader;
import java.lang.ref.WeakReference;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.model.AlbumMediaCollection */
/* loaded from: classes4.dex */
public class AlbumMediaCollection implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARGS_ALBUM = "args_album";
    private static final String ARGS_ENABLE_CAPTURE = "args_enable_capture";
    private static final int LOADER_ID = 2;
    private AlbumMediaCallbacks mCallbacks;
    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;

    /* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.model.AlbumMediaCollection$AlbumMediaCallbacks */
    /* loaded from: classes4.dex */
    public interface AlbumMediaCallbacks {
        void onAlbumMediaLoad(Cursor cursor);

        void onAlbumMediaReset();
    }

    @Override // android.support.p002v4.app.LoaderManager.LoaderCallbacks
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Album album;
        Context context = this.mContext.get();
        if (context == null || (album = (Album) bundle.getParcelable(ARGS_ALBUM)) == null) {
            return null;
        }
        boolean z = false;
        if (album.isAll() && bundle.getBoolean(ARGS_ENABLE_CAPTURE, false)) {
            z = true;
        }
        return AlbumMediaLoader.newInstance(context, album, z);
    }

    @Override // android.support.p002v4.app.LoaderManager.LoaderCallbacks
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (this.mContext.get() == null) {
            return;
        }
        this.mCallbacks.onAlbumMediaLoad(cursor);
    }

    @Override // android.support.p002v4.app.LoaderManager.LoaderCallbacks
    public void onLoaderReset(Loader<Cursor> loader) {
        if (this.mContext.get() == null) {
            return;
        }
        this.mCallbacks.onAlbumMediaReset();
    }

    public void onCreate(@NonNull FragmentActivity fragmentActivity, @NonNull AlbumMediaCallbacks albumMediaCallbacks) {
        this.mContext = new WeakReference<>(fragmentActivity);
        this.mLoaderManager = fragmentActivity.getSupportLoaderManager();
        this.mCallbacks = albumMediaCallbacks;
    }

    public void onDestroy() {
        LoaderManager loaderManager = this.mLoaderManager;
        if (loaderManager != null) {
            loaderManager.destroyLoader(2);
        }
        this.mCallbacks = null;
    }

    public void load(@Nullable Album album) {
        load(album, false);
    }

    public void load(@Nullable Album album, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ALBUM, album);
        bundle.putBoolean(ARGS_ENABLE_CAPTURE, z);
        this.mLoaderManager.initLoader(2, bundle, this);
    }
}
