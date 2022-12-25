package com.one.tomato.adapter;

import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.LocationCity;
import com.one.tomato.entity.LocationCountry;
import com.one.tomato.entity.LocationProvince;
import com.one.tomato.p085ui.mine.LocationPickActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;

/* loaded from: classes3.dex */
public class LocationPickAdapter extends BaseRecyclerViewAdapter {
    private int curType = 1;
    private String intentCity;
    private String intentCountry;
    private String intentProvince;
    private LocationPickActivity locationPickActivity;
    private String selectCity;
    private String selectCountry;
    private String selectProvince;

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
    }

    public void setIntentData(String str, String str2, String str3) {
        this.intentCountry = str;
        this.intentProvince = str2;
        this.intentCity = str3;
    }

    public void setCurType(int i) {
        this.curType = i;
    }

    public LocationPickAdapter(LocationPickActivity locationPickActivity, int i, RecyclerView recyclerView) {
        super(locationPickActivity, i, recyclerView);
        this.locationPickActivity = locationPickActivity;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, Object obj) {
        super.convert(baseViewHolder, obj);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_location);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_select_hint);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_select);
        textView2.setVisibility(8);
        imageView.setVisibility(8);
        int i = this.curType;
        if (i == 1) {
            LocationCountry locationCountry = (LocationCountry) obj;
            textView.setText(locationCountry.getCountryName());
            if (TextUtils.isEmpty(this.selectCountry)) {
                this.selectCountry = this.intentCountry;
            }
            if (locationCountry.getCountryName().equals(this.selectCountry)) {
                imageView.setVisibility(0);
                if (!AppUtil.getString(R.string.location_country_china).equals(locationCountry.getCountryName())) {
                    return;
                }
                imageView.setVisibility(8);
                textView2.setVisibility(0);
                textView2.setText(R.string.location_pick_y);
            } else if (!AppUtil.getString(R.string.location_country_china).equals(locationCountry.getCountryName())) {
            } else {
                textView2.setVisibility(0);
                textView2.setText(R.string.location_pick);
            }
        } else if (i == 2) {
            LocationProvince locationProvince = (LocationProvince) obj;
            textView.setText(locationProvince.getProvinceName());
            if (TextUtils.isEmpty(this.selectProvince)) {
                this.selectProvince = this.intentProvince;
            }
            if (locationProvince.getProvinceName().equals(this.selectProvince)) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        } else if (i != 3) {
        } else {
            LocationCity locationCity = (LocationCity) obj;
            textView.setText(locationCity.getCityName());
            if (TextUtils.isEmpty(this.selectCity)) {
                this.selectCity = this.intentCity;
            }
            if (locationCity.getCityName().equals(this.selectCity)) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        Object obj = this.mData.get(i);
        int i2 = this.curType;
        if (i2 == 1) {
            LocationCountry locationCountry = (LocationCountry) obj;
            this.selectCountry = locationCountry.getCountryName();
            this.selectProvince = "";
            this.selectCity = "";
            this.locationPickActivity.updateLocation(this.selectCountry, this.selectProvince, this.selectCity);
            if (AppUtil.getString(R.string.location_country_china).equals(locationCountry.getCountryName())) {
                this.locationPickActivity.updateType(2);
                this.locationPickActivity.setCanSubmit(false);
                return;
            }
            notifyDataSetChanged();
            this.locationPickActivity.setCanSubmit(true);
        } else if (i2 != 2) {
            if (i2 != 3) {
                return;
            }
            this.selectCity = ((LocationCity) obj).getCityName();
            notifyDataSetChanged();
            this.locationPickActivity.updateLocation(this.selectCountry, this.selectProvince, this.selectCity);
            this.locationPickActivity.setCanSubmit(true);
        } else {
            this.selectProvince = ((LocationProvince) obj).getProvinceName();
            this.selectCity = "";
            this.locationPickActivity.updateLocation(this.selectCountry, this.selectProvince, this.selectCity);
            if (AppUtil.getString(R.string.location_city_beijing).equals(this.selectProvince) || AppUtil.getString(R.string.location_city_tianjin).equals(this.selectProvince) || AppUtil.getString(R.string.location_city_shanghai).equals(this.selectProvince) || AppUtil.getString(R.string.location_city_chongqing).equals(this.selectProvince)) {
                notifyDataSetChanged();
                this.locationPickActivity.setCanSubmit(true);
                return;
            }
            this.locationPickActivity.updateType(3);
            this.locationPickActivity.setCanSubmit(false);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        this.locationPickActivity.initData();
    }
}
