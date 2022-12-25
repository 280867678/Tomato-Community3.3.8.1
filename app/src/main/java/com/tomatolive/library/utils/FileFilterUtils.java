package com.tomatolive.library.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.FileFilter;

/* loaded from: classes4.dex */
public class FileFilterUtils implements FileFilter {
    private String fileSuffixName;

    public FileFilterUtils(String str) {
        this.fileSuffixName = str;
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        if (com.blankj.utilcode.util.FileUtils.isDir(file)) {
            return true;
        }
        return TextUtils.equals(com.blankj.utilcode.util.FileUtils.getFileExtension(file), this.fileSuffixName);
    }
}
