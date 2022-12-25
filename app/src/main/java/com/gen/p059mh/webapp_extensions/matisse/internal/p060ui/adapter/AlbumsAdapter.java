package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$attr;
import com.gen.p059mh.webapp_extensions.R$dimen;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Album;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import java.io.File;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumsAdapter */
/* loaded from: classes2.dex */
public class AlbumsAdapter extends CursorAdapter {
    private final Drawable mPlaceholder;

    public AlbumsAdapter(Context context, Cursor cursor, boolean z) {
        super(context, cursor, z);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R$attr.sdk_album_thumbnail_placeholder});
        this.mPlaceholder = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
    }

    public AlbumsAdapter(Context context, Cursor cursor, int i) {
        super(context, cursor, i);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R$attr.sdk_album_thumbnail_placeholder});
        this.mPlaceholder = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R$layout.web_sdk_album_list_item, viewGroup, false);
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        Album valueOf = Album.valueOf(cursor);
        ((TextView) view.findViewById(R$id.album_name)).setText(valueOf.getDisplayName(context));
        ((TextView) view.findViewById(R$id.album_media_count)).setText(String.valueOf(valueOf.getCount()));
        SelectionSpec.getInstance().imageEngine.loadThumbnail(context, context.getResources().getDimensionPixelSize(R$dimen.media_grid_size), this.mPlaceholder, (ImageView) view.findViewById(R$id.album_cover), Uri.fromFile(new File(valueOf.getCoverPath())));
    }
}
