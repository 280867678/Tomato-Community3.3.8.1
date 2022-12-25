package com.amazonaws.services.p054s3.model.inventory;

/* renamed from: com.amazonaws.services.s3.model.inventory.InventoryFormat */
/* loaded from: classes2.dex */
public enum InventoryFormat {
    CSV("CSV");
    
    private final String format;

    InventoryFormat(String str) {
        this.format = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.format;
    }
}
