package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.PapaReportSection;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.tomatolive.library.utils.LogConstants;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_papa_report)
/* renamed from: com.one.tomato.mvp.ui.papa.view.PapaReportActivity */
/* loaded from: classes3.dex */
public class PapaReportActivity extends BaseActivity {
    @ViewInject(R.id.et_input)
    private EditText et_input;

    /* renamed from: id */
    private int f1749id;
    private String imgUrl;
    @ViewInject(R.id.recyclerView_report)
    private RecyclerView recyclerView_report;
    @ViewInject(R.id.recyclerView_upload)
    private RecyclerView recyclerView_upload;
    private BaseRecyclerSectionAdapter<PapaReportSection> reportAdapter;
    private SelectGridImageAdapter selectGridImageAdapter;
    private SelectPicTypeUtil selectPicTypeUtil;
    private TTUtil ttUtil;
    @ViewInject(R.id.tv_desc_length)
    private TextView tv_desc_length;
    @ViewInject(R.id.tv_desc_tip)
    private TextView tv_desc_tip;
    private int reportItem = -1;
    private List<PapaReportSection> reportSections = new ArrayList();
    private List<LocalMedia> selectList = new ArrayList();
    private ArrayList<LocalMedia> uploadSuccessList = new ArrayList<>();

    public static void startActivity(Context context, int i) {
        Intent intent = new Intent(context, PapaReportActivity.class);
        intent.putExtra(DatabaseFieldConfigLoader.FIELD_NAME_ID, i);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f1749id = getIntent().getExtras().getInt(DatabaseFieldConfigLoader.FIELD_NAME_ID);
        initTitleBar();
        initView();
        initReportAdapter();
        initGridImgAdapter();
    }

    private void initView() {
        this.titleTV.setText(R.string.report_papa);
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.common_commit);
        this.et_input.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.papa.view.PapaReportActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PapaReportActivity.this.tv_desc_length.setText(AppUtil.getString(R.string.report_desc_length, Integer.valueOf(editable.toString().length()), 200));
            }
        });
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PapaReportActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PapaReportActivity.this.selectList.size() > 0) {
                    PapaReportActivity.this.uploadImg();
                } else {
                    PapaReportActivity.this.commit();
                }
            }
        });
    }

    private void initReportAdapter() {
        this.recyclerView_report.setLayoutManager(new BaseLinearLayoutManager(this, 1, false));
        String[] stringArray = getResources().getStringArray(R.array.report_papa_item_content1);
        String[] stringArray2 = getResources().getStringArray(R.array.report_papa_item_content2);
        String[] stringArray3 = getResources().getStringArray(R.array.report_papa_item_content3);
        String[] stringArray4 = getResources().getStringArray(R.array.report_papa_item_content4);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(AppUtil.getString(R.string.report_papa_item_title1), stringArray);
        linkedHashMap.put(AppUtil.getString(R.string.report_papa_item_title2), stringArray2);
        linkedHashMap.put(AppUtil.getString(R.string.report_papa_item_title3), stringArray3);
        linkedHashMap.put(AppUtil.getString(R.string.report_papa_item_title4), stringArray4);
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String[] strArr = (String[]) entry.getValue();
            this.reportSections.add(new PapaReportSection(true, (String) entry.getKey()));
            for (String str : strArr) {
                this.reportSections.add(new PapaReportSection(str));
            }
        }
        this.reportAdapter = new BaseRecyclerSectionAdapter<PapaReportSection>(this, R.layout.item_report_post_or_member, R.layout.item_report_content_title, this.reportSections, this.recyclerView_report) { // from class: com.one.tomato.mvp.ui.papa.view.PapaReportActivity.3
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void convertHead(BaseViewHolder baseViewHolder, PapaReportSection papaReportSection) {
                ((TextView) baseViewHolder.getView(R.id.tv_title)).setText(papaReportSection.header);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, PapaReportSection papaReportSection) {
                int indexOf = this.mData.indexOf(papaReportSection);
                CheckBox checkBox = (CheckBox) baseViewHolder.getView(R.id.checkbox);
                ((TextView) baseViewHolder.getView(R.id.textView)).setText((CharSequence) papaReportSection.f1223t);
                if (PapaReportActivity.this.reportItem == indexOf) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerSectionAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i);
                if (PapaReportActivity.this.reportItem == i || ((PapaReportSection) this.mData.get(i)).isHeader) {
                    return;
                }
                PapaReportActivity.this.reportItem = i;
                PapaReportActivity.this.reportAdapter.notifyDataSetChanged();
                if (PapaReportActivity.this.reportItem == this.mData.size() - 1) {
                    PapaReportActivity.this.tv_desc_tip.setText(R.string.report_desc_tip_y);
                } else {
                    PapaReportActivity.this.tv_desc_tip.setText(R.string.report_desc_tip_n);
                }
            }
        };
        this.recyclerView_report.setAdapter(this.reportAdapter);
        this.reportAdapter.setEnableLoadMore(false);
    }

    private void initGridImgAdapter() {
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
        this.recyclerView_upload.setLayoutManager(new FullyGridLayoutManager(this, 4, 1, false));
        this.selectGridImageAdapter = new SelectGridImageAdapter(this);
        this.selectGridImageAdapter.setAdd_mark_img_bg_id(getResources().getColor(R.color.white));
        this.selectGridImageAdapter.setSelectMax(4);
        this.recyclerView_upload.setAdapter(this.selectGridImageAdapter);
        this.selectGridImageAdapter.setOnItemClickListener(new SelectGridImageAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PapaReportActivity.4
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemClickListener
            public void onItemClick(int i, View view) {
                PictureSelector.create(PapaReportActivity.this).themeStyle(2131821197).openExternalPreview(i, PapaReportActivity.this.selectList);
            }
        });
        this.selectGridImageAdapter.setOnAddPicClickListener(new SelectGridImageAdapter.OnAddPicClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PapaReportActivity.5
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnAddPicClickListener
            public void onAddPicClick() {
                PapaReportActivity.this.selectPicTypeUtil.selectCommonPhoto(4, false, false, false, PapaReportActivity.this.selectList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commit() {
        int i;
        if (this.reportItem == -1) {
            ToastUtil.showCenterToast((int) R.string.report_select_first);
            return;
        }
        String trim = this.et_input.getText().toString().trim();
        if (this.reportItem == this.reportSections.size() - 1 && TextUtils.isEmpty(trim)) {
            ToastUtil.showCenterToast((int) R.string.report_desc_tip_y);
            return;
        }
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/record/report/add");
        tomatoParams.addParameter("reportId", Integer.valueOf(this.f1749id));
        int i2 = this.reportItem;
        if (i2 < 0 || i2 > 5) {
            int i3 = this.reportItem;
            if (i3 < 6 || i3 > 8) {
                int i4 = this.reportItem;
                if (i4 < 9 || i4 > 10) {
                    int i5 = this.reportItem;
                    i = 4;
                    i2 = i5 == this.reportSections.size() - 1 ? 0 : i5 - 3;
                } else {
                    i2 = i4 - 2;
                    i = 3;
                }
            } else {
                i2 = i3 - 1;
                i = 2;
            }
        } else {
            i = 1;
        }
        tomatoParams.addParameter("type", Integer.valueOf(i2));
        tomatoParams.addParameter("typeGroup", Integer.valueOf(i));
        tomatoParams.addParameter(LogConstants.ENTER_SOURCE, 3);
        tomatoParams.addParameter("reason", trim);
        tomatoParams.addParameter("imageUrl", this.imgUrl);
        tomatoParams.addParameter("createMemberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadImg() {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.papa.view.PapaReportActivity.6
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                PapaReportActivity.this.showWaitingDialog();
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia localMedia) {
                PapaReportActivity.this.uploadSuccessList.add(localMedia);
                if (PapaReportActivity.this.uploadSuccessList.size() == PapaReportActivity.this.selectList.size()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < PapaReportActivity.this.uploadSuccessList.size(); i++) {
                        String ceph = PapaReportActivity.this.ttUtil.getCeph((LocalMedia) PapaReportActivity.this.uploadSuccessList.get(i));
                        sb.append("/");
                        sb.append(PapaReportActivity.this.ttUtil.getBucketName());
                        sb.append("/");
                        sb.append(ceph);
                        if (i < PapaReportActivity.this.uploadSuccessList.size() - 1) {
                            sb.append(";");
                        }
                    }
                    PapaReportActivity.this.imgUrl = sb.toString();
                    PapaReportActivity.this.commit();
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                PapaReportActivity.this.hideWaitingDialog();
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        this.ttUtil.getStsToken(this.selectList);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 2) {
            return;
        }
        ToastUtil.showCenterToast((int) R.string.report_success);
        finish();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 188) {
            List<LocalMedia> obtainMultipleResult = PictureSelector.obtainMultipleResult(intent);
            for (LocalMedia localMedia : obtainMultipleResult) {
                Log.i("图片-----》", localMedia.getPath());
            }
            this.selectList.clear();
            this.selectList.addAll(obtainMultipleResult);
            this.selectGridImageAdapter.setList(this.selectList);
            this.selectGridImageAdapter.notifyDataSetChanged();
        }
    }
}
