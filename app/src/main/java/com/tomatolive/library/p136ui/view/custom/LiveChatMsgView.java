package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.ChatEntity;
import com.tomatolive.library.p136ui.adapter.ChatMsgListAdapter;
import com.tomatolive.library.p136ui.view.divider.RVDividerListMsg;
import com.tomatolive.library.p136ui.view.widget.ScrollTextView;
import com.tomatolive.library.p136ui.view.widget.ScrollTextViewForGrade;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.LiveChatMsgView */
/* loaded from: classes3.dex */
public class LiveChatMsgView extends LinearLayout {
    private Disposable liveTitleTimeOutTimer;
    private Context mContext;
    private RecyclerView mRecycleChatMsg;
    private ChatMsgListAdapter msgListAdapter;
    private TextView tvLiveTitle;
    private TextView tvUnreadMsg;
    private ScrollTextViewForGrade tvUserGrade;
    private ScrollTextView tvUserName;

    public LiveChatMsgView(Context context) {
        super(context);
        initView(context);
    }

    public LiveChatMsgView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(1);
        LinearLayout.inflate(context, R$layout.fq_layout_chat_msg_view, this);
        this.mContext = context;
        this.tvLiveTitle = (TextView) findViewById(R$id.tv_live_title);
        this.mRecycleChatMsg = (RecyclerView) findViewById(R$id.rv_chat_msg);
        this.tvUnreadMsg = (TextView) findViewById(R$id.tv_unread_msg);
        this.tvUserGrade = (ScrollTextViewForGrade) findViewById(R$id.tv_user_grade);
        this.tvUserName = (ScrollTextView) findViewById(R$id.tv_user_name);
        initMsgAdapter();
        initListener();
    }

    private void initMsgAdapter() {
        this.msgListAdapter = new ChatMsgListAdapter(R$layout.fq_recycle_item_chat_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setAutoMeasureEnabled(false);
        linearLayoutManager.setOrientation(1);
        this.mRecycleChatMsg.setLayoutManager(linearLayoutManager);
        this.mRecycleChatMsg.setHasFixedSize(true);
        this.mRecycleChatMsg.addItemDecoration(new RVDividerListMsg(this.mContext, 17170445));
        this.mRecycleChatMsg.setAdapter(this.msgListAdapter);
        this.msgListAdapter.bindToRecyclerView(this.mRecycleChatMsg);
    }

    private void initListener() {
        this.mRecycleChatMsg.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.tomatolive.library.ui.view.custom.LiveChatMsgView.1
            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (!recyclerView.canScrollVertically(1)) {
                    LiveChatMsgView.this.tvUnreadMsg.setVisibility(4);
                }
            }
        });
        this.tvUnreadMsg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveChatMsgView$tXtngGTSUXJHxzVMKYDLvk_Dgv4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LiveChatMsgView.this.lambda$initListener$0$LiveChatMsgView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LiveChatMsgView(View view) {
        this.tvUnreadMsg.setVisibility(4);
        this.mRecycleChatMsg.scrollToPosition(this.msgListAdapter.getItemCount() - 1);
    }

    public void setOnChatMsgItemClickListener(ChatMsgListAdapter.OnItemClickListener onItemClickListener) {
        this.msgListAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void addChatMsg(ChatEntity chatEntity) {
        this.msgListAdapter.addMsg(chatEntity);
        if (this.tvUnreadMsg.getVisibility() == 0 || !this.mRecycleChatMsg.canScrollVertically(1)) {
            return;
        }
        this.tvUnreadMsg.setVisibility(0);
    }

    public void addChatMsgList(List<ChatEntity> list) {
        this.msgListAdapter.addMsgs(list);
        if (this.tvUnreadMsg.getVisibility() == 0 || !this.mRecycleChatMsg.canScrollVertically(1)) {
            return;
        }
        this.tvUnreadMsg.setVisibility(0);
    }

    public void clearChatMsg() {
        ChatMsgListAdapter chatMsgListAdapter = this.msgListAdapter;
        if (chatMsgListAdapter == null || chatMsgListAdapter.getData() == null) {
            return;
        }
        this.msgListAdapter.getData().clear();
        this.msgListAdapter.notifyDataSetChanged();
    }

    public void setUserGradeInfo(String str, String str2) {
        this.tvUserGrade.setText(str);
        this.tvUserName.setText(str2 + ConstantUtils.PLACEHOLDER_STR_ONE + getResources().getString(R$string.fq_live_join_notify_nobility));
    }

    public void setChatMsgBigSize(boolean z) {
        this.msgListAdapter.setTextSize(z);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mRecycleChatMsg.getLayoutParams();
        layoutParams.height = z ? ScreenUtils.getScreenHeight() / 2 : SizeUtils.dp2px(180.0f);
        this.mRecycleChatMsg.setLayoutParams(layoutParams);
        if (!z) {
            this.mRecycleChatMsg.scrollToPosition(this.msgListAdapter.getItemCount() - 1);
        }
        float f = 18.0f;
        this.tvUserGrade.updateSize(z ? 18.0f : 12.0f);
        ScrollTextView scrollTextView = this.tvUserName;
        if (!z) {
            f = 12.0f;
        }
        scrollTextView.updateSize(f);
    }

    public void updateGuardTypeItemDataByUid(final String str, final int i) {
        Observable.just(this.msgListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveChatMsgView$6kw2DV8bHlpU4R1BlG9cewx5KM4
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return LiveChatMsgView.lambda$updateGuardTypeItemDataByUid$1(str, i, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.LiveChatMsgView.2
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                LiveChatMsgView.this.msgListAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Boolean lambda$updateGuardTypeItemDataByUid$1(String str, int i, List list) throws Exception {
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ChatEntity chatEntity = (ChatEntity) it2.next();
            if (TextUtils.equals(chatEntity.getUid(), str)) {
                chatEntity.setGuardType(i);
            }
        }
        return true;
    }

    public void updateNobilityTypeItemDataByUid(final String str, final int i) {
        Observable.just(this.msgListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveChatMsgView$c3NXkVZQcaJkr-7_gLJuHmPp4pw
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return LiveChatMsgView.lambda$updateNobilityTypeItemDataByUid$2(str, i, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.LiveChatMsgView.3
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                LiveChatMsgView.this.msgListAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Boolean lambda$updateNobilityTypeItemDataByUid$2(String str, int i, List list) throws Exception {
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ChatEntity chatEntity = (ChatEntity) it2.next();
            if (TextUtils.equals(chatEntity.getUid(), str)) {
                chatEntity.setNobilityType(i);
            }
        }
        return true;
    }

    public void updateRoleItemDataByUid(final String str, final String str2) {
        Observable.just(this.msgListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveChatMsgView$L-enCpjxIaINPyJCMLgPQlSmDx0
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return LiveChatMsgView.lambda$updateRoleItemDataByUid$3(str, str2, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.LiveChatMsgView.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                LiveChatMsgView.this.msgListAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Boolean lambda$updateRoleItemDataByUid$3(String str, String str2, List list) throws Exception {
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ChatEntity chatEntity = (ChatEntity) it2.next();
            if (TextUtils.equals(chatEntity.getUid(), str)) {
                chatEntity.setRole(str2);
            }
        }
        return true;
    }

    public void updateAttentionAnchor(final boolean z) {
        Observable.just(this.msgListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveChatMsgView$KsIC7b8SbaW0BdY0gdNsUBbZ10M
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return LiveChatMsgView.this.lambda$updateAttentionAnchor$4$LiveChatMsgView(z, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.LiveChatMsgView.5
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                LiveChatMsgView.this.msgListAdapter.notifyDataSetChanged();
            }
        });
    }

    public /* synthetic */ Boolean lambda$updateAttentionAnchor$4$LiveChatMsgView(boolean z, List list) throws Exception {
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ChatEntity chatEntity = (ChatEntity) it2.next();
            if (TextUtils.equals(chatEntity.getUid(), UserInfoManager.getInstance().getUserId()) && chatEntity.getMsgType() == 18 && z) {
                chatEntity.setUid(UserInfoManager.getInstance().getUserId());
                chatEntity.setTargetId(UserInfoManager.getInstance().getUserId());
                chatEntity.setMsgText(this.mContext.getString(R$string.fq_home_btn_attention_yes));
                chatEntity.setMsgType(18);
            }
        }
        return true;
    }

    public void setLiveTitle(String str) {
        this.tvLiveTitle.setText(this.mContext.getString(R$string.fq_text_live_title, str));
        this.tvLiveTitle.setVisibility(0);
        this.liveTitleTimeOutTimer = Observable.timer(10L, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveChatMsgView$zBWFCc3Pq2YXPJjFRObIfQK0uEo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                LiveChatMsgView.this.lambda$setLiveTitle$5$LiveChatMsgView((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$setLiveTitle$5$LiveChatMsgView(Long l) throws Exception {
        this.tvLiveTitle.setVisibility(4);
    }

    public void onRelease() {
        clearChatMsg();
        Disposable disposable = this.liveTitleTimeOutTimer;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.liveTitleTimeOutTimer.dispose();
        this.liveTitleTimeOutTimer = null;
    }
}
