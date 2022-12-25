package com.one.tomato.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.p002v4.app.DialogFragment;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.p082up.impl.IRecordCountListener;
import com.one.tomato.mvp.p080ui.p082up.view.RewardFragment;
import com.one.tomato.mvp.p080ui.p082up.view.RewardRecordFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import java.util.HashMap;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostRewardDialog.kt */
/* loaded from: classes3.dex */
public final class PostRewardDialog extends DialogFragment implements IRecordCountListener {
    private HashMap _$_findViewCache;
    private RewardFragment rewardFragment;
    private RewardRecordFragment rewardRecordFragment;
    public static final Companion Companion = new Companion(null);
    private static final String INTENT_ARTID = INTENT_ARTID;
    private static final String INTENT_ARTID = INTENT_ARTID;

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

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: PostRewardDialog.kt */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getINTENT_ARTID() {
            return PostRewardDialog.INTENT_ARTID;
        }

        public final PostRewardDialog showDialog(PostList postList) {
            Bundle bundle = new Bundle();
            PostRewardDialog postRewardDialog = new PostRewardDialog();
            bundle.putParcelable(getINTENT_ARTID(), postList);
            postRewardDialog.setArguments(bundle);
            return postRewardDialog;
        }
    }

    private final void init(View view) {
        List<String> mutableListOf;
        List mutableListOf2;
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        PreviewViewPager previewViewPager = (PreviewViewPager) view.findViewById(R.id.preview_pager);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView3);
        String string = AppUtil.getString(R.string.post_reward);
        Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_reward)");
        String string2 = AppUtil.getString(R.string.post_reward_record);
        Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.post_reward_record)");
        mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(string, string2);
        Bundle arguments = getArguments();
        PostList postList = arguments != null ? (PostList) arguments.getParcelable(INTENT_ARTID) : null;
        this.rewardFragment = new RewardFragment();
        this.rewardRecordFragment = new RewardRecordFragment();
        RewardRecordFragment rewardRecordFragment = this.rewardRecordFragment;
        if (rewardRecordFragment != null) {
            rewardRecordFragment.setRecordCountListener(this);
        }
        RewardFragment rewardFragment = this.rewardFragment;
        if (rewardFragment != null) {
            rewardFragment.setPostList(postList);
        }
        RewardRecordFragment rewardRecordFragment2 = this.rewardRecordFragment;
        if (rewardRecordFragment2 != null) {
            rewardRecordFragment2.setArtcId(postList != null ? Long.valueOf(postList.getId()) : null);
        }
        Fragment[] fragmentArr = new Fragment[2];
        RewardFragment rewardFragment2 = this.rewardFragment;
        if (rewardFragment2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        fragmentArr[0] = rewardFragment2;
        RewardRecordFragment rewardRecordFragment3 = this.rewardRecordFragment;
        if (rewardRecordFragment3 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        fragmentArr[1] = rewardRecordFragment3;
        mutableListOf2 = CollectionsKt__CollectionsKt.mutableListOf(fragmentArr);
        PaPaTabAdapter paPaTabAdapter = new PaPaTabAdapter(getChildFragmentManager(), mutableListOf2, mutableListOf);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(paPaTabAdapter);
        }
        if (previewViewPager != null) {
            previewViewPager.setOffscreenPageLimit(mutableListOf2.size());
        }
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(previewViewPager);
        }
        tabChangeListener(tabLayout, mutableListOf);
        if (imageView == null) {
            return;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PostRewardDialog$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PostRewardDialog.this.dismiss();
            }
        });
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Dialog dialog = getDialog();
        Intrinsics.checkExpressionValueIsNotNull(dialog, "dialog");
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(80);
        }
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        if (window != null) {
            window.setLayout(-1, -2);
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.post_reward_dialog, (ViewGroup) null);
        Intrinsics.checkExpressionValueIsNotNull(inflate, "inflate");
        init(inflate);
        return inflate;
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, R.style.PostRewardDialog);
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.IRecordCountListener
    public void getRecordCount(int i) {
        TabLayout.Tab tabAt = ((TabLayout) _$_findCachedViewById(R$id.tabLayout)).getTabAt(1);
        View customView = tabAt != null ? tabAt.getCustomView() : null;
        if (customView instanceof TextView) {
            ((TextView) customView).setText(AppUtil.getString(R.string.post_reward_record) + "(" + FormatUtil.formatNumOverTenThousand(String.valueOf(i)) + ")");
        }
    }

    private final void tabChangeListener(TabLayout tabLayout, List<String> list) {
        for (int i = 0; i < 2; i++) {
            TabLayout.Tab tabAt = tabLayout != null ? tabLayout.getTabAt(i) : null;
            TextView textView = new TextView(getContext());
            textView.setText(list.get(i));
            textView.setTextSize(14.0f);
            textView.setTag(Integer.valueOf(i));
            textView.setWidth((int) DisplayMetricsUtils.dp2px(117.0f));
            textView.setGravity(17);
            textView.setHeight((int) DisplayMetricsUtils.dp2px(36.0f));
            if (i == 0) {
                Context context = getContext();
                if (context == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setTextColor(ContextCompat.getColor(context, R.color.white));
                Context context2 = getContext();
                if (context2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setBackground(ContextCompat.getDrawable(context2, R.drawable.reward_tab_one_select_yes));
            } else {
                Context context3 = getContext();
                if (context3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setBackground(ContextCompat.getDrawable(context3, R.drawable.reward_tab_two_select_no));
                Context context4 = getContext();
                if (context4 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setTextColor(ContextCompat.getColor(context4, R.color.text_light));
            }
            if (tabAt != null) {
                tabAt.setCustomView(textView);
            }
        }
        if (tabLayout != null) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.one.tomato.dialog.PostRewardDialog$tabChangeListener$1
                @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                public void onTabReselected(TabLayout.Tab tab) {
                }

                @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                public void onTabUnselected(TabLayout.Tab tab) {
                    TextView textView2;
                    if (tab == null || (textView2 = (TextView) tab.getCustomView()) == null) {
                        return;
                    }
                    Context context5 = PostRewardDialog.this.getContext();
                    if (context5 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    textView2.setTextColor(ContextCompat.getColor(context5, R.color.text_light));
                    if (Intrinsics.areEqual(textView2.getTag(), 0)) {
                        Context context6 = PostRewardDialog.this.getContext();
                        if (context6 != null) {
                            textView2.setBackground(ContextCompat.getDrawable(context6, R.drawable.reward_tab_one_select_no));
                            return;
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    Context context7 = PostRewardDialog.this.getContext();
                    if (context7 != null) {
                        textView2.setBackground(ContextCompat.getDrawable(context7, R.drawable.reward_tab_two_select_no));
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }

                @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                public void onTabSelected(TabLayout.Tab tab) {
                    TextView textView2;
                    if (tab == null || (textView2 = (TextView) tab.getCustomView()) == null) {
                        return;
                    }
                    Context context5 = PostRewardDialog.this.getContext();
                    if (context5 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    textView2.setTextColor(ContextCompat.getColor(context5, R.color.white));
                    if (Intrinsics.areEqual(textView2.getTag(), 0)) {
                        Context context6 = PostRewardDialog.this.getContext();
                        if (context6 != null) {
                            textView2.setBackground(ContextCompat.getDrawable(context6, R.drawable.reward_tab_one_select_yes));
                            return;
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                    Context context7 = PostRewardDialog.this.getContext();
                    if (context7 != null) {
                        textView2.setBackground(ContextCompat.getDrawable(context7, R.drawable.reward_tab_two_select_yes));
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
    }
}
