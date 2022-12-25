package com.tomatolive.library.http;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class HttpResultPageModel<T> {
    public int pageNumber = 1;
    public int pageSize = 0;
    public int totalRowsCount = 0;
    public int totalPageCount = 1;
    public String totalPrice = "0";
    public String totalGold = "0";
    public List<T> dataList = new ArrayList();
    public boolean isHasBanner = false;

    public boolean isMorePage() {
        int i;
        int i2 = this.totalPageCount;
        return i2 == 0 || (i = this.pageNumber) == 0 || i >= i2;
    }

    public boolean isNoEmptyData() {
        return this.totalRowsCount > 0;
    }

    public String toString() {
        return "HttpResultPageModel{dataList=" + this.dataList + '}';
    }
}
