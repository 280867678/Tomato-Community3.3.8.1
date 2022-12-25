package com.one.tomato.mvp.p080ui.level;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.p005v7.widget.RecyclerView;
import android.support.p006v8.renderscript.ScriptIntrinsicBLAS;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.LevelRight;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.thirdpart.pictureselector.FullyGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LevelFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.level.LevelFragment */
/* loaded from: classes3.dex */
public final class LevelFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private LevelBean levelBean;
    private String nick;
    private int position;
    private String[] rightColors;
    private String[] rightContents;
    private Integer[] rightIcons;
    private int levelNo = 1;
    private HashMap<Integer, Integer> levelMap = new HashMap<>();

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
        return R.layout.fragment_level;
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

    /* compiled from: LevelFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.level.LevelFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LevelFragment getInstance(LevelBean levelBean, String nick, int i) {
            Intrinsics.checkParameterIsNotNull(nick, "nick");
            LevelFragment levelFragment = new LevelFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("levelBean", levelBean);
            bundle.putString("nick", nick);
            bundle.putInt("pos", i);
            levelFragment.setArguments(bundle);
            return levelFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        Integer num = null;
        Serializable serializable = arguments != null ? arguments.getSerializable("levelBean") : null;
        if (serializable == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.LevelBean");
        }
        this.levelBean = (LevelBean) serializable;
        this.nick = arguments != null ? arguments.getString("nick") : null;
        if (arguments != null) {
            num = Integer.valueOf(arguments.getInt("pos"));
        }
        this.position = num.intValue();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        if (this.position == 0) {
            setUserVisibleHint(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        List<LevelBean.LevelCfg> listLevelCfg;
        LevelBean.LevelCfg levelCfg;
        List<LevelBean.LevelCfg> listLevelCfg2;
        LevelBean.LevelCfg levelCfg2;
        super.onLazyLoad();
        ((TextView) _$_findCachedViewById(R$id.tv_level_nick)).setText(AppUtil.getString(R.string.common_tomato) + this.nick);
        LevelBean levelBean = this.levelBean;
        Integer valueOf = levelBean != null ? Integer.valueOf(levelBean.getCurrentLevelIndex()) : null;
        LevelBean levelBean2 = this.levelBean;
        Integer valueOf2 = levelBean2 != null ? Integer.valueOf(levelBean2.getLevelNickIndex()) : null;
        if (valueOf2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        this.levelNo = valueOf2.intValue();
        Integer[] numArr = {Integer.valueOf((int) R.drawable.level_right1_s), Integer.valueOf((int) R.drawable.level_right2_s), Integer.valueOf((int) R.drawable.level_right3_s), Integer.valueOf((int) R.drawable.level_right4_s), Integer.valueOf((int) R.drawable.level_right5_s), Integer.valueOf((int) R.drawable.level_right6_s)};
        Integer[] numArr2 = {Integer.valueOf((int) R.drawable.level_right1_n), Integer.valueOf((int) R.drawable.level_right2_n), Integer.valueOf((int) R.drawable.level_right3_n), Integer.valueOf((int) R.drawable.level_right4_n), Integer.valueOf((int) R.drawable.level_right5_n), Integer.valueOf((int) R.drawable.level_right6_n)};
        LevelBean levelBean3 = this.levelBean;
        int freeLookTime = (levelBean3 == null || (listLevelCfg2 = levelBean3.getListLevelCfg()) == null || (levelCfg2 = listLevelCfg2.get(this.position)) == null) ? 0 : levelCfg2.getFreeLookTime();
        LevelBean levelBean4 = this.levelBean;
        int levelUpReward = (levelBean4 == null || (listLevelCfg = levelBean4.getListLevelCfg()) == null || (levelCfg = listLevelCfg.get(this.position)) == null) ? 0 : levelCfg.getLevelUpReward();
        String string = AppUtil.getString(R.string.credit_right_times_no_limit);
        String string2 = AppUtil.getString(R.string.level_right_content1, this.nick);
        Object[] objArr = new Object[1];
        objArr[0] = freeLookTime == -1 ? string : String.valueOf(freeLookTime);
        String string3 = AppUtil.getString(R.string.level_right_content2, objArr);
        Object[] objArr2 = new Object[1];
        if (levelUpReward != -1) {
            string = String.valueOf(levelUpReward);
        }
        objArr2[0] = string;
        String string4 = AppUtil.getString(R.string.level_right_content3, objArr2);
        String[] strArr = {string2, string3, string4, AppUtil.getString(R.string.level_right_content4_s), AppUtil.getString(R.string.level_right_content5_s), AppUtil.getString(R.string.level_right_content6_s)};
        String[] strArr2 = {string2, string3, string4, AppUtil.getString(R.string.level_right_content4_n), AppUtil.getString(R.string.level_right_content5_n), AppUtil.getString(R.string.level_right_content6_n)};
        String[] strArr3 = {"#B98747", "#B98747", "#B98747", "#B98747", "#B98747", "#B98747"};
        String[] strArr4 = {"#999EAD", "#999EAD", "#999EAD", "#999EAD", "#999EAD", "#999EAD"};
        int i = this.position;
        if (i == 0) {
            ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick1_s);
            ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_y);
            ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#FFF6EB"));
            this.rightIcons = numArr;
            this.rightContents = strArr;
            this.rightColors = strArr3;
            if (valueOf != null && valueOf.intValue() == 1) {
                Integer[] numArr3 = this.rightIcons;
                if (numArr3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightIcons");
                    throw null;
                }
                numArr3[4] = numArr2[4];
                String[] strArr5 = this.rightContents;
                if (strArr5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightContents");
                    throw null;
                }
                String str = strArr2[4];
                Intrinsics.checkExpressionValueIsNotNull(str, "defaultRightContent[4]");
                strArr5[4] = str;
                String[] strArr6 = this.rightColors;
                if (strArr6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightColors");
                    throw null;
                }
                strArr6[4] = strArr4[4];
                Integer[] numArr4 = this.rightIcons;
                if (numArr4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightIcons");
                    throw null;
                }
                numArr4[5] = numArr2[5];
                String[] strArr7 = this.rightContents;
                if (strArr7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightContents");
                    throw null;
                }
                String str2 = strArr2[5];
                Intrinsics.checkExpressionValueIsNotNull(str2, "defaultRightContent[5]");
                strArr7[5] = str2;
                String[] strArr8 = this.rightColors;
                if (strArr8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightColors");
                    throw null;
                }
                strArr8[5] = strArr4[5];
            } else if (valueOf != null && valueOf.intValue() == 2) {
                Integer[] numArr5 = this.rightIcons;
                if (numArr5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightIcons");
                    throw null;
                }
                numArr5[5] = numArr2[5];
                String[] strArr9 = this.rightContents;
                if (strArr9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightContents");
                    throw null;
                }
                String str3 = strArr2[5];
                Intrinsics.checkExpressionValueIsNotNull(str3, "defaultRightContent[5]");
                strArr9[5] = str3;
                String[] strArr10 = this.rightColors;
                if (strArr10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rightColors");
                    throw null;
                }
                strArr10[5] = strArr4[5];
            }
        } else if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            if (Intrinsics.compare(this.levelNo, i) == 1) {
                                ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick6_s);
                                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_y);
                                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                                ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#FFF6EB"));
                                this.rightIcons = numArr;
                                this.rightContents = strArr;
                                this.rightColors = strArr3;
                            } else {
                                ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick6_n);
                                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_n);
                                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                                ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#F5F5F7"));
                                this.rightIcons = numArr2;
                                this.rightContents = strArr2;
                                this.rightColors = strArr4;
                            }
                        }
                    } else if (Intrinsics.compare(this.levelNo, i) == 1) {
                        ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick5_s);
                        ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_y);
                        ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                        ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#FFF6EB"));
                        this.rightIcons = numArr;
                        this.rightContents = strArr;
                        this.rightColors = strArr3;
                    } else {
                        ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick5_n);
                        ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_n);
                        ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                        ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#F5F5F7"));
                        this.rightIcons = numArr2;
                        this.rightContents = strArr2;
                        this.rightColors = strArr4;
                    }
                } else if (Intrinsics.compare(this.levelNo, i) == 1) {
                    ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick4_s);
                    ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_y);
                    ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                    ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#FFF6EB"));
                    this.rightIcons = numArr;
                    this.rightContents = strArr;
                    this.rightColors = strArr3;
                } else {
                    ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick4_n);
                    ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_n);
                    ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                    ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#F5F5F7"));
                    this.rightIcons = numArr2;
                    this.rightContents = strArr2;
                    this.rightColors = strArr4;
                }
            } else if (Intrinsics.compare(this.levelNo, i) == 1) {
                ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick3_s);
                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_y);
                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
                ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#FFF6EB"));
                this.rightIcons = numArr;
                this.rightContents = strArr;
                this.rightColors = strArr3;
            } else {
                ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick3_n);
                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_n);
                ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
                ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#F5F5F7"));
                this.rightIcons = numArr2;
                this.rightContents = strArr2;
                this.rightColors = strArr4;
            }
        } else if (Intrinsics.compare(this.levelNo, i) == 1) {
            ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick2_s);
            ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_y);
            ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_selector_solid_corner30_coloraccent);
            ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#FFF6EB"));
            this.rightIcons = numArr;
            this.rightContents = strArr;
            this.rightColors = strArr3;
        } else {
            ((ImageView) _$_findCachedViewById(R$id.iv_nick_head)).setImageResource(R.drawable.level_nick2_n);
            ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setText(R.string.level_get_n);
            ((TextView) _$_findCachedViewById(R$id.tv_level_no)).setBackgroundResource(R.drawable.common_shape_solid_corner30_disable);
            ((LinearLayout) _$_findCachedViewById(R$id.ll_expire)).setBackgroundColor(Color.parseColor("#F5F5F7"));
            this.rightIcons = numArr2;
            this.rightContents = strArr2;
            this.rightColors = strArr4;
        }
        int i2 = 0;
        Integer[] numArr6 = {0, 51, Integer.valueOf((int) ScriptIntrinsicBLAS.UPPER), 241, 361, 601, 961, 1441, 2161, 3121, 4321, 5761, 7441, 9361, 11521, 13921, 16321, 18721, 21121, 24001, 26881, 29881, 33081, 376481, 40081, 44081, 50081, 58081, 70081, 90081};
        int length = numArr6.length;
        while (i2 < length) {
            int i3 = i2 + 1;
            this.levelMap.put(Integer.valueOf(i3), numArr6[i2]);
            i2 = i3;
        }
        initRightAdapter();
        initExpireAdapter();
    }

    private final void initRightAdapter() {
        final ArrayList arrayList = new ArrayList();
        Integer[] numArr = this.rightIcons;
        if (numArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rightIcons");
            throw null;
        }
        int length = numArr.length;
        for (int i = 0; i < length; i++) {
            LevelRight levelRight = new LevelRight();
            Integer[] numArr2 = this.rightIcons;
            if (numArr2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightIcons");
                throw null;
            }
            levelRight.setIcon(numArr2[i].intValue());
            String[] strArr = this.rightContents;
            if (strArr == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightContents");
                throw null;
            }
            levelRight.setTitle(strArr[i]);
            String[] strArr2 = this.rightColors;
            if (strArr2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rightColors");
                throw null;
            }
            levelRight.setColor(strArr2[i]);
            arrayList.add(levelRight);
        }
        final Context mContext = getMContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_right);
        BaseRecyclerViewAdapter<LevelRight> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<LevelRight>(this, arrayList, mContext, R.layout.item_level_right, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.level.LevelFragment$initRightAdapter$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i2) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(mContext, r4, arrayList, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, LevelRight itemData) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                TextView tv_title = (TextView) holder.getView(R.id.tv_title);
                ((ImageView) holder.getView(R.id.iv_icon)).setImageResource(itemData.getIcon());
                Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
                tv_title.setText(itemData.getTitle());
                tv_title.setTextColor(Color.parseColor(itemData.getColor()));
            }
        };
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_right)).setLayoutManager(new FullyGridLayoutManager(getMContext(), 3));
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_right)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }

    private final void initExpireAdapter() {
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i <= 5; i++) {
            if (i == 0) {
                arrayList.add(AppUtil.getString(R.string.common_level));
            } else {
                int i2 = (this.position * 5) + i;
                arrayList.add("Lv." + i2);
                arrayList2.add(String.valueOf(this.levelMap.get(Integer.valueOf(i2))));
            }
        }
        arrayList.add(AppUtil.getString(R.string.common_potato_expire));
        arrayList.addAll(arrayList2);
        final Context mContext = getMContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_expire);
        BaseRecyclerViewAdapter<String> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<String>(arrayList, mContext, R.layout.item_level_expire_value, arrayList, recyclerView) { // from class: com.one.tomato.mvp.ui.level.LevelFragment$initExpireAdapter$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i3) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(mContext, r4, arrayList, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, String itemData) {
                int i3;
                int i4;
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                TextView tv_title = (TextView) holder.getView(R.id.tv_title);
                Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
                tv_title.setText(itemData);
                i3 = LevelFragment.this.levelNo;
                i4 = LevelFragment.this.position;
                if (Intrinsics.compare(i3, i4) == 1) {
                    tv_title.setTextColor(Color.parseColor("#B98747"));
                } else {
                    tv_title.setTextColor(Color.parseColor("#999EAD"));
                }
            }
        };
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_expire)).setLayoutManager(new FullyGridLayoutManager(getMContext(), 6));
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView_expire)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }
}
