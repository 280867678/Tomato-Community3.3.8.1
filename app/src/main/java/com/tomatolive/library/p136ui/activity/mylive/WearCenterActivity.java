package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.p136ui.adapter.HomeMenuTagAdapter;
import com.tomatolive.library.p136ui.fragment.WearCenterSpeakMedalFragment;
import com.tomatolive.library.p136ui.view.widget.DrawableTransitionPagerTitleView;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

/* renamed from: com.tomatolive.library.ui.activity.mylive.WearCenterActivity */
/* loaded from: classes3.dex */
public class WearCenterActivity extends BaseActivity {
    private int currentIndex = 0;

    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter */
    protected BasePresenter mo6636createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_wear_center;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_achieve_wear_center);
        initMagicIndicator(this.mContext, getSupportFragmentManager(), (MagicIndicator) findViewById(R$id.magic_indicator), (ViewPager) findViewById(R$id.view_pager), getFragmentList(), getLabelList(), this.currentIndex);
    }

    private List<BaseFragment> getFragmentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(WearCenterSpeakMedalFragment.newInstance());
        return arrayList;
    }

    private List<LabelEntity> getLabelList() {
        ArrayList arrayList = new ArrayList();
        for (String str : getResources().getStringArray(R$array.fq_achieve_wear_center_menu)) {
            arrayList.add(new LabelEntity(str));
        }
        return arrayList;
    }

    private void initMagicIndicator(Context context, FragmentManager fragmentManager, MagicIndicator magicIndicator, ViewPager viewPager, List<BaseFragment> list, List<LabelEntity> list2, int i) {
        viewPager.setAdapter(new HomeMenuTagAdapter(list, list2, fragmentManager));
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new C40781(list, list2, viewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        viewPager.setOffscreenPageLimit(list.size());
        viewPager.setCurrentItem(i, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.mylive.WearCenterActivity$1 */
    /* loaded from: classes3.dex */
    public class C40781 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$fragmentList;
        final /* synthetic */ List val$itemList;
        final /* synthetic */ ViewPager val$pager;

        C40781(List list, List list2, ViewPager viewPager) {
            this.val$fragmentList = list;
            this.val$itemList = list2;
            this.val$pager = viewPager;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            List list = this.val$fragmentList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int i) {
            DrawableTransitionPagerTitleView drawableTransitionPagerTitleView = new DrawableTransitionPagerTitleView(context);
            drawableTransitionPagerTitleView.setText(((LabelEntity) this.val$itemList.get(i)).name);
            drawableTransitionPagerTitleView.setTextSize(2, 14.0f);
            drawableTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_color));
            drawableTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, R$color.image_color_white));
            final ViewPager viewPager = this.val$pager;
            drawableTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$WearCenterActivity$1$XW6kew8RvYXkYE30yKVoLAul820
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewPager.this.setCurrentItem(i, false);
                }
            });
            return drawableTransitionPagerTitleView;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseActivity) WearCenterActivity.this).mContext, R$color.fq_color_transparent)));
            linePagerIndicator.setMode(1);
            return linePagerIndicator;
        }
    }
}
