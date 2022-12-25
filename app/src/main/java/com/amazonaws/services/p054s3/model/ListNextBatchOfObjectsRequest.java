package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.ListNextBatchOfObjectsRequest */
/* loaded from: classes2.dex */
public class ListNextBatchOfObjectsRequest extends AmazonWebServiceRequest implements Serializable {
    private ObjectListing previousObjectListing;

    public ListNextBatchOfObjectsRequest(ObjectListing objectListing) {
        setPreviousObjectListing(objectListing);
    }

    public ObjectListing getPreviousObjectListing() {
        return this.previousObjectListing;
    }

    public void setPreviousObjectListing(ObjectListing objectListing) {
        if (objectListing == null) {
            throw new IllegalArgumentException("The parameter previousObjectListing must be specified.");
        }
        this.previousObjectListing = objectListing;
    }

    public ListNextBatchOfObjectsRequest withPreviousObjectListing(ObjectListing objectListing) {
        setPreviousObjectListing(objectListing);
        return this;
    }

    public ListObjectsRequest toListObjectsRequest() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.previousObjectListing.getBucketName(), this.previousObjectListing.getPrefix(), this.previousObjectListing.getNextMarker(), this.previousObjectListing.getDelimiter(), Integer.valueOf(this.previousObjectListing.getMaxKeys()));
        listObjectsRequest.withEncodingType(this.previousObjectListing.getEncodingType());
        return listObjectsRequest;
    }
}
