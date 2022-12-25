package com.youdao.sdk.ydtranslate;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.LogConstants;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.sdk.common.Constants;
import com.youdao.sdk.common.YDUrlGenerator;
import java.util.Map;
import java.util.Random;

/* loaded from: classes4.dex */
public final class TranslateParameters {
    private final Language mFrom;
    private final String mSound;
    private final String mSource;
    private final int mTimeout;
    private final Language mTo;
    private final String mVoice;
    private final boolean useAutoConvertLine;
    private final boolean useAutoConvertWord;

    /* loaded from: classes4.dex */
    public static final class Builder {
        private Language from;
        private String source;
        private int timeout;

        /* renamed from: to */
        private Language f5952to;
        private boolean useAutoConvertLine = true;
        private boolean useAutoConvertWord = true;
        private String voice = Constants.VOICE_GIRL_US;
        private String sound = Constants.SOUND_OUTPUT_MP3;

        public final Builder voice(String str) {
            this.voice = str;
            return this;
        }

        public final Builder sound(String str) {
            this.sound = str;
            return this;
        }

        public final Builder source(String str) {
            this.source = str;
            return this;
        }

        public final Builder from(Language language) {
            this.from = language;
            return this;
        }

        /* renamed from: to */
        public final Builder m145to(Language language) {
            this.f5952to = language;
            return this;
        }

        public final Builder timeout(int i) {
            this.timeout = i;
            return this;
        }

        public final Builder useAutoConvertLine(boolean z) {
            this.useAutoConvertLine = z;
            return this;
        }

        public final Builder useAutoConvertWord(boolean z) {
            this.useAutoConvertWord = z;
            return this;
        }

        public final TranslateParameters build() {
            return new TranslateParameters(this);
        }
    }

    public TranslateParameters(Builder builder) {
        this.mSource = builder.source;
        this.mFrom = builder.from;
        this.mTo = builder.f5952to;
        this.mTimeout = builder.timeout;
        this.useAutoConvertLine = builder.useAutoConvertLine;
        this.useAutoConvertWord = builder.useAutoConvertWord;
        this.mSound = builder.sound;
        this.mVoice = builder.voice;
    }

    public final String getSource() {
        return this.mSource;
    }

    public final String getAppKey() {
        return YouDaoApplication.mAppKey;
    }

    public Language getFrom() {
        Language language = this.mFrom;
        return language == null ? Language.CHINESE : language;
    }

    public Language getTo() {
        Language language = this.mTo;
        return language == null ? Language.ENGLISH : language;
    }

    public boolean isUseAutoConvertLine() {
        return this.useAutoConvertLine;
    }

    public boolean isUseAutoConvertWord() {
        return this.useAutoConvertWord;
    }

    public final int getTimeout() {
        int i = this.mTimeout;
        return i < 1 ? ConstantUtils.MAX_ITEM_NUM : i;
    }

    Map<String, String> params(Context context, String str) {
        String appKey = getAppKey();
        YDUrlGenerator yDUrlGenerator = new YDUrlGenerator(context);
        yDUrlGenerator.withAppKey(appKey);
        Map<String, String> generateUrlMap = yDUrlGenerator.generateUrlMap();
        if (getFrom() != null) {
            generateUrlMap.put("from", getFrom().getCode());
        }
        if (getTo() != null) {
            generateUrlMap.put("to", getTo().getCode());
        }
        int nextInt = new Random().nextInt(1000);
        String sign = new TranslateSdk().sign(context, appKey, str, String.valueOf(nextInt));
        generateUrlMap.put("q", str);
        generateUrlMap.put("salt", String.valueOf(nextInt));
        generateUrlMap.put("signType", "v1");
        generateUrlMap.put("docType", "json");
        generateUrlMap.put("sign", sign);
        generateUrlMap.put(LogConstants.ENTER_SOURCE, this.mSource);
        generateUrlMap.put("offline", "0");
        generateUrlMap.put("sound", this.mSound);
        generateUrlMap.put("voice", this.mVoice);
        int i = this.mTimeout;
        if (i > 0) {
            generateUrlMap.put("timeout", String.valueOf(i));
        }
        return generateUrlMap;
    }

    public String paramString(Context context, String str) {
        Map<String, String> params = params(context, str);
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                sb.append(key);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                sb.append(Uri.encode(value));
                sb.append("&");
            }
        }
        return sb.toString();
    }

    public String getTranslateType() {
        if (this.mFrom == Language.SPANISH && this.mTo == Language.CHINESE) {
            return this.mTo.getCode() + "-" + this.mFrom.getCode();
        } else if (this.mFrom == Language.INDO && this.mTo == Language.ENGLISH) {
            return this.mTo.getCode() + "-" + this.mFrom.getCode();
        } else {
            return this.mFrom.getCode() + "-" + this.mTo.getCode();
        }
    }
}
