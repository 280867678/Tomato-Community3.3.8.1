package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.ComponentsEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.utils.CacheUtils;

/* renamed from: com.tomatolive.library.ui.view.custom.LiveGamePrizeView */
/* loaded from: classes3.dex */
public class LiveGamePrizeView extends RelativeLayout {
    private TextView tvContent;
    private TextView tvTitle;

    public LiveGamePrizeView(Context context) {
        this(context, null);
    }

    public LiveGamePrizeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_layout_live_game_open_prize_view, this);
        this.tvTitle = (TextView) findViewById(R$id.tv_game_title);
        this.tvContent = (TextView) findViewById(R$id.tv_game_content);
    }

    public void initData(SocketMessageEvent.ResultData resultData) {
        String string = getContext().getString(R$string.fq_game_open_prize_result);
        ComponentsEntity localCacheComponentsByGameId = CacheUtils.getLocalCacheComponentsByGameId(resultData.gameId);
        if (localCacheComponentsByGameId != null) {
            string = localCacheComponentsByGameId.name;
        }
        TextView textView = this.tvTitle;
        if (TextUtils.isEmpty(string)) {
            string = getContext().getString(R$string.fq_game_open_prize_result);
        }
        textView.setText(string);
        this.tvContent.setText(resultData.content);
    }
}
