package net.vidageek.mirror.get.dsl;

import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public interface GetterHandler {
    Object field(String str);

    Object field(Field field);
}
