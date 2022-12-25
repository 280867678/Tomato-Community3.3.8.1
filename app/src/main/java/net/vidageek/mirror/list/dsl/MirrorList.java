package net.vidageek.mirror.list.dsl;

import java.util.List;

/* loaded from: classes4.dex */
public interface MirrorList<T> extends List<T> {
    <E> MirrorList<E> mappingTo(Mapper<T, E> mapper);

    MirrorList<T> matching(Matcher<T> matcher);
}
