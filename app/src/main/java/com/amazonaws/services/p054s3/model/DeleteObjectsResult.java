package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.internal.S3RequesterChargedResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.DeleteObjectsResult */
/* loaded from: classes2.dex */
public class DeleteObjectsResult implements Serializable, S3RequesterChargedResult {
    private final List<DeletedObject> deletedObjects;
    private boolean isRequesterCharged;

    public DeleteObjectsResult(List<DeletedObject> list) {
        this(list, false);
    }

    public DeleteObjectsResult(List<DeletedObject> list, boolean z) {
        this.deletedObjects = new ArrayList();
        this.deletedObjects.addAll(list);
        setRequesterCharged(z);
    }

    public List<DeletedObject> getDeletedObjects() {
        return this.deletedObjects;
    }

    public boolean isRequesterCharged() {
        return this.isRequesterCharged;
    }

    @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
    public void setRequesterCharged(boolean z) {
        this.isRequesterCharged = z;
    }

    /* renamed from: com.amazonaws.services.s3.model.DeleteObjectsResult$DeletedObject */
    /* loaded from: classes2.dex */
    public static class DeletedObject implements Serializable {
        private boolean deleteMarker;
        private String deleteMarkerVersionId;
        private String key;
        private String versionId;

        public String getKey() {
            return this.key;
        }

        public void setKey(String str) {
            this.key = str;
        }

        public String getVersionId() {
            return this.versionId;
        }

        public void setVersionId(String str) {
            this.versionId = str;
        }

        public boolean isDeleteMarker() {
            return this.deleteMarker;
        }

        public void setDeleteMarker(boolean z) {
            this.deleteMarker = z;
        }

        public String getDeleteMarkerVersionId() {
            return this.deleteMarkerVersionId;
        }

        public void setDeleteMarkerVersionId(String str) {
            this.deleteMarkerVersionId = str;
        }
    }
}
