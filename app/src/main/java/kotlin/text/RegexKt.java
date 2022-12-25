package kotlin.text;

import java.util.regex.Matcher;
import kotlin.ranges.Ranges;
import kotlin.ranges._Ranges;

/* compiled from: Regex.kt */
/* loaded from: classes4.dex */
public final class RegexKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final MatchResult findNext(Matcher matcher, int i, CharSequence charSequence) {
        if (!matcher.find(i)) {
            return null;
        }
        return new MatcherMatchResult(matcher, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MatchResult matchEntire(Matcher matcher, CharSequence charSequence) {
        if (!matcher.matches()) {
            return null;
        }
        return new MatcherMatchResult(matcher, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ranges range(java.util.regex.MatchResult matchResult) {
        Ranges until;
        until = _Ranges.until(matchResult.start(), matchResult.end());
        return until;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ranges range(java.util.regex.MatchResult matchResult, int i) {
        Ranges until;
        until = _Ranges.until(matchResult.start(i), matchResult.end(i));
        return until;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int toInt(Iterable<? extends FlagEnum> iterable) {
        int i = 0;
        for (FlagEnum flagEnum : iterable) {
            i |= flagEnum.getValue();
        }
        return i;
    }
}
