package com.tomatolive.library.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class QMInteractTaskEntity implements Parcelable {
    public static final Parcelable.Creator<QMInteractTaskEntity> CREATOR = new Parcelable.Creator<QMInteractTaskEntity>() { // from class: com.tomatolive.library.model.QMInteractTaskEntity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public QMInteractTaskEntity mo6582createFromParcel(Parcel parcel) {
            return new QMInteractTaskEntity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        /* renamed from: newArray */
        public QMInteractTaskEntity[] mo6583newArray(int i) {
            return new QMInteractTaskEntity[i];
        }
    };
    public String anchorName;
    public String chargeGiftNum;
    public String createTime;
    public String giftMarkId;
    public String giftNum;
    public String giftUrl;

    /* renamed from: id */
    public String f5845id;
    public boolean isEdit;
    public boolean isSelected;
    public boolean isStartTask;
    public String putName;
    public String putUserId;
    public String status;
    public String taskId;
    public String taskName;
    public String taskType;
    public String type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isChargeTask() {
        return TextUtils.equals(this.type, "1") || TextUtils.equals(this.taskType, "1");
    }

    public String getChargeGiftNum() {
        if (isChargeTask() && (NumberUtils.string2int(this.chargeGiftNum, 0) >= NumberUtils.string2int(this.giftNum, 0) || TextUtils.equals(this.status, ConstantUtils.QM_TASK_STATUS_103))) {
            return this.giftNum;
        }
        return this.chargeGiftNum;
    }

    public String toString() {
        return "QMInteractTaskEntity{taskId='" + this.taskId + "'taskType='" + this.taskType + "', giftNum='" + this.giftNum + "', taskName='" + this.taskName + "', chargeGiftNum='" + this.chargeGiftNum + "', putName='" + this.putName + "'}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f5845id);
        parcel.writeString(this.taskId);
        parcel.writeString(this.giftMarkId);
        parcel.writeString(this.giftNum);
        parcel.writeString(this.giftUrl);
        parcel.writeString(this.taskName);
        parcel.writeString(this.chargeGiftNum);
        parcel.writeString(this.putName);
        parcel.writeString(this.anchorName);
        parcel.writeString(this.putUserId);
        parcel.writeString(this.status);
        parcel.writeString(this.createTime);
        parcel.writeString(this.taskType);
        parcel.writeString(this.type);
        parcel.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isEdit ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.isStartTask ? (byte) 1 : (byte) 0);
    }

    public QMInteractTaskEntity() {
        this.giftNum = "0";
        this.chargeGiftNum = "0";
        this.status = "";
        this.createTime = "";
        this.taskType = "";
        this.type = "";
        this.isSelected = false;
        this.isEdit = false;
        this.isStartTask = false;
    }

    protected QMInteractTaskEntity(Parcel parcel) {
        this.giftNum = "0";
        this.chargeGiftNum = "0";
        this.status = "";
        this.createTime = "";
        this.taskType = "";
        this.type = "";
        boolean z = false;
        this.isSelected = false;
        this.isEdit = false;
        this.isStartTask = false;
        this.f5845id = parcel.readString();
        this.taskId = parcel.readString();
        this.giftMarkId = parcel.readString();
        this.giftNum = parcel.readString();
        this.giftUrl = parcel.readString();
        this.taskName = parcel.readString();
        this.chargeGiftNum = parcel.readString();
        this.putName = parcel.readString();
        this.anchorName = parcel.readString();
        this.putUserId = parcel.readString();
        this.status = parcel.readString();
        this.createTime = parcel.readString();
        this.taskType = parcel.readString();
        this.type = parcel.readString();
        this.isSelected = parcel.readByte() != 0;
        this.isEdit = parcel.readByte() != 0;
        this.isStartTask = parcel.readByte() != 0 ? true : z;
    }
}
