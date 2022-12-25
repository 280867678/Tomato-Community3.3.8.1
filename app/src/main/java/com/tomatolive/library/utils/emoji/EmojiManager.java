package com.tomatolive.library.utils.emoji;

import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.utils.emoji.EmojiParser;
import com.tomatolive.library.utils.emoji.EmojiTrie;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class EmojiManager {
    private static final List<Emoji> ALL_EMOJIS;
    private static final Map<String, Emoji> EMOJIS_BY_ALIAS = new HashMap();
    private static final Map<String, Set<Emoji>> EMOJIS_BY_TAG = new HashMap();
    private static final EmojiTrie EMOJI_TRIE;
    private static final String PATH = "emojis.json";

    static {
        try {
            InputStream open = TomatoLiveSDK.getSingleton().getApplication().getAssets().open(PATH);
            List<Emoji> loadEmojis = EmojiLoader.loadEmojis(open);
            ALL_EMOJIS = loadEmojis;
            for (Emoji emoji : loadEmojis) {
                for (String str : emoji.getTags()) {
                    if (EMOJIS_BY_TAG.get(str) == null) {
                        EMOJIS_BY_TAG.put(str, new HashSet());
                    }
                    EMOJIS_BY_TAG.get(str).add(emoji);
                }
                for (String str2 : emoji.getAliases()) {
                    EMOJIS_BY_ALIAS.put(str2, emoji);
                }
            }
            EMOJI_TRIE = new EmojiTrie(loadEmojis);
            open.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private EmojiManager() {
    }

    public static Set<Emoji> getForTag(String str) {
        if (str == null) {
            return null;
        }
        return EMOJIS_BY_TAG.get(str);
    }

    public static Emoji getForAlias(String str) {
        if (str == null) {
            return null;
        }
        return EMOJIS_BY_ALIAS.get(trimAlias(str));
    }

    private static String trimAlias(String str) {
        if (str.startsWith(":")) {
            str = str.substring(1, str.length());
        }
        return str.endsWith(":") ? str.substring(0, str.length() - 1) : str;
    }

    public static Emoji getByUnicode(String str) {
        if (str == null) {
            return null;
        }
        return EMOJI_TRIE.getEmoji(str);
    }

    public static Collection<Emoji> getAll() {
        return ALL_EMOJIS;
    }

    public static boolean isEmoji(String str) {
        EmojiParser.UnicodeCandidate nextUnicodeCandidate;
        return str != null && (nextUnicodeCandidate = EmojiParser.getNextUnicodeCandidate(str.toCharArray(), 0)) != null && nextUnicodeCandidate.getEmojiStartIndex() == 0 && nextUnicodeCandidate.getFitzpatrickEndIndex() == str.length();
    }

    public static boolean isOnlyEmojis(String str) {
        return str != null && EmojiParser.removeAllEmojis(str).isEmpty();
    }

    public static EmojiTrie.Matches isEmoji(char[] cArr) {
        return EMOJI_TRIE.isEmoji(cArr);
    }

    public static Collection<String> getAllTags() {
        return EMOJIS_BY_TAG.keySet();
    }
}
