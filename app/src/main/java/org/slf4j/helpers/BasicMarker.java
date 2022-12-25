package org.slf4j.helpers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class BasicMarker implements Marker {
    private static String CLOSE = " ]";
    private static String OPEN = "[ ";
    private static String SEP = ", ";
    private static final long serialVersionUID = 1803952589649545191L;
    private final String name;
    private List<Marker> referenceList;

    BasicMarker(String str) {
        if (str == null) {
            throw new IllegalArgumentException("A marker name cannot be null");
        }
        this.name = str;
    }

    @Override // org.slf4j.Marker
    public String getName() {
        return this.name;
    }

    @Override // org.slf4j.Marker
    public synchronized void add(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("A null value cannot be added to a Marker as reference.");
        }
        if (contains(marker)) {
            return;
        }
        if (marker.contains(this)) {
            return;
        }
        if (this.referenceList == null) {
            this.referenceList = new Vector();
        }
        this.referenceList.add(marker);
    }

    @Override // org.slf4j.Marker
    public synchronized boolean hasReferences() {
        boolean z;
        if (this.referenceList != null) {
            if (this.referenceList.size() > 0) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    @Override // org.slf4j.Marker
    public boolean hasChildren() {
        return hasReferences();
    }

    @Override // org.slf4j.Marker
    public synchronized Iterator<Marker> iterator() {
        if (this.referenceList != null) {
            return this.referenceList.iterator();
        }
        return Collections.emptyList().iterator();
    }

    @Override // org.slf4j.Marker
    public synchronized boolean remove(Marker marker) {
        if (this.referenceList == null) {
            return false;
        }
        int size = this.referenceList.size();
        for (int i = 0; i < size; i++) {
            if (marker.equals(this.referenceList.get(i))) {
                this.referenceList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override // org.slf4j.Marker
    public boolean contains(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        if (equals(marker)) {
            return true;
        }
        if (!hasReferences()) {
            return false;
        }
        for (Marker marker2 : this.referenceList) {
            if (marker2.contains(marker)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.slf4j.Marker
    public boolean contains(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        if (this.name.equals(str)) {
            return true;
        }
        if (!hasReferences()) {
            return false;
        }
        for (Marker marker : this.referenceList) {
            if (marker.contains(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.slf4j.Marker
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Marker)) {
            return false;
        }
        return this.name.equals(((Marker) obj).getName());
    }

    @Override // org.slf4j.Marker
    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        if (!hasReferences()) {
            return getName();
        }
        Iterator<Marker> it2 = iterator();
        StringBuilder sb = new StringBuilder(getName());
        sb.append(' ');
        sb.append(OPEN);
        while (it2.hasNext()) {
            sb.append(it2.next().getName());
            if (it2.hasNext()) {
                sb.append(SEP);
            }
        }
        sb.append(CLOSE);
        return sb.toString();
    }
}
