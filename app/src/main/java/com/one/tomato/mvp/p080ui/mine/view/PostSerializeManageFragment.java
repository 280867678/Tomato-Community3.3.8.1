package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.PostSerializeBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.mine.adapter.PostSerializeManageAdapter;
import com.one.tomato.mvp.p080ui.mine.view.ChoosePostActivity;
import com.one.tomato.mvp.p080ui.mine.view.SingleSerialPostListActivity;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostSerializeManageFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment */
/* loaded from: classes3.dex */
public final class PostSerializeManageFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PostSerializeManageAdapter adapter;
    private int memberId;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;

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
        return R.layout.post_serialize_manage_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: PostSerializeManageFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PostSerializeManageFragment getInstance(int i) {
            PostSerializeManageFragment postSerializeManageFragment = new PostSerializeManageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("memberId", i);
            postSerializeManageFragment.setArguments(bundle);
            return postSerializeManageFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        initRefreshLayout();
        initRecyclerView();
        initAdapter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        Button button = (Button) _$_findCachedViewById(R$id.button_delete);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$inintData$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostSerializeManageAdapter postSerializeManageAdapter;
                    postSerializeManageAdapter = PostSerializeManageFragment.this.adapter;
                    List<PostSerializeBean> data = postSerializeManageAdapter != null ? postSerializeManageAdapter.getData() : null;
                    if ((data == null || data.isEmpty()) || data.size() <= 0) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (PostSerializeBean it2 : data) {
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        if (it2.isSelect()) {
                            if (sb.length() > 0) {
                                sb.append(",");
                            }
                            sb.append(String.valueOf(it2.getId()));
                        }
                    }
                    PostSerializeManageFragment postSerializeManageFragment = PostSerializeManageFragment.this;
                    String sb2 = sb.toString();
                    Intrinsics.checkExpressionValueIsNotNull(sb2, "ids.toString()");
                    postSerializeManageFragment.showDeleteDialog(sb2);
                }
            });
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_serialize_all);
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$inintData$2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    PostSerializeManageFragment.this.selectAllItem(z);
                }
            });
        }
        PostSerializeManageAdapter postSerializeManageAdapter = this.adapter;
        if (postSerializeManageAdapter != null) {
            postSerializeManageAdapter.addCheckBoxCallBack(new PostSerializeManageFragment$inintData$3(this));
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_create_serialize);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$inintData$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    ChoosePostActivity.Companion companion = ChoosePostActivity.Companion;
                    mContext = PostSerializeManageFragment.this.getMContext();
                    if (mContext != null) {
                        companion.startActivity(mContext, PostSerializeManageFragment.this, 1, new ArrayList<>());
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        Bundle arguments = getArguments();
        this.memberId = arguments != null ? arguments.getInt("memberId") : 0;
        if (DBUtil.getMemberId() != this.memberId) {
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_create_serialize);
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_tip);
            if (textView3 == null) {
                return;
            }
            textView3.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void selectAllItem(boolean z) {
        PostSerializeManageAdapter postSerializeManageAdapter = this.adapter;
        List<PostSerializeBean> data = postSerializeManageAdapter != null ? postSerializeManageAdapter.getData() : null;
        if (!(data == null || data.isEmpty())) {
            for (PostSerializeBean it2 : data) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                it2.setSelect(z);
            }
            PostSerializeManageAdapter postSerializeManageAdapter2 = this.adapter;
            if (postSerializeManageAdapter2 != null) {
                postSerializeManageAdapter2.notifyDataSetChanged();
            }
            Button button = (Button) _$_findCachedViewById(R$id.button_delete);
            if (button != null) {
                button.setEnabled(z);
            }
        }
        selectNum();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void selectNum() {
        boolean z;
        int i;
        PostSerializeManageAdapter postSerializeManageAdapter = this.adapter;
        List<PostSerializeBean> data = postSerializeManageAdapter != null ? postSerializeManageAdapter.getData() : null;
        if (!(data == null || data.isEmpty())) {
            z = false;
            i = 0;
            for (PostSerializeBean it2 : data) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (it2.isSelect()) {
                    i++;
                    z = true;
                }
            }
        } else {
            z = false;
            i = 0;
        }
        Button button_delete = (Button) _$_findCachedViewById(R$id.button_delete);
        Intrinsics.checkExpressionValueIsNotNull(button_delete, "button_delete");
        button_delete.setEnabled(z);
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_serialize_choose_num);
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_serialize_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…ost_serialize_choose_num)");
            Object[] objArr = {String.valueOf(i)};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
    }

    protected final void initRefreshLayout() {
        View layoutView = getLayoutView();
        this.refreshLayout = layoutView != null ? (SmartRefreshLayout) layoutView.findViewById(R.id.refreshLayout) : null;
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setEnableRefresh(true);
        }
        SmartRefreshLayout smartRefreshLayout2 = this.refreshLayout;
        if (smartRefreshLayout2 != null) {
            smartRefreshLayout2.mo6487setEnableLoadMore(false);
        }
        SmartRefreshLayout smartRefreshLayout3 = this.refreshLayout;
        if (smartRefreshLayout3 != null) {
            smartRefreshLayout3.mo6486setEnableAutoLoadMore(false);
        }
        SmartRefreshLayout smartRefreshLayout4 = this.refreshLayout;
        if (smartRefreshLayout4 != null) {
            smartRefreshLayout4.setOnRefreshLoadMoreListener(new PostSerializeManageFragment$initRefreshLayout$1(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refresh() {
        ApiImplService.Companion.getApiImplService().requestSerializeGroup(this.memberId).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxFragment) this)).subscribe(new ApiDisposableObserver<ArrayList<PostSerializeBean>>() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$refresh$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostSerializeBean> arrayList) {
                PostSerializeManageFragment.this.updateData(arrayList);
            }

            /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
                r3 = r2.this$0.adapter;
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onResultError(ResponseThrowable responseThrowable) {
                SmartRefreshLayout smartRefreshLayout;
                PostSerializeManageAdapter postSerializeManageAdapter;
                List<PostSerializeBean> data;
                PostSerializeManageAdapter postSerializeManageAdapter2;
                SmartRefreshLayout smartRefreshLayout2;
                smartRefreshLayout = PostSerializeManageFragment.this.refreshLayout;
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.mo6481finishRefresh();
                }
                postSerializeManageAdapter = PostSerializeManageFragment.this.adapter;
                if (postSerializeManageAdapter == null || (data = postSerializeManageAdapter.getData()) == null || data.size() != 0 || postSerializeManageAdapter2 == null) {
                    return;
                }
                smartRefreshLayout2 = PostSerializeManageFragment.this.refreshLayout;
                postSerializeManageAdapter2.setEmptyViewState(1, smartRefreshLayout2);
            }
        });
    }

    protected void initRecyclerView() {
        View layoutView = getLayoutView();
        RecyclerView.ItemAnimator itemAnimator = null;
        this.recyclerView = layoutView != null ? (RecyclerView) layoutView.findViewById(R.id.recyclerView) : null;
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 != null) {
            itemAnimator = recyclerView2.getItemAnimator();
        }
        SimpleItemAnimator simpleItemAnimator = (SimpleItemAnimator) itemAnimator;
        if (simpleItemAnimator != null) {
            simpleItemAnimator.setSupportsChangeAnimations(false);
        }
        configLinearLayoutVerticalManager(this.recyclerView);
    }

    protected void initAdapter() {
        Context mContext = getMContext();
        if (mContext != null) {
            this.adapter = new PostSerializeManageAdapter(mContext, this.recyclerView);
            RecyclerView recyclerView = this.recyclerView;
            if (recyclerView != null) {
                recyclerView.setAdapter(this.adapter);
            }
            PostSerializeManageAdapter postSerializeManageAdapter = this.adapter;
            if (postSerializeManageAdapter != null) {
                postSerializeManageAdapter.setEnableLoadMore(false);
            }
            PostSerializeManageAdapter postSerializeManageAdapter2 = this.adapter;
            if (postSerializeManageAdapter2 == null) {
                return;
            }
            postSerializeManageAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$initAdapter$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    Context mContext2;
                    String str;
                    int i2;
                    List<Object> data;
                    PostSerializeBean postSerializeBean = (PostSerializeBean) ((baseQuickAdapter == null || (data = baseQuickAdapter.getData()) == null) ? null : data.get(i));
                    SingleSerialPostListActivity.Companion companion = SingleSerialPostListActivity.Companion;
                    mContext2 = PostSerializeManageFragment.this.getMContext();
                    boolean z = false;
                    int id = postSerializeBean != null ? postSerializeBean.getId() : 0;
                    if (postSerializeBean == null || (str = postSerializeBean.getTitle()) == null) {
                        str = "";
                    }
                    int memberId = DBUtil.getMemberId();
                    i2 = PostSerializeManageFragment.this.memberId;
                    if (memberId == i2) {
                        z = true;
                    }
                    companion.startActivity(mContext2, id, str, z);
                }
            });
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    protected final void configLinearLayoutVerticalManager(RecyclerView recyclerView) {
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getMContext(), 1, false);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.autoRefresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateData(ArrayList<PostSerializeBean> arrayList) {
        List<PostSerializeBean> data;
        PostSerializeManageAdapter postSerializeManageAdapter;
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.mo6481finishRefresh();
        }
        if (arrayList == null || arrayList.isEmpty()) {
            PostSerializeManageAdapter postSerializeManageAdapter2 = this.adapter;
            if (postSerializeManageAdapter2 == null || (data = postSerializeManageAdapter2.getData()) == null || data.size() != 0 || (postSerializeManageAdapter = this.adapter) == null) {
                return;
            }
            postSerializeManageAdapter.setEmptyViewState(2, this.refreshLayout);
            return;
        }
        PostSerializeManageAdapter postSerializeManageAdapter3 = this.adapter;
        if (postSerializeManageAdapter3 != null) {
            postSerializeManageAdapter3.setNewData(arrayList);
        }
        PostSerializeManageAdapter postSerializeManageAdapter4 = this.adapter;
        if (postSerializeManageAdapter4 != null) {
            postSerializeManageAdapter4.setEnableLoadMore(false);
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_delete);
        if (button != null) {
            button.setEnabled(false);
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_serialize_all);
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_serialize_choose_num);
        if (textView == null) {
            return;
        }
        String string = AppUtil.getString(R.string.post_serialize_choose_num);
        Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…ost_serialize_choose_num)");
        Object[] objArr = {"0"};
        String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
        textView.setText(format);
    }

    public final void deleteOrCancel(int i) {
        Button button = (Button) _$_findCachedViewById(R$id.button_delete);
        if (button != null) {
            button.setEnabled(false);
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_serialize_all);
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_serialize_choose_num);
        boolean z = true;
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_serialize_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…ost_serialize_choose_num)");
            Object[] objArr = {"0"};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
        List<PostSerializeBean> list = null;
        if (i == 1) {
            PostSerializeManageAdapter postSerializeManageAdapter = this.adapter;
            if (postSerializeManageAdapter != null) {
                list = postSerializeManageAdapter.getData();
            }
            if ((list == null || list.isEmpty()) || list.size() <= 0) {
                return;
            }
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_serialize_choose);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            Button button2 = (Button) _$_findCachedViewById(R$id.button_delete);
            if (button2 != null) {
                button2.setVisibility(0);
            }
            for (PostSerializeBean it2 : list) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                it2.setSelect(false);
                it2.setShowCheckBox(true);
            }
            PostSerializeManageAdapter postSerializeManageAdapter2 = this.adapter;
            if (postSerializeManageAdapter2 == null) {
                return;
            }
            postSerializeManageAdapter2.notifyDataSetChanged();
            return;
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_serialize_choose);
        if (relativeLayout2 != null) {
            relativeLayout2.setVisibility(8);
        }
        Button button3 = (Button) _$_findCachedViewById(R$id.button_delete);
        if (button3 != null) {
            button3.setVisibility(8);
        }
        PostSerializeManageAdapter postSerializeManageAdapter3 = this.adapter;
        if (postSerializeManageAdapter3 != null) {
            list = postSerializeManageAdapter3.getData();
        }
        if (list != null && !list.isEmpty()) {
            z = false;
        }
        if (z || list.size() <= 0) {
            return;
        }
        for (PostSerializeBean it3 : list) {
            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
            it3.setSelect(false);
            it3.setShowCheckBox(false);
        }
        PostSerializeManageAdapter postSerializeManageAdapter4 = this.adapter;
        if (postSerializeManageAdapter4 == null) {
            return;
        }
        postSerializeManageAdapter4.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDeleteDialog(final String str) {
        Context mContext = getMContext();
        if (mContext != null) {
            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
            customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
            customAlertDialog.setMessage(AppUtil.getString(R.string.post_serialize_delete));
            customAlertDialog.setBottomHorizontalLineVisible(false);
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            customAlertDialog.setTitleBackgroundDrawable(ContextCompat.getDrawable(mContext2, R.drawable.common_shape_solid_corner12_white));
            customAlertDialog.setConfirmButton(AppUtil.getString(R.string.common_confirm), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$showDeleteDialog$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostSerializeManageFragment.this.deleteSerialize(str);
                    customAlertDialog.dismiss();
                }
            });
            customAlertDialog.setCancelButton(AppUtil.getString(R.string.common_cancel), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$showDeleteDialog$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CustomAlertDialog.this.dismiss();
                }
            });
            Context mContext3 = getMContext();
            if (mContext3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            customAlertDialog.setCancleButtonBackgroundDrable(ContextCompat.getDrawable(mContext3, R.drawable.common_shape_solid_corner5_disable));
            customAlertDialog.show();
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteSerialize(String str) {
        ApiImplService.Companion.getApiImplService().deleteSerialize(str).compose(RxUtils.bindToLifecycler((RxFragment) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$deleteSerialize$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                PostSerializeManageFragment.this.showDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.view.PostSerializeManageFragment$deleteSerialize$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                PostSerializeManageAdapter postSerializeManageAdapter;
                PostSerializeManageAdapter postSerializeManageAdapter2;
                PostSerializeManageFragment.this.dismissDialog();
                postSerializeManageAdapter = PostSerializeManageFragment.this.adapter;
                List<PostSerializeBean> data = postSerializeManageAdapter != null ? postSerializeManageAdapter.getData() : null;
                ArrayList arrayList = new ArrayList();
                if (!(data == null || data.isEmpty()) && data.size() > 0) {
                    for (PostSerializeBean it2 : data) {
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        if (it2.isSelect()) {
                            arrayList.add(it2);
                        }
                    }
                    if (arrayList.size() > 0) {
                        Iterator it3 = arrayList.iterator();
                        while (it3.hasNext()) {
                            PostSerializeBean postSerializeBean = (PostSerializeBean) it3.next();
                            if (data.contains(postSerializeBean)) {
                                data.remove(postSerializeBean);
                            }
                        }
                        postSerializeManageAdapter2 = PostSerializeManageFragment.this.adapter;
                        if (postSerializeManageAdapter2 != null) {
                            postSerializeManageAdapter2.notifyDataSetChanged();
                        }
                    }
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_delete_success));
                CheckBox checkBox = (CheckBox) PostSerializeManageFragment.this._$_findCachedViewById(R$id.checkbox_serialize_all);
                if (checkBox != null) {
                    checkBox.setChecked(false);
                }
                Button button_delete = (Button) PostSerializeManageFragment.this._$_findCachedViewById(R$id.button_delete);
                Intrinsics.checkExpressionValueIsNotNull(button_delete, "button_delete");
                button_delete.setEnabled(false);
                TextView textView = (TextView) PostSerializeManageFragment.this._$_findCachedViewById(R$id.text_serialize_choose_num);
                if (textView != null) {
                    String string = AppUtil.getString(R.string.post_serialize_choose_num);
                    Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.stri…ost_serialize_choose_num)");
                    Object[] objArr = {"0"};
                    String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                    textView.setText(format);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PostSerializeManageFragment.this.dismissDialog();
            }
        });
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        SmartRefreshLayout smartRefreshLayout;
        super.onActivityResult(i, i2, intent);
        if (i != 100 || (smartRefreshLayout = this.refreshLayout) == null) {
            return;
        }
        smartRefreshLayout.autoRefresh();
    }
}
