package com.eclipsesource.p056v8.utils;

/* renamed from: com.eclipsesource.v8.utils.SingleTypeAdapter */
/* loaded from: classes2.dex */
public abstract class SingleTypeAdapter implements TypeAdapter {
    private int typeToAdapt;

    public abstract Object adapt(Object obj);

    public SingleTypeAdapter(int i) {
        this.typeToAdapt = i;
    }

    @Override // com.eclipsesource.p056v8.utils.TypeAdapter
    public Object adapt(int i, Object obj) {
        if (i == this.typeToAdapt) {
            return adapt(obj);
        }
        return TypeAdapter.DEFAULT;
    }
}
