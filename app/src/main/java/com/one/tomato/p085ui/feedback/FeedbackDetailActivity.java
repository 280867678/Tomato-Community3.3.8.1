package com.one.tomato.p085ui.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.p005v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.NetWorkUtil;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_report)
/* renamed from: com.one.tomato.ui.feedback.FeedbackDetailActivity */
/* loaded from: classes3.dex */
public class FeedbackDetailActivity extends BaseActivity {
    private String businessType;
    private String commitUrl;
    @ViewInject(R.id.connect_e)
    EditText connect_e;
    @ViewInject(R.id.et_input)
    ClearEditText et_input;
    @ViewInject(R.id.iv_camera)
    View iv_camera;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    private SelectGridImageAdapter selectGridImageAdapter;
    private SelectPicTypeUtil selectPicTypeUtil;
    private String title;
    private TTUtil ttUtil;
    @ViewInject(R.id.tv_input_num)
    TextView tv_input_num;
    private List<LocalMedia> selectList = new ArrayList();
    private boolean isImgUpload = false;
    private String imgUrl = "";
    private int feedbackType = 0;
    private ArrayList<LocalMedia> uploadSuccessList = new ArrayList<>();

    public static void startActivity(Context context, String str, String str2) {
        Intent intent = new Intent();
        intent.setClass(context, FeedbackDetailActivity.class);
        intent.putExtra("businessType", str);
        intent.putExtra("title", str2);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.businessType = getIntent().getExtras().getString("businessType");
        this.title = getIntent().getExtras().getString("title");
        initTitleBar();
        this.titleTV.setText(this.title);
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.report_sub_tile);
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackDetailActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int length = FeedbackDetailActivity.this.et_input.getText().toString().trim().length();
                if (length < 10) {
                    ToastUtil.showCenterToast((int) R.string.report_submit_tip_length1);
                } else if (length <= 200) {
                    FeedbackDetailActivity.this.judge();
                } else {
                    ToastUtil.showCenterToast((int) R.string.report_submit_tip_length2);
                }
            }
        });
        initView();
        initGridImgAdapter();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void initView() {
        char c;
        String string;
        String str = this.businessType;
        switch (str.hashCode()) {
            case -1863356540:
                if (str.equals("suggest")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -934521548:
                if (str.equals("report")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -806191449:
                if (str.equals("recharge")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3046195:
                if (str.equals("cash")) {
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
            this.commitUrl = "/app/newReport/save";
            this.feedbackType = 0;
            string = AppUtil.getString(R.string.report_submit_reason1);
        } else if (c == 1) {
            this.commitUrl = "/app/newFeedback/save";
            this.feedbackType = 0;
            string = AppUtil.getString(R.string.report_submit_reason2);
        } else if (c == 2) {
            this.commitUrl = "/app/newFeedback/save";
            this.feedbackType = 1;
            string = AppUtil.getString(R.string.report_submit_reason2);
        } else if (c != 3) {
            string = "";
        } else {
            this.commitUrl = "/app/newFeedback/save";
            this.feedbackType = 2;
            string = AppUtil.getString(R.string.report_submit_reason_cash);
        }
        this.et_input.setHint(string);
        this.tv_input_num.setText(AppUtil.getString(R.string.update_report_text_num, 0));
        this.et_input.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.feedback.FeedbackDetailActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                FeedbackDetailActivity.this.tv_input_num.setText(AppUtil.getString(R.string.update_report_text_num, Integer.valueOf(FeedbackDetailActivity.this.et_input.getText().toString().trim().length())));
            }
        });
        this.iv_camera.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackDetailActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                FeedbackDetailActivity.this.selectPicTypeUtil.selectCommonPhoto(9, false, false, false, FeedbackDetailActivity.this.selectList);
            }
        });
    }

    private void initGridImgAdapter() {
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
        this.recyclerView.setLayoutManager(new FullyGridLayoutManager(this, 3, 1, false));
        this.selectGridImageAdapter = new SelectGridImageAdapter(this);
        this.selectGridImageAdapter.setList(this.selectList);
        this.selectGridImageAdapter.setSelectMax(9);
        this.recyclerView.setAdapter(this.selectGridImageAdapter);
        this.selectGridImageAdapter.setOnItemClickListener(new SelectGridImageAdapter.OnItemClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackDetailActivity.4
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemClickListener
            public void onItemClick(int i, View view) {
                if (FeedbackDetailActivity.this.selectList.size() <= 0 || PictureMimeType.pictureToVideo(((LocalMedia) FeedbackDetailActivity.this.selectList.get(i)).getPictureType()) != 1) {
                    return;
                }
                PictureSelector.create(FeedbackDetailActivity.this).themeStyle(2131821197).openExternalPreview(i, FeedbackDetailActivity.this.selectList);
            }
        });
        this.selectGridImageAdapter.setOnItemChangeListener(new SelectGridImageAdapter.OnItemChangeListener() { // from class: com.one.tomato.ui.feedback.FeedbackDetailActivity.5
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemChangeListener
            public void onItemRemove(int i) {
            }

            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnItemChangeListener
            public void onItemClear() {
                FeedbackDetailActivity.this.selectList.clear();
            }
        });
        this.selectGridImageAdapter.setOnAddPicClickListener(new SelectGridImageAdapter.OnAddPicClickListener() { // from class: com.one.tomato.ui.feedback.FeedbackDetailActivity.6
            @Override // com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.OnAddPicClickListener
            public void onAddPicClick() {
                FeedbackDetailActivity.this.selectPicTypeUtil.selectCommonPhoto(9, false, false, false, FeedbackDetailActivity.this.selectList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void judge() {
        if (this.selectList.size() == 0) {
            commit();
        } else if (this.selectList != null && this.isImgUpload) {
            commit();
        } else {
            uploadImg();
        }
    }

    private void uploadImg() {
        this.ttUtil = new TTUtil(1, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.ui.feedback.FeedbackDetailActivity.7
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                FeedbackDetailActivity.this.showWaitingDialog();
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia localMedia) {
                FeedbackDetailActivity.this.uploadSuccessList.add(localMedia);
                if (FeedbackDetailActivity.this.uploadSuccessList.size() == FeedbackDetailActivity.this.selectList.size()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < FeedbackDetailActivity.this.uploadSuccessList.size(); i++) {
                        String ceph = FeedbackDetailActivity.this.ttUtil.getCeph((LocalMedia) FeedbackDetailActivity.this.uploadSuccessList.get(i));
                        sb.append("/");
                        sb.append(FeedbackDetailActivity.this.ttUtil.getBucketName());
                        sb.append("/");
                        sb.append(ceph);
                        if (i < FeedbackDetailActivity.this.uploadSuccessList.size() - 1) {
                            sb.append(";");
                        }
                    }
                    FeedbackDetailActivity.this.imgUrl = sb.toString();
                    FeedbackDetailActivity.this.isImgUpload = true;
                    FeedbackDetailActivity.this.commit();
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                FeedbackDetailActivity.this.hideWaitingDialog();
                FeedbackDetailActivity.this.isImgUpload = false;
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        this.ttUtil.getStsToken(this.selectList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commit() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), this.commitUrl);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("description", this.et_input.getText().toString().trim());
        tomatoParams.addParameter("imageUrl", this.imgUrl);
        tomatoParams.addParameter("feedbackType", Integer.valueOf(this.feedbackType));
        tomatoParams.addParameter("brand", DeviceInfoUtil.getDeviceBrand());
        tomatoParams.addParameter("model", DeviceInfoUtil.getDeviceTypeName());
        tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, DeviceInfoUtil.getPhoneOSVersion());
        tomatoParams.addParameter("contact", this.connect_e.getText().toString());
        tomatoParams.addParameter("netStatus", NetWorkUtil.getNetWorkType());
        tomatoParams.post(new TomatoCallback(this, 2));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 2) {
            return;
        }
        ToastUtil.showCenterToast(baseModel.message);
        finish();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        int i = message.what;
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 188) {
            this.selectList = PictureSelector.obtainMultipleResult(intent);
            new ArrayList();
            for (LocalMedia localMedia : this.selectList) {
                Log.i("图片-----》", localMedia.getPath());
            }
            this.selectGridImageAdapter.setList(this.selectList);
            this.selectGridImageAdapter.notifyDataSetChanged();
        }
    }
}
