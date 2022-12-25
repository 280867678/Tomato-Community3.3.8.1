package net.vidageek.mirror.dsl;

import net.vidageek.mirror.reflect.dsl.AllMemberHandler;
import net.vidageek.mirror.reflect.dsl.FieldHandler;

/* loaded from: classes4.dex */
public interface FieldController {
    FieldHandler reflect();

    AllMemberHandler reflectAll();
}
