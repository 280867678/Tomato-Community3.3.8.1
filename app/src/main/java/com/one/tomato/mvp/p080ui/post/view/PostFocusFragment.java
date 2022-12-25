package com.one.tomato.mvp.p080ui.post.view;

import android.support.p002v4.app.FragmentTransaction;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.FocusHotPostListBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.PostFragmentChange;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.tomatolive.library.http.RequestParams;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostFocusFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostFocusFragment */
/* loaded from: classes3.dex */
public final class PostFocusFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private MyHomePagePostFragment memberNotFocusFragment;
    private boolean needRefresh;
    private MyHomePagePostFragment postListFragment;
    private boolean showMemberNotFocusFragment;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_post_focus;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        getFocusListFromServer();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onVisibleLoad() {
        super.onVisibleLoad();
        if (this.needRefresh) {
            getFocusListFromServer();
            this.needRefresh = false;
        }
        if (this.showMemberNotFocusFragment) {
            MyHomePagePostFragment myHomePagePostFragment = this.memberNotFocusFragment;
            if (myHomePagePostFragment == null || myHomePagePostFragment == null) {
                return;
            }
            myHomePagePostFragment.fragmentIsVisible(true);
            return;
        }
        MyHomePagePostFragment myHomePagePostFragment2 = this.postListFragment;
        if (myHomePagePostFragment2 == null || myHomePagePostFragment2 == null) {
            return;
        }
        myHomePagePostFragment2.fragmentIsVisible(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onInvisibleLoad() {
        super.onInvisibleLoad();
        MyHomePagePostFragment myHomePagePostFragment = this.postListFragment;
        if (myHomePagePostFragment != null && myHomePagePostFragment != null) {
            myHomePagePostFragment.fragmentIsVisible(false);
        }
        MyHomePagePostFragment myHomePagePostFragment2 = this.memberNotFocusFragment;
        if (myHomePagePostFragment2 == null || myHomePagePostFragment2 == null) {
            return;
        }
        myHomePagePostFragment2.fragmentIsVisible(false);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!this.needRefresh || !isVisibleToUser()) {
            return;
        }
        getFocusListFromServer();
        this.needRefresh = false;
    }

    private final void getFocusListFromServer() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", 1);
        linkedHashMap.put(RequestParams.PAGE_SIZE, 1);
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        ApiImplService.Companion.getApiImplService().requestFoucsPostList(linkedHashMap).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxFragment) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.post.view.PostFocusFragment$getFocusListFromServer$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                PostFocusFragment.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<FocusHotPostListBean>() { // from class: com.one.tomato.mvp.ui.post.view.PostFocusFragment$getFocusListFromServer$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(FocusHotPostListBean focusHotPostListBean) {
                PostFocusFragment.this.dismissDialog();
                ArrayList<PostList> data = focusHotPostListBean != null ? focusHotPostListBean.getData() : null;
                if (data != null) {
                    if ((data == null || data.isEmpty()) && focusHotPostListBean.isFollowFlag() == 0) {
                        PostFocusFragment.this.showMemberNotFocusFragment();
                        return;
                    }
                }
                PostFocusFragment.this.showPostListFragment();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PostFocusFragment.this.dismissDialog();
                PostFocusFragment.this.showMemberNotFocusFragment();
            }
        });
    }

    public final void showPostListFragment() {
        this.showMemberNotFocusFragment = false;
        if (this.memberNotFocusFragment != null) {
            FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
            MyHomePagePostFragment myHomePagePostFragment = this.memberNotFocusFragment;
            if (myHomePagePostFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            beginTransaction.hide(myHomePagePostFragment).commitAllowingStateLoss();
        }
        if (this.postListFragment == null) {
            this.postListFragment = MyHomePagePostFragment.Companion.getInstance(-1, "focus", true);
            FragmentTransaction beginTransaction2 = getChildFragmentManager().beginTransaction();
            MyHomePagePostFragment myHomePagePostFragment2 = this.postListFragment;
            if (myHomePagePostFragment2 != null) {
                beginTransaction2.add(R.id.framelayout, myHomePagePostFragment2).commitAllowingStateLoss();
                return;
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        FragmentTransaction beginTransaction3 = getChildFragmentManager().beginTransaction();
        MyHomePagePostFragment myHomePagePostFragment3 = this.postListFragment;
        if (myHomePagePostFragment3 != null) {
            beginTransaction3.show(myHomePagePostFragment3).commitAllowingStateLoss();
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showMemberNotFocusFragment() {
        this.showMemberNotFocusFragment = true;
        if (this.postListFragment != null) {
            FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
            MyHomePagePostFragment myHomePagePostFragment = this.postListFragment;
            if (myHomePagePostFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            beginTransaction.hide(myHomePagePostFragment).commitAllowingStateLoss();
        }
        if (this.memberNotFocusFragment == null) {
            this.memberNotFocusFragment = MyHomePagePostFragment.Companion.getInstance(-1, "no_focus", true);
            FragmentTransaction beginTransaction2 = getChildFragmentManager().beginTransaction();
            MyHomePagePostFragment myHomePagePostFragment2 = this.memberNotFocusFragment;
            if (myHomePagePostFragment2 != null) {
                beginTransaction2.add(R.id.framelayout, myHomePagePostFragment2).commitAllowingStateLoss();
                return;
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        FragmentTransaction beginTransaction3 = getChildFragmentManager().beginTransaction();
        MyHomePagePostFragment myHomePagePostFragment3 = this.memberNotFocusFragment;
        if (myHomePagePostFragment3 != null) {
            beginTransaction3.show(myHomePagePostFragment3).commitAllowingStateLoss();
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    public final void onEventMainThread(PostFragmentChange event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        int i = event.flag;
        if (i == 1) {
            showPostListFragment();
        } else if (i != 2) {
        } else {
            showMemberNotFocusFragment();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        this.needRefresh = true;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.one.tomato.net.LoginOutResponseObserver
    public void onLoginOutSuccess() {
        super.onLoginOutSuccess();
        this.needRefresh = true;
    }
}
