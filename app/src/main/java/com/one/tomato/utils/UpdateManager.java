package com.one.tomato.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.media.ExifInterface;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.UpdateInfo;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.okhttp.download.DownLoadManager;
import com.one.tomato.mvp.base.okhttp.download.ProgressCallBack;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.File;
import java.util.ArrayList;
import okhttp3.ResponseBody;

/* loaded from: classes3.dex */
public class UpdateManager {
    public static boolean cancelUpdate;
    private static UpdateManager updateManager;
    private ProgressDialog checkProDialog;
    private CustomAlertDialog downloadDialog;
    private String installUrl;
    private ImageView iv_delete;
    private LinearLayout ll_update_desc;
    private Context mContext;
    private ProgressBar progressBar;
    private RelativeLayout rl_replace_app;
    private RelativeLayout rl_update_ing;
    private RelativeLayout rl_upgrade_fail;
    private TextView tv_description;
    private TextView tv_install_tip;
    private TextView tv_progress;
    private TextView tv_re_download;
    private TextView tv_size;
    private TextView tv_update;
    private TextView tv_version;
    private TextView tv_website_download;
    private TextView tv_website_tip;
    private UpdateInfo.VersionListBean updateBean;
    private String websiteUrl;
    private int updateStatus = 1;
    private String apkFilePath = "";
    private String tmpFilePath = "";
    private int curProgress = 0;
    private int preProgress = 0;
    private boolean isDownloading = false;
    private boolean isCheckVersion = false;
    private boolean isShowMsg = false;
    @SuppressLint({"HandlerLeak"})
    private Handler updateHandler = new Handler() { // from class: com.one.tomato.utils.UpdateManager.6
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                UpdateManager.this.progressBar.setProgress(UpdateManager.this.curProgress);
                TextView textView = UpdateManager.this.tv_progress;
                textView.setText(UpdateManager.this.curProgress + "%");
            } else if (i == 2) {
                AppUtil.installApk(UpdateManager.this.apkFilePath);
            } else if (i != 3) {
            } else {
                UpdateManager.this.curProgress = 0;
                UpdateManager.this.preProgress = 0;
                UpdateManager.this.progressBar.setProgress(UpdateManager.this.curProgress);
                TextView textView2 = UpdateManager.this.tv_progress;
                textView2.setText(UpdateManager.this.curProgress + "%");
                UpdateManager.this.showDownloadDialog(3);
            }
        }
    };
    private DialogInterface.OnKeyListener onKey = new DialogInterface.OnKeyListener() { // from class: com.one.tomato.utils.UpdateManager.7
        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i == 4 && keyEvent.getAction() == 0) {
                if (UpdateManager.this.updateStatus == 2 || UpdateManager.this.isDownloading) {
                    UpdateManager.this.destroyApp();
                    return true;
                }
                UpdateManager.this.destroyUpdate();
                return true;
            }
            return true;
        }
    };

    public static UpdateManager getUpdateManager() {
        if (updateManager == null) {
            updateManager = new UpdateManager();
        }
        return updateManager;
    }

    @SuppressLint({"HandlerLeak"})
    public void checkAppUpdate(Context context, boolean z) {
        this.isShowMsg = z;
        if (this.isDownloading) {
            ToastUtil.showCenterToast((int) R.string.update_version_update_downloading);
            return;
        }
        this.mContext = context;
        if (z && !this.isCheckVersion) {
            ProgressDialog progressDialog = this.checkProDialog;
            if (progressDialog == null) {
                this.checkProDialog = ProgressDialog.show(this.mContext, null, AppUtil.getString(R.string.update_version_update_loading), true, true);
                this.checkProDialog.setCanceledOnTouchOutside(false);
                this.checkProDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.one.tomato.utils.UpdateManager.1
                    @Override // android.content.DialogInterface.OnKeyListener
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == 4 && keyEvent.getAction() == 0) {
                            UpdateManager.this.checkProDialog.dismiss();
                            UpdateManager.this.checkProDialog = null;
                            UpdateManager.this.onDestroy();
                            return false;
                        }
                        return false;
                    }
                });
            } else if (progressDialog.isShowing()) {
                return;
            }
        }
        if (this.isCheckVersion) {
            return;
        }
        this.isCheckVersion = true;
        getUpdateInfo();
    }

    private void getUpdateInfo() {
        ApiImplService.Companion.getApiImplService().getUpdateInfo(AppUtil.getVersionCodeStr()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<UpdateInfo>() { // from class: com.one.tomato.utils.UpdateManager.2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(UpdateInfo updateInfo) {
                if (UpdateManager.this.isShowMsg && UpdateManager.this.checkProDialog != null) {
                    UpdateManager.this.checkProDialog.dismiss();
                    UpdateManager.this.checkProDialog = null;
                }
                if (updateInfo == null) {
                    UpdateManager.this.checkFailOrNoUpgrade(true);
                    return;
                }
                UpdateManager.this.updateStatus = updateInfo.getStatus();
                if (UpdateManager.this.updateStatus == 0) {
                    UpdateManager.this.checkFailOrNoUpgrade(false);
                    return;
                }
                ArrayList<UpdateInfo.VersionListBean> versionList = updateInfo.getVersionList();
                if (versionList == null || versionList.size() == 0) {
                    UpdateManager.this.checkFailOrNoUpgrade(true);
                    return;
                }
                UpdateManager.this.updateBean = versionList.get(0);
                if (AppUtil.compareAPKVersion(AppUtil.getVersionName(), UpdateManager.this.updateBean.getVersionId())) {
                    UpdateManager.this.showDownloadDialog(1);
                } else {
                    UpdateManager.this.checkFailOrNoUpgrade(false);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                if (UpdateManager.this.checkProDialog != null) {
                    UpdateManager.this.checkProDialog.dismiss();
                    UpdateManager.this.checkProDialog = null;
                }
                UpdateManager.this.checkFailOrNoUpgrade(true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkFailOrNoUpgrade(boolean z) {
        PreferencesUtil.getInstance().putBoolean("need_upgrade", z);
        if (this.isShowMsg) {
            if (z) {
                ToastUtil.showCenterToast((int) R.string.update_version_update_fail);
            } else {
                ToastUtil.showCenterToast((int) R.string.update_current_new_version);
            }
        }
        onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDownloadDialog(int i) {
        this.isCheckVersion = false;
        PreferencesUtil.getInstance().putBoolean("need_upgrade", true);
        this.websiteUrl = DomainServer.getInstance().getWebsiteUrl();
        this.installUrl = DomainServer.getInstance().getShareUrl() + "/html/androidGuide.html";
        if (this.downloadDialog == null) {
            this.downloadDialog = new CustomAlertDialog(this.mContext);
            this.downloadDialog.setMiddleNeedPadding(false);
            this.downloadDialog.setBackground(null);
            this.downloadDialog.bottomLayoutGone();
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_update, (ViewGroup) null);
            this.downloadDialog.setContentView(inflate);
            this.downloadDialog.setOnKeyListener(this.onKey);
            this.tv_version = (TextView) inflate.findViewById(R.id.tv_version);
            this.tv_size = (TextView) inflate.findViewById(R.id.tv_size);
            this.ll_update_desc = (LinearLayout) inflate.findViewById(R.id.ll_update_desc);
            this.tv_description = (TextView) inflate.findViewById(R.id.tv_description);
            this.tv_update = (TextView) inflate.findViewById(R.id.tv_update);
            this.rl_update_ing = (RelativeLayout) inflate.findViewById(R.id.rl_update_ing);
            this.tv_progress = (TextView) inflate.findViewById(R.id.tv_progress);
            this.progressBar = (ProgressBar) inflate.findViewById(R.id.progressBar);
            this.rl_replace_app = (RelativeLayout) inflate.findViewById(R.id.rl_replace_app);
            this.rl_upgrade_fail = (RelativeLayout) inflate.findViewById(R.id.rl_upgrade_fail);
            this.tv_re_download = (TextView) inflate.findViewById(R.id.tv_re_download);
            this.tv_website_download = (TextView) inflate.findViewById(R.id.tv_website_download);
            this.tv_install_tip = (TextView) inflate.findViewById(R.id.tv_install_tip);
            this.tv_website_tip = (TextView) inflate.findViewById(R.id.tv_website_tip);
            this.iv_delete = (ImageView) inflate.findViewById(R.id.iv_delete);
        }
        TextView textView = this.tv_version;
        textView.setText(ExifInterface.GPS_MEASUREMENT_INTERRUPTED + this.updateBean.getVersionId());
        TextView textView2 = this.tv_size;
        textView2.setText(AppUtil.getString(R.string.uptate_size) + ConstantUtils.PLACEHOLDER_STR_ONE + this.updateBean.getSize() + "M");
        this.tv_install_tip.getPaint().setFlags(8);
        this.tv_install_tip.getPaint().setAntiAlias(true);
        this.tv_website_tip.setText(AppUtil.getString(R.string.update_website_tip, this.websiteUrl));
        if (i == 1) {
            this.ll_update_desc.setVisibility(0);
            this.rl_update_ing.setVisibility(8);
            this.rl_replace_app.setVisibility(8);
            this.rl_upgrade_fail.setVisibility(8);
            this.tv_description.setMovementMethod(ScrollingMovementMethod.getInstance());
            this.tv_description.setText(this.updateBean.getDescription());
            int i2 = this.updateStatus;
            if (i2 == 2) {
                this.iv_delete.setVisibility(8);
            } else if (i2 == 1) {
                this.iv_delete.setVisibility(0);
            }
        } else if (i == 2) {
            this.ll_update_desc.setVisibility(8);
            this.rl_update_ing.setVisibility(0);
            this.rl_replace_app.setVisibility(8);
            this.rl_upgrade_fail.setVisibility(8);
            if ("1".equals(this.updateBean.getChangId())) {
                this.rl_replace_app.setVisibility(0);
            } else {
                this.rl_replace_app.setVisibility(8);
            }
            this.iv_delete.setVisibility(8);
        } else if (i == 3) {
            this.ll_update_desc.setVisibility(8);
            this.rl_update_ing.setVisibility(8);
            this.rl_replace_app.setVisibility(8);
            this.rl_upgrade_fail.setVisibility(0);
            int i3 = this.updateStatus;
            if (i3 == 2) {
                this.iv_delete.setVisibility(8);
            } else if (i3 == 1) {
                this.iv_delete.setVisibility(0);
            }
        }
        this.tv_update.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.utils.-$$Lambda$UpdateManager$cCKAZnywbBlLup9M-vGip3zChf4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateManager.this.lambda$showDownloadDialog$0$UpdateManager(view);
            }
        });
        this.iv_delete.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.utils.-$$Lambda$UpdateManager$gTDo1eUuaR7XD7x1LDDEHO5Smkw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateManager.this.lambda$showDownloadDialog$1$UpdateManager(view);
            }
        });
        this.tv_re_download.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.utils.-$$Lambda$UpdateManager$SyCzXKa2cYm-ocuZW63NbgiG_os
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateManager.this.lambda$showDownloadDialog$2$UpdateManager(view);
            }
        });
        this.tv_website_download.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.utils.-$$Lambda$UpdateManager$xNLuCOK2bsJOOF1gZoLlDwX1YRE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateManager.this.lambda$showDownloadDialog$3$UpdateManager(view);
            }
        });
        this.tv_install_tip.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.utils.-$$Lambda$UpdateManager$CEuiK498WZH5F-q1BSSm0Aljr-o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateManager.this.lambda$showDownloadDialog$4$UpdateManager(view);
            }
        });
        this.tv_website_tip.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.utils.-$$Lambda$UpdateManager$hD3744SbwfDOG4pT6g2AKnmn2_o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpdateManager.this.lambda$showDownloadDialog$5$UpdateManager(view);
            }
        });
    }

    public /* synthetic */ void lambda$showDownloadDialog$0$UpdateManager(View view) {
        if (DeviceInfoUtil.isOverMarshmallow() && ContextCompat.checkSelfPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            showMissingPermissionDialog();
        } else if (!createFilePath()) {
        } else {
            downloadApk();
            showDownloadDialog(2);
        }
    }

    public /* synthetic */ void lambda$showDownloadDialog$1$UpdateManager(View view) {
        destroyUpdate();
        cancelUpdate = true;
    }

    public /* synthetic */ void lambda$showDownloadDialog$2$UpdateManager(View view) {
        downloadApk();
        showDownloadDialog(2);
    }

    public /* synthetic */ void lambda$showDownloadDialog$3$UpdateManager(View view) {
        AppUtil.startBrowseView(this.websiteUrl);
    }

    public /* synthetic */ void lambda$showDownloadDialog$4$UpdateManager(View view) {
        AppUtil.startBrowseView(this.installUrl);
    }

    public /* synthetic */ void lambda$showDownloadDialog$5$UpdateManager(View view) {
        AppUtil.startBrowseView(this.websiteUrl);
    }

    public void showMissingPermissionDialog() {
        String string = this.mContext.getResources().getString(R.string.update_storage_permission_tip);
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this.mContext);
        customAlertDialog.bottomButtonVisiblity(0);
        customAlertDialog.setMessage(string);
        customAlertDialog.setConfirmButton(R.string.common_setting, new View.OnClickListener(this) { // from class: com.one.tomato.utils.UpdateManager.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                customAlertDialog.dismiss();
                AppUtil.startAppSetting();
            }
        });
        customAlertDialog.setCancelButton(R.string.common_cancel, new View.OnClickListener(this) { // from class: com.one.tomato.utils.UpdateManager.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                customAlertDialog.dismiss();
            }
        });
    }

    private boolean createFilePath() {
        String str = "tomato_" + this.updateBean.getVersionId() + ".apk";
        String str2 = "tomato_" + this.updateBean.getVersionId() + ".tmp";
        if (FileUtil.hasSD()) {
            File apkCacheDir = FileUtil.getApkCacheDir();
            if (!apkCacheDir.exists() && !apkCacheDir.mkdirs()) {
                this.updateHandler.sendEmptyMessage(3);
                return false;
            }
            this.apkFilePath = FileUtil.getApkCacheDir().getAbsolutePath() + File.separator + str;
            this.tmpFilePath = FileUtil.getApkCacheDir().getAbsolutePath() + File.separator + str2;
        }
        if (TextUtils.isEmpty(this.apkFilePath)) {
            this.updateHandler.sendEmptyMessage(3);
            return false;
        }
        return true;
    }

    private void downloadApk() {
        this.isDownloading = true;
        String absolutePath = FileUtil.getApkCacheDir().getAbsolutePath();
        final File file = new File(this.apkFilePath);
        final File file2 = new File(this.tmpFilePath);
        DownLoadManager.getInstance().load(this.updateBean.getUrl(), new ProgressCallBack<ResponseBody>(absolutePath, "tomato_" + this.updateBean.getVersionId() + ".tmp") { // from class: com.one.tomato.utils.UpdateManager.5
            @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
            public void onSuccess(ResponseBody responseBody) {
                File file3 = file2;
                if (file3 != null && file3.length() == responseBody.contentLength()) {
                    if (file2.renameTo(file)) {
                        UpdateManager.this.updateHandler.sendEmptyMessage(2);
                        LogUtil.m3784i("下载成功");
                    }
                } else {
                    LogUtil.m3786e("下载文件不完整");
                    UpdateManager.this.updateHandler.sendEmptyMessage(3);
                }
                UpdateManager.this.isDownloading = false;
            }

            @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
            public void progress(long j, long j2) {
                long size = UpdateManager.this.updateBean.getSize() * 1024 * 1024;
                UpdateManager.this.curProgress = (int) (((j * 1.0d) / size) * 100.0d);
                LogUtil.m3784i("升级过程中下载：\nprogress = " + j + "\ntotal = " + size + "\ncurProgress = " + UpdateManager.this.curProgress + "\npreProgress = " + UpdateManager.this.preProgress);
                if (UpdateManager.this.curProgress - UpdateManager.this.preProgress > 0.5d) {
                    UpdateManager updateManager2 = UpdateManager.this;
                    updateManager2.preProgress = updateManager2.curProgress;
                    UpdateManager.this.updateHandler.sendEmptyMessage(1);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
            public void onError(Throwable th) {
                UpdateManager.this.updateHandler.sendEmptyMessage(3);
                UpdateManager.this.isDownloading = false;
                LogUtil.m3784i("下载异常");
            }
        });
    }

    public void onDestroy() {
        this.mContext = null;
        this.isDownloading = false;
        this.isCheckVersion = false;
        this.updateHandler = null;
        updateManager = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyUpdate() {
        CustomAlertDialog customAlertDialog = this.downloadDialog;
        if (customAlertDialog != null) {
            customAlertDialog.dismiss();
        }
        onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyApp() {
        CustomAlertDialog customAlertDialog = this.downloadDialog;
        if (customAlertDialog != null) {
            customAlertDialog.dismiss();
        }
        onDestroy();
        AppManager.getAppManager().exitApp();
    }
}
