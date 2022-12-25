package com.one.tomato.mvp.p080ui.login.impl;

import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: ICountryCodeContact.kt */
/* renamed from: com.one.tomato.mvp.ui.login.impl.ICountryCodeContact$ICountryCodeView */
/* loaded from: classes3.dex */
public interface ICountryCodeContact$ICountryCodeView extends IBaseView {
    void handleCountryListFromDB(ArrayList<CountryDB> arrayList);

    void handleCountryListFromServer(ArrayList<CountryDB> arrayList);
}
