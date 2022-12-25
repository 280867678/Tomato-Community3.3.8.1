package org.dom4j;

import java.util.List;
import org.dom4j.dtd.Decl;

/* loaded from: classes4.dex */
public interface DocumentType extends Node {
    String getElementName();

    List<Decl> getExternalDeclarations();

    List<Decl> getInternalDeclarations();

    String getPublicID();

    String getSystemID();

    void setElementName(String str);

    void setExternalDeclarations(List<Decl> list);

    void setInternalDeclarations(List<Decl> list);

    void setPublicID(String str);

    void setSystemID(String str);
}
