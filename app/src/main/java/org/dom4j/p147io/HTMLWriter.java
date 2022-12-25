package org.dom4j.p147io;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.xml.sax.SAXException;

/* renamed from: org.dom4j.io.HTMLWriter */
/* loaded from: classes4.dex */
public class HTMLWriter extends XMLWriter {
    private HashSet<String> omitElementCloseSet;
    private static String lineSeparator = System.getProperty("line.separator");
    protected static final HashSet<String> DEFAULT_PREFORMATTED_TAGS = new HashSet<>();
    protected static final OutputFormat DEFAULT_HTML_FORMAT = new OutputFormat(ConstantUtils.PLACEHOLDER_STR_TWO, true);
    private Stack<FormatState> formatStack = new Stack<>();
    private String lastText = "";
    private int tagsOuput = 0;
    private int newLineAfterNTags = -1;
    private HashSet<String> preformattedTags = DEFAULT_PREFORMATTED_TAGS;

    @Override // org.dom4j.p147io.XMLWriter, org.xml.sax.ext.LexicalHandler
    public void endCDATA() throws SAXException {
    }

    @Override // org.dom4j.p147io.XMLWriter, org.xml.sax.ext.LexicalHandler
    public void startCDATA() throws SAXException {
    }

    @Override // org.dom4j.p147io.XMLWriter
    protected void writeDeclaration() throws IOException {
    }

    static {
        DEFAULT_PREFORMATTED_TAGS.add("PRE");
        DEFAULT_PREFORMATTED_TAGS.add("SCRIPT");
        DEFAULT_PREFORMATTED_TAGS.add("STYLE");
        DEFAULT_PREFORMATTED_TAGS.add("TEXTAREA");
        DEFAULT_HTML_FORMAT.setTrimText(true);
        DEFAULT_HTML_FORMAT.setSuppressDeclaration(true);
    }

    public HTMLWriter(Writer writer) {
        super(writer, DEFAULT_HTML_FORMAT);
    }

    public HTMLWriter(Writer writer, OutputFormat outputFormat) {
        super(writer, outputFormat);
    }

    public HTMLWriter() throws UnsupportedEncodingException {
        super(DEFAULT_HTML_FORMAT);
    }

    public HTMLWriter(OutputFormat outputFormat) throws UnsupportedEncodingException {
        super(outputFormat);
    }

    public HTMLWriter(OutputStream outputStream) throws UnsupportedEncodingException {
        super(outputStream, DEFAULT_HTML_FORMAT);
    }

    public HTMLWriter(OutputStream outputStream, OutputFormat outputFormat) throws UnsupportedEncodingException {
        super(outputStream, outputFormat);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.p147io.XMLWriter
    public void writeCDATA(String str) throws IOException {
        if (getOutputFormat().isXHTML()) {
            super.writeCDATA(str);
        } else {
            this.writer.write(str);
        }
        this.lastOutputNodeType = 4;
    }

    @Override // org.dom4j.p147io.XMLWriter
    protected void writeEntity(Entity entity) throws IOException {
        this.writer.write(entity.getText());
        this.lastOutputNodeType = 5;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.p147io.XMLWriter
    public void writeString(String str) throws IOException {
        if (str.equals("\n")) {
            if (this.formatStack.empty()) {
                return;
            }
            super.writeString(lineSeparator);
            return;
        }
        this.lastText = str;
        if (this.formatStack.empty()) {
            super.writeString(str.trim());
        } else {
            super.writeString(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.p147io.XMLWriter
    public void writeClose(String str) throws IOException {
        if (!omitElementClose(str)) {
            super.writeClose(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.p147io.XMLWriter
    public void writeEmptyElementClose(String str) throws IOException {
        if (getOutputFormat().isXHTML()) {
            if (omitElementClose(str)) {
                this.writer.write(" />");
            } else {
                super.writeEmptyElementClose(str);
            }
        } else if (omitElementClose(str)) {
            this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
        } else {
            super.writeEmptyElementClose(str);
        }
    }

    protected boolean omitElementClose(String str) {
        return internalGetOmitElementCloseSet().contains(str.toUpperCase());
    }

    private HashSet<String> internalGetOmitElementCloseSet() {
        if (this.omitElementCloseSet == null) {
            this.omitElementCloseSet = new HashSet<>();
            loadOmitElementCloseSet(this.omitElementCloseSet);
        }
        return this.omitElementCloseSet;
    }

    protected void loadOmitElementCloseSet(Set<String> set) {
        set.add("AREA");
        set.add("BASE");
        set.add("BR");
        set.add("COL");
        set.add("HR");
        set.add("IMG");
        set.add("INPUT");
        set.add("LINK");
        set.add("META");
        set.add("P");
        set.add("PARAM");
    }

    public Set<String> getOmitElementCloseSet() {
        return (Set) internalGetOmitElementCloseSet().clone();
    }

    public void setOmitElementCloseSet(Set<String> set) {
        this.omitElementCloseSet = new HashSet<>();
        if (set != null) {
            this.omitElementCloseSet = new HashSet<>();
            for (String str : set) {
                if (str != null) {
                    this.omitElementCloseSet.add(str.toUpperCase());
                }
            }
        }
    }

    public Set<String> getPreformattedTags() {
        return (Set) this.preformattedTags.clone();
    }

    public void setPreformattedTags(Set<String> set) {
        this.preformattedTags = new HashSet<>();
        if (set != null) {
            for (String str : set) {
                if (str != null) {
                    this.preformattedTags.add(str.toUpperCase());
                }
            }
        }
    }

    public boolean isPreformattedTag(String str) {
        HashSet<String> hashSet = this.preformattedTags;
        return hashSet != null && hashSet.contains(str.toUpperCase());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.p147io.XMLWriter
    public void writeElement(Element element) throws IOException {
        int i;
        if (this.newLineAfterNTags == -1) {
            lazyInitNewLinesAfterNTags();
        }
        int i2 = this.newLineAfterNTags;
        if (i2 > 0 && (i = this.tagsOuput) > 0 && i % i2 == 0) {
            this.writer.write(lineSeparator);
        }
        this.tagsOuput++;
        String qualifiedName = element.getQualifiedName();
        String str = this.lastText;
        element.nodeCount();
        if (isPreformattedTag(qualifiedName)) {
            OutputFormat outputFormat = getOutputFormat();
            boolean isNewlines = outputFormat.isNewlines();
            boolean isTrimText = outputFormat.isTrimText();
            String indent = outputFormat.getIndent();
            this.formatStack.push(new FormatState(isNewlines, isTrimText, indent));
            try {
                super.writePrintln();
                if (str.trim().length() == 0 && indent != null && indent.length() > 0) {
                    this.writer.write(justSpaces(str));
                }
                outputFormat.setNewlines(false);
                outputFormat.setTrimText(false);
                outputFormat.setIndent("");
                super.writeElement(element);
                return;
            } finally {
                FormatState pop = this.formatStack.pop();
                outputFormat.setNewlines(pop.isNewlines());
                outputFormat.setTrimText(pop.isTrimText());
                outputFormat.setIndent(pop.getIndent());
            }
        }
        super.writeElement(element);
    }

    private String justSpaces(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != '\n' && charAt != '\r') {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }

    private void lazyInitNewLinesAfterNTags() {
        if (getOutputFormat().isNewlines()) {
            this.newLineAfterNTags = 0;
        } else {
            this.newLineAfterNTags = getOutputFormat().getNewLineAfterNTags();
        }
    }

    public static String prettyPrintHTML(String str) throws IOException, UnsupportedEncodingException, DocumentException {
        return prettyPrintHTML(str, true, true, false, true);
    }

    public static String prettyPrintXHTML(String str) throws IOException, UnsupportedEncodingException, DocumentException {
        return prettyPrintHTML(str, true, true, true, false);
    }

    public static String prettyPrintHTML(String str, boolean z, boolean z2, boolean z3, boolean z4) throws IOException, UnsupportedEncodingException, DocumentException {
        StringWriter stringWriter = new StringWriter();
        OutputFormat createPrettyPrint = OutputFormat.createPrettyPrint();
        createPrettyPrint.setNewlines(z);
        createPrettyPrint.setTrimText(z2);
        createPrettyPrint.setXHTML(z3);
        createPrettyPrint.setExpandEmptyElements(z4);
        HTMLWriter hTMLWriter = new HTMLWriter(stringWriter, createPrettyPrint);
        hTMLWriter.write(DocumentHelper.parseText(str));
        hTMLWriter.flush();
        return stringWriter.toString();
    }

    /* renamed from: org.dom4j.io.HTMLWriter$FormatState */
    /* loaded from: classes4.dex */
    private class FormatState {
        private String indent;
        private boolean newlines;
        private boolean trimText;

        public FormatState(boolean z, boolean z2, String str) {
            this.newlines = false;
            this.trimText = false;
            this.indent = "";
            this.newlines = z;
            this.trimText = z2;
            this.indent = str;
        }

        public boolean isNewlines() {
            return this.newlines;
        }

        public boolean isTrimText() {
            return this.trimText;
        }

        public String getIndent() {
            return this.indent;
        }
    }
}
