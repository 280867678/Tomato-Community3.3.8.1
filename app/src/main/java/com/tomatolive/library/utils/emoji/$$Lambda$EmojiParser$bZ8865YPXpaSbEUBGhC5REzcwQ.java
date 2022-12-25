package com.tomatolive.library.utils.emoji;

import com.tomatolive.library.utils.emoji.EmojiParser;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.utils.emoji.-$$Lambda$EmojiParser$bZ8865YPX-paSbEUBGhC5REzcwQ  reason: invalid class name */
/* loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$EmojiParser$bZ8865YPXpaSbEUBGhC5REzcwQ implements EmojiParser.EmojiTransformer {
    public static final /* synthetic */ $$Lambda$EmojiParser$bZ8865YPXpaSbEUBGhC5REzcwQ INSTANCE = new $$Lambda$EmojiParser$bZ8865YPXpaSbEUBGhC5REzcwQ();

    private /* synthetic */ $$Lambda$EmojiParser$bZ8865YPXpaSbEUBGhC5REzcwQ() {
    }

    @Override // com.tomatolive.library.utils.emoji.EmojiParser.EmojiTransformer
    public final String transform(EmojiParser.UnicodeCandidate unicodeCandidate) {
        return EmojiParser.lambda$removeAllEmojis$0(unicodeCandidate);
    }
}
