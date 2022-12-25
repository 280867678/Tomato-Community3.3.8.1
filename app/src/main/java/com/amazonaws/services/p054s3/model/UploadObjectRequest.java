package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.UploadObjectObserver;
import com.amazonaws.services.p054s3.internal.MultiFileOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* renamed from: com.amazonaws.services.s3.model.UploadObjectRequest */
/* loaded from: classes2.dex */
public class UploadObjectRequest extends AbstractPutObjectRequest implements MaterialsDescriptionProvider, Serializable {
    static final int MIN_PART_SIZE = 5242880;
    private static final long serialVersionUID = 1;
    private transient ExecutorService executorService;
    private Map<String, String> materialsDescription;
    private transient MultiFileOutputStream multiFileOutputStream;
    private transient UploadObjectObserver uploadObjectObserver;
    private ObjectMetadata uploadPartMetadata;
    private long partSize = 5242880;
    private long diskLimit = Long.MAX_VALUE;

    public UploadObjectRequest(String str, String str2, File file) {
        super(str, str2, file);
    }

    public UploadObjectRequest(String str, String str2, InputStream inputStream, ObjectMetadata objectMetadata) {
        super(str, str2, inputStream, objectMetadata);
    }

    public long getPartSize() {
        return this.partSize;
    }

    public UploadObjectRequest withPartSize(long j) {
        if (j < 5242880) {
            throw new IllegalArgumentException("partSize must be at least 5242880");
        }
        this.partSize = j;
        return this;
    }

    public long getDiskLimit() {
        return this.diskLimit;
    }

    public UploadObjectRequest withDiskLimit(long j) {
        this.diskLimit = j;
        return this;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public UploadObjectRequest withExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public MultiFileOutputStream getMultiFileOutputStream() {
        return this.multiFileOutputStream;
    }

    public UploadObjectRequest withMultiFileOutputStream(MultiFileOutputStream multiFileOutputStream) {
        this.multiFileOutputStream = multiFileOutputStream;
        return this;
    }

    public UploadObjectObserver getUploadObjectObserver() {
        return this.uploadObjectObserver;
    }

    public UploadObjectRequest withUploadObjectObserver(UploadObjectObserver uploadObjectObserver) {
        this.uploadObjectObserver = uploadObjectObserver;
        return this;
    }

    public Map<String, String> getMaterialsDescription() {
        return this.materialsDescription;
    }

    public void setMaterialsDescription(Map<String, String> map) {
        this.materialsDescription = map == null ? null : Collections.unmodifiableMap(new HashMap(map));
    }

    public UploadObjectRequest withMaterialsDescription(Map<String, String> map) {
        setMaterialsDescription(map);
        return this;
    }

    public ObjectMetadata getUploadPartMetadata() {
        return this.uploadPartMetadata;
    }

    public void setUploadPartMetadata(ObjectMetadata objectMetadata) {
        this.uploadPartMetadata = objectMetadata;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends UploadObjectRequest> T withUploadPartMetadata(ObjectMetadata objectMetadata) {
        setUploadPartMetadata(objectMetadata);
        return this;
    }

    @Override // com.amazonaws.services.p054s3.model.AbstractPutObjectRequest, com.amazonaws.AmazonWebServiceRequest
    /* renamed from: clone */
    public UploadObjectRequest mo5854clone() {
        UploadObjectRequest uploadObjectRequest = (UploadObjectRequest) super.mo5854clone();
        super.copyPutObjectBaseTo(uploadObjectRequest);
        Map<String, String> materialsDescription = getMaterialsDescription();
        ObjectMetadata uploadPartMetadata = getUploadPartMetadata();
        ObjectMetadata objectMetadata = null;
        UploadObjectRequest withUploadObjectObserver = uploadObjectRequest.withMaterialsDescription(materialsDescription == null ? null : new HashMap(materialsDescription)).withDiskLimit(getDiskLimit()).withExecutorService(getExecutorService()).withPartSize(getPartSize()).withUploadObjectObserver(getUploadObjectObserver());
        if (uploadPartMetadata != null) {
            objectMetadata = uploadPartMetadata.m5835clone();
        }
        return withUploadObjectObserver.withUploadPartMetadata(objectMetadata);
    }
}
