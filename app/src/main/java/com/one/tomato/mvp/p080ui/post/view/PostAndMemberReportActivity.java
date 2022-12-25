package com.one.tomato.mvp.p080ui.post.view;

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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.adapter.CommonBaseAdapter;
import com.one.tomato.adapter.ViewHolder;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.NoScrollListView;
import com.tomatolive.library.utils.LogConstants;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_post_report)
/* renamed from: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity */
/* loaded from: classes3.dex */
public class PostAndMemberReportActivity extends BaseActivity {
    private String businessType;
    private int commentId;
    private int downPostType;
    @ViewInject(R.id.et_input)
    private EditText et_input;

    /* renamed from: id */
    private int f1750id;
    private String imgUrl;
    @ViewInject(R.id.listView)
    private NoScrollListView listView;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    private CommonBaseAdapter<String> reportAdapter;
    private String[] reportItems;
    private SelectGridImageAdapter selectGridImageAdapter;
    private SelectPicTypeUtil selectPicTypeUtil;
    private TTUtil ttUtil;
    @ViewInject(R.id.tv_desc_length)
    private TextView tv_desc_length;
    @ViewInject(R.id.tv_desc_tip)
    private TextView tv_desc_tip;
    private int reportItem = -1;
    private List<LocalMedia> selectList = new ArrayList();
    private ArrayList<LocalMedia> uploadSuccessList = new ArrayList<>();

    public static void startActivity(Context context, String str, int i) {
        Intent intent = new Intent(context, PostAndMemberReportActivity.class);
        intent.putExtra("business", str);
        intent.putExtra(DatabaseFieldConfigLoader.FIELD_NAME_ID, i);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String str, int i, int i2) {
        Intent intent = new Intent(context, PostAndMemberReportActivity.class);
        intent.putExtra("business", str);
        intent.putExtra(DatabaseFieldConfigLoader.FIELD_NAME_ID, i);
        intent.putExtra("down_post", i2);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String str, int i, int i2, int i3) {
        Intent intent = new Intent(context, PostAndMemberReportActivity.class);
        intent.putExtra("business", str);
        intent.putExtra(DatabaseFieldConfigLoader.FIELD_NAME_ID, i);
        intent.putExtra("down_post", i2);
        intent.putExtra("comment_id", i3);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.businessType = getIntent().getExtras().getString("business");
        this.f1750id = getIntent().getExtras().getInt(DatabaseFieldConfigLoader.FIELD_NAME_ID);
        this.downPostType = getIntent().getExtras().getInt("down_post");
        this.commentId = getIntent().getExtras().getInt("comment_id");
        initView();
        initReportAdapter();
        initGridImgAdapter();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void initView() {
        char c;
        String str = this.businessType;
        switch (str.hashCode()) {
            case -1722878014:
                if (str.equals("down_comment")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1077769574:
                if (str.equals("member")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3446944:
                if (str.equals("post")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 950398559:
                if (str.equals("comment")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1300742685:
                if (str.equals("down_post")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.titleTV.setText(R.string.report_post);
            this.reportItems = getResources().getStringArray(R.array.report_post_content);
        } else if (c == 1) {
            this.titleTV.setText(R.string.report_post);
            this.reportItems = getResources().getStringArray(R.array.report_post_content);
        } else if (c == 2) {
            this.titleTV.setText(R.string.report_member);
            this.reportItems = getResources().getStringArray(R.array.report_member_content);
        } else if (c == 3) {
            this.titleTV.setText(AppUtil.getString(R.string.post_down_reason_title));
            this.reportItems = getResources().getStringArray(R.array.report_down_post);
            this.recyclerView.setVisibility(8);
            this.tv_desc_tip.setText(AppUtil.getString(R.string.post_comment_down_desc));
            this.et_input.setHint(AppUtil.getString(R.string.post_down_comment_write_reason));
        } else if (c == 4) {
            this.titleTV.setText(AppUtil.getString(R.string.post_down_comment_reason));
            this.reportItems = getResources().getStringArray(R.array.report_down_comment);
            this.recyclerView.setVisibility(8);
            this.tv_desc_tip.setText(AppUtil.getString(R.string.post_comment_down_desc));
            this.et_input.setHint(AppUtil.getString(R.string.post_down_comment_write_reason));
        }
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.common_commit);
        this.et_input.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PostAndMemberReportActivity.this.tv_desc_length.setText(AppUtil.getString(R.string.report_desc_length, Integer.valueOf(editable.toString().length()), 200));
            }
        });
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PostAndMemberReportActivity.this.selectList.size() > 0) {
                    PostAndMemberReportActivity.this.uploadImg();
                } else {
                    PostAndMemberReportActivity.this.commit();
                }
            }
        });
    }

    private void initReportAdapter() {
        this.reportAdapter = new CommonBaseAdapter<String>(this, Arrays.asList(this.reportItems), R.layout.item_report_post_or_member) { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.3
            @Override // com.one.tomato.adapter.CommonBaseAdapter
            public void convert(ViewHolder viewHolder, String str, int i) {
                CheckBox checkBox = (CheckBox) viewHolder.getView(R.id.checkbox);
                ((TextView) viewHolder.getView(R.id.textView)).setText(str);
                if (PostAndMemberReportActivity.this.reportItem == i) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        };
        this.listView.setAdapter((ListAdapter) this.reportAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (PostAndMemberReportActivity.this.reportItem == i) {
                    return;
                }
                PostAndMemberReportActivity.this.reportItem = i;
                PostAndMemberReportActivity.this.reportAdapter.notifyDataSetChanged();
                if (!"member".equals(PostAndMemberReportActivity.this.businessType)) {
                    return;
                }
                if (PostAndMemberReportActivity.this.reportItem == PostAndMemberReportActivity.this.reportItems.length - 1) {
                    PostAndMemberReportActivity.this.tv_desc_tip.setText(R.string.report_desc_tip_y);
                } else {
                    PostAndMemberReportActivity.this.tv_desc_tip.setText(R.string.report_desc_tip_n);
                }
            }
        });
    }

    private void initGridImgAdapter() {
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
        this.recyclerView.setLayoutManager(new FullyGridLayoutManager(this, 4, 1, false));
        this.selectGridImageAdapter = new SelectGridImageAdapter(this);
        this.selectGridImageAdapter.setAdd_mark_img_bg_id(getResources().getColor(R.color.white));
        this.selectGridImageAdapter.setSelectMax(4);
        this.recyclerView.setAdapter(this.selectGridImageAdapter);
        this.selectGridImageAdapter.setOnItemClickListener(new SelectGridImageAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.5
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemClickListener
            public void onItemClick(int i, View view) {
                PictureSelector.create(PostAndMemberReportActivity.this).themeStyle(2131821197).openExternalPreview(i, PostAndMemberReportActivity.this.selectList);
            }
        });
        this.selectGridImageAdapter.setOnAddPicClickListener(new SelectGridImageAdapter.OnAddPicClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.6
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnAddPicClickListener
            public void onAddPicClick() {
                PostAndMemberReportActivity.this.selectPicTypeUtil.selectCommonPhoto(4, false, false, false, PostAndMemberReportActivity.this.selectList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commit() {
        char c = 65535;
        if (this.reportItem == -1) {
            ToastUtil.showCenterToast((int) R.string.report_select_first);
            return;
        }
        String trim = this.et_input.getText().toString().trim();
        int i = 1;
        if (("member".equals(this.businessType) || "down_post".equals(this.businessType) || "down_comment".equals(this.businessType)) && this.reportItem == this.reportItems.length - 1 && TextUtils.isEmpty(trim)) {
            ToastUtil.showCenterToast((int) R.string.report_desc_tip_y);
        } else if ("down_post".equals(this.businessType)) {
            if (this.reportItem == this.reportItems.length - 1) {
                requestDownPost(trim);
                return;
            }
            requestDownPost(this.reportAdapter.getDatas().get(this.reportItem) + "\n" + trim);
        } else if ("down_comment".equals(this.businessType)) {
            if (this.reportItem == this.reportItems.length - 1) {
                requestDownComment(trim);
                return;
            }
            requestDownComment(this.reportAdapter.getDatas().get(this.reportItem) + "\n" + trim);
        } else {
            showWaitingDialog();
            TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/record/report/add");
            tomatoParams.addParameter("reportId", Integer.valueOf(this.f1750id));
            int i2 = this.reportItem + 1;
            if ("member".equals(this.businessType) && this.reportItem == this.reportItems.length - 1) {
                i2 = 0;
            }
            tomatoParams.addParameter("type", Integer.valueOf(i2));
            String str = this.businessType;
            int hashCode = str.hashCode();
            if (hashCode != -1077769574) {
                if (hashCode != 3446944) {
                    if (hashCode == 950398559 && str.equals("comment")) {
                        c = 1;
                    }
                } else if (str.equals("post")) {
                    c = 0;
                }
            } else if (str.equals("member")) {
                c = 2;
            }
            if (c != 0) {
                i = c != 1 ? c != 2 ? 0 : 4 : 2;
            }
            tomatoParams.addParameter(LogConstants.ENTER_SOURCE, Integer.valueOf(i));
            tomatoParams.addParameter("reason", trim);
            tomatoParams.addParameter("imageUrl", this.imgUrl);
            tomatoParams.addParameter("createMemberId", Integer.valueOf(DBUtil.getMemberId()));
            tomatoParams.post(new TomatoCallback(this, 2));
        }
    }

    private void requestDownPost(String str) {
        ApiImplService.Companion.getApiImplService().requestPostDown(this.downPostType, this.f1750id, str).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.8
            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                PostAndMemberReportActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.7
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                PostAndMemberReportActivity.this.hideWaitingDialog();
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_down_sucess));
                PostAndMemberReportActivity.this.onBackPressed();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PostAndMemberReportActivity.this.hideWaitingDialog();
            }
        });
    }

    private void requestDownComment(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("operateType", Integer.valueOf(this.downPostType));
        hashMap.put("operateTableId", Integer.valueOf(this.commentId));
        hashMap.put("remark", str);
        hashMap.put("articleId", Integer.valueOf(this.f1750id));
        ApiImplService.Companion.getApiImplService().requestDownComment(hashMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.10
            @Override // io.reactivex.functions.Consumer
            public void accept(Disposable disposable) throws Exception {
                PostAndMemberReportActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.9
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                PostAndMemberReportActivity.this.hideWaitingDialog();
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_down_sucess));
                PostAndMemberReportActivity.this.onBackPressed();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PostAndMemberReportActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadImg() {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.post.view.PostAndMemberReportActivity.11
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                PostAndMemberReportActivity.this.showWaitingDialog();
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia localMedia) {
                PostAndMemberReportActivity.this.uploadSuccessList.add(localMedia);
                if (PostAndMemberReportActivity.this.uploadSuccessList.size() == PostAndMemberReportActivity.this.selectList.size()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < PostAndMemberReportActivity.this.uploadSuccessList.size(); i++) {
                        String ceph = PostAndMemberReportActivity.this.ttUtil.getCeph((LocalMedia) PostAndMemberReportActivity.this.uploadSuccessList.get(i));
                        sb.append("/");
                        sb.append(PostAndMemberReportActivity.this.ttUtil.getBucketName());
                        sb.append("/");
                        sb.append(ceph);
                        if (i < PostAndMemberReportActivity.this.uploadSuccessList.size() - 1) {
                            sb.append(";");
                        }
                    }
                    PostAndMemberReportActivity.this.imgUrl = sb.toString();
                    PostAndMemberReportActivity.this.commit();
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                PostAndMemberReportActivity.this.hideWaitingDialog();
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
