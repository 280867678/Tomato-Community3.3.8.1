package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.UpRankListBean;
import com.one.tomato.entity.UpStatusBean;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.mine.view.NewMyHomePageActivity;
import com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView;
import com.one.tomato.mvp.p080ui.p082up.presenter.UpHomePresenter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.Standard;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpChildRankFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpChildRankFragment */
/* loaded from: classes3.dex */
public final class UpChildRankFragment extends MvpBaseFragment<UpContarct$UpIView, UpHomePresenter> implements UpContarct$UpIView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private BaseQuickAdapter<UpRankListBean, BaseViewHolder> adapter;
    private int topListType;
    private int type;

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
        return R.layout.up_child_rank_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: UpChildRankFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.up.view.UpChildRankFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final UpChildRankFragment getInstance(int i, int i2, boolean z) {
            UpChildRankFragment upChildRankFragment = new UpChildRankFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("top_type", i2);
            bundle.putBoolean("auto_load", z);
            bundle.putInt("type", i);
            upChildRankFragment.setArguments(bundle);
            return upChildRankFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public UpHomePresenter mo6441createPresenter() {
        return new UpHomePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        addAdapter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        Bundle arguments = getArguments();
        this.type = arguments != null ? arguments.getInt("type") : 1;
        Bundle arguments2 = getArguments();
        this.topListType = arguments2 != null ? arguments2.getInt("top_type") : 1;
        Bundle arguments3 = getArguments();
        if (arguments3 != null) {
            arguments3.getBoolean("auto_load");
        }
        if (this.type == 1) {
            setUserVisibleHint(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        showWaitingDialog();
        UpHomePresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestUpRankList(this.type, this.topListType);
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        hideWaitingDialog();
        setData(null);
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryUpStatusInfo(UpStatusBean upStatusBean) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyUpSuccess() {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerApplyError() {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerQueryAchiSucess(UpStatusBean upStatusBean) {
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.UpContarct$UpIView
    public void handlerRankList(ArrayList<UpRankListBean> arrayList) {
        hideWaitingDialog();
        setData(arrayList);
    }

    private final void addAdapter() {
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        this.adapter = new BaseQuickAdapter<UpRankListBean, BaseViewHolder>(R.layout.layout_up_rank_item) { // from class: com.one.tomato.mvp.ui.up.view.UpChildRankFragment$addAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, UpRankListBean upRankListBean) {
                int i;
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image_head) : null;
                TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
                TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_tot_num) : null;
                TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_number) : null;
                if (roundedImageView != null) {
                    roundedImageView.setImageResource(R.drawable.default_img_head);
                }
                ImageLoaderUtil.loadHeadImage(this.mContext, roundedImageView, new ImageBean(upRankListBean != null ? upRankListBean.getAvatar() : null));
                if (textView != null) {
                    if (upRankListBean == null || (str5 = upRankListBean.getMemberName()) == null) {
                        str5 = "";
                    }
                    textView.setText(str5);
                }
                i = UpChildRankFragment.this.topListType;
                if (i != 1) {
                    if (i == 2) {
                        if (textView2 != null) {
                            StringBuilder sb = new StringBuilder();
                            if (upRankListBean == null || (str2 = String.valueOf(upRankListBean.getIncome())) == null) {
                                str2 = "0";
                            }
                            sb.append(FormatUtil.formatNumOverTenThousand(str2));
                            sb.append(AppUtil.getString(R.string.up_creator_zp));
                            textView2.setText(sb.toString());
                        }
                        if (textView2 != null) {
                            textView2.setCompoundDrawables(null, null, null, null);
                        }
                    } else if (i == 3) {
                        if (textView2 != null) {
                            if (upRankListBean == null || (str3 = String.valueOf(upRankListBean.getIncome())) == null) {
                                str3 = "0";
                            }
                            textView2.setText(FormatUtil.formatNumOverTenThousand(str3));
                        }
                        Drawable drawable = ContextCompat.getDrawable(this.mContext, R.drawable.creator_hot);
                        if (drawable != null) {
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        }
                        if (textView2 != null) {
                            textView2.setCompoundDrawables(drawable, null, null, null);
                        }
                    } else if (i == 4) {
                        if (textView2 != null) {
                            StringBuilder sb2 = new StringBuilder();
                            if (upRankListBean == null || (str4 = String.valueOf(upRankListBean.getIncome())) == null) {
                                str4 = "0";
                            }
                            sb2.append(FormatUtil.formatNumOverTenThousand(str4));
                            sb2.append(AppUtil.getString(R.string.up_creator_fans));
                            textView2.setText(sb2.toString());
                        }
                        if (textView2 != null) {
                            textView2.setCompoundDrawables(null, null, null, null);
                        }
                    }
                } else if (textView2 != null) {
                    if (upRankListBean == null || (str = String.valueOf(upRankListBean.getIncome())) == null) {
                        str = "0";
                    }
                    textView2.setText(FormatUtil.formatTomato2RMB(str));
                }
                if (textView3 != null) {
                    textView3.setText(String.valueOf(getData().indexOf(upRankListBean) + 4));
                }
            }
        };
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.adapter);
        }
        BaseQuickAdapter<UpRankListBean, BaseViewHolder> baseQuickAdapter = this.adapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpChildRankFragment$addAdapter$2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter2, View view, int i) {
                    Object item = baseQuickAdapter2.getItem(i);
                    if (item instanceof UpRankListBean) {
                        UpChildRankFragment.this.jumpPersonHome(Integer.valueOf(((UpRankListBean) item).getMemberId()));
                    }
                }
            });
        }
        RoundedImageView roundedImageView = (RoundedImageView) _$_findCachedViewById(R$id.round_view_03);
        if (roundedImageView != null) {
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpChildRankFragment$addAdapter$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextView textView = (TextView) UpChildRankFragment.this._$_findCachedViewById(R$id.text_rank_03);
                    UpChildRankFragment.this.jumpPersonHome(textView != null ? textView.getTag() : null);
                }
            });
        }
        RoundedImageView roundedImageView2 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_01);
        if (roundedImageView2 != null) {
            roundedImageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpChildRankFragment$addAdapter$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextView textView = (TextView) UpChildRankFragment.this._$_findCachedViewById(R$id.text_rank_01);
                    UpChildRankFragment.this.jumpPersonHome(textView != null ? textView.getTag() : null);
                }
            });
        }
        RoundedImageView roundedImageView3 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_02);
        if (roundedImageView3 != null) {
            roundedImageView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpChildRankFragment$addAdapter$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextView textView = (TextView) UpChildRankFragment.this._$_findCachedViewById(R$id.text_rank_02);
                    UpChildRankFragment.this.jumpPersonHome(textView != null ? textView.getTag() : null);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void jumpPersonHome(Object obj) {
        if (obj == null || !(obj instanceof Integer) || !(!Intrinsics.areEqual(obj, 0))) {
            return;
        }
        NewMyHomePageActivity.Companion companion = NewMyHomePageActivity.Companion;
        Context mContext = getMContext();
        if (mContext != null) {
            companion.startActivity(mContext, ((Number) obj).intValue());
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    private final void setData(ArrayList<UpRankListBean> arrayList) {
        List<UpRankListBean> data;
        hideWaitingDialog();
        BaseQuickAdapter<UpRankListBean, BaseViewHolder> baseQuickAdapter = this.adapter;
        if (baseQuickAdapter != null && (data = baseQuickAdapter.getData()) != null) {
            data.clear();
            Unit unit = Unit.INSTANCE;
        }
        BaseQuickAdapter<UpRankListBean, BaseViewHolder> baseQuickAdapter2 = this.adapter;
        if (baseQuickAdapter2 != null) {
            baseQuickAdapter2.notifyDataSetChanged();
            Unit unit2 = Unit.INSTANCE;
        }
        int i = this.topListType;
        if (i == 1) {
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView != null) {
                textView.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView2 != null) {
                textView2.setText("0");
            }
            RoundedImageView roundedImageView = (RoundedImageView) _$_findCachedViewById(R$id.round_view_02);
            if (roundedImageView != null) {
                roundedImageView.setImageResource(R.drawable.default_img_head);
                Unit unit3 = Unit.INSTANCE;
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView3 != null) {
                textView3.setTag("");
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView4 != null) {
                textView4.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView5 != null) {
                textView5.setText("0");
            }
            RoundedImageView roundedImageView2 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_01);
            if (roundedImageView2 != null) {
                roundedImageView2.setImageResource(R.drawable.default_img_head);
                Unit unit4 = Unit.INSTANCE;
            }
            TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView6 != null) {
                textView6.setTag("");
            }
            TextView textView7 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView7 != null) {
                textView7.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView8 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView8 != null) {
                textView8.setText("0");
            }
            RoundedImageView roundedImageView3 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_03);
            if (roundedImageView3 != null) {
                roundedImageView3.setImageResource(R.drawable.default_img_head);
                Unit unit5 = Unit.INSTANCE;
            }
            TextView textView9 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView9 != null) {
                textView9.setTag("");
            }
        } else if (i == 2) {
            TextView textView10 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView10 != null) {
                textView10.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView11 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView11 != null) {
                textView11.setText(FormatUtil.formatNumOverTenThousand("0") + AppUtil.getString(R.string.up_creator_zp));
            }
            RoundedImageView roundedImageView4 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_02);
            if (roundedImageView4 != null) {
                roundedImageView4.setImageResource(R.drawable.default_img_head);
                Unit unit6 = Unit.INSTANCE;
            }
            TextView textView12 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView12 != null) {
                textView12.setTag("");
            }
            TextView textView13 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView13 != null) {
                textView13.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView14 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView14 != null) {
                textView14.setText(FormatUtil.formatNumOverTenThousand("0") + AppUtil.getString(R.string.up_creator_zp));
            }
            RoundedImageView roundedImageView5 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_01);
            if (roundedImageView5 != null) {
                roundedImageView5.setImageResource(R.drawable.default_img_head);
                Unit unit7 = Unit.INSTANCE;
            }
            TextView textView15 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView15 != null) {
                textView15.setTag("");
            }
            TextView textView16 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView16 != null) {
                textView16.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView17 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView17 != null) {
                textView17.setText(FormatUtil.formatNumOverTenThousand("0") + AppUtil.getString(R.string.up_creator_zp));
            }
            RoundedImageView roundedImageView6 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_03);
            if (roundedImageView6 != null) {
                roundedImageView6.setImageResource(R.drawable.default_img_head);
                Unit unit8 = Unit.INSTANCE;
            }
            TextView textView18 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView18 != null) {
                textView18.setTag("");
            }
            TextView textView19 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView19 != null) {
                textView19.setCompoundDrawables(null, null, null, null);
                Unit unit9 = Unit.INSTANCE;
            }
            TextView textView20 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView20 != null) {
                textView20.setCompoundDrawables(null, null, null, null);
                Unit unit10 = Unit.INSTANCE;
            }
            TextView textView21 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView21 != null) {
                textView21.setCompoundDrawables(null, null, null, null);
                Unit unit11 = Unit.INSTANCE;
            }
        } else if (i == 3) {
            TextView textView22 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView22 != null) {
                textView22.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView23 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView23 != null) {
                textView23.setText("0");
            }
            RoundedImageView roundedImageView7 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_02);
            if (roundedImageView7 != null) {
                roundedImageView7.setImageResource(R.drawable.default_img_head);
                Unit unit12 = Unit.INSTANCE;
            }
            TextView textView24 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView24 != null) {
                textView24.setTag("");
            }
            TextView textView25 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView25 != null) {
                textView25.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView26 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView26 != null) {
                textView26.setText("0");
            }
            RoundedImageView roundedImageView8 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_01);
            if (roundedImageView8 != null) {
                roundedImageView8.setImageResource(R.drawable.default_img_head);
                Unit unit13 = Unit.INSTANCE;
            }
            TextView textView27 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView27 != null) {
                textView27.setTag("");
            }
            TextView textView28 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView28 != null) {
                textView28.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView29 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView29 != null) {
                textView29.setText("0");
            }
            RoundedImageView roundedImageView9 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_03);
            if (roundedImageView9 != null) {
                roundedImageView9.setImageResource(R.drawable.default_img_head);
                Unit unit14 = Unit.INSTANCE;
            }
            TextView textView30 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView30 != null) {
                textView30.setTag("");
            }
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.creator_hot);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                Unit unit15 = Unit.INSTANCE;
            }
            TextView textView31 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView31 != null) {
                textView31.setCompoundDrawables(drawable, null, null, null);
                Unit unit16 = Unit.INSTANCE;
            }
            TextView textView32 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView32 != null) {
                textView32.setCompoundDrawables(drawable, null, null, null);
                Unit unit17 = Unit.INSTANCE;
            }
            TextView textView33 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView33 != null) {
                textView33.setCompoundDrawables(drawable, null, null, null);
                Unit unit18 = Unit.INSTANCE;
            }
        } else if (i == 4) {
            TextView textView34 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView34 != null) {
                textView34.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView35 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView35 != null) {
                textView35.setText(FormatUtil.formatNumOverTenThousand("0") + AppUtil.getString(R.string.up_creator_fans));
            }
            RoundedImageView roundedImageView10 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_02);
            if (roundedImageView10 != null) {
                roundedImageView10.setImageResource(R.drawable.default_img_head);
                Unit unit19 = Unit.INSTANCE;
            }
            TextView textView36 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView36 != null) {
                textView36.setTag("");
            }
            TextView textView37 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView37 != null) {
                textView37.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView38 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView38 != null) {
                textView38.setText(FormatUtil.formatNumOverTenThousand("0") + AppUtil.getString(R.string.up_creator_fans));
            }
            RoundedImageView roundedImageView11 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_01);
            if (roundedImageView11 != null) {
                roundedImageView11.setImageResource(R.drawable.default_img_head);
                Unit unit20 = Unit.INSTANCE;
            }
            TextView textView39 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView39 != null) {
                textView39.setTag("");
            }
            TextView textView40 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView40 != null) {
                textView40.setText(AppUtil.getString(R.string.up_rank_empty_name));
            }
            TextView textView41 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView41 != null) {
                textView41.setText(FormatUtil.formatNumOverTenThousand("0") + AppUtil.getString(R.string.up_creator_fans));
            }
            RoundedImageView roundedImageView12 = (RoundedImageView) _$_findCachedViewById(R$id.round_view_03);
            if (roundedImageView12 != null) {
                roundedImageView12.setImageResource(R.drawable.default_img_head);
                Unit unit21 = Unit.INSTANCE;
            }
            TextView textView42 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView42 != null) {
                textView42.setTag("");
            }
            TextView textView43 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView43 != null) {
                textView43.setCompoundDrawables(null, null, null, null);
                Unit unit22 = Unit.INSTANCE;
            }
            TextView textView44 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView44 != null) {
                textView44.setCompoundDrawables(null, null, null, null);
                Unit unit23 = Unit.INSTANCE;
            }
            TextView textView45 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView45 != null) {
                textView45.setCompoundDrawables(null, null, null, null);
                Unit unit24 = Unit.INSTANCE;
            }
        }
        ArrayList<UpRankListBean> arrayList2 = arrayList == null ? new ArrayList<>() : arrayList;
        if (arrayList2.size() == 0) {
            int i2 = 0;
            for (int i3 = 9; i2 <= i3; i3 = 9) {
                String string = AppUtil.getString(R.string.up_rank_empty_name);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.up_rank_empty_name)");
                arrayList2.add(new UpRankListBean("", 0, i2, string));
                i2++;
            }
        }
        if (arrayList2.size() < 10) {
            int i4 = 0;
            for (int i5 = 9; i4 <= i5; i5 = 9) {
                String string2 = AppUtil.getString(R.string.up_rank_empty_name);
                Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.up_rank_empty_name)");
                arrayList2.add(new UpRankListBean("", 0, i4, string2));
                if (arrayList2.size() == 10) {
                    break;
                }
                i4++;
            }
        }
        int i6 = this.topListType;
        if (i6 == 1) {
            UpRankListBean upRankListBean = arrayList2.get(0);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean, "tempList[0]");
            UpRankListBean upRankListBean2 = upRankListBean;
            TextView textView46 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView46 != null) {
                textView46.setText(upRankListBean2.getMemberName());
            }
            TextView textView47 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView47 != null) {
                textView47.setText(String.valueOf(FormatUtil.formatTomato2RMB(upRankListBean2.getIncome())));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_02), new ImageBean(upRankListBean2.getAvatar()));
            TextView textView48 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView48 != null) {
                textView48.setTag(Integer.valueOf(upRankListBean2.getMemberId()));
            }
            UpRankListBean upRankListBean3 = arrayList2.get(2);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean3, "tempList[2]");
            UpRankListBean upRankListBean4 = upRankListBean3;
            TextView textView49 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView49 != null) {
                textView49.setText(upRankListBean4.getMemberName());
            }
            TextView textView50 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView50 != null) {
                textView50.setText(String.valueOf(FormatUtil.formatTomato2RMB(upRankListBean4.getIncome())));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_03), new ImageBean(upRankListBean4.getAvatar()));
            TextView textView51 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView51 != null) {
                textView51.setTag(Integer.valueOf(upRankListBean4.getMemberId()));
            }
            UpRankListBean upRankListBean5 = arrayList2.get(1);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean5, "tempList[1]");
            UpRankListBean upRankListBean6 = upRankListBean5;
            TextView textView52 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView52 != null) {
                textView52.setText(upRankListBean6.getMemberName());
            }
            TextView textView53 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView53 != null) {
                textView53.setText(String.valueOf(FormatUtil.formatTomato2RMB(upRankListBean6.getIncome())));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_01), new ImageBean(upRankListBean6.getAvatar()));
            TextView textView54 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView54 != null) {
                textView54.setTag(Integer.valueOf(upRankListBean6.getMemberId()));
            }
        } else if (i6 == 2) {
            UpRankListBean upRankListBean7 = arrayList2.get(0);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean7, "tempList[0]");
            UpRankListBean upRankListBean8 = upRankListBean7;
            TextView textView55 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView55 != null) {
                textView55.setText(upRankListBean8.getMemberName());
            }
            TextView textView56 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView56 != null) {
                textView56.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean8.getIncome())) + AppUtil.getString(R.string.up_creator_zp)));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_02), new ImageBean(upRankListBean8.getAvatar()));
            TextView textView57 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView57 != null) {
                textView57.setTag(Integer.valueOf(upRankListBean8.getMemberId()));
            }
            UpRankListBean upRankListBean9 = arrayList2.get(2);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean9, "tempList[2]");
            UpRankListBean upRankListBean10 = upRankListBean9;
            TextView textView58 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView58 != null) {
                textView58.setText(upRankListBean10.getMemberName());
            }
            TextView textView59 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView59 != null) {
                textView59.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean10.getIncome())) + AppUtil.getString(R.string.up_creator_zp)));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_03), new ImageBean(upRankListBean10.getAvatar()));
            TextView textView60 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView60 != null) {
                textView60.setTag(Integer.valueOf(upRankListBean10.getMemberId()));
            }
            UpRankListBean upRankListBean11 = arrayList2.get(1);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean11, "tempList[1]");
            UpRankListBean upRankListBean12 = upRankListBean11;
            TextView textView61 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView61 != null) {
                textView61.setText(upRankListBean12.getMemberName());
            }
            TextView textView62 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView62 != null) {
                textView62.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean12.getIncome())) + AppUtil.getString(R.string.up_creator_zp)));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_01), new ImageBean(upRankListBean12.getAvatar()));
            TextView textView63 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView63 != null) {
                textView63.setTag(Integer.valueOf(upRankListBean12.getMemberId()));
            }
            TextView textView64 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView64 != null) {
                textView64.setCompoundDrawables(null, null, null, null);
                Unit unit25 = Unit.INSTANCE;
            }
            TextView textView65 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView65 != null) {
                textView65.setCompoundDrawables(null, null, null, null);
                Unit unit26 = Unit.INSTANCE;
            }
            TextView textView66 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView66 != null) {
                textView66.setCompoundDrawables(null, null, null, null);
                Unit unit27 = Unit.INSTANCE;
            }
        } else if (i6 == 3) {
            UpRankListBean upRankListBean13 = arrayList2.get(0);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean13, "tempList[0]");
            UpRankListBean upRankListBean14 = upRankListBean13;
            TextView textView67 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView67 != null) {
                textView67.setText(upRankListBean14.getMemberName());
            }
            TextView textView68 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView68 != null) {
                textView68.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean14.getIncome()))));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_02), new ImageBean(upRankListBean14.getAvatar()));
            TextView textView69 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView69 != null) {
                textView69.setTag(Integer.valueOf(upRankListBean14.getMemberId()));
            }
            UpRankListBean upRankListBean15 = arrayList2.get(2);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean15, "tempList[2]");
            UpRankListBean upRankListBean16 = upRankListBean15;
            TextView textView70 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView70 != null) {
                textView70.setText(upRankListBean16.getMemberName());
            }
            TextView textView71 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView71 != null) {
                textView71.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean16.getIncome()))));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_03), new ImageBean(upRankListBean16.getAvatar()));
            TextView textView72 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView72 != null) {
                textView72.setTag(Integer.valueOf(upRankListBean16.getMemberId()));
            }
            UpRankListBean upRankListBean17 = arrayList2.get(1);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean17, "tempList[1]");
            UpRankListBean upRankListBean18 = upRankListBean17;
            TextView textView73 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView73 != null) {
                textView73.setText(upRankListBean18.getMemberName());
            }
            TextView textView74 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView74 != null) {
                textView74.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean18.getIncome()))));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_01), new ImageBean(upRankListBean18.getAvatar()));
            TextView textView75 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView75 != null) {
                textView75.setTag(Integer.valueOf(upRankListBean18.getMemberId()));
            }
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            Drawable drawable2 = ContextCompat.getDrawable(mContext2, R.drawable.creator_hot);
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                Unit unit28 = Unit.INSTANCE;
            }
            TextView textView76 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView76 != null) {
                textView76.setCompoundDrawables(drawable2, null, null, null);
                Unit unit29 = Unit.INSTANCE;
            }
            TextView textView77 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView77 != null) {
                textView77.setCompoundDrawables(drawable2, null, null, null);
                Unit unit30 = Unit.INSTANCE;
            }
            TextView textView78 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView78 != null) {
                textView78.setCompoundDrawables(drawable2, null, null, null);
                Unit unit31 = Unit.INSTANCE;
            }
        } else if (i6 == 4) {
            UpRankListBean upRankListBean19 = arrayList2.get(0);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean19, "tempList[0]");
            UpRankListBean upRankListBean20 = upRankListBean19;
            TextView textView79 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView79 != null) {
                textView79.setText(upRankListBean20.getMemberName());
            }
            TextView textView80 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView80 != null) {
                textView80.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean20.getIncome())) + AppUtil.getString(R.string.up_creator_fans)));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_02), new ImageBean(upRankListBean20.getAvatar()));
            TextView textView81 = (TextView) _$_findCachedViewById(R$id.text_rank_02);
            if (textView81 != null) {
                textView81.setTag(Integer.valueOf(upRankListBean20.getMemberId()));
            }
            UpRankListBean upRankListBean21 = arrayList2.get(2);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean21, "tempList[2]");
            UpRankListBean upRankListBean22 = upRankListBean21;
            TextView textView82 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView82 != null) {
                textView82.setText(upRankListBean22.getMemberName());
            }
            TextView textView83 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView83 != null) {
                textView83.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean22.getIncome())) + AppUtil.getString(R.string.up_creator_fans)));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_03), new ImageBean(upRankListBean22.getAvatar()));
            TextView textView84 = (TextView) _$_findCachedViewById(R$id.text_rank_03);
            if (textView84 != null) {
                textView84.setTag(Integer.valueOf(upRankListBean22.getMemberId()));
            }
            UpRankListBean upRankListBean23 = arrayList2.get(1);
            Intrinsics.checkExpressionValueIsNotNull(upRankListBean23, "tempList[1]");
            UpRankListBean upRankListBean24 = upRankListBean23;
            TextView textView85 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView85 != null) {
                textView85.setText(upRankListBean24.getMemberName());
            }
            TextView textView86 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView86 != null) {
                textView86.setText(String.valueOf(FormatUtil.formatNumOverTenThousand(String.valueOf(upRankListBean24.getIncome())) + AppUtil.getString(R.string.up_creator_fans)));
            }
            ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view_01), new ImageBean(upRankListBean24.getAvatar()));
            TextView textView87 = (TextView) _$_findCachedViewById(R$id.text_rank_01);
            if (textView87 != null) {
                textView87.setTag(Integer.valueOf(upRankListBean24.getMemberId()));
            }
            TextView textView88 = (TextView) _$_findCachedViewById(R$id.text_total_num_03);
            if (textView88 != null) {
                textView88.setCompoundDrawables(null, null, null, null);
                Unit unit32 = Unit.INSTANCE;
            }
            TextView textView89 = (TextView) _$_findCachedViewById(R$id.text_total_num_01);
            if (textView89 != null) {
                textView89.setCompoundDrawables(null, null, null, null);
                Unit unit33 = Unit.INSTANCE;
            }
            TextView textView90 = (TextView) _$_findCachedViewById(R$id.text_total_num_02);
            if (textView90 != null) {
                textView90.setCompoundDrawables(null, null, null, null);
                Unit unit34 = Unit.INSTANCE;
            }
        }
        List<UpRankListBean> subList = arrayList2.subList(3, arrayList2.size());
        Intrinsics.checkExpressionValueIsNotNull(subList, "tempList.subList(3, tempList.size)");
        BaseQuickAdapter<UpRankListBean, BaseViewHolder> baseQuickAdapter3 = this.adapter;
        if (baseQuickAdapter3 != null) {
            baseQuickAdapter3.setNewData(subList);
            Unit unit35 = Unit.INSTANCE;
        }
        BaseQuickAdapter<UpRankListBean, BaseViewHolder> baseQuickAdapter4 = this.adapter;
        if (baseQuickAdapter4 != null) {
            baseQuickAdapter4.setEnableLoadMore(false);
            Unit unit36 = Unit.INSTANCE;
        }
    }
}
