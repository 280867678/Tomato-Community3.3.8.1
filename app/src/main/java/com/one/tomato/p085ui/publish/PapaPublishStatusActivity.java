package com.one.tomato.p085ui.publish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.PostSame;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaListVideoActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.service.PublishService;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.post.PapaPublishUtil;
import java.util.ArrayList;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_publish_status)
/* renamed from: com.one.tomato.ui.publish.PapaPublishStatusActivity */
/* loaded from: classes3.dex */
public class PapaPublishStatusActivity extends BaseActivity {
    @ViewInject(R.id.cancelTV)
    private TextView cancelTV;
    @ViewInject(R.id.confirmTV)
    private TextView confirmTV;
    @ViewInject(R.id.line_bottom_vertical)
    private View line_bottom_vertical;
    @ViewInject(R.id.messageTV)
    private TextView messageTV;
    @ViewInject(R.id.messageTV1)
    private TextView messageTV1;
    private PublishInfo publishInfo;
    private int publishType;
    private ResponseThrowable throwable;
    @ViewInject(R.id.titleTV)
    private TextView titleTV;

    public static void startActivity(Context context, int i, PublishInfo publishInfo, ResponseThrowable responseThrowable) {
        Intent intent = new Intent(context, PapaPublishStatusActivity.class);
        intent.putExtra("business", i);
        intent.putExtra("publishInfo", publishInfo);
        intent.putExtra("throwable", responseThrowable);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent == null || intent.getExtras() == null) {
            return;
        }
        initIntentData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initIntentData();
    }

    private void initIntentData() {
        this.publishType = getIntent().getExtras().getInt("business", -1);
        this.publishInfo = (PublishInfo) getIntent().getExtras().getParcelable("publishInfo");
        this.throwable = (ResponseThrowable) getIntent().getExtras().getSerializable("throwable");
        init();
    }

    private void init() {
        int i = this.publishType;
        if (i == 1) {
            this.cancelTV.setVisibility(0);
            this.line_bottom_vertical.setVisibility(0);
            this.confirmTV.setVisibility(0);
            this.messageTV.setText(R.string.post_publish_ing);
            this.cancelTV.setText(R.string.post_publish_wait);
            this.confirmTV.setText(R.string.post_publish_cancel);
        } else if (i == 2) {
            this.cancelTV.setVisibility(8);
            this.line_bottom_vertical.setVisibility(8);
            this.confirmTV.setVisibility(0);
            this.messageTV.setText(R.string.post_publish_success);
            this.confirmTV.setText(R.string.common_confirm);
        } else if (i == 3) {
            this.cancelTV.setVisibility(0);
            this.line_bottom_vertical.setVisibility(0);
            this.confirmTV.setVisibility(0);
            this.titleTV.setVisibility(0);
            if (TextUtils.isEmpty(this.throwable.message)) {
                this.messageTV.setText(R.string.post_publish_fail);
            } else {
                this.messageTV.setText(this.throwable.message);
            }
            this.cancelTV.setText(R.string.post_publish_giveup);
            this.confirmTV.setText(R.string.post_publish_retry);
        } else if (i != 4) {
        } else {
            this.cancelTV.setVisibility(0);
            this.titleTV.setVisibility(0);
            this.line_bottom_vertical.setVisibility(0);
            this.confirmTV.setVisibility(0);
            ResponseThrowable responseThrowable = this.throwable;
            if (responseThrowable.code == -10) {
                this.messageTV.setSingleLine(true);
                this.messageTV.setEllipsize(TextUtils.TruncateAt.END);
                TextView textView = this.messageTV;
                textView.setText("“" + this.publishInfo.getTitle() + "”");
                this.messageTV1.setVisibility(0);
                this.messageTV1.setText(R.string.papa_publish_same);
                this.cancelTV.setText(R.string.common_cancel);
                this.confirmTV.setText(R.string.papa_publish_same_btn);
                return;
            }
            if (TextUtils.isEmpty(responseThrowable.message)) {
                this.messageTV.setText(R.string.post_publish_fail);
            } else {
                this.messageTV.setText(this.throwable.message);
            }
            this.cancelTV.setText(R.string.post_publish_giveup);
            this.confirmTV.setText(R.string.post_publish_retry);
        }
    }

    @Event({R.id.cancelTV, R.id.confirmTV})
    private void onClick(View view) {
        int id = view.getId();
        boolean z = true;
        if (id == R.id.cancelTV) {
            int i = this.publishType;
            if (1 != i) {
                if (3 == i) {
                    cancelPublish();
                } else if (4 == i) {
                    cancelPublish();
                }
            }
        } else if (id == R.id.confirmTV) {
            int i2 = this.publishType;
            if (1 == i2) {
                cancelPublish();
            } else if (2 == i2) {
                cancelPublish();
            } else if (3 == i2) {
                cancelPublish();
                PublishActivity.startActivity(this.mContext, this.publishInfo);
            } else if (4 == i2) {
                cancelPublish();
                if (this.throwable.code == -10) {
                    getPostDetail();
                    z = false;
                } else {
                    PublishActivity.startActivity(this.mContext, this.publishInfo);
                }
            }
        }
        if (z) {
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        int i = this.publishType;
        if (1 != i) {
            if (2 == i) {
                PapaPublishUtil.getInstance().onPublishCancel();
            } else if (3 == i) {
                PapaPublishUtil.getInstance().onPublishCancel();
            } else if (4 == i) {
                PapaPublishUtil.getInstance().onPublishCancel();
            }
        }
        finish();
        overridePendingTransition(0, 0);
    }

    private void cancelPublish() {
        PapaPublishUtil.getInstance().onPublishCancel();
        this.mContext.stopService(new Intent(this.mContext, PublishService.class));
    }

    private void getPostDetail() {
        PostSame postSame = (PostSame) this.throwable.getData();
        if (postSame == null) {
            return;
        }
        String articleId = postSame.getArticleId();
        if (TextUtils.isEmpty(articleId)) {
            return;
        }
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/article/recommend/detail");
        tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_ID, articleId);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, PostList.class));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what != 1) {
            return;
        }
        ArrayList<PostList> arrayList = new ArrayList<>();
        arrayList.add((PostList) baseModel.obj);
        NewPaPaListVideoActivity.Companion.startAct(this.mContext, arrayList, "collect", "", 0, 0, 0, true);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what == 1) {
            finish();
            overridePendingTransition(0, 0);
        }
        return true;
    }
}
