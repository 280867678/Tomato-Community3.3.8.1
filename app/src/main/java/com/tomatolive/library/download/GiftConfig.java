package com.tomatolive.library.download;

import com.blankj.utilcode.util.PathUtils;
import com.tomatolive.library.utils.MD5Utils;
import java.io.File;

/* loaded from: classes3.dex */
public enum GiftConfig {
    INSTANCE;
    
    private final String APP_ROOT_DIR_NAME = "giftRes";
    private final String ANIM_ROOT_DIR_NAME = MD5Utils.getMd5("animation");
    private final String ANIM_CAR_DIR_NAME = MD5Utils.getMd5("carAnimation");
    private final String ANIM_NOBILITY_DIR_NAME = MD5Utils.getMd5("nobilityAnimation");
    private final String RES_HOT_LOAD_DIR_NAME = MD5Utils.getMd5("resHotLoad");
    private final String RES_ICON_LOAD_DIR_NAME = MD5Utils.getMd5("resIconLoad");
    public final String CAR_CONFIG_NAME = MD5Utils.getMd5("carConfig.txt");
    public String animResRootPath = getAnimResRootPath();
    public String carAnimResRootPath = getCarAnimResRootPath();
    public String nobilityAnimResRootPath = getNobilityAnimResRootPath();
    public String resHotLoadRootPath = getResHotLoadRootPath();
    public String resIconLoadRootPath = getResIconLoadRootPath();

    GiftConfig() {
    }

    private String getAnimResRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppFilesPath());
        sb.append(File.separator);
        sb.append("giftRes");
        sb.append(File.separator);
        sb.append(this.ANIM_ROOT_DIR_NAME);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    private String getCarAnimResRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppFilesPath());
        sb.append(File.separator);
        sb.append("giftRes");
        sb.append(File.separator);
        sb.append(this.ANIM_CAR_DIR_NAME);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    private String getNobilityAnimResRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppFilesPath());
        sb.append(File.separator);
        sb.append("giftRes");
        sb.append(File.separator);
        sb.append(this.ANIM_NOBILITY_DIR_NAME);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    private String getResHotLoadRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppFilesPath());
        sb.append(File.separator);
        sb.append("giftRes");
        sb.append(File.separator);
        sb.append(this.RES_HOT_LOAD_DIR_NAME);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    private String getResIconLoadRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathUtils.getExternalAppFilesPath());
        sb.append(File.separator);
        sb.append("giftRes");
        sb.append(File.separator);
        sb.append(this.RES_ICON_LOAD_DIR_NAME);
        sb.append(File.separator);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }
}
