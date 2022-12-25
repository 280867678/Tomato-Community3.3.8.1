package com.luck.picture.lib.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.luck.picture.lib.R$anim;
import com.luck.picture.lib.R$attr;
import com.luck.picture.lib.R$color;
import com.luck.picture.lib.R$id;
import com.luck.picture.lib.R$layout;
import com.luck.picture.lib.R$style;
import com.luck.picture.lib.adapter.PictureAlbumDirectoryAdapter;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.tools.AttrsUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.StringUtils;
import java.util.List;

/* loaded from: classes3.dex */
public class FolderPopWindow extends PopupWindow implements View.OnClickListener {
    private PictureAlbumDirectoryAdapter adapter;
    private Animation animationIn;
    private Animation animationOut;
    private Context context;
    private Drawable drawableDown;
    private Drawable drawableUp;
    private LinearLayout id_ll_root;
    private boolean isDismiss = false;
    private int mimeType;
    private TextView picture_title;
    private RecyclerView recyclerView;
    private View window;

    public FolderPopWindow(Context context, int i) {
        this.context = context;
        this.mimeType = i;
        this.window = LayoutInflater.from(context).inflate(R$layout.picture_window_folder, (ViewGroup) null);
        setContentView(this.window);
        setWidth(ScreenUtils.getScreenWidth(context));
        setHeight(ScreenUtils.getScreenHeight(context));
        setAnimationStyle(R$style.WindowStyle);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(new ColorDrawable(Color.argb(123, 0, 0, 0)));
        this.drawableUp = AttrsUtils.getTypeValuePopWindowImg(context, R$attr.picture_arrow_up_icon);
        this.drawableDown = AttrsUtils.getTypeValuePopWindowImg(context, R$attr.picture_arrow_down_icon);
        this.animationIn = AnimationUtils.loadAnimation(context, R$anim.photo_album_show);
        this.animationOut = AnimationUtils.loadAnimation(context, R$anim.photo_album_dismiss);
        initView();
    }

    public void initView() {
        this.id_ll_root = (LinearLayout) this.window.findViewById(R$id.id_ll_root);
        this.adapter = new PictureAlbumDirectoryAdapter(this.context);
        this.recyclerView = (RecyclerView) this.window.findViewById(R$id.folder_list);
        this.recyclerView.getLayoutParams().height = (int) (ScreenUtils.getScreenHeight(this.context) * 0.6d);
        RecyclerView recyclerView = this.recyclerView;
        Context context = this.context;
        recyclerView.addItemDecoration(new RecycleViewDivider(context, 0, ScreenUtils.dip2px(context, 0.0f), ContextCompat.getColor(this.context, R$color.transparent)));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.recyclerView.setAdapter(this.adapter);
        this.id_ll_root.setOnClickListener(this);
    }

    public void bindFolder(List<LocalMediaFolder> list) {
        this.adapter.setMimeType(this.mimeType);
        this.adapter.bindFolderData(list);
    }

    public void setPictureTitleView(TextView textView) {
        this.picture_title = textView;
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                setHeight(view.getResources().getDisplayMetrics().heightPixels - rect.bottom);
            }
            super.showAsDropDown(view);
            this.isDismiss = false;
            this.recyclerView.startAnimation(this.animationIn);
            StringUtils.modifyTextViewDrawable(this.picture_title, this.drawableUp, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnItemClickListener(PictureAlbumDirectoryAdapter.OnItemClickListener onItemClickListener) {
        this.adapter.setOnItemClickListener(onItemClickListener);
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (this.isDismiss) {
            return;
        }
        StringUtils.modifyTextViewDrawable(this.picture_title, this.drawableDown, 2);
        this.isDismiss = true;
        this.recyclerView.startAnimation(this.animationOut);
        dismiss();
        this.animationOut.setAnimationListener(new Animation.AnimationListener() { // from class: com.luck.picture.lib.widget.FolderPopWindow.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                FolderPopWindow.this.isDismiss = false;
                if (Build.VERSION.SDK_INT <= 16) {
                    FolderPopWindow.this.dismiss4Pop();
                } else {
                    FolderPopWindow.super.dismiss();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismiss4Pop() {
        new Handler().post(new Runnable() { // from class: com.luck.picture.lib.widget.FolderPopWindow.2
            @Override // java.lang.Runnable
            public void run() {
                FolderPopWindow.super.dismiss();
            }
        });
    }

    public void notifyDataCheckedStatus(List<LocalMedia> list) {
        try {
            List<LocalMediaFolder> folderData = this.adapter.getFolderData();
            for (LocalMediaFolder localMediaFolder : folderData) {
                localMediaFolder.setCheckedNum(0);
            }
            if (list.size() > 0) {
                for (LocalMediaFolder localMediaFolder2 : folderData) {
                    int i = 0;
                    for (LocalMedia localMedia : localMediaFolder2.getImages()) {
                        String path = localMedia.getPath();
                        for (LocalMedia localMedia2 : list) {
                            if (path.equals(localMedia2.getPath())) {
                                i++;
                                localMediaFolder2.setCheckedNum(i);
                            }
                        }
                    }
                }
            }
            this.adapter.bindFolderData(folderData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.id_ll_root) {
            dismiss();
        }
    }
}
