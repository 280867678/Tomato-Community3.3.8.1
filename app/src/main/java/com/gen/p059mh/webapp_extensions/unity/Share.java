package com.gen.p059mh.webapp_extensions.unity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.p002v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import com.gen.p059mh.webapps.unity.Function;
import com.gen.p059mh.webapps.unity.Unity;
import com.iceteck.silicompressorr.FileUtils;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.unity.Share */
/* loaded from: classes2.dex */
public class Share extends Function {
    public Share() {
        registerMethod("invoke", new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$0zyiYwmT3R7OF6iHoh19VgfGyZY
            @Override // com.gen.p059mh.webapps.unity.Unity.Method
            public final void call(Unity.MethodCallback methodCallback, Object[] objArr) {
                Share.this.invoke(methodCallback, objArr);
            }
        });
    }

    @Override // com.gen.p059mh.webapps.unity.Function
    public void invoke(final Unity.MethodCallback methodCallback, final Object... objArr) {
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.Share.1
            @Override // java.lang.Runnable
            public void run() {
                if (!TextUtils.isEmpty(Share.this.getImageStringText((List) objArr[0]))) {
                    Share.this.requestPermissionsHere(methodCallback, objArr);
                } else {
                    Share.this.initData(methodCallback, objArr);
                }
            }
        });
    }

    public void initData(Unity.MethodCallback methodCallback, Object... objArr) {
        List list = (List) objArr[0];
        shareText(getImageStringText(list), getTextString(list));
        methodCallback.run(true);
    }

    public void shareText(String str, String str2) {
        Uri saveBitmap;
        String imageString = getImageString(str);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType(FileUtils.MIME_TYPE_TEXT);
        intent.putExtra("android.intent.extra.TEXT", str2);
        if (getBitmap(imageString) != null && (saveBitmap = saveBitmap(getBitmap(imageString))) != null) {
            intent.putExtra("android.intent.extra.STREAM", saveBitmap);
            intent.setType(FileUtils.MIME_TYPE_IMAGE);
        }
        getWebViewFragment().getContext().startActivity(Intent.createChooser(intent, "分享到"));
    }

    public String getImageString(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(",");
            return split.length >= 2 ? split[1] : "";
        }
        return "";
    }

    public Bitmap getBitmap(String str) {
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    private Uri saveBitmap(Bitmap bitmap) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/web_app/share_pic.jpg");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return getNewUri(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public Uri getNewUri(File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            if (!file.exists()) {
                return null;
            }
            Context context = getWebViewFragment().getContext();
            return FileProvider.getUriForFile(context, getWebViewFragment().getContext().getPackageName() + ".websdk.fileprovider", file);
        }
        return Uri.fromFile(file);
    }

    public String getTextString(List list) {
        String str = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!((String) list.get(i)).contains("data:image")) {
                    str = TextUtils.isEmpty(str) ? (String) list.get(i) : str + "\n" + ((String) list.get(i));
                } else {
                    ((String) list.get(i)).contains("data:image");
                }
            }
        }
        return str;
    }

    public String getImageStringText(List list) {
        if (list == null || list.size() <= 0) {
            return "";
        }
        for (int i = 0; i < list.size(); i++) {
            if (((String) list.get(i)).contains("data:image")) {
                return (String) list.get(i);
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPermissionsHere(final Unity.MethodCallback methodCallback, final Object... objArr) {
        new RxPermissions(getWebViewFragment().getActivity()).request("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE").subscribe(new Consumer<Boolean>() { // from class: com.gen.mh.webapp_extensions.unity.Share.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) throws Exception {
                if (bool.booleanValue()) {
                    Share.this.initData(methodCallback, objArr);
                } else {
                    Toast.makeText(Share.this.getWebViewFragment().getContext(), "未授权权限，该功能不能使用", 0).show();
                }
            }
        });
    }
}
