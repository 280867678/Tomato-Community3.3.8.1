package com.gen.p059mh.webapp_extensions.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.p002v4.app.ActivityCompat;
import android.support.p002v4.app.DialogFragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.app.ActionBar;
import android.support.p005v7.app.AlertDialog;
import android.support.p005v7.app.AppCompatActivity;
import android.support.p005v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.R$drawable;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$menu;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.AspectRatio;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.AspectRatioFragment;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraView;
import com.gen.p059mh.webapps.utils.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: com.gen.mh.webapp_extensions.activities.TakePhotoActivity */
/* loaded from: classes2.dex */
public class TakePhotoActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, AspectRatioFragment.Listener {
    private Handler mBackgroundHandler;
    private CameraView mCameraView;
    private int mCurrentFlash;
    private static final int[] FLASH_OPTIONS = {3, 0, 1};
    private static final int[] FLASH_ICONS = {R$drawable.ic_drawable_flash_auto, R$drawable.ic_drawable_flash_off, R$drawable.ic_drawable_flash_on};
    private static final int[] FLASH_TITLES = {R$string.flash_auto, R$string.flash_off, R$string.flash_on};
    private View.OnClickListener mOnClickListener = new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.activities.TakePhotoActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getId() != R$id.take_picture || TakePhotoActivity.this.mCameraView == null) {
                return;
            }
            TakePhotoActivity.this.mCameraView.takePicture();
        }
    };
    private CameraView.Callback mCallback = new CameraView.Callback() { // from class: com.gen.mh.webapp_extensions.activities.TakePhotoActivity.3
        @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraView.Callback
        public void onCameraOpened(CameraView cameraView) {
            Log.d("MainActivity", "onCameraOpened");
        }

        @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraView.Callback
        public void onCameraClosed(CameraView cameraView) {
            Log.d("MainActivity", "onCameraClosed");
        }

        @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraView.Callback
        public void onPictureTaken(CameraView cameraView, final byte[] bArr) {
            Log.d("MainActivity", "onPictureTaken " + bArr.length);
            Toast.makeText(cameraView.getContext(), R$string.picture_taken, 0).show();
            TakePhotoActivity.this.getBackgroundHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.activities.TakePhotoActivity.3.1
                /* JADX WARN: Code restructure failed: missing block: B:16:0x0047, code lost:
                    if (r2 == null) goto L10;
                 */
                /* JADX WARN: Removed duplicated region for block: B:21:0x0061 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    FileOutputStream fileOutputStream;
                    IOException e;
                    File file = new File(TakePhotoActivity.this.getIntent().getStringExtra("photo_file_path"));
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        try {
                            try {
                                fileOutputStream.write(bArr);
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                e = e2;
                                Log.w("MainActivity", "Cannot write to " + file, e);
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException unused) {
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        fileOutputStream = null;
                        e = e3;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused2) {
                        TakePhotoActivity takePhotoActivity = TakePhotoActivity.this;
                        takePhotoActivity.setResult(-1, takePhotoActivity.getIntent());
                        TakePhotoActivity.this.finish();
                    }
                }
            });
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.activity_web_sdk_take_photo);
        this.mCameraView = (CameraView) findViewById(R$id.camera);
        CameraView cameraView = this.mCameraView;
        if (cameraView != null) {
            cameraView.addCallback(this.mCallback);
        }
        ImageView imageView = (ImageView) findViewById(R$id.take_picture);
        if (imageView != null) {
            imageView.setOnClickListener(this.mOnClickListener);
        }
        Toolbar toolbar = (Toolbar) findViewById(R$id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.activities.TakePhotoActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TakePhotoActivity.this.onBackPressed();
            }
        });
        Logger.m4113i("photo path:" + getIntent().getStringExtra("photo_file_path"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
            this.mCameraView.start();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
            int i = R$string.permission_request_denied;
            ConfirmationDialogFragment.newInstance(i, new String[]{"android.permission.CAMERA"}, 1, i).show(getSupportFragmentManager(), "dialog");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.mCameraView.stop();
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mBackgroundHandler;
        if (handler != null) {
            if (Build.VERSION.SDK_INT >= 18) {
                handler.getLooper().quitSafely();
            } else {
                handler.getLooper().quit();
            }
            this.mBackgroundHandler = null;
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity, android.support.p002v4.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != 1) {
            return;
        }
        if (strArr.length != 1 || iArr.length != 1) {
            throw new RuntimeException("Error on requesting camera permission.");
        }
        if (iArr[0] == 0) {
            return;
        }
        Toast.makeText(this, R$string.permission_request_denied, 0).show();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R$menu.main, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R$id.aspect_ratio) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            if (this.mCameraView != null && supportFragmentManager.findFragmentByTag("dialog") == null) {
                AspectRatioFragment.newInstance(this.mCameraView.getSupportedAspectRatios(), this.mCameraView.getAspectRatio()).show(supportFragmentManager, "dialog");
            }
            return true;
        } else if (itemId == R$id.switch_flash) {
            if (this.mCameraView != null) {
                this.mCurrentFlash = (this.mCurrentFlash + 1) % FLASH_OPTIONS.length;
                menuItem.setTitle(FLASH_TITLES[this.mCurrentFlash]);
                menuItem.setIcon(FLASH_ICONS[this.mCurrentFlash]);
                this.mCameraView.setFlash(FLASH_OPTIONS[this.mCurrentFlash]);
            }
            return true;
        } else if (itemId == R$id.switch_camera) {
            CameraView cameraView = this.mCameraView;
            if (cameraView != null) {
                this.mCameraView.setFacing(cameraView.getFacing() == 1 ? 0 : 1);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.AspectRatioFragment.Listener
    public void onAspectRatioSelected(@NonNull AspectRatio aspectRatio) {
        if (this.mCameraView != null) {
            Toast.makeText(this, aspectRatio.toString(), 0).show();
            this.mCameraView.setAspectRatio(aspectRatio);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Handler getBackgroundHandler() {
        if (this.mBackgroundHandler == null) {
            HandlerThread handlerThread = new HandlerThread("background");
            handlerThread.start();
            this.mBackgroundHandler = new Handler(handlerThread.getLooper());
        }
        return this.mBackgroundHandler;
    }

    /* renamed from: com.gen.mh.webapp_extensions.activities.TakePhotoActivity$ConfirmationDialogFragment */
    /* loaded from: classes2.dex */
    public static class ConfirmationDialogFragment extends DialogFragment {
        public static ConfirmationDialogFragment newInstance(@StringRes int i, String[] strArr, int i2, @StringRes int i3) {
            ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("message", i);
            bundle.putStringArray("permissions", strArr);
            bundle.putInt("request_code", i2);
            bundle.putInt("not_granted_message", i3);
            confirmationDialogFragment.setArguments(bundle);
            return confirmationDialogFragment;
        }

        @Override // android.support.p002v4.app.DialogFragment
        @NonNull
        public Dialog onCreateDialog(Bundle bundle) {
            final Bundle arguments = getArguments();
            return new AlertDialog.Builder(getActivity()).setMessage(arguments.getInt("message")).setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.gen.mh.webapp_extensions.activities.TakePhotoActivity.ConfirmationDialogFragment.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    String[] stringArray = arguments.getStringArray("permissions");
                    if (stringArray == null) {
                        throw new IllegalArgumentException();
                    }
                    ActivityCompat.requestPermissions(ConfirmationDialogFragment.this.getActivity(), stringArray, arguments.getInt("request_code"));
                }
            }).setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.gen.mh.webapp_extensions.activities.TakePhotoActivity.ConfirmationDialogFragment.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(ConfirmationDialogFragment.this.getActivity(), arguments.getInt("not_granted_message"), 0).show();
                }
            }).create();
        }
    }
}
