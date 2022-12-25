package com.one.tomato.p085ui.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.media.ExifInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ExpireRecord;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.level.MyLevelActivity;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ViewUtil;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import org.slf4j.Marker;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_expire_record_info)
/* renamed from: com.one.tomato.ui.task.ExpireRecordInfoActivity */
/* loaded from: classes3.dex */
public class ExpireRecordInfoActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<ExpireRecord> expireAdapter;
    @ViewInject(R.id.ll_level)
    private LinearLayout ll_level;
    @ViewInject(R.id.tv_current_expire)
    private TextView tv_current_expire;
    @ViewInject(R.id.tv_level_current)
    private TextView tv_level_current;
    @ViewInject(R.id.tv_level_next)
    private TextView tv_level_next;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ExpireRecordInfoActivity.class);
        context.startActivity(intent);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
    }

    private void initView() {
        initTitleBar();
        this.titleTV.setText(R.string.expire_record_title);
        LevelBean levelBean = DBUtil.getLevelBean();
        TextView textView = this.tv_current_expire;
        textView.setText(levelBean.getCurrentLevelValue() + "");
        TextView textView2 = this.tv_level_current;
        textView2.setText("V." + levelBean.getCurrentLevelIndex() + ConstantUtils.PLACEHOLDER_STR_ONE + AppUtil.getString(R.string.expire_record_title) + levelBean.getCurrentLevelValue() + " / " + levelBean.getNextLevelValue());
        TextView textView3 = this.tv_level_next;
        StringBuilder sb = new StringBuilder();
        sb.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        sb.append(levelBean.getCurrentLevelIndex() + 1);
        textView3.setText(sb.toString());
        this.ll_level.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.task.ExpireRecordInfoActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyLevelActivity.Companion.startActivity(((BaseActivity) ExpireRecordInfoActivity.this).mContext);
            }
        });
        initAdapter();
        refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
        getListFromServer(1);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
        getListFromServer(2);
    }

    private void initAdapter() {
        this.expireAdapter = new BaseRecyclerViewAdapter<ExpireRecord>(this, R.layout.item_expire_record, this.recyclerView) { // from class: com.one.tomato.ui.task.ExpireRecordInfoActivity.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ExpireRecord expireRecord) {
                super.convert(baseViewHolder, (BaseViewHolder) expireRecord);
                View view = baseViewHolder.getView(R.id.divider);
                ((TextView) baseViewHolder.getView(R.id.tv_title)).setText(expireRecord.getContent());
                ((TextView) baseViewHolder.getView(R.id.tv_time)).setText(expireRecord.getUpdateTime());
                ViewUtil.initTextViewWithSpannableString((TextView) baseViewHolder.getView(R.id.tv_num), new String[]{Marker.ANY_NON_NULL_MARKER + expireRecord.getAmount() + ConstantUtils.PLACEHOLDER_STR_ONE, ExpireRecordInfoActivity.this.getResources().getString(R.string.expire_record_title)}, new String[]{String.valueOf(ExpireRecordInfoActivity.this.getResources().getColor(R.color.colorAccent)), String.valueOf(ExpireRecordInfoActivity.this.getResources().getColor(R.color.text_light))}, new String[]{"16", "12"});
                if (this.mData.indexOf(expireRecord) == 0) {
                    view.setVisibility(8);
                } else {
                    view.setVisibility(0);
                }
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                ExpireRecordInfoActivity.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                ExpireRecordInfoActivity.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.expireAdapter);
    }

    private void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
            showWaitingDialog();
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/task/expDetail");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.get(new TomatoCallback(this, 1, new TypeToken<ArrayList<ExpireRecord>>(this) { // from class: com.one.tomato.ui.task.ExpireRecordInfoActivity.3
        }.getType(), i));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 1) {
            return;
        }
        updateData(message.arg1, (ArrayList) baseModel.obj);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what == 1) {
            if (message.arg1 == 1) {
                this.expireAdapter.setEmptyViewState(1, this.refreshLayout);
            } else {
                this.expireAdapter.loadMoreFail();
            }
            if (this.expireAdapter.getData().size() == 0) {
                this.expireAdapter.setEmptyViewState(1, this.refreshLayout);
            }
        }
        return true;
    }

    private void updateData(int i, ArrayList<ExpireRecord> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.expireAdapter.setEmptyViewState(2, this.refreshLayout);
            }
            if (i != 2) {
                return;
            }
            this.expireAdapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.pageNo = 2;
            this.expireAdapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.expireAdapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.expireAdapter.loadMoreComplete();
        } else {
            this.expireAdapter.loadMoreEnd();
        }
    }
}
