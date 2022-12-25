package com.amazonaws.services.p054s3.model.inventory;

import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.inventory.InventoryFilterPredicate */
/* loaded from: classes2.dex */
public abstract class InventoryFilterPredicate implements Serializable {
    public abstract void accept(InventoryPredicateVisitor inventoryPredicateVisitor);
}
