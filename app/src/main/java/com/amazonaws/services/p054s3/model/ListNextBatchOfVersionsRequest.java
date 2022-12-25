package com.amazonaws.services.p054s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.ListNextBatchOfVersionsRequest */
/* loaded from: classes2.dex */
public class ListNextBatchOfVersionsRequest extends AmazonWebServiceRequest implements Serializable {
    private VersionListing previousVersionListing;

    public ListNextBatchOfVersionsRequest(VersionListing versionListing) {
        setPreviousVersionListing(versionListing);
    }

    public VersionListing getPreviousVersionListing() {
        return this.previousVersionListing;
    }

    public void setPreviousVersionListing(VersionListing versionListing) {
        if (versionListing == null) {
            throw new IllegalArgumentException("The parameter previousVersionListing must be specified.");
        }
        this.previousVersionListing = versionListing;
    }

    public ListNextBatchOfVersionsRequest withPreviousVersionListing(VersionListing versionListing) {
        setPreviousVersionListing(versionListing);
        return this;
    }

    public ListVersionsRequest toListVersionsRequest() {
        this.previousVersionListing.getBucketName();
        throw null;
    }
}
