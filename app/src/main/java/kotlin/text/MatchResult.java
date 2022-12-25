package kotlin.text;

import kotlin.ranges.Ranges;

/* compiled from: MatchResult.kt */
/* loaded from: classes4.dex */
public interface MatchResult {
    Ranges getRange();

    MatchResult next();
}
