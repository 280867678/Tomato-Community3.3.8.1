package com.amazonaws.services.p054s3.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.services.p054s3.internal.DeleteObjectsResponse;
import com.amazonaws.services.p054s3.internal.ObjectExpirationResult;
import com.amazonaws.services.p054s3.internal.S3HttpUtils;
import com.amazonaws.services.p054s3.internal.S3RequesterChargedResult;
import com.amazonaws.services.p054s3.internal.S3VersionResult;
import com.amazonaws.services.p054s3.internal.ServerSideEncryptionResult;
import com.amazonaws.services.p054s3.internal.ServiceUtils;
import com.amazonaws.services.p054s3.model.AbortIncompleteMultipartUpload;
import com.amazonaws.services.p054s3.model.AccessControlList;
import com.amazonaws.services.p054s3.model.AmazonS3Exception;
import com.amazonaws.services.p054s3.model.Bucket;
import com.amazonaws.services.p054s3.model.BucketAccelerateConfiguration;
import com.amazonaws.services.p054s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.p054s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.p054s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.p054s3.model.BucketReplicationConfiguration;
import com.amazonaws.services.p054s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.p054s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.p054s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.p054s3.model.CORSRule;
import com.amazonaws.services.p054s3.model.CanonicalGrantee;
import com.amazonaws.services.p054s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.p054s3.model.CopyObjectResult;
import com.amazonaws.services.p054s3.model.DeleteObjectsResult;
import com.amazonaws.services.p054s3.model.EmailAddressGrantee;
import com.amazonaws.services.p054s3.model.GetBucketInventoryConfigurationResult;
import com.amazonaws.services.p054s3.model.GetObjectTaggingResult;
import com.amazonaws.services.p054s3.model.Grantee;
import com.amazonaws.services.p054s3.model.GroupGrantee;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.p054s3.model.ListBucketAnalyticsConfigurationsResult;
import com.amazonaws.services.p054s3.model.ListBucketInventoryConfigurationsResult;
import com.amazonaws.services.p054s3.model.ListBucketMetricsConfigurationsResult;
import com.amazonaws.services.p054s3.model.ListObjectsV2Result;
import com.amazonaws.services.p054s3.model.MultiObjectDeleteException;
import com.amazonaws.services.p054s3.model.MultipartUpload;
import com.amazonaws.services.p054s3.model.MultipartUploadListing;
import com.amazonaws.services.p054s3.model.ObjectListing;
import com.amazonaws.services.p054s3.model.Owner;
import com.amazonaws.services.p054s3.model.PartListing;
import com.amazonaws.services.p054s3.model.PartSummary;
import com.amazonaws.services.p054s3.model.Permission;
import com.amazonaws.services.p054s3.model.RedirectRule;
import com.amazonaws.services.p054s3.model.ReplicationDestinationConfig;
import com.amazonaws.services.p054s3.model.ReplicationRule;
import com.amazonaws.services.p054s3.model.RoutingRule;
import com.amazonaws.services.p054s3.model.RoutingRuleCondition;
import com.amazonaws.services.p054s3.model.S3ObjectSummary;
import com.amazonaws.services.p054s3.model.S3VersionSummary;
import com.amazonaws.services.p054s3.model.Tag;
import com.amazonaws.services.p054s3.model.TagSet;
import com.amazonaws.services.p054s3.model.VersionListing;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsAndOperator;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsConfiguration;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsExportDestination;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsFilter;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsFilterPredicate;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsPrefixPredicate;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsS3BucketDestination;
import com.amazonaws.services.p054s3.model.analytics.AnalyticsTagPredicate;
import com.amazonaws.services.p054s3.model.analytics.StorageClassAnalysis;
import com.amazonaws.services.p054s3.model.analytics.StorageClassAnalysisDataExport;
import com.amazonaws.services.p054s3.model.inventory.InventoryConfiguration;
import com.amazonaws.services.p054s3.model.inventory.InventoryDestination;
import com.amazonaws.services.p054s3.model.inventory.InventoryFilter;
import com.amazonaws.services.p054s3.model.inventory.InventoryPrefixPredicate;
import com.amazonaws.services.p054s3.model.inventory.InventoryS3BucketDestination;
import com.amazonaws.services.p054s3.model.inventory.InventorySchedule;
import com.amazonaws.services.p054s3.model.lifecycle.LifecycleAndOperator;
import com.amazonaws.services.p054s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.p054s3.model.lifecycle.LifecycleFilterPredicate;
import com.amazonaws.services.p054s3.model.lifecycle.LifecyclePrefixPredicate;
import com.amazonaws.services.p054s3.model.lifecycle.LifecycleTagPredicate;
import com.amazonaws.services.p054s3.model.metrics.MetricsAndOperator;
import com.amazonaws.services.p054s3.model.metrics.MetricsConfiguration;
import com.amazonaws.services.p054s3.model.metrics.MetricsFilter;
import com.amazonaws.services.p054s3.model.metrics.MetricsFilterPredicate;
import com.amazonaws.services.p054s3.model.metrics.MetricsPrefixPredicate;
import com.amazonaws.services.p054s3.model.metrics.MetricsTagPredicate;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser */
/* loaded from: classes2.dex */
public class XmlResponsesSaxParser {
    private static final Log log = LogFactory.getLog(XmlResponsesSaxParser.class);

    /* renamed from: xr */
    private XMLReader f1192xr;

    public XmlResponsesSaxParser() throws AmazonClientException {
        this.f1192xr = null;
        try {
            this.f1192xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
            try {
                this.f1192xr = XMLReaderFactory.createXMLReader();
            } catch (SAXException unused) {
                throw new AmazonClientException("Couldn't initialize a sax driver for the XMLReader", e);
            }
        }
    }

    protected void parseXmlInputStream(DefaultHandler defaultHandler, InputStream inputStream) throws IOException {
        try {
            if (log.isDebugEnabled()) {
                Log log2 = log;
                log2.debug("Parsing XML response document with handler: " + defaultHandler.getClass());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            this.f1192xr.setContentHandler(defaultHandler);
            this.f1192xr.setErrorHandler(defaultHandler);
            this.f1192xr.parse(new InputSource(bufferedReader));
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                if (log.isErrorEnabled()) {
                    log.error("Unable to close response InputStream up after XML parse failure", e2);
                }
            }
            throw new AmazonClientException("Failed to parse XML document with handler " + defaultHandler.getClass(), th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String checkForEmptyString(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log log2 = log;
            log2.error("Unable to parse integer value '" + str + "'", e);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            Log log2 = log;
            log2.error("Unable to parse long value '" + str + "'", e);
            return -1L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String findAttributeValue(String str, Attributes attributes) {
        if (!StringUtils.isBlank(str) && attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (attributes.getQName(i).trim().equalsIgnoreCase(str.trim())) {
                    return attributes.getValue(i);
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String decodeIfSpecified(String str, boolean z) {
        return z ? S3HttpUtils.urlDecode(str) : str;
    }

    public CompleteMultipartUploadHandler parseCompleteMultipartUploadResponse(InputStream inputStream) throws IOException {
        CompleteMultipartUploadHandler completeMultipartUploadHandler = new CompleteMultipartUploadHandler();
        parseXmlInputStream(completeMultipartUploadHandler, inputStream);
        return completeMultipartUploadHandler;
    }

    public InitiateMultipartUploadHandler parseInitiateMultipartUploadResponse(InputStream inputStream) throws IOException {
        InitiateMultipartUploadHandler initiateMultipartUploadHandler = new InitiateMultipartUploadHandler();
        parseXmlInputStream(initiateMultipartUploadHandler, inputStream);
        return initiateMultipartUploadHandler;
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListBucketHandler */
    /* loaded from: classes2.dex */
    public static class ListBucketHandler extends AbstractHandler {
        private S3ObjectSummary currentObject;
        private Owner currentOwner;
        private String lastKey;
        private final ObjectListing objectListing;
        private final boolean shouldSDKDecodeResponse;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListBucketResult")) {
                if (!str2.equals("Contents")) {
                    return;
                }
                this.currentObject = new S3ObjectSummary();
                this.currentObject.setBucketName(this.objectListing.getBucketName());
            } else if (!m4185in("ListBucketResult", "Contents") || !str2.equals("Owner")) {
            } else {
                this.currentOwner = new Owner();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            String str4 = null;
            if (atTopLevel()) {
                if (!str2.equals("ListBucketResult") || !this.objectListing.isTruncated() || this.objectListing.getNextMarker() != null) {
                    return;
                }
                if (!this.objectListing.getObjectSummaries().isEmpty()) {
                    str4 = this.objectListing.getObjectSummaries().get(this.objectListing.getObjectSummaries().size() - 1).getKey();
                } else if (this.objectListing.getCommonPrefixes().isEmpty()) {
                    XmlResponsesSaxParser.log.error("S3 response indicates truncated results, but contains no object summaries or common prefixes.");
                } else {
                    str4 = this.objectListing.getCommonPrefixes().get(this.objectListing.getCommonPrefixes().size() - 1);
                }
                this.objectListing.setNextMarker(str4);
            } else if (m4185in("ListBucketResult")) {
                if (str2.equals("Name")) {
                    this.objectListing.setBucketName(getText());
                    if (!XmlResponsesSaxParser.log.isDebugEnabled()) {
                        return;
                    }
                    Log log = XmlResponsesSaxParser.log;
                    log.debug("Examining listing for bucket: " + this.objectListing.getBucketName());
                } else if (str2.equals("Prefix")) {
                    this.objectListing.setPrefix(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                } else if (str2.equals("Marker")) {
                    this.objectListing.setMarker(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                } else if (str2.equals("NextMarker")) {
                    this.objectListing.setNextMarker(XmlResponsesSaxParser.decodeIfSpecified(getText(), this.shouldSDKDecodeResponse));
                } else if (str2.equals("MaxKeys")) {
                    this.objectListing.setMaxKeys(XmlResponsesSaxParser.parseInt(getText()));
                } else if (str2.equals("Delimiter")) {
                    this.objectListing.setDelimiter(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                } else if (str2.equals("EncodingType")) {
                    this.objectListing.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("IsTruncated")) {
                    String lowerCase = StringUtils.lowerCase(getText());
                    if (lowerCase.startsWith("false")) {
                        this.objectListing.setTruncated(false);
                    } else if (lowerCase.startsWith("true")) {
                        this.objectListing.setTruncated(true);
                    } else {
                        throw new IllegalStateException("Invalid value for IsTruncated field: " + lowerCase);
                    }
                } else if (!str2.equals("Contents")) {
                } else {
                    this.objectListing.getObjectSummaries().add(this.currentObject);
                    this.currentObject = null;
                }
            } else if (m4185in("ListBucketResult", "Contents")) {
                if (str2.equals("Key")) {
                    this.lastKey = getText();
                    this.currentObject.setKey(XmlResponsesSaxParser.decodeIfSpecified(this.lastKey, this.shouldSDKDecodeResponse));
                } else if (str2.equals("LastModified")) {
                    this.currentObject.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                } else if (str2.equals("ETag")) {
                    this.currentObject.setETag(ServiceUtils.removeQuotes(getText()));
                } else if (str2.equals("Size")) {
                    this.currentObject.setSize(XmlResponsesSaxParser.parseLong(getText()));
                } else if (str2.equals("StorageClass")) {
                    this.currentObject.setStorageClass(getText());
                } else if (!str2.equals("Owner")) {
                } else {
                    this.currentObject.setOwner(this.currentOwner);
                    this.currentOwner = null;
                }
            } else if (m4185in("ListBucketResult", "Contents", "Owner")) {
                if (str2.equals("ID")) {
                    this.currentOwner.setId(getText());
                } else if (!str2.equals("DisplayName")) {
                } else {
                    this.currentOwner.setDisplayName(getText());
                }
            } else if (!m4185in("ListBucketResult", "CommonPrefixes") || !str2.equals("Prefix")) {
            } else {
                this.objectListing.getCommonPrefixes().add(XmlResponsesSaxParser.decodeIfSpecified(getText(), this.shouldSDKDecodeResponse));
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListObjectsV2Handler */
    /* loaded from: classes2.dex */
    public static class ListObjectsV2Handler extends AbstractHandler {
        private S3ObjectSummary currentObject;
        private Owner currentOwner;
        private String lastKey;
        private final ListObjectsV2Result result;
        private final boolean shouldSDKDecodeResponse;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListBucketResult")) {
                if (!str2.equals("Contents")) {
                    return;
                }
                this.currentObject = new S3ObjectSummary();
                this.result.getBucketName();
                throw null;
            } else if (!m4185in("ListBucketResult", "Contents") || !str2.equals("Owner")) {
            } else {
                this.currentOwner = new Owner();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (atTopLevel()) {
                if (!str2.equals("ListBucketResult")) {
                    return;
                }
                this.result.isTruncated();
                throw null;
            } else if (m4185in("ListBucketResult")) {
                if (str2.equals("Name")) {
                    this.result.setBucketName(getText());
                    throw null;
                } else if (str2.equals("Prefix")) {
                    this.result.setPrefix(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                    throw null;
                } else if (str2.equals("MaxKeys")) {
                    this.result.setMaxKeys(XmlResponsesSaxParser.parseInt(getText()));
                    throw null;
                } else if (str2.equals("NextContinuationToken")) {
                    this.result.setNextContinuationToken(getText());
                    throw null;
                } else if (str2.equals("ContinuationToken")) {
                    this.result.setContinuationToken(getText());
                    throw null;
                } else if (str2.equals("StartAfter")) {
                    this.result.setStartAfter(XmlResponsesSaxParser.decodeIfSpecified(getText(), this.shouldSDKDecodeResponse));
                    throw null;
                } else if (str2.equals("KeyCount")) {
                    this.result.setKeyCount(XmlResponsesSaxParser.parseInt(getText()));
                    throw null;
                } else if (str2.equals("Delimiter")) {
                    this.result.setDelimiter(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                    throw null;
                } else if (str2.equals("EncodingType")) {
                    this.result.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
                    throw null;
                } else if (str2.equals("IsTruncated")) {
                    String lowerCase = StringUtils.lowerCase(getText());
                    if (lowerCase.startsWith("false")) {
                        this.result.setTruncated(false);
                        throw null;
                    } else if (lowerCase.startsWith("true")) {
                        this.result.setTruncated(true);
                        throw null;
                    } else {
                        throw new IllegalStateException("Invalid value for IsTruncated field: " + lowerCase);
                    }
                } else if (!str2.equals("Contents")) {
                } else {
                    this.result.getObjectSummaries();
                    throw null;
                }
            } else if (m4185in("ListBucketResult", "Contents")) {
                if (str2.equals("Key")) {
                    this.lastKey = getText();
                    this.currentObject.setKey(XmlResponsesSaxParser.decodeIfSpecified(this.lastKey, this.shouldSDKDecodeResponse));
                } else if (str2.equals("LastModified")) {
                    this.currentObject.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                } else if (str2.equals("ETag")) {
                    this.currentObject.setETag(ServiceUtils.removeQuotes(getText()));
                } else if (str2.equals("Size")) {
                    this.currentObject.setSize(XmlResponsesSaxParser.parseLong(getText()));
                } else if (str2.equals("StorageClass")) {
                    this.currentObject.setStorageClass(getText());
                } else if (!str2.equals("Owner")) {
                } else {
                    this.currentObject.setOwner(this.currentOwner);
                    this.currentOwner = null;
                }
            } else if (m4185in("ListBucketResult", "Contents", "Owner")) {
                if (str2.equals("ID")) {
                    this.currentOwner.setId(getText());
                } else if (!str2.equals("DisplayName")) {
                } else {
                    this.currentOwner.setDisplayName(getText());
                }
            } else if (!m4185in("ListBucketResult", "CommonPrefixes") || !str2.equals("Prefix")) {
            } else {
                this.result.getCommonPrefixes();
                throw null;
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListAllMyBucketsHandler */
    /* loaded from: classes2.dex */
    public static class ListAllMyBucketsHandler extends AbstractHandler {
        private final List<Bucket> buckets = new ArrayList();
        private Owner bucketsOwner = null;
        private Bucket currentBucket = null;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListAllMyBucketsResult")) {
                if (!str2.equals("Owner")) {
                    return;
                }
                this.bucketsOwner = new Owner();
            } else if (!m4185in("ListAllMyBucketsResult", "Buckets") || !str2.equals("Bucket")) {
            } else {
                this.currentBucket = new Bucket();
                this.currentBucket.setOwner(this.bucketsOwner);
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ListAllMyBucketsResult", "Owner")) {
                if (str2.equals("ID")) {
                    this.bucketsOwner.setId(getText());
                } else if (!str2.equals("DisplayName")) {
                } else {
                    this.bucketsOwner.setDisplayName(getText());
                }
            } else if (m4185in("ListAllMyBucketsResult", "Buckets")) {
                if (!str2.equals("Bucket")) {
                    return;
                }
                this.buckets.add(this.currentBucket);
                this.currentBucket = null;
            } else if (!m4185in("ListAllMyBucketsResult", "Buckets", "Bucket")) {
            } else {
                if (str2.equals("Name")) {
                    this.currentBucket.setName(getText());
                } else if (!str2.equals("CreationDate")) {
                } else {
                    this.currentBucket.setCreationDate(DateUtils.parseISO8601Date(getText()));
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$AccessControlListHandler */
    /* loaded from: classes2.dex */
    public static class AccessControlListHandler extends AbstractHandler {
        private final AccessControlList accessControlList = new AccessControlList();
        private Grantee currentGrantee = null;
        private Permission currentPermission = null;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("AccessControlPolicy")) {
                if (!str2.equals("Owner")) {
                    return;
                }
                this.accessControlList.setOwner(new Owner());
            } else if (!m4185in("AccessControlPolicy", "AccessControlList", "Grant") || !str2.equals("Grantee")) {
            } else {
                String findAttributeValue = XmlResponsesSaxParser.findAttributeValue("xsi:type", attributes);
                if ("AmazonCustomerByEmail".equals(findAttributeValue)) {
                    this.currentGrantee = new EmailAddressGrantee(null);
                } else if ("CanonicalUser".equals(findAttributeValue)) {
                    this.currentGrantee = new CanonicalGrantee(null);
                } else {
                    "Group".equals(findAttributeValue);
                }
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("AccessControlPolicy", "Owner")) {
                if (str2.equals("ID")) {
                    this.accessControlList.getOwner().setId(getText());
                } else if (!str2.equals("DisplayName")) {
                } else {
                    this.accessControlList.getOwner().setDisplayName(getText());
                }
            } else if (m4185in("AccessControlPolicy", "AccessControlList")) {
                if (!str2.equals("Grant")) {
                    return;
                }
                this.accessControlList.grantPermission(this.currentGrantee, this.currentPermission);
                this.currentGrantee = null;
                this.currentPermission = null;
            } else if (m4185in("AccessControlPolicy", "AccessControlList", "Grant")) {
                if (!str2.equals("Permission")) {
                    return;
                }
                this.currentPermission = Permission.parsePermission(getText());
            } else if (!m4185in("AccessControlPolicy", "AccessControlList", "Grant", "Grantee")) {
            } else {
                if (str2.equals("ID")) {
                    this.currentGrantee.setIdentifier(getText());
                } else if (str2.equals("EmailAddress")) {
                    this.currentGrantee.setIdentifier(getText());
                } else if (str2.equals("URI")) {
                    this.currentGrantee = GroupGrantee.parseGroupGrantee(getText());
                } else if (!str2.equals("DisplayName")) {
                } else {
                    ((CanonicalGrantee) this.currentGrantee).setDisplayName(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketLoggingConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketLoggingConfigurationHandler extends AbstractHandler {
        private final BucketLoggingConfiguration bucketLoggingConfiguration = new BucketLoggingConfiguration();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("BucketLoggingStatus", "LoggingEnabled")) {
                if (str2.equals("TargetBucket")) {
                    this.bucketLoggingConfiguration.setDestinationBucketName(getText());
                } else if (!str2.equals("TargetPrefix")) {
                } else {
                    this.bucketLoggingConfiguration.setLogFilePrefix(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketLocationHandler */
    /* loaded from: classes2.dex */
    public static class BucketLocationHandler extends AbstractHandler {
        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (!atTopLevel() || !str2.equals("LocationConstraint")) {
                return;
            }
            getText().length();
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$CopyObjectResultHandler */
    /* loaded from: classes2.dex */
    public static class CopyObjectResultHandler extends AbstractSSEHandler implements ObjectExpirationResult, S3RequesterChargedResult, S3VersionResult {
        private final CopyObjectResult result = new CopyObjectResult();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractSSEHandler
        protected ServerSideEncryptionResult sseResult() {
            return this.result;
        }

        @Override // com.amazonaws.services.p054s3.internal.S3VersionResult
        public void setVersionId(String str) {
            this.result.setVersionId(str);
        }

        @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
        public void setExpirationTime(Date date) {
            this.result.setExpirationTime(date);
        }

        @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
        public void setExpirationTimeRuleId(String str) {
            this.result.setExpirationTimeRuleId(str);
        }

        @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
        public void setRequesterCharged(boolean z) {
            this.result.setRequesterCharged(z);
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (!atTopLevel() || str2.equals("CopyObjectResult") || str2.equals("CopyPartResult")) {
                return;
            }
            str2.equals("Error");
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("CopyObjectResult") || m4185in("CopyPartResult")) {
                if (str2.equals("LastModified")) {
                    this.result.setLastModifiedDate(ServiceUtils.parseIso8601Date(getText()));
                } else if (!str2.equals("ETag")) {
                } else {
                    this.result.setETag(ServiceUtils.removeQuotes(getText()));
                }
            } else if (!m4185in("Error")) {
            } else {
                if (str2.equals("Code")) {
                    getText();
                } else if (str2.equals("Message")) {
                    getText();
                } else if (str2.equals("RequestId")) {
                    getText();
                } else if (!str2.equals("HostId")) {
                } else {
                    getText();
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$RequestPaymentConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class RequestPaymentConfigurationHandler extends AbstractHandler {
        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (!m4185in("RequestPaymentConfiguration") || !str2.equals("Payer")) {
                return;
            }
            getText();
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListVersionsHandler */
    /* loaded from: classes2.dex */
    public static class ListVersionsHandler extends AbstractHandler {
        private Owner currentOwner;
        private S3VersionSummary currentVersionSummary;
        private final boolean shouldSDKDecodeResponse;
        private final VersionListing versionListing;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListVersionsResult")) {
                if (str2.equals("Version")) {
                    this.currentVersionSummary = new S3VersionSummary();
                    this.versionListing.getBucketName();
                    throw null;
                } else if (!str2.equals("DeleteMarker")) {
                } else {
                    this.currentVersionSummary = new S3VersionSummary();
                    this.versionListing.getBucketName();
                    throw null;
                }
            } else if ((!m4185in("ListVersionsResult", "Version") && !m4185in("ListVersionsResult", "DeleteMarker")) || !str2.equals("Owner")) {
            } else {
                this.currentOwner = new Owner();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ListVersionsResult")) {
                if (str2.equals("Name")) {
                    this.versionListing.setBucketName(getText());
                    throw null;
                } else if (str2.equals("Prefix")) {
                    this.versionListing.setPrefix(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                    throw null;
                } else if (str2.equals("KeyMarker")) {
                    this.versionListing.setKeyMarker(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                    throw null;
                } else if (str2.equals("VersionIdMarker")) {
                    this.versionListing.setVersionIdMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
                    throw null;
                } else if (str2.equals("MaxKeys")) {
                    this.versionListing.setMaxKeys(Integer.parseInt(getText()));
                    throw null;
                } else if (str2.equals("Delimiter")) {
                    this.versionListing.setDelimiter(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                    throw null;
                } else if (str2.equals("EncodingType")) {
                    this.versionListing.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
                    throw null;
                } else if (str2.equals("NextKeyMarker")) {
                    this.versionListing.setNextKeyMarker(XmlResponsesSaxParser.decodeIfSpecified(XmlResponsesSaxParser.checkForEmptyString(getText()), this.shouldSDKDecodeResponse));
                    throw null;
                } else if (str2.equals("NextVersionIdMarker")) {
                    this.versionListing.setNextVersionIdMarker(getText());
                    throw null;
                } else if (str2.equals("IsTruncated")) {
                    this.versionListing.setTruncated("true".equals(getText()));
                    throw null;
                } else if (!str2.equals("Version") && !str2.equals("DeleteMarker")) {
                } else {
                    this.versionListing.getVersionSummaries();
                    throw null;
                }
            } else if (m4185in("ListVersionsResult", "CommonPrefixes")) {
                if (!str2.equals("Prefix")) {
                    return;
                }
                XmlResponsesSaxParser.checkForEmptyString(getText());
                this.versionListing.getCommonPrefixes();
                throw null;
            } else if (m4185in("ListVersionsResult", "Version") || m4185in("ListVersionsResult", "DeleteMarker")) {
                if (str2.equals("Key")) {
                    this.currentVersionSummary.setKey(XmlResponsesSaxParser.decodeIfSpecified(getText(), this.shouldSDKDecodeResponse));
                } else if (str2.equals("VersionId")) {
                    this.currentVersionSummary.setVersionId(getText());
                } else if (str2.equals("IsLatest")) {
                    this.currentVersionSummary.setIsLatest("true".equals(getText()));
                } else if (str2.equals("LastModified")) {
                    this.currentVersionSummary.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                } else if (str2.equals("ETag")) {
                    this.currentVersionSummary.setETag(ServiceUtils.removeQuotes(getText()));
                } else if (str2.equals("Size")) {
                    this.currentVersionSummary.setSize(Long.parseLong(getText()));
                } else if (str2.equals("Owner")) {
                    this.currentVersionSummary.setOwner(this.currentOwner);
                    this.currentOwner = null;
                } else if (!str2.equals("StorageClass")) {
                } else {
                    this.currentVersionSummary.setStorageClass(getText());
                }
            } else if (!m4185in("ListVersionsResult", "Version", "Owner") && !m4185in("ListVersionsResult", "DeleteMarker", "Owner")) {
            } else {
                if (str2.equals("ID")) {
                    this.currentOwner.setId(getText());
                } else if (!str2.equals("DisplayName")) {
                } else {
                    this.currentOwner.setDisplayName(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketWebsiteConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketWebsiteConfigurationHandler extends AbstractHandler {
        private final BucketWebsiteConfiguration configuration = new BucketWebsiteConfiguration(null);
        private RoutingRuleCondition currentCondition = null;
        private RedirectRule currentRedirectRule = null;
        private RoutingRule currentRoutingRule = null;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("WebsiteConfiguration")) {
                if (!str2.equals("RedirectAllRequestsTo")) {
                    return;
                }
                this.currentRedirectRule = new RedirectRule();
            } else if (m4185in("WebsiteConfiguration", "RoutingRules")) {
                if (!str2.equals("RoutingRule")) {
                    return;
                }
                this.currentRoutingRule = new RoutingRule();
            } else if (!m4185in("WebsiteConfiguration", "RoutingRules", "RoutingRule")) {
            } else {
                if (str2.equals("Condition")) {
                    this.currentCondition = new RoutingRuleCondition();
                } else if (!str2.equals("Redirect")) {
                } else {
                    this.currentRedirectRule = new RedirectRule();
                }
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("WebsiteConfiguration")) {
                if (!str2.equals("RedirectAllRequestsTo")) {
                    return;
                }
                this.configuration.setRedirectAllRequestsTo(this.currentRedirectRule);
                this.currentRedirectRule = null;
            } else if (m4185in("WebsiteConfiguration", "IndexDocument")) {
                if (!str2.equals("Suffix")) {
                    return;
                }
                this.configuration.setIndexDocumentSuffix(getText());
            } else if (m4185in("WebsiteConfiguration", "ErrorDocument")) {
                if (!str2.equals("Key")) {
                    return;
                }
                this.configuration.setErrorDocument(getText());
            } else if (m4185in("WebsiteConfiguration", "RoutingRules")) {
                if (!str2.equals("RoutingRule")) {
                    return;
                }
                this.configuration.getRoutingRules().add(this.currentRoutingRule);
                this.currentRoutingRule = null;
            } else if (m4185in("WebsiteConfiguration", "RoutingRules", "RoutingRule")) {
                if (str2.equals("Condition")) {
                    this.currentRoutingRule.setCondition(this.currentCondition);
                    this.currentCondition = null;
                } else if (!str2.equals("Redirect")) {
                } else {
                    this.currentRoutingRule.setRedirect(this.currentRedirectRule);
                    this.currentRedirectRule = null;
                }
            } else if (m4185in("WebsiteConfiguration", "RoutingRules", "RoutingRule", "Condition")) {
                if (str2.equals("KeyPrefixEquals")) {
                    this.currentCondition.setKeyPrefixEquals(getText());
                } else if (!str2.equals("HttpErrorCodeReturnedEquals")) {
                } else {
                    this.currentCondition.setHttpErrorCodeReturnedEquals(getText());
                }
            } else if (!m4185in("WebsiteConfiguration", "RedirectAllRequestsTo") && !m4185in("WebsiteConfiguration", "RoutingRules", "RoutingRule", "Redirect")) {
            } else {
                if (str2.equals("Protocol")) {
                    this.currentRedirectRule.setProtocol(getText());
                } else if (str2.equals("HostName")) {
                    this.currentRedirectRule.setHostName(getText());
                } else if (str2.equals("ReplaceKeyPrefixWith")) {
                    this.currentRedirectRule.setReplaceKeyPrefixWith(getText());
                } else if (str2.equals("ReplaceKeyWith")) {
                    this.currentRedirectRule.setReplaceKeyWith(getText());
                } else if (!str2.equals("HttpRedirectCode")) {
                } else {
                    this.currentRedirectRule.setHttpRedirectCode(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketVersioningConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketVersioningConfigurationHandler extends AbstractHandler {
        private final BucketVersioningConfiguration configuration = new BucketVersioningConfiguration();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("VersioningConfiguration")) {
                if (str2.equals("Status")) {
                    this.configuration.setStatus(getText());
                } else if (!str2.equals("MfaDelete")) {
                } else {
                    String text = getText();
                    if (text.equals(BucketLifecycleConfiguration.DISABLED)) {
                        this.configuration.setMfaDeleteEnabled(false);
                    } else if (text.equals("Enabled")) {
                        this.configuration.setMfaDeleteEnabled(true);
                    } else {
                        this.configuration.setMfaDeleteEnabled(null);
                    }
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketAccelerateConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketAccelerateConfigurationHandler extends AbstractHandler {
        private final BucketAccelerateConfiguration configuration = new BucketAccelerateConfiguration(null);

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (!m4185in("AccelerateConfiguration") || !str2.equals("Status")) {
                return;
            }
            this.configuration.setStatus(getText());
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$CompleteMultipartUploadHandler */
    /* loaded from: classes2.dex */
    public static class CompleteMultipartUploadHandler extends AbstractSSEHandler implements ObjectExpirationResult, S3VersionResult, S3RequesterChargedResult {
        private AmazonS3Exception ase;
        private String errorCode;
        private String hostId;
        private String requestId;
        private CompleteMultipartUploadResult result;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractSSEHandler
        protected ServerSideEncryptionResult sseResult() {
            return this.result;
        }

        @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
        public void setExpirationTime(Date date) {
            CompleteMultipartUploadResult completeMultipartUploadResult = this.result;
            if (completeMultipartUploadResult != null) {
                completeMultipartUploadResult.setExpirationTime(date);
            }
        }

        @Override // com.amazonaws.services.p054s3.internal.ObjectExpirationResult
        public void setExpirationTimeRuleId(String str) {
            CompleteMultipartUploadResult completeMultipartUploadResult = this.result;
            if (completeMultipartUploadResult != null) {
                completeMultipartUploadResult.setExpirationTimeRuleId(str);
            }
        }

        @Override // com.amazonaws.services.p054s3.internal.S3VersionResult
        public void setVersionId(String str) {
            CompleteMultipartUploadResult completeMultipartUploadResult = this.result;
            if (completeMultipartUploadResult != null) {
                completeMultipartUploadResult.setVersionId(str);
            }
        }

        @Override // com.amazonaws.services.p054s3.internal.S3RequesterChargedResult
        public void setRequesterCharged(boolean z) {
            CompleteMultipartUploadResult completeMultipartUploadResult = this.result;
            if (completeMultipartUploadResult != null) {
                completeMultipartUploadResult.setRequesterCharged(z);
            }
        }

        public CompleteMultipartUploadResult getCompleteMultipartUploadResult() {
            return this.result;
        }

        public AmazonS3Exception getAmazonS3Exception() {
            return this.ase;
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (!atTopLevel() || !str2.equals("CompleteMultipartUploadResult")) {
                return;
            }
            this.result = new CompleteMultipartUploadResult();
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            AmazonS3Exception amazonS3Exception;
            if (atTopLevel()) {
                if (!str2.equals("Error") || (amazonS3Exception = this.ase) == null) {
                    return;
                }
                amazonS3Exception.setErrorCode(this.errorCode);
                this.ase.setRequestId(this.requestId);
                this.ase.setExtendedRequestId(this.hostId);
            } else if (m4185in("CompleteMultipartUploadResult")) {
                if (str2.equals("Location")) {
                    this.result.setLocation(getText());
                } else if (str2.equals("Bucket")) {
                    this.result.setBucketName(getText());
                } else if (str2.equals("Key")) {
                    this.result.setKey(getText());
                } else if (!str2.equals("ETag")) {
                } else {
                    this.result.setETag(ServiceUtils.removeQuotes(getText()));
                }
            } else if (!m4185in("Error")) {
            } else {
                if (str2.equals("Code")) {
                    this.errorCode = getText();
                } else if (str2.equals("Message")) {
                    this.ase = new AmazonS3Exception(getText());
                } else if (str2.equals("RequestId")) {
                    this.requestId = getText();
                } else if (!str2.equals("HostId")) {
                } else {
                    this.hostId = getText();
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$InitiateMultipartUploadHandler */
    /* loaded from: classes2.dex */
    public static class InitiateMultipartUploadHandler extends AbstractHandler {
        private final InitiateMultipartUploadResult result = new InitiateMultipartUploadResult();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
        }

        public InitiateMultipartUploadResult getInitiateMultipartUploadResult() {
            return this.result;
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("InitiateMultipartUploadResult")) {
                if (str2.equals("Bucket")) {
                    this.result.setBucketName(getText());
                } else if (str2.equals("Key")) {
                    this.result.setKey(getText());
                } else if (!str2.equals("UploadId")) {
                } else {
                    this.result.setUploadId(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListMultipartUploadsHandler */
    /* loaded from: classes2.dex */
    public static class ListMultipartUploadsHandler extends AbstractHandler {
        private MultipartUpload currentMultipartUpload;
        private Owner currentOwner;
        private final MultipartUploadListing result = new MultipartUploadListing();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListMultipartUploadsResult")) {
                if (!str2.equals("Upload")) {
                    return;
                }
                this.currentMultipartUpload = new MultipartUpload();
            } else if (!m4185in("ListMultipartUploadsResult", "Upload")) {
            } else {
                if (!str2.equals("Owner") && !str2.equals("Initiator")) {
                    return;
                }
                this.currentOwner = new Owner();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ListMultipartUploadsResult")) {
                if (str2.equals("Bucket")) {
                    this.result.setBucketName(getText());
                } else if (str2.equals("KeyMarker")) {
                    this.result.setKeyMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("Delimiter")) {
                    this.result.setDelimiter(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("Prefix")) {
                    this.result.setPrefix(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("UploadIdMarker")) {
                    this.result.setUploadIdMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("NextKeyMarker")) {
                    this.result.setNextKeyMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("NextUploadIdMarker")) {
                    this.result.setNextUploadIdMarker(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("MaxUploads")) {
                    this.result.setMaxUploads(Integer.parseInt(getText()));
                } else if (str2.equals("EncodingType")) {
                    this.result.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("IsTruncated")) {
                    this.result.setTruncated(Boolean.parseBoolean(getText()));
                } else if (!str2.equals("Upload")) {
                } else {
                    this.result.getMultipartUploads().add(this.currentMultipartUpload);
                    this.currentMultipartUpload = null;
                }
            } else if (m4185in("ListMultipartUploadsResult", "CommonPrefixes")) {
                if (!str2.equals("Prefix")) {
                    return;
                }
                this.result.getCommonPrefixes().add(getText());
            } else if (m4185in("ListMultipartUploadsResult", "Upload")) {
                if (str2.equals("Key")) {
                    this.currentMultipartUpload.setKey(getText());
                } else if (str2.equals("UploadId")) {
                    this.currentMultipartUpload.setUploadId(getText());
                } else if (str2.equals("Owner")) {
                    this.currentMultipartUpload.setOwner(this.currentOwner);
                    this.currentOwner = null;
                } else if (str2.equals("Initiator")) {
                    this.currentMultipartUpload.setInitiator(this.currentOwner);
                    this.currentOwner = null;
                } else if (str2.equals("StorageClass")) {
                    this.currentMultipartUpload.setStorageClass(getText());
                } else if (!str2.equals("Initiated")) {
                } else {
                    this.currentMultipartUpload.setInitiated(ServiceUtils.parseIso8601Date(getText()));
                }
            } else if (!m4185in("ListMultipartUploadsResult", "Upload", "Owner") && !m4185in("ListMultipartUploadsResult", "Upload", "Initiator")) {
            } else {
                if (str2.equals("ID")) {
                    this.currentOwner.setId(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (!str2.equals("DisplayName")) {
                } else {
                    this.currentOwner.setDisplayName(XmlResponsesSaxParser.checkForEmptyString(getText()));
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListPartsHandler */
    /* loaded from: classes2.dex */
    public static class ListPartsHandler extends AbstractHandler {
        private Owner currentOwner;
        private PartSummary currentPart;
        private final PartListing result = new PartListing();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListPartsResult")) {
                if (str2.equals("Part")) {
                    this.currentPart = new PartSummary();
                } else if (!str2.equals("Owner") && !str2.equals("Initiator")) {
                } else {
                    this.currentOwner = new Owner();
                }
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ListPartsResult")) {
                if (str2.equals("Bucket")) {
                    this.result.setBucketName(getText());
                } else if (str2.equals("Key")) {
                    this.result.setKey(getText());
                } else if (str2.equals("UploadId")) {
                    this.result.setUploadId(getText());
                } else if (str2.equals("Owner")) {
                    this.result.setOwner(this.currentOwner);
                    this.currentOwner = null;
                } else if (str2.equals("Initiator")) {
                    this.result.setInitiator(this.currentOwner);
                    this.currentOwner = null;
                } else if (str2.equals("StorageClass")) {
                    this.result.setStorageClass(getText());
                } else if (str2.equals("PartNumberMarker")) {
                    this.result.setPartNumberMarker(parseInteger(getText()).intValue());
                } else if (str2.equals("NextPartNumberMarker")) {
                    this.result.setNextPartNumberMarker(parseInteger(getText()).intValue());
                } else if (str2.equals("MaxParts")) {
                    this.result.setMaxParts(parseInteger(getText()).intValue());
                } else if (str2.equals("EncodingType")) {
                    this.result.setEncodingType(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (str2.equals("IsTruncated")) {
                    this.result.setTruncated(Boolean.parseBoolean(getText()));
                } else if (!str2.equals("Part")) {
                } else {
                    this.result.getParts().add(this.currentPart);
                    this.currentPart = null;
                }
            } else if (m4185in("ListPartsResult", "Part")) {
                if (str2.equals("PartNumber")) {
                    this.currentPart.setPartNumber(Integer.parseInt(getText()));
                } else if (str2.equals("LastModified")) {
                    this.currentPart.setLastModified(ServiceUtils.parseIso8601Date(getText()));
                } else if (str2.equals("ETag")) {
                    this.currentPart.setETag(ServiceUtils.removeQuotes(getText()));
                } else if (!str2.equals("Size")) {
                } else {
                    this.currentPart.setSize(Long.parseLong(getText()));
                }
            } else if (!m4185in("ListPartsResult", "Owner") && !m4185in("ListPartsResult", "Initiator")) {
            } else {
                if (str2.equals("ID")) {
                    this.currentOwner.setId(XmlResponsesSaxParser.checkForEmptyString(getText()));
                } else if (!str2.equals("DisplayName")) {
                } else {
                    this.currentOwner.setDisplayName(XmlResponsesSaxParser.checkForEmptyString(getText()));
                }
            }
        }

        private Integer parseInteger(String str) {
            String checkForEmptyString = XmlResponsesSaxParser.checkForEmptyString(getText());
            if (checkForEmptyString == null) {
                return null;
            }
            return Integer.valueOf(Integer.parseInt(checkForEmptyString));
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketReplicationConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketReplicationConfigurationHandler extends AbstractHandler {
        private final BucketReplicationConfiguration bucketReplicationConfiguration = new BucketReplicationConfiguration();
        private ReplicationRule currentRule;
        private String currentRuleId;
        private ReplicationDestinationConfig destinationConfig;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ReplicationConfiguration")) {
                if (!str2.equals("Rule")) {
                    return;
                }
                this.currentRule = new ReplicationRule();
            } else if (!m4185in("ReplicationConfiguration", "Rule") || !str2.equals("Destination")) {
            } else {
                this.destinationConfig = new ReplicationDestinationConfig();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ReplicationConfiguration")) {
                if (str2.equals("Rule")) {
                    this.bucketReplicationConfiguration.addRule(this.currentRuleId, this.currentRule);
                    this.currentRule = null;
                    this.currentRuleId = null;
                    this.destinationConfig = null;
                } else if (!str2.equals("Role")) {
                } else {
                    this.bucketReplicationConfiguration.setRoleARN(getText());
                }
            } else if (m4185in("ReplicationConfiguration", "Rule")) {
                if (str2.equals("ID")) {
                    this.currentRuleId = getText();
                } else if (str2.equals("Prefix")) {
                    this.currentRule.setPrefix(getText());
                } else if (str2.equals("Status")) {
                    this.currentRule.setStatus(getText());
                } else if (!str2.equals("Destination")) {
                } else {
                    this.currentRule.setDestinationConfig(this.destinationConfig);
                }
            } else if (!m4185in("ReplicationConfiguration", "Rule", "Destination")) {
            } else {
                if (str2.equals("Bucket")) {
                    this.destinationConfig.setBucketARN(getText());
                } else if (!str2.equals("StorageClass")) {
                } else {
                    this.destinationConfig.setStorageClass(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketTaggingConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketTaggingConfigurationHandler extends AbstractHandler {
        private final BucketTaggingConfiguration configuration = new BucketTaggingConfiguration();
        private String currentTagKey;
        private Map<String, String> currentTagSet;
        private String currentTagValue;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (!m4185in("Tagging") || !str2.equals("TagSet")) {
                return;
            }
            this.currentTagSet = new HashMap();
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            String str4;
            if (m4185in("Tagging")) {
                if (!str2.equals("TagSet")) {
                    return;
                }
                this.configuration.getAllTagSets().add(new TagSet(this.currentTagSet));
                this.currentTagSet = null;
            } else if (m4185in("Tagging", "TagSet")) {
                if (!str2.equals("Tag")) {
                    return;
                }
                String str5 = this.currentTagKey;
                if (str5 != null && (str4 = this.currentTagValue) != null) {
                    this.currentTagSet.put(str5, str4);
                }
                this.currentTagKey = null;
                this.currentTagValue = null;
            } else if (!m4185in("Tagging", "TagSet", "Tag")) {
            } else {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$GetObjectTaggingHandler */
    /* loaded from: classes2.dex */
    public static class GetObjectTaggingHandler extends AbstractHandler {
        private String currentTagKey;
        private String currentTagValue;
        private List<Tag> tagSet;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (!m4185in("Tagging") || !str2.equals("TagSet")) {
                return;
            }
            this.tagSet = new ArrayList();
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("Tagging") && str2.equals("TagSet")) {
                new GetObjectTaggingResult(this.tagSet);
                this.tagSet = null;
            }
            if (m4185in("Tagging", "TagSet")) {
                if (!str2.equals("Tag")) {
                    return;
                }
                this.tagSet.add(new Tag(this.currentTagKey, this.currentTagValue));
                this.currentTagKey = null;
                this.currentTagValue = null;
            } else if (!m4185in("Tagging", "TagSet", "Tag")) {
            } else {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$DeleteObjectsHandler */
    /* loaded from: classes2.dex */
    public static class DeleteObjectsHandler extends AbstractHandler {
        private final DeleteObjectsResponse response = new DeleteObjectsResponse();
        private DeleteObjectsResult.DeletedObject currentDeletedObject = null;
        private MultiObjectDeleteException.DeleteError currentError = null;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("DeleteResult")) {
                if (str2.equals("Deleted")) {
                    this.currentDeletedObject = new DeleteObjectsResult.DeletedObject();
                } else if (!str2.equals("Error")) {
                } else {
                    this.currentError = new MultiObjectDeleteException.DeleteError();
                }
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("DeleteResult")) {
                if (str2.equals("Deleted")) {
                    this.response.getDeletedObjects().add(this.currentDeletedObject);
                    this.currentDeletedObject = null;
                } else if (!str2.equals("Error")) {
                } else {
                    this.response.getErrors().add(this.currentError);
                    this.currentError = null;
                }
            } else if (m4185in("DeleteResult", "Deleted")) {
                if (str2.equals("Key")) {
                    this.currentDeletedObject.setKey(getText());
                } else if (str2.equals("VersionId")) {
                    this.currentDeletedObject.setVersionId(getText());
                } else if (str2.equals("DeleteMarker")) {
                    this.currentDeletedObject.setDeleteMarker(getText().equals("true"));
                } else if (!str2.equals("DeleteMarkerVersionId")) {
                } else {
                    this.currentDeletedObject.setDeleteMarkerVersionId(getText());
                }
            } else if (!m4185in("DeleteResult", "Error")) {
            } else {
                if (str2.equals("Key")) {
                    this.currentError.setKey(getText());
                } else if (str2.equals("VersionId")) {
                    this.currentError.setVersionId(getText());
                } else if (str2.equals("Code")) {
                    this.currentError.setCode(getText());
                } else if (!str2.equals("Message")) {
                } else {
                    this.currentError.setMessage(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketLifecycleConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketLifecycleConfigurationHandler extends AbstractHandler {
        private AbortIncompleteMultipartUpload abortIncompleteMultipartUpload;
        private List<LifecycleFilterPredicate> andOperandsList;
        private final BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration(new ArrayList());
        private LifecycleFilter currentFilter;
        private BucketLifecycleConfiguration.NoncurrentVersionTransition currentNcvTransition;
        private BucketLifecycleConfiguration.Rule currentRule;
        private String currentTagKey;
        private String currentTagValue;
        private BucketLifecycleConfiguration.Transition currentTransition;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("LifecycleConfiguration")) {
                if (!str2.equals("Rule")) {
                    return;
                }
                this.currentRule = new BucketLifecycleConfiguration.Rule();
            } else if (m4185in("LifecycleConfiguration", "Rule")) {
                if (str2.equals("Transition")) {
                    this.currentTransition = new BucketLifecycleConfiguration.Transition();
                } else if (str2.equals("NoncurrentVersionTransition")) {
                    this.currentNcvTransition = new BucketLifecycleConfiguration.NoncurrentVersionTransition();
                } else if (str2.equals("AbortIncompleteMultipartUpload")) {
                    this.abortIncompleteMultipartUpload = new AbortIncompleteMultipartUpload();
                } else if (!str2.equals("Filter")) {
                } else {
                    this.currentFilter = new LifecycleFilter();
                }
            } else if (!m4185in("LifecycleConfiguration", "Rule", "Filter") || !str2.equals("And")) {
            } else {
                this.andOperandsList = new ArrayList();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("LifecycleConfiguration")) {
                if (!str2.equals("Rule")) {
                    return;
                }
                this.configuration.getRules().add(this.currentRule);
                this.currentRule = null;
            } else if (m4185in("LifecycleConfiguration", "Rule")) {
                if (str2.equals("ID")) {
                    this.currentRule.setId(getText());
                } else if (str2.equals("Prefix")) {
                    this.currentRule.setPrefix(getText());
                } else if (str2.equals("Status")) {
                    this.currentRule.setStatus(getText());
                } else if (str2.equals("Transition")) {
                    this.currentRule.addTransition(this.currentTransition);
                    this.currentTransition = null;
                } else if (str2.equals("NoncurrentVersionTransition")) {
                    this.currentRule.addNoncurrentVersionTransition(this.currentNcvTransition);
                    this.currentNcvTransition = null;
                } else if (str2.equals("AbortIncompleteMultipartUpload")) {
                    this.currentRule.setAbortIncompleteMultipartUpload(this.abortIncompleteMultipartUpload);
                    this.abortIncompleteMultipartUpload = null;
                } else if (!str2.equals("Filter")) {
                } else {
                    this.currentRule.setFilter(this.currentFilter);
                    this.currentFilter = null;
                }
            } else if (m4185in("LifecycleConfiguration", "Rule", "Expiration")) {
                if (str2.equals("Date")) {
                    this.currentRule.setExpirationDate(ServiceUtils.parseIso8601Date(getText()));
                } else if (str2.equals("Days")) {
                    this.currentRule.setExpirationInDays(Integer.parseInt(getText()));
                } else if (!str2.equals("ExpiredObjectDeleteMarker") || !"true".equals(getText())) {
                } else {
                    this.currentRule.setExpiredObjectDeleteMarker(true);
                }
            } else if (m4185in("LifecycleConfiguration", "Rule", "Transition")) {
                if (str2.equals("StorageClass")) {
                    this.currentTransition.setStorageClass(getText());
                } else if (str2.equals("Date")) {
                    this.currentTransition.setDate(ServiceUtils.parseIso8601Date(getText()));
                } else if (!str2.equals("Days")) {
                } else {
                    this.currentTransition.setDays(Integer.parseInt(getText()));
                }
            } else if (m4185in("LifecycleConfiguration", "Rule", "NoncurrentVersionExpiration")) {
                if (!str2.equals("NoncurrentDays")) {
                    return;
                }
                this.currentRule.setNoncurrentVersionExpirationInDays(Integer.parseInt(getText()));
            } else if (m4185in("LifecycleConfiguration", "Rule", "NoncurrentVersionTransition")) {
                if (str2.equals("StorageClass")) {
                    this.currentNcvTransition.setStorageClass(getText());
                } else if (!str2.equals("NoncurrentDays")) {
                } else {
                    this.currentNcvTransition.setDays(Integer.parseInt(getText()));
                }
            } else if (m4185in("LifecycleConfiguration", "Rule", "AbortIncompleteMultipartUpload")) {
                if (!str2.equals("DaysAfterInitiation")) {
                    return;
                }
                this.abortIncompleteMultipartUpload.setDaysAfterInitiation(Integer.parseInt(getText()));
            } else if (m4185in("LifecycleConfiguration", "Rule", "Filter")) {
                if (str2.equals("Prefix")) {
                    this.currentFilter.setPredicate(new LifecyclePrefixPredicate(getText()));
                } else if (str2.equals("Tag")) {
                    this.currentFilter.setPredicate(new LifecycleTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                } else if (!str2.equals("And")) {
                } else {
                    this.currentFilter.setPredicate(new LifecycleAndOperator(this.andOperandsList));
                    this.andOperandsList = null;
                }
            } else if (m4185in("LifecycleConfiguration", "Rule", "Filter", "Tag")) {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            } else if (m4185in("LifecycleConfiguration", "Rule", "Filter", "And")) {
                if (str2.equals("Prefix")) {
                    this.andOperandsList.add(new LifecyclePrefixPredicate(getText()));
                } else if (!str2.equals("Tag")) {
                } else {
                    this.andOperandsList.add(new LifecycleTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                }
            } else if (!m4185in("LifecycleConfiguration", "Rule", "Filter", "And", "Tag")) {
            } else {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$BucketCrossOriginConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class BucketCrossOriginConfigurationHandler extends AbstractHandler {
        private CORSRule currentRule;
        private final BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration(new ArrayList());
        private List<CORSRule.AllowedMethods> allowedMethods = null;
        private List<String> allowedOrigins = null;
        private List<String> exposedHeaders = null;
        private List<String> allowedHeaders = null;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("CORSConfiguration")) {
                if (!str2.equals("CORSRule")) {
                    return;
                }
                this.currentRule = new CORSRule();
            } else if (!m4185in("CORSConfiguration", "CORSRule")) {
            } else {
                if (str2.equals("AllowedOrigin")) {
                    if (this.allowedOrigins != null) {
                        return;
                    }
                    this.allowedOrigins = new ArrayList();
                } else if (str2.equals("AllowedMethod")) {
                    if (this.allowedMethods != null) {
                        return;
                    }
                    this.allowedMethods = new ArrayList();
                } else if (str2.equals("ExposeHeader")) {
                    if (this.exposedHeaders != null) {
                        return;
                    }
                    this.exposedHeaders = new ArrayList();
                } else if (!str2.equals("AllowedHeader") || this.allowedHeaders != null) {
                } else {
                    this.allowedHeaders = new LinkedList();
                }
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("CORSConfiguration")) {
                if (!str2.equals("CORSRule")) {
                    return;
                }
                this.currentRule.setAllowedHeaders(this.allowedHeaders);
                this.currentRule.setAllowedMethods(this.allowedMethods);
                this.currentRule.setAllowedOrigins(this.allowedOrigins);
                this.currentRule.setExposedHeaders(this.exposedHeaders);
                this.allowedHeaders = null;
                this.allowedMethods = null;
                this.allowedOrigins = null;
                this.exposedHeaders = null;
                this.configuration.getRules().add(this.currentRule);
                this.currentRule = null;
            } else if (!m4185in("CORSConfiguration", "CORSRule")) {
            } else {
                if (str2.equals("ID")) {
                    this.currentRule.setId(getText());
                } else if (str2.equals("AllowedOrigin")) {
                    this.allowedOrigins.add(getText());
                } else if (str2.equals("AllowedMethod")) {
                    this.allowedMethods.add(CORSRule.AllowedMethods.fromValue(getText()));
                } else if (str2.equals("MaxAgeSeconds")) {
                    this.currentRule.setMaxAgeSeconds(Integer.parseInt(getText()));
                } else if (str2.equals("ExposeHeader")) {
                    this.exposedHeaders.add(getText());
                } else if (!str2.equals("AllowedHeader")) {
                } else {
                    this.allowedHeaders.add(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$GetBucketMetricsConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class GetBucketMetricsConfigurationHandler extends AbstractHandler {
        private List<MetricsFilterPredicate> andOperandsList;
        private final MetricsConfiguration configuration = new MetricsConfiguration();
        private String currentTagKey;
        private String currentTagValue;
        private MetricsFilter filter;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("MetricsConfiguration")) {
                if (!str2.equals("Filter")) {
                    return;
                }
                this.filter = new MetricsFilter();
            } else if (!m4185in("MetricsConfiguration", "Filter") || !str2.equals("And")) {
            } else {
                this.andOperandsList = new ArrayList();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("MetricsConfiguration")) {
                if (str2.equals("Id")) {
                    this.configuration.setId(getText());
                } else if (!str2.equals("Filter")) {
                } else {
                    this.configuration.setFilter(this.filter);
                    this.filter = null;
                }
            } else if (m4185in("MetricsConfiguration", "Filter")) {
                if (str2.equals("Prefix")) {
                    this.filter.setPredicate(new MetricsPrefixPredicate(getText()));
                } else if (str2.equals("Tag")) {
                    this.filter.setPredicate(new MetricsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                } else if (!str2.equals("And")) {
                } else {
                    this.filter.setPredicate(new MetricsAndOperator(this.andOperandsList));
                    this.andOperandsList = null;
                }
            } else if (m4185in("MetricsConfiguration", "Filter", "Tag")) {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            } else if (m4185in("MetricsConfiguration", "Filter", "And")) {
                if (str2.equals("Prefix")) {
                    this.andOperandsList.add(new MetricsPrefixPredicate(getText()));
                } else if (!str2.equals("Tag")) {
                } else {
                    this.andOperandsList.add(new MetricsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                }
            } else if (!m4185in("MetricsConfiguration", "Filter", "And", "Tag")) {
            } else {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListBucketMetricsConfigurationsHandler */
    /* loaded from: classes2.dex */
    public static class ListBucketMetricsConfigurationsHandler extends AbstractHandler {
        private List<MetricsFilterPredicate> andOperandsList;
        private MetricsConfiguration currentConfiguration;
        private MetricsFilter currentFilter;
        private String currentTagKey;
        private String currentTagValue;
        private final ListBucketMetricsConfigurationsResult result = new ListBucketMetricsConfigurationsResult();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListMetricsConfigurationsResult")) {
                if (!str2.equals("MetricsConfiguration")) {
                    return;
                }
                this.currentConfiguration = new MetricsConfiguration();
            } else if (m4185in("ListMetricsConfigurationsResult", "MetricsConfiguration")) {
                if (!str2.equals("Filter")) {
                    return;
                }
                this.currentFilter = new MetricsFilter();
            } else if (!m4185in("ListMetricsConfigurationsResult", "MetricsConfiguration", "Filter") || !str2.equals("And")) {
            } else {
                this.andOperandsList = new ArrayList();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ListMetricsConfigurationsResult")) {
                if (str2.equals("MetricsConfiguration")) {
                    if (this.result.getMetricsConfigurationList() == null) {
                        this.result.setMetricsConfigurationList(new ArrayList());
                    }
                    this.result.getMetricsConfigurationList().add(this.currentConfiguration);
                    this.currentConfiguration = null;
                } else if (str2.equals("IsTruncated")) {
                    this.result.setTruncated("true".equals(getText()));
                } else if (str2.equals("ContinuationToken")) {
                    this.result.setContinuationToken(getText());
                } else if (!str2.equals("NextContinuationToken")) {
                } else {
                    this.result.setNextContinuationToken(getText());
                }
            } else if (m4185in("ListMetricsConfigurationsResult", "MetricsConfiguration")) {
                if (str2.equals("Id")) {
                    this.currentConfiguration.setId(getText());
                } else if (!str2.equals("Filter")) {
                } else {
                    this.currentConfiguration.setFilter(this.currentFilter);
                    this.currentFilter = null;
                }
            } else if (m4185in("ListMetricsConfigurationsResult", "MetricsConfiguration", "Filter")) {
                if (str2.equals("Prefix")) {
                    this.currentFilter.setPredicate(new MetricsPrefixPredicate(getText()));
                } else if (str2.equals("Tag")) {
                    this.currentFilter.setPredicate(new MetricsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                } else if (!str2.equals("And")) {
                } else {
                    this.currentFilter.setPredicate(new MetricsAndOperator(this.andOperandsList));
                    this.andOperandsList = null;
                }
            } else if (m4185in("ListMetricsConfigurationsResult", "MetricsConfiguration", "Filter", "Tag")) {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            } else if (m4185in("ListMetricsConfigurationsResult", "MetricsConfiguration", "Filter", "And")) {
                if (str2.equals("Prefix")) {
                    this.andOperandsList.add(new MetricsPrefixPredicate(getText()));
                } else if (!str2.equals("Tag")) {
                } else {
                    this.andOperandsList.add(new MetricsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                }
            } else if (!m4185in("ListMetricsConfigurationsResult", "MetricsConfiguration", "Filter", "And", "Tag")) {
            } else {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$GetBucketAnalyticsConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class GetBucketAnalyticsConfigurationHandler extends AbstractHandler {
        private List<AnalyticsFilterPredicate> andOperandsList;
        private final AnalyticsConfiguration configuration = new AnalyticsConfiguration();
        private String currentTagKey;
        private String currentTagValue;
        private StorageClassAnalysisDataExport dataExport;
        private AnalyticsExportDestination destination;
        private AnalyticsFilter filter;
        private AnalyticsS3BucketDestination s3BucketDestination;
        private StorageClassAnalysis storageClassAnalysis;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("AnalyticsConfiguration")) {
                if (str2.equals("Filter")) {
                    this.filter = new AnalyticsFilter();
                } else if (!str2.equals("StorageClassAnalysis")) {
                } else {
                    this.storageClassAnalysis = new StorageClassAnalysis();
                }
            } else if (m4185in("AnalyticsConfiguration", "Filter")) {
                if (!str2.equals("And")) {
                    return;
                }
                this.andOperandsList = new ArrayList();
            } else if (m4185in("AnalyticsConfiguration", "StorageClassAnalysis")) {
                if (!str2.equals("DataExport")) {
                    return;
                }
                this.dataExport = new StorageClassAnalysisDataExport();
            } else if (m4185in("AnalyticsConfiguration", "StorageClassAnalysis", "DataExport")) {
                if (!str2.equals("Destination")) {
                    return;
                }
                this.destination = new AnalyticsExportDestination();
            } else if (!m4185in("AnalyticsConfiguration", "StorageClassAnalysis", "DataExport", "Destination") || !str2.equals("S3BucketDestination")) {
            } else {
                this.s3BucketDestination = new AnalyticsS3BucketDestination();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("AnalyticsConfiguration")) {
                if (str2.equals("Id")) {
                    this.configuration.setId(getText());
                } else if (str2.equals("Filter")) {
                    this.configuration.setFilter(this.filter);
                } else if (!str2.equals("StorageClassAnalysis")) {
                } else {
                    this.configuration.setStorageClassAnalysis(this.storageClassAnalysis);
                }
            } else if (m4185in("AnalyticsConfiguration", "Filter")) {
                if (str2.equals("Prefix")) {
                    this.filter.setPredicate(new AnalyticsPrefixPredicate(getText()));
                } else if (str2.equals("Tag")) {
                    this.filter.setPredicate(new AnalyticsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                } else if (!str2.equals("And")) {
                } else {
                    this.filter.setPredicate(new AnalyticsAndOperator(this.andOperandsList));
                    this.andOperandsList = null;
                }
            } else if (m4185in("AnalyticsConfiguration", "Filter", "Tag")) {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            } else if (m4185in("AnalyticsConfiguration", "Filter", "And")) {
                if (str2.equals("Prefix")) {
                    this.andOperandsList.add(new AnalyticsPrefixPredicate(getText()));
                } else if (!str2.equals("Tag")) {
                } else {
                    this.andOperandsList.add(new AnalyticsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                }
            } else if (m4185in("AnalyticsConfiguration", "Filter", "And", "Tag")) {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            } else if (m4185in("AnalyticsConfiguration", "StorageClassAnalysis")) {
                if (!str2.equals("DataExport")) {
                    return;
                }
                this.storageClassAnalysis.setDataExport(this.dataExport);
            } else if (m4185in("AnalyticsConfiguration", "StorageClassAnalysis", "DataExport")) {
                if (str2.equals("OutputSchemaVersion")) {
                    this.dataExport.setOutputSchemaVersion(getText());
                } else if (!str2.equals("Destination")) {
                } else {
                    this.dataExport.setDestination(this.destination);
                }
            } else if (m4185in("AnalyticsConfiguration", "StorageClassAnalysis", "DataExport", "Destination")) {
                if (!str2.equals("S3BucketDestination")) {
                    return;
                }
                this.destination.setS3BucketDestination(this.s3BucketDestination);
            } else if (!m4185in("AnalyticsConfiguration", "StorageClassAnalysis", "DataExport", "Destination", "S3BucketDestination")) {
            } else {
                if (str2.equals("Format")) {
                    this.s3BucketDestination.setFormat(getText());
                } else if (str2.equals("BucketAccountId")) {
                    this.s3BucketDestination.setBucketAccountId(getText());
                } else if (str2.equals("Bucket")) {
                    this.s3BucketDestination.setBucketArn(getText());
                } else if (!str2.equals("Prefix")) {
                } else {
                    this.s3BucketDestination.setPrefix(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListBucketAnalyticsConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class ListBucketAnalyticsConfigurationHandler extends AbstractHandler {
        private List<AnalyticsFilterPredicate> andOperandsList;
        private AnalyticsConfiguration currentConfiguration;
        private AnalyticsFilter currentFilter;
        private String currentTagKey;
        private String currentTagValue;
        private StorageClassAnalysisDataExport dataExport;
        private AnalyticsExportDestination destination;
        private final ListBucketAnalyticsConfigurationsResult result = new ListBucketAnalyticsConfigurationsResult();
        private AnalyticsS3BucketDestination s3BucketDestination;
        private StorageClassAnalysis storageClassAnalysis;

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListBucketAnalyticsConfigurationsResult")) {
                if (!str2.equals("AnalyticsConfiguration")) {
                    return;
                }
                this.currentConfiguration = new AnalyticsConfiguration();
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration")) {
                if (str2.equals("Filter")) {
                    this.currentFilter = new AnalyticsFilter();
                } else if (!str2.equals("StorageClassAnalysis")) {
                } else {
                    this.storageClassAnalysis = new StorageClassAnalysis();
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "Filter")) {
                if (!str2.equals("And")) {
                    return;
                }
                this.andOperandsList = new ArrayList();
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "StorageClassAnalysis")) {
                if (!str2.equals("DataExport")) {
                    return;
                }
                this.dataExport = new StorageClassAnalysisDataExport();
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "StorageClassAnalysis", "DataExport")) {
                if (!str2.equals("Destination")) {
                    return;
                }
                this.destination = new AnalyticsExportDestination();
            } else if (!m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "StorageClassAnalysis", "DataExport", "Destination") || !str2.equals("S3BucketDestination")) {
            } else {
                this.s3BucketDestination = new AnalyticsS3BucketDestination();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ListBucketAnalyticsConfigurationsResult")) {
                if (str2.equals("AnalyticsConfiguration")) {
                    if (this.result.getAnalyticsConfigurationList() == null) {
                        this.result.setAnalyticsConfigurationList(new ArrayList());
                    }
                    this.result.getAnalyticsConfigurationList().add(this.currentConfiguration);
                    this.currentConfiguration = null;
                } else if (str2.equals("IsTruncated")) {
                    this.result.setTruncated("true".equals(getText()));
                } else if (str2.equals("ContinuationToken")) {
                    this.result.setContinuationToken(getText());
                } else if (!str2.equals("NextContinuationToken")) {
                } else {
                    this.result.setNextContinuationToken(getText());
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration")) {
                if (str2.equals("Id")) {
                    this.currentConfiguration.setId(getText());
                } else if (str2.equals("Filter")) {
                    this.currentConfiguration.setFilter(this.currentFilter);
                } else if (!str2.equals("StorageClassAnalysis")) {
                } else {
                    this.currentConfiguration.setStorageClassAnalysis(this.storageClassAnalysis);
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "Filter")) {
                if (str2.equals("Prefix")) {
                    this.currentFilter.setPredicate(new AnalyticsPrefixPredicate(getText()));
                } else if (str2.equals("Tag")) {
                    this.currentFilter.setPredicate(new AnalyticsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                } else if (!str2.equals("And")) {
                } else {
                    this.currentFilter.setPredicate(new AnalyticsAndOperator(this.andOperandsList));
                    this.andOperandsList = null;
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "Filter", "Tag")) {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "Filter", "And")) {
                if (str2.equals("Prefix")) {
                    this.andOperandsList.add(new AnalyticsPrefixPredicate(getText()));
                } else if (!str2.equals("Tag")) {
                } else {
                    this.andOperandsList.add(new AnalyticsTagPredicate(new Tag(this.currentTagKey, this.currentTagValue)));
                    this.currentTagKey = null;
                    this.currentTagValue = null;
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "Filter", "And", "Tag")) {
                if (str2.equals("Key")) {
                    this.currentTagKey = getText();
                } else if (!str2.equals("Value")) {
                } else {
                    this.currentTagValue = getText();
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "StorageClassAnalysis")) {
                if (!str2.equals("DataExport")) {
                    return;
                }
                this.storageClassAnalysis.setDataExport(this.dataExport);
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "StorageClassAnalysis", "DataExport")) {
                if (str2.equals("OutputSchemaVersion")) {
                    this.dataExport.setOutputSchemaVersion(getText());
                } else if (!str2.equals("Destination")) {
                } else {
                    this.dataExport.setDestination(this.destination);
                }
            } else if (m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "StorageClassAnalysis", "DataExport", "Destination")) {
                if (!str2.equals("S3BucketDestination")) {
                    return;
                }
                this.destination.setS3BucketDestination(this.s3BucketDestination);
            } else if (!m4185in("ListBucketAnalyticsConfigurationsResult", "AnalyticsConfiguration", "StorageClassAnalysis", "DataExport", "Destination", "S3BucketDestination")) {
            } else {
                if (str2.equals("Format")) {
                    this.s3BucketDestination.setFormat(getText());
                } else if (str2.equals("BucketAccountId")) {
                    this.s3BucketDestination.setBucketAccountId(getText());
                } else if (str2.equals("Bucket")) {
                    this.s3BucketDestination.setBucketArn(getText());
                } else if (!str2.equals("Prefix")) {
                } else {
                    this.s3BucketDestination.setPrefix(getText());
                }
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$GetBucketInventoryConfigurationHandler */
    /* loaded from: classes2.dex */
    public static class GetBucketInventoryConfigurationHandler extends AbstractHandler {
        private final InventoryConfiguration configuration = new InventoryConfiguration();
        private InventoryFilter filter;
        private InventoryDestination inventoryDestination;
        private InventorySchedule inventorySchedule;
        private List<String> optionalFields;
        private InventoryS3BucketDestination s3BucketDestination;

        public GetBucketInventoryConfigurationHandler() {
            new GetBucketInventoryConfigurationResult();
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("InventoryConfiguration")) {
                if (str2.equals("Destination")) {
                    this.inventoryDestination = new InventoryDestination();
                } else if (str2.equals("Filter")) {
                    this.filter = new InventoryFilter();
                } else if (str2.equals("Schedule")) {
                    this.inventorySchedule = new InventorySchedule();
                } else if (!str2.equals("OptionalFields")) {
                } else {
                    this.optionalFields = new ArrayList();
                }
            } else if (!m4185in("InventoryConfiguration", "Destination") || !str2.equals("S3BucketDestination")) {
            } else {
                this.s3BucketDestination = new InventoryS3BucketDestination();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("InventoryConfiguration")) {
                if (str2.equals("Id")) {
                    this.configuration.setId(getText());
                } else if (str2.equals("Destination")) {
                    this.configuration.setDestination(this.inventoryDestination);
                    this.inventoryDestination = null;
                } else if (str2.equals("IsEnabled")) {
                    this.configuration.setEnabled(Boolean.valueOf("true".equals(getText())));
                } else if (str2.equals("Filter")) {
                    this.configuration.setInventoryFilter(this.filter);
                    this.filter = null;
                } else if (str2.equals("IncludedObjectVersions")) {
                    this.configuration.setIncludedObjectVersions(getText());
                } else if (str2.equals("Schedule")) {
                    this.configuration.setSchedule(this.inventorySchedule);
                    this.inventorySchedule = null;
                } else if (!str2.equals("OptionalFields")) {
                } else {
                    this.configuration.setOptionalFields(this.optionalFields);
                    this.optionalFields = null;
                }
            } else if (m4185in("InventoryConfiguration", "Destination")) {
                if (!str2.equals("S3BucketDestination")) {
                    return;
                }
                this.inventoryDestination.setS3BucketDestination(this.s3BucketDestination);
                this.s3BucketDestination = null;
            } else if (m4185in("InventoryConfiguration", "Destination", "S3BucketDestination")) {
                if (str2.equals("AccountId")) {
                    this.s3BucketDestination.setAccountId(getText());
                } else if (str2.equals("Bucket")) {
                    this.s3BucketDestination.setBucketArn(getText());
                } else if (str2.equals("Format")) {
                    this.s3BucketDestination.setFormat(getText());
                } else if (!str2.equals("Prefix")) {
                } else {
                    this.s3BucketDestination.setPrefix(getText());
                }
            } else if (m4185in("InventoryConfiguration", "Filter")) {
                if (!str2.equals("Prefix")) {
                    return;
                }
                this.filter.setPredicate(new InventoryPrefixPredicate(getText()));
            } else if (m4185in("InventoryConfiguration", "Schedule")) {
                if (!str2.equals("Frequency")) {
                    return;
                }
                this.inventorySchedule.setFrequency(getText());
            } else if (!m4185in("InventoryConfiguration", "OptionalFields") || !str2.equals("Field")) {
            } else {
                this.optionalFields.add(getText());
            }
        }
    }

    /* renamed from: com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser$ListBucketInventoryConfigurationsHandler */
    /* loaded from: classes2.dex */
    public static class ListBucketInventoryConfigurationsHandler extends AbstractHandler {
        private InventoryConfiguration currentConfiguration;
        private InventoryDestination currentDestination;
        private InventoryFilter currentFilter;
        private List<String> currentOptionalFieldsList;
        private InventoryS3BucketDestination currentS3BucketDestination;
        private InventorySchedule currentSchedule;
        private final ListBucketInventoryConfigurationsResult result = new ListBucketInventoryConfigurationsResult();

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doStartElement(String str, String str2, String str3, Attributes attributes) {
            if (m4185in("ListInventoryConfigurationsResult")) {
                if (!str2.equals("InventoryConfiguration")) {
                    return;
                }
                this.currentConfiguration = new InventoryConfiguration();
            } else if (m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration")) {
                if (str2.equals("Destination")) {
                    this.currentDestination = new InventoryDestination();
                } else if (str2.equals("Filter")) {
                    this.currentFilter = new InventoryFilter();
                } else if (str2.equals("Schedule")) {
                    this.currentSchedule = new InventorySchedule();
                } else if (!str2.equals("OptionalFields")) {
                } else {
                    this.currentOptionalFieldsList = new ArrayList();
                }
            } else if (!m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration", "Destination") || !str2.equals("S3BucketDestination")) {
            } else {
                this.currentS3BucketDestination = new InventoryS3BucketDestination();
            }
        }

        @Override // com.amazonaws.services.p054s3.model.transform.AbstractHandler
        protected void doEndElement(String str, String str2, String str3) {
            if (m4185in("ListInventoryConfigurationsResult")) {
                if (str2.equals("InventoryConfiguration")) {
                    if (this.result.getInventoryConfigurationList() == null) {
                        this.result.setInventoryConfigurationList(new ArrayList());
                    }
                    this.result.getInventoryConfigurationList().add(this.currentConfiguration);
                    this.currentConfiguration = null;
                } else if (str2.equals("IsTruncated")) {
                    this.result.setTruncated("true".equals(getText()));
                } else if (str2.equals("ContinuationToken")) {
                    this.result.setContinuationToken(getText());
                } else if (!str2.equals("NextContinuationToken")) {
                } else {
                    this.result.setNextContinuationToken(getText());
                }
            } else if (m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration")) {
                if (str2.equals("Id")) {
                    this.currentConfiguration.setId(getText());
                } else if (str2.equals("Destination")) {
                    this.currentConfiguration.setDestination(this.currentDestination);
                    this.currentDestination = null;
                } else if (str2.equals("IsEnabled")) {
                    this.currentConfiguration.setEnabled(Boolean.valueOf("true".equals(getText())));
                } else if (str2.equals("Filter")) {
                    this.currentConfiguration.setInventoryFilter(this.currentFilter);
                    this.currentFilter = null;
                } else if (str2.equals("IncludedObjectVersions")) {
                    this.currentConfiguration.setIncludedObjectVersions(getText());
                } else if (str2.equals("Schedule")) {
                    this.currentConfiguration.setSchedule(this.currentSchedule);
                    this.currentSchedule = null;
                } else if (!str2.equals("OptionalFields")) {
                } else {
                    this.currentConfiguration.setOptionalFields(this.currentOptionalFieldsList);
                    this.currentOptionalFieldsList = null;
                }
            } else if (m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration", "Destination")) {
                if (!str2.equals("S3BucketDestination")) {
                    return;
                }
                this.currentDestination.setS3BucketDestination(this.currentS3BucketDestination);
                this.currentS3BucketDestination = null;
            } else if (m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration", "Destination", "S3BucketDestination")) {
                if (str2.equals("AccountId")) {
                    this.currentS3BucketDestination.setAccountId(getText());
                } else if (str2.equals("Bucket")) {
                    this.currentS3BucketDestination.setBucketArn(getText());
                } else if (str2.equals("Format")) {
                    this.currentS3BucketDestination.setFormat(getText());
                } else if (!str2.equals("Prefix")) {
                } else {
                    this.currentS3BucketDestination.setPrefix(getText());
                }
            } else if (m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration", "Filter")) {
                if (!str2.equals("Prefix")) {
                    return;
                }
                this.currentFilter.setPredicate(new InventoryPrefixPredicate(getText()));
            } else if (m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration", "Schedule")) {
                if (!str2.equals("Frequency")) {
                    return;
                }
                this.currentSchedule.setFrequency(getText());
            } else if (!m4185in("ListInventoryConfigurationsResult", "InventoryConfiguration", "OptionalFields") || !str2.equals("Field")) {
            } else {
                this.currentOptionalFieldsList.add(getText());
            }
        }
    }
}
