package com.one.tomato.entity.p079db;

import com.one.tomato.entity.PostList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.CircleDiscoverListBean */
/* loaded from: classes3.dex */
public class CircleDiscoverListBean extends LitePalSupport implements Serializable {
    public int articleCount;
    public ArrayList<PostList> articleList;
    public String brief;
    public int categoryId;
    public String categoryName;
    public int followCount;
    public int followFlag;
    public int groupId;
    public String groupImage;
    public String groupName;
    public boolean isAdBanner;
    public int official;
    public int textFlag;

    public CircleDiscoverListBean() {
    }

    public CircleDiscoverListBean(int i) {
        this.groupId = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && CircleDiscoverListBean.class == obj.getClass() && this.groupId == ((CircleDiscoverListBean) obj).groupId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.groupId));
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int i) {
        this.groupId = i;
    }

    public String getGroupImage() {
        return this.groupImage;
    }

    public void setGroupImage(String str) {
        this.groupImage = str;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String str) {
        this.brief = str;
    }

    public int getFollowFlag() {
        return this.followFlag;
    }

    public void setFollowFlag(int i) {
        this.followFlag = i;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int i) {
        this.categoryId = i;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public int getArticleCount() {
        return this.articleCount;
    }

    public void setArticleCount(int i) {
        this.articleCount = i;
    }

    public int getFollowCount() {
        return this.followCount;
    }

    public void setFollowCount(int i) {
        this.followCount = i;
    }

    public int getTextFlag() {
        return this.textFlag;
    }

    public void setTextFlag(int i) {
        this.textFlag = i;
    }

    public int getOfficial() {
        return this.official;
    }

    public void setOfficial(int i) {
        this.official = i;
    }

    public ArrayList<PostList> getArticleList() {
        return this.articleList;
    }

    public void setArticleList(ArrayList<PostList> arrayList) {
        this.articleList = arrayList;
    }

    public boolean isAdBanner() {
        return this.isAdBanner;
    }

    public void setAdBanner(boolean z) {
        this.isAdBanner = z;
    }
}
