package com.amazonaws.services.p054s3.internal;

import com.amazonaws.services.p054s3.model.DeleteObjectsResult;
import com.amazonaws.services.p054s3.model.MultiObjectDeleteException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.internal.DeleteObjectsResponse */
/* loaded from: classes2.dex */
public class DeleteObjectsResponse implements S3RequesterChargedResult {
    private List<DeleteObjectsResult.DeletedObject> deletedObjects;
    private List<MultiObjectDeleteException.DeleteError> errors;

    @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
    public void setRequesterCharged(boolean z) {
    }

    public DeleteObjectsResponse() {
        this(new ArrayList(), new ArrayList());
    }

    public DeleteObjectsResponse(List<DeleteObjectsResult.DeletedObject> list, List<MultiObjectDeleteException.DeleteError> list2) {
        this.deletedObjects = list;
        this.errors = list2;
    }

    public List<DeleteObjectsResult.DeletedObject> getDeletedObjects() {
        return this.deletedObjects;
    }

    public List<MultiObjectDeleteException.DeleteError> getErrors() {
        return this.errors;
    }
}
