package org.dom4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.regex.Pattern;
import org.dom4j.tree.QNameCache;
import org.dom4j.util.SingletonStrategy;

/* loaded from: classes4.dex */
public class QName implements Serializable {
    private static final String NAME_CHAR = "_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�-.0-9·̀-ͯ‿-⁀";
    private static final String NAME_START_CHAR = "_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�";
    private static SingletonStrategy<QNameCache> singleton;
    private DocumentFactory documentFactory;
    private int hashCode;
    private String name;
    private transient Namespace namespace;
    private String qualifiedName;
    private static final Pattern RE_NAME = Pattern.compile("[:_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�][:_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�-.0-9·̀-ͯ‿-⁀]*");
    private static final String NCNAME = "[_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�][_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�-.0-9·̀-ͯ‿-⁀]*";
    private static final Pattern RE_NCNAME = Pattern.compile(NCNAME);
    private static final Pattern RE_QNAME = Pattern.compile("(?:[_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�][_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�-.0-9·̀-ͯ‿-⁀]*:)?[_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�][_A-Za-zÀ-ÖØ-öø-˿Ͱ-ͽ\u037f-\u1fff\u200c-\u200d⁰-\u218fⰀ-\u2fef、-\ud7ff豈-\ufdcfﷰ-�-.0-9·̀-ͯ‿-⁀]*");

    static {
        Class<?> cls = null;
        try {
            try {
                cls = Class.forName(System.getProperty("org.dom4j.QName.singleton.strategy", "org.dom4j.util.SimpleSingleton"));
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            cls = Class.forName("org.dom4j.util.SimpleSingleton");
        }
        try {
            singleton = (SingletonStrategy) cls.newInstance();
            singleton.setSingletonClassName(QNameCache.class.getName());
        } catch (Exception unused3) {
        }
    }

    public QName(String str) {
        this(str, Namespace.NO_NAMESPACE);
    }

    public QName(String str, Namespace namespace) {
        this.name = str == null ? "" : str;
        this.namespace = namespace == null ? Namespace.NO_NAMESPACE : namespace;
        if (this.namespace.equals(Namespace.NO_NAMESPACE)) {
            validateName(this.name);
        } else {
            validateNCName(this.name);
        }
    }

    public QName(String str, Namespace namespace, String str2) {
        this.name = str == null ? "" : str;
        this.qualifiedName = str2;
        this.namespace = namespace == null ? Namespace.NO_NAMESPACE : namespace;
        validateNCName(this.name);
        validateQName(this.qualifiedName);
    }

    public static QName get(String str) {
        return getCache().get(str);
    }

    public static QName get(String str, Namespace namespace) {
        return getCache().get(str, namespace);
    }

    public static QName get(String str, String str2, String str3) {
        if ((str2 == null || str2.length() == 0) && str3 == null) {
            return get(str);
        }
        if (str2 == null || str2.length() == 0) {
            return getCache().get(str, Namespace.get(str3));
        }
        if (str3 == null) {
            return get(str);
        }
        return getCache().get(str, Namespace.get(str2, str3));
    }

    public static QName get(String str, String str2) {
        if (str2 == null) {
            return getCache().get(str);
        }
        return getCache().get(str, str2);
    }

    public static QName get(String str, Namespace namespace, String str2) {
        return getCache().get(str, namespace, str2);
    }

    public String getName() {
        return this.name;
    }

    public String getQualifiedName() {
        if (this.qualifiedName == null) {
            String namespacePrefix = getNamespacePrefix();
            if (namespacePrefix != null && namespacePrefix.length() > 0) {
                this.qualifiedName = namespacePrefix + ":" + this.name;
            } else {
                this.qualifiedName = this.name;
            }
        }
        return this.qualifiedName;
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public String getNamespacePrefix() {
        Namespace namespace = this.namespace;
        return namespace == null ? "" : namespace.getPrefix();
    }

    public String getNamespaceURI() {
        Namespace namespace = this.namespace;
        return namespace == null ? "" : namespace.getURI();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = getName().hashCode() ^ getNamespaceURI().hashCode();
            if (this.hashCode == 0) {
                this.hashCode = 47806;
            }
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof QName) {
            QName qName = (QName) obj;
            return hashCode() == qName.hashCode() && getName().equals(qName.getName()) && getNamespaceURI().equals(qName.getNamespaceURI());
        }
        return false;
    }

    public String toString() {
        return super.toString() + " [name: " + getName() + " namespace: \"" + getNamespace() + "\"]";
    }

    public DocumentFactory getDocumentFactory() {
        return this.documentFactory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.namespace.getPrefix());
        objectOutputStream.writeObject(this.namespace.getURI());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.namespace = Namespace.get((String) objectInputStream.readObject(), (String) objectInputStream.readObject());
    }

    private static QNameCache getCache() {
        return singleton.instance();
    }

    private static void validateName(String str) {
        if (RE_NAME.matcher(str).matches()) {
            return;
        }
        throw new IllegalArgumentException(String.format("Illegal character in name: '%s'.", str));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void validateNCName(String str) {
        if (RE_NCNAME.matcher(str).matches()) {
            return;
        }
        throw new IllegalArgumentException(String.format("Illegal character in local name: '%s'.", str));
    }

    private static void validateQName(String str) {
        if (RE_QNAME.matcher(str).matches()) {
            return;
        }
        throw new IllegalArgumentException(String.format("Illegal character in qualified name: '%s'.", str));
    }
}
