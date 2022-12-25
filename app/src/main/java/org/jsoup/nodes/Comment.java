package org.jsoup.nodes;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/* loaded from: classes4.dex */
public class Comment extends LeafNode {
    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#comment";
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) {
    }

    public Comment(String str) {
        this.value = str;
    }

    public String getData() {
        return coreValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r5.outline() != false) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x001c, code lost:
        if (((org.jsoup.nodes.Element) r0).tag().formatAsBlock() != false) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0024, code lost:
        indent(r3, r4, r5);
     */
    @Override // org.jsoup.nodes.Node
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        if (outputSettings.prettyPrint()) {
            if (siblingIndex() == 0) {
                Node node = this.parentNode;
                if (node instanceof Element) {
                }
            }
        }
        appendable.append("<!--").append(getData()).append("-->");
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }

    @Override // org.jsoup.nodes.Node
    /* renamed from: clone  reason: collision with other method in class */
    public Comment mo6842clone() {
        return (Comment) super.mo6842clone();
    }

    public boolean isXmlDeclaration() {
        String data = getData();
        return data.length() > 1 && (data.startsWith("!") || data.startsWith("?"));
    }

    public XmlDeclaration asXmlDeclaration() {
        String data = getData();
        Document parse = Jsoup.parse(SimpleComparison.LESS_THAN_OPERATION + data.substring(1, data.length() - 1) + SimpleComparison.GREATER_THAN_OPERATION, baseUri(), Parser.xmlParser());
        if (parse.children().size() > 0) {
            Element child = parse.child(0);
            XmlDeclaration xmlDeclaration = new XmlDeclaration(NodeUtils.parser(parse).settings().normalizeTag(child.tagName()), data.startsWith("!"));
            xmlDeclaration.attributes().addAll(child.attributes());
            return xmlDeclaration;
        }
        return null;
    }
}
