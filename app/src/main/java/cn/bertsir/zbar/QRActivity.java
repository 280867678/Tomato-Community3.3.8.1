package cn.bertsir.zbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.p002v4.util.Pair;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.bertsir.zbar.QRActivity;
import cn.bertsir.zbar.format_control.BarCodeUtil;
import cn.bertsir.zbar.p036Qr.ScanResult;
import cn.bertsir.zbar.p036Qr.Symbol;
import cn.bertsir.zbar.utils.GetPathFromUri;
import cn.bertsir.zbar.utils.QRUtils;
import cn.bertsir.zbar.view.ScanView;
import cn.bertsir.zbar.view.VerticalSeekBar;
import com.gen.p059mh.webapp_extensions.R$color;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$style;
import com.gyf.immersionbar.ImmersionBar;
import com.iceteck.silicompressorr.FileUtils;
import com.soundcloud.android.crop.Crop;
import java.io.File;

/* loaded from: classes2.dex */
public class QRActivity extends Activity implements View.OnClickListener {
    static final int REQUEST_IMAGE_GET = 1;
    static final int REQUEST_PHOTO_CUT = 2;
    private static final String TAG = "QRActivity";

    /* renamed from: cp */
    private CameraPreview f730cp;
    private FrameLayout fl_title;
    Handler handler;
    private ImageView iv_album;
    private ImageView iv_flash;
    private ImageView mo_scanner_back;
    private QrConfig options;
    private AlertDialog progressDialog;
    private SoundPool soundPool;

    /* renamed from: sv */
    private ScanView f731sv;
    private TextView textDialog;
    private TextView tv_des;
    private TextView tv_title;
    private Uri uriCropFile;
    private VerticalSeekBar vsb_zoom;
    private String cropTempPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "cropQr.jpg";
    private float oldDist = 1.0f;
    private ScanCallback resultCallback = new ScanCallback() { // from class: cn.bertsir.zbar.QRActivity.2
        @Override // cn.bertsir.zbar.ScanCallback
        public void onScanResult(ScanResult scanResult) {
            if (QRActivity.this.options.isPlay_sound()) {
                QRActivity.this.soundPool.play(1, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            if (QRActivity.this.f730cp != null) {
                QRActivity.this.f730cp.setFlash(false);
            }
            QrManager.getInstance().getResultCallback().onScanSuccess(scanResult);
            if (!Symbol.looperScan) {
                QRActivity.this.finish();
            }
        }
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ImmersionBar with = ImmersionBar.with(this);
        with.statusBarDarkFont(true, 0.2f);
        with.init();
        this.handler = new Handler(Looper.getMainLooper());
        this.options = (QrConfig) getIntent().getExtras().get(QrConfig.EXTRA_THIS_CONFIG);
        int screen_orientation = this.options.getSCREEN_ORIENTATION();
        if (screen_orientation != 1) {
            if (screen_orientation != 2) {
                if (screen_orientation == 3) {
                    setRequestedOrientation(4);
                } else {
                    setRequestedOrientation(1);
                }
            } else if (getResources().getConfiguration().orientation == 1) {
                setRequestedOrientation(0);
            }
        } else if (getResources().getConfiguration().orientation == 2) {
            setRequestedOrientation(1);
        }
        Symbol.scanType = this.options.getScan_type();
        Symbol.scanFormat = this.options.getCustomBarcodeFormatList();
        Symbol.is_only_scan_center = this.options.isOnly_center();
        Symbol.is_auto_zoom = this.options.isAuto_zoom();
        Symbol.doubleEngine = this.options.isDouble_engine();
        Symbol.looperScan = this.options.isLoop_scan();
        Symbol.looperWaitTime = this.options.getLoop_wait_time();
        Symbol.screenWidth = QRUtils.getInstance().getScreenWidth(this);
        Symbol.screenHeight = QRUtils.getInstance().getScreenHeight(this);
        setContentView(R$layout.activity_web_sdk_qr);
        initView();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        CameraPreview cameraPreview = this.f730cp;
        if (cameraPreview != null) {
            cameraPreview.setScanCallback(this.resultCallback);
            this.f730cp.start();
        }
        this.f731sv.onResume();
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        QrManager.getInstance().getResultCallback().onFailure(new Exception("user canceled"));
    }

    private void initView() {
        this.f730cp = (CameraPreview) findViewById(R$id.f1285cp);
        this.soundPool = new SoundPool(10, 1, 5);
        this.soundPool.load(this, QrConfig.getDing_path(), 1);
        this.f731sv = (ScanView) findViewById(R$id.f1287sv);
        this.f731sv.setType(this.options.getScan_view_type());
        this.f731sv.startScan();
        this.mo_scanner_back = (ImageView) findViewById(R$id.mo_scanner_back);
        this.mo_scanner_back.setOnClickListener(this);
        this.iv_flash = (ImageView) findViewById(R$id.iv_flash);
        this.iv_flash.setOnClickListener(this);
        this.iv_album = (ImageView) findViewById(R$id.iv_album);
        this.iv_album.setOnClickListener(this);
        this.tv_title = (TextView) findViewById(R$id.tv_title);
        this.fl_title = (FrameLayout) findViewById(R$id.fl_title);
        this.tv_des = (TextView) findViewById(R$id.tv_des);
        this.vsb_zoom = (VerticalSeekBar) findViewById(R$id.vsb_zoom);
        int i = 0;
        this.iv_album.setVisibility(this.options.isShow_light() ? 0 : 8);
        this.fl_title.setVisibility(this.options.isShow_title() ? 0 : 8);
        this.iv_flash.setVisibility(this.options.isShow_light() ? 0 : 8);
        this.iv_album.setVisibility(this.options.isShow_album() ? 0 : 8);
        this.tv_des.setVisibility(this.options.isShow_des() ? 0 : 8);
        VerticalSeekBar verticalSeekBar = this.vsb_zoom;
        if (!this.options.isShow_zoom()) {
            i = 8;
        }
        verticalSeekBar.setVisibility(i);
        this.tv_des.setText(this.options.getDes_text());
        this.tv_title.setText(this.options.getTitle_text());
        this.fl_title.setBackgroundColor(this.options.getTITLE_BACKGROUND_COLOR());
        this.tv_title.setTextColor(this.options.getTITLE_TEXT_COLOR());
        this.f731sv.setCornerColor(this.options.getCORNER_COLOR());
        this.f731sv.setLineSpeed(this.options.getLine_speed());
        this.f731sv.setLineColor(this.options.getLINE_COLOR());
        if (Build.VERSION.SDK_INT >= 16) {
            setSeekBarColor(this.vsb_zoom, this.options.getCORNER_COLOR());
        }
        this.vsb_zoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: cn.bertsir.zbar.QRActivity.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                QRActivity.this.f730cp.setZoom(i2 / 100.0f);
            }
        });
    }

    @RequiresApi(api = 16)
    public void setSeekBarColor(SeekBar seekBar, int i) {
        seekBar.getThumb().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        seekBar.getProgressDrawable().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        CameraPreview cameraPreview = this.f730cp;
        if (cameraPreview != null) {
            cameraPreview.setFlash(false);
            this.f730cp.stop();
        }
        this.soundPool.release();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        CameraPreview cameraPreview = this.f730cp;
        if (cameraPreview != null) {
            cameraPreview.stop();
        }
        this.f731sv.onPause();
    }

    private void fromAlbum() {
        if (QRUtils.getInstance().isMIUI()) {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, FileUtils.MIME_TYPE_IMAGE);
            startActivityForResult(Intent.createChooser(intent, this.options.getOpen_album_text()), 1);
            return;
        }
        Intent intent2 = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent2.setAction("android.intent.action.GET_CONTENT");
            intent2.setType(FileUtils.MIME_TYPE_IMAGE);
        } else {
            intent2.setAction("android.intent.action.OPEN_DOCUMENT");
            intent2.addCategory("android.intent.category.OPENABLE");
            intent2.setType(FileUtils.MIME_TYPE_IMAGE);
        }
        startActivityForResult(Intent.createChooser(intent2, this.options.getOpen_album_text()), 1);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.iv_album) {
            fromAlbum();
        } else if (view.getId() == R$id.iv_flash) {
            CameraPreview cameraPreview = this.f730cp;
            if (cameraPreview == null) {
                return;
            }
            cameraPreview.setFlash();
        } else if (view.getId() != R$id.mo_scanner_back) {
        } else {
            QrManager.getInstance().getResultCallback().onFailure(new Exception("user canceled"));
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            if (i != 1) {
                if (i == 6709) {
                    recognitionLocation(this.uriCropFile);
                }
            } else if (this.options.isNeed_crop()) {
                cropPhoto(intent.getData());
            } else {
                recognitionLocation(intent.getData());
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    private void recognitionLocation(Uri uri) {
        String path = GetPathFromUri.getPath(this, uri);
        this.textDialog = showProgressDialog();
        this.textDialog.setText("请稍后...");
        new Thread(new RunnableC08283(path)).start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: cn.bertsir.zbar.QRActivity$3 */
    /* loaded from: classes2.dex */
    public class RunnableC08283 implements Runnable {
        final /* synthetic */ String val$imagePath;

        RunnableC08283(String str) {
            this.val$imagePath = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (TextUtils.isEmpty(this.val$imagePath)) {
                    QRActivity.this.handler.post(new Runnable() { // from class: cn.bertsir.zbar.-$$Lambda$QRActivity$3$lEzk5l5X8lglKwzBA_LVoROOcSw
                        @Override // java.lang.Runnable
                        public final void run() {
                            QRActivity.RunnableC08283.this.lambda$run$0$QRActivity$3();
                        }
                    });
                    return;
                }
                final Pair<String, Integer> decodeQRcodeDetail = QRUtils.getInstance().decodeQRcodeDetail(this.val$imagePath);
                final String str = decodeQRcodeDetail.first;
                QRActivity.this.runOnUiThread(new Runnable() { // from class: cn.bertsir.zbar.QRActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!TextUtils.isEmpty(str)) {
                            QRActivity.this.closeProgressDialog();
                            ScanResult scanResult = new ScanResult();
                            scanResult.setResult(str);
                            scanResult.setScanType(BarCodeUtil.getScanTypeNameFromSymbolType(((Integer) decodeQRcodeDetail.second).intValue()));
                            QrManager.getInstance().getResultCallback().onScanSuccess(scanResult);
                            QRActivity qRActivity = QRActivity.this;
                            qRActivity.delete(qRActivity.cropTempPath);
                            QRActivity.this.finish();
                            return;
                        }
                        String decodeQRcodeByZxing = QRUtils.getInstance().decodeQRcodeByZxing(RunnableC08283.this.val$imagePath);
                        if (!TextUtils.isEmpty(decodeQRcodeByZxing)) {
                            QRActivity.this.closeProgressDialog();
                            ScanResult scanResult2 = new ScanResult();
                            scanResult2.setResult(decodeQRcodeByZxing);
                            scanResult2.setScanType("QRCODE");
                            QrManager.getInstance().getResultCallback().onScanSuccess(scanResult2);
                            QRActivity qRActivity2 = QRActivity.this;
                            qRActivity2.delete(qRActivity2.cropTempPath);
                            QRActivity.this.finish();
                            return;
                        }
                        try {
                            Pair<String, Integer> decodeBarcodeDetail = QRUtils.getInstance().decodeBarcodeDetail(RunnableC08283.this.val$imagePath);
                            String str2 = decodeBarcodeDetail.first;
                            if (!TextUtils.isEmpty(str2)) {
                                QRActivity.this.closeProgressDialog();
                                ScanResult scanResult3 = new ScanResult();
                                scanResult3.setResult(str2);
                                scanResult3.setScanType(BarCodeUtil.getScanTypeNameFromSymbolType(decodeBarcodeDetail.second.intValue()));
                                QrManager.getInstance().getResultCallback().onScanSuccess(scanResult3);
                                QRActivity.this.delete(QRActivity.this.cropTempPath);
                                QRActivity.this.finish();
                            } else {
                                Toast.makeText(QRActivity.this.getApplicationContext(), "识别失败！", 0).show();
                                QRActivity.this.closeProgressDialog();
                            }
                        } catch (Exception e) {
                            Toast.makeText(QRActivity.this.getApplicationContext(), "识别异常！", 0).show();
                            QRActivity.this.closeProgressDialog();
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                QRActivity.this.handler.post(new Runnable() { // from class: cn.bertsir.zbar.-$$Lambda$QRActivity$3$iFgKMMlMoxig9l93OMloNmCndOw
                    @Override // java.lang.Runnable
                    public final void run() {
                        QRActivity.RunnableC08283.this.lambda$run$1$QRActivity$3(e);
                    }
                });
            }
        }

        public /* synthetic */ void lambda$run$0$QRActivity$3() {
            Toast.makeText(QRActivity.this.getApplicationContext(), "获取图片失败！", 0).show();
            QrManager.getInstance().getResultCallback().onFailure(new RuntimeException("imagePath is null"));
            QRActivity.this.closeProgressDialog();
        }

        public /* synthetic */ void lambda$run$1$QRActivity$3(Exception exc) {
            Toast.makeText(QRActivity.this.getApplicationContext(), "识别异常！", 0).show();
            QRActivity.this.closeProgressDialog();
            QrManager.getInstance().getResultCallback().onFailure(exc);
        }
    }

    public void cropPhoto(Uri uri) {
        this.uriCropFile = Uri.parse("file:///" + this.cropTempPath);
        Crop m3671of = Crop.m3671of(uri, this.uriCropFile);
        m3671of.asSquare();
        m3671of.start(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean delete(String str) {
        File file = new File(str);
        return file.exists() && file.isFile() && file.delete();
    }

    public TextView showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R$style.SDK_AlertDialogStyle);
        builder.setCancelable(false);
        View inflate = View.inflate(this, R$layout.web_sdk_dialog_loading, null);
        builder.setView(inflate);
        ProgressBar progressBar = (ProgressBar) inflate.findViewById(R$id.pb_loading);
        TextView textView = (TextView) inflate.findViewById(R$id.tv_hint);
        if (Build.VERSION.SDK_INT >= 23) {
            progressBar.setIndeterminateTintList(getColorStateList(R$color.dialog_pro_color));
        }
        this.progressDialog = builder.create();
        this.progressDialog.show();
        return textView;
    }

    public void closeProgressDialog() {
        try {
            if (this.progressDialog == null) {
                return;
            }
            this.progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.options.isFinger_zoom()) {
            int action = motionEvent.getAction() & 255;
            if (action != 2) {
                if (action == 5) {
                    this.oldDist = QRUtils.getInstance().getFingerSpacing(motionEvent);
                }
            } else if (motionEvent.getPointerCount() == 2) {
                float fingerSpacing = QRUtils.getInstance().getFingerSpacing(motionEvent);
                float f = this.oldDist;
                if (fingerSpacing > f) {
                    this.f730cp.handleZoom(true);
                } else if (fingerSpacing < f) {
                    this.f730cp.handleZoom(false);
                }
                this.oldDist = fingerSpacing;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
