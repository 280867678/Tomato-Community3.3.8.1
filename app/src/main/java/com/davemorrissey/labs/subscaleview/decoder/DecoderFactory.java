package com.davemorrissey.labs.subscaleview.decoder;

import android.support.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public interface DecoderFactory<T> {
    @NonNull
    T make() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;
}
