package com.one.tomato.mvp.p080ui.game.view;

import android.graphics.Color;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.p080ui.game.adapter.GameCenterHorAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.widget.MarqueeTextView;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameTabFragment$startScrollNotice$subscribe$1 */
/* loaded from: classes3.dex */
public final class GameTabFragment$startScrollNotice$subscribe$1<T> implements Consumer<Long> {
    final /* synthetic */ List $adPage;
    final /* synthetic */ GameTabFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public GameTabFragment$startScrollNotice$subscribe$1(GameTabFragment gameTabFragment, List list) {
        this.this$0 = gameTabFragment;
        this.$adPage = list;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Long l) {
        ArrayList arrayList;
        ArrayList arrayList2;
        Random.Default r6 = Random.Default;
        arrayList = this.this$0.gameRegisterUserBean;
        int nextInt = r6.nextInt(0, arrayList.size());
        arrayList2 = this.this$0.gameRegisterUserBean;
        Object obj = arrayList2.get(nextInt);
        Intrinsics.checkExpressionValueIsNotNull(obj, "gameRegisterUserBean[nextInt]");
        String string = AppUtil.getString(R.string.game_notice_text1);
        Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.game_notice_text1)");
        Object[] objArr = {(String) obj};
        String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
        MarqueeTextView marqueeTextView = (MarqueeTextView) this.this$0._$_findCachedViewById(R$id.tv_notice);
        if (marqueeTextView != null) {
            marqueeTextView.setVisibility(0);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) format);
        final SubGamesBean gameBrand = (SubGamesBean) this.$adPage.get(Random.Default.nextInt(0, this.$adPage.size()));
        MarqueeTextView marqueeTextView2 = (MarqueeTextView) this.this$0._$_findCachedViewById(R$id.tv_notice);
        if (marqueeTextView2 != null) {
            marqueeTextView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$startScrollNotice$subscribe$1.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GameCenterHorAdapter gameCenterHorAdapter;
                    GameCenterHorAdapter gameCenterHorAdapter2;
                    GameCenterHorAdapter gameCenterHorAdapter3;
                    gameCenterHorAdapter = GameTabFragment$startScrollNotice$subscribe$1.this.this$0.gameHorAdapter;
                    List<SubGamesBean> data = gameCenterHorAdapter != null ? gameCenterHorAdapter.getData() : null;
                    if (!(data == null || data.isEmpty())) {
                        for (SubGamesBean its : data) {
                            Intrinsics.checkExpressionValueIsNotNull(its, "its");
                            int gameId = its.getGameId();
                            SubGamesBean gameBrand2 = gameBrand;
                            Intrinsics.checkExpressionValueIsNotNull(gameBrand2, "gameBrand");
                            its.setSelector(gameId == gameBrand2.getGameId());
                        }
                        gameCenterHorAdapter2 = GameTabFragment$startScrollNotice$subscribe$1.this.this$0.gameHorAdapter;
                        if (gameCenterHorAdapter2 != null) {
                            gameCenterHorAdapter2.notifyDataSetChanged();
                        }
                        RecyclerView recyclerView = (RecyclerView) GameTabFragment$startScrollNotice$subscribe$1.this.this$0._$_findCachedViewById(R$id.game_recycler);
                        if (recyclerView != null) {
                            gameCenterHorAdapter3 = GameTabFragment$startScrollNotice$subscribe$1.this.this$0.gameHorAdapter;
                            if (gameCenterHorAdapter3 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            recyclerView.smoothScrollToPosition(gameCenterHorAdapter3.getData().indexOf(gameBrand));
                        }
                    }
                    GameTabFragment gameTabFragment = GameTabFragment$startScrollNotice$subscribe$1.this.this$0;
                    SubGamesBean gameBrand3 = gameBrand;
                    Intrinsics.checkExpressionValueIsNotNull(gameBrand3, "gameBrand");
                    gameTabFragment.loadGame(gameBrand3);
                }
            });
        }
        Intrinsics.checkExpressionValueIsNotNull(gameBrand, "gameBrand");
        spannableStringBuilder.append((CharSequence) gameBrand.getAdBrandName());
        SpannableString spannableString = new SpannableString(gameBrand.getAdName());
        spannableString.setSpan(new ClickableSpan() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$startScrollNotice$subscribe$1$titleClickSpan$1
            @Override // android.text.style.ClickableSpan
            public void onClick(View widget) {
                GameCenterHorAdapter gameCenterHorAdapter;
                GameCenterHorAdapter gameCenterHorAdapter2;
                GameCenterHorAdapter gameCenterHorAdapter3;
                Intrinsics.checkParameterIsNotNull(widget, "widget");
                gameCenterHorAdapter = GameTabFragment$startScrollNotice$subscribe$1.this.this$0.gameHorAdapter;
                List<SubGamesBean> data = gameCenterHorAdapter != null ? gameCenterHorAdapter.getData() : null;
                if (!(data == null || data.isEmpty())) {
                    for (SubGamesBean its : data) {
                        Intrinsics.checkExpressionValueIsNotNull(its, "its");
                        int gameId = its.getGameId();
                        SubGamesBean gameBrand2 = gameBrand;
                        Intrinsics.checkExpressionValueIsNotNull(gameBrand2, "gameBrand");
                        its.setSelector(gameId == gameBrand2.getGameId());
                    }
                    gameCenterHorAdapter2 = GameTabFragment$startScrollNotice$subscribe$1.this.this$0.gameHorAdapter;
                    if (gameCenterHorAdapter2 != null) {
                        gameCenterHorAdapter2.notifyDataSetChanged();
                    }
                    RecyclerView recyclerView = (RecyclerView) GameTabFragment$startScrollNotice$subscribe$1.this.this$0._$_findCachedViewById(R$id.game_recycler);
                    if (recyclerView != null) {
                        gameCenterHorAdapter3 = GameTabFragment$startScrollNotice$subscribe$1.this.this$0.gameHorAdapter;
                        if (gameCenterHorAdapter3 == null) {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                        recyclerView.smoothScrollToPosition(gameCenterHorAdapter3.getData().indexOf(gameBrand));
                    }
                }
                GameTabFragment gameTabFragment = GameTabFragment$startScrollNotice$subscribe$1.this.this$0;
                SubGamesBean gameBrand3 = gameBrand;
                Intrinsics.checkExpressionValueIsNotNull(gameBrand3, "gameBrand");
                gameTabFragment.loadGame(gameBrand3);
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint ds) {
                Intrinsics.checkParameterIsNotNull(ds, "ds");
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.parseColor("#FC4C7B"));
            }
        }, 0, spannableString.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString);
        spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.game_notice_text2));
        spannableStringBuilder.append((CharSequence) String.valueOf(FormatUtil.formatTwo(Double.valueOf(Random.Default.nextDouble(950.0d, 10000.0d)))));
        spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.common_renmingbi));
        MarqueeTextView marqueeTextView3 = (MarqueeTextView) this.this$0._$_findCachedViewById(R$id.tv_notice);
        if (marqueeTextView3 != null) {
            marqueeTextView3.setText(spannableStringBuilder);
        }
    }
}
