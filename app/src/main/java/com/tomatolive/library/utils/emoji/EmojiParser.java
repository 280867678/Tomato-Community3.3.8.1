package com.tomatolive.library.utils.emoji;

import com.tomatolive.library.utils.emoji.EmojiTrie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class EmojiParser {
    private static final Pattern ALIAS_CANDIDATE_PATTERN = Pattern.compile("(?<=:)\\+?(\\w|\\||\\-)+(?=:)");

    /* loaded from: classes4.dex */
    public interface EmojiTransformer {
        String transform(UnicodeCandidate unicodeCandidate);
    }

    /* loaded from: classes4.dex */
    public enum FitzpatrickAction {
        PARSE,
        REMOVE,
        IGNORE
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ String lambda$removeAllEmojis$0(UnicodeCandidate unicodeCandidate) {
        return "";
    }

    public static String parseToAliases(String str) {
        return parseToAliases(str, FitzpatrickAction.PARSE);
    }

    public static String parseToAliases(String str, final FitzpatrickAction fitzpatrickAction) {
        return parseFromUnicode(str, new EmojiTransformer() { // from class: com.tomatolive.library.utils.emoji.EmojiParser.1
            @Override // com.tomatolive.library.utils.emoji.EmojiParser.EmojiTransformer
            public String transform(UnicodeCandidate unicodeCandidate) {
                int i = C50386.f5877xbeb798cb[FitzpatrickAction.this.ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        if (i == 3) {
                            return ":" + unicodeCandidate.getEmoji().getAliases().get(0) + ":" + unicodeCandidate.getFitzpatrickUnicode();
                        }
                        return ":" + unicodeCandidate.getEmoji().getAliases().get(0) + "|" + unicodeCandidate.getFitzpatrickType() + ":";
                    }
                } else if (unicodeCandidate.hasFitzpatrick()) {
                    return ":" + unicodeCandidate.getEmoji().getAliases().get(0) + "|" + unicodeCandidate.getFitzpatrickType() + ":";
                }
                return ":" + unicodeCandidate.getEmoji().getAliases().get(0) + ":";
            }
        });
    }

    /* renamed from: com.tomatolive.library.utils.emoji.EmojiParser$6 */
    /* loaded from: classes4.dex */
    static /* synthetic */ class C50386 {

        /* renamed from: $SwitchMap$com$tomatolive$library$utils$emoji$EmojiParser$FitzpatrickAction */
        static final /* synthetic */ int[] f5877xbeb798cb = new int[FitzpatrickAction.values().length];

        static {
            try {
                f5877xbeb798cb[FitzpatrickAction.PARSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f5877xbeb798cb[FitzpatrickAction.REMOVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f5877xbeb798cb[FitzpatrickAction.IGNORE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static String parseToUnicode(String str) {
        for (AliasCandidate aliasCandidate : getAliasCandidates(str)) {
            Emoji forAlias = EmojiManager.getForAlias(aliasCandidate.alias);
            if (forAlias != null && (forAlias.supportsFitzpatrick() || (!forAlias.supportsFitzpatrick() && aliasCandidate.fitzpatrick == null))) {
                String unicode = forAlias.getUnicode();
                if (aliasCandidate.fitzpatrick != null) {
                    unicode = unicode + aliasCandidate.fitzpatrick.unicode;
                }
                str = str.replace(":" + aliasCandidate.fullString + ":", unicode);
            }
        }
        for (Emoji emoji : EmojiManager.getAll()) {
            str = str.replace(emoji.getHtmlHexadecimal(), emoji.getUnicode()).replace(emoji.getHtmlDecimal(), emoji.getUnicode());
        }
        return str;
    }

    protected static List<AliasCandidate> getAliasCandidates(String str) {
        ArrayList arrayList = new ArrayList();
        Matcher useTransparentBounds = ALIAS_CANDIDATE_PATTERN.matcher(str).useTransparentBounds(true);
        while (useTransparentBounds.find()) {
            String group = useTransparentBounds.group();
            if (!group.contains("|")) {
                arrayList.add(new AliasCandidate(group, group, null));
            } else {
                String[] split = group.split("\\|");
                if (split.length == 2 || split.length > 2) {
                    arrayList.add(new AliasCandidate(group, split[0], split[1]));
                } else {
                    arrayList.add(new AliasCandidate(group, group, null));
                }
            }
        }
        return arrayList;
    }

    public static String parseToHtmlDecimal(String str) {
        return parseToHtmlDecimal(str, FitzpatrickAction.PARSE);
    }

    public static String parseToHtmlDecimal(String str, final FitzpatrickAction fitzpatrickAction) {
        return parseFromUnicode(str, new EmojiTransformer() { // from class: com.tomatolive.library.utils.emoji.EmojiParser.2
            @Override // com.tomatolive.library.utils.emoji.EmojiParser.EmojiTransformer
            public String transform(UnicodeCandidate unicodeCandidate) {
                if (C50386.f5877xbeb798cb[FitzpatrickAction.this.ordinal()] != 3) {
                    return unicodeCandidate.getEmoji().getHtmlDecimal();
                }
                return unicodeCandidate.getEmoji().getHtmlDecimal() + unicodeCandidate.getFitzpatrickUnicode();
            }
        });
    }

    public static String parseToHtmlHexadecimal(String str) {
        return parseToHtmlHexadecimal(str, FitzpatrickAction.PARSE);
    }

    public static String parseToHtmlHexadecimal(String str, final FitzpatrickAction fitzpatrickAction) {
        return parseFromUnicode(str, new EmojiTransformer() { // from class: com.tomatolive.library.utils.emoji.EmojiParser.3
            @Override // com.tomatolive.library.utils.emoji.EmojiParser.EmojiTransformer
            public String transform(UnicodeCandidate unicodeCandidate) {
                if (C50386.f5877xbeb798cb[FitzpatrickAction.this.ordinal()] != 3) {
                    return unicodeCandidate.getEmoji().getHtmlHexadecimal();
                }
                return unicodeCandidate.getEmoji().getHtmlHexadecimal() + unicodeCandidate.getFitzpatrickUnicode();
            }
        });
    }

    public static String removeAllEmojis(String str) {
        return parseFromUnicode(str, $$Lambda$EmojiParser$bZ8865YPXpaSbEUBGhC5REzcwQ.INSTANCE);
    }

    public static String removeEmojis(String str, final Collection<Emoji> collection) {
        return parseFromUnicode(str, new EmojiTransformer() { // from class: com.tomatolive.library.utils.emoji.EmojiParser.4
            @Override // com.tomatolive.library.utils.emoji.EmojiParser.EmojiTransformer
            public String transform(UnicodeCandidate unicodeCandidate) {
                if (!collection.contains(unicodeCandidate.getEmoji())) {
                    return unicodeCandidate.getEmoji().getUnicode() + unicodeCandidate.getFitzpatrickUnicode();
                }
                return "";
            }
        });
    }

    public static String removeAllEmojisExcept(String str, final Collection<Emoji> collection) {
        return parseFromUnicode(str, new EmojiTransformer() { // from class: com.tomatolive.library.utils.emoji.EmojiParser.5
            @Override // com.tomatolive.library.utils.emoji.EmojiParser.EmojiTransformer
            public String transform(UnicodeCandidate unicodeCandidate) {
                if (collection.contains(unicodeCandidate.getEmoji())) {
                    return unicodeCandidate.getEmoji().getUnicode() + unicodeCandidate.getFitzpatrickUnicode();
                }
                return "";
            }
        });
    }

    public static String parseFromUnicode(String str, EmojiTransformer emojiTransformer) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (UnicodeCandidate unicodeCandidate : getUnicodeCandidates(str)) {
            sb.append(str.substring(i, unicodeCandidate.getEmojiStartIndex()));
            sb.append(emojiTransformer.transform(unicodeCandidate));
            i = unicodeCandidate.getFitzpatrickEndIndex();
        }
        sb.append(str.substring(i));
        return sb.toString();
    }

    public static List<String> extractEmojis(String str) {
        List<UnicodeCandidate> unicodeCandidates = getUnicodeCandidates(str);
        ArrayList arrayList = new ArrayList();
        for (UnicodeCandidate unicodeCandidate : unicodeCandidates) {
            arrayList.add(unicodeCandidate.getEmoji().getUnicode());
        }
        return arrayList;
    }

    protected static List<UnicodeCandidate> getUnicodeCandidates(String str) {
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            UnicodeCandidate nextUnicodeCandidate = getNextUnicodeCandidate(charArray, i);
            if (nextUnicodeCandidate != null) {
                arrayList.add(nextUnicodeCandidate);
                i = nextUnicodeCandidate.getFitzpatrickEndIndex();
            } else {
                return arrayList;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static UnicodeCandidate getNextUnicodeCandidate(char[] cArr, int i) {
        while (i < cArr.length) {
            int emojiEndPos = getEmojiEndPos(cArr, i);
            if (emojiEndPos != -1) {
                return new UnicodeCandidate(EmojiManager.getByUnicode(new String(cArr, i, emojiEndPos - i)), emojiEndPos + 2 <= cArr.length ? new String(cArr, emojiEndPos, 2) : null, i);
            }
            i++;
        }
        return null;
    }

    protected static int getEmojiEndPos(char[] cArr, int i) {
        int i2 = -1;
        for (int i3 = i + 1; i3 <= cArr.length; i3++) {
            EmojiTrie.Matches isEmoji = EmojiManager.isEmoji(Arrays.copyOfRange(cArr, i, i3));
            if (isEmoji.exactMatch()) {
                i2 = i3;
            } else if (isEmoji.impossibleMatch()) {
                return i2;
            }
        }
        return i2;
    }

    /* loaded from: classes4.dex */
    public static class UnicodeCandidate {
        private final Emoji emoji;
        private final Fitzpatrick fitzpatrick;
        private final int startIndex;

        private UnicodeCandidate(Emoji emoji, String str, int i) {
            this.emoji = emoji;
            this.fitzpatrick = Fitzpatrick.fitzpatrickFromUnicode(str);
            this.startIndex = i;
        }

        public Emoji getEmoji() {
            return this.emoji;
        }

        public boolean hasFitzpatrick() {
            return getFitzpatrick() != null;
        }

        public Fitzpatrick getFitzpatrick() {
            return this.fitzpatrick;
        }

        public String getFitzpatrickType() {
            return hasFitzpatrick() ? this.fitzpatrick.name().toLowerCase() : "";
        }

        public String getFitzpatrickUnicode() {
            return hasFitzpatrick() ? this.fitzpatrick.unicode : "";
        }

        public int getEmojiStartIndex() {
            return this.startIndex;
        }

        public int getEmojiEndIndex() {
            return this.startIndex + this.emoji.getUnicode().length();
        }

        public int getFitzpatrickEndIndex() {
            return getEmojiEndIndex() + (this.fitzpatrick != null ? 2 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public static class AliasCandidate {
        public final String alias;
        public final Fitzpatrick fitzpatrick;
        public final String fullString;

        private AliasCandidate(String str, String str2, String str3) {
            this.fullString = str;
            this.alias = str2;
            if (str3 == null) {
                this.fitzpatrick = null;
            } else {
                this.fitzpatrick = Fitzpatrick.fitzpatrickFromType(str3);
            }
        }
    }
}
