package org.dom4j.tree;

import java.util.List;
import org.dom4j.dtd.Decl;

/* loaded from: classes4.dex */
public class DefaultDocumentType extends AbstractDocumentType {
    protected String elementName;
    private List<Decl> externalDeclarations;
    private List<Decl> internalDeclarations;
    private String publicID;
    private String systemID;

    public DefaultDocumentType() {
    }

    public DefaultDocumentType(String str, String str2) {
        this.elementName = str;
        this.systemID = str2;
    }

    public DefaultDocumentType(String str, String str2, String str3) {
        this.elementName = str;
        this.publicID = str2;
        this.systemID = str3;
    }

    @Override // org.dom4j.DocumentType
    public String getElementName() {
        return this.elementName;
    }

    @Override // org.dom4j.DocumentType
    public void setElementName(String str) {
        this.elementName = str;
    }

    @Override // org.dom4j.DocumentType
    public String getPublicID() {
        return this.publicID;
    }

    @Override // org.dom4j.DocumentType
    public void setPublicID(String str) {
        this.publicID = str;
    }

    @Override // org.dom4j.DocumentType
    public String getSystemID() {
        return this.systemID;
    }

    @Override // org.dom4j.DocumentType
    public void setSystemID(String str) {
        this.systemID = str;
    }

    @Override // org.dom4j.DocumentType
    public List<Decl> getInternalDeclarations() {
        return this.internalDeclarations;
    }

    @Override // org.dom4j.DocumentType
    public void setInternalDeclarations(List<Decl> list) {
        this.internalDeclarations = list;
    }

    @Override // org.dom4j.DocumentType
    public List<Decl> getExternalDeclarations() {
        return this.externalDeclarations;
    }

    @Override // org.dom4j.DocumentType
    public void setExternalDeclarations(List<Decl> list) {
        this.externalDeclarations = list;
    }
}
