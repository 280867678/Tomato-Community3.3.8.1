package com.amazonaws.services.p054s3.model;

/* renamed from: com.amazonaws.services.s3.model.ReplicationRule */
/* loaded from: classes2.dex */
public class ReplicationRule {
    public void setStatus(String str) {
    }

    public void setPrefix(String str) {
        if (str != null) {
            return;
        }
        throw new IllegalArgumentException("Prefix cannot be null for a replication rule");
    }

    public void setDestinationConfig(ReplicationDestinationConfig replicationDestinationConfig) {
        if (replicationDestinationConfig != null) {
            return;
        }
        throw new IllegalArgumentException("Destination cannot be null in the replication rule");
    }
}
