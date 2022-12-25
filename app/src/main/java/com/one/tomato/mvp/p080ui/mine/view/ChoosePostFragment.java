package com.one.tomato.mvp.p080ui.mine.view;

import android.os.Bundle;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.mine.adapter.MinePostPublishAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.EmptyViewLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChoosePostFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostFragment */
/* loaded from: classes3.dex */
public final class ChoosePostFragment extends MinePostPublishFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int selectType = 1;

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment
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

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: ChoosePostFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ChoosePostFragment getInstance(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            ChoosePostFragment choosePostFragment = new ChoosePostFragment();
            choosePostFragment.setArguments(bundle);
            return choosePostFragment;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        TextView textView;
        ArrayList<PostList> mo6822invoke;
        super.inintData();
        Bundle arguments = getArguments();
        this.selectType = arguments != null ? arguments.getInt("type") : 0;
        if (this.selectType != 2 || (textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num)) == null) {
            return;
        }
        String string = AppUtil.getString(R.string.post_choose_num);
        Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
        Object[] objArr = new Object[1];
        StringBuilder sb = new StringBuilder();
        Functions<ArrayList<PostList>> pullSelectData = getPullSelectData();
        sb.append((pullSelectData == null || (mo6822invoke = pullSelectData.mo6822invoke()) == null) ? 0 : mo6822invoke.size());
        sb.append("/30");
        objArr[0] = sb.toString();
        String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
        textView.setText(format);
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        super.initView();
        MinePostPublishAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.addCheckBoxCallBack(new ChoosePostFragment$initView$1(this));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    protected void initAdapter() {
        EmptyViewLayout emptyViewLayout;
        setAdapter(mo6434createAdapter());
        MinePostPublishAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setPreLoadNumber(getPreLoadNumber());
        }
        MinePostPublishAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.setOnLoadMoreListener(null, getRecyclerView());
        }
        MinePostPublishAdapter adapter3 = getAdapter();
        if (adapter3 != null && (emptyViewLayout = adapter3.getEmptyViewLayout()) != null) {
            emptyViewLayout.setButtonClickListener(getEmptyOnClick());
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.setAdapter(getAdapter());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0023, code lost:
        if ((r3 == null || r3.isEmpty()) != false) goto L15;
     */
    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void selectAllItem(boolean z) {
        ArrayList<PostList> mo6822invoke;
        ArrayList<PostList> mo6822invoke2;
        if (!z) {
            if (this.selectType != 1) {
                Functions<ArrayList<PostList>> pullSelectData = getPullSelectData();
                ArrayList<PostList> mo6822invoke3 = pullSelectData != null ? pullSelectData.mo6822invoke() : null;
            }
            setPostType(0);
        }
        MinePostPublishAdapter adapter = getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        if ((data == null || data.isEmpty()) || data.size() <= 0) {
            return;
        }
        int i = 0;
        for (PostList it2 : data) {
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            it2.setSelectMinePostDelete(z);
            it2.setShowSelectPostDelete(true);
            int i2 = 30;
            if (this.selectType == 2) {
                Functions<ArrayList<PostList>> pullSelectData2 = getPullSelectData();
                ArrayList<PostList> mo6822invoke4 = pullSelectData2 != null ? pullSelectData2.mo6822invoke() : null;
                if (mo6822invoke4 != null && mo6822invoke4.size() > 0) {
                    Iterator<PostList> it3 = mo6822invoke4.iterator();
                    while (it3.hasNext()) {
                        PostList select = it3.next();
                        if (getPostType() == 0) {
                            Intrinsics.checkExpressionValueIsNotNull(select, "select");
                            setPostType(select.getPostType());
                        }
                        if (getPostType() != it2.getPostType()) {
                            it2.setShowSelectPostDelete(false);
                            it2.setSelectMinePostDelete(false);
                        }
                        int id = it2.getId();
                        Intrinsics.checkExpressionValueIsNotNull(select, "select");
                        if (id == select.getId()) {
                            it2.setShowSelectPostDelete(false);
                            it2.setSelectMinePostDelete(false);
                        }
                    }
                    if (it2.isSelectMinePostDelete()) {
                        Functions<ArrayList<PostList>> pullSelectData3 = getPullSelectData();
                        if (i >= 30 - ((pullSelectData3 == null || (mo6822invoke2 = pullSelectData3.mo6822invoke()) == null) ? 0 : mo6822invoke2.size())) {
                            it2.setSelectMinePostDelete(false);
                            it2.setSelectMinePostDelete(false);
                        }
                    }
                    i++;
                }
            }
            if (z) {
                if (getPostType() == 0) {
                    setPostType(it2.getPostType());
                }
                if (getPostType() != it2.getPostType()) {
                    it2.setShowSelectPostDelete(false);
                    it2.setSelectMinePostDelete(false);
                }
                if (it2.isSelectMinePostDelete()) {
                    if (this.selectType == 2) {
                        Functions<ArrayList<PostList>> pullSelectData4 = getPullSelectData();
                        i2 = 30 - ((pullSelectData4 == null || (mo6822invoke = pullSelectData4.mo6822invoke()) == null) ? 0 : mo6822invoke.size());
                    }
                    if (i >= i2) {
                        it2.setSelectMinePostDelete(false);
                        it2.setSelectMinePostDelete(false);
                    }
                }
                i++;
            }
        }
        MinePostPublishAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        selectTextNum();
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment, com.one.tomato.mvp.p080ui.mine.impl.IMinePostPublish
    public void handlerPostList(ArrayList<PostList> arrayList) {
        MinePostPublishAdapter adapter;
        ArrayList<PostList> mo6822invoke;
        hideWaitingDialog();
        ArrayList<PostList> arrayList2 = new ArrayList<>();
        int i = this.selectType;
        if (i == 1 || i == 2) {
            if (!(arrayList == null || arrayList.isEmpty())) {
                Iterator<PostList> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    PostList it3 = it2.next();
                    Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                    if (it3.getSerialGroupId() == 0) {
                        arrayList2.add(it3);
                    }
                }
                filterData(arrayList2);
                if (arrayList.size() != 0) {
                    if (getState() == getGET_LIST_REFRESH()) {
                        Button button = (Button) _$_findCachedViewById(R$id.button_ok);
                        if (button != null) {
                            button.setEnabled(false);
                        }
                        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
                        if (checkBox != null) {
                            checkBox.setChecked(false);
                        }
                        if (this.selectType == 2) {
                            TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
                            if (textView != null) {
                                String string = AppUtil.getString(R.string.post_choose_num);
                                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
                                Object[] objArr = new Object[1];
                                StringBuilder sb = new StringBuilder();
                                Functions<ArrayList<PostList>> pullSelectData = getPullSelectData();
                                sb.append((pullSelectData == null || (mo6822invoke = pullSelectData.mo6822invoke()) == null) ? 0 : mo6822invoke.size());
                                sb.append("/30");
                                objArr[0] = sb.toString();
                                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                                textView.setText(format);
                            }
                        } else {
                            TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
                            if (textView2 != null) {
                                String string2 = AppUtil.getString(R.string.post_choose_num);
                                Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.post_choose_num)");
                                Object[] objArr2 = {"0/30"};
                                String format2 = String.format(string2, Arrays.copyOf(objArr2, objArr2.length));
                                Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(this, *args)");
                                textView2.setText(format2);
                            }
                        }
                        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_choose);
                        if (relativeLayout != null) {
                            relativeLayout.setVisibility(0);
                        }
                        Button button2 = (Button) _$_findCachedViewById(R$id.button_ok);
                        if (button2 != null) {
                            button2.setVisibility(8);
                        }
                    }
                    if (arrayList2.size() > 0 && (adapter = getAdapter()) != null) {
                        adapter.addData((Collection) arrayList2);
                    }
                    setPageNo(getPageNo() + 1);
                    loadMore();
                    return;
                }
                updateData(arrayList2);
            } else {
                updateData(arrayList);
            }
        } else {
            updateData(arrayList);
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.setEnableRefresh(false);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment
    protected void requestData() {
        showWaitingDialog();
        super.requestData();
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    protected void onLazyLoad() {
        refresh();
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment, com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        super.onError(str);
        hideWaitingDialog();
    }

    private final ArrayList<PostList> filterData(ArrayList<PostList> arrayList) {
        if (!(arrayList == null || arrayList.isEmpty())) {
            if (getState() == getGET_LIST_REFRESH()) {
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
                    Object[] objArr = {"0/30"};
                    String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                    textView.setText(format);
                }
                RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_choose);
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(0);
                }
                Button button2 = (Button) _$_findCachedViewById(R$id.button_ok);
                if (button2 != null) {
                    button2.setVisibility(8);
                }
            }
            Iterator<PostList> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                PostList it3 = it2.next();
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                it3.setSelectMinePostDelete(false);
                it3.setShowSelectPostDelete(true);
                if (this.selectType == 2) {
                    Functions<ArrayList<PostList>> pullSelectData = getPullSelectData();
                    ArrayList<PostList> mo6822invoke = pullSelectData != null ? pullSelectData.mo6822invoke() : null;
                    if (mo6822invoke != null && mo6822invoke.size() > 0) {
                        Iterator<PostList> it4 = mo6822invoke.iterator();
                        while (it4.hasNext()) {
                            PostList select = it4.next();
                            if (getPostType() == 0) {
                                Intrinsics.checkExpressionValueIsNotNull(select, "select");
                                setPostType(select.getPostType());
                            }
                            int id = it3.getId();
                            Intrinsics.checkExpressionValueIsNotNull(select, "select");
                            if (id == select.getId()) {
                                it3.setShowSelectPostDelete(false);
                            }
                        }
                        if (this.selectType == 2 && getPostType() != it3.getPostType()) {
                            it3.setShowSelectPostDelete(false);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0024, code lost:
        if ((r0 == null || r0.isEmpty()) != false) goto L54;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void selectSamePostType(PostList postList) {
        boolean z;
        ArrayList<PostList> mo6822invoke;
        if (this.selectType != 1) {
            Functions<ArrayList<PostList>> pullSelectData = getPullSelectData();
            ArrayList<PostList> mo6822invoke2 = pullSelectData != null ? pullSelectData.mo6822invoke() : null;
        }
        MinePostPublishAdapter adapter = getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        if (postList.isSelectMinePostDelete()) {
            setPostType(postList.getPostType());
        } else if (data != null) {
            Iterator<PostList> it2 = data.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                PostList it3 = it2.next();
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                if (it3.isSelectMinePostDelete()) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                for (PostList it4 : data) {
                    Intrinsics.checkExpressionValueIsNotNull(it4, "it");
                    it4.setShowSelectPostDelete(true);
                }
                setPostType(0);
            }
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
        if (postList.isSelectMinePostDelete()) {
            MinePostPublishAdapter adapter2 = getAdapter();
            List<PostList> data2 = adapter2 != null ? adapter2.getData() : null;
            if (data2 != null) {
                int i = 0;
                for (PostList it5 : data2) {
                    Intrinsics.checkExpressionValueIsNotNull(it5, "it");
                    it5.setShowSelectPostDelete(it5.getPostType() == postList.getPostType());
                    if (it5.isSelectMinePostDelete()) {
                        i++;
                    }
                }
                int i2 = 30;
                if (this.selectType == 2) {
                    Functions<ArrayList<PostList>> pullSelectData2 = getPullSelectData();
                    i2 = 30 - ((pullSelectData2 == null || (mo6822invoke = pullSelectData2.mo6822invoke()) == null) ? 0 : mo6822invoke.size());
                }
                if (i > i2) {
                    postList.setSelectMinePostDelete(false);
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.post_serialize_most_num));
                }
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        MinePostPublishAdapter adapter3 = getAdapter();
        if (adapter3 != null) {
            adapter3.notifyDataSetChanged();
        }
        selectTextNum();
    }

    @Override // com.one.tomato.mvp.p080ui.mine.view.MinePostPublishFragment
    protected void selectTextNum() {
        boolean z;
        int i;
        TextView textView;
        ArrayList<PostList> mo6822invoke;
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
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
        if (textView2 != null) {
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = {i + "/30"};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView2.setText(format);
        }
        if (this.selectType == 2 && (textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num)) != null) {
            String string2 = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr2 = new Object[1];
            StringBuilder sb = new StringBuilder();
            Functions<ArrayList<PostList>> pullSelectData = getPullSelectData();
            sb.append(i + ((pullSelectData == null || (mo6822invoke = pullSelectData.mo6822invoke()) == null) ? 0 : mo6822invoke.size()));
            sb.append("/30");
            objArr2[0] = sb.toString();
            String format2 = String.format(string2, Arrays.copyOf(objArr2, objArr2.length));
            Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(this, *args)");
            textView.setText(format2);
        }
        Function1<Boolean, Unit> checkBoxCallBack = getCheckBoxCallBack();
        if (checkBoxCallBack != null) {
            checkBoxCallBack.mo6794invoke(Boolean.valueOf(z));
        }
    }

    public final ArrayList<PostList> getSelectPost() {
        ArrayList<PostList> arrayList = new ArrayList<>();
        MinePostPublishAdapter adapter = getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        if (!(data == null || data.isEmpty()) && data.size() > 0) {
            for (PostList it2 : data) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                if (it2.isSelectMinePostDelete()) {
                    arrayList.add(it2);
                }
            }
        }
        return arrayList;
    }
}
