package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.ImageVerifyDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ExchangeList;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.ClearEditText;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import org.slf4j.Marker;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_exchange)
/* renamed from: com.one.tomato.ui.mine.MyExchangeActivity */
/* loaded from: classes3.dex */
public class MyExchangeActivity extends BaseRecyclerViewActivity {
    private BaseRecyclerViewAdapter<ExchangeList> adapter;
    @ViewInject(R.id.et_input)
    private ClearEditText et_input;
    private ImageVerifyDialog imageVerifyDialog;
    @ViewInject(R.id.iv_head)
    private ImageView iv_head;
    @ViewInject(R.id.tv_confirm)
    private TextView tv_confirm;
    @ViewInject(R.id.tv_nick)
    private TextView tv_nick;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MyExchangeActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.exchange_name);
        init();
        initAdapter();
        refresh();
    }

    private void init() {
        UserInfo userInfo = DBUtil.getUserInfo();
        ImageLoaderUtil.loadHeadImage(this, this.iv_head, new ImageBean(userInfo.getAvatar()));
        this.tv_nick.setText(AppUtil.getString(R.string.exchange_nick, userInfo.getName()));
        this.tv_confirm.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyExchangeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!TextUtils.isEmpty(MyExchangeActivity.this.et_input.getText().toString())) {
                    MyExchangeActivity.this.showImageVerifyDialog();
                } else {
                    ToastUtil.showCenterToast((int) R.string.exchange_input_hint);
                }
            }
        });
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.MyExchangeActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyExchangeListActivity.startActivity(((BaseActivity) MyExchangeActivity.this).mContext);
            }
        });
    }

    private void initAdapter() {
        this.adapter = new BaseRecyclerViewAdapter<ExchangeList>(this, R.layout.item_exchange, this.recyclerView) { // from class: com.one.tomato.ui.mine.MyExchangeActivity.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ExchangeList exchangeList) {
                String string;
                super.convert(baseViewHolder, (BaseViewHolder) exchangeList);
                TextView textView = (TextView) baseViewHolder.getView(R.id.tv_result);
                ((TextView) baseViewHolder.getView(R.id.tv_code)).setText(AppUtil.getString(R.string.exchange_code) + ConstantUtils.PLACEHOLDER_STR_ONE + exchangeList.getRedeemCode());
                ((TextView) baseViewHolder.getView(R.id.tv_time)).setText(exchangeList.getCreateTime());
                int exchangeType = exchangeList.getExchangeType();
                if (exchangeType == 1) {
                    string = AppUtil.getString(R.string.common_potato_virtual);
                } else if (exchangeType == 2) {
                    string = AppUtil.getString(R.string.common_potato_currency);
                } else {
                    string = exchangeType != 3 ? "" : AppUtil.getString(R.string.common_potato_expire);
                }
                ViewUtil.initTextViewWithSpannableString(textView, new String[]{Marker.ANY_NON_NULL_MARKER + exchangeList.getExchangeNum(), string}, new String[]{String.valueOf(MyExchangeActivity.this.getResources().getColor(R.color.colorAccent)), String.valueOf(Color.parseColor("#999EAD"))}, new String[]{"16", "12"});
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                MyExchangeActivity.this.refresh();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                MyExchangeActivity.this.loadMore();
            }
        };
        this.recyclerView.setAdapter(this.adapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void refresh() {
        super.refresh();
        this.adapter.setEmptyViewState(0, null);
        getListFromServer(1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity
    public void loadMore() {
        super.loadMore();
        getListFromServer(2);
    }

    private void getListFromServer(int i) {
        if (i == 1) {
            this.pageNo = 1;
        }
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/redeem/queryExchangeHistory");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pageNo", Integer.valueOf(this.pageNo));
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf(this.pageSize));
        tomatoParams.post(new TomatoCallback(this, 1, new TypeToken<ArrayList<ExchangeList>>(this) { // from class: com.one.tomato.ui.mine.MyExchangeActivity.4
        }.getType(), i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showImageVerifyDialog() {
        ImageVerifyDialog imageVerifyDialog = this.imageVerifyDialog;
        if (imageVerifyDialog == null) {
            this.imageVerifyDialog = new ImageVerifyDialog(this, AppUtil.getString(R.string.exchange_dialog_title));
        } else {
            imageVerifyDialog.show();
        }
        this.imageVerifyDialog.getVerifyImage();
        this.imageVerifyDialog.setImageVerifyConfirmListener(new ImageVerifyDialog.ImageVerifyConfirmListener() { // from class: com.one.tomato.ui.mine.MyExchangeActivity.5
            @Override // com.one.tomato.dialog.ImageVerifyDialog.ImageVerifyConfirmListener
            public void imageVerifyConfirm(String str) {
                MyExchangeActivity.this.exchangeConfirm(str);
            }
        });
    }

    public void exchangeConfirm(String str) {
        hideKeyBoard(this);
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/redeem/codeExchange");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("redeemCode", this.et_input.getText().toString().trim());
        tomatoParams.addParameter("verifyCode", str);
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            hideWaitingDialog();
            updateData(message.arg1, (ArrayList) baseModel.obj);
        } else if (i != 2) {
        } else {
            this.et_input.setText("");
            refresh();
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 1) {
            if (message.arg1 != 1) {
                this.adapter.loadMoreFail();
            }
            if (this.adapter.getData().size() == 0) {
                this.adapter.setEmptyViewState(1, null);
            }
        } else if (i == 2) {
            hideWaitingDialog();
        }
        return true;
    }

    private void updateData(int i, ArrayList<ExchangeList> arrayList) {
        boolean z = true;
        if (arrayList == null || arrayList.isEmpty()) {
            if (i == 1) {
                this.adapter.setEmptyViewState(2, null);
            }
            if (i != 2) {
                return;
            }
            this.adapter.loadMoreEnd();
            return;
        }
        if (i == 1) {
            this.pageNo = 2;
            this.adapter.setNewData(arrayList);
        } else {
            this.pageNo++;
            this.adapter.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            this.adapter.loadMoreComplete();
        } else {
            this.adapter.loadMoreEnd();
        }
    }
}
