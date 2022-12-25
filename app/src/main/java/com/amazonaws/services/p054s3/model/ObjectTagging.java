package com.amazonaws.services.p054s3.model;

import java.io.Serializable;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.ObjectTagging */
/* loaded from: classes2.dex */
public class ObjectTagging implements Serializable {
    private List<Tag> tagSet;

    public ObjectTagging(List<Tag> list) {
        this.tagSet = list;
    }

    public List<Tag> getTagSet() {
        return this.tagSet;
    }

    public void setTagSet(List<Tag> list) {
        this.tagSet = list;
    }

    private ObjectTagging withTagSet(List<Tag> list) {
        this.tagSet = list;
        return this;
    }
}
