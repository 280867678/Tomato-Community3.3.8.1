package com.tomatolive.library.model;

import com.tomatolive.library.http.HttpResultPageModel;

/* loaded from: classes3.dex */
public class ReceiveGiftRecordPageEntity {
    public HttpResultPageModel<ReceiveGiftRecordEntity> page;
    public GiftStatisticsEntity statis;

    /* loaded from: classes3.dex */
    public static class GiftStatisticsEntity {
        public long payMemberCount;
        public String totalPracticalPrice;
    }
}
