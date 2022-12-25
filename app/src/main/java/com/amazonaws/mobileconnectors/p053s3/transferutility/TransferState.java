package com.amazonaws.mobileconnectors.p053s3.transferutility;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferState */
/* loaded from: classes2.dex */
public enum TransferState {
    WAITING,
    IN_PROGRESS,
    PAUSED,
    RESUMED_WAITING,
    COMPLETED,
    CANCELED,
    FAILED,
    WAITING_FOR_NETWORK,
    PART_COMPLETED,
    PENDING_CANCEL,
    PENDING_PAUSE,
    PENDING_NETWORK_DISCONNECT,
    UNKNOWN;
    
    private static final Log LOGGER;
    private static final Map<String, TransferState> MAP = new HashMap();

    static {
        TransferState[] values;
        for (TransferState transferState : values()) {
            MAP.put(transferState.toString(), transferState);
        }
        LOGGER = LogFactory.getLog(TransferState.class);
    }

    public static TransferState getState(String str) {
        if (MAP.containsKey(str)) {
            return MAP.get(str);
        }
        Log log = LOGGER;
        log.error("Unknown state " + str + " transfer will be have state set to UNKNOWN.");
        return UNKNOWN;
    }
}
