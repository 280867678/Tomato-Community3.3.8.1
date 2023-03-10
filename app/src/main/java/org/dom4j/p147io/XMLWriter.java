package org.dom4j.p147io;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;
import org.dom4j.tree.NamespaceStack;
import org.dom4j.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.XMLFilterImpl;

/* renamed from: org.dom4j.io.XMLWriter */
/* loaded from: classes4.dex */
public class XMLWriter extends XMLFilterImpl implements LexicalHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String PAD_TEXT = " ";
    private boolean autoFlush;
    private StringBuffer buffer;
    private boolean charsAdded;
    private boolean escapeText;
    private OutputFormat format;
    private boolean inDTD;
    private int indentLevel;
    private char lastChar;
    private boolean lastElementClosed;
    protected int lastOutputNodeType;
    private LexicalHandler lexicalHandler;
    private int maximumAllowedCharacter;
    private NamespaceStack namespaceStack;
    private Map<String, String> namespacesMap;
    protected boolean preserve;
    private boolean resolveEntityRefs;
    private boolean showCommentsInDTDs;
    protected Writer writer;
    protected static final String[] LEXICAL_HANDLER_NAMES = {"http://xml.org/sax/properties/lexical-handler", "http://xml.org/sax/handlers/LexicalHandler"};
    protected static final OutputFormat DEFAULT_FORMAT = new OutputFormat();

    public XMLWriter(Writer writer) {
        this(writer, DEFAULT_FORMAT);
    }

    public XMLWriter(Writer writer, OutputFormat outputFormat) {
        this.resolveEntityRefs = true;
        this.lastElementClosed = false;
        this.preserve = false;
        this.namespaceStack = new NamespaceStack();
        this.escapeText = true;
        this.indentLevel = 0;
        this.buffer = new StringBuffer();
        this.charsAdded = false;
        this.writer = writer;
        this.format = outputFormat;
        this.namespaceStack.push(Namespace.NO_NAMESPACE);
    }

    public XMLWriter() {
        this.resolveEntityRefs = true;
        this.lastElementClosed = false;
        this.preserve = false;
        this.namespaceStack = new NamespaceStack();
        this.escapeText = true;
        this.indentLevel = 0;
        this.buffer = new StringBuffer();
        this.charsAdded = false;
        this.format = DEFAULT_FORMAT;
        this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
        this.autoFlush = true;
        this.namespaceStack.push(Namespace.NO_NAMESPACE);
    }

    public XMLWriter(OutputStream outputStream) throws UnsupportedEncodingException {
        this.resolveEntityRefs = true;
        this.lastElementClosed = false;
        this.preserve = false;
        this.namespaceStack = new NamespaceStack();
        this.escapeText = true;
        this.indentLevel = 0;
        this.buffer = new StringBuffer();
        this.charsAdded = false;
        this.format = DEFAULT_FORMAT;
        this.writer = createWriter(outputStream, this.format.getEncoding());
        this.autoFlush = true;
        this.namespaceStack.push(Namespace.NO_NAMESPACE);
    }

    public XMLWriter(OutputStream outputStream, OutputFormat outputFormat) throws UnsupportedEncodingException {
        this.resolveEntityRefs = true;
        this.lastElementClosed = false;
        this.preserve = false;
        this.namespaceStack = new NamespaceStack();
        this.escapeText = true;
        this.indentLevel = 0;
        this.buffer = new StringBuffer();
        this.charsAdded = false;
        this.format = outputFormat;
        this.writer = createWriter(outputStream, outputFormat.getEncoding());
        this.autoFlush = true;
        this.namespaceStack.push(Namespace.NO_NAMESPACE);
    }

    public XMLWriter(OutputFormat outputFormat) throws UnsupportedEncodingException {
        this.resolveEntityRefs = true;
        this.lastElementClosed = false;
        this.preserve = false;
        this.namespaceStack = new NamespaceStack();
        this.escapeText = true;
        this.indentLevel = 0;
        this.buffer = new StringBuffer();
        this.charsAdded = false;
        this.format = outputFormat;
        this.writer = createWriter(System.out, outputFormat.getEncoding());
        this.autoFlush = true;
        this.namespaceStack.push(Namespace.NO_NAMESPACE);
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
        this.autoFlush = false;
    }

    public void setOutputStream(OutputStream outputStream) throws UnsupportedEncodingException {
        this.writer = createWriter(outputStream, this.format.getEncoding());
        this.autoFlush = true;
    }

    public boolean isEscapeText() {
        return this.escapeText;
    }

    public void setEscapeText(boolean z) {
        this.escapeText = z;
    }

    public void setIndentLevel(int i) {
        this.indentLevel = i;
    }

    public int getMaximumAllowedCharacter() {
        if (this.maximumAllowedCharacter == 0) {
            this.maximumAllowedCharacter = defaultMaximumAllowedCharacter();
        }
        return this.maximumAllowedCharacter;
    }

    public void setMaximumAllowedCharacter(int i) {
        this.maximumAllowedCharacter = i;
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public void println() throws IOException {
        this.writer.write(this.format.getLineSeparator());
    }

    public void write(Attribute attribute) throws IOException {
        writeAttribute(attribute);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Document document) throws IOException {
        writeDeclaration();
        if (document.getDocType() != null) {
            indent();
            writeDocType(document.getDocType());
        }
        int nodeCount = document.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            writeNode(document.node(i));
        }
        writePrintln();
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Element element) throws IOException {
        writeElement(element);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(CDATA cdata) throws IOException {
        writeCDATA(cdata.getText());
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Comment comment) throws IOException {
        writeComment(comment.getText());
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(DocumentType documentType) throws IOException {
        writeDocType(documentType);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Entity entity) throws IOException {
        writeEntity(entity);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Namespace namespace) throws IOException {
        writeNamespace(namespace);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(ProcessingInstruction processingInstruction) throws IOException {
        writeProcessingInstruction(processingInstruction);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(String str) throws IOException {
        writeString(str);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Text text) throws IOException {
        writeString(text.getText());
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Node node) throws IOException {
        writeNode(node);
        if (this.autoFlush) {
            flush();
        }
    }

    public void write(Object obj) throws IOException {
        if (obj instanceof Node) {
            write((Node) obj);
        } else if (obj instanceof String) {
            write((String) obj);
        } else if (obj instanceof List) {
            for (Object obj2 : (List) obj) {
                write(obj2);
            }
        } else if (obj != null) {
            throw new IOException("Invalid object: " + obj);
        }
    }

    public void writeOpen(Element element) throws IOException {
        this.writer.write(SimpleComparison.LESS_THAN_OPERATION);
        this.writer.write(element.getQualifiedName());
        writeNamespaces(element);
        writeAttributes(element);
        this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
    }

    public void writeClose(Element element) throws IOException {
        writeClose(element.getQualifiedName());
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.XMLReader
    public void parse(InputSource inputSource) throws IOException, SAXException {
        installLexicalHandler();
        super.parse(inputSource);
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.XMLReader
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        for (String str2 : LEXICAL_HANDLER_NAMES) {
            if (str2.equals(str)) {
                setLexicalHandler((LexicalHandler) obj);
                return;
            }
        }
        super.setProperty(str, obj);
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.XMLReader
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        for (String str2 : LEXICAL_HANDLER_NAMES) {
            if (str2.equals(str)) {
                return getLexicalHandler();
            }
        }
        return super.getProperty(str);
    }

    public void setLexicalHandler(LexicalHandler lexicalHandler) {
        if (lexicalHandler == null) {
            throw new NullPointerException("Null lexical handler");
        }
        this.lexicalHandler = lexicalHandler;
    }

    public LexicalHandler getLexicalHandler() {
        return this.lexicalHandler;
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        try {
            writeDeclaration();
            super.startDocument();
        } catch (IOException e) {
            handleException(e);
        }
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        super.endDocument();
        if (this.autoFlush) {
            try {
                flush();
            } catch (IOException unused) {
            }
        }
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void startPrefixMapping(String str, String str2) throws SAXException {
        if (this.namespacesMap == null) {
            this.namespacesMap = new HashMap();
        }
        this.namespacesMap.put(str, str2);
        super.startPrefixMapping(str, str2);
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void endPrefixMapping(String str) throws SAXException {
        super.endPrefixMapping(str);
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        try {
            this.charsAdded = false;
            writePrintln();
            indent();
            this.writer.write(SimpleComparison.LESS_THAN_OPERATION);
            this.writer.write(str3);
            writeNamespaces();
            writeAttributes(attributes);
            this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
            this.indentLevel++;
            this.lastOutputNodeType = 1;
            this.lastElementClosed = false;
            super.startElement(str, str2, str3, attributes);
        } catch (IOException e) {
            handleException(e);
        }
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        try {
            this.charsAdded = false;
            this.indentLevel--;
            if (this.lastElementClosed) {
                writePrintln();
                indent();
            }
            writeClose(str3);
            this.lastOutputNodeType = 1;
            this.lastElementClosed = true;
            super.endElement(str, str2, str3);
        } catch (IOException e) {
            handleException(e);
        }
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        if (cArr == null || cArr.length == 0 || i2 <= 0) {
            return;
        }
        try {
            String valueOf = String.valueOf(cArr, i, i2);
            if (this.escapeText) {
                valueOf = escapeElementEntities(valueOf);
            }
            if (this.format.isTrimText()) {
                if (this.lastOutputNodeType == 3 && !this.charsAdded) {
                    this.writer.write(32);
                } else if (this.charsAdded && Character.isWhitespace(this.lastChar)) {
                    this.writer.write(32);
                } else if (this.lastOutputNodeType == 1 && this.format.isPadText() && this.lastElementClosed && Character.isWhitespace(cArr[0])) {
                    this.writer.write(" ");
                }
                String str = "";
                StringTokenizer stringTokenizer = new StringTokenizer(valueOf);
                while (stringTokenizer.hasMoreTokens()) {
                    this.writer.write(str);
                    this.writer.write(stringTokenizer.nextToken());
                    str = " ";
                }
            } else {
                this.writer.write(valueOf);
            }
            this.charsAdded = true;
            this.lastChar = cArr[(i + i2) - 1];
            this.lastOutputNodeType = 3;
            super.characters(cArr, i, i2);
        } catch (IOException e) {
            handleException(e);
        }
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        super.ignorableWhitespace(cArr, i, i2);
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.ContentHandler
    public void processingInstruction(String str, String str2) throws SAXException {
        try {
            indent();
            this.writer.write("<?");
            this.writer.write(str);
            this.writer.write(" ");
            this.writer.write(str2);
            this.writer.write("?>");
            writePrintln();
            this.lastOutputNodeType = 7;
            super.processingInstruction(str, str2);
        } catch (IOException e) {
            handleException(e);
        }
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.DTDHandler
    public void notationDecl(String str, String str2, String str3) throws SAXException {
        super.notationDecl(str, str2, str3);
    }

    @Override // org.xml.sax.helpers.XMLFilterImpl, org.xml.sax.DTDHandler
    public void unparsedEntityDecl(String str, String str2, String str3, String str4) throws SAXException {
        super.unparsedEntityDecl(str, str2, str3, str4);
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startDTD(String str, String str2, String str3) throws SAXException {
        this.inDTD = true;
        try {
            writeDocType(str, str2, str3);
        } catch (IOException e) {
            handleException(e);
        }
        LexicalHandler lexicalHandler = this.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startDTD(str, str2, str3);
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endDTD() throws SAXException {
        this.inDTD = false;
        LexicalHandler lexicalHandler = this.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endDTD();
        }
    }

    public void startCDATA() throws SAXException {
        try {
            this.writer.write("<![CDATA[");
        } catch (IOException e) {
            handleException(e);
        }
        LexicalHandler lexicalHandler = this.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startCDATA();
        }
    }

    public void endCDATA() throws SAXException {
        try {
            this.writer.write("]]>");
        } catch (IOException e) {
            handleException(e);
        }
        LexicalHandler lexicalHandler = this.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endCDATA();
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startEntity(String str) throws SAXException {
        try {
            writeEntityRef(str);
        } catch (IOException e) {
            handleException(e);
        }
        LexicalHandler lexicalHandler = this.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startEntity(str);
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endEntity(String str) throws SAXException {
        LexicalHandler lexicalHandler = this.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endEntity(str);
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void comment(char[] cArr, int i, int i2) throws SAXException {
        if (this.showCommentsInDTDs || !this.inDTD) {
            try {
                this.charsAdded = false;
                writeComment(new String(cArr, i, i2));
            } catch (IOException e) {
                handleException(e);
            }
        }
        LexicalHandler lexicalHandler = this.lexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.comment(cArr, i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void writeElement(Element element) throws IOException {
        int nodeCount = element.nodeCount();
        String qualifiedName = element.getQualifiedName();
        writePrintln();
        indent();
        this.writer.write(SimpleComparison.LESS_THAN_OPERATION);
        this.writer.write(qualifiedName);
        int size = this.namespaceStack.size();
        Namespace namespace = element.getNamespace();
        if (isNamespaceDeclaration(namespace)) {
            this.namespaceStack.push(namespace);
            writeNamespace(namespace);
        }
        boolean z = true;
        for (int i = 0; i < nodeCount; i++) {
            Node node = element.node(i);
            if (node instanceof Namespace) {
                Namespace namespace2 = (Namespace) node;
                if (isNamespaceDeclaration(namespace2)) {
                    this.namespaceStack.push(namespace2);
                    writeNamespace(namespace2);
                }
            } else if ((node instanceof Element) || (node instanceof Comment)) {
                z = false;
            }
        }
        writeAttributes(element);
        this.lastOutputNodeType = 1;
        if (nodeCount <= 0) {
            writeEmptyElementClose(qualifiedName);
        } else {
            this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
            if (z) {
                writeElementContent(element);
            } else {
                this.indentLevel++;
                writeElementContent(element);
                this.indentLevel--;
                writePrintln();
                indent();
            }
            this.writer.write("</");
            this.writer.write(qualifiedName);
            this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
        }
        while (this.namespaceStack.size() > size) {
            this.namespaceStack.pop();
        }
        this.lastOutputNodeType = 1;
    }

    protected final boolean isElementSpacePreserved(Element element) {
        Attribute mo6817attribute = element.mo6817attribute("space");
        return mo6817attribute != null ? "xml".equals(mo6817attribute.getNamespacePrefix()) && "preserve".equals(mo6817attribute.getText()) : this.preserve;
    }

    protected void writeElementContent(Element element) throws IOException {
        boolean startsWithWhitespace;
        boolean endsWithWhitespace;
        boolean startsWithWhitespace2;
        boolean isTrimText = this.format.isTrimText();
        boolean z = this.preserve;
        boolean z2 = true;
        if (isTrimText) {
            this.preserve = isElementSpacePreserved(element);
            isTrimText = !this.preserve;
        }
        if (isTrimText) {
            Text text = null;
            StringBuilder sb = null;
            for (Node node : element.content()) {
                if (!(node instanceof Text)) {
                    if (!z2 && this.format.isPadText()) {
                        if (sb != null) {
                            startsWithWhitespace2 = StringUtils.startsWithWhitespace(sb);
                        } else {
                            startsWithWhitespace2 = text != null ? StringUtils.startsWithWhitespace(text.getText()) : false;
                        }
                        if (startsWithWhitespace2) {
                            this.writer.write(" ");
                        }
                    }
                    if (text != null) {
                        if (sb != null) {
                            writeString(sb.toString());
                            sb = null;
                        } else {
                            writeString(text.getText());
                        }
                        if (this.format.isPadText()) {
                            if (sb != null) {
                                endsWithWhitespace = StringUtils.endsWithWhitespace(sb);
                            } else {
                                endsWithWhitespace = StringUtils.endsWithWhitespace(text.getText());
                            }
                            if (endsWithWhitespace) {
                                this.writer.write(" ");
                            }
                        }
                        text = null;
                    }
                    writeNode(node);
                    z2 = false;
                } else if (text == null) {
                    text = (Text) node;
                } else {
                    if (sb == null) {
                        sb = new StringBuilder(text.getText());
                    }
                    sb.append(node.getText());
                }
            }
            if (text != null) {
                if (!z2 && this.format.isPadText()) {
                    if (sb != null) {
                        startsWithWhitespace = StringUtils.startsWithWhitespace(sb);
                    } else {
                        startsWithWhitespace = StringUtils.startsWithWhitespace(text.getText());
                    }
                    if (startsWithWhitespace) {
                        this.writer.write(" ");
                    }
                }
                if (sb != null) {
                    writeString(sb.toString());
                } else {
                    writeString(text.getText());
                }
            }
        } else {
            loop1: while (true) {
                Node node2 = null;
                for (Node node3 : element.content()) {
                    if (node3 instanceof Text) {
                        writeNode(node3);
                        node2 = node3;
                    } else {
                        if (node2 != null && this.format.isPadText() && StringUtils.endsWithWhitespace(node2.getText())) {
                            this.writer.write(" ");
                        }
                        writeNode(node3);
                    }
                }
                break loop1;
            }
        }
        this.preserve = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void writeCDATA(String str) throws IOException {
        this.writer.write("<![CDATA[");
        if (str != null) {
            this.writer.write(str);
        }
        this.writer.write("]]>");
        this.lastOutputNodeType = 4;
    }

    protected void writeDocType(DocumentType documentType) throws IOException {
        if (documentType != null) {
            documentType.write(this.writer);
            writePrintln();
        }
    }

    protected void writeNamespace(Namespace namespace) throws IOException {
        if (namespace != null) {
            writeNamespace(namespace.getPrefix(), namespace.getURI());
        }
    }

    protected void writeNamespaces() throws IOException {
        Map<String, String> map = this.namespacesMap;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writeNamespace(entry.getKey(), entry.getValue());
            }
            this.namespacesMap = null;
        }
    }

    protected void writeNamespace(String str, String str2) throws IOException {
        if (str != null && str.length() > 0) {
            this.writer.write(" xmlns:");
            this.writer.write(str);
            this.writer.write("=\"");
        } else {
            this.writer.write(" xmlns=\"");
        }
        this.writer.write(str2);
        this.writer.write("\"");
    }

    protected void writeNamespaces(Element element) throws IOException {
        for (Namespace namespace : element.declaredNamespaces()) {
            writeNamespace(namespace);
            this.namespaceStack.push(namespace);
        }
    }

    protected void writeProcessingInstruction(ProcessingInstruction processingInstruction) throws IOException {
        this.writer.write("<?");
        this.writer.write(processingInstruction.getName());
        this.writer.write(" ");
        this.writer.write(processingInstruction.getText());
        this.writer.write("?>");
        writePrintln();
        this.lastOutputNodeType = 7;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void writeString(String str) throws IOException {
        if (str == null || str.length() <= 0) {
            return;
        }
        if (this.escapeText) {
            str = escapeElementEntities(str);
        }
        if (this.format.isTrimText()) {
            boolean z = true;
            StringTokenizer stringTokenizer = new StringTokenizer(str);
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                if (z) {
                    z = false;
                    if (this.lastOutputNodeType == 3) {
                        this.writer.write(" ");
                    }
                } else {
                    this.writer.write(" ");
                }
                this.writer.write(nextToken);
                this.lastOutputNodeType = 3;
            }
            return;
        }
        this.lastOutputNodeType = 3;
        this.writer.write(str);
    }

    protected void writeNodeText(Node node) throws IOException {
        String text = node.getText();
        if (text == null || text.length() <= 0) {
            return;
        }
        if (this.escapeText) {
            text = escapeElementEntities(text);
        }
        this.lastOutputNodeType = 3;
        this.writer.write(text);
    }

    protected void writeNode(Node node) throws IOException {
        switch (node.getNodeType()) {
            case 1:
                writeElement((Element) node);
                return;
            case 2:
                writeAttribute((Attribute) node);
                return;
            case 3:
                writeNodeText(node);
                return;
            case 4:
                writeCDATA(node.getText());
                return;
            case 5:
                writeEntity((Entity) node);
                return;
            case 6:
            case 11:
            case 12:
            default:
                throw new IOException("Invalid node type: " + node);
            case 7:
                writeProcessingInstruction((ProcessingInstruction) node);
                return;
            case 8:
                writeComment(node.getText());
                return;
            case 9:
                write((Document) node);
                return;
            case 10:
                writeDocType((DocumentType) node);
                return;
            case 13:
                return;
        }
    }

    protected void installLexicalHandler() {
        XMLReader parent = getParent();
        if (parent == null) {
            throw new NullPointerException("No parent for filter");
        }
        for (String str : LEXICAL_HANDLER_NAMES) {
            try {
                parent.setProperty(str, this);
                return;
            } catch (SAXNotRecognizedException | SAXNotSupportedException unused) {
            }
        }
    }

    protected void writeDocType(String str, String str2, String str3) throws IOException {
        boolean z;
        this.writer.write("<!DOCTYPE ");
        this.writer.write(str);
        if (str2 == null || str2.equals("")) {
            z = false;
        } else {
            this.writer.write(" PUBLIC \"");
            this.writer.write(str2);
            this.writer.write("\"");
            z = true;
        }
        if (str3 != null && !str3.equals("")) {
            if (!z) {
                this.writer.write(" SYSTEM");
            }
            this.writer.write(" \"");
            this.writer.write(str3);
            this.writer.write("\"");
        }
        this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
        writePrintln();
    }

    protected void writeEntity(Entity entity) throws IOException {
        if (!resolveEntityRefs()) {
            writeEntityRef(entity.getName());
        } else {
            this.writer.write(entity.getText());
        }
    }

    protected void writeEntityRef(String str) throws IOException {
        this.writer.write("&");
        this.writer.write(str);
        this.writer.write(";");
        this.lastOutputNodeType = 5;
    }

    protected void writeComment(String str) throws IOException {
        if (this.format.isNewlines()) {
            println();
            indent();
        }
        this.writer.write("<!--");
        this.writer.write(str);
        this.writer.write("-->");
        this.lastOutputNodeType = 8;
    }

    protected void writeAttributes(Element element) throws IOException {
        int attributeCount = element.attributeCount();
        for (int i = 0; i < attributeCount; i++) {
            Attribute attribute = element.attribute(i);
            Namespace namespace = attribute.getNamespace();
            if (namespace != null && namespace != Namespace.NO_NAMESPACE && namespace != Namespace.XML_NAMESPACE) {
                if (!namespace.getURI().equals(this.namespaceStack.getURI(namespace.getPrefix()))) {
                    writeNamespace(namespace);
                    this.namespaceStack.push(namespace);
                }
            }
            String name = attribute.getName();
            if (name.startsWith("xmlns:")) {
                String substring = name.substring(6);
                if (this.namespaceStack.getNamespaceForPrefix(substring) == null) {
                    String value = attribute.getValue();
                    this.namespaceStack.push(substring, value);
                    writeNamespace(substring, value);
                }
            } else if (name.equals("xmlns")) {
                if (this.namespaceStack.getDefaultNamespace() == null) {
                    String value2 = attribute.getValue();
                    this.namespaceStack.push(null, value2);
                    writeNamespace(null, value2);
                }
            } else {
                char attributeQuoteCharacter = this.format.getAttributeQuoteCharacter();
                this.writer.write(" ");
                this.writer.write(attribute.getQualifiedName());
                this.writer.write(SimpleComparison.EQUAL_TO_OPERATION);
                this.writer.write(attributeQuoteCharacter);
                writeEscapeAttributeEntities(attribute.getValue());
                this.writer.write(attributeQuoteCharacter);
            }
        }
    }

    protected void writeAttribute(Attribute attribute) throws IOException {
        this.writer.write(" ");
        this.writer.write(attribute.getQualifiedName());
        this.writer.write(SimpleComparison.EQUAL_TO_OPERATION);
        char attributeQuoteCharacter = this.format.getAttributeQuoteCharacter();
        this.writer.write(attributeQuoteCharacter);
        writeEscapeAttributeEntities(attribute.getValue());
        this.writer.write(attributeQuoteCharacter);
        this.lastOutputNodeType = 2;
    }

    protected void writeAttributes(Attributes attributes) throws IOException {
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            writeAttribute(attributes, i);
        }
    }

    protected void writeAttribute(Attributes attributes, int i) throws IOException {
        char attributeQuoteCharacter = this.format.getAttributeQuoteCharacter();
        this.writer.write(" ");
        this.writer.write(attributes.getQName(i));
        this.writer.write(SimpleComparison.EQUAL_TO_OPERATION);
        this.writer.write(attributeQuoteCharacter);
        writeEscapeAttributeEntities(attributes.getValue(i));
        this.writer.write(attributeQuoteCharacter);
    }

    protected void indent() throws IOException {
        String indent = this.format.getIndent();
        if (indent == null || indent.length() <= 0) {
            return;
        }
        for (int i = 0; i < this.indentLevel; i++) {
            this.writer.write(indent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void writePrintln() throws IOException {
        if (this.format.isNewlines()) {
            this.writer.write(this.format.getLineSeparator());
        }
    }

    protected Writer createWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return new BufferedWriter(new OutputStreamWriter(outputStream, str));
    }

    protected void writeDeclaration() throws IOException {
        String encoding = this.format.getEncoding();
        if (!this.format.isSuppressDeclaration()) {
            if (encoding.equals("UTF8")) {
                this.writer.write("<?xml version=\"1.0\"");
                if (!this.format.isOmitEncoding()) {
                    this.writer.write(" encoding=\"UTF-8\"");
                }
                this.writer.write("?>");
            } else {
                this.writer.write("<?xml version=\"1.0\"");
                if (!this.format.isOmitEncoding()) {
                    Writer writer = this.writer;
                    writer.write(" encoding=\"" + encoding + "\"");
                }
                this.writer.write("?>");
            }
            if (!this.format.isNewLineAfterDeclaration()) {
                return;
            }
            println();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void writeClose(String str) throws IOException {
        this.writer.write("</");
        this.writer.write(str);
        this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void writeEmptyElementClose(String str) throws IOException {
        if (!this.format.isExpandEmptyElements()) {
            this.writer.write("/>");
            return;
        }
        this.writer.write("></");
        this.writer.write(str);
        this.writer.write(SimpleComparison.GREATER_THAN_OPERATION);
    }

    protected boolean isExpandEmptyElements() {
        return this.format.isExpandEmptyElements();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0082 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected String escapeElementEntities(String str) {
        String valueOf;
        int length = str.length();
        char[] cArr = null;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int codePointAt = str.codePointAt(i);
            if (codePointAt == 9 || codePointAt == 10 || codePointAt == 13) {
                if (this.preserve) {
                    valueOf = String.valueOf((char) codePointAt);
                    if (valueOf != null) {
                        if (cArr == null) {
                            cArr = str.toCharArray();
                        }
                        this.buffer.append(cArr, i2, i - i2);
                        this.buffer.append(valueOf);
                        i2 = i + 1;
                        if (Character.isSupplementaryCodePoint(codePointAt)) {
                            i2++;
                        }
                    }
                    if (Character.isSupplementaryCodePoint(codePointAt)) {
                        i++;
                    }
                    i++;
                }
                valueOf = null;
                if (valueOf != null) {
                }
                if (Character.isSupplementaryCodePoint(codePointAt)) {
                }
                i++;
            } else {
                if (codePointAt == 38) {
                    valueOf = "&amp;";
                } else if (codePointAt == 60) {
                    valueOf = "&lt;";
                } else if (codePointAt != 62) {
                    if (codePointAt < 32 || shouldEncodeChar(codePointAt)) {
                        valueOf = "&#" + codePointAt + ";";
                    }
                    valueOf = null;
                } else {
                    valueOf = "&gt;";
                }
                if (valueOf != null) {
                }
                if (Character.isSupplementaryCodePoint(codePointAt)) {
                }
                i++;
            }
        }
        if (i2 == 0) {
            return str;
        }
        if (i2 < length) {
            if (cArr == null) {
                cArr = str.toCharArray();
            }
            this.buffer.append(cArr, i2, i - i2);
        }
        String stringBuffer = this.buffer.toString();
        this.buffer.setLength(0);
        return stringBuffer;
    }

    protected void writeEscapeAttributeEntities(String str) throws IOException {
        if (str != null) {
            this.writer.write(escapeAttributeEntities(str));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0090 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected String escapeAttributeEntities(String str) {
        String str2;
        char attributeQuoteCharacter = this.format.getAttributeQuoteCharacter();
        int length = str.length();
        char[] cArr = null;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int codePointAt = str.codePointAt(i);
            if (codePointAt != 9 && codePointAt != 10 && codePointAt != 13) {
                if (codePointAt != 34) {
                    if (codePointAt == 60) {
                        str2 = "&lt;";
                    } else if (codePointAt == 62) {
                        str2 = "&gt;";
                    } else if (codePointAt == 38) {
                        str2 = "&amp;";
                    } else if (codePointAt != 39) {
                        if (codePointAt < 32 || shouldEncodeChar(codePointAt)) {
                            str2 = "&#" + codePointAt + ";";
                        }
                    } else if (attributeQuoteCharacter == '\'') {
                        str2 = "&apos;";
                    }
                } else if (attributeQuoteCharacter == '\"') {
                    str2 = "&quot;";
                }
                if (str2 != null) {
                    if (cArr == null) {
                        cArr = str.toCharArray();
                    }
                    this.buffer.append(cArr, i2, i - i2);
                    this.buffer.append(str2);
                    i2 = i + 1;
                    if (Character.isSupplementaryCodePoint(codePointAt)) {
                        i2++;
                    }
                }
                if (!Character.isSupplementaryCodePoint(codePointAt)) {
                    i++;
                }
                i++;
            }
            str2 = null;
            if (str2 != null) {
            }
            if (!Character.isSupplementaryCodePoint(codePointAt)) {
            }
            i++;
        }
        if (i2 == 0) {
            return str;
        }
        if (i2 < length) {
            if (cArr == null) {
                cArr = str.toCharArray();
            }
            this.buffer.append(cArr, i2, i - i2);
        }
        String stringBuffer = this.buffer.toString();
        this.buffer.setLength(0);
        return stringBuffer;
    }

    protected boolean shouldEncodeChar(int i) {
        int maximumAllowedCharacter = getMaximumAllowedCharacter();
        return maximumAllowedCharacter > 0 && i > maximumAllowedCharacter;
    }

    protected int defaultMaximumAllowedCharacter() {
        String encoding = this.format.getEncoding();
        return (encoding == null || !encoding.equals("US-ASCII")) ? -1 : 127;
    }

    protected boolean isNamespaceDeclaration(Namespace namespace) {
        return (namespace == null || namespace == Namespace.XML_NAMESPACE || namespace.getURI() == null || this.namespaceStack.contains(namespace)) ? false : true;
    }

    protected void handleException(IOException iOException) throws SAXException {
        throw new SAXException(iOException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public OutputFormat getOutputFormat() {
        return this.format;
    }

    public boolean resolveEntityRefs() {
        return this.resolveEntityRefs;
    }

    public void setResolveEntityRefs(boolean z) {
        this.resolveEntityRefs = z;
    }
}
