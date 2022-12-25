package org.jsoup.parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Token;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public abstract class TreeBuilder {
    protected String baseUri;
    protected Token currentToken;
    protected Document doc;
    protected Parser parser;
    CharacterReader reader;
    protected ParseSettings settings;
    protected ArrayList<Element> stack;
    Tokeniser tokeniser;
    private Token.StartTag start = new Token.StartTag();
    private Token.EndTag end = new Token.EndTag();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract ParseSettings defaultSettings();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract List<Node> parseFragment(String str, Element element, String str2, Parser parser);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean process(Token token);

    /* JADX INFO: Access modifiers changed from: protected */
    public void initialiseParse(Reader reader, String str, Parser parser) {
        Validate.notNull(reader, "String input must not be null");
        Validate.notNull(str, "BaseURI must not be null");
        this.doc = new Document(str);
        this.doc.parser(parser);
        this.parser = parser;
        this.settings = parser.settings();
        this.reader = new CharacterReader(reader);
        this.currentToken = null;
        this.tokeniser = new Tokeniser(this.reader, parser.getErrors());
        this.stack = new ArrayList<>(32);
        this.baseUri = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Document parse(Reader reader, String str, Parser parser) {
        initialiseParse(reader, str, parser);
        runParser();
        this.reader.close();
        this.reader = null;
        this.tokeniser = null;
        this.stack = null;
        return this.doc;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void runParser() {
        Token read;
        Tokeniser tokeniser = this.tokeniser;
        Token.TokenType tokenType = Token.TokenType.EOF;
        do {
            read = tokeniser.read();
            process(read);
            read.mo6845reset();
        } while (read.type != tokenType);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean processStartTag(String str) {
        Token.StartTag startTag = this.start;
        if (this.currentToken == startTag) {
            Token.StartTag startTag2 = new Token.StartTag();
            startTag2.name(str);
            return process(startTag2);
        }
        startTag.mo6845reset();
        startTag.name(str);
        return process(startTag);
    }

    public boolean processStartTag(String str, Attributes attributes) {
        Token.StartTag startTag = this.start;
        if (this.currentToken == startTag) {
            Token.StartTag startTag2 = new Token.StartTag();
            startTag2.nameAttr(str, attributes);
            return process(startTag2);
        }
        startTag.mo6845reset();
        startTag.nameAttr(str, attributes);
        return process(startTag);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean processEndTag(String str) {
        Token token = this.currentToken;
        Token.EndTag endTag = this.end;
        if (token == endTag) {
            Token.EndTag endTag2 = new Token.EndTag();
            endTag2.name(str);
            return process(endTag2);
        }
        endTag.mo6845reset();
        endTag.name(str);
        return process(endTag);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Element currentElement() {
        int size = this.stack.size();
        if (size > 0) {
            return this.stack.get(size - 1);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void error(String str) {
        ParseErrorList errors = this.parser.getErrors();
        if (errors.canAddError()) {
            errors.add(new ParseError(this.reader.pos(), str));
        }
    }
}
