package com.luck.picture.lib.model;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.app.LoaderManager;
import android.support.p002v4.content.CursorLoader;
import android.support.p002v4.content.Loader;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.luck.picture.lib.R$string;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class LocalMediaLoader {
    private FragmentActivity activity;
    private boolean isGif;
    private int type;
    private long videoMaxS;
    private long videoMinS;
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String[] PROJECTION = {FieldType.FOREIGN_ID_FIELD_SUFFIX, "_data", "mime_type", "width", "height", "duration"};
    private static final String[] SELECTION_ALL_ARGS = {String.valueOf(1), String.valueOf(3)};

    /* loaded from: classes3.dex */
    public interface LocalMediaLoadListener {
        void loadComplete(List<LocalMediaFolder> list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSelectionArgsForSingleMediaCondition(String str) {
        return "media_type=? AND _size>0 AND " + str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getSelectionArgsForAllMediaCondition(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("(media_type=?");
        sb.append(z ? "" : " AND mime_type!='image/gif'");
        sb.append(" OR ");
        sb.append("media_type=? AND ");
        sb.append(str);
        sb.append(") AND ");
        sb.append("_size");
        sb.append(">0");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] getSelectionArgsForSingleMediaType(int i) {
        return new String[]{String.valueOf(i)};
    }

    public LocalMediaLoader(FragmentActivity fragmentActivity, int i, boolean z, long j, long j2) {
        this.type = 1;
        this.videoMaxS = 0L;
        this.videoMinS = 0L;
        this.activity = fragmentActivity;
        this.type = i;
        this.isGif = z;
        this.videoMaxS = j;
        this.videoMinS = j2;
    }

    public void loadAllMedia(final LocalMediaLoadListener localMediaLoadListener) {
        this.activity.getSupportLoaderManager().initLoader(this.type, null, new LoaderManager.LoaderCallbacks<Cursor>() { // from class: com.luck.picture.lib.model.LocalMediaLoader.1
            @Override // android.support.p002v4.app.LoaderManager.LoaderCallbacks
            public void onLoaderReset(Loader<Cursor> loader) {
            }

            @Override // android.support.p002v4.app.LoaderManager.LoaderCallbacks
            public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                if (i == 0) {
                    return new CursorLoader(LocalMediaLoader.this.activity, LocalMediaLoader.QUERY_URI, LocalMediaLoader.PROJECTION, LocalMediaLoader.getSelectionArgsForAllMediaCondition(LocalMediaLoader.this.getDurationCondition(0L, 0L), LocalMediaLoader.this.isGif), LocalMediaLoader.SELECTION_ALL_ARGS, "_id DESC");
                } else if (i == 1) {
                    return new CursorLoader(LocalMediaLoader.this.activity, LocalMediaLoader.QUERY_URI, LocalMediaLoader.PROJECTION, LocalMediaLoader.this.isGif ? "media_type=? AND _size>0" : "media_type=? AND _size>0 AND mime_type!='image/gif'", LocalMediaLoader.getSelectionArgsForSingleMediaType(1), "_id DESC");
                } else if (i == 2) {
                    return new CursorLoader(LocalMediaLoader.this.activity, LocalMediaLoader.QUERY_URI, LocalMediaLoader.PROJECTION, LocalMediaLoader.getSelectionArgsForSingleMediaCondition(LocalMediaLoader.this.getDurationCondition(0L, 0L)), LocalMediaLoader.getSelectionArgsForSingleMediaType(3), "_id DESC");
                } else if (i != 3) {
                    return null;
                } else {
                    return new CursorLoader(LocalMediaLoader.this.activity, LocalMediaLoader.QUERY_URI, LocalMediaLoader.PROJECTION, LocalMediaLoader.getSelectionArgsForSingleMediaCondition(LocalMediaLoader.this.getDurationCondition(0L, 500L)), LocalMediaLoader.getSelectionArgsForSingleMediaType(2), "_id DESC");
                }
            }

            @Override // android.support.p002v4.app.LoaderManager.LoaderCallbacks
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                try {
                    ArrayList arrayList = new ArrayList();
                    LocalMediaFolder localMediaFolder = new LocalMediaFolder();
                    ArrayList arrayList2 = new ArrayList();
                    if (cursor == null) {
                        return;
                    }
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            String string = cursor.getString(cursor.getColumnIndexOrThrow(LocalMediaLoader.PROJECTION[1]));
                            LocalMedia localMedia = new LocalMedia(string, cursor.getInt(cursor.getColumnIndexOrThrow(LocalMediaLoader.PROJECTION[5])), LocalMediaLoader.this.type, cursor.getString(cursor.getColumnIndexOrThrow(LocalMediaLoader.PROJECTION[2])), cursor.getInt(cursor.getColumnIndexOrThrow(LocalMediaLoader.PROJECTION[3])), cursor.getInt(cursor.getColumnIndexOrThrow(LocalMediaLoader.PROJECTION[4])));
                            LocalMediaFolder imageFolder = LocalMediaLoader.this.getImageFolder(string, arrayList);
                            imageFolder.getImages().add(localMedia);
                            imageFolder.setImageNum(imageFolder.getImageNum() + 1);
                            arrayList2.add(localMedia);
                            localMediaFolder.setImageNum(localMediaFolder.getImageNum() + 1);
                        } while (cursor.moveToNext());
                        if (arrayList2.size() > 0) {
                            LocalMediaLoader.this.sortFolder(arrayList);
                            arrayList.add(0, localMediaFolder);
                            localMediaFolder.setFirstImagePath(arrayList2.get(0).getPath());
                            localMediaFolder.setName(LocalMediaLoader.this.type == PictureMimeType.ofAudio() ? LocalMediaLoader.this.activity.getString(R$string.picture_all_audio) : LocalMediaLoader.this.activity.getString(R$string.picture_camera_roll));
                            localMediaFolder.setImages(arrayList2);
                        }
                        localMediaLoadListener.loadComplete(arrayList);
                        return;
                    }
                    localMediaLoadListener.loadComplete(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sortFolder(List<LocalMediaFolder> list) {
        Collections.sort(list, new Comparator<LocalMediaFolder>(this) { // from class: com.luck.picture.lib.model.LocalMediaLoader.2
            @Override // java.util.Comparator
            public int compare(LocalMediaFolder localMediaFolder, LocalMediaFolder localMediaFolder2) {
                int imageNum;
                int imageNum2;
                if (localMediaFolder.getImages() == null || localMediaFolder2.getImages() == null || (imageNum = localMediaFolder.getImageNum()) == (imageNum2 = localMediaFolder2.getImageNum())) {
                    return 0;
                }
                return imageNum < imageNum2 ? 1 : -1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LocalMediaFolder getImageFolder(String str, List<LocalMediaFolder> list) {
        File parentFile = new File(str).getParentFile();
        for (LocalMediaFolder localMediaFolder : list) {
            if (localMediaFolder.getName().equals(parentFile.getName())) {
                return localMediaFolder;
            }
        }
        LocalMediaFolder localMediaFolder2 = new LocalMediaFolder();
        localMediaFolder2.setName(parentFile.getName());
        localMediaFolder2.setPath(parentFile.getAbsolutePath());
        localMediaFolder2.setFirstImagePath(str);
        list.add(localMediaFolder2);
        return localMediaFolder2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDurationCondition(long j, long j2) {
        long j3 = this.videoMaxS;
        if (j3 == 0) {
            j3 = Long.MAX_VALUE;
        }
        if (j != 0) {
            j3 = Math.min(j3, j);
        }
        Locale locale = Locale.CHINA;
        Object[] objArr = new Object[3];
        objArr[0] = Long.valueOf(Math.max(j2, this.videoMinS));
        objArr[1] = Math.max(j2, this.videoMinS) == 0 ? "" : SimpleComparison.EQUAL_TO_OPERATION;
        objArr[2] = Long.valueOf(j3);
        return String.format(locale, "%d <%s duration and duration <= %d", objArr);
    }
}
