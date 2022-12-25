package com.one.tomato.thirdpart.captcha;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;
import com.one.tomato.entity.p079db.VerNumBean;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;

/* loaded from: classes3.dex */
public class CaptchaUtil {
    private Captcha captcha;
    private CaptchaUtilListener captchaUtilListener;
    private CaptchaConfiguration configuration;

    /* loaded from: classes3.dex */
    public interface CaptchaUtilListener {
        void validateFail();

        void validateSuccess(String str);
    }

    public void setCaptchaUtilListener(CaptchaUtilListener captchaUtilListener) {
        this.captchaUtilListener = captchaUtilListener;
    }

    public CaptchaUtil(Context context) {
        this.configuration = new CaptchaConfiguration.Builder().captchaId("5adb8a4c99144f26a3a5d5f3dc1e00b3").mode(CaptchaConfiguration.ModeType.MODE_CAPTCHA).listener(new CaptchaListener() { // from class: com.one.tomato.thirdpart.captcha.CaptchaUtil.1
            @Override // com.netease.nis.captcha.CaptchaListener
            public void onReady() {
            }

            @Override // com.netease.nis.captcha.CaptchaListener
            public void onValidate(String str, String str2, String str3) {
                if (!TextUtils.isEmpty(str2)) {
                    LogUtil.m3784i("验证成功: result = " + str + ",validate = " + str2 + ",msg = " + str3);
                    if (CaptchaUtil.this.captchaUtilListener == null) {
                        return;
                    }
                    CaptchaUtil.this.captchaUtilListener.validateSuccess(str2);
                    return;
                }
                LogUtil.m3786e("验证失败: " + str3);
                if (CaptchaUtil.this.captchaUtilListener == null) {
                    return;
                }
                CaptchaUtil.this.captchaUtilListener.validateFail();
            }

            @Override // com.netease.nis.captcha.CaptchaListener
            public void onError(int i, String str) {
                LogUtil.m3786e("验证出错: code = " + i + ", msg = " + str);
                if (CaptchaUtil.this.captchaUtilListener != null) {
                    CaptchaUtil.this.captchaUtilListener.validateFail();
                }
            }

            @Override // com.netease.nis.captcha.CaptchaListener
            public void onCancel() {
                if (CaptchaUtil.this.captchaUtilListener != null) {
                    CaptchaUtil.this.captchaUtilListener.validateFail();
                }
            }

            @Override // com.netease.nis.captcha.CaptchaListener
            public void onClose() {
                LogUtil.m3784i("用户关闭验证码");
                if (CaptchaUtil.this.captchaUtilListener != null) {
                    CaptchaUtil.this.captchaUtilListener.validateFail();
                }
            }
        }).timeout(10000L).languageType(CaptchaConfiguration.LangType.LANG_ZH_TW).debug(false).controlBarImageUrl("", "", "").backgroundDimAmount(0.5f).touchOutsideDisappear(false).useDefaultFallback(true).failedMaxRetryCount(5).hideCloseButton(false).loadingText("").build(context);
        this.captcha = Captcha.getInstance().init(this.configuration);
    }

    public void validate() {
        if (!isHaveNumShow()) {
            ToastUtil.showCenterToast("验证码获取已到达上线，请明天再试");
            return;
        }
        Captcha captcha = this.captcha;
        if (captcha == null) {
            return;
        }
        captcha.validate();
    }

    public void onDestroy() {
        Captcha.getInstance().destroy();
    }

    private boolean isHaveNumShow() {
        VerNumBean verNumBean = DBUtil.getVerNumBean();
        if (DateUtils.isToday(verNumBean.getCurrentSysTime())) {
            if (verNumBean.getNum() <= 0) {
                return false;
            }
            verNumBean.setNum(verNumBean.getNum() - 1);
            verNumBean.setCurrentSysTime(System.currentTimeMillis());
            DBUtil.setVerNumBean(verNumBean);
            return true;
        }
        verNumBean.setCurrentSysTime(System.currentTimeMillis());
        verNumBean.setNum(9);
        DBUtil.setVerNumBean(verNumBean);
        return true;
    }
}
