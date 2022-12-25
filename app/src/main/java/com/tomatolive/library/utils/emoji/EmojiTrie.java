package com.tomatolive.library.utils.emoji;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class EmojiTrie {
    private Node root = new Node();

    public EmojiTrie(Collection<Emoji> collection) {
        char[] charArray;
        for (Emoji emoji : collection) {
            Node node = this.root;
            for (char c : emoji.getUnicode().toCharArray()) {
                if (!node.hasChild(c)) {
                    node.addChild(c);
                }
                node = node.getChild(c);
            }
            node.setEmoji(emoji);
        }
    }

    public Matches isEmoji(char[] cArr) {
        if (cArr == null) {
            return Matches.POSSIBLY;
        }
        Node node = this.root;
        for (char c : cArr) {
            if (!node.hasChild(c)) {
                return Matches.IMPOSSIBLE;
            }
            node = node.getChild(c);
        }
        return node.isEndOfEmoji() ? Matches.EXACTLY : Matches.POSSIBLY;
    }

    public Emoji getEmoji(String str) {
        char[] charArray;
        Node node = this.root;
        for (char c : str.toCharArray()) {
            if (!node.hasChild(c)) {
                return null;
            }
            node = node.getChild(c);
        }
        return node.getEmoji();
    }

    /* loaded from: classes4.dex */
    public enum Matches {
        EXACTLY,
        POSSIBLY,
        IMPOSSIBLE;

        public boolean exactMatch() {
            return this == EXACTLY;
        }

        public boolean impossibleMatch() {
            return this == IMPOSSIBLE;
        }
    }

    /* loaded from: classes4.dex */
    private class Node {
        private Map<Character, Node> children;
        private Emoji emoji;

        private Node() {
            this.children = new HashMap();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEmoji(Emoji emoji) {
            this.emoji = emoji;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Emoji getEmoji() {
            return this.emoji;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasChild(char c) {
            return this.children.containsKey(Character.valueOf(c));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChild(char c) {
            this.children.put(Character.valueOf(c), new Node());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Node getChild(char c) {
            return this.children.get(Character.valueOf(c));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEndOfEmoji() {
            return this.emoji != null;
        }
    }
}
