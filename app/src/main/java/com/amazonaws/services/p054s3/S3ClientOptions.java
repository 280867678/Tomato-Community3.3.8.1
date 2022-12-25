package com.amazonaws.services.p054s3;

/* renamed from: com.amazonaws.services.s3.S3ClientOptions */
/* loaded from: classes2.dex */
public class S3ClientOptions {
    private final boolean accelerateModeEnabled;
    private final boolean chunkedEncodingDisabled;
    private final boolean dualstackEnabled;
    private boolean pathStyleAccess;
    private final boolean payloadSigningEnabled;

    public static Builder builder() {
        return new Builder();
    }

    /* renamed from: com.amazonaws.services.s3.S3ClientOptions$Builder */
    /* loaded from: classes2.dex */
    public static final class Builder {
        private boolean accelerateModeEnabled;
        private boolean chunkedEncodingDisabled;
        private boolean dualstackEnabled;
        private boolean pathStyleAccess;
        private boolean payloadSigningEnabled;

        private Builder() {
            this.pathStyleAccess = false;
            this.chunkedEncodingDisabled = false;
            this.accelerateModeEnabled = false;
            this.payloadSigningEnabled = false;
            this.dualstackEnabled = false;
        }

        public S3ClientOptions build() {
            return new S3ClientOptions(this.pathStyleAccess, this.chunkedEncodingDisabled, this.accelerateModeEnabled, this.payloadSigningEnabled, this.dualstackEnabled);
        }

        public Builder setPathStyleAccess(boolean z) {
            this.pathStyleAccess = z;
            return this;
        }
    }

    @Deprecated
    public S3ClientOptions() {
        this.pathStyleAccess = false;
        this.chunkedEncodingDisabled = false;
        this.accelerateModeEnabled = false;
        this.payloadSigningEnabled = false;
        this.dualstackEnabled = false;
    }

    @Deprecated
    public S3ClientOptions(S3ClientOptions s3ClientOptions) {
        this.pathStyleAccess = s3ClientOptions.pathStyleAccess;
        this.chunkedEncodingDisabled = s3ClientOptions.chunkedEncodingDisabled;
        this.accelerateModeEnabled = s3ClientOptions.accelerateModeEnabled;
        this.payloadSigningEnabled = s3ClientOptions.payloadSigningEnabled;
        this.dualstackEnabled = s3ClientOptions.dualstackEnabled;
    }

    private S3ClientOptions(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.pathStyleAccess = z;
        this.chunkedEncodingDisabled = z2;
        this.accelerateModeEnabled = z3;
        this.payloadSigningEnabled = z4;
        this.dualstackEnabled = z5;
    }

    public boolean isPathStyleAccess() {
        return this.pathStyleAccess;
    }

    public boolean isAccelerateModeEnabled() {
        return this.accelerateModeEnabled;
    }

    public boolean isDualstackEnabled() {
        return this.dualstackEnabled;
    }
}
