package com.one.tomato.entity;

import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.SubGamesBean;
import java.util.ArrayList;

/* renamed from: com.one.tomato.entity.Ad */
/* loaded from: classes3.dex */
public class C2516Ad {
    public static final String TYPE_ARTICLE_REC = "articleRec";
    public static final String TYPE_CHESS_BANNER = "chess_banner";
    public static final String TYPE_CIRCLE = "circle";
    public static final String TYPE_FORCE = "force";
    public static final String TYPE_GAME_CENTER_BANNER = "game_center_banner";
    public static final String TYPE_LIST = "list";
    public static final String TYPE_LIVE_GAME = "live_game";
    public static final String TYPE_PAPA = "papa";
    public static final String TYPE_PAPA_BANNER = "papa_banner";
    public static final String TYPE_PAPA_REC = "papaRec";
    public static final String TYPE_START = "start";
    private ArrayList<SubGamesBean> articleRec;
    private ArrayList<AdPage> bgGameBannerPage;
    private ArrayList<AdPage> circlePage;
    private ArrayList<AdPage> forceAlertPage;
    private ArrayList<SubGamesBean> gameInLive;
    private String keyApp;
    private ArrayList<AdPage> listPage;
    private ArrayList<AdPage> papaBannerPage;
    private ArrayList<AdPage> papaPage;
    private ArrayList<SubGamesBean> papaRec;
    private ArrayList<AdPage> startupPage;
    private int version;
    private ArrayList<AdPage> yuleBannerPage;

    public String getKeyApp() {
        return this.keyApp;
    }

    public void setKeyApp(String str) {
        this.keyApp = str;
    }

    public ArrayList<SubGamesBean> getArticleRec() {
        return this.articleRec;
    }

    public void setArticleRec(ArrayList<SubGamesBean> arrayList) {
        this.articleRec = arrayList;
    }

    public ArrayList<SubGamesBean> getPapaRec() {
        return this.papaRec;
    }

    public void setPapaRec(ArrayList<SubGamesBean> arrayList) {
        this.papaRec = arrayList;
    }

    public ArrayList<AdPage> getPapaPage() {
        return this.papaPage;
    }

    public void setPapaPage(ArrayList<AdPage> arrayList) {
        this.papaPage = arrayList;
    }

    public ArrayList<AdPage> getStartupPage() {
        return this.startupPage;
    }

    public void setStartupPage(ArrayList<AdPage> arrayList) {
        this.startupPage = arrayList;
    }

    public ArrayList<AdPage> getListPage() {
        return this.listPage;
    }

    public void setListPage(ArrayList<AdPage> arrayList) {
        this.listPage = arrayList;
    }

    public ArrayList<AdPage> getCirclePage() {
        return this.circlePage;
    }

    public void setCirclePage(ArrayList<AdPage> arrayList) {
        this.circlePage = arrayList;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public ArrayList<AdPage> getPapaBannerPage() {
        return this.papaBannerPage;
    }

    public void setPapaBannerPage(ArrayList<AdPage> arrayList) {
        this.papaBannerPage = arrayList;
    }

    public ArrayList<AdPage> getForceAlertPage() {
        return this.forceAlertPage;
    }

    public void setForceAlertPage(ArrayList<AdPage> arrayList) {
        this.forceAlertPage = arrayList;
    }

    public ArrayList<SubGamesBean> getGameInLive() {
        return this.gameInLive;
    }

    public void setGameInLive(ArrayList<SubGamesBean> arrayList) {
        this.gameInLive = arrayList;
    }

    public ArrayList<AdPage> getYuleBannerPage() {
        return this.yuleBannerPage;
    }

    public void setYuleBannerPage(ArrayList<AdPage> arrayList) {
        this.yuleBannerPage = arrayList;
    }

    public void setBgGameBannerPage(ArrayList<AdPage> arrayList) {
        this.bgGameBannerPage = arrayList;
    }

    public ArrayList<AdPage> getBgGameBannerPage() {
        return this.bgGameBannerPage;
    }
}
