package com.one.tomato.utils.post;

import android.text.TextUtils;
import android.widget.EditText;
import com.broccoli.p150bh.R;
import com.one.tomato.utils.ToastUtil;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class CommentCheckUtil {
    private static char[] normal = {',', '.', '!', '?', ':', ';', 65292, 12290, 65281, 65311, 65306, 65307};

    public static boolean isValid(EditText editText, String str) {
        if (TextUtils.isEmpty(str)) {
            ToastUtil.showCenterToast((int) R.string.video_comment_submit_tip);
            return false;
        } else if (str.length() < 4) {
            ToastUtil.showCenterToast((int) R.string.post_comment_content_short_tip);
            return false;
        } else if (checkIsMaxString(str)) {
            return false;
        } else {
            if (onlyNumber(str)) {
                ToastUtil.showCenterToast((int) R.string.post_comment_content_number_tip);
                editText.setText("");
                return false;
            } else if (onlySpecialChar(str)) {
                ToastUtil.showCenterToast((int) R.string.post_comment_content_invalid_tip);
                editText.setText("");
                return false;
            } else if (onlyNumberAndSpecialChar(str, 8)) {
                ToastUtil.showCenterToast((int) R.string.post_comment_content_invalid_tip);
                editText.setText("");
                return false;
            } else if (tooManyNumber(str, 8)) {
                ToastUtil.showCenterToast((int) R.string.post_comment_content_invalid_tip);
                editText.setText("");
                return false;
            } else if (!tooManySpecialChar(str)) {
                return true;
            } else {
                ToastUtil.showCenterToast((int) R.string.post_comment_content_invalid_tip);
                editText.setText("");
                return false;
            }
        }
    }

    public static boolean checkIsMaxString(String str) {
        if (str.trim().length() >= 270) {
            ToastUtil.showCenterToast((int) R.string.post_comment_content_long_tip);
            return true;
        }
        return false;
    }

    public static boolean onlyNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!isNumber(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean onlySpecialChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (isNormalChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean onlyNumberAndSpecialChar(String str, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            i2 = (onlyNumber(str) || !isNormalChar(str.charAt(i3))) ? i2 + 1 : 0;
        }
        return i2 >= i;
    }

    private static boolean isNumber(char c) {
        Pattern compile = Pattern.compile("[0-9]");
        return compile.matcher(c + "").matches();
    }

    private static boolean isNormalChar(char c) {
        Pattern compile = Pattern.compile("[\\u4E00-\\u9FA5|0-9|a-z|A-Z]");
        return compile.matcher(c + "").matches();
    }

    public static boolean tooManyNumber(String str, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (isNumber(str.charAt(i3))) {
                i2++;
            }
        }
        return i2 >= i;
    }

    public static boolean tooManySpecialChar(String str) {
        List asList = Arrays.asList(normal);
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (!asList.contains(Character.valueOf(charAt)) && !isNormalChar(charAt)) {
                i++;
            }
        }
        if ((i * 100) / str.length() > 30) {
            ToastUtil.showCenterToast((int) R.string.post_comment_content_invalid_tip);
            return true;
        } else if (i < 10) {
            return false;
        } else {
            ToastUtil.showCenterToast((int) R.string.post_comment_content_invalid_tip);
            return true;
        }
    }
}
