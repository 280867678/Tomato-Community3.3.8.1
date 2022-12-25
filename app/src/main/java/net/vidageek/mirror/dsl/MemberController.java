package net.vidageek.mirror.dsl;

import net.vidageek.mirror.reflect.dsl.AllMemberHandler;
import net.vidageek.mirror.reflect.dsl.MemberHandler;

/* loaded from: classes4.dex */
public interface MemberController {
    MemberHandler reflect();

    AllMemberHandler reflectAll();
}
