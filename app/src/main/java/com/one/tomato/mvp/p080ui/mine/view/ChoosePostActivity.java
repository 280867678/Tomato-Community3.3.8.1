package com.one.tomato.mvp.p080ui.mine.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentTransaction;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CreateSerializeDialog;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ChoosePostActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostActivity */
/* loaded from: classes3.dex */
public final class ChoosePostActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private ChoosePostFragment minePostFragment;
    private int type = 1;
    private ArrayList<PostList> selectListData = new ArrayList<>();

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
        return R.layout.activity_choose_post;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: ChoosePostActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Activity mContext, int i, ArrayList<PostList> selectedData) {
            Intrinsics.checkParameterIsNotNull(mContext, "mContext");
            Intrinsics.checkParameterIsNotNull(selectedData, "selectedData");
            Intent intent = new Intent(mContext, ChoosePostActivity.class);
            intent.putExtra("type", i);
            intent.putParcelableArrayListExtra("selectedData", selectedData);
            mContext.startActivityForResult(intent, 100);
        }

        public final void startActivity(Context mContext, Fragment fragment, int i, ArrayList<PostList> selectedData) {
            Intrinsics.checkParameterIsNotNull(mContext, "mContext");
            Intrinsics.checkParameterIsNotNull(selectedData, "selectedData");
            Intent intent = new Intent(mContext, ChoosePostActivity.class);
            intent.putExtra("type", i);
            intent.putParcelableArrayListExtra("selectedData", selectedData);
            if (fragment != null) {
                fragment.startActivityForResult(intent, 100);
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        FragmentTransaction beginTransaction;
        ImmersionBarUtil.init(this, (int) R.id.ll_title);
        Intent intent = getIntent();
        int i = 1;
        if (intent != null) {
            i = intent.getIntExtra("type", 1);
        }
        this.type = i;
        this.minePostFragment = ChoosePostFragment.Companion.getInstance(this.type);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager != null && (beginTransaction = supportFragmentManager.beginTransaction()) != null) {
            ChoosePostFragment choosePostFragment = this.minePostFragment;
            if (choosePostFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            FragmentTransaction add = beginTransaction.add(R.id.content, choosePostFragment);
            if (add != null) {
                add.commitAllowingStateLoss();
            }
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.ChoosePostActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChoosePostActivity.this.onBackPressed();
                }
            });
        }
        ChoosePostFragment choosePostFragment2 = this.minePostFragment;
        if (choosePostFragment2 != null) {
            choosePostFragment2.addCheckBoxCallBack(new ChoosePostActivity$initView$2(this));
        }
        ChoosePostFragment choosePostFragment3 = this.minePostFragment;
        if (choosePostFragment3 != null) {
            choosePostFragment3.addPullSelectCall(new ChoosePostActivity$initView$3(this));
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_next);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.ChoosePostActivity$initView$4

                /* compiled from: ChoosePostActivity.kt */
                /* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostActivity$initView$4$2 */
                /* loaded from: classes3.dex */
                static final class C25732 extends Lambda implements Function1<String, Unit> {
                    C25732() {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
                        invoke2(str);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke  reason: avoid collision after fix types in other method */
                    public final void invoke2(String str) {
                        ChoosePostActivity.this.setResult(10);
                        ChoosePostActivity.this.onBackPressed();
                    }
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChoosePostFragment choosePostFragment4;
                    int i2;
                    Context mContext;
                    choosePostFragment4 = ChoosePostActivity.this.minePostFragment;
                    ArrayList<PostList> selectPost = choosePostFragment4 != null ? choosePostFragment4.getSelectPost() : null;
                    if ((selectPost != null ? selectPost.size() : 0) > 0) {
                        i2 = ChoosePostActivity.this.type;
                        if (i2 == 1) {
                            mContext = ChoosePostActivity.this.getMContext();
                            if (mContext != null) {
                                CreateSerializeDialog createSerializeDialog = new CreateSerializeDialog(mContext, 0);
                                createSerializeDialog.setTitle(AppUtil.getString(R.string.text_create_serialize));
                                StringBuilder sb = new StringBuilder();
                                if (selectPost != null) {
                                    for (PostList postList : selectPost) {
                                        if (sb.length() > 0) {
                                            sb.append(",");
                                        }
                                        sb.append(postList.getId());
                                    }
                                }
                                String sb2 = sb.toString();
                                Intrinsics.checkExpressionValueIsNotNull(sb2, "stringBuilder.toString()");
                                createSerializeDialog.setPostIds(sb2);
                                createSerializeDialog.addCreateSerialCallBack(new C25732());
                                createSerializeDialog.show();
                                return;
                            }
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        Intent intent2 = new Intent();
                        intent2.putParcelableArrayListExtra(AopConstants.APP_PROPERTIES_KEY, selectPost);
                        ChoosePostActivity.this.setResult(100, intent2);
                        ChoosePostActivity.this.finish();
                    }
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_next);
        if (textView2 != null) {
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView2.setTextColor(ContextCompat.getColor(mContext, R.color.text_light));
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_next);
        if (textView3 != null) {
            textView3.setEnabled(false);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        Intent intent = getIntent();
        ArrayList parcelableArrayListExtra = intent != null ? intent.getParcelableArrayListExtra("selectedData") : null;
        if (parcelableArrayListExtra == null || parcelableArrayListExtra.size() <= 0) {
            return;
        }
        this.selectListData.addAll(parcelableArrayListExtra);
    }
}
