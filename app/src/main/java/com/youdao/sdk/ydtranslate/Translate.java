package com.youdao.sdk.ydtranslate;

import android.content.Context;
import android.text.TextUtils;
import com.tomatolive.library.utils.TranslationUtils;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.app.Utils;
import com.youdao.sdk.app.WordHelper;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes4.dex */
public class Translate implements Serializable {
    private String UKSpeakUrl;
    private String USSpeakUrl;
    private String deeplink;
    private String dictDeeplink;
    private int errorCode;
    private List<String> explains;
    private String from;

    /* renamed from: le */
    private String f5950le;
    private String phonetic;
    private String query;
    private String resultSpeakUrl;
    private String speakUrl;

    /* renamed from: to */
    private String f5951to;
    private List<String> translations;
    private String ukPhonetic;
    private String usPhonetic;
    private List<WebExplain> webExplains;

    public String getDictDeeplink() {
        return this.dictDeeplink;
    }

    public void setDictDeeplink(String str) {
        this.dictDeeplink = str;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String str) {
        this.from = str;
    }

    public String getTo() {
        return this.f5951to;
    }

    public void setTo(String str) {
        this.f5951to = str;
    }

    public String getLe() {
        return this.f5950le;
    }

    public void setLe(String str) {
        this.f5950le = str;
    }

    public String getDeeplink() {
        return this.deeplink;
    }

    public void setDeeplink(String str) {
        this.deeplink = str;
    }

    public void setUKSpeakUrl(String str) {
        this.UKSpeakUrl = str;
    }

    public void setUSSpeakUrl(String str) {
        this.USSpeakUrl = str;
    }

    public boolean success() {
        return this.errorCode == 0;
    }

    public List<WebExplain> getWebExplains() {
        return this.webExplains;
    }

    public void setWebExplains(List<WebExplain> list) {
        this.webExplains = list;
    }

    public String getUsPhonetic() {
        return this.usPhonetic;
    }

    public void setUsPhonetic(String str) {
        this.usPhonetic = str;
    }

    public String getPhonetic() {
        return this.phonetic;
    }

    public void setPhonetic(String str) {
        this.phonetic = str;
    }

    public String getUkPhonetic() {
        return this.ukPhonetic;
    }

    public void setUkPhonetic(String str) {
        this.ukPhonetic = str;
    }

    public List<String> getExplains() {
        return this.explains;
    }

    public void setExplains(List<String> list) {
        this.explains = list;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public List<String> getTranslations() {
        return this.translations;
    }

    public void setTranslations(List<String> list) {
        this.translations = list;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String str) {
        this.query = str;
    }

    public void setSpeakUrl(String str) {
        this.speakUrl = str;
    }

    public void setResultSpeakUrl(String str) {
        this.resultSpeakUrl = str;
    }

    public void openMore(Context context) {
        if (!TextUtils.isEmpty(this.deeplink) && !TextUtils.isEmpty(this.dictDeeplink)) {
            WordHelper.openMore(context, this.query, this.f5950le, LanguageUtils.getLangByCode(this.from), LanguageUtils.getLangByCode(this.f5951to), this.deeplink, this.dictDeeplink);
        } else if (!TextUtils.isEmpty(this.deeplink)) {
            WordHelper.openMore_Foreigner(context, this.query, this.deeplink, this.from, this.f5951to, this.f5950le);
        } else if (Utils.isChinese(context)) {
            WordHelper.openMore(context, this.query, this.f5950le, LanguageUtils.getLangByCode(this.from), LanguageUtils.getLangByCode(this.f5951to));
        } else {
            this.f5951to = this.f5951to.replace(Language.CHINESE.getCode(), TranslationUtils.f5876CH);
            this.f5951to = this.f5951to.replace(Language.ENGLISH.getCode(), "en");
            this.from = this.from.replace(Language.CHINESE.getCode(), TranslationUtils.f5876CH);
            this.from = this.from.replace(Language.ENGLISH.getCode(), "en");
            WordHelper.openMore_Foreigner(context, this.query, null, this.from, this.f5951to, this.f5950le);
        }
    }

    public String getDictWebUrl() {
        if (!TextUtils.isEmpty(this.deeplink)) {
            return this.deeplink;
        }
        return WordHelper.getWordDetailUrl(this.query, this.f5950le, LanguageUtils.getLangByCode(this.from), LanguageUtils.getLangByCode(this.f5951to));
    }

    public boolean openDict(Context context) {
        return WordHelper.openDeepLink(context, this.query, this.f5950le, LanguageUtils.getLangByCode(this.from), LanguageUtils.getLangByCode(this.f5951to));
    }

    public String getResultSpeakUrl() {
        return this.resultSpeakUrl;
    }

    public String getSpeakUrl() {
        return this.speakUrl;
    }

    public String getUKSpeakUrl() {
        return this.UKSpeakUrl;
    }

    public String getUSSpeakUrl() {
        return this.USSpeakUrl;
    }
}
