package com.tomatolive.library.model;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class OnLineUsersEntity implements Serializable {
    public List<UserEntity> list;
    public int popularity = 0;
    public int vipCount = 0;

    public List<UserEntity> getUserEntityList() {
        return this.list;
    }

    public void setUserEntitieList(List<UserEntity> list) {
        this.list = list;
    }
}
