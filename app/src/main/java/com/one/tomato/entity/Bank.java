package com.one.tomato.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/* loaded from: classes3.dex */
public class Bank extends BaseIndexPinyinBean implements Parcelable {
    public static final Parcelable.Creator<Bank> CREATOR = new Parcelable.Creator<Bank>() { // from class: com.one.tomato.entity.Bank.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public Bank mo6355createFromParcel(Parcel parcel) {
            return new Bank(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public Bank[] mo6356newArray(int i) {
            return new Bank[i];
        }
    };
    private String bankId;
    private String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean
    public String getTarget() {
        return this.name;
    }

    public String getBankId() {
        return this.bankId;
    }

    public void setBankId(String str) {
        this.bankId = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.bankId);
        parcel.writeString(this.name);
    }

    public Bank() {
    }

    protected Bank(Parcel parcel) {
        this.bankId = parcel.readString();
        this.name = parcel.readString();
    }
}
