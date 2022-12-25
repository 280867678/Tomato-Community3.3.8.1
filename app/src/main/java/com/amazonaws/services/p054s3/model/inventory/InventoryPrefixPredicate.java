package com.amazonaws.services.p054s3.model.inventory;

/* renamed from: com.amazonaws.services.s3.model.inventory.InventoryPrefixPredicate */
/* loaded from: classes2.dex */
public final class InventoryPrefixPredicate extends InventoryFilterPredicate {
    private final String prefix;

    public InventoryPrefixPredicate(String str) {
        this.prefix = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Override // com.amazonaws.services.p054s3.model.inventory.InventoryFilterPredicate
    public void accept(InventoryPredicateVisitor inventoryPredicateVisitor) {
        inventoryPredicateVisitor.visit(this);
    }
}
