package com.gen.p059mh.webapp_extensions.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewCompat;
import android.support.p005v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.plugins.Open2Plugin;
import com.gen.p059mh.webapp_extensions.plugins.PickImagePlugin;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;
import com.gen.p059mh.webapp_extensions.views.dialog.SwitchPhotoDialog;
import com.gen.p059mh.webapps.listener.PhotoSwitchListener;
import com.gen.p059mh.webapps.utils.SoftKeyBoardListener;
import com.gen.p059mh.webapps.utils.Utils;
import com.gen.p059mh.webapps.views.DefaultWebChromeClient;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.HashMap;

/* renamed from: com.gen.mh.webapp_extensions.activities.OpenWebActivity */
/* loaded from: classes2.dex */
public class OpenWebActivity extends AppCompatActivity {
    DefaultWebChromeClient chromeClient;
    FrameLayout frameLayout;
    ImageView ivOpen;
    View line;
    LinearLayout navigationBar;
    String themeColor = "#5489E0";
    TextView tvTitle;
    WebView webView;
    private int webappOpenerId;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.frameLayout = new FrameLayout(this);
        setContentView(this.frameLayout);
        initLayoutView();
        intConfig();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void intConfig() {
        char c;
        if (getIntent().hasExtra("open_option")) {
            HashMap hashMap = (HashMap) getIntent().getSerializableExtra("open_option");
            if (hashMap != null && hashMap.containsKey("context") && "fullscreen".equals(hashMap.get("context").toString()) && this.navigationBar != null) {
                WebView webView = this.webView;
                if (webView != null) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) webView.getLayoutParams();
                    layoutParams.topMargin = 0;
                    this.webView.setLayoutParams(layoutParams);
                }
                this.navigationBar.setVisibility(8);
            }
            if (hashMap != null && hashMap.containsKey("nav-bar")) {
                String obj = hashMap.get("nav-bar").toString();
                int hashCode = obj.hashCode();
                if (hashCode == -1726194350) {
                    if (obj.equals("transparent")) {
                        c = 2;
                        if (c != 0) {
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                } else if (hashCode != 93818879) {
                    if (hashCode == 113101865 && obj.equals("white")) {
                        c = 0;
                        if (c != 0) {
                            this.navigationBar.setBackgroundColor(-1);
                        } else if (c == 1) {
                            this.navigationBar.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                        } else if (c == 2) {
                            transparentNavBar();
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                } else {
                    if (obj.equals("black")) {
                        c = 1;
                        if (c != 0) {
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                }
            }
        }
        if (getIntent().getBooleanExtra("fitSystemWindow", false)) {
            this.frameLayout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        }
    }

    public void transparentNavBar() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.webView.getLayoutParams();
        layoutParams.topMargin = 0;
        this.webView.setLayoutParams(layoutParams);
        this.navigationBar.setBackgroundColor(0);
        this.tvTitle.setVisibility(8);
        this.line.setVisibility(8);
        this.ivOpen.setVisibility(8);
    }

    private void initLayoutView() {
        Window window = getWindow();
        new SoftKeyBoardListener(this).addListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.1
            @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardShow(int i) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) OpenWebActivity.this.webView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, i);
                OpenWebActivity.this.webView.setLayoutParams(layoutParams);
            }

            @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardHide(int i) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) OpenWebActivity.this.webView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                OpenWebActivity.this.webView.setLayoutParams(layoutParams);
            }
        });
        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(67108864);
        }
        ViewGroup.LayoutParams layoutParams = this.frameLayout.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -1);
        } else {
            layoutParams.width = -1;
            layoutParams.height = -1;
        }
        this.frameLayout.setLayoutParams(layoutParams);
        this.navigationBar = new LinearLayout(this);
        this.navigationBar.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.navigationBar.setOrientation(1);
        View view = new View(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) DeviceUtils.dpToPixel(this, 26.0f)));
        this.navigationBar.addView(view);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) DeviceUtils.dpToPixel(this, 46.0f)));
        relativeLayout.setPadding(20, 0, 20, 0);
        this.navigationBar.addView(relativeLayout);
        ImageView imageView = new ImageView(this);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageDrawable(Utils.tintDrawable(ContextCompat.getDrawable(this, R$mipmap.icon_player_pop_cancel), ColorStateList.valueOf(Color.parseColor(this.themeColor))));
        relativeLayout.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                OpenWebActivity.this.unload();
                OpenWebActivity.this.onBackPressed();
            }
        });
        this.tvTitle = new TextView(this);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(13);
        this.tvTitle.setLayoutParams(layoutParams3);
        this.tvTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        this.tvTitle.setText(getIntent().getStringExtra("open_url"));
        this.tvTitle.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.tvTitle.setTextSize(14.0f);
        relativeLayout.addView(this.tvTitle);
        this.ivOpen = new ImageView(this);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams4.addRule(11);
        layoutParams4.addRule(15);
        this.ivOpen.setLayoutParams(layoutParams4);
        this.ivOpen.setImageDrawable(Utils.tintDrawable(ContextCompat.getDrawable(this, R$mipmap.share), ColorStateList.valueOf(Color.parseColor(this.themeColor))));
        relativeLayout.addView(this.ivOpen);
        this.ivOpen.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(OpenWebActivity.this.getIntent().getStringExtra("open_url")));
                OpenWebActivity.this.startActivity(intent);
            }
        });
        this.line = new View(this);
        this.line.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) DeviceUtils.dpToPixel(this, 1.0f)));
        this.line.setBackgroundColor(Color.parseColor("#f1f1f1"));
        this.navigationBar.addView(this.line);
        this.webView = new WebView(this);
        this.webView.setScrollContainer(false);
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams5.topMargin = (int) DeviceUtils.dpToPixel(this, 73.0f);
        this.webView.setLayoutParams(layoutParams5);
        this.frameLayout.addView(this.webView);
        this.frameLayout.addView(this.navigationBar);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.getSettings().setGeolocationEnabled(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= 16) {
            this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
            this.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        this.webView.getSettings().setAllowContentAccess(true);
        this.webView.requestFocus(130);
        this.chromeClient = new DefaultWebChromeClient(this) { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.4
            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
                OpenWebActivity openWebActivity = OpenWebActivity.this;
                if (openWebActivity == null) {
                    return false;
                }
                new AlertDialog.Builder(openWebActivity).setMessage(str2).setPositiveButton("YES", new DialogInterface.OnClickListener(this) { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.4.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        jsResult.confirm();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener(this) { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.4.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        jsResult.cancel();
                    }
                }).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener(this) { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.4.1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public void onCancel(DialogInterface dialogInterface) {
                        jsResult.cancel();
                    }
                }).create().show();
                return true;
            }

            @Override // com.gen.p059mh.webapps.views.DefaultWebChromeClient
            public void switchPhotoOrAlbum(PhotoSwitchListener photoSwitchListener) {
                new SwitchPhotoDialog(OpenWebActivity.this, photoSwitchListener).show();
            }

            @Override // com.gen.p059mh.webapps.views.DefaultWebChromeClient
            public void startActivity(Intent intent, int i, PhotoSwitchListener photoSwitchListener) {
                OpenWebActivity.this.checkPermissionAndStart(intent, i, photoSwitchListener);
            }

            @Override // com.gen.p059mh.webapps.views.DefaultWebChromeClient, android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
                TextView textView = OpenWebActivity.this.tvTitle;
                if (textView == null || str == null) {
                    return;
                }
                textView.setText(str);
            }
        };
        this.webView.setWebChromeClient(this.chromeClient);
        this.webView.setWebViewClient(new WebViewClient(this) { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.5
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                return super.shouldOverrideUrlLoading(webView, str);
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                return super.shouldOverrideUrlLoading(webView, webResourceRequest);
            }
        });
        this.webappOpenerId = getIntent().getIntExtra("webapp_opener_id", -1);
        this.webView.loadUrl(getIntent().getStringExtra("open_url"));
    }

    public void checkPermissionAndStart(final Intent intent, final int i, final PhotoSwitchListener photoSwitchListener) {
        new RxPermissions(this).request(PickImagePlugin.CAMERA).subscribe(new Observer<Boolean>() { // from class: com.gen.mh.webapp_extensions.activities.OpenWebActivity.6
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                if (bool.booleanValue()) {
                    OpenWebActivity.this.startActivityForResult(intent, i);
                    return;
                }
                PhotoSwitchListener photoSwitchListener2 = photoSwitchListener;
                if (photoSwitchListener2 == null) {
                    return;
                }
                photoSwitchListener2.cancel();
            }
        });
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 36865 || i == 36866) {
            this.chromeClient.onActivityResult(i, i2, intent);
        } else {
            super.onActivityResult(i, i2, intent);
        }
    }

    public void unload() {
        int i = this.webappOpenerId;
        if (i == -1 || !Open2Plugin.openerMap.containsKey(Integer.valueOf(i))) {
            return;
        }
        Open2Plugin.openerMap.remove(Integer.valueOf(this.webappOpenerId));
        HashMap hashMap = new HashMap();
        hashMap.put("type", "unload");
        hashMap.put("from", "subwindow");
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(this.webappOpenerId));
        Open2Plugin.openerMap.get(Integer.valueOf(this.webappOpenerId)).executor.executeEvent("open2.event", hashMap, null);
    }
}
