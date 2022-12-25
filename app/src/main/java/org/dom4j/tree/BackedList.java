package org.dom4j.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Node;

/* loaded from: classes4.dex */
public class BackedList<T extends Node> extends ArrayList<T> {
    private AbstractBranch branch;
    private List<Node> branchContent;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ void add(int i, Object obj) {
        add(i, (int) ((Node) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return add((BackedList<T>) ((Node) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        return set(i, (int) ((Node) obj));
    }

    public BackedList(AbstractBranch abstractBranch, List<Node> list) {
        this(abstractBranch, list, list.size());
    }

    public BackedList(AbstractBranch abstractBranch, List<Node> list, int i) {
        super(i);
        this.branch = abstractBranch;
        this.branchContent = list;
    }

    public BackedList(AbstractBranch abstractBranch, List<Node> list, List<T> list2) {
        super(list2);
        this.branch = abstractBranch;
        this.branchContent = list;
    }

    public boolean add(T t) {
        this.branch.addNode(t);
        return super.add((BackedList<T>) t);
    }

    public void add(int i, T t) {
        int indexOf;
        int size = size();
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index value: " + i + " is less than zero");
        } else if (i > size) {
            throw new IndexOutOfBoundsException("Index value: " + i + " cannot be greater than the size: " + size);
        } else {
            if (size == 0) {
                indexOf = this.branchContent.size();
            } else if (i < size) {
                indexOf = this.branchContent.indexOf(get(i));
            } else {
                indexOf = this.branchContent.indexOf(get(size - 1)) + 1;
            }
            this.branch.addNode(indexOf, t);
            super.add(i, (int) t);
        }
    }

    public T set(int i, T t) {
        int indexOf = this.branchContent.indexOf(get(i));
        if (indexOf < 0) {
            indexOf = i == 0 ? 0 : Integer.MAX_VALUE;
        }
        if (indexOf < this.branchContent.size()) {
            this.branch.removeNode((Node) get(i));
            this.branch.addNode(indexOf, t);
        } else {
            this.branch.removeNode((Node) get(i));
            this.branch.addNode(t);
        }
        this.branch.childAdded(t);
        return (T) super.set(i, (int) t);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        if (obj instanceof Node) {
            this.branch.removeNode((Node) obj);
        }
        return super.remove(obj);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T remove(int i) {
        T t = (T) super.remove(i);
        if (t != null) {
            this.branch.removeNode(t);
        }
        return t;
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends T> collection) {
        ensureCapacity(size() + collection.size());
        int size = size();
        for (T t : collection) {
            add((BackedList<T>) t);
            size--;
        }
        return size != 0;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends T> collection) {
        ensureCapacity(size() + collection.size());
        int size = size();
        for (T t : collection) {
            add(i, (int) t);
            size--;
            i++;
        }
        return size != 0;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        Iterator<T> it2 = iterator();
        while (it2.hasNext()) {
            Node node = (Node) it2.next();
            this.branchContent.remove(node);
            this.branch.childRemoved(node);
        }
        super.clear();
    }

    public void addLocal(T t) {
        super.add((BackedList<T>) t);
    }
}
