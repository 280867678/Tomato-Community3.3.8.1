package org.jsoup.nodes;

import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;

/* loaded from: classes4.dex */
public class DocumentType extends LeafNode {
    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#doctype";
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlTail(Appendable appendable, int i, Document.OutputSettings outputSettings) {
    }

    public DocumentType(String str, String str2, String str3) {
        Validate.notNull(str);
        Validate.notNull(str2);
        Validate.notNull(str3);
        attr("name", str);
        attr("publicId", str2);
        attr("systemId", str3);
        updatePubSyskey();
    }

    public void setPubSysKey(String str) {
        if (str != null) {
            attr("pubSysKey", str);
        }
    }

    private void updatePubSyskey() {
        if (has("publicId")) {
            attr("pubSysKey", "PUBLIC");
        } else if (!has("systemId")) {
        } else {
            attr("pubSysKey", "SYSTEM");
        }
    }

    @Override // org.jsoup.nodes.Node
    void outerHtmlHead(Appendable appendable, int i, Document.OutputSettings outputSettings) throws IOException {
        if (outputSettings.syntax() == Document.OutputSettings.Syntax.html && !has("publicId") && !has("systemId")) {
            appendable.append("<!doctype");
        } else {
            appendable.append("<!DOCTYPE");
        }
        if (has("name")) {
            appendable.append(ConstantUtils.PLACEHOLDER_STR_ONE).append(attr("name"));
        }
        if (has("pubSysKey")) {
            appendable.append(ConstantUtils.PLACEHOLDER_STR_ONE).append(attr("pubSysKey"));
        }
        if (has("publicId")) {
            appendable.append(" \"").append(attr("publicId")).append('\"');
        }
        if (has("systemId")) {
            appendable.append(" \"").append(attr("systemId")).append('\"');
        }
        appendable.append('>');
    }

    private boolean has(String str) {
        return !StringUtil.isBlank(attr(str));
    }
}
