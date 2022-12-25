package com.gen.p059mh.webapp_extensions.matisse.internal.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.p002v4.content.CursorLoader;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Album;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.j256.ormlite.field.FieldType;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.loader.AlbumLoader */
/* loaded from: classes2.dex */
public class AlbumLoader extends CursorLoader {
    private static final String BUCKET_ORDER_BY = "datetaken DESC";
    public static final String COLUMN_COUNT = "count";
    private static final String SELECTION = "(media_type=? OR media_type=?) AND _size>0) GROUP BY (bucket_id";
    private static final String SELECTION_FOR_SINGLE_MEDIA_TYPE = "media_type=? AND _size>0) GROUP BY (bucket_id";
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String[] COLUMNS = {FieldType.FOREIGN_ID_FIELD_SUFFIX, "bucket_id", "bucket_display_name", "_data", "count"};
    private static final String[] PROJECTION = {FieldType.FOREIGN_ID_FIELD_SUFFIX, "bucket_id", "bucket_display_name", "_data", "COUNT(*) AS count"};
    private static final String[] SELECTION_ARGS = {String.valueOf(1), String.valueOf(3)};

    @Override // android.support.p002v4.content.Loader
    public void onContentChanged() {
    }

    private static String[] getSelectionArgsForSingleMediaType(int i) {
        return new String[]{String.valueOf(i)};
    }

    private AlbumLoader(Context context, String str, String[] strArr) {
        super(context, QUERY_URI, PROJECTION, str, strArr, BUCKET_ORDER_BY);
    }

    public static CursorLoader newInstance(Context context) {
        String[] strArr;
        boolean onlyShowImages = SelectionSpec.getInstance().onlyShowImages();
        String str = SELECTION_FOR_SINGLE_MEDIA_TYPE;
        if (onlyShowImages) {
            strArr = getSelectionArgsForSingleMediaType(1);
        } else if (SelectionSpec.getInstance().onlyShowVideos()) {
            strArr = getSelectionArgsForSingleMediaType(3);
        } else {
            strArr = SELECTION_ARGS;
            str = SELECTION;
        }
        return new AlbumLoader(context, str, strArr);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.support.p002v4.content.CursorLoader, android.support.p002v4.content.AsyncTaskLoader
    /* renamed from: loadInBackground */
    public Cursor mo6738loadInBackground() {
        Cursor mo6738loadInBackground = super.mo6738loadInBackground();
        MatrixCursor matrixCursor = new MatrixCursor(COLUMNS);
        String str = "";
        int i = 0;
        if (mo6738loadInBackground != null) {
            while (mo6738loadInBackground.moveToNext()) {
                i += mo6738loadInBackground.getInt(mo6738loadInBackground.getColumnIndex("count"));
            }
            if (mo6738loadInBackground.moveToFirst()) {
                str = mo6738loadInBackground.getString(mo6738loadInBackground.getColumnIndex("_data"));
            }
        }
        String str2 = Album.ALBUM_ID_ALL;
        matrixCursor.addRow(new String[]{str2, str2, "All", str, String.valueOf(i)});
        return new MergeCursor(new Cursor[]{matrixCursor, mo6738loadInBackground});
    }
}
