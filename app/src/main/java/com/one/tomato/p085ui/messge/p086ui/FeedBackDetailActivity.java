package com.one.tomato.p085ui.messge.p086ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.CommonBaseAdapter;
import com.one.tomato.adapter.ViewHolder;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.p085ui.messge.bean.PostReviewBean;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoScrollGridView;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_feed_back_detail)
/* renamed from: com.one.tomato.ui.messge.ui.FeedBackDetailActivity */
/* loaded from: classes3.dex */
public class FeedBackDetailActivity extends BaseActivity {
    @ViewInject(R.id.grid_view)
    NoScrollGridView gridView;
    @ViewInject(R.id.text_content_time)
    TextView textContentTime;
    @ViewInject(R.id.text_reason)
    TextView textReason;
    @ViewInject(R.id.text_reply)
    TextView textReply;
    @ViewInject(R.id.text_time)
    TextView textTime;
    @ViewInject(R.id.text_title)
    TextView textTitle;
    @ViewInject(R.id.text_order)
    TextView text_order;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        setData((PostReviewBean.DataBean) getIntent().getSerializableExtra(AopConstants.APP_PROPERTIES_KEY));
    }

    private void setData(PostReviewBean.DataBean dataBean) {
        String[] split;
        if (dataBean == null) {
            return;
        }
        this.titleTV.setText(dataBean.getMsgTitle());
        switch (dataBean.getMsgType()) {
            case 401:
            case 402:
            case 403:
                this.textTitle.setText(R.string.string_reply_des);
                break;
            case 404:
                this.textTitle.setText(R.string.string_jubao_reason);
                break;
        }
        this.textTime.setText(dataBean.getExt1());
        if (!TextUtils.isEmpty(dataBean.getExt3())) {
            this.text_order.setVisibility(0);
            ViewUtil.initTextViewWithSpannableString(this.text_order, new String[]{AppUtil.getString(R.string.feedback_recharge_order_num), dataBean.getExt3()}, new String[]{String.valueOf(getResources().getColor(R.color.text_dark)), String.valueOf(getResources().getColor(R.color.text_dark))}, new String[]{"14", "14"});
        }
        this.textReason.setText(dataBean.getFeedbackMsg());
        this.textContentTime.setText(FormatUtil.formatTime(FormatUtil.formatTimeToDate(DateUtils.C_TIME_PATTON_DEFAULT, dataBean.getCreateTime()), new Date()));
        String feedbackMsgReply = dataBean.getFeedbackMsgReply();
        String string = this.mContext.getResources().getString(R.string.string_feedback_reply_me);
        int length = string.length();
        SpannableString spannableString = new SpannableString(string + feedbackMsgReply);
        spannableString.setSpan(new StyleSpan(1), 0, length, 17);
        this.textReply.setText(spannableString);
        final ArrayList arrayList = new ArrayList();
        try {
            if (!TextUtils.isEmpty(dataBean.getArticleCover())) {
                for (String str : dataBean.getArticleCover().split(";")) {
                    ImageBean imageBean = new ImageBean();
                    imageBean.setImage(str);
                    arrayList.add(imageBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonBaseAdapter<ImageBean> commonBaseAdapter = new CommonBaseAdapter<ImageBean>(this, this, arrayList, R.layout.itme_suggest) { // from class: com.one.tomato.ui.messge.ui.FeedBackDetailActivity.1
            @Override // com.one.tomato.adapter.CommonBaseAdapter
            public void convert(ViewHolder viewHolder, ImageBean imageBean2, int i) {
                ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, (ImageView) viewHolder.getView(R.id.image_content_i), new ImageBean(imageBean2.getImage()));
            }
        };
        if (arrayList.isEmpty()) {
            this.gridView.setVisibility(8);
        } else {
            this.gridView.setAdapter((ListAdapter) commonBaseAdapter);
        }
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.ui.messge.ui.FeedBackDetailActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ImageShowActivity.Companion.startActivity(FeedBackDetailActivity.this, arrayList, i);
            }
        });
    }
}
