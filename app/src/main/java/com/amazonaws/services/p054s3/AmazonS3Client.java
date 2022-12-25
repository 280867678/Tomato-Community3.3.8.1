package com.amazonaws.services.p054s3;

import com.amazonaws.AbortedException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.Signer;
import com.amazonaws.auth.SignerFactory;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListenerCallbackExecutor;
import com.amazonaws.event.ProgressReportingInputStream;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpClient;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.http.UrlHttpClient;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.p054s3.internal.AWSS3V4Signer;
import com.amazonaws.services.p054s3.internal.BucketNameUtils;
import com.amazonaws.services.p054s3.internal.CompleteMultipartUploadRetryCondition;
import com.amazonaws.services.p054s3.internal.DigestValidationInputStream;
import com.amazonaws.services.p054s3.internal.InputSubstream;
import com.amazonaws.services.p054s3.internal.MD5DigestCalculatingInputStream;
import com.amazonaws.services.p054s3.internal.ObjectExpirationHeaderHandler;
import com.amazonaws.services.p054s3.internal.RepeatableFileInputStream;
import com.amazonaws.services.p054s3.internal.ResponseHeaderHandlerChain;
import com.amazonaws.services.p054s3.internal.S3ErrorResponseHandler;
import com.amazonaws.services.p054s3.internal.S3ExecutionContext;
import com.amazonaws.services.p054s3.internal.S3HttpUtils;
import com.amazonaws.services.p054s3.internal.S3MetadataResponseHandler;
import com.amazonaws.services.p054s3.internal.S3ObjectResponseHandler;
import com.amazonaws.services.p054s3.internal.S3RequesterChargedHeaderHandler;
import com.amazonaws.services.p054s3.internal.S3Signer;
import com.amazonaws.services.p054s3.internal.S3VersionHeaderHandler;
import com.amazonaws.services.p054s3.internal.S3XmlResponseHandler;
import com.amazonaws.services.p054s3.internal.ServerSideEncryptionHeaderHandler;
import com.amazonaws.services.p054s3.internal.ServiceUtils;
import com.amazonaws.services.p054s3.metrics.S3ServiceMetric;
import com.amazonaws.services.p054s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.AccessControlList;
import com.amazonaws.services.p054s3.model.AmazonS3Exception;
import com.amazonaws.services.p054s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.p054s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.p054s3.model.GetObjectRequest;
import com.amazonaws.services.p054s3.model.Grant;
import com.amazonaws.services.p054s3.model.Grantee;
import com.amazonaws.services.p054s3.model.HeadBucketRequest;
import com.amazonaws.services.p054s3.model.HeadBucketResult;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.p054s3.model.ObjectMetadata;
import com.amazonaws.services.p054s3.model.ObjectTagging;
import com.amazonaws.services.p054s3.model.Permission;
import com.amazonaws.services.p054s3.model.PutObjectRequest;
import com.amazonaws.services.p054s3.model.PutObjectResult;
import com.amazonaws.services.p054s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.p054s3.model.S3AccelerateUnsupported;
import com.amazonaws.services.p054s3.model.S3Object;
import com.amazonaws.services.p054s3.model.S3ObjectInputStream;
import com.amazonaws.services.p054s3.model.SSEAwsKeyManagementParams;
import com.amazonaws.services.p054s3.model.SSECustomerKey;
import com.amazonaws.services.p054s3.model.Tag;
import com.amazonaws.services.p054s3.model.UploadPartRequest;
import com.amazonaws.services.p054s3.model.UploadPartResult;
import com.amazonaws.services.p054s3.model.transform.BucketConfigurationXmlFactory;
import com.amazonaws.services.p054s3.model.transform.HeadBucketResultHandler;
import com.amazonaws.services.p054s3.model.transform.RequestPaymentConfigurationXmlFactory;
import com.amazonaws.services.p054s3.model.transform.RequestXmlFactory;
import com.amazonaws.services.p054s3.model.transform.XmlResponsesSaxParser;
import com.amazonaws.services.p054s3.util.Mimetypes;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AwsHostNameUtils;
import com.amazonaws.util.Base64;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.LengthCheckInputStream;
import com.amazonaws.util.Md5Utils;
import com.amazonaws.util.RuntimeHttpUtils;
import com.amazonaws.util.ServiceClientHolderInputStream;
import com.amazonaws.util.ValidationUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.amazonaws.services.s3.AmazonS3Client */
/* loaded from: classes2.dex */
public class AmazonS3Client extends AmazonWebServiceClient implements AmazonS3 {
    private final AWSCredentialsProvider awsCredentialsProvider;
    private S3ClientOptions clientOptions;
    volatile String clientRegion;
    private final CompleteMultipartUploadRetryCondition completeMultipartUploadRetryCondition;
    private final S3ErrorResponseHandler errorResponseHandler;
    private int notificationThreshold;
    private final S3XmlResponseHandler<Void> voidResponseHandler;
    private static Log log = LogFactory.getLog(AmazonS3Client.class);
    private static final Map<String, String> bucketRegionCache = Collections.synchronizedMap(new LinkedHashMap<String, String>(300, 1.1f, true) { // from class: com.amazonaws.services.s3.AmazonS3Client.1
        private static final long serialVersionUID = 23453;

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<String, String> entry) {
            return size() > 300;
        }
    });

    static {
        AwsSdkMetrics.addAll(Arrays.asList(S3ServiceMetric.values()));
        SignerFactory.registerSigner("S3SignerType", S3Signer.class);
        SignerFactory.registerSigner("AWSS3V4SignerType", AWSS3V4Signer.class);
        new BucketConfigurationXmlFactory();
        new RequestPaymentConfigurationXmlFactory();
    }

    @Deprecated
    public AmazonS3Client() {
        this(new DefaultAWSCredentialsProviderChain());
    }

    @Deprecated
    public AmazonS3Client(AWSCredentials aWSCredentials, ClientConfiguration clientConfiguration) {
        this(new StaticCredentialsProvider(aWSCredentials), clientConfiguration);
    }

    @Deprecated
    public AmazonS3Client(AWSCredentialsProvider aWSCredentialsProvider) {
        this(aWSCredentialsProvider, new ClientConfiguration());
    }

    @Deprecated
    public AmazonS3Client(AWSCredentialsProvider aWSCredentialsProvider, ClientConfiguration clientConfiguration) {
        this(aWSCredentialsProvider, clientConfiguration, new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AmazonS3Client(AWSCredentialsProvider aWSCredentialsProvider, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(clientConfiguration, httpClient);
        this.errorResponseHandler = new S3ErrorResponseHandler();
        this.voidResponseHandler = new S3XmlResponseHandler<>(null);
        this.clientOptions = new S3ClientOptions();
        this.notificationThreshold = 1024;
        this.completeMultipartUploadRetryCondition = new CompleteMultipartUploadRetryCondition();
        this.awsCredentialsProvider = aWSCredentialsProvider;
        init();
    }

    @Deprecated
    private void init() {
        setEndpoint("s3.amazonaws.com");
        this.endpointPrefix = "s3";
        HandlerChainFactory handlerChainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(handlerChainFactory.newRequestHandlerChain("/com/amazonaws/services/s3/request.handlers"));
        this.requestHandler2s.addAll(handlerChainFactory.newRequestHandler2Chain("/com/amazonaws/services/s3/request.handler2s"));
    }

    @Override // com.amazonaws.AmazonWebServiceClient, com.amazonaws.services.p054s3.AmazonS3
    public void setEndpoint(String str) {
        if (str.endsWith("s3-accelerate.amazonaws.com")) {
            throw new IllegalStateException("To enable accelerate mode, please use AmazonS3Client.setS3ClientOptions(S3ClientOptions.builder().setAccelerateModeEnabled(true).build());");
        }
        super.setEndpoint(str);
        if (str.endsWith("s3.amazonaws.com")) {
            return;
        }
        this.clientRegion = AwsHostNameUtils.parseRegionName(this.endpoint.getHost(), "s3");
    }

    @Override // com.amazonaws.AmazonWebServiceClient, com.amazonaws.services.p054s3.AmazonS3
    public void setRegion(Region region) {
        super.setRegion(region);
        this.clientRegion = region.getName();
    }

    @Override // com.amazonaws.services.p054s3.AmazonS3
    public void setS3ClientOptions(S3ClientOptions s3ClientOptions) {
        this.clientOptions = new S3ClientOptions(s3ClientOptions);
    }

    @Override // com.amazonaws.services.p054s3.AmazonS3
    public S3Object getObject(GetObjectRequest getObjectRequest) throws AmazonClientException, AmazonServiceException {
        ProgressReportingInputStream progressReportingInputStream;
        FilterInputStream lengthCheckInputStream;
        ValidationUtils.assertParameterNotNull(getObjectRequest, "The GetObjectRequest parameter must be specified when requesting an object");
        ValidationUtils.assertParameterNotNull(getObjectRequest.getBucketName(), "The bucket name parameter must be specified when requesting an object");
        ValidationUtils.assertParameterNotNull(getObjectRequest.getKey(), "The key parameter must be specified when requesting an object");
        Request createRequest = createRequest(getObjectRequest.getBucketName(), getObjectRequest.getKey(), getObjectRequest, HttpMethodName.GET);
        if (getObjectRequest.getVersionId() != null) {
            createRequest.addParameter("versionId", getObjectRequest.getVersionId());
        }
        long[] range = getObjectRequest.getRange();
        if (range != null) {
            String str = "bytes=" + Long.toString(range[0]) + "-";
            if (range[1] >= 0) {
                str = str + Long.toString(range[1]);
            }
            createRequest.addHeader("Range", str);
        }
        populateRequesterPaysHeader(createRequest, getObjectRequest.isRequesterPays());
        addResponseHeaderParameters(createRequest, getObjectRequest.getResponseHeaders());
        addDateHeader(createRequest, "If-Modified-Since", getObjectRequest.getModifiedSinceConstraint());
        addDateHeader(createRequest, "If-Unmodified-Since", getObjectRequest.getUnmodifiedSinceConstraint());
        addStringListHeader(createRequest, "If-Match", getObjectRequest.getMatchingETagConstraints());
        addStringListHeader(createRequest, "If-None-Match", getObjectRequest.getNonmatchingETagConstraints());
        populateSSE_C(createRequest, getObjectRequest.getSSECustomerKey());
        ProgressListenerCallbackExecutor wrapListener = ProgressListenerCallbackExecutor.wrapListener(getObjectRequest.getGeneralProgressListener());
        try {
            S3Object s3Object = (S3Object) invoke(createRequest, new S3ObjectResponseHandler(), getObjectRequest.getBucketName(), getObjectRequest.getKey());
            s3Object.setBucketName(getObjectRequest.getBucketName());
            s3Object.setKey(getObjectRequest.getKey());
            FilterInputStream serviceClientHolderInputStream = new ServiceClientHolderInputStream(s3Object.getObjectContent(), this);
            if (wrapListener != null) {
                ProgressReportingInputStream progressReportingInputStream2 = new ProgressReportingInputStream(serviceClientHolderInputStream, wrapListener);
                progressReportingInputStream2.setFireCompletedEvent(true);
                progressReportingInputStream2.setNotificationThreshold(this.notificationThreshold);
                fireProgressEvent(wrapListener, 2);
                progressReportingInputStream = progressReportingInputStream2;
            } else {
                progressReportingInputStream = serviceClientHolderInputStream;
            }
            if (!ServiceUtils.skipMd5CheckPerRequest(getObjectRequest) && !ServiceUtils.skipMd5CheckPerResponse(s3Object.getObjectMetadata())) {
                String eTag = s3Object.getObjectMetadata().getETag();
                FilterInputStream filterInputStream = progressReportingInputStream;
                if (eTag != null) {
                    filterInputStream = progressReportingInputStream;
                    if (!ServiceUtils.isMultipartUploadETag(eTag)) {
                        try {
                            filterInputStream = new DigestValidationInputStream(progressReportingInputStream, MessageDigest.getInstance("MD5"), BinaryUtils.fromHex(s3Object.getObjectMetadata().getETag()));
                        } catch (NoSuchAlgorithmException e) {
                            log.warn("No MD5 digest algorithm available. Unable to calculate checksum and verify data integrity.", e);
                            filterInputStream = progressReportingInputStream;
                        }
                    }
                }
                lengthCheckInputStream = filterInputStream;
            } else {
                lengthCheckInputStream = new LengthCheckInputStream(progressReportingInputStream, s3Object.getObjectMetadata().getContentLength(), true);
            }
            s3Object.setObjectContent(new S3ObjectInputStream(lengthCheckInputStream));
            return s3Object;
        } catch (AmazonS3Exception e2) {
            if (e2.getStatusCode() == 412 || e2.getStatusCode() == 304) {
                fireProgressEvent(wrapListener, 16);
                return null;
            }
            fireProgressEvent(wrapListener, 8);
            throw e2;
        }
    }

    @Override // com.amazonaws.services.p054s3.AmazonS3
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        ProgressReportingInputStream progressReportingInputStream;
        ValidationUtils.assertParameterNotNull(putObjectRequest, "The PutObjectRequest parameter must be specified when uploading an object");
        String bucketName = putObjectRequest.getBucketName();
        String key = putObjectRequest.getKey();
        ObjectMetadata metadata = putObjectRequest.getMetadata();
        InputStream inputStream = putObjectRequest.getInputStream();
        ProgressListenerCallbackExecutor wrapListener = ProgressListenerCallbackExecutor.wrapListener(putObjectRequest.getGeneralProgressListener());
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        ValidationUtils.assertParameterNotNull(bucketName, "The bucket name parameter must be specified when uploading an object");
        ValidationUtils.assertParameterNotNull(key, "The key parameter must be specified when uploading an object");
        boolean skipMd5CheckPerRequest = ServiceUtils.skipMd5CheckPerRequest(putObjectRequest);
        if (putObjectRequest.getFile() != null) {
            File file = putObjectRequest.getFile();
            metadata.setContentLength(file.length());
            boolean z = metadata.getContentMD5() == null;
            if (metadata.getContentType() == null) {
                metadata.setContentType(Mimetypes.getInstance().getMimetype(file));
            }
            if (z && !skipMd5CheckPerRequest) {
                try {
                    metadata.setContentMD5(Md5Utils.md5AsBase64(file));
                } catch (Exception e) {
                    throw new AmazonClientException("Unable to calculate MD5 hash: " + e.getMessage(), e);
                }
            }
            try {
                inputStream = new RepeatableFileInputStream(file);
            } catch (FileNotFoundException e2) {
                throw new AmazonClientException("Unable to find file to upload", e2);
            }
        }
        Request<?> createRequest = createRequest(bucketName, key, putObjectRequest, HttpMethodName.PUT);
        if (putObjectRequest.getAccessControlList() != null) {
            addAclHeaders(createRequest, putObjectRequest.getAccessControlList());
        } else if (putObjectRequest.getCannedAcl() != null) {
            createRequest.addHeader("x-amz-acl", putObjectRequest.getCannedAcl().toString());
        }
        if (putObjectRequest.getStorageClass() != null) {
            createRequest.addHeader("x-amz-storage-class", putObjectRequest.getStorageClass());
        }
        ByteArrayInputStream byteArrayInputStream = inputStream;
        if (putObjectRequest.getRedirectLocation() != null) {
            createRequest.addHeader("x-amz-website-redirect-location", putObjectRequest.getRedirectLocation());
            byteArrayInputStream = inputStream;
            if (inputStream == null) {
                setZeroContentLength(createRequest);
                byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
            }
        }
        addHeaderIfNotNull(createRequest, "x-amz-tagging", urlEncodeTags(putObjectRequest.getTagging()));
        populateRequesterPaysHeader(createRequest, putObjectRequest.isRequesterPays());
        populateSSE_C(createRequest, putObjectRequest.getSSECustomerKey());
        Long l = (Long) metadata.getRawMetadataValue("Content-Length");
        if (l == null) {
            if (!byteArrayInputStream.markSupported()) {
                log.warn("No content length specified for stream data.  Stream contents will be buffered in memory and could result in out of memory errors.");
                ByteArrayInputStream byteArray = toByteArray(byteArrayInputStream);
                createRequest.addHeader("Content-Length", String.valueOf(byteArray.available()));
                createRequest.setStreaming(true);
                progressReportingInputStream = byteArray;
            } else {
                createRequest.addHeader("Content-Length", String.valueOf(calculateContentLength(byteArrayInputStream)));
                progressReportingInputStream = byteArrayInputStream;
            }
        } else {
            long longValue = l.longValue();
            progressReportingInputStream = byteArrayInputStream;
            if (longValue >= 0) {
                LengthCheckInputStream lengthCheckInputStream = new LengthCheckInputStream(byteArrayInputStream, longValue, false);
                createRequest.addHeader("Content-Length", l.toString());
                progressReportingInputStream = lengthCheckInputStream;
            }
        }
        if (wrapListener != null) {
            ProgressReportingInputStream progressReportingInputStream2 = new ProgressReportingInputStream(progressReportingInputStream, wrapListener);
            progressReportingInputStream2.setNotificationThreshold(this.notificationThreshold);
            fireProgressEvent(wrapListener, 2);
            progressReportingInputStream = progressReportingInputStream2;
        }
        MD5DigestCalculatingInputStream mD5DigestCalculatingInputStream = null;
        MD5DigestCalculatingInputStream mD5DigestCalculatingInputStream2 = progressReportingInputStream;
        mD5DigestCalculatingInputStream2 = progressReportingInputStream;
        if (metadata.getContentMD5() == null && !skipMd5CheckPerRequest) {
            mD5DigestCalculatingInputStream = new MD5DigestCalculatingInputStream(progressReportingInputStream);
            mD5DigestCalculatingInputStream2 = mD5DigestCalculatingInputStream;
        }
        if (metadata.getContentType() == null) {
            metadata.setContentType("application/octet-stream");
        }
        populateRequestMetadata(createRequest, metadata);
        populateSSE_KMS(createRequest, putObjectRequest.getSSEAwsKeyManagementParams());
        createRequest.setContent(mD5DigestCalculatingInputStream2);
        try {
            try {
                ObjectMetadata objectMetadata = (ObjectMetadata) invoke(createRequest, new S3MetadataResponseHandler(), bucketName, key);
                try {
                    mD5DigestCalculatingInputStream2.close();
                } catch (AbortedException unused) {
                } catch (Exception e3) {
                    Log log2 = log;
                    log2.debug("Unable to cleanly close input stream: " + e3.getMessage(), e3);
                }
                String contentMD5 = metadata.getContentMD5();
                if (mD5DigestCalculatingInputStream != null) {
                    contentMD5 = BinaryUtils.toBase64(mD5DigestCalculatingInputStream.getMd5Digest());
                }
                if (objectMetadata != null && contentMD5 != null && !skipMd5CheckPerRequest && !Arrays.equals(BinaryUtils.fromBase64(contentMD5), BinaryUtils.fromHex(objectMetadata.getETag()))) {
                    fireProgressEvent(wrapListener, 8);
                    throw new AmazonClientException("Unable to verify integrity of data upload.  Client calculated content hash didn't match hash calculated by Amazon S3.  You may need to delete the data stored in Amazon S3.");
                }
                fireProgressEvent(wrapListener, 4);
                PutObjectResult putObjectResult = new PutObjectResult();
                putObjectResult.setVersionId(objectMetadata.getVersionId());
                putObjectResult.setSSEAlgorithm(objectMetadata.getSSEAlgorithm());
                putObjectResult.setSSECustomerAlgorithm(objectMetadata.getSSECustomerAlgorithm());
                putObjectResult.setSSECustomerKeyMd5(objectMetadata.getSSECustomerKeyMd5());
                putObjectResult.setExpirationTime(objectMetadata.getExpirationTime());
                putObjectResult.setExpirationTimeRuleId(objectMetadata.getExpirationTimeRuleId());
                putObjectResult.setETag(objectMetadata.getETag());
                putObjectResult.setMetadata(objectMetadata);
                putObjectResult.setRequesterCharged(objectMetadata.isRequesterCharged());
                return putObjectResult;
            } catch (AmazonClientException e4) {
                fireProgressEvent(wrapListener, 8);
                throw e4;
            }
        } catch (Throwable th) {
            try {
                mD5DigestCalculatingInputStream2.close();
            } catch (AbortedException unused2) {
            } catch (Exception e5) {
                Log log3 = log;
                log3.debug("Unable to cleanly close input stream: " + e5.getMessage(), e5);
            }
            throw th;
        }
    }

    private long calculateContentLength(InputStream inputStream) {
        byte[] bArr = new byte[8192];
        inputStream.mark(-1);
        long j = 0;
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    inputStream.reset();
                    return j;
                }
                j += read;
            } catch (IOException e) {
                throw new AmazonClientException("Could not calculate content length.", e);
            }
        }
    }

    private static void addAclHeaders(Request<? extends AmazonWebServiceRequest> request, AccessControlList accessControlList) {
        Permission[] values;
        Set<Grant> grants = accessControlList.getGrants();
        HashMap hashMap = new HashMap();
        for (Grant grant : grants) {
            if (!hashMap.containsKey(grant.getPermission())) {
                hashMap.put(grant.getPermission(), new LinkedList());
            }
            ((Collection) hashMap.get(grant.getPermission())).add(grant.getGrantee());
        }
        for (Permission permission : Permission.values()) {
            if (hashMap.containsKey(permission)) {
                StringBuilder sb = new StringBuilder();
                boolean z = false;
                for (Grantee grantee : (Collection) hashMap.get(permission)) {
                    if (!z) {
                        z = true;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(grantee.getTypeIdentifier());
                    sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                    sb.append("\"");
                    sb.append(grantee.getIdentifier());
                    sb.append("\"");
                }
                request.addHeader(permission.getHeaderName(), sb.toString());
            }
        }
    }

    @Override // com.amazonaws.services.p054s3.AmazonS3
    public void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        ValidationUtils.assertParameterNotNull(abortMultipartUploadRequest, "The request parameter must be specified when aborting a multipart upload");
        ValidationUtils.assertParameterNotNull(abortMultipartUploadRequest.getBucketName(), "The bucket name parameter must be specified when aborting a multipart upload");
        ValidationUtils.assertParameterNotNull(abortMultipartUploadRequest.getKey(), "The key parameter must be specified when aborting a multipart upload");
        ValidationUtils.assertParameterNotNull(abortMultipartUploadRequest.getUploadId(), "The upload ID parameter must be specified when aborting a multipart upload");
        String bucketName = abortMultipartUploadRequest.getBucketName();
        String key = abortMultipartUploadRequest.getKey();
        Request createRequest = createRequest(bucketName, key, abortMultipartUploadRequest, HttpMethodName.DELETE);
        createRequest.addParameter("uploadId", abortMultipartUploadRequest.getUploadId());
        populateRequesterPaysHeader(createRequest, abortMultipartUploadRequest.isRequesterPays());
        invoke(createRequest, this.voidResponseHandler, bucketName, key);
    }

    @Override // com.amazonaws.services.p054s3.AmazonS3
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        ValidationUtils.assertParameterNotNull(completeMultipartUploadRequest, "The request parameter must be specified when completing a multipart upload");
        String bucketName = completeMultipartUploadRequest.getBucketName();
        String key = completeMultipartUploadRequest.getKey();
        String uploadId = completeMultipartUploadRequest.getUploadId();
        ValidationUtils.assertParameterNotNull(bucketName, "The bucket name parameter must be specified when completing a multipart upload");
        ValidationUtils.assertParameterNotNull(key, "The key parameter must be specified when completing a multipart upload");
        ValidationUtils.assertParameterNotNull(uploadId, "The upload ID parameter must be specified when completing a multipart upload");
        ValidationUtils.assertParameterNotNull(completeMultipartUploadRequest.getPartETags(), "The part ETags parameter must be specified when completing a multipart upload");
        int i = 0;
        while (true) {
            Request createRequest = createRequest(bucketName, key, completeMultipartUploadRequest, HttpMethodName.POST);
            createRequest.addParameter("uploadId", uploadId);
            populateRequesterPaysHeader(createRequest, completeMultipartUploadRequest.isRequesterPays());
            byte[] convertToXmlByteArray = RequestXmlFactory.convertToXmlByteArray(completeMultipartUploadRequest.getPartETags());
            createRequest.addHeader("Content-Type", "application/xml");
            createRequest.addHeader("Content-Length", String.valueOf(convertToXmlByteArray.length));
            createRequest.setContent(new ByteArrayInputStream(convertToXmlByteArray));
            XmlResponsesSaxParser.CompleteMultipartUploadHandler completeMultipartUploadHandler = (XmlResponsesSaxParser.CompleteMultipartUploadHandler) invoke(createRequest, new ResponseHeaderHandlerChain(new Unmarshaller<XmlResponsesSaxParser.CompleteMultipartUploadHandler, InputStream>() { // from class: com.amazonaws.services.s3.model.transform.Unmarshallers$CompleteMultipartUploadResultUnmarshaller
                @Override // com.amazonaws.transform.Unmarshaller
                public XmlResponsesSaxParser.CompleteMultipartUploadHandler unmarshall(InputStream inputStream) throws Exception {
                    return new XmlResponsesSaxParser().parseCompleteMultipartUploadResponse(inputStream);
                }
            }, new ServerSideEncryptionHeaderHandler(), new ObjectExpirationHeaderHandler(), new S3VersionHeaderHandler(), new S3RequesterChargedHeaderHandler()), bucketName, key);
            if (completeMultipartUploadHandler.getCompleteMultipartUploadResult() != null) {
                return completeMultipartUploadHandler.getCompleteMultipartUploadResult();
            }
            int i2 = i + 1;
            if (!shouldRetryCompleteMultipartUpload(completeMultipartUploadRequest, completeMultipartUploadHandler.getAmazonS3Exception(), i)) {
                throw completeMultipartUploadHandler.getAmazonS3Exception();
            }
            i = i2;
        }
    }

    private boolean shouldRetryCompleteMultipartUpload(AmazonWebServiceRequest amazonWebServiceRequest, AmazonS3Exception amazonS3Exception, int i) {
        RetryPolicy retryPolicy = this.clientConfiguration.getRetryPolicy();
        if (retryPolicy == null || retryPolicy.getRetryCondition() == null || retryPolicy == PredefinedRetryPolicies.NO_RETRY_POLICY) {
            return false;
        }
        return this.completeMultipartUploadRetryCondition.shouldRetry(amazonWebServiceRequest, amazonS3Exception, i);
    }

    @Override // com.amazonaws.services.p054s3.AmazonS3
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        ValidationUtils.assertParameterNotNull(initiateMultipartUploadRequest, "The request parameter must be specified when initiating a multipart upload");
        ValidationUtils.assertParameterNotNull(initiateMultipartUploadRequest.getBucketName(), "The bucket name parameter must be specified when initiating a multipart upload");
        ValidationUtils.assertParameterNotNull(initiateMultipartUploadRequest.getKey(), "The key parameter must be specified when initiating a multipart upload");
        Request<?> createRequest = createRequest(initiateMultipartUploadRequest.getBucketName(), initiateMultipartUploadRequest.getKey(), initiateMultipartUploadRequest, HttpMethodName.POST);
        createRequest.addParameter("uploads", null);
        if (initiateMultipartUploadRequest.getStorageClass() != null) {
            createRequest.addHeader("x-amz-storage-class", initiateMultipartUploadRequest.getStorageClass().toString());
        }
        if (initiateMultipartUploadRequest.getRedirectLocation() != null) {
            createRequest.addHeader("x-amz-website-redirect-location", initiateMultipartUploadRequest.getRedirectLocation());
        }
        if (initiateMultipartUploadRequest.getAccessControlList() != null) {
            addAclHeaders(createRequest, initiateMultipartUploadRequest.getAccessControlList());
        } else if (initiateMultipartUploadRequest.getCannedACL() != null) {
            createRequest.addHeader("x-amz-acl", initiateMultipartUploadRequest.getCannedACL().toString());
        }
        ObjectMetadata objectMetadata = initiateMultipartUploadRequest.objectMetadata;
        if (objectMetadata != null) {
            populateRequestMetadata(createRequest, objectMetadata);
        }
        populateRequesterPaysHeader(createRequest, initiateMultipartUploadRequest.isRequesterPays());
        populateSSE_C(createRequest, initiateMultipartUploadRequest.getSSECustomerKey());
        populateSSE_KMS(createRequest, initiateMultipartUploadRequest.getSSEAwsKeyManagementParams());
        setZeroContentLength(createRequest);
        createRequest.setContent(new ByteArrayInputStream(new byte[0]));
        return (InitiateMultipartUploadResult) invoke(createRequest, new ResponseHeaderHandlerChain(new Unmarshaller<InitiateMultipartUploadResult, InputStream>() { // from class: com.amazonaws.services.s3.model.transform.Unmarshallers$InitiateMultipartUploadResultUnmarshaller
            @Override // com.amazonaws.transform.Unmarshaller
            public InitiateMultipartUploadResult unmarshall(InputStream inputStream) throws Exception {
                return new XmlResponsesSaxParser().parseInitiateMultipartUploadResponse(inputStream).getInitiateMultipartUploadResult();
            }
        }, new ServerSideEncryptionHeaderHandler()), initiateMultipartUploadRequest.getBucketName(), initiateMultipartUploadRequest.getKey());
    }

    @Override // com.amazonaws.services.p054s3.AmazonS3
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws AmazonClientException, AmazonServiceException {
        ProgressReportingInputStream inputSubstream;
        ValidationUtils.assertParameterNotNull(uploadPartRequest, "The request parameter must be specified when uploading a part");
        String bucketName = uploadPartRequest.getBucketName();
        String key = uploadPartRequest.getKey();
        String uploadId = uploadPartRequest.getUploadId();
        int partNumber = uploadPartRequest.getPartNumber();
        long partSize = uploadPartRequest.getPartSize();
        ValidationUtils.assertParameterNotNull(bucketName, "The bucket name parameter must be specified when uploading a part");
        ValidationUtils.assertParameterNotNull(key, "The key parameter must be specified when uploading a part");
        ValidationUtils.assertParameterNotNull(uploadId, "The upload ID parameter must be specified when uploading a part");
        ValidationUtils.assertParameterNotNull(Integer.valueOf(partNumber), "The part number parameter must be specified when uploading a part");
        ValidationUtils.assertParameterNotNull(Long.valueOf(partSize), "The part size parameter must be specified when uploading a part");
        Request createRequest = createRequest(bucketName, key, uploadPartRequest, HttpMethodName.PUT);
        createRequest.addParameter("uploadId", uploadId);
        createRequest.addParameter("partNumber", Integer.toString(partNumber));
        ObjectMetadata objectMetadata = uploadPartRequest.getObjectMetadata();
        if (objectMetadata != null) {
            populateRequestMetadata(createRequest, objectMetadata);
        }
        addHeaderIfNotNull(createRequest, "Content-MD5", uploadPartRequest.getMd5Digest());
        createRequest.addHeader("Content-Length", Long.toString(partSize));
        populateRequesterPaysHeader(createRequest, uploadPartRequest.isRequesterPays());
        populateSSE_C(createRequest, uploadPartRequest.getSSECustomerKey());
        if (uploadPartRequest.getInputStream() != null) {
            inputSubstream = uploadPartRequest.getInputStream();
        } else if (uploadPartRequest.getFile() != null) {
            try {
                inputSubstream = new InputSubstream(new RepeatableFileInputStream(uploadPartRequest.getFile()), uploadPartRequest.getFileOffset(), partSize, true);
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("The specified file doesn't exist", e);
            }
        } else {
            throw new IllegalArgumentException("A File or InputStream must be specified when uploading part");
        }
        MD5DigestCalculatingInputStream mD5DigestCalculatingInputStream = null;
        if (uploadPartRequest.getMd5Digest() == null && !ServiceUtils.skipMd5CheckPerRequest(uploadPartRequest)) {
            mD5DigestCalculatingInputStream = new MD5DigestCalculatingInputStream(inputSubstream);
            inputSubstream = mD5DigestCalculatingInputStream;
        }
        ProgressListenerCallbackExecutor wrapListener = ProgressListenerCallbackExecutor.wrapListener(uploadPartRequest.getGeneralProgressListener());
        if (wrapListener != null) {
            ProgressReportingInputStream progressReportingInputStream = new ProgressReportingInputStream(inputSubstream, wrapListener);
            progressReportingInputStream.setNotificationThreshold(this.notificationThreshold);
            fireProgressEvent(wrapListener, 1024);
            inputSubstream = progressReportingInputStream;
        }
        try {
            try {
                createRequest.setContent(inputSubstream);
                ObjectMetadata objectMetadata2 = (ObjectMetadata) invoke(createRequest, new S3MetadataResponseHandler(), bucketName, key);
                if (objectMetadata2 != null && mD5DigestCalculatingInputStream != null && !ServiceUtils.skipMd5CheckPerResponse(objectMetadata2) && !Arrays.equals(mD5DigestCalculatingInputStream.getMd5Digest(), BinaryUtils.fromHex(objectMetadata2.getETag()))) {
                    throw new AmazonClientException("Unable to verify integrity of data upload.  Client calculated content hash didn't match hash calculated by Amazon S3.  You may need to delete the data stored in Amazon S3.");
                }
                fireProgressEvent(wrapListener, 2048);
                UploadPartResult uploadPartResult = new UploadPartResult();
                uploadPartResult.setETag(objectMetadata2.getETag());
                uploadPartResult.setPartNumber(partNumber);
                uploadPartResult.setSSEAlgorithm(objectMetadata2.getSSEAlgorithm());
                uploadPartResult.setSSECustomerAlgorithm(objectMetadata2.getSSECustomerAlgorithm());
                uploadPartResult.setSSECustomerKeyMd5(objectMetadata2.getSSECustomerKeyMd5());
                uploadPartResult.setRequesterCharged(objectMetadata2.isRequesterCharged());
                if (inputSubstream != null) {
                    try {
                        inputSubstream.close();
                    } catch (Exception unused) {
                    }
                }
                return uploadPartResult;
            } catch (AmazonClientException e2) {
                fireProgressEvent(wrapListener, 4096);
                throw e2;
            }
        } catch (Throwable th) {
            if (inputSubstream != null) {
                try {
                    inputSubstream.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    private void fireProgressEvent(ProgressListenerCallbackExecutor progressListenerCallbackExecutor, int i) {
        if (progressListenerCallbackExecutor == null) {
            return;
        }
        ProgressEvent progressEvent = new ProgressEvent(0L);
        progressEvent.setEventCode(i);
        progressListenerCallbackExecutor.progressChanged(progressEvent);
    }

    protected Signer createSigner(Request<?> request, String str, String str2) {
        String signerRegionOverride;
        Signer signerByURI = getSignerByURI(this.clientOptions.isAccelerateModeEnabled() ? this.endpoint : request.getEndpoint());
        if (!isSignerOverridden()) {
            if ((signerByURI instanceof AWSS3V4Signer) && noExplicitRegionProvided(request)) {
                String str3 = this.clientRegion == null ? bucketRegionCache.get(str) : this.clientRegion;
                if (str3 != null) {
                    resolveRequestEndpoint(request, str, str2, RuntimeHttpUtils.toUri(RegionUtils.getRegion(str3).getServiceEndpoint("s3"), this.clientConfiguration));
                    AWSS3V4Signer aWSS3V4Signer = (AWSS3V4Signer) signerByURI;
                    setAWSS3V4SignerWithServiceNameAndRegion(aWSS3V4Signer, str3);
                    return aWSS3V4Signer;
                } else if (request.getOriginalRequest() instanceof GeneratePresignedUrlRequest) {
                    return createSigV2Signer(request, str, str2);
                }
            }
            if (getSignerRegionOverride() == null) {
                signerRegionOverride = this.clientRegion == null ? bucketRegionCache.get(str) : this.clientRegion;
            } else {
                signerRegionOverride = getSignerRegionOverride();
            }
            if (signerRegionOverride != null) {
                AWSS3V4Signer aWSS3V4Signer2 = new AWSS3V4Signer();
                setAWSS3V4SignerWithServiceNameAndRegion(aWSS3V4Signer2, signerRegionOverride);
                return aWSS3V4Signer2;
            }
        }
        return signerByURI instanceof S3Signer ? createSigV2Signer(request, str, str2) : signerByURI;
    }

    private void setAWSS3V4SignerWithServiceNameAndRegion(AWSS3V4Signer aWSS3V4Signer, String str) {
        aWSS3V4Signer.setServiceName(getServiceNameIntern());
        aWSS3V4Signer.setRegionName(str);
    }

    private boolean isSignerOverridden() {
        ClientConfiguration clientConfiguration = this.clientConfiguration;
        return (clientConfiguration == null || clientConfiguration.getSignerOverride() == null) ? false : true;
    }

    private boolean noExplicitRegionProvided(Request<?> request) {
        return isStandardEndpoint(request.getEndpoint()) && getSignerRegion() == null;
    }

    private String getSignerRegion() {
        String signerRegionOverride = getSignerRegionOverride();
        return signerRegionOverride == null ? this.clientRegion : signerRegionOverride;
    }

    private boolean isStandardEndpoint(URI uri) {
        return uri.getHost().endsWith("s3.amazonaws.com");
    }

    @Deprecated
    private S3Signer createSigV2Signer(Request<?> request, String str, String str2) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        if (str != null) {
            str3 = str + "/";
        } else {
            str3 = "";
        }
        sb.append(str3);
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        return new S3Signer(request.getHttpMethod().toString(), sb.toString());
    }

    private URI convertToVirtualHostEndpoint(URI uri, String str) {
        try {
            return new URI(uri.getScheme() + "://" + str + "." + uri.getAuthority());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid bucket name: " + str, e);
        }
    }

    protected static void populateRequestMetadata(Request<?> request, ObjectMetadata objectMetadata) {
        Map<String, Object> rawMetadata = objectMetadata.getRawMetadata();
        if (rawMetadata.get("x-amz-server-side-encryption-aws-kms-key-id") != null && !ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION.equals(rawMetadata.get("x-amz-server-side-encryption"))) {
            throw new IllegalArgumentException("If you specify a KMS key id for server side encryption, you must also set the SSEAlgorithm to ObjectMetadata.KMS_SERVER_SIDE_ENCRYPTION");
        }
        if (rawMetadata != null) {
            for (Map.Entry<String, Object> entry : rawMetadata.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        Date httpExpiresDate = objectMetadata.getHttpExpiresDate();
        if (httpExpiresDate != null) {
            request.addHeader("Expires", DateUtils.formatRFC822Date(httpExpiresDate));
        }
        Map<String, String> userMetadata = objectMetadata.getUserMetadata();
        if (userMetadata == null) {
            return;
        }
        for (Map.Entry<String, String> entry2 : userMetadata.entrySet()) {
            String key = entry2.getKey();
            String value = entry2.getValue();
            if (key != null) {
                key = key.trim();
            }
            if (value != null) {
                value = value.trim();
            }
            request.addHeader("x-amz-meta-" + key, value);
        }
    }

    private static void populateSSE_C(Request<?> request, SSECustomerKey sSECustomerKey) {
        if (sSECustomerKey == null) {
            return;
        }
        addHeaderIfNotNull(request, "x-amz-server-side-encryption-customer-algorithm", sSECustomerKey.getAlgorithm());
        addHeaderIfNotNull(request, "x-amz-server-side-encryption-customer-key", sSECustomerKey.getKey());
        addHeaderIfNotNull(request, "x-amz-server-side-encryption-customer-key-MD5", sSECustomerKey.getMd5());
        if (sSECustomerKey.getKey() == null || sSECustomerKey.getMd5() != null) {
            return;
        }
        request.addHeader("x-amz-server-side-encryption-customer-key-MD5", Md5Utils.md5AsBase64(Base64.decode(sSECustomerKey.getKey())));
    }

    private static void populateSSE_KMS(Request<?> request, SSEAwsKeyManagementParams sSEAwsKeyManagementParams) {
        if (sSEAwsKeyManagementParams != null) {
            addHeaderIfNotNull(request, "x-amz-server-side-encryption", sSEAwsKeyManagementParams.getEncryption());
            addHeaderIfNotNull(request, "x-amz-server-side-encryption-aws-kms-key-id", sSEAwsKeyManagementParams.getAwsKmsKeyId());
        }
    }

    private static void addHeaderIfNotNull(Request<?> request, String str, String str2) {
        if (str2 != null) {
            request.addHeader(str, str2);
        }
    }

    private static void addDateHeader(Request<?> request, String str, Date date) {
        if (date != null) {
            request.addHeader(str, ServiceUtils.formatRfc822Date(date));
        }
    }

    private static void addStringListHeader(Request<?> request, String str, List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        request.addHeader(str, ServiceUtils.join(list));
    }

    private static void addResponseHeaderParameters(Request<?> request, ResponseHeaderOverrides responseHeaderOverrides) {
        if (responseHeaderOverrides == null) {
            return;
        }
        responseHeaderOverrides.getCacheControl();
        throw null;
    }

    protected <X extends AmazonWebServiceRequest> Request<X> createRequest(String str, String str2, X x, HttpMethodName httpMethodName) {
        return createRequest(str, str2, x, httpMethodName, null);
    }

    protected <X extends AmazonWebServiceRequest> Request<X> createRequest(String str, String str2, X x, HttpMethodName httpMethodName, URI uri) {
        DefaultRequest defaultRequest = new DefaultRequest(x, "Amazon S3");
        if (this.clientOptions.isAccelerateModeEnabled() && !(defaultRequest.getOriginalRequest() instanceof S3AccelerateUnsupported)) {
            if (this.clientOptions.isDualstackEnabled()) {
                uri = RuntimeHttpUtils.toUri("s3-accelerate.dualstack.amazonaws.com", this.clientConfiguration);
            } else {
                uri = RuntimeHttpUtils.toUri("s3-accelerate.amazonaws.com", this.clientConfiguration);
            }
        }
        defaultRequest.setHttpMethod(httpMethodName);
        resolveRequestEndpoint(defaultRequest, str, str2, uri);
        return defaultRequest;
    }

    protected final ExecutionContext createExecutionContext(AmazonWebServiceRequest amazonWebServiceRequest) {
        return new S3ExecutionContext(this.requestHandler2s, isRequestMetricsEnabled(amazonWebServiceRequest) || AmazonWebServiceClient.isProfilingEnabled(), this);
    }

    private <X, Y extends AmazonWebServiceRequest> X invoke(Request<Y> request, HttpResponseHandler<AmazonWebServiceResponse<X>> httpResponseHandler, String str, String str2) {
        AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
        ExecutionContext createExecutionContext = createExecutionContext(originalRequest);
        AWSRequestMetrics awsRequestMetrics = createExecutionContext.getAwsRequestMetrics();
        request.setAWSRequestMetrics(awsRequestMetrics);
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        Response<?> response = null;
        try {
            try {
                request.setTimeOffset(this.timeOffset);
                if (!request.getHeaders().containsKey("Content-Type")) {
                    request.addHeader("Content-Type", "application/octet-stream");
                }
                if (str != null) {
                    request.getOriginalRequest();
                    if (noExplicitRegionProvided(request)) {
                        fetchRegionFromCache(str);
                    }
                }
                AWSCredentials credentials = this.awsCredentialsProvider.getCredentials();
                if (originalRequest.getRequestCredentials() != null) {
                    credentials = originalRequest.getRequestCredentials();
                }
                createExecutionContext.setSigner(createSigner(request, str, str2));
                createExecutionContext.setCredentials(credentials);
                response = this.client.execute(request, httpResponseHandler, this.errorResponseHandler, createExecutionContext);
                return (X) response.getAwsResponse();
            } catch (AmazonS3Exception e) {
                if (e.getStatusCode() == 301 && e.getAdditionalDetails() != null) {
                    String str3 = e.getAdditionalDetails().get("x-amz-bucket-region");
                    bucketRegionCache.put(str, str3);
                    e.setErrorMessage("The bucket is in this region: " + str3 + ". Please use this region to retry the request");
                }
                throw e;
            }
        } finally {
            endClientExecution(awsRequestMetrics, request, response);
        }
    }

    private void setZeroContentLength(Request<?> request) {
        request.addHeader("Content-Length", String.valueOf(0));
    }

    private ByteArrayInputStream toByteArray(InputStream inputStream) {
        int i = 262144;
        byte[] bArr = new byte[262144];
        int i2 = 0;
        while (i > 0) {
            try {
                int read = inputStream.read(bArr, i2, i);
                if (read == -1) {
                    break;
                }
                i2 += read;
                i -= read;
            } catch (IOException e) {
                throw new AmazonClientException("Failed to read from inputstream", e);
            }
        }
        if (inputStream.read() != -1) {
            throw new AmazonClientException("Input stream exceeds 256k buffer.");
        }
        inputStream.close();
        return new ByteArrayInputStream(bArr, 0, i2);
    }

    private String urlEncodeTags(ObjectTagging objectTagging) {
        if (objectTagging == null || objectTagging.getTagSet() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<Tag> it2 = objectTagging.getTagSet().iterator();
        while (it2.hasNext()) {
            Tag next = it2.next();
            sb.append(S3HttpUtils.urlEncode(next.getKey(), false));
            sb.append('=');
            sb.append(S3HttpUtils.urlEncode(next.getValue(), false));
            if (it2.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    protected static void populateRequesterPaysHeader(Request<?> request, boolean z) {
        if (z) {
            request.addHeader("x-amz-request-payer", "requester");
        }
    }

    private String fetchRegionFromCache(String str) {
        String str2 = bucketRegionCache.get(str);
        if (str2 == null) {
            if (log.isDebugEnabled()) {
                Log log2 = log;
                log2.debug("Bucket region cache doesn't have an entry for " + str + ". Trying to get bucket region from Amazon S3.");
            }
            str2 = getBucketRegionViaHeadRequest(str);
            if (str2 != null) {
                bucketRegionCache.put(str, str2);
            }
        }
        if (log.isDebugEnabled()) {
            Log log3 = log;
            log3.debug("Region for " + str + " is " + str2);
        }
        return str2;
    }

    private String getBucketRegionViaHeadRequest(String str) {
        String str2 = null;
        try {
            str2 = ((HeadBucketResult) invoke(createRequest(str, null, new HeadBucketRequest(str), HttpMethodName.HEAD, new URI("https://s3-us-west-1.amazonaws.com")), new HeadBucketResultHandler(), str, null)).getBucketRegion();
        } catch (AmazonS3Exception e) {
            if (e.getAdditionalDetails() != null) {
                str2 = e.getAdditionalDetails().get("x-amz-bucket-region");
            }
        } catch (URISyntaxException unused) {
            log.warn("Error while creating URI");
        }
        if (str2 == null && log.isDebugEnabled()) {
            Log log2 = log;
            log2.debug("Not able to derive region of the " + str + " from the HEAD Bucket requests.");
        }
        return str2;
    }

    public void resolveRequestEndpoint(Request<?> request, String str, String str2, URI uri) {
        if (uri == null) {
            uri = this.endpoint;
        }
        if (shouldUseVirtualAddressing(uri, str)) {
            Log log2 = log;
            log2.debug("Using virtual style addressing. Endpoint = " + uri);
            request.setEndpoint(convertToVirtualHostEndpoint(uri, str));
            request.setResourcePath(getHostStyleResourcePath(str2));
        } else {
            Log log3 = log;
            log3.debug("Using path style addressing. Endpoint = " + uri);
            request.setEndpoint(uri);
            if (str != null) {
                request.setResourcePath(getPathStyleResourcePath(str, str2));
            }
        }
        Log log4 = log;
        log4.debug("Key: " + str2 + "; Request: " + request);
    }

    private boolean shouldUseVirtualAddressing(URI uri, String str) {
        return !this.clientOptions.isPathStyleAccess() && BucketNameUtils.isDNSBucketName(str) && !isValidIpV4Address(uri.getHost());
    }

    private String getHostStyleResourcePath(String str) {
        if (str == null || !str.startsWith("/")) {
            return str;
        }
        return "/" + str;
    }

    private String getPathStyleResourcePath(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/");
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        return sb.toString();
    }

    static boolean isValidIpV4Address(String str) {
        if (str == null) {
            return false;
        }
        String[] split = str.split("\\.");
        if (split.length != 4) {
            return false;
        }
        for (String str2 : split) {
            try {
                int parseInt = Integer.parseInt(str2);
                if (parseInt >= 0 && parseInt <= 255) {
                }
            } catch (NumberFormatException unused) {
            }
            return false;
        }
        return true;
    }
}
