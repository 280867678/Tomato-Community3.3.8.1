package com.tomatolive.library.model;

import android.text.TextUtils;

/* loaded from: classes3.dex */
public class TicketRoomInfoEntity {
    public String ticketRoomSwitch;
    public String totalUserNum = "0";
    public String currentUserNum = "0";
    public String income = "0";

    public boolean isOpenTicketEnterEnable() {
        return TextUtils.equals("1", this.ticketRoomSwitch);
    }

    public void setTicketRoomSwitch(String str) {
        this.ticketRoomSwitch = str;
    }
}
