package org.jsoup.nodes;

import java.io.IOException;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

/* loaded from: classes4.dex */
public class TextNode extends LeafNode {
    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#text";
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) {
    }

    public TextNode(String str) {
        this.value = str;
    }

    public String getWholeText() {
        return coreValue();
    }

    public boolean isBlank() {
        return StringUtil.isBlank(coreValue());
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0022, code lost:
        if (isBlank() == false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x003a, code lost:
        indent(r10, r11, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0038, code lost:
        if (isBlank() == false) goto L11;
     */
    @Override // org.jsoup.nodes.Node
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        boolean prettyPrint = outputSettings.prettyPrint();
        if (prettyPrint) {
            if (siblingIndex() == 0) {
                Node node = this.parentNode;
                if (node instanceof Element) {
                    if (((Element) node).tag().formatAsBlock()) {
                    }
                }
            }
            if (outputSettings.outline()) {
                if (siblingNodes().size() > 0) {
                }
            }
        }
        Entities.escape(appendable, coreValue(), outputSettings, false, prettyPrint && !Element.preserveWhitespace(this.parentNode), prettyPrint && (this.parentNode instanceof Document));
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }

    @Override // org.jsoup.nodes.Node
    /* renamed from: clone */
    public TextNode mo6842clone() {
        return (TextNode) super.mo6842clone();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean lastCharIsWhitespace(StringBuilder sb) {
        return sb.length() != 0 && sb.charAt(sb.length() - 1) == ' ';
    }
}
