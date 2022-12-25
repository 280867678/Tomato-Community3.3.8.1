package org.dom4j.tree;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.dom4j.IllegalAddException;
import org.dom4j.Node;

/* loaded from: classes4.dex */
public class ContentListFacade<T extends Node> extends AbstractList<T> {
    private AbstractBranch branch;
    private List<T> branchContent;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ void add(int i, Object obj) {
        add(i, (int) ((Node) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return add((ContentListFacade<T>) ((Node) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        return set(i, (int) ((Node) obj));
    }

    public ContentListFacade(AbstractBranch abstractBranch, List<T> list) {
        this.branch = abstractBranch;
        this.branchContent = list;
    }

    public boolean add(T t) {
        this.branch.childAdded(t);
        return this.branchContent.add(t);
    }

    public void add(int i, T t) {
        this.branch.childAdded(t);
        this.branchContent.add(i, t);
    }

    public T set(int i, T t) {
        this.branch.childAdded(t);
        return this.branchContent.set(i, t);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        this.branch.childRemoved(asNode(obj));
        return this.branchContent.remove(obj);
    }

    @Override // java.util.AbstractList, java.util.List
    public T remove(int i) {
        T remove = this.branchContent.remove(i);
        if (remove != null) {
            this.branch.childRemoved(remove);
        }
        return remove;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends T> collection) {
        int size = this.branchContent.size();
        for (T t : collection) {
            add((ContentListFacade<T>) t);
            size++;
        }
        return size == this.branchContent.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends T> collection) {
        int size = this.branchContent.size();
        for (T t : collection) {
            add(i, (int) t);
            size--;
            i++;
        }
        return size == this.branchContent.size();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        Iterator<T> it2 = iterator();
        while (it2.hasNext()) {
            this.branch.childRemoved((Node) it2.next());
        }
        this.branchContent.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it2 = collection.iterator();
        while (it2.hasNext()) {
            this.branch.childRemoved(asNode(it2.next()));
        }
        return this.branchContent.removeAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.branchContent.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return this.branchContent.isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return this.branchContent.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        return this.branchContent.toArray();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray(Object[] objArr) {
        return this.branchContent.toArray(objArr);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean containsAll(Collection<?> collection) {
        return this.branchContent.containsAll(collection);
    }

    @Override // java.util.AbstractList, java.util.List
    public T get(int i) {
        return this.branchContent.get(i);
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(Object obj) {
        return this.branchContent.indexOf(obj);
    }

    @Override // java.util.AbstractList, java.util.List
    public int lastIndexOf(Object obj) {
        return this.branchContent.lastIndexOf(obj);
    }

    protected Node asNode(Object obj) {
        if (obj instanceof Node) {
            return (Node) obj;
        }
        throw new IllegalAddException("This list must contain instances of Node. Invalid type: " + obj);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<T> getBackingList() {
        return this.branchContent;
    }
}
