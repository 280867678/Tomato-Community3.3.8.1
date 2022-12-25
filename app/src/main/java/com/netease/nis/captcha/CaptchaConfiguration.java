package com.netease.nis.captcha;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class CaptchaConfiguration {

    /* renamed from: a */
    final Context f1569a;

    /* renamed from: b */
    final String f1570b;

    /* renamed from: c */
    final String f1571c;

    /* renamed from: d */
    final ModeType f1572d;

    /* renamed from: e */
    final LangType f1573e;

    /* renamed from: f */
    final float f1574f;

    /* renamed from: g */
    final String f1575g;

    /* renamed from: h */
    final String f1576h;

    /* renamed from: i */
    final String f1577i;

    /* renamed from: j */
    final int f1578j;

    /* renamed from: k */
    final int f1579k;

    /* renamed from: l */
    final int f1580l;

    /* renamed from: m */
    final CaptchaListener f1581m;

    /* renamed from: n */
    final long f1582n;

    /* renamed from: o */
    final boolean f1583o;

    /* renamed from: p */
    final boolean f1584p;

    /* renamed from: q */
    final boolean f1585q;

    /* renamed from: r */
    final int f1586r;

    /* renamed from: s */
    final String f1587s;

    /* renamed from: t */
    final String f1588t;

    /* renamed from: u */
    final String f1589u;

    /* renamed from: v */
    final String f1590v;

    /* renamed from: w */
    final int f1591w;

    /* renamed from: x */
    final int f1592x;

    /* loaded from: classes3.dex */
    public static class Builder {
        private String apiServer;
        private String captchaId;
        private boolean debug;
        private String errorIconUrl;
        private CaptchaListener listener;
        private int loadingAnimResId;
        private String loadingText;
        private int loadingTextId;
        private String movingIconUrl;
        private String protocol;
        private String startIconUrl;
        private String staticServer;
        private String url = "https://cstaticdun.126.net/api/v2/mobile.v2.10.1.html";
        private ModeType mode = ModeType.MODE_CAPTCHA;
        private LangType langType = LangType.LANG_ZH_CN;
        private long timeout = 10000;
        private float backgroundDimAmount = 0.5f;
        private int xCoordinate = -1;
        private int yCoordinate = -1;
        private int width = 0;
        private int height = 0;
        private boolean isHideCloseButton = false;
        private boolean isTouchOutsideDisappear = true;
        private boolean isUsedDefaultFallback = true;
        private int failedMaxRetryCount = 3;

        public Builder apiServer(String str) {
            this.apiServer = str;
            return this;
        }

        public Builder backgroundDimAmount(float f) {
            this.backgroundDimAmount = f;
            return this;
        }

        public CaptchaConfiguration build(Context context) {
            return new CaptchaConfiguration(context, this);
        }

        public Builder captchaId(String str) {
            this.captchaId = str;
            return this;
        }

        public Builder controlBarImageUrl(String str, String str2, String str3) {
            this.startIconUrl = str;
            this.movingIconUrl = str2;
            this.errorIconUrl = str3;
            return this;
        }

        public Builder debug(boolean z) {
            this.debug = z;
            return this;
        }

        public Builder failedMaxRetryCount(int i) {
            this.failedMaxRetryCount = i;
            return this;
        }

        public Builder hideCloseButton(boolean z) {
            this.isHideCloseButton = z;
            return this;
        }

        public Builder languageType(LangType langType) {
            this.langType = langType;
            return this;
        }

        public Builder listener(CaptchaListener captchaListener) {
            this.listener = captchaListener;
            return this;
        }

        public Builder loadingAnimResId(int i) {
            this.loadingAnimResId = i;
            return this;
        }

        public Builder loadingText(String str) {
            this.loadingText = str;
            return this;
        }

        public Builder loadingTextId(int i) {
            this.loadingTextId = i;
            return this;
        }

        public Builder mode(ModeType modeType) {
            this.mode = modeType;
            return this;
        }

        public Builder position(int i, int i2) {
            this.xCoordinate = i;
            this.yCoordinate = i2;
            return this;
        }

        @Deprecated
        public Builder position(int i, int i2, int i3, int i4) {
            this.xCoordinate = i;
            this.yCoordinate = i2;
            this.width = i3;
            this.height = i4;
            return this;
        }

        public Builder protocol(String str) {
            if (!str.equals("http") && !str.equals("https")) {
                str = "https";
            }
            this.protocol = str;
            return this;
        }

        public Builder staticServer(String str) {
            this.staticServer = str;
            return this;
        }

        public Builder timeout(long j) {
            this.timeout = j;
            return this;
        }

        public Builder touchOutsideDisappear(boolean z) {
            this.isTouchOutsideDisappear = z;
            return this;
        }

        public Builder url(String str) {
            if (!TextUtils.isEmpty(str)) {
                this.url = str;
            }
            return this;
        }

        public Builder useDefaultFallback(boolean z) {
            this.isUsedDefaultFallback = z;
            return this;
        }
    }

    /* loaded from: classes3.dex */
    public enum LangType {
        LANG_ZH_CN,
        LANG_ZH_TW,
        LANG_EN,
        LANG_JA,
        LANG_KO,
        LANG_TH,
        LANG_VI,
        LANG_FR,
        LANG_AR,
        LANG_RU,
        LANG_DE,
        LANG_IT,
        LANG_HE,
        LANG_HI,
        LANG_ID,
        LANG_MY,
        LANG_LO,
        LANG_MS,
        LANG_PL,
        LANG_PT,
        LANG_ES,
        LANG_TR
    }

    /* loaded from: classes3.dex */
    public enum ModeType {
        MODE_CAPTCHA,
        MODE_INTELLIGENT_NO_SENSE
    }

    public CaptchaConfiguration(Context context, Builder builder) {
        this.f1569a = context;
        this.f1570b = builder.captchaId;
        this.f1571c = builder.url;
        this.f1572d = builder.mode;
        this.f1573e = builder.langType;
        this.f1574f = builder.backgroundDimAmount;
        this.f1575g = builder.startIconUrl;
        this.f1576h = builder.movingIconUrl;
        this.f1577i = builder.errorIconUrl;
        this.f1578j = builder.xCoordinate;
        this.f1579k = builder.yCoordinate;
        this.f1580l = builder.width;
        this.f1581m = builder.listener;
        this.f1582n = builder.timeout;
        this.f1583o = builder.isHideCloseButton;
        this.f1584p = builder.isTouchOutsideDisappear;
        this.f1585q = builder.isUsedDefaultFallback;
        this.f1586r = builder.failedMaxRetryCount;
        this.f1589u = builder.protocol;
        this.f1587s = builder.apiServer;
        this.f1588t = builder.staticServer;
        this.f1590v = builder.loadingText;
        this.f1591w = builder.loadingTextId;
        this.f1592x = builder.loadingAnimResId;
        C2409d.m3799a(builder.debug);
    }
}
