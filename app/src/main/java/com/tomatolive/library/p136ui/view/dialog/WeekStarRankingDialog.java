package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.p136ui.adapter.GiftTagAdapter;
import com.tomatolive.library.p136ui.fragment.RankingWeekStarLiveFragment;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

/* renamed from: com.tomatolive.library.ui.view.dialog.WeekStarRankingDialog */
/* loaded from: classes3.dex */
public class WeekStarRankingDialog extends BaseBottomDialogFragment {
    private AnchorEntity anchorItemEntity;
    private CommonNavigator commonNavigator;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private GiftTagAdapter menuTagAdapter;
    private List<BaseFragment> fragmentList = new ArrayList();
    private List<GiftDownloadItemEntity> labelList = new ArrayList();
    private OnUserCardCallback onUserCardCallback = null;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return 0.56d;
    }

    public static WeekStarRankingDialog newInstance(AnchorEntity anchorEntity, OnUserCardCallback onUserCardCallback) {
        Bundle bundle = new Bundle();
        WeekStarRankingDialog weekStarRankingDialog = new WeekStarRankingDialog();
        weekStarRankingDialog.setArguments(bundle);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        weekStarRankingDialog.setOnUserCardCallback(onUserCardCallback);
        return weekStarRankingDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.anchorItemEntity = (AnchorEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_week_star_ranking;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.magicIndicator = (MagicIndicator) view.findViewById(R$id.magic_indicator_rank);
        this.mViewPager = (ViewPager) view.findViewById(R$id.view_pager_rank);
        initViewPager(this.magicIndicator, this.mViewPager);
        sendRequest();
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }

    private void sendRequest() {
        ApiRetrofit.getInstance().getApiService().getStarGifListService(new RequestParams().getAppIdParams()).map(new ServerResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.WeekStarRankingDialog.3
        }).flatMap(new Function<List<GiftDownloadItemEntity>, ObservableSource<List<GiftDownloadItemEntity>>>() { // from class: com.tomatolive.library.ui.view.dialog.WeekStarRankingDialog.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<List<GiftDownloadItemEntity>> mo6755apply(List<GiftDownloadItemEntity> list) throws Exception {
                return Observable.just(AppUtils.formatWeekStarGiftList(((BaseRxDialogFragment) WeekStarRankingDialog.this).mContext, list));
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.WeekStarRankingDialog.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<GiftDownloadItemEntity> list) {
                WeekStarRankingDialog.this.initFragmentLabelList(list);
                WeekStarRankingDialog.this.notifyDataSetChangedViewPager();
            }
        });
    }

    private void initViewPager(MagicIndicator magicIndicator, final ViewPager viewPager) {
        this.menuTagAdapter = new GiftTagAdapter(this.fragmentList, this.labelList, getChildFragmentManager());
        this.commonNavigator = new CommonNavigator(this.mContext);
        this.commonNavigatorAdapter = new CommonNavigatorAdapter() { // from class: com.tomatolive.library.ui.view.dialog.WeekStarRankingDialog.4
            @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public int getCount() {
                if (WeekStarRankingDialog.this.fragmentList == null) {
                    return 0;
                }
                return WeekStarRankingDialog.this.fragmentList.size();
            }

            @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerTitleView getTitleView(Context context, final int i) {
                GiftDownloadItemEntity giftDownloadItemEntity = (GiftDownloadItemEntity) WeekStarRankingDialog.this.labelList.get(i);
                if (giftDownloadItemEntity == null) {
                    return null;
                }
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                final View inflate = LayoutInflater.from(context).inflate(R$layout.fq_layout_gift_tab_label_view, (ViewGroup) null);
                final TextView textView = (TextView) inflate.findViewById(R$id.tv_gift_name);
                textView.setText(StringUtils.formatStrLen(giftDownloadItemEntity.name, 3));
                GlideUtils.loadImage(((BaseRxDialogFragment) WeekStarRankingDialog.this).mContext, (ImageView) inflate.findViewById(R$id.iv_gift_img), giftDownloadItemEntity.imgurl, R$drawable.fq_ic_gift_default);
                commonPagerTitleView.setContentView(inflate);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.tomatolive.library.ui.view.dialog.WeekStarRankingDialog.4.1
                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onEnter(int i2, int i3, float f, boolean z) {
                    }

                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onLeave(int i2, int i3, float f, boolean z) {
                    }

                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onSelected(int i2, int i3) {
                        textView.setTextColor(ContextCompat.getColor(((BaseRxDialogFragment) WeekStarRankingDialog.this).mContext, R$color.fq_tab_menu_text_select_color));
                        inflate.setBackgroundColor(ContextCompat.getColor(((BaseRxDialogFragment) WeekStarRankingDialog.this).mContext, R$color.fq_week_star_gift_tab_selected));
                    }

                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onDeselected(int i2, int i3) {
                        textView.setTextColor(ContextCompat.getColor(((BaseRxDialogFragment) WeekStarRankingDialog.this).mContext, R$color.fq_text_black));
                        inflate.setBackgroundColor(ContextCompat.getColor(((BaseRxDialogFragment) WeekStarRankingDialog.this).mContext, R$color.fq_week_star_gift_tab_normal));
                    }
                });
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.WeekStarRankingDialog.4.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        viewPager.setCurrentItem(i);
                    }
                });
                return commonPagerTitleView;
            }
        };
        this.commonNavigator.setAdapter(this.commonNavigatorAdapter);
        magicIndicator.setNavigator(this.commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        viewPager.setAdapter(this.menuTagAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFragmentLabelList(List<GiftDownloadItemEntity> list) {
        List<BaseFragment> list2 = this.fragmentList;
        if (list2 != null) {
            list2.clear();
        }
        List<GiftDownloadItemEntity> list3 = this.labelList;
        if (list3 != null) {
            list3.clear();
        }
        if (list == null || list.size() <= 0) {
            return;
        }
        for (GiftDownloadItemEntity giftDownloadItemEntity : list) {
            addFragment(RankingWeekStarLiveFragment.newInstance(this.anchorItemEntity, giftDownloadItemEntity.markId, this.onUserCardCallback), giftDownloadItemEntity.name, giftDownloadItemEntity.imgurl);
        }
    }

    private void addFragment(BaseFragment baseFragment, String str, String str2) {
        if (this.fragmentList == null) {
            this.fragmentList = new ArrayList();
        }
        if (this.labelList == null) {
            this.labelList = new ArrayList();
        }
        if (baseFragment == null || baseFragment.isAdded()) {
            return;
        }
        this.fragmentList.add(baseFragment);
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.name = str;
        giftItemEntity.imgurl = str2;
        this.labelList.add(giftItemEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataSetChangedViewPager() {
        this.commonNavigatorAdapter.notifyDataSetChanged();
        this.menuTagAdapter.notifyDataSetChanged();
        this.mViewPager.setOffscreenPageLimit(this.fragmentList.size());
        this.mViewPager.setCurrentItem(0, false);
    }
}
