package com.one.tomato.p085ui.income;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.indexlib.IndexBar.widget.C2277IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.one.tomato.adapter.CountryDividerItemDecoration;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.Bank;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.widget.ClearEditText;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_select_bank)
/* renamed from: com.one.tomato.ui.income.BankSelectListActivity */
/* loaded from: classes3.dex */
public class BankSelectListActivity extends BaseRecyclerViewActivity {
    @ViewInject(R.id.et_input)
    private ClearEditText et_input;
    @ViewInject(R.id.indexBar)
    private C2277IndexBar indexBar;
    private BaseRecyclerViewAdapter<Bank> mAdapter;
    private BaseLinearLayoutManager mManager;
    private SuspensionDecoration suspensionDecoration;
    @ViewInject(R.id.tvSideBarHint)
    private TextView tvSideBarHint;
    private ArrayList<Bank> mDatas = new ArrayList<>();
    private ArrayList<Bank> filterDatas = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BankSelectListActivity.class);
        ((Activity) context).startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.bank_select_title);
        initAdapter();
        getBankList();
        this.et_input.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.BankSelectListActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                BankSelectListActivity.this.filterData(editable.toString().trim());
            }
        });
    }

    private void initAdapter() {
        this.mAdapter = new BaseRecyclerViewAdapter<Bank>(this.mContext, R.layout.item_country, this.recyclerView) { // from class: com.one.tomato.ui.income.BankSelectListActivity.2
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, Bank bank) {
                super.convert(baseViewHolder, (BaseViewHolder) bank);
                ((TextView) baseViewHolder.getView(R.id.textView)).setText(bank.getName());
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                BankSelectListActivity.this.getBankList();
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                Intent intent = new Intent();
                intent.putExtra("intent_result", getItem(i));
                BankSelectListActivity.this.setResult(-1, intent);
                BankSelectListActivity.this.finish();
            }
        };
        this.mManager = new BaseLinearLayoutManager(this, 1, false);
        this.recyclerView.setLayoutManager(this.mManager);
        this.recyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setEnableLoadMore(false);
        this.suspensionDecoration = new SuspensionDecoration(this, this.mDatas);
        this.suspensionDecoration.setColorTitleBg(Color.parseColor("#DFDFDF"));
        this.suspensionDecoration.setColorTitleFont(Color.parseColor("#121213"));
        this.recyclerView.addItemDecoration(this.suspensionDecoration);
        this.recyclerView.addItemDecoration(new CountryDividerItemDecoration(this, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getBankList() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/listBankCard");
        tomatoParams.addParameter("pageNo", 1);
        tomatoParams.addParameter(RequestParams.PAGE_SIZE, Integer.valueOf((int) ConstantUtils.MAX_ITEM_NUM));
        tomatoParams.get(new TomatoCallback(this, 1, new TypeToken<ArrayList<Bank>>(this) { // from class: com.one.tomato.ui.income.BankSelectListActivity.3
        }.getType()));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what == 1) {
            String str = baseModel.data;
            if (!TextUtils.isEmpty(str)) {
                PreferencesUtil.getInstance().putString("bank_list", str);
            }
            ArrayList<Bank> arrayList = (ArrayList) baseModel.obj;
            if (arrayList == null || arrayList.size() == 0) {
                String string = PreferencesUtil.getInstance().getString("bank_list");
                if (TextUtils.isEmpty(string)) {
                    this.mAdapter.setEmptyViewState(2, null);
                    return;
                }
                arrayList = getBankListFormCache(string);
            }
            this.mDatas.clear();
            this.mDatas.addAll(arrayList);
            updateData(arrayList);
        }
    }

    private ArrayList<Bank> getBankListFormCache(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (ArrayList) BaseApplication.getGson().fromJson(str, new TypeToken<ArrayList<Bank>>(this) { // from class: com.one.tomato.ui.income.BankSelectListActivity.4
        }.getType());
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        String string = PreferencesUtil.getInstance().getString("bank_list");
        if (!TextUtils.isEmpty(string)) {
            ArrayList<Bank> bankListFormCache = getBankListFormCache(string);
            this.mDatas.clear();
            this.mDatas.addAll(bankListFormCache);
            updateData(bankListFormCache);
            return true;
        } else if (message.what != 1) {
            return false;
        } else {
            this.mAdapter.setEmptyViewState(1, null);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateData(ArrayList<Bank> arrayList) {
        this.mAdapter.setNewData(arrayList);
        this.mAdapter.setEnableLoadMore(false);
        this.indexBar.setmPressedShowTextView(this.tvSideBarHint).setNeedRealIndex(false).setmLayoutManager(this.mManager);
        this.indexBar.setVisibility(0);
        this.indexBar.setmSourceDatas(arrayList).invalidate();
        this.suspensionDecoration.setmDatas(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterData(final String str) {
        this.recyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.ui.income.BankSelectListActivity.5
            @Override // java.lang.Runnable
            public void run() {
                if (!TextUtils.isEmpty(str)) {
                    BankSelectListActivity.this.filterDatas.clear();
                    for (int i = 0; i < BankSelectListActivity.this.mDatas.size(); i++) {
                        Bank bank = (Bank) BankSelectListActivity.this.mDatas.get(i);
                        if (bank.getName().contains(str)) {
                            BankSelectListActivity.this.filterDatas.add(bank);
                        }
                    }
                    BankSelectListActivity bankSelectListActivity = BankSelectListActivity.this;
                    bankSelectListActivity.updateData(bankSelectListActivity.filterDatas);
                } else {
                    BankSelectListActivity bankSelectListActivity2 = BankSelectListActivity.this;
                    bankSelectListActivity2.updateData(bankSelectListActivity2.mDatas);
                }
                BankSelectListActivity bankSelectListActivity3 = BankSelectListActivity.this;
                bankSelectListActivity3.hideKeyBoard((Activity) ((BaseActivity) bankSelectListActivity3).mContext);
            }
        }, 500L);
    }
}
