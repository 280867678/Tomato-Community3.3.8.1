package com.airbnb.lottie.parser;

import android.util.JsonReader;
import java.io.IOException;

/* loaded from: classes2.dex */
interface ValueParser<V> {
    /* renamed from: parse */
    V mo5817parse(JsonReader jsonReader, float f) throws IOException;
}
