package kotlin.text;

import java.util.regex.Matcher;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Ranges;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Regex.kt */
/* loaded from: classes4.dex */
public final class MatcherMatchResult implements MatchResult {
    private final CharSequence input;
    private final Matcher matcher;

    public MatcherMatchResult(Matcher matcher, CharSequence input) {
        Intrinsics.checkParameterIsNotNull(matcher, "matcher");
        Intrinsics.checkParameterIsNotNull(input, "input");
        this.matcher = matcher;
        this.input = input;
        new MatcherMatchResult$groups$1(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final java.util.regex.MatchResult getMatchResult() {
        return this.matcher;
    }

    @Override // kotlin.text.MatchResult
    public Ranges getRange() {
        Ranges range;
        range = RegexKt.range(getMatchResult());
        return range;
    }

    @Override // kotlin.text.MatchResult
    public MatchResult next() {
        MatchResult findNext;
        int end = getMatchResult().end() + (getMatchResult().end() == getMatchResult().start() ? 1 : 0);
        if (end <= this.input.length()) {
            Matcher matcher = this.matcher.pattern().matcher(this.input);
            Intrinsics.checkExpressionValueIsNotNull(matcher, "matcher.pattern().matcher(input)");
            findNext = RegexKt.findNext(matcher, end, this.input);
            return findNext;
        }
        return null;
    }
}
