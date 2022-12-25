package com.one.tomato.p085ui.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.adapter.LocationPickAdapter;
import com.one.tomato.entity.LocationCity;
import com.one.tomato.entity.LocationCountry;
import com.one.tomato.entity.LocationProvince;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_location_pick)
/* renamed from: com.one.tomato.ui.mine.LocationPickActivity */
/* loaded from: classes3.dex */
public class LocationPickActivity extends BaseRecyclerViewActivity {
    private String intentCity;
    private String intentCountry;
    private String intentProvince;
    private LocationPickAdapter locationPickAdapter;
    private String selectCity;
    private String selectCountry;
    private String selectProvince;
    @ViewInject(R.id.tv_all)
    private TextView tv_all;
    private int curType = 1;
    private List<LocationCountry> countryList = new ArrayList();
    private List<LocationProvince> provinceList = new ArrayList();
    Handler handler = new Handler() { // from class: com.one.tomato.ui.mine.LocationPickActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            LocationPickActivity.this.hideWaitingDialog();
            int i = message.what;
            if (i == 1) {
                LocationPickActivity.this.tv_all.setVisibility(0);
                LocationPickActivity locationPickActivity = LocationPickActivity.this;
                locationPickActivity.updateType(locationPickActivity.curType);
            } else if (i != 2) {
            } else {
                LocationPickActivity.this.locationPickAdapter.setEmptyViewState(1, ((BaseRecyclerViewActivity) LocationPickActivity.this).refreshLayout);
                LocationPickActivity.this.tv_all.setVisibility(8);
            }
        }
    };

    public static void startActivity(Context context, String str, String str2, String str3) {
        Intent intent = new Intent(context, LocationPickActivity.class);
        intent.putExtra(AopConstants.COUNTRY, str);
        intent.putExtra(AopConstants.PROVINCE, str2);
        intent.putExtra(AopConstants.CITY, str3);
        ((Activity) context).startActivityForResult(intent, 26);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity, com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.intentCountry = getIntent().getExtras().getString(AopConstants.COUNTRY);
        this.intentProvince = getIntent().getExtras().getString(AopConstants.PROVINCE);
        this.intentCity = getIntent().getExtras().getString(AopConstants.CITY);
        init();
    }

    private void init() {
        initTitleBar();
        this.titleTV.setText(R.string.location_setting);
        this.rightTV.setText(R.string.common_complete);
        this.rightTV.setVisibility(0);
        this.rightTV.setTextColor(getResources().getColor(R.color.text_light));
        this.rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.mine.LocationPickActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(AopConstants.COUNTRY, LocationPickActivity.this.selectCountry);
                intent.putExtra(AopConstants.PROVINCE, LocationPickActivity.this.selectProvince);
                intent.putExtra(AopConstants.CITY, LocationPickActivity.this.selectCity);
                LocationPickActivity.this.setResult(-1, intent);
                LocationPickActivity.this.finish();
            }
        });
        this.refreshLayout.setEnableRefresh(false);
        initAdapter();
        initData();
    }

    private void initAdapter() {
        this.locationPickAdapter = new LocationPickAdapter(this, R.layout.item_location_pick, this.recyclerView);
        this.recyclerView.setAdapter(this.locationPickAdapter);
        this.locationPickAdapter.setIntentData(this.intentCountry, this.intentProvince, this.intentCity);
    }

    public void updateType(int i) {
        this.curType = i;
        this.locationPickAdapter.setCurType(this.curType);
        int i2 = this.curType;
        if (i2 == 1) {
            LocationCountry locationCountry = new LocationCountry(this.intentCountry);
            if (this.countryList.contains(locationCountry)) {
                int indexOf = this.countryList.indexOf(locationCountry);
                this.countryList.remove(indexOf);
                this.countryList.add(0, this.countryList.get(indexOf));
            }
            this.locationPickAdapter.setNewData(this.countryList);
        } else if (i2 == 2) {
            LocationProvince locationProvince = new LocationProvince(this.intentProvince);
            if (this.provinceList.contains(locationProvince)) {
                int indexOf2 = this.provinceList.indexOf(locationProvince);
                this.provinceList.remove(indexOf2);
                this.provinceList.add(0, this.provinceList.get(indexOf2));
            }
            this.locationPickAdapter.setNewData(this.provinceList);
        } else if (i2 == 3) {
            int indexOf3 = this.provinceList.indexOf(new LocationProvince(this.selectProvince));
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.provinceList.get(indexOf3).getCityList());
            LocationCity locationCity = new LocationCity(this.intentCity);
            if (arrayList.contains(locationCity)) {
                int indexOf4 = arrayList.indexOf(locationCity);
                arrayList.remove(indexOf4);
                arrayList.add(0, (LocationCity) arrayList.get(indexOf4));
            }
            this.locationPickAdapter.setNewData(arrayList);
        }
        this.locationPickAdapter.setEnableLoadMore(false);
    }

    public void updateLocation(String str, String str2, String str3) {
        this.selectCountry = str;
        this.selectProvince = str2;
        this.selectCity = str3;
    }

    public void setCanSubmit(boolean z) {
        this.rightTV.setEnabled(z);
        if (z) {
            this.rightTV.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            this.rightTV.setTextColor(getResources().getColor(R.color.text_middle));
        }
    }

    public void initData() {
        showWaitingDialog();
        new Thread(new Runnable() { // from class: com.one.tomato.ui.mine.LocationPickActivity.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(LocationPickActivity.this.getAssets().open("country.json")));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine.trim());
                    }
                    LocationPickActivity.this.countryList.addAll((ArrayList) BaseApplication.getGson().fromJson(sb.toString(), new TypeToken<ArrayList<LocationCountry>>(this) { // from class: com.one.tomato.ui.mine.LocationPickActivity.3.1
                    }.getType()));
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(LocationPickActivity.this.getAssets().open("city.json")));
                    StringBuilder sb2 = new StringBuilder();
                    while (true) {
                        String readLine2 = bufferedReader2.readLine();
                        if (readLine2 == null) {
                            LocationPickActivity.this.provinceList.addAll((ArrayList) BaseApplication.getGson().fromJson(sb2.toString(), new TypeToken<ArrayList<LocationProvince>>(this) { // from class: com.one.tomato.ui.mine.LocationPickActivity.3.2
                            }.getType()));
                            LocationPickActivity.this.handler.sendEmptyMessage(1);
                            return;
                        }
                        sb2.append(readLine2.trim());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LocationPickActivity.this.handler.sendEmptyMessage(2);
                }
            }
        }).start();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        int i = this.curType;
        if (i == 3) {
            this.curType = 2;
            updateType(this.curType);
            this.selectCity = null;
        } else if (i == 2) {
            this.curType = 1;
            updateType(this.curType);
            this.selectProvince = null;
        } else {
            super.onBackPressed();
        }
    }
}
