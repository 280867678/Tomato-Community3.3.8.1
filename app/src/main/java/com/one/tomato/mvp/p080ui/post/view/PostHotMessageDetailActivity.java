package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaTabFragment;
import com.one.tomato.mvp.p080ui.post.view.PostHotMessageDetailFragment;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotMessageDetailActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostHotMessageDetailActivity */
/* loaded from: classes3.dex */
public final class PostHotMessageDetailActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PostHotMessageBean postHotMessageBean;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_post_hot_message_detail;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: PostHotMessageDetailActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PostHotMessageDetailActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, PostHotMessageBean postHotMessageBean) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent(context, PostHotMessageDetailActivity.class);
            intent.putExtra("eventBean", postHotMessageBean);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        Fragment companion;
        Intent intent = getIntent();
        this.postHotMessageBean = intent != null ? (PostHotMessageBean) intent.getParcelableExtra("eventBean") : null;
        PostHotMessageBean postHotMessageBean = this.postHotMessageBean;
        if (Intrinsics.areEqual(postHotMessageBean != null ? postHotMessageBean.getEventPosition() : null, "2")) {
            NewPaPaTabFragment.Companion companion2 = NewPaPaTabFragment.Companion;
            PostHotMessageBean postHotMessageBean2 = this.postHotMessageBean;
            companion = companion2.initInstance(String.valueOf(postHotMessageBean2 != null ? Integer.valueOf(postHotMessageBean2.getMessageId()) : null), 11);
        } else {
            PostHotMessageDetailFragment.Companion companion3 = PostHotMessageDetailFragment.Companion;
            PostHotMessageBean postHotMessageBean3 = this.postHotMessageBean;
            companion = companion3.getInstance(-1, "event_message", String.valueOf(postHotMessageBean3 != null ? Integer.valueOf(postHotMessageBean3.getMessageId()) : null));
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager != null ? supportFragmentManager.beginTransaction() : null;
        if (beginTransaction != null) {
            if (companion == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            beginTransaction.add(R.id.content, companion);
        }
        if (beginTransaction != null) {
            beginTransaction.commitAllowingStateLoss();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostHotMessageDetailActivity$initData$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostHotMessageDetailActivity.this.onBackPressed();
                }
            });
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_title);
        String str = null;
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append('#');
            PostHotMessageBean postHotMessageBean = this.postHotMessageBean;
            sb.append(postHotMessageBean != null ? postHotMessageBean.getEventName() : null);
            textView.setText(sb.toString());
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_description);
        if (textView2 != null) {
            PostHotMessageBean postHotMessageBean2 = this.postHotMessageBean;
            if (postHotMessageBean2 != null) {
                str = postHotMessageBean2.getEventDesc();
            }
            textView2.setText(str);
        }
    }
}
