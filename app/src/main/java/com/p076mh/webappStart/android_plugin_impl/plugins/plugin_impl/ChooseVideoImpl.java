package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.matisse.Matisse;
import com.gen.p059mh.webapp_extensions.matisse.MimeType;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.ActivityResultListener;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.iceteck.silicompressorr.SiliCompressor;
import com.p076mh.webappStart.android_plugin_impl.beans.ChooseVideoParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import com.p076mh.webappStart.util.ImgUtils;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.util.TempUtil;
import com.p076mh.webappStart.util.TransferUtil;
import com.p076mh.webappStart.util.dialog.SystemDialogFactory;
import com.p076mh.webappStart.util.thread.ThreadManager;
import com.p076mh.webappStart.util.video_compress.VideoInfo;
import com.p076mh.webappStart.util.video_compress.VideoUtil;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl */
/* loaded from: classes3.dex */
public class ChooseVideoImpl extends BasePluginImpl<ChooseVideoParamsBean> {
    private ProgressDialog compressProgressDialog;
    private final int REQUEST_CODE_PICK_VIDEO = 555;
    private final int REQUEST_CODE_VIDEO_CAPTURE = 666;
    private Map map = new HashMap();

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(final IWebFragmentController iWebFragmentController, final ChooseVideoParamsBean chooseVideoParamsBean, final Plugin.PluginCallback pluginCallback) {
        List<String> sourceType = chooseVideoParamsBean.getSourceType();
        if (sourceType == null || sourceType.size() == 0) {
            responseFailure(pluginCallback, this.map);
        } else if (sourceType.size() == 1) {
            String str = sourceType.get(0);
            if ("album".equals(str)) {
                onChooseVideo(iWebFragmentController, chooseVideoParamsBean, pluginCallback);
            } else if (!"camera".equals(str)) {
            } else {
                onRecordVideo(iWebFragmentController, chooseVideoParamsBean, pluginCallback);
            }
        } else if (sourceType.size() != 2) {
        } else {
            AlertDialog create = SystemDialogFactory.getItemDialog(iWebFragmentController.getContext(), new String[]{"拍摄", "从相册选取"}, new DialogInterface.OnClickListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        ChooseVideoImpl.this.onRecordVideo(iWebFragmentController, chooseVideoParamsBean, pluginCallback);
                    } else {
                        ChooseVideoImpl.this.onChooseVideo(iWebFragmentController, chooseVideoParamsBean, pluginCallback);
                    }
                }
            }).create();
            create.setCanceledOnTouchOutside(false);
            create.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRecordVideo(final IWebFragmentController iWebFragmentController, final ChooseVideoParamsBean chooseVideoParamsBean, final Plugin.PluginCallback pluginCallback) {
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl.2
            @Override // java.lang.Runnable
            public void run() {
                new RxPermissions(iWebFragmentController.getActivity()).request("android.permission.CAMERA").subscribe(new Consumer<Boolean>() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl.2.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Boolean bool) throws Exception {
                        if (bool.booleanValue()) {
                            Toast.makeText(iWebFragmentController.getContext(), "允许了权限!", 0).show();
                            RunnableC23282 runnableC23282 = RunnableC23282.this;
                            ChooseVideoImpl.this.doRecordVideoWork(iWebFragmentController, chooseVideoParamsBean, pluginCallback);
                            return;
                        }
                        Toast.makeText(iWebFragmentController.getContext(), "未授权权限，部分功能不能使用", 0).show();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRecordVideoWork(final IWebFragmentController iWebFragmentController, final ChooseVideoParamsBean chooseVideoParamsBean, final Plugin.PluginCallback pluginCallback) {
        iWebFragmentController.addResultListeners(new ActivityResultListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl.3
            @Override // com.gen.p059mh.webapps.listener.ActivityResultListener
            public boolean onSdkActivityResult(int i, int i2, Intent intent) {
                if (i2 == -1 && i == 666) {
                    String realPathFromURI = TransferUtil.getRealPathFromURI(iWebFragmentController.getActivity(), intent.getData());
                    String str = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                    Log.e(str, "录制视频成功: " + realPathFromURI);
                    String str2 = null;
                    try {
                        str2 = TempUtil.getTempFilePathFromSimpleFileName("VID.mp4");
                        ImgUtils.copyFileAndDeleteOldFile(realPathFromURI, str2);
                        String str3 = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                        Logger.m4112i(str3, "拷贝后的真实路径：" + str2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        ChooseVideoImpl chooseVideoImpl = ChooseVideoImpl.this;
                        chooseVideoImpl.responseFailure(pluginCallback, chooseVideoImpl.map);
                    } catch (ExecutionException e2) {
                        e2.printStackTrace();
                        ChooseVideoImpl chooseVideoImpl2 = ChooseVideoImpl.this;
                        chooseVideoImpl2.responseFailure(pluginCallback, chooseVideoImpl2.map);
                    }
                    if (!chooseVideoParamsBean.isCompressed()) {
                        try {
                            String str4 = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                            Log.e(str4, "tempFilePath: " + str2);
                            VideoInfo videoInfo = VideoUtil.getVideoInfo(iWebFragmentController.getContext(), str2);
                            ChooseVideoImpl.this.map.clear();
                            Map map = ChooseVideoImpl.this.map;
                            map.put("tempFilePath", "tmp:///" + str2.replace(iWebFragmentController.getTempDir().getAbsolutePath(), ""));
                            ChooseVideoImpl.this.map.put("duration", Integer.valueOf(videoInfo.getDuration()));
                            ChooseVideoImpl.this.map.put("size", Long.valueOf(videoInfo.getSize()));
                            ChooseVideoImpl.this.map.put("height", Integer.valueOf(videoInfo.getHeight()));
                            ChooseVideoImpl.this.map.put("width", Integer.valueOf(videoInfo.getWidth()));
                            ChooseVideoImpl.this.responseSuccess(pluginCallback, ChooseVideoImpl.this.map);
                            return false;
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            ChooseVideoImpl chooseVideoImpl3 = ChooseVideoImpl.this;
                            chooseVideoImpl3.responseFailure(pluginCallback, chooseVideoImpl3.map);
                            return false;
                        }
                    }
                    ChooseVideoImpl.this.compressVideo(str2, TempUtil.getTempFilePathFromAbsoluteFilePath(str2), iWebFragmentController, pluginCallback);
                    return false;
                }
                return false;
            }
        });
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        intent.putExtra("android.intent.extra.durationLimit", chooseVideoParamsBean.getMaxDuration());
        intent.putExtra("android.intent.extras.CAMERA_FACING", chooseVideoParamsBean.getCamera().equals("front") ? 1 : 0);
        intent.putExtra("autofocus", true);
        intent.putExtra("android.intent.extra.fullScreen", false);
        intent.putExtra("showActionIcons", false);
        iWebFragmentController.getFragment().startActivityForResult(intent, 666);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChooseVideo(final IWebFragmentController iWebFragmentController, final ChooseVideoParamsBean chooseVideoParamsBean, final Plugin.PluginCallback pluginCallback) {
        Matisse.from(iWebFragmentController.getFragment()).choose(MimeType.ofVideo()).capture(false).showSingleMediaType(true).maxSelectable(1).restrictOrientation(-1).imageEngine(SelectionSpec.getInstance().imageEngine).forResult(555, Environment.getExternalStorageDirectory().getAbsolutePath());
        iWebFragmentController.addResultListeners(new ActivityResultListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl.4
            @Override // com.gen.p059mh.webapps.listener.ActivityResultListener
            public boolean onSdkActivityResult(int i, int i2, Intent intent) {
                if (i == 555 && i2 == -1) {
                    List<String> obtainPathResult = Matisse.obtainPathResult(intent);
                    if (obtainPathResult != null && obtainPathResult.size() == 1) {
                        String str = obtainPathResult.get(0);
                        String str2 = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                        Logger.m4114e(str2, "选择视频: " + str);
                        VideoInfo videoInfo = VideoUtil.getVideoInfo(iWebFragmentController.getContext(), str);
                        String str3 = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                        Logger.m4114e(str3, "压缩前 videoInfo: " + videoInfo);
                        if (chooseVideoParamsBean.isCompressed()) {
                            String tempFilePathFromAbsoluteFilePath = TempUtil.getTempFilePathFromAbsoluteFilePath(str);
                            File file = new File(tempFilePathFromAbsoluteFilePath);
                            File parentFile = file.getParentFile();
                            if (!parentFile.exists()) {
                                parentFile.mkdirs();
                            }
                            if (file.exists()) {
                                file.delete();
                            }
                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ChooseVideoImpl.this.compressVideo(str, tempFilePathFromAbsoluteFilePath, iWebFragmentController, pluginCallback);
                        } else {
                            File file2 = new File(TempUtil.getTempFilePathFromAbsoluteFilePath(str));
                            try {
                                ImgUtils.copyFile(str, file2.getAbsolutePath());
                                String str4 = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                                Logger.m4114e(str4, "tempFilePath: " + file2.getAbsolutePath());
                                ChooseVideoImpl.this.map.clear();
                                Map map = ChooseVideoImpl.this.map;
                                map.put("tempFilePath", "tmp:///" + file2.getAbsolutePath().replace(iWebFragmentController.getTempDir().getAbsolutePath(), ""));
                                ChooseVideoImpl.this.map.put("duration", Integer.valueOf(videoInfo.getDuration()));
                                ChooseVideoImpl.this.map.put("size", Long.valueOf(videoInfo.getSize()));
                                ChooseVideoImpl.this.map.put("height", Integer.valueOf(videoInfo.getHeight()));
                                ChooseVideoImpl.this.map.put("width", Integer.valueOf(videoInfo.getWidth()));
                                ChooseVideoImpl.this.responseSuccess(pluginCallback, ChooseVideoImpl.this.map);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                ChooseVideoImpl chooseVideoImpl = ChooseVideoImpl.this;
                                chooseVideoImpl.responseFailure(pluginCallback, chooseVideoImpl.map);
                            }
                        }
                    } else {
                        ChooseVideoImpl chooseVideoImpl2 = ChooseVideoImpl.this;
                        chooseVideoImpl2.responseFailure(pluginCallback, chooseVideoImpl2.map);
                    }
                }
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compressVideo(final String str, final String str2, final IWebFragmentController iWebFragmentController, final Plugin.PluginCallback pluginCallback) {
        try {
            this.compressProgressDialog = SystemDialogFactory.getProgressDialog(iWebFragmentController.getContext(), "正在压缩中...");
            this.compressProgressDialog.show();
            ThreadManager.getInstance().executeTask(new Runnable() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ChooseVideoImpl.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String compressVideo = SiliCompressor.with(WebApplication.getInstance().getApplication()).compressVideo(str, new File(str2).getParent());
                        ChooseVideoImpl.this.compressProgressDialog.dismiss();
                        try {
                            VideoInfo videoInfo = VideoUtil.getVideoInfo(iWebFragmentController.getContext(), compressVideo);
                            String str3 = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                            Logger.m4112i(str3, "压缩后路径:" + compressVideo);
                            String str4 = ((BasePluginImpl) ChooseVideoImpl.this).TAG;
                            Logger.m4114e(str4, "压缩后 videoInfo: " + videoInfo);
                            ChooseVideoImpl.this.map.clear();
                            Map map = ChooseVideoImpl.this.map;
                            map.put("tempFilePath", "tmp:///" + new File(compressVideo).getAbsolutePath().replace(iWebFragmentController.getTempDir().getAbsolutePath(), ""));
                            ChooseVideoImpl.this.map.put("duration", Integer.valueOf(videoInfo.getDuration()));
                            ChooseVideoImpl.this.map.put("size", Long.valueOf(videoInfo.getSize()));
                            ChooseVideoImpl.this.map.put("height", Integer.valueOf(videoInfo.getHeight()));
                            ChooseVideoImpl.this.map.put("width", Integer.valueOf(videoInfo.getWidth()));
                            ChooseVideoImpl.this.responseSuccess(pluginCallback, ChooseVideoImpl.this.map);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ChooseVideoImpl.this.responseFailure(pluginCallback);
                        }
                    } catch (URISyntaxException e2) {
                        ChooseVideoImpl.this.compressProgressDialog.dismiss();
                        e2.printStackTrace();
                        ChooseVideoImpl.this.responseFailure(pluginCallback);
                    }
                }
            });
        } catch (Exception e) {
            this.compressProgressDialog.dismiss();
            e.printStackTrace();
            responseFailure(pluginCallback);
        }
    }
}
