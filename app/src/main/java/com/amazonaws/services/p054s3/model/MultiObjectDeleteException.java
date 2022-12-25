package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.model.DeleteObjectsResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.MultiObjectDeleteException */
/* loaded from: classes2.dex */
public class MultiObjectDeleteException extends AmazonS3Exception {
    private static final long serialVersionUID = -2004213552302446866L;
    private final List<DeleteError> errors = new ArrayList();
    private final List<DeleteObjectsResult.DeletedObject> deletedObjects = new ArrayList();

    /* renamed from: com.amazonaws.services.s3.model.MultiObjectDeleteException$DeleteError */
    /* loaded from: classes2.dex */
    public static class DeleteError {
        public void setCode(String str) {
        }

        public void setKey(String str) {
        }

        public void setMessage(String str) {
        }

        public void setVersionId(String str) {
        }
    }

    public MultiObjectDeleteException(Collection<DeleteError> collection, Collection<DeleteObjectsResult.DeletedObject> collection2) {
        super("One or more objects could not be deleted");
        this.deletedObjects.addAll(collection2);
        this.errors.addAll(collection);
    }

    @Override // com.amazonaws.AmazonServiceException
    public String getErrorCode() {
        return super.getErrorCode();
    }

    public List<DeleteObjectsResult.DeletedObject> getDeletedObjects() {
        return this.deletedObjects;
    }

    public List<DeleteError> getErrors() {
        return this.errors;
    }
}
