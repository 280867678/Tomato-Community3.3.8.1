package com.tomatolive.library.p136ui.activity.live;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.http.ApiService;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.ReportTypeEntity;
import com.tomatolive.library.p136ui.adapter.ReportTypeAdapter;
import com.tomatolive.library.p136ui.presenter.ReportLivePresenter;
import com.tomatolive.library.p136ui.view.dialog.alert.WarnDialog;
import com.tomatolive.library.p136ui.view.iview.IReportLiveView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.live.ReportLiveActivity */
/* loaded from: classes3.dex */
public class ReportLiveActivity extends BaseActivity<ReportLivePresenter> implements IReportLiveView {
    private AnchorEntity anchorEntity;
    private EditText etCode;
    private EditText etContent;
    private ImageView ivCodeImg;
    private ImageView ivImg;
    private ReportTypeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String reportTypeCode;
    private Bitmap screenBitmap;
    private TextView tvLiveTitle;
    private TextView tvNumber;
    private TextView tvReport;
    private TextView tvReported;
    private TextView tvSubmit;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public ReportLivePresenter mo6636createPresenter() {
        return new ReportLivePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_report_live;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initImmersionBar() {
        this.mImmersionBar = ImmersionBar.with(this);
        ImmersionBar immersionBar = this.mImmersionBar;
        immersionBar.transparentStatusBar();
        immersionBar.statusBarView(findViewById(R$id.title_top_view));
        immersionBar.keyboardEnable(true);
        immersionBar.statusBarDarkFont(true, ImmersionBar.isSupportStatusBarDarkFont() ? 0.0f : 0.2f);
        immersionBar.init();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.tvNumber = (TextView) findViewById(R$id.tv_number);
        this.tvReport = (TextView) findViewById(R$id.tv_report);
        this.tvReported = (TextView) findViewById(R$id.tv_reported);
        this.tvLiveTitle = (TextView) findViewById(R$id.tv_live_title);
        this.tvSubmit = (TextView) findViewById(R$id.tv_submit);
        this.etContent = (EditText) findViewById(R$id.et_content);
        this.etCode = (EditText) findViewById(R$id.et_code);
        this.ivImg = (ImageView) findViewById(R$id.iv_img);
        this.ivCodeImg = (ImageView) findViewById(R$id.iv_code_img);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        setActivityTitle(R$string.fq_report_live_content);
        initAdapter();
        ((ReportLivePresenter) this.mPresenter).getReportTypeList();
        this.anchorEntity = (AnchorEntity) getIntent().getParcelableExtra(ConstantUtils.RESULT_ITEM);
        if (this.anchorEntity != null) {
            this.tvReport.setText(Html.fromHtml(getString(R$string.fq_report_personal, new Object[]{UserInfoManager.getInstance().getUserNickname()})));
            TextView textView = this.tvReported;
            int i = R$string.fq_reported;
            AnchorEntity anchorEntity = this.anchorEntity;
            textView.setText(Html.fromHtml(getString(i, new Object[]{anchorEntity.nickname, anchorEntity.liveId})));
            this.tvLiveTitle.setText(Html.fromHtml(getString(R$string.fq_report_live_title, new Object[]{this.anchorEntity.topic})));
        }
        GlideUtils.loadImage(this.mContext, this.ivCodeImg, getCodeUrl());
        this.screenBitmap = ImageUtils.getBitmap(AppUtils.getScreenshotPath());
        Bitmap bitmap = this.screenBitmap;
        if (bitmap != null) {
            this.ivImg.setImageBitmap(bitmap);
        } else {
            this.ivImg.setImageResource(R$drawable.fq_ic_placeholder_screen);
        }
    }

    private void initAdapter() {
        this.mAdapter = new ReportTypeAdapter(R$layout.fq_item_grid_report_type);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$ReportLiveActivity$2sQikX4ff5fRatYMHWP4LN_NAwA
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ReportLiveActivity.this.lambda$initListener$0$ReportLiveActivity(baseQuickAdapter, view, i);
            }
        });
        RxTextView.textChanges(this.etContent).map($$Lambda$ReportLiveActivity$4MYKymlVR0HU73Q7YhkzmHsnBsU.INSTANCE).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Integer>() { // from class: com.tomatolive.library.ui.activity.live.ReportLiveActivity.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Integer num) {
                ReportLiveActivity.this.tvNumber.setText(num + "/100");
            }
        });
        RxTextView.textChanges(this.etCode).map($$Lambda$ReportLiveActivity$WhgroP6Ut9RP6eWoLFhFB85U1go.INSTANCE).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.activity.live.ReportLiveActivity.2
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                ReportLiveActivity.this.tvSubmit.setEnabled(bool.booleanValue());
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.ivCodeImg, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$ReportLiveActivity$wnwOJ5rGZXRhZJGKBYblvl1Ja5E
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                ReportLiveActivity.this.lambda$initListener$3$ReportLiveActivity(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvSubmit, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$ReportLiveActivity$mFbCzXL6MefF57tI2iPfWwwc9FU
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                ReportLiveActivity.this.lambda$initListener$4$ReportLiveActivity(obj);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$ReportLiveActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ReportTypeEntity reportTypeEntity = (ReportTypeEntity) baseQuickAdapter.getItem(i);
        if (reportTypeEntity == null) {
            return;
        }
        this.reportTypeCode = reportTypeEntity.code;
        this.mAdapter.setSelectedPosition(i);
    }

    public /* synthetic */ void lambda$initListener$3$ReportLiveActivity(Object obj) {
        GlideUtils.loadImage(this.mContext, this.ivCodeImg, getCodeUrl());
    }

    public /* synthetic */ void lambda$initListener$4$ReportLiveActivity(Object obj) {
        if (this.anchorEntity == null) {
            return;
        }
        String trim = this.etCode.getText().toString().trim();
        String str = this.anchorEntity.userId;
        String trim2 = this.etContent.getText().toString().trim();
        if (EmojiFilter.containsEmoji(trim2) || EmojiFilter.containsEmoji(trim)) {
            showToast(R$string.fq_input_content_emoji_tips);
        } else {
            ((ReportLivePresenter) this.mPresenter).submitReport(str, this.reportTypeCode, trim2, trim);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IReportLiveView
    public void onReportTypeListSuccess(List<ReportTypeEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.reportTypeCode = list.get(0).code;
        this.mAdapter.setNewData(list);
        this.mAdapter.setSelectedPosition(0);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IReportLiveView
    public void onReportSuccess() {
        FileUtils.deleteFile(AppUtils.getScreenshotPath());
        WarnDialog.newInstance(WarnDialog.REPORT_TIP, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$ReportLiveActivity$DAsNIKtec5eqqmWkQ7WmDqgNEjM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReportLiveActivity.this.lambda$onReportSuccess$5$ReportLiveActivity(view);
            }
        }).show(getSupportFragmentManager());
    }

    public /* synthetic */ void lambda$onReportSuccess$5$ReportLiveActivity(View view) {
        finish();
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        FileUtils.deleteFile(AppUtils.getScreenshotPath());
        Bitmap bitmap = this.screenBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.screenBitmap.recycle();
        this.screenBitmap = null;
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        super.onBackPressed();
    }

    private String getCodeUrl() {
        return AppUtils.getApiURl() + ApiService.BASE_TL_REPORT_SERVER_URL + "verifyCode?" + StringUtils.getRandomString(5);
    }
}
