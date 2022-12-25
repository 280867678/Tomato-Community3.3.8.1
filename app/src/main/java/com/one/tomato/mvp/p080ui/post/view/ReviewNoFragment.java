package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.post.view.PublishPostSpecificationActivity;
import com.one.tomato.utils.AppUtil;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: ReviewNoFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.ReviewNoFragment */
/* loaded from: classes3.dex */
public final class ReviewNoFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.review_no_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        int indexOf$default;
        int lastIndexOf$default;
        String string = AppUtil.getString(R.string.review_notice_2);
        if (string instanceof String) {
            SpannableString spannableString = new SpannableString(string);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#5B92E1"));
            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) string, "《", 0, false, 6, (Object) null);
            lastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default(string, "》", 0, false, 6, null);
            spannableString.setSpan(foregroundColorSpan, indexOf$default, lastIndexOf$default + 1, 18);
            TextView text_4 = (TextView) _$_findCachedViewById(R$id.text_4);
            Intrinsics.checkExpressionValueIsNotNull(text_4, "text_4");
            text_4.setText(spannableString);
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_4);
            if (textView != null) {
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.ReviewNoFragment$inintData$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Context mContext;
                        PublishPostSpecificationActivity.Companion companion = PublishPostSpecificationActivity.Companion;
                        mContext = ReviewNoFragment.this.getMContext();
                        if (mContext != null) {
                            companion.startAct(mContext);
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                });
            }
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_weigui);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.ReviewNoFragment$inintData$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Intent intent = new Intent(ReviewNoFragment.this.getContext(), ReviewPostWGActivity.class);
                    Context context = ReviewNoFragment.this.getContext();
                    if (context != null) {
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
