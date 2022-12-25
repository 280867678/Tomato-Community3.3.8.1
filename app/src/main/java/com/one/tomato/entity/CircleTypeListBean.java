package com.one.tomato.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes3.dex */
public class CircleTypeListBean implements Serializable {
    private static final long serialVersionUID = -6226653991760427678L;
    public int articleCount;
    public ArrayList<PostList> articleList;
    public String brief;
    public String categoryName;
    public int followCount;
    public int followFlag;
    public int groupId;
    public String groupImage;
    public String groupName;
    public int official;
    public int textFlag;

    public CircleTypeListBean(int i) {
        this.groupId = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && CircleTypeListBean.class == obj.getClass() && this.groupId == ((CircleTypeListBean) obj).groupId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.groupId));
    }
}
