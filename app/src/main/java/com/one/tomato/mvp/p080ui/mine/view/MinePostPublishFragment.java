package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
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
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.mine.adapter.MinePostPublishAdapter;
import com.one.tomato.mvp.p080ui.mine.impl.IMinePostPublish;
import com.one.tomato.mvp.p080ui.mine.presenter.MinePostPublishPresenter;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MinePostPublishFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.MinePostPublishFragment */
/* loaded from: classes3.dex */
public class MinePostPublishFragment extends MvpBaseRecyclerViewFragment<IMinePostPublish, MinePostPublishPresenter, MinePostPublishAdapter, PostList> implements IMinePostPublish {
    private HashMap _$_findViewCache;
    private Function1<? super Boolean, Unit> checkBoxCallBack;
    private int pageSize = 10;
    private int postType;
    private Functions<? extends ArrayList<PostList>> pullSelectData;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
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
        return R.layout.mine_post_publish_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    public static final /* synthetic */ MinePostPublishPresenter access$getMPresenter$p(MinePostPublishFragment minePostPublishFragment) {
        return (MinePostPublishPresenter) minePostPublishFragment.getMPresenter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getPostType() {
        return this.postType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setPostType(int i) {
        this.postType = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public int getPageSize() {
        return this.pageSize;
    }

    public final Function1<Boolean, Unit> getCheckBoxCallBack() {
        return this.checkBoxCallBack;
    }

    public final Functions<ArrayList<PostList>> getPullSelectData() {
        return this.pullSelectData;
    }

    public final void addCheckBoxCallBack(Function1<? super Boolean, Unit> function1) {
        this.checkBoxCallBack = function1;
    }

    public final void addPullSelectCall(Functions<? extends ArrayList<PostList>> functions) {
        this.pullSelectData = functions;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        setPageNo(1);
        setState(getGET_LIST_REFRESH());
        requestData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        setState(getGET_LIST_LOAD());
        requestData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public MinePostPublishAdapter mo6434createAdapter() {
        return new MinePostPublishAdapter(getMContext(), getRecyclerView());
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MinePostPublishPresenter mo6441createPresenter() {
        return new MinePostPublishPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        super.initView();
        Button button = (Button) _$_findCachedViewById(R$id.button_ok);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MinePostPublishFragment$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MinePostPublishAdapter adapter;
                    adapter = MinePostPublishFragment.this.getAdapter();
                    List<PostList> data = adapter != null ? adapter.getData() : null;
                    if ((data == null || data.isEmpty()) || data.size() <= 0) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (PostList it2 : data) {
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        if (it2.isSelectMinePostDelete()) {
                            if (sb.length() > 0) {
                                sb.append(",");
                            }
                            sb.append(String.valueOf(it2.getId()));
                        }
                    }
                    MinePostPublishFragment minePostPublishFragment = MinePostPublishFragment.this;
                    String sb2 = sb.toString();
                    Intrinsics.checkExpressionValueIsNotNull(sb2, "ids.toString()");
                    minePostPublishFragment.showDeleteDialog(sb2);
                }
            });
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.one.tomato.mvp.ui.mine.view.MinePostPublishFragment$initView$2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    MinePostPublishFragment.this.selectAllItem(z);
                }
            });
        }
        MinePostPublishAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.addCheckBoxCallBack(new MinePostPublishFragment$initView$3(this));
        }
        MinePostPublishAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MinePostPublishFragment$initView$4
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    Context mContext;
                    List<Object> data;
                    Object obj = (baseQuickAdapter == null || (data = baseQuickAdapter.getData()) == null) ? null : data.get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PostList");
                    }
                    NewPostDetailViewPagerActivity.Companion companion = NewPostDetailViewPagerActivity.Companion;
                    mContext = MinePostPublishFragment.this.getMContext();
                    companion.startActivity(mContext, ((PostList) obj).getId(), false, false, false);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void selectTextNum() {
        boolean z;
        int i;
        MinePostPublishAdapter adapter = getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        if ((data == null || data.isEmpty()) || data.size() <= 0) {
            z = false;
            i = 0;
        } else {
            z = false;
            i = 0;
            for (PostList it2 : data) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (it2.isSelectMinePostDelete()) {
                    i++;
                    z = true;
                }
            }
        }
        Button button_ok = (Button) _$_findCachedViewById(R$id.button_ok);
        Intrinsics.checkExpressionValueIsNotNull(button_ok, "button_ok");
        button_ok.setEnabled(z);
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = {String.valueOf(i)};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
        Function1<? super Boolean, Unit> function1 = this.checkBoxCallBack;
        if (function1 != null) {
            function1.mo6794invoke(Boolean.valueOf(z));
        }
    }

    public void selectAllItem(boolean z) {
        MinePostPublishAdapter adapter = getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        if ((data == null || data.isEmpty()) || data.size() <= 0) {
            return;
        }
        for (PostList it2 : data) {
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            it2.setSelectMinePostDelete(z);
            it2.setShowSelectPostDelete(true);
        }
        MinePostPublishAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        selectTextNum();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        setUserVisibleHint(true);
    }

    public void onError(String str) {
        List<PostList> data;
        MinePostPublishAdapter adapter;
        MinePostPublishAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        MinePostPublishAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(1, getRefreshLayout());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void requestData() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        MinePostPublishPresenter minePostPublishPresenter = (MinePostPublishPresenter) getMPresenter();
        if (minePostPublishPresenter != null) {
            minePostPublishPresenter.requestData(linkedHashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDeleteDialog(final String str) {
        Context mContext = getMContext();
        if (mContext != null) {
            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
            customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
            customAlertDialog.setMessage(AppUtil.getString(R.string.post_mine_delete_tip));
            customAlertDialog.setBottomHorizontalLineVisible(false);
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            customAlertDialog.setTitleBackgroundDrawable(ContextCompat.getDrawable(mContext2, R.drawable.common_shape_solid_corner12_white));
            customAlertDialog.setConfirmButton(AppUtil.getString(R.string.common_confirm), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MinePostPublishFragment$showDeleteDialog$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MinePostPublishPresenter access$getMPresenter$p = MinePostPublishFragment.access$getMPresenter$p(MinePostPublishFragment.this);
                    if (access$getMPresenter$p != null) {
                        access$getMPresenter$p.requestDeletePushPost(str, DBUtil.getMemberId());
                    }
                    customAlertDialog.dismiss();
                }
            });
            customAlertDialog.setCancelButton(AppUtil.getString(R.string.common_cancel), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.MinePostPublishFragment$showDeleteDialog$2
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

    public void handlerPostList(ArrayList<PostList> arrayList) {
        updateData(arrayList);
        Button button = (Button) _$_findCachedViewById(R$id.button_ok);
        if (button != null) {
            button.setEnabled(false);
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = {"0"};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
    }

    public void deleteOrCancel(int i) {
        Button button = (Button) _$_findCachedViewById(R$id.button_ok);
        if (button != null) {
            button.setEnabled(false);
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
        boolean z = true;
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = {"0"};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
        List<PostList> list = null;
        if (i == 1) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_choose);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            MinePostPublishAdapter adapter = getAdapter();
            if (adapter != null) {
                list = adapter.getData();
            }
            if ((list == null || list.isEmpty()) || list.size() <= 0) {
                return;
            }
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_choose);
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(0);
            }
            Button button2 = (Button) _$_findCachedViewById(R$id.button_ok);
            if (button2 != null) {
                button2.setVisibility(0);
            }
            for (PostList it2 : list) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                it2.setSelectMinePostDelete(false);
                it2.setShowSelectPostDelete(true);
            }
            MinePostPublishAdapter adapter2 = getAdapter();
            if (adapter2 == null) {
                return;
            }
            adapter2.notifyDataSetChanged();
            return;
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_choose);
        if (relativeLayout3 != null) {
            relativeLayout3.setVisibility(8);
        }
        Button button3 = (Button) _$_findCachedViewById(R$id.button_ok);
        if (button3 != null) {
            button3.setVisibility(8);
        }
        MinePostPublishAdapter adapter3 = getAdapter();
        if (adapter3 != null) {
            list = adapter3.getData();
        }
        if (list != null && !list.isEmpty()) {
            z = false;
        }
        if (z || list.size() <= 0) {
            return;
        }
        for (PostList it3 : list) {
            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
            it3.setSelectMinePostDelete(false);
            it3.setShowSelectPostDelete(false);
        }
        MinePostPublishAdapter adapter4 = getAdapter();
        if (adapter4 == null) {
            return;
        }
        adapter4.notifyDataSetChanged();
    }

    @Override // com.one.tomato.mvp.p080ui.mine.impl.IMinePostPublish
    public void handlerRemoveItem() {
        MinePostPublishAdapter adapter = getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        ArrayList arrayList = new ArrayList();
        if (!(data == null || data.isEmpty()) && data.size() > 0) {
            for (PostList it2 : data) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (it2.isSelectMinePostDelete()) {
                    arrayList.add(it2);
                }
            }
            if (arrayList.size() > 0) {
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    PostList postList = (PostList) it3.next();
                    if (data.contains(postList)) {
                        data.remove(postList);
                    }
                }
                MinePostPublishAdapter adapter2 = getAdapter();
                if (adapter2 != null) {
                    adapter2.notifyDataSetChanged();
                }
            }
        }
        ToastUtil.showCenterToast(AppUtil.getString(R.string.post_delete_success));
        Button button = (Button) _$_findCachedViewById(R$id.button_ok);
        if (button != null) {
            button.setEnabled(false);
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = {"0"};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
    }
}
