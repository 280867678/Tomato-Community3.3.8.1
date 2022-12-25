package com.one.tomato.dialog;

import android.content.Context;
import android.support.p002v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import io.reactivex.schedulers.Schedulers;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReviewPostChooseDialog.kt */
/* loaded from: classes3.dex */
public final class ReviewPostChooseDialog extends CustomAlertDialog {
    private Functions<Unit> callReviewCom;
    private ImageView image_close;
    private PostList postList;
    private int step;
    private TextView text_desc;
    private TextView text_no;
    private TextView text_yes;
    private TextView tvTitle;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReviewPostChooseDialog(Context context, PostList postList) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        View inflate = LayoutInflater.from(context).inflate(R.layout.review_post_choose_dialog, (ViewGroup) null);
        setContentView(inflate);
        bottomButtonVisiblity(3);
        this.image_close = (ImageView) inflate.findViewById(R.id.image_close);
        this.tvTitle = (TextView) inflate.findViewById(R.id.tvTitle);
        this.text_desc = (TextView) inflate.findViewById(R.id.text_desc);
        this.text_no = (TextView) inflate.findViewById(R.id.text_no);
        this.text_yes = (TextView) inflate.findViewById(R.id.text_yes);
        ImageView imageView = this.image_close;
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ReviewPostChooseDialog.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ReviewPostChooseDialog.this.dismiss();
                }
            });
        }
        TextView textView = this.text_yes;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ReviewPostChooseDialog.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ReviewPostChooseDialog.this.nextStep();
                }
            });
        }
        TextView textView2 = this.text_no;
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ReviewPostChooseDialog.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (ReviewPostChooseDialog.this.step == 4) {
                        ReviewPostChooseDialog.this.itemPostReview(true, 0);
                    } else {
                        ReviewPostChooseDialog.this.itemPostReview(false, 0);
                    }
                }
            });
        }
        this.postList = postList;
        nextStep();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void nextStep() {
        this.step++;
        int i = this.step;
        if (i == 1) {
            TextView textView = this.tvTitle;
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.post_review_choose_title_1));
            }
            TextView textView2 = this.text_desc;
            if (textView2 != null) {
                textView2.setText(AppUtil.getString(R.string.post_review_choose_desc));
            }
            TextView textView3 = this.text_no;
            if (textView3 != null) {
                textView3.setText(AppUtil.getString(R.string.post_review_choose_no_1));
            }
            TextView textView4 = this.text_yes;
            if (textView4 == null) {
                return;
            }
            textView4.setText(AppUtil.getString(R.string.post_review_choose_yes_1));
        } else if (i == 2) {
            TextView textView5 = this.tvTitle;
            if (textView5 != null) {
                textView5.setText(AppUtil.getString(R.string.post_review_choose_title_2));
            }
            TextView textView6 = this.text_desc;
            if (textView6 != null) {
                textView6.setVisibility(8);
            }
            TextView textView7 = this.text_no;
            if (textView7 != null) {
                textView7.setText(AppUtil.getString(R.string.post_review_choose_no_2));
            }
            TextView textView8 = this.text_yes;
            if (textView8 == null) {
                return;
            }
            textView8.setText(AppUtil.getString(R.string.post_review_choose_yes_2));
        } else if (i == 3) {
            TextView textView9 = this.tvTitle;
            if (textView9 != null) {
                textView9.setText(AppUtil.getString(R.string.post_review_choose_title_3));
            }
            TextView textView10 = this.text_desc;
            if (textView10 != null) {
                textView10.setVisibility(8);
            }
            TextView textView11 = this.text_no;
            if (textView11 != null) {
                textView11.setText(AppUtil.getString(R.string.post_review_choose_no_3));
            }
            TextView textView12 = this.text_yes;
            if (textView12 == null) {
                return;
            }
            textView12.setText(AppUtil.getString(R.string.post_review_choose_yes_3));
        } else if (i != 4) {
            if (i != 5) {
                return;
            }
            itemPostReview(true, 1);
        } else {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
            if (userInfo.isOfficial()) {
                itemPostReview(true, 1);
                return;
            }
            TextView textView13 = this.tvTitle;
            if (textView13 != null) {
                textView13.setText(AppUtil.getString(R.string.post_review_choose_title_4));
            }
            TextView textView14 = this.text_desc;
            if (textView14 != null) {
                textView14.setVisibility(8);
            }
            TextView textView15 = this.text_no;
            if (textView15 != null) {
                textView15.setText(AppUtil.getString(R.string.post_review_choose_no_4));
            }
            TextView textView16 = this.text_yes;
            if (textView16 == null) {
                return;
            }
            textView16.setText(AppUtil.getString(R.string.post_review_choose_yes_4));
        }
    }

    public final void addCallBackReviewCom(Functions<Unit> functions) {
        this.callReviewCom = functions;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void itemPostReview(boolean z, int i) {
        String str;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        PostList postList = this.postList;
        Integer num = null;
        linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, postList != null ? Integer.valueOf(postList.getId()) : null);
        PostList postList2 = this.postList;
        if (postList2 != null) {
            num = Integer.valueOf(postList2.getId());
        }
        linkedHashMap.put("articleId", num);
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
        if (userInfo.isOfficial()) {
            linkedHashMap.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(z ? 1 : 3));
            str = "/app/officialAuditor/audit";
        } else {
            linkedHashMap.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(z ? 1 : 0));
            str = "/app/userAuditor/auditNew";
        }
        linkedHashMap.put("like", Integer.valueOf(i));
        StringBuilder sb = new StringBuilder();
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        sb.append(domainServer.getServerUrl());
        sb.append(str);
        reviewPost(sb.toString(), linkedHashMap);
        Functions<Unit> functions = this.callReviewCom;
        if (functions != null) {
            functions.mo6822invoke();
        }
        dismiss();
    }

    private final void reviewPost(String str, Map<String, Object> map) {
        ApiImplService.Companion.getApiImplService().reviewPost(str, map).subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.dialog.ReviewPostChooseDialog$reviewPost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                LogUtil.m3783i("yan", "帖子·审核·成功");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                StringBuilder sb = new StringBuilder();
                sb.append("帖子·审核·失败1");
                sb.append(responseThrowable != null ? responseThrowable.getThrowableMessage() : null);
                LogUtil.m3783i("yan", sb.toString());
            }
        });
    }
}
