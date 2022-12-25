package com.tomatolive.library.p136ui.view.widget.matisse.internal.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.p002v4.content.CursorLoader;
import com.j256.ormlite.field.FieldType;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Album;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.SelectionSpec;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.MediaStoreCompat;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.loader.AlbumMediaLoader */
/* loaded from: classes4.dex */
public class AlbumMediaLoader extends CursorLoader {
    private static final String ORDER_BY = "datetaken DESC";
    private static final String SELECTION_ALBUM = "(media_type=? OR media_type=?) AND  bucket_id=? AND _size>0";
    private static final String SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE = "media_type=? AND  bucket_id=? AND _size>0";
    private static final String SELECTION_ALL = "(media_type=? OR media_type=?) AND _size>0";
    private static final String SELECTION_ALL_FOR_SINGLE_MEDIA_TYPE = "media_type=? AND _size>0";
    private final boolean mEnableCapture;
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String[] PROJECTION = {FieldType.FOREIGN_ID_FIELD_SUFFIX, "_display_name", "mime_type", "_size", "duration"};
    private static final String[] SELECTION_ALL_ARGS = {String.valueOf(1), String.valueOf(3)};

    @Override // android.support.p002v4.content.Loader
    public void onContentChanged() {
    }

    private static String[] getSelectionArgsForSingleMediaType(int i) {
        return new String[]{String.valueOf(i)};
    }

    private static String[] getSelectionAlbumArgs(String str) {
        return new String[]{String.valueOf(1), String.valueOf(3), str};
    }

    private static String[] getSelectionAlbumArgsForSingleMediaType(int i, String str) {
        return new String[]{String.valueOf(i), str};
    }

    private AlbumMediaLoader(Context context, String str, String[] strArr, boolean z) {
        super(context, QUERY_URI, PROJECTION, str, strArr, ORDER_BY);
        this.mEnableCapture = z;
    }

    public static CursorLoader newInstance(Context context, Album album, boolean z) {
        String str;
        String[] selectionAlbumArgs;
        if (album.isAll()) {
            boolean onlyShowImages = SelectionSpec.getInstance().onlyShowImages();
            str = SELECTION_ALL_FOR_SINGLE_MEDIA_TYPE;
            if (onlyShowImages) {
                selectionAlbumArgs = getSelectionArgsForSingleMediaType(1);
            } else if (SelectionSpec.getInstance().onlyShowVideos()) {
                selectionAlbumArgs = getSelectionArgsForSingleMediaType(3);
            } else {
                selectionAlbumArgs = SELECTION_ALL_ARGS;
                str = SELECTION_ALL;
            }
        } else {
            boolean onlyShowImages2 = SelectionSpec.getInstance().onlyShowImages();
            str = SELECTION_ALBUM_FOR_SINGLE_MEDIA_TYPE;
            if (onlyShowImages2) {
                selectionAlbumArgs = getSelectionAlbumArgsForSingleMediaType(1, album.getId());
            } else if (SelectionSpec.getInstance().onlyShowVideos()) {
                selectionAlbumArgs = getSelectionAlbumArgsForSingleMediaType(3, album.getId());
            } else {
                selectionAlbumArgs = getSelectionAlbumArgs(album.getId());
                str = SELECTION_ALBUM;
            }
            z = false;
        }
        return new AlbumMediaLoader(context, str, selectionAlbumArgs, z);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.support.p002v4.content.CursorLoader, android.support.p002v4.content.AsyncTaskLoader
    /* renamed from: loadInBackground */
    public Cursor mo6738loadInBackground() {
        Cursor mo6738loadInBackground = super.mo6738loadInBackground();
        if (!this.mEnableCapture || !MediaStoreCompat.hasCameraFeature(getContext())) {
            return mo6738loadInBackground;
        }
        MatrixCursor matrixCursor = new MatrixCursor(PROJECTION);
        matrixCursor.addRow(new Object[]{-1L, "Capture", "", 0, 0});
        return new MergeCursor(new Cursor[]{matrixCursor, mo6738loadInBackground});
    }
}
