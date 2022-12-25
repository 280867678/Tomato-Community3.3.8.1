package com.gen.p059mh.webapps.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.p002v4.content.FileProvider;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.gen.p059mh.webapps.listener.PhotoSwitchListener;
import com.gen.p059mh.webapps.listener.WebViewClientLoadListener;
import com.gen.p059mh.webapps.utils.Logger;
import com.iceteck.silicompressorr.FileUtils;
import java.io.File;
import java.util.UUID;

/* renamed from: com.gen.mh.webapps.views.DefaultWebChromeClient */
/* loaded from: classes2.dex */
public abstract class DefaultWebChromeClient extends WebChromeClient implements PhotoSwitchListener {
    public static final int CAMERA_REQUEST_CODE = 36866;
    public static final int CHOOSE_REQUEST_CODE = 36865;
    private Context mContext;
    PhotoSwitchListener photoSwitchListener = this;
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
    Uri uri;
    WebViewClientLoadListener webViewClientLoadListener;

    public abstract void startActivity(Intent intent, int i, PhotoSwitchListener photoSwitchListener);

    public abstract void switchPhotoOrAlbum(PhotoSwitchListener photoSwitchListener);

    public void setWebViewClientLoadListener(WebViewClientLoadListener webViewClientLoadListener) {
        this.webViewClientLoadListener = webViewClientLoadListener;
    }

    public DefaultWebChromeClient(Context context) {
        this.mContext = context;
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
        WebViewClientLoadListener webViewClientLoadListener = this.webViewClientLoadListener;
        if (webViewClientLoadListener == null) {
            return;
        }
        if (i == 100) {
            webViewClientLoadListener.show(false);
        } else {
            webViewClientLoadListener.show(true);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView webView, String str) {
        super.onReceivedTitle(webView, str);
        WebViewClientLoadListener webViewClientLoadListener = this.webViewClientLoadListener;
        if (webViewClientLoadListener == null) {
            return;
        }
        webViewClientLoadListener.receiveTitle(str);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str) {
        this.uploadFile = this.uploadFile;
        switchPhotoOrAlbum(this.photoSwitchListener);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        this.uploadFile = this.uploadFile;
        switchPhotoOrAlbum(this.photoSwitchListener);
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
        this.uploadFile = this.uploadFile;
        switchPhotoOrAlbum(this.photoSwitchListener);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        Logger.m4114e("onShowFileChooser", "onShowFileChooser");
        this.uploadFiles = valueCallback;
        switchPhotoOrAlbum(this.photoSwitchListener);
        return true;
    }

    private void openFileChooseProcess() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(FileUtils.MIME_TYPE_IMAGE);
        startActivity(intent, CHOOSE_REQUEST_CODE, this);
    }

    private void takePhoto() {
        String str = UUID.randomUUID() + ".jpg";
        File file = new File(Environment.getExternalStorageDirectory() + "/web_app");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        this.uri = Uri.fromFile(file2);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (Build.VERSION.SDK_INT >= 24) {
            String packageName = this.mContext.getPackageName();
            this.uri = FileProvider.getUriForFile(this.mContext, packageName + ".websdk.fileprovider", file2);
            intent.addFlags(1);
        }
        intent.putExtra("output", this.uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivity(intent, CAMERA_REQUEST_CODE, this);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            if (i2 != 0) {
                return;
            }
            ValueCallback<Uri> valueCallback = this.uploadFile;
            if (valueCallback != null) {
                valueCallback.onReceiveValue(null);
                this.uploadFile = null;
            }
            ValueCallback<Uri[]> valueCallback2 = this.uploadFiles;
            if (valueCallback2 == null) {
                return;
            }
            valueCallback2.onReceiveValue(null);
            this.uploadFiles = null;
            return;
        }
        switch (i) {
            case CHOOSE_REQUEST_CODE /* 36865 */:
                if (this.uploadFile != null) {
                    this.uploadFile.onReceiveValue((intent == null || i2 != -1) ? null : intent.getData());
                    this.uploadFile = null;
                }
                if (this.uploadFiles == null) {
                    return;
                }
                this.uploadFiles.onReceiveValue(new Uri[]{(intent == null || i2 != -1) ? null : intent.getData()});
                this.uploadFiles = null;
                return;
            case CAMERA_REQUEST_CODE /* 36866 */:
                ValueCallback<Uri> valueCallback3 = this.uploadFile;
                if (valueCallback3 != null) {
                    valueCallback3.onReceiveValue(this.uri);
                }
                ValueCallback<Uri[]> valueCallback4 = this.uploadFiles;
                if (valueCallback4 == null) {
                    return;
                }
                valueCallback4.onReceiveValue(new Uri[]{this.uri});
                return;
            default:
                return;
        }
    }

    @Override // com.gen.p059mh.webapps.listener.PhotoSwitchListener
    public void onCamera() {
        takePhoto();
    }

    @Override // com.gen.p059mh.webapps.listener.PhotoSwitchListener
    public void onAlbum() {
        openFileChooseProcess();
    }

    @Override // com.gen.p059mh.webapps.listener.PhotoSwitchListener
    public void cancel() {
        ValueCallback<Uri> valueCallback = this.uploadFile;
        if (valueCallback != null) {
            valueCallback.onReceiveValue(null);
            this.uploadFile = null;
        }
        ValueCallback<Uri[]> valueCallback2 = this.uploadFiles;
        if (valueCallback2 != null) {
            valueCallback2.onReceiveValue(null);
            this.uploadFiles = null;
        }
    }
}
