package com.one.tomato.mvp.p080ui.game.impl;

import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: IGameContact.kt */
/* renamed from: com.one.tomato.mvp.ui.game.impl.IGameContact$IGameView */
/* loaded from: classes3.dex */
public interface IGameContact$IGameView extends IBaseView {
    void handleList(ArrayList<AdPage> arrayList);

    void handlerBGLogin(GameBGLoginBean gameBGLoginBean, SubGamesBean subGamesBean);

    void handlerGameType(ArrayList<GameTypeData> arrayList);
}
