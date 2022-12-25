package com.tomatolive.library.p136ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.p136ui.presenter.WearCenterPresenter;

/* renamed from: com.tomatolive.library.ui.fragment.WearCenterSpeakBubbleFragment */
/* loaded from: classes3.dex */
public class WearCenterSpeakBubbleFragment extends BaseFragment {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public WearCenterPresenter mo6641createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
    }

    public static WearCenterSpeakBubbleFragment newInstance() {
        Bundle bundle = new Bundle();
        WearCenterSpeakBubbleFragment wearCenterSpeakBubbleFragment = new WearCenterSpeakBubbleFragment();
        wearCenterSpeakBubbleFragment.setArguments(bundle);
        return wearCenterSpeakBubbleFragment;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_achieve_fragment_speak_bubble;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }
}
