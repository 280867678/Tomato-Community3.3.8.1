package com.youdao.sdk.ydonlinetranslate;

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
import com.youdao.sdk.ydtranslate.TranslateSdk;
import java.util.Map;
import java.util.Random;

/* loaded from: classes4.dex */
public final class SpeechTranslateParameters {
    private final String mFormat;
    private final Language mFrom;
    private final int mRate;
    private final String mSound;
    private final String mSource;
    private final int mTimeout;
    private final Language mTo;
    private final String mVoice;

    /* loaded from: classes4.dex */
    public static final class Builder {
        private Language from;
        private String source;

        /* renamed from: to */
        private Language f5936to;
        private int timeout = ConstantUtils.MAX_ITEM_NUM;
        private String format = Constants.SOUND_INPUT_WAV;
        private int rate = Constants.RATE_16000;
        private String voice = Constants.VOICE_GIRL_US;
        private String sound = Constants.SOUND_OUTPUT_WAV;

        public Builder() {
            Language language = Language.AUTO;
            this.from = language;
            this.f5936to = language;
        }

        public final Builder voice(String str) {
            this.voice = str;
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
        public final Builder m162to(Language language) {
            this.f5936to = language;
            return this;
        }

        public final Builder timeout(int i) {
            this.timeout = i;
            return this;
        }

        public final Builder rate(int i) {
            this.rate = i;
            return this;
        }

        public final SpeechTranslateParameters build() {
            return new SpeechTranslateParameters(this);
        }
    }

    public SpeechTranslateParameters(Builder builder) {
        this.mSource = builder.source;
        this.mFrom = builder.from;
        this.mTo = builder.f5936to;
        this.mTimeout = builder.timeout;
        this.mFormat = builder.format;
        this.mRate = builder.rate;
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
        return this.mFrom;
    }

    public Language getTo() {
        return this.mTo;
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
        generateUrlMap.put("format", this.mFormat);
        generateUrlMap.put("rate", this.mRate + "");
        generateUrlMap.put("type", "1");
        generateUrlMap.put("channel", "1");
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
}
