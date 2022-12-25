package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.tomatolive.library.R$attr;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Album;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.Platform;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.AlbumsSpinner */
/* loaded from: classes4.dex */
public class AlbumsSpinner {
    private static final int MAX_SHOWN_COUNT = 6;
    private CursorAdapter mAdapter;
    private ListPopupWindow mListPopupWindow;
    private AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private TextView mSelected;

    public AlbumsSpinner(@NonNull Context context) {
        this.mListPopupWindow = new ListPopupWindow(context, null, R$attr.listPopupWindowStyle);
        this.mListPopupWindow.setModal(true);
        float f = context.getResources().getDisplayMetrics().density;
        this.mListPopupWindow.setContentWidth((int) (216.0f * f));
        this.mListPopupWindow.setHorizontalOffset((int) (16.0f * f));
        this.mListPopupWindow.setVerticalOffset((int) (f * (-48.0f)));
        this.mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.AlbumsSpinner.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                AlbumsSpinner.this.onItemSelected(adapterView.getContext(), i);
                if (AlbumsSpinner.this.mOnItemSelectedListener != null) {
                    AlbumsSpinner.this.mOnItemSelectedListener.onItemSelected(adapterView, view, i, j);
                }
            }
        });
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setSelection(Context context, int i) {
        this.mListPopupWindow.setSelection(i);
        onItemSelected(context, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onItemSelected(Context context, int i) {
        this.mListPopupWindow.dismiss();
        Cursor cursor = this.mAdapter.getCursor();
        cursor.moveToPosition(i);
        String displayName = Album.valueOf(cursor).getDisplayName(context);
        if (this.mSelected.getVisibility() == 0) {
            this.mSelected.setText(displayName);
        } else if (Platform.hasICS()) {
            this.mSelected.setAlpha(0.0f);
            this.mSelected.setVisibility(0);
            this.mSelected.setText(displayName);
            this.mSelected.animate().alpha(1.0f).setDuration(context.getResources().getInteger(17694722)).start();
        } else {
            this.mSelected.setVisibility(0);
            this.mSelected.setText(displayName);
        }
    }

    public void setAdapter(CursorAdapter cursorAdapter) {
        this.mListPopupWindow.setAdapter(cursorAdapter);
        this.mAdapter = cursorAdapter;
    }

    public void setSelectedTextView(TextView textView) {
        this.mSelected = textView;
        Drawable drawable = this.mSelected.getCompoundDrawables()[2];
        TypedArray obtainStyledAttributes = this.mSelected.getContext().getTheme().obtainStyledAttributes(new int[]{R$attr.album_element_color});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        this.mSelected.setVisibility(8);
        this.mSelected.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.AlbumsSpinner.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int dimensionPixelSize = view.getResources().getDimensionPixelSize(R$dimen.fq_matisse_album_item_height);
                AlbumsSpinner.this.mListPopupWindow.setHeight(AlbumsSpinner.this.mAdapter.getCount() > 6 ? dimensionPixelSize * 6 : dimensionPixelSize * AlbumsSpinner.this.mAdapter.getCount());
                AlbumsSpinner.this.mListPopupWindow.show();
            }
        });
        TextView textView2 = this.mSelected;
        textView2.setOnTouchListener(this.mListPopupWindow.createDragToOpenListener(textView2));
    }

    public void setPopupAnchorView(View view) {
        this.mListPopupWindow.setAnchorView(view);
    }
}
