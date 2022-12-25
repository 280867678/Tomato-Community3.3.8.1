package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.tomatolive.library.ui.adapter.SearchKeywordAdapter */
/* loaded from: classes3.dex */
public class SearchKeywordAdapter extends BaseQuickAdapter<LabelEntity, BaseViewHolder> {
    private String keyWord;

    public SearchKeywordAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LabelEntity labelEntity) {
        baseViewHolder.setText(R$id.tv_keyword, getTextHighLightByMatcher(getKeyWord(), labelEntity.keyword, false));
    }

    private String getKeyWord() {
        return this.keyWord;
    }

    public void setKeyWord(String str) {
        this.keyWord = str;
    }

    private SpannableString getTextHighLightByMatcher(String str, String str2, boolean z) {
        if (TextUtils.isEmpty(str2)) {
            return new SpannableString(ConstantUtils.PLACEHOLDER_STR_ONE);
        }
        if (z) {
            str2 = "ID：" + str2;
        }
        SpannableString spannableString = new SpannableString(str2);
        if (TextUtils.isEmpty(str)) {
            return spannableString;
        }
        for (int i = 0; i < str.length(); i++) {
            Matcher matcher = Pattern.compile(String.valueOf(str.charAt(i)), 2).matcher(spannableString);
            while (matcher.find()) {
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary)), matcher.start(), matcher.end(), 33);
            }
        }
        return spannableString;
    }
}
