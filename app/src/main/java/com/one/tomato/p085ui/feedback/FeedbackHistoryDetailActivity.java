package com.one.tomato.p085ui.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.one.tomato.entity.InteractiveSysBean;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.NoScrollGridView;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_feedback_history_detail)
/* renamed from: com.one.tomato.ui.feedback.FeedbackHistoryDetailActivity */
/* loaded from: classes3.dex */
public class FeedbackHistoryDetailActivity extends BaseActivity {
    private static InteractiveSysBean.SystemNoticeListBean mydata;
    @ViewInject(R.id.gridView)
    NoScrollGridView gridView;
    @ViewInject(R.id.suggest_back_context_t)
    TextView suggest_back_context_t;
    @ViewInject(R.id.suggest_back_time_t)
    TextView suggest_back_time_t;
    @ViewInject(R.id.suggest_repaly_context_t)
    TextView suggest_repaly_context_t;
    @ViewInject(R.id.suggest_repaly_time_t)
    TextView suggest_repaly_time_t;
    @ViewInject(R.id.suggest_repaly_time_ts)
    TextView suggest_repaly_time_ts;

    public static void startActivity(Context context, InteractiveSysBean.SystemNoticeListBean systemNoticeListBean) {
        mydata = systemNoticeListBean;
        Intent intent = new Intent();
        intent.setClass(context, FeedbackHistoryDetailActivity.class);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        initView();
    }

    private void initView() {
        String string;
        if (mydata.getType().equals("2")) {
            if (TextUtils.isEmpty(mydata.getReply())) {
                string = AppUtil.getString(R.string.feedback_history_title2);
            } else {
                string = AppUtil.getString(R.string.feedback_history_title3);
            }
        } else if (TextUtils.isEmpty(mydata.getReply())) {
            string = AppUtil.getString(R.string.feedback_history_title4);
        } else {
            string = AppUtil.getString(R.string.feedback_history_title5);
        }
        this.titleTV.setText(string);
        this.suggest_back_context_t.setText(mydata.getNotice_content());
        this.suggest_back_time_t.setText(mydata.getCreate_time());
        if (mydata.getReply_time().equals("")) {
            this.suggest_repaly_time_ts.setVisibility(8);
            this.suggest_repaly_time_t.setVisibility(8);
            this.suggest_repaly_context_t.setVisibility(8);
        }
        this.suggest_repaly_time_t.setText(mydata.getReply_time());
        this.suggest_repaly_context_t.setText(mydata.getReply());
        final ArrayList arrayList = new ArrayList();
        try {
            if (!TextUtils.isEmpty(mydata.getImage_url())) {
                String[] split = mydata.getImage_url().split(";");
                if (!split[0].equals("")) {
                    for (String str : split) {
                        ImageBean imageBean = new ImageBean();
                        imageBean.setImage(str);
                        arrayList.add(imageBean);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonBaseAdapter<ImageBean> commonBaseAdapter = new CommonBaseAdapter<ImageBean>(this, this, arrayList, R.layout.itme_suggest) { // from class: com.one.tomato.ui.feedback.FeedbackHistoryDetailActivity.1
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
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackHistoryDetailActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ImageShowActivity.Companion.startActivity(FeedbackHistoryDetailActivity.this, arrayList, i);
            }
        });
    }
}
