package kotlin.text;

import java.util.Iterator;
import kotlin.collections.AbstractCollection;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections._Collections;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Ranges;
import kotlin.sequences.Sequence;
import kotlin.sequences._Sequences;

/* compiled from: Regex.kt */
/* loaded from: classes4.dex */
public final class MatcherMatchResult$groups$1 extends AbstractCollection<MatchGroup> implements MatchNamedGroupCollection {
    final /* synthetic */ MatcherMatchResult this$0;

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MatcherMatchResult$groups$1(MatcherMatchResult matcherMatchResult) {
        this.this$0 = matcherMatchResult;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj != null ? obj instanceof MatchGroup : true) {
            return contains((MatchGroup) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(MatchGroup matchGroup) {
        return super.contains((Object) matchGroup);
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        java.util.regex.MatchResult matchResult;
        matchResult = this.this$0.getMatchResult();
        return matchResult.groupCount() + 1;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator<MatchGroup> iterator() {
        Ranges indices;
        Sequence asSequence;
        Sequence map;
        indices = CollectionsKt__CollectionsKt.getIndices(this);
        asSequence = _Collections.asSequence(indices);
        map = _Sequences.map(asSequence, new MatcherMatchResult$groups$1$iterator$1(this));
        return map.iterator();
    }

    public MatchGroup get(int i) {
        java.util.regex.MatchResult matchResult;
        Ranges range;
        java.util.regex.MatchResult matchResult2;
        matchResult = this.this$0.getMatchResult();
        range = RegexKt.range(matchResult, i);
        if (range.getStart().intValue() >= 0) {
            matchResult2 = this.this$0.getMatchResult();
            String group = matchResult2.group(i);
            Intrinsics.checkExpressionValueIsNotNull(group, "matchResult.group(index)");
            return new MatchGroup(group, range);
        }
        return null;
    }
}
