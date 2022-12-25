package org.dom4j.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.DocumentFactory;
import org.dom4j.Namespace;
import org.dom4j.QName;

/* loaded from: classes4.dex */
public class NamespaceStack {
    private Map<String, QName> currentNamespaceCache;
    private Namespace defaultNamespace;
    private DocumentFactory documentFactory;
    private ArrayList<Map<String, QName>> namespaceCacheList;
    private ArrayList<Namespace> namespaceStack;
    private Map<String, QName> rootNamespaceCache;

    public NamespaceStack() {
        this.namespaceStack = new ArrayList<>();
        this.namespaceCacheList = new ArrayList<>();
        this.rootNamespaceCache = new HashMap();
        this.documentFactory = DocumentFactory.getInstance();
    }

    public NamespaceStack(DocumentFactory documentFactory) {
        this.namespaceStack = new ArrayList<>();
        this.namespaceCacheList = new ArrayList<>();
        this.rootNamespaceCache = new HashMap();
        this.documentFactory = documentFactory;
    }

    public void push(Namespace namespace) {
        this.namespaceStack.add(namespace);
        this.namespaceCacheList.add(null);
        this.currentNamespaceCache = null;
        String prefix = namespace.getPrefix();
        if (prefix == null || prefix.length() == 0) {
            this.defaultNamespace = namespace;
        }
    }

    public Namespace pop() {
        return remove(this.namespaceStack.size() - 1);
    }

    public int size() {
        return this.namespaceStack.size();
    }

    public void clear() {
        this.namespaceStack.clear();
        this.namespaceCacheList.clear();
        this.rootNamespaceCache.clear();
        this.currentNamespaceCache = null;
    }

    public Namespace getNamespace(int i) {
        return this.namespaceStack.get(i);
    }

    public Namespace getNamespaceForPrefix(String str) {
        if (str == null) {
            str = "";
        }
        for (int size = this.namespaceStack.size() - 1; size >= 0; size--) {
            Namespace namespace = this.namespaceStack.get(size);
            if (str.equals(namespace.getPrefix())) {
                return namespace;
            }
        }
        return null;
    }

    public String getURI(String str) {
        Namespace namespaceForPrefix = getNamespaceForPrefix(str);
        if (namespaceForPrefix != null) {
            return namespaceForPrefix.getURI();
        }
        return null;
    }

    public boolean contains(Namespace namespace) {
        Namespace defaultNamespace;
        String prefix = namespace.getPrefix();
        if (prefix == null || prefix.length() == 0) {
            defaultNamespace = getDefaultNamespace();
        } else {
            defaultNamespace = getNamespaceForPrefix(prefix);
        }
        if (defaultNamespace == null) {
            return false;
        }
        if (defaultNamespace != namespace) {
            return namespace.getURI().equals(defaultNamespace.getURI());
        }
        return true;
    }

    public QName getQName(String str, String str2, String str3) {
        if (str2 == null) {
            str2 = str3;
        } else if (str3 == null) {
            str3 = str2;
        }
        String str4 = "";
        if (str == null) {
            str = str4;
        }
        int indexOf = str3.indexOf(":");
        if (indexOf > 0) {
            str4 = str3.substring(0, indexOf);
            if (str2.trim().length() == 0) {
                str2 = str3.substring(indexOf + 1);
            }
        } else if (str2.trim().length() == 0) {
            str2 = str3;
        }
        return pushQName(str2, str3, createNamespace(str4, str), str4);
    }

    public QName getAttributeQName(String str, String str2, String str3) {
        Namespace namespace;
        if (str3 == null) {
            str3 = str2;
        }
        Map<String, QName> namespaceCache = getNamespaceCache();
        QName qName = namespaceCache.get(str3);
        if (qName != null) {
            return qName;
        }
        if (str2 == null) {
            str2 = str3;
        }
        String str4 = "";
        if (str == null) {
            str = str4;
        }
        int indexOf = str3.indexOf(":");
        if (indexOf > 0) {
            str4 = str3.substring(0, indexOf);
            namespace = createNamespace(str4, str);
            if (str2.trim().length() == 0) {
                str2 = str3.substring(indexOf + 1);
            }
        } else {
            namespace = Namespace.NO_NAMESPACE;
            if (str2.trim().length() == 0) {
                str2 = str3;
            }
        }
        QName pushQName = pushQName(str2, str3, namespace, str4);
        namespaceCache.put(str3, pushQName);
        return pushQName;
    }

    public void push(String str, String str2) {
        if (str2 == null) {
            str2 = "";
        }
        push(createNamespace(str, str2));
    }

    public Namespace addNamespace(String str, String str2) {
        Namespace createNamespace = createNamespace(str, str2);
        push(createNamespace);
        return createNamespace;
    }

    public Namespace pop(String str) {
        if (str == null) {
            str = "";
        }
        Namespace namespace = null;
        int size = this.namespaceStack.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            Namespace namespace2 = this.namespaceStack.get(size);
            if (str.equals(namespace2.getPrefix())) {
                remove(size);
                namespace = namespace2;
                break;
            }
            size--;
        }
        if (namespace == null) {
            System.out.println("Warning: missing namespace prefix ignored: " + str);
        }
        return namespace;
    }

    public String toString() {
        return super.toString() + " Stack: " + this.namespaceStack.toString();
    }

    public DocumentFactory getDocumentFactory() {
        return this.documentFactory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    public Namespace getDefaultNamespace() {
        if (this.defaultNamespace == null) {
            this.defaultNamespace = findDefaultNamespace();
        }
        return this.defaultNamespace;
    }

    protected QName pushQName(String str, String str2, Namespace namespace, String str3) {
        if (str3 == null || str3.length() == 0) {
            this.defaultNamespace = null;
        }
        return createQName(str, str2, namespace);
    }

    protected QName createQName(String str, String str2, Namespace namespace) {
        return this.documentFactory.createQName(str, namespace);
    }

    protected Namespace createNamespace(String str, String str2) {
        return this.documentFactory.createNamespace(str, str2);
    }

    protected Namespace findDefaultNamespace() {
        for (int size = this.namespaceStack.size() - 1; size >= 0; size--) {
            Namespace namespace = this.namespaceStack.get(size);
            if (namespace != null && (namespace.getPrefix() == null || namespace.getPrefix().length() == 0)) {
                return namespace;
            }
        }
        return null;
    }

    protected Namespace remove(int i) {
        Namespace remove = this.namespaceStack.remove(i);
        this.namespaceCacheList.remove(i);
        this.defaultNamespace = null;
        this.currentNamespaceCache = null;
        return remove;
    }

    protected Map<String, QName> getNamespaceCache() {
        if (this.currentNamespaceCache == null) {
            int size = this.namespaceStack.size() - 1;
            if (size < 0) {
                this.currentNamespaceCache = this.rootNamespaceCache;
            } else {
                this.currentNamespaceCache = this.namespaceCacheList.get(size);
                if (this.currentNamespaceCache == null) {
                    this.currentNamespaceCache = new HashMap();
                    this.namespaceCacheList.set(size, this.currentNamespaceCache);
                }
            }
        }
        return this.currentNamespaceCache;
    }
}
