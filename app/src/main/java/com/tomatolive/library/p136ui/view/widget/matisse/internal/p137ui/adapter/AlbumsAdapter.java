package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter;

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
import com.tomatolive.library.R$attr;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Album;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.SelectionSpec;
import java.io.File;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.adapter.AlbumsAdapter */
/* loaded from: classes4.dex */
public class AlbumsAdapter extends CursorAdapter {
    private final Drawable mPlaceholder;

    public AlbumsAdapter(Context context, Cursor cursor, boolean z) {
        super(context, cursor, z);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R$attr.album_thumbnail_placeholder});
        this.mPlaceholder = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
    }

    public AlbumsAdapter(Context context, Cursor cursor, int i) {
        super(context, cursor, i);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R$attr.album_thumbnail_placeholder});
        this.mPlaceholder = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R$layout.fq_matisse_album_list_item, viewGroup, false);
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        Album valueOf = Album.valueOf(cursor);
        ((TextView) view.findViewById(R$id.album_name)).setText(valueOf.getDisplayName(context));
        ((TextView) view.findViewById(R$id.album_media_count)).setText(String.valueOf(valueOf.getCount()));
        SelectionSpec.getInstance().imageEngine.loadThumbnail(context, context.getResources().getDimensionPixelSize(R$dimen.fq_matisse_media_grid_size), this.mPlaceholder, (ImageView) view.findViewById(R$id.album_cover), Uri.fromFile(new File(valueOf.getCoverPath())));
    }
}
