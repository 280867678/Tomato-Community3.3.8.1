package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.support.p005v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CreateSerializeDialog;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.SerializePostListBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.mine.adapter.MinePostPublishAdapter;
import com.one.tomato.mvp.p080ui.mine.view.ChoosePostActivity;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: SingleSerialPostListActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity */
/* loaded from: classes3.dex */
public final class SingleSerialPostListActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private MinePostPublishAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private int serializeId;
    private boolean isMySelf = true;
    private final SingleSerialPostListActivity$callBack$1 callBack = new ItemTouchHelper.Callback() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$callBack$1
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
            return ItemTouchHelper.Callback.makeMovementFlags(recyclerView.getLayoutManager() instanceof LinearLayoutManager ? 3 : 0, 0);
        }

        @Override // android.support.p005v7.widget.helper.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView p0, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            MinePostPublishAdapter minePostPublishAdapter;
            MinePostPublishAdapter minePostPublishAdapter2;
            MinePostPublishAdapter minePostPublishAdapter3;
            int i;
            MinePostPublishAdapter minePostPublishAdapter4;
            MinePostPublishAdapter minePostPublishAdapter5;
            MinePostPublishAdapter minePostPublishAdapter6;
            MinePostPublishAdapter minePostPublishAdapter7;
            int i2;
            MinePostPublishAdapter minePostPublishAdapter8;
            MinePostPublishAdapter minePostPublishAdapter9;
            MinePostPublishAdapter minePostPublishAdapter10;
            MinePostPublishAdapter minePostPublishAdapter11;
            MinePostPublishAdapter minePostPublishAdapter12;
            MinePostPublishAdapter minePostPublishAdapter13;
            int i3;
            MinePostPublishAdapter minePostPublishAdapter14;
            int i4;
            Intrinsics.checkParameterIsNotNull(p0, "p0");
            Intrinsics.checkParameterIsNotNull(viewHolder, "viewHolder");
            Intrinsics.checkParameterIsNotNull(target, "target");
            int adapterPosition = viewHolder.getAdapterPosition();
            int adapterPosition2 = target.getAdapterPosition();
            minePostPublishAdapter = SingleSerialPostListActivity.this.adapter;
            PostList item = minePostPublishAdapter != null ? minePostPublishAdapter.getItem(adapterPosition) : null;
            if (adapterPosition2 == 0) {
                minePostPublishAdapter14 = SingleSerialPostListActivity.this.adapter;
                PostList item2 = minePostPublishAdapter14 != null ? minePostPublishAdapter14.getItem(adapterPosition2) : null;
                if (item != null) {
                    if (item2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    item.setSortNum(item2.getSortNum() + 100);
                }
                if (item != null) {
                    SingleSerialPostListActivity singleSerialPostListActivity = SingleSerialPostListActivity.this;
                    String valueOf = String.valueOf(item.getId());
                    i4 = SingleSerialPostListActivity.this.serializeId;
                    singleSerialPostListActivity.movePost(valueOf, i4, item.getSortNum());
                }
            } else {
                minePostPublishAdapter2 = SingleSerialPostListActivity.this.adapter;
                if (minePostPublishAdapter2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (adapterPosition2 == minePostPublishAdapter2.getData().size() - 1) {
                    minePostPublishAdapter3 = SingleSerialPostListActivity.this.adapter;
                    PostList item3 = minePostPublishAdapter3 != null ? minePostPublishAdapter3.getItem(adapterPosition2) : null;
                    if (item != null) {
                        if (item3 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        item.setSortNum(item3.getSortNum() / 2);
                    }
                    if (item != null) {
                        SingleSerialPostListActivity singleSerialPostListActivity2 = SingleSerialPostListActivity.this;
                        String valueOf2 = String.valueOf(item.getId());
                        i = SingleSerialPostListActivity.this.serializeId;
                        singleSerialPostListActivity2.movePost(valueOf2, i, item.getSortNum());
                    }
                }
            }
            long j = 0;
            if (adapterPosition < adapterPosition2) {
                if (adapterPosition2 != 0) {
                    minePostPublishAdapter11 = SingleSerialPostListActivity.this.adapter;
                    if (minePostPublishAdapter11 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else if (adapterPosition2 != minePostPublishAdapter11.getData().size() - 1) {
                        minePostPublishAdapter12 = SingleSerialPostListActivity.this.adapter;
                        PostList item4 = minePostPublishAdapter12 != null ? minePostPublishAdapter12.getItem(adapterPosition2) : null;
                        long sortNum = item4 != null ? item4.getSortNum() : 0L;
                        minePostPublishAdapter13 = SingleSerialPostListActivity.this.adapter;
                        PostList item5 = minePostPublishAdapter13 != null ? minePostPublishAdapter13.getItem(adapterPosition2 + 1) : null;
                        if (item5 != null) {
                            j = item5.getSortNum();
                        }
                        if (item != null) {
                            item.setSortNum((sortNum + j) / 2);
                        }
                        if (item != null) {
                            SingleSerialPostListActivity singleSerialPostListActivity3 = SingleSerialPostListActivity.this;
                            String valueOf3 = String.valueOf(item.getId());
                            i3 = SingleSerialPostListActivity.this.serializeId;
                            singleSerialPostListActivity3.movePost(valueOf3, i3, item.getSortNum());
                        }
                    }
                }
                int i5 = adapterPosition;
                while (i5 < adapterPosition2) {
                    minePostPublishAdapter10 = SingleSerialPostListActivity.this.adapter;
                    int i6 = i5 + 1;
                    Collections.swap(minePostPublishAdapter10 != null ? minePostPublishAdapter10.getData() : null, i5, i6);
                    i5 = i6;
                }
            } else {
                if (adapterPosition2 != 0) {
                    minePostPublishAdapter5 = SingleSerialPostListActivity.this.adapter;
                    if (minePostPublishAdapter5 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else if (adapterPosition2 != minePostPublishAdapter5.getData().size() - 1) {
                        minePostPublishAdapter6 = SingleSerialPostListActivity.this.adapter;
                        PostList item6 = minePostPublishAdapter6 != null ? minePostPublishAdapter6.getItem(adapterPosition2 - 1) : null;
                        long sortNum2 = item6 != null ? item6.getSortNum() : 0L;
                        minePostPublishAdapter7 = SingleSerialPostListActivity.this.adapter;
                        PostList item7 = minePostPublishAdapter7 != null ? minePostPublishAdapter7.getItem(adapterPosition2) : null;
                        if (item7 != null) {
                            j = item7.getSortNum();
                        }
                        if (item != null) {
                            item.setSortNum((sortNum2 + j) / 2);
                        }
                        if (item != null) {
                            SingleSerialPostListActivity singleSerialPostListActivity4 = SingleSerialPostListActivity.this;
                            String valueOf4 = String.valueOf(item.getId());
                            i2 = SingleSerialPostListActivity.this.serializeId;
                            singleSerialPostListActivity4.movePost(valueOf4, i2, item.getSortNum());
                        }
                    }
                }
                if (adapterPosition >= adapterPosition2) {
                    int i7 = adapterPosition;
                    while (true) {
                        if (i7 != adapterPosition2) {
                            minePostPublishAdapter4 = SingleSerialPostListActivity.this.adapter;
                            Collections.swap(minePostPublishAdapter4 != null ? minePostPublishAdapter4.getData() : null, i7, i7 - 1);
                        }
                        if (i7 == adapterPosition2) {
                            break;
                        }
                        i7--;
                    }
                }
            }
            minePostPublishAdapter8 = SingleSerialPostListActivity.this.adapter;
            if (minePostPublishAdapter8 != null) {
                minePostPublishAdapter8.getData();
            }
            minePostPublishAdapter9 = SingleSerialPostListActivity.this.adapter;
            if (minePostPublishAdapter9 != null) {
                minePostPublishAdapter9.notifyItemMoved(adapterPosition, adapterPosition2);
            }
            return true;
        }

        @Override // android.support.p005v7.widget.helper.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (i != 0) {
                Object systemService = SingleSerialPostListActivity.this.getSystemService("vibrator");
                if (systemService == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.os.Vibrator");
                }
                ((Vibrator) systemService).vibrate(50L);
            }
            super.onSelectedChanged(viewHolder, i);
        }
    };

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
        return R.layout.activity_singel_serial_post_list;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: SingleSerialPostListActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, int i, String title, boolean z) {
            Intrinsics.checkParameterIsNotNull(title, "title");
            Intent intent = new Intent(context, SingleSerialPostListActivity.class);
            intent.putExtra(DatabaseFieldConfigLoader.FIELD_NAME_ID, i);
            intent.putExtra("title", title);
            intent.putExtra("isMySelf", z);
            if (context != null) {
                context.startActivity(intent);
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        initRefreshLayout();
        initRecyclerView();
        initAdapter();
        initClick();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        String str;
        Intent intent = getIntent();
        this.serializeId = intent != null ? intent.getIntExtra(DatabaseFieldConfigLoader.FIELD_NAME_ID, 0) : 0;
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            Intent intent2 = getIntent();
            if (intent2 == null || (str = intent2.getStringExtra("title")) == null) {
                str = "";
            }
            titleTV.setText(str);
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
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
        Intent intent3 = getIntent();
        this.isMySelf = intent3 != null ? intent3.getBooleanExtra("isMySelf", true) : true;
        if (this.isMySelf) {
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_menu);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            touchItem();
            MinePostPublishAdapter minePostPublishAdapter = this.adapter;
            if (minePostPublishAdapter != null) {
                minePostPublishAdapter.setIsShowMove(true);
            }
        } else {
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_menu);
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(8);
            }
            MinePostPublishAdapter minePostPublishAdapter2 = this.adapter;
            if (minePostPublishAdapter2 != null) {
                minePostPublishAdapter2.setIsShowMove(false);
            }
        }
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout != null) {
            smartRefreshLayout.autoRefresh();
        }
    }

    private final void initClick() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_delete);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RelativeLayout relativeLayout = (RelativeLayout) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.relate_choose);
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(0);
                    }
                    LinearLayout linearLayout = (LinearLayout) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.liner_delete);
                    if (linearLayout != null) {
                        linearLayout.setVisibility(0);
                    }
                    SingleSerialPostListActivity.this.isShowCheck(true);
                }
            });
        }
        ImageView imageView2 = (ImageView) _$_findCachedViewById(R$id.image_add);
        if (imageView2 != null) {
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MinePostPublishAdapter minePostPublishAdapter;
                    SingleSerialPostListActivity.this.isShowCheck(false);
                    RelativeLayout relativeLayout = (RelativeLayout) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.relate_choose);
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(8);
                    }
                    LinearLayout linearLayout = (LinearLayout) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.liner_delete);
                    if (linearLayout != null) {
                        linearLayout.setVisibility(8);
                    }
                    ChoosePostActivity.Companion companion = ChoosePostActivity.Companion;
                    SingleSerialPostListActivity singleSerialPostListActivity = SingleSerialPostListActivity.this;
                    minePostPublishAdapter = singleSerialPostListActivity.adapter;
                    ArrayList<PostList> arrayList = (ArrayList) (minePostPublishAdapter != null ? minePostPublishAdapter.getData() : null);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    companion.startActivity(singleSerialPostListActivity, 2, arrayList);
                }
            });
        }
        ImageView imageView3 = (ImageView) _$_findCachedViewById(R$id.image_edit);
        if (imageView3 != null) {
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$3

                /* compiled from: SingleSerialPostListActivity.kt */
                /* renamed from: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$3$1 */
                /* loaded from: classes3.dex */
                static final class C25751 extends Lambda implements Function1<String, Unit> {
                    C25751() {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
                        invoke2(str);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke  reason: avoid collision after fix types in other method */
                    public final void invoke2(String str) {
                        TextView titleTV;
                        titleTV = SingleSerialPostListActivity.this.getTitleTV();
                        if (titleTV != null) {
                            if (str == null) {
                                str = "";
                            }
                            titleTV.setText(str);
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    int i;
                    mContext = SingleSerialPostListActivity.this.getMContext();
                    if (mContext != null) {
                        CreateSerializeDialog createSerializeDialog = new CreateSerializeDialog(mContext, 1);
                        createSerializeDialog.setTitle(AppUtil.getString(R.string.post_serialize_rename));
                        i = SingleSerialPostListActivity.this.serializeId;
                        createSerializeDialog.setSerialize(i);
                        createSerializeDialog.addCreateSerialCallBack(new C25751());
                        createSerializeDialog.show();
                        return;
                    }
                    Intrinsics.throwNpe();
                    throw null;
                }
            });
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_cancel);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RelativeLayout relativeLayout = (RelativeLayout) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.relate_choose);
                    if (relativeLayout != null) {
                        relativeLayout.setVisibility(8);
                    }
                    LinearLayout linearLayout = (LinearLayout) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.liner_delete);
                    if (linearLayout != null) {
                        linearLayout.setVisibility(8);
                    }
                    SingleSerialPostListActivity.this.isShowCheck(false);
                }
            });
        }
        Button button2 = (Button) _$_findCachedViewById(R$id.button_delete);
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MinePostPublishAdapter minePostPublishAdapter;
                    minePostPublishAdapter = SingleSerialPostListActivity.this.adapter;
                    List<PostList> data = minePostPublishAdapter != null ? minePostPublishAdapter.getData() : null;
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
                    SingleSerialPostListActivity singleSerialPostListActivity = SingleSerialPostListActivity.this;
                    String sb2 = sb.toString();
                    Intrinsics.checkExpressionValueIsNotNull(sb2, "ids.toString()");
                    singleSerialPostListActivity.showDeleteDialog(sb2);
                }
            });
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$6
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SingleSerialPostListActivity.this.selectAllItem(z);
                }
            });
        }
        MinePostPublishAdapter minePostPublishAdapter = this.adapter;
        if (minePostPublishAdapter != null) {
            minePostPublishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initClick$7
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    Context mContext;
                    List<Object> data;
                    Object obj = (baseQuickAdapter == null || (data = baseQuickAdapter.getData()) == null) ? null : data.get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PostList");
                    }
                    NewPostDetailViewPagerActivity.Companion companion = NewPostDetailViewPagerActivity.Companion;
                    mContext = SingleSerialPostListActivity.this.getMContext();
                    companion.startActivity(mContext, ((PostList) obj).getId(), false, false, false);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showDeleteDialog(final String str) {
        Context mContext = getMContext();
        if (mContext != null) {
            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
            customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
            customAlertDialog.setMessage(AppUtil.getString(R.string.post_serialize_remove_tip));
            customAlertDialog.setBottomHorizontalLineVisible(false);
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            customAlertDialog.setTitleBackgroundDrawable(ContextCompat.getDrawable(mContext2, R.drawable.common_shape_solid_corner12_white));
            customAlertDialog.setConfirmButton(AppUtil.getString(R.string.common_confirm), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$showDeleteDialog$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SingleSerialPostListActivity.this.removeSerializePost(str);
                    customAlertDialog.dismiss();
                }
            });
            customAlertDialog.setCancelButton(AppUtil.getString(R.string.common_cancel), new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$showDeleteDialog$2
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
    public final void selectAllItem(boolean z) {
        List<PostList> data;
        MinePostPublishAdapter minePostPublishAdapter = this.adapter;
        List<PostList> data2 = minePostPublishAdapter != null ? minePostPublishAdapter.getData() : null;
        if (!(data2 == null || data2.isEmpty()) && data2.size() > 0) {
            for (PostList it2 : data2) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                it2.setSelectMinePostDelete(z);
            }
            MinePostPublishAdapter minePostPublishAdapter2 = this.adapter;
            if (minePostPublishAdapter2 != null) {
                minePostPublishAdapter2.notifyDataSetChanged();
            }
            Button button = (Button) _$_findCachedViewById(R$id.button_delete);
            if (button != null) {
                button.setEnabled(z);
            }
        }
        if (z) {
            TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
            if (textView == null) {
                return;
            }
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = new Object[1];
            MinePostPublishAdapter minePostPublishAdapter3 = this.adapter;
            objArr[0] = String.valueOf((minePostPublishAdapter3 == null || (data = minePostPublishAdapter3.getData()) == null) ? 0 : data.size());
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
            return;
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
        if (textView2 == null) {
            return;
        }
        String string2 = AppUtil.getString(R.string.post_choose_num);
        Intrinsics.checkExpressionValueIsNotNull(string2, "AppUtil.getString(R.string.post_choose_num)");
        Object[] objArr2 = {"0"};
        String format2 = String.format(string2, Arrays.copyOf(objArr2, objArr2.length));
        Intrinsics.checkExpressionValueIsNotNull(format2, "java.lang.String.format(this, *args)");
        textView2.setText(format2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void isShowCheck(boolean z) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_post_choose_num);
        if (textView != null) {
            String string = AppUtil.getString(R.string.post_choose_num);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
            Object[] objArr = {"0"};
            String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
            textView.setText(format);
        }
        CheckBox checkBox = (CheckBox) _$_findCachedViewById(R$id.checkbox_all);
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
        MinePostPublishAdapter minePostPublishAdapter = this.adapter;
        List<PostList> data = minePostPublishAdapter != null ? minePostPublishAdapter.getData() : null;
        if ((data == null || data.isEmpty()) || data.size() <= 0) {
            return;
        }
        for (PostList it2 : data) {
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            it2.setSelectMinePostDelete(false);
            it2.setShowSelectPostDelete(z);
        }
        MinePostPublishAdapter minePostPublishAdapter2 = this.adapter;
        if (minePostPublishAdapter2 != null) {
            minePostPublishAdapter2.notifyDataSetChanged();
        }
        if (z) {
            MinePostPublishAdapter minePostPublishAdapter3 = this.adapter;
            if (minePostPublishAdapter3 == null) {
                return;
            }
            minePostPublishAdapter3.setIsShowMove(false);
            return;
        }
        MinePostPublishAdapter minePostPublishAdapter4 = this.adapter;
        if (minePostPublishAdapter4 == null) {
            return;
        }
        minePostPublishAdapter4.setIsShowMove(true);
    }

    private final void touchItem() {
        this.itemTouchHelper = new ItemTouchHelper(this.callBack);
        ItemTouchHelper itemTouchHelper = this.itemTouchHelper;
        if (itemTouchHelper != null) {
            itemTouchHelper.attachToRecyclerView(this.recyclerView);
        }
    }

    protected final void initRefreshLayout() {
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
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
            smartRefreshLayout4.setOnRefreshLoadMoreListener(new SingleSerialPostListActivity$initRefreshLayout$1(this));
        }
    }

    private final void initRecyclerView() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        RecyclerView recyclerView2 = this.recyclerView;
        SimpleItemAnimator simpleItemAnimator = (SimpleItemAnimator) (recyclerView2 != null ? recyclerView2.getItemAnimator() : null);
        if (simpleItemAnimator != null) {
            simpleItemAnimator.setSupportsChangeAnimations(false);
        }
        configLinearLayoutVerticalManager(this.recyclerView);
    }

    private final void initAdapter() {
        this.adapter = new MinePostPublishAdapter(getMContext(), this.recyclerView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
        MinePostPublishAdapter minePostPublishAdapter = this.adapter;
        if (minePostPublishAdapter != null) {
            minePostPublishAdapter.setEnableLoadMore(false);
        }
        MinePostPublishAdapter minePostPublishAdapter2 = this.adapter;
        if (minePostPublishAdapter2 != null) {
            minePostPublishAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initAdapter$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    Context mContext;
                    List<Object> data;
                    Object obj = (baseQuickAdapter == null || (data = baseQuickAdapter.getData()) == null) ? null : data.get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PostList");
                    }
                    NewPostDetailViewPagerActivity.Companion companion = NewPostDetailViewPagerActivity.Companion;
                    mContext = SingleSerialPostListActivity.this.getMContext();
                    companion.startActivity(mContext, ((PostList) obj).getId(), false, false, false);
                }
            });
        }
        MinePostPublishAdapter minePostPublishAdapter3 = this.adapter;
        if (minePostPublishAdapter3 != null) {
            minePostPublishAdapter3.addCheckBoxCallBack(new SingleSerialPostListActivity$initAdapter$2(this));
        }
    }

    private final void configLinearLayoutVerticalManager(RecyclerView recyclerView) {
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(getMContext(), 1, false);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refresh() {
        ApiImplService.Companion.getApiImplService().requestSerializePostList(this.serializeId).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<SerializePostListBean>() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$refresh$1
            /* JADX WARN: Code restructure failed: missing block: B:19:0x0037, code lost:
                r4 = r3.this$0.adapter;
             */
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onResult(SerializePostListBean serializePostListBean) {
                SmartRefreshLayout smartRefreshLayout;
                MinePostPublishAdapter minePostPublishAdapter;
                MinePostPublishAdapter minePostPublishAdapter2;
                SmartRefreshLayout smartRefreshLayout2;
                MinePostPublishAdapter minePostPublishAdapter3;
                List<PostList> data;
                MinePostPublishAdapter minePostPublishAdapter4;
                SmartRefreshLayout smartRefreshLayout3;
                smartRefreshLayout = SingleSerialPostListActivity.this.refreshLayout;
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.mo6481finishRefresh();
                }
                ArrayList<PostList> arrayList = null;
                ArrayList<PostList> data2 = serializePostListBean != null ? serializePostListBean.getData() : null;
                if (data2 == null || data2.isEmpty()) {
                    minePostPublishAdapter3 = SingleSerialPostListActivity.this.adapter;
                    if (minePostPublishAdapter3 == null || (data = minePostPublishAdapter3.getData()) == null || data.size() != 0 || minePostPublishAdapter4 == null) {
                        return;
                    }
                    smartRefreshLayout3 = SingleSerialPostListActivity.this.refreshLayout;
                    minePostPublishAdapter4.setEmptyViewState(2, smartRefreshLayout3);
                    return;
                }
                minePostPublishAdapter = SingleSerialPostListActivity.this.adapter;
                if (minePostPublishAdapter != null) {
                    if (serializePostListBean != null) {
                        arrayList = serializePostListBean.getData();
                    }
                    minePostPublishAdapter.setNewData(arrayList);
                }
                minePostPublishAdapter2 = SingleSerialPostListActivity.this.adapter;
                if (minePostPublishAdapter2 != null) {
                    minePostPublishAdapter2.setEnableLoadMore(false);
                }
                smartRefreshLayout2 = SingleSerialPostListActivity.this.refreshLayout;
                if (smartRefreshLayout2 == null) {
                    return;
                }
                smartRefreshLayout2.setEnableRefresh(false);
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
                MinePostPublishAdapter minePostPublishAdapter;
                List<PostList> data;
                MinePostPublishAdapter minePostPublishAdapter2;
                SmartRefreshLayout smartRefreshLayout2;
                smartRefreshLayout = SingleSerialPostListActivity.this.refreshLayout;
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.mo6481finishRefresh();
                }
                minePostPublishAdapter = SingleSerialPostListActivity.this.adapter;
                if (minePostPublishAdapter == null || (data = minePostPublishAdapter.getData()) == null || data.size() != 0 || minePostPublishAdapter2 == null) {
                    return;
                }
                smartRefreshLayout2 = SingleSerialPostListActivity.this.refreshLayout;
                minePostPublishAdapter2.setEmptyViewState(1, smartRefreshLayout2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeSerializePost(String str) {
        ApiImplService.Companion.getApiImplService().removeSerializePost(str, this.serializeId).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$removeSerializePost$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                SingleSerialPostListActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$removeSerializePost$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                MinePostPublishAdapter minePostPublishAdapter;
                MinePostPublishAdapter minePostPublishAdapter2;
                SingleSerialPostListActivity.this.hideWaitingDialog();
                minePostPublishAdapter = SingleSerialPostListActivity.this.adapter;
                List<PostList> data = minePostPublishAdapter != null ? minePostPublishAdapter.getData() : null;
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
                        minePostPublishAdapter2 = SingleSerialPostListActivity.this.adapter;
                        if (minePostPublishAdapter2 != null) {
                            minePostPublishAdapter2.notifyDataSetChanged();
                        }
                    }
                }
                Button button_delete = (Button) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.button_delete);
                Intrinsics.checkExpressionValueIsNotNull(button_delete, "button_delete");
                button_delete.setEnabled(false);
                TextView textView = (TextView) SingleSerialPostListActivity.this._$_findCachedViewById(R$id.text_post_choose_num);
                if (textView != null) {
                    String string = AppUtil.getString(R.string.post_choose_num);
                    Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.post_choose_num)");
                    Object[] objArr = {"0"};
                    String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                    Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                    textView.setText(format);
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_remove_serial_post_sucess));
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                SingleSerialPostListActivity.this.hideWaitingDialog();
            }
        });
    }

    private final void addPostToSerialize(String str, final ArrayList<PostList> arrayList) {
        ApiImplService.Companion.getApiImplService().addPostToSerialize(str, this.serializeId).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$addPostToSerialize$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                SingleSerialPostListActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$addPostToSerialize$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                MinePostPublishAdapter minePostPublishAdapter;
                SmartRefreshLayout smartRefreshLayout;
                SmartRefreshLayout smartRefreshLayout2;
                SingleSerialPostListActivity.this.hideWaitingDialog();
                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_add_serialize_post));
                minePostPublishAdapter = SingleSerialPostListActivity.this.adapter;
                if (minePostPublishAdapter != null) {
                    minePostPublishAdapter.addData((Collection) arrayList);
                }
                smartRefreshLayout = SingleSerialPostListActivity.this.refreshLayout;
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.setEnableRefresh(true);
                }
                smartRefreshLayout2 = SingleSerialPostListActivity.this.refreshLayout;
                if (smartRefreshLayout2 != null) {
                    smartRefreshLayout2.autoRefresh();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                SingleSerialPostListActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void movePost(String str, int i, long j) {
        ApiImplService.Companion.getApiImplService().moveSerializePost(str, i, j).subscribeOn(Schedulers.computation()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$movePost$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == 100) {
            ArrayList<PostList> parcelableArrayListExtra = intent != null ? intent.getParcelableArrayListExtra(AopConstants.APP_PROPERTIES_KEY) : null;
            if (parcelableArrayListExtra == null || parcelableArrayListExtra.size() <= 0) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            Iterator<PostList> it2 = parcelableArrayListExtra.iterator();
            while (it2.hasNext()) {
                PostList it3 = it2.next();
                if (sb.length() > 0) {
                    sb.append(",");
                }
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                sb.append(String.valueOf(it3.getId()));
            }
            String sb2 = sb.toString();
            Intrinsics.checkExpressionValueIsNotNull(sb2, "ids.toString()");
            addPostToSerialize(sb2, parcelableArrayListExtra);
        }
    }
}
