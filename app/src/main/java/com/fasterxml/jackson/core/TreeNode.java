package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonParser;

/* loaded from: classes2.dex */
public interface TreeNode {
    JsonToken asToken();

    JsonParser.NumberType numberType();
}
