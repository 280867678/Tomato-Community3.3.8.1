package com.one.tomato.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;

/* loaded from: classes3.dex */
public class ImageDownloadDialog extends BottomSheetDialog {
    private ImageDownloadListener listener;
    private TextView tv_download = (TextView) findViewById(R.id.tv_download);
    private TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);

    /* loaded from: classes3.dex */
    public interface ImageDownloadListener {
        void download();
    }

    public void setImageDownloadListener(ImageDownloadListener imageDownloadListener) {
        this.listener = imageDownloadListener;
    }

    public ImageDownloadDialog(@NonNull Context context) {
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_download_image, (ViewGroup) null));
        this.tv_download.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ImageDownloadDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImageDownloadDialog.this.dismiss();
                if (ImageDownloadDialog.this.listener != null) {
                    ImageDownloadDialog.this.listener.download();
                }
            }
        });
        this.tv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ImageDownloadDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ImageDownloadDialog.this.dismiss();
            }
        });
    }
}
