package com.one.tomato.mvp.p080ui.circle.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Vibrator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.DefaultChannelBean;
import com.one.tomato.entity.p079db.UserChannelBean;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.circle.adapter.MyChannelAdapter;
import com.one.tomato.mvp.p080ui.circle.impl.IChannelChooseContract$IChannelChooseView;
import com.one.tomato.mvp.p080ui.circle.presenter.NewChannelChoosePresenter;
import com.one.tomato.mvp.p080ui.circle.utils.ChannelManger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewChannelChooseActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity */
/* loaded from: classes3.dex */
public final class NewChannelChooseActivity extends MvpBaseActivity<IChannelChooseContract$IChannelChooseView, NewChannelChoosePresenter> implements IChannelChooseContract$IChannelChooseView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private boolean isEdit;
    private ItemTouchHelper itemTouchHelper;
    private MyChannelAdapter<DefaultChannelBean> myChannelAdapter;
    private MyChannelAdapter<DefaultChannelBean> recommentChannelAdapter;
    private int clickItemPos = -1;
    private final NewChannelChooseActivity$callBack$1 callBack = new ItemTouchHelper.Callback() { // from class: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity$callBack$1
        @Override // android.support.p005v7.widget.helper.ItemTouchHelper.Callback
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override // android.support.p005v7.widget.helper.ItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder p0, int i) {
            Intrinsics.checkParameterIsNotNull(p0, "p0");
        }

        @Override // android.support.p005v7.widget.helper.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder p1) {
            Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ItemTouchHelper.Callback.makeMovementFlags(recyclerView.getLayoutManager() instanceof GridLayoutManager ? 15 : 0, 0);
        }

        @Override // android.support.p005v7.widget.helper.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView p0, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            MyChannelAdapter myChannelAdapter;
            MyChannelAdapter myChannelAdapter2;
            MyChannelAdapter myChannelAdapter3;
            MyChannelAdapter myChannelAdapter4;
            Intrinsics.checkParameterIsNotNull(p0, "p0");
            Intrinsics.checkParameterIsNotNull(viewHolder, "viewHolder");
            Intrinsics.checkParameterIsNotNull(target, "target");
            int adapterPosition = viewHolder.getAdapterPosition();
            int adapterPosition2 = target.getAdapterPosition();
            if (adapterPosition >= 0 && 2 >= adapterPosition) {
                return false;
            }
            if (adapterPosition2 >= 0 && 2 >= adapterPosition2) {
                return false;
            }
            if (adapterPosition < adapterPosition2) {
                int i = adapterPosition;
                while (i < adapterPosition2) {
                    myChannelAdapter4 = NewChannelChooseActivity.this.myChannelAdapter;
                    int i2 = i + 1;
                    Collections.swap(myChannelAdapter4 != null ? myChannelAdapter4.getData() : null, i, i2);
                    i = i2;
                }
            } else if (adapterPosition >= adapterPosition2) {
                int i3 = adapterPosition;
                while (true) {
                    if (i3 != adapterPosition2) {
                        myChannelAdapter = NewChannelChooseActivity.this.myChannelAdapter;
                        Collections.swap(myChannelAdapter != null ? myChannelAdapter.getData() : null, i3, i3 - 1);
                    }
                    if (i3 == adapterPosition2) {
                        break;
                    }
                    i3--;
                }
            }
            myChannelAdapter2 = NewChannelChooseActivity.this.myChannelAdapter;
            if (myChannelAdapter2 != null) {
                myChannelAdapter2.getData();
            }
            myChannelAdapter3 = NewChannelChooseActivity.this.myChannelAdapter;
            if (myChannelAdapter3 == null) {
                return true;
            }
            myChannelAdapter3.notifyItemMoved(adapterPosition, adapterPosition2);
            return true;
        }

        @Override // android.support.p005v7.widget.helper.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (i != 0) {
                Object systemService = NewChannelChooseActivity.this.getSystemService("vibrator");
                if (systemService == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.os.Vibrator");
                }
                ((Vibrator) systemService).vibrate(50L);
            }
            super.onSelectedChanged(viewHolder, i);
        }
    };
    private final Functions<Unit> callBackToHome = new NewChannelChooseActivity$callBackToHome$1(this);

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.new_channel_choose_activity;
    }

    /* compiled from: NewChannelChooseActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Activity context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivityForResult(new Intent(context, NewChannelChooseActivity.class), 20);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public NewChannelChoosePresenter mo6439createPresenter() {
        return new NewChannelChoosePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(getString(R.string.circle_channel_choose_title));
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(0);
        }
        TextView rightTV2 = getRightTV();
        if (rightTV2 != null) {
            rightTV2.setText(getString(R.string.circle_channel_edit));
        }
        TextView rightTV3 = getRightTV();
        if (rightTV3 != null) {
            rightTV3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TextView rightTV4;
                    TextView rightTV5;
                    MyChannelAdapter myChannelAdapter;
                    TextView rightTV6;
                    MyChannelAdapter myChannelAdapter2;
                    CharSequence text;
                    rightTV4 = NewChannelChooseActivity.this.getRightTV();
                    if (Intrinsics.areEqual((rightTV4 == null || (text = rightTV4.getText()) == null) ? null : text.toString(), NewChannelChooseActivity.this.getString(R.string.circle_channel_edit))) {
                        rightTV6 = NewChannelChooseActivity.this.getRightTV();
                        if (rightTV6 != null) {
                            rightTV6.setText(NewChannelChooseActivity.this.getString(R.string.circle_channel_finsh));
                        }
                        myChannelAdapter2 = NewChannelChooseActivity.this.myChannelAdapter;
                        if (myChannelAdapter2 != null) {
                            myChannelAdapter2.setIsEidt(true);
                        }
                        NewChannelChooseActivity.this.isEdit = true;
                        return;
                    }
                    rightTV5 = NewChannelChooseActivity.this.getRightTV();
                    if (rightTV5 != null) {
                        rightTV5.setText(NewChannelChooseActivity.this.getString(R.string.circle_channel_edit));
                    }
                    myChannelAdapter = NewChannelChooseActivity.this.myChannelAdapter;
                    if (myChannelAdapter != null) {
                        myChannelAdapter.setIsEidt(false);
                    }
                    NewChannelChooseActivity.this.isEdit = false;
                }
            });
        }
        ImageView backImg = getBackImg();
        if (backImg != null) {
            backImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Functions functions;
                    functions = NewChannelChooseActivity.this.callBackToHome;
                    functions.mo6822invoke();
                }
            });
        }
        this.myChannelAdapter = new MyChannelAdapter<>(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.my_channel_recycler), 1);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.my_channel_recycler);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.my_channel_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.myChannelAdapter);
        }
        MyChannelAdapter<DefaultChannelBean> myChannelAdapter = this.myChannelAdapter;
        if (myChannelAdapter != null) {
            myChannelAdapter.setEnableLoadMore(false);
        }
        this.recommentChannelAdapter = new MyChannelAdapter<>(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.my_channel_recycler), 2);
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recomment_channel_recycler);
        if (recyclerView3 != null) {
            recyclerView3.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.recomment_channel_recycler);
        if (recyclerView4 != null) {
            recyclerView4.setAdapter(this.recommentChannelAdapter);
        }
        MyChannelAdapter<DefaultChannelBean> myChannelAdapter2 = this.recommentChannelAdapter;
        if (myChannelAdapter2 != null) {
            myChannelAdapter2.setEnableLoadMore(false);
        }
        MyChannelAdapter<DefaultChannelBean> myChannelAdapter3 = this.myChannelAdapter;
        if (myChannelAdapter3 != null) {
            myChannelAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity$initView$3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    boolean z;
                    MyChannelAdapter myChannelAdapter4;
                    MyChannelAdapter myChannelAdapter5;
                    MyChannelAdapter myChannelAdapter6;
                    z = NewChannelChooseActivity.this.isEdit;
                    if (!z) {
                        NewChannelChooseActivity.this.clickItemPos = i;
                        NewChannelChooseActivity.this.onBackPressed();
                        return;
                    }
                    List<DefaultChannelBean> list = null;
                    Object item = baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null;
                    if (!(item instanceof DefaultChannelBean)) {
                        return;
                    }
                    DefaultChannelBean defaultChannelBean = (DefaultChannelBean) item;
                    int channelId = defaultChannelBean.getChannelId();
                    if (1 <= channelId && 3 >= channelId) {
                        return;
                    }
                    myChannelAdapter4 = NewChannelChooseActivity.this.myChannelAdapter;
                    if (myChannelAdapter4 != null) {
                        myChannelAdapter4.remove(i);
                    }
                    myChannelAdapter5 = NewChannelChooseActivity.this.recommentChannelAdapter;
                    if (myChannelAdapter5 != null) {
                        list = myChannelAdapter5.getData();
                    }
                    if (list != null) {
                        for (DefaultChannelBean it2 : list) {
                            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                            if (it2.getChannelId() == defaultChannelBean.getChannelId()) {
                                return;
                            }
                        }
                    }
                    myChannelAdapter6 = NewChannelChooseActivity.this.recommentChannelAdapter;
                    if (myChannelAdapter6 == null) {
                        return;
                    }
                    myChannelAdapter6.addData((MyChannelAdapter) item);
                }
            });
        }
        MyChannelAdapter<DefaultChannelBean> myChannelAdapter4 = this.recommentChannelAdapter;
        if (myChannelAdapter4 != null) {
            myChannelAdapter4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.NewChannelChooseActivity$initView$4
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    MyChannelAdapter myChannelAdapter5;
                    MyChannelAdapter myChannelAdapter6;
                    MyChannelAdapter myChannelAdapter7;
                    MyChannelAdapter myChannelAdapter8;
                    myChannelAdapter5 = NewChannelChooseActivity.this.recommentChannelAdapter;
                    List<DefaultChannelBean> list = null;
                    DefaultChannelBean defaultChannelBean = myChannelAdapter5 != null ? (DefaultChannelBean) myChannelAdapter5.getItem(i) : null;
                    if (defaultChannelBean instanceof DefaultChannelBean) {
                        myChannelAdapter6 = NewChannelChooseActivity.this.recommentChannelAdapter;
                        if (myChannelAdapter6 != null) {
                            myChannelAdapter6.remove(i);
                        }
                        myChannelAdapter7 = NewChannelChooseActivity.this.myChannelAdapter;
                        if (myChannelAdapter7 != null) {
                            list = myChannelAdapter7.getData();
                        }
                        if (list != null) {
                            for (DefaultChannelBean it2 : list) {
                                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                                if (it2.getChannelId() == defaultChannelBean.getChannelId()) {
                                    return;
                                }
                            }
                        }
                        myChannelAdapter8 = NewChannelChooseActivity.this.myChannelAdapter;
                        if (myChannelAdapter8 == null) {
                            return;
                        }
                        myChannelAdapter8.addData((MyChannelAdapter) defaultChannelBean);
                    }
                }
            });
        }
        touchItem();
    }

    private final void touchItem() {
        this.itemTouchHelper = new ItemTouchHelper(this.callBack);
        ItemTouchHelper itemTouchHelper = this.itemTouchHelper;
        if (itemTouchHelper != null) {
            itemTouchHelper.attachToRecyclerView((RecyclerView) _$_findCachedViewById(R$id.my_channel_recycler));
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        this.callBackToHome.mo6822invoke();
        super.onBackPressed();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        MyChannelAdapter<DefaultChannelBean> myChannelAdapter;
        MyChannelAdapter<DefaultChannelBean> myChannelAdapter2;
        ArrayList<DefaultChannelBean> userChannel = ChannelManger.INSTANCE.getUserChannel(UserChannelBean.TYPE_DEFAULT);
        ArrayList<DefaultChannelBean> userChannel2 = ChannelManger.INSTANCE.getUserChannel(UserChannelBean.TYPE_NO_DEFAULT);
        boolean z = false;
        if (!(userChannel == null || userChannel.isEmpty()) && (myChannelAdapter2 = this.myChannelAdapter) != null) {
            myChannelAdapter2.addData(userChannel);
        }
        if (userChannel2 == null || userChannel2.isEmpty()) {
            z = true;
        }
        if (z || (myChannelAdapter = this.recommentChannelAdapter) == null) {
            return;
        }
        myChannelAdapter.addData(userChannel2);
    }
}
