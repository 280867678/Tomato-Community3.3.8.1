package net.vidageek.mirror.config;

/* loaded from: classes4.dex */
public enum Item {
    REFLECTION_PROVIDER("provider.class");
    
    private final String key;

    Item(String str) {
        this.key = str;
    }

    public String getPropertyKey() {
        return this.key;
    }
}
