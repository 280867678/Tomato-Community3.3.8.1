package com.example.websocket.com.neovisionaries.p057ws.client;

import com.example.websocket.com.neovisionaries.p057ws.client.StateManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.example.websocket.com.neovisionaries.ws.client.ReadingThread */
/* loaded from: classes2.dex */
public class ReadingThread extends WebSocketThread {
    private long mCloseDelay;
    private WebSocketFrame mCloseFrame;
    private CloseTask mCloseTask;
    private Timer mCloseTimer;
    private boolean mNotWaitForCloseFrame;
    private final PerMessageCompressionExtension mPMCE;
    private boolean mStopRequested;
    private List<WebSocketFrame> mContinuation = new ArrayList();
    private Object mCloseLock = new Object();

    public ReadingThread(WebSocket webSocket) {
        super("ReadingThread", webSocket, ThreadType.READING_THREAD);
        this.mPMCE = webSocket.getPerMessageCompressionExtension();
    }

    @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketThread
    public void runMain() {
        try {
            main();
        } catch (Throwable th) {
            WebSocketError webSocketError = WebSocketError.UNEXPECTED_ERROR_IN_READING_THREAD;
            WebSocketException webSocketException = new WebSocketException(webSocketError, "An uncaught throwable was detected in the reading thread: " + th.getMessage(), th);
            ListenerManager listenerManager = this.mWebSocket.getListenerManager();
            listenerManager.callOnError(webSocketException);
            listenerManager.callOnUnexpectedError(webSocketException);
        }
        notifyFinished();
    }

    private void main() {
        this.mWebSocket.onReadingThreadStarted();
        while (true) {
            synchronized (this) {
                if (!this.mStopRequested) {
                    WebSocketFrame readFrame = readFrame();
                    if (readFrame == null) {
                        break;
                    } else if (!handleFrame(readFrame)) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        waitForCloseFrame();
        cancelClose();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void requestStop(long j) {
        synchronized (this) {
            if (this.mStopRequested) {
                return;
            }
            this.mStopRequested = true;
            interrupt();
            this.mCloseDelay = j;
            scheduleClose();
        }
    }

    private void callOnFrame(WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnFrame(webSocketFrame);
    }

    private void callOnContinuationFrame(WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnContinuationFrame(webSocketFrame);
    }

    private void callOnTextFrame(WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnTextFrame(webSocketFrame);
    }

    private void callOnBinaryFrame(WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnBinaryFrame(webSocketFrame);
    }

    private void callOnCloseFrame(WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnCloseFrame(webSocketFrame);
    }

    private void callOnPingFrame(WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnPingFrame(webSocketFrame);
    }

    private void callOnPongFrame(WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnPongFrame(webSocketFrame);
    }

    private void callOnTextMessage(byte[] bArr) {
        if (this.mWebSocket.isDirectTextMessage()) {
            this.mWebSocket.getListenerManager().callOnTextMessage(bArr);
            return;
        }
        try {
            callOnTextMessage(Misc.toStringUTF8(bArr));
        } catch (Throwable th) {
            WebSocketError webSocketError = WebSocketError.TEXT_MESSAGE_CONSTRUCTION_ERROR;
            WebSocketException webSocketException = new WebSocketException(webSocketError, "Failed to convert payload data into a string: " + th.getMessage(), th);
            callOnError(webSocketException);
            callOnTextMessageError(webSocketException, bArr);
        }
    }

    private void callOnTextMessage(String str) {
        this.mWebSocket.getListenerManager().callOnTextMessage(str);
    }

    private void callOnBinaryMessage(byte[] bArr) {
        this.mWebSocket.getListenerManager().callOnBinaryMessage(bArr);
    }

    private void callOnError(WebSocketException webSocketException) {
        this.mWebSocket.getListenerManager().callOnError(webSocketException);
    }

    private void callOnFrameError(WebSocketException webSocketException, WebSocketFrame webSocketFrame) {
        this.mWebSocket.getListenerManager().callOnFrameError(webSocketException, webSocketFrame);
    }

    private void callOnMessageError(WebSocketException webSocketException, List<WebSocketFrame> list) {
        this.mWebSocket.getListenerManager().callOnMessageError(webSocketException, list);
    }

    private void callOnMessageDecompressionError(WebSocketException webSocketException, byte[] bArr) {
        this.mWebSocket.getListenerManager().callOnMessageDecompressionError(webSocketException, bArr);
    }

    private void callOnTextMessageError(WebSocketException webSocketException, byte[] bArr) {
        this.mWebSocket.getListenerManager().callOnTextMessageError(webSocketException, bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private WebSocketFrame readFrame() {
        WebSocketFrame webSocketFrame;
        WebSocketException webSocketException;
        boolean z;
        try {
            webSocketFrame = this.mWebSocket.getInput().readFrame();
            try {
                verifyFrame(webSocketFrame);
                return webSocketFrame;
            } catch (WebSocketException e) {
                e = e;
                webSocketException = e;
                z = true;
                if (webSocketException instanceof NoMoreFrameException) {
                    this.mNotWaitForCloseFrame = true;
                    if (this.mWebSocket.isMissingCloseFrameAllowed()) {
                        z = false;
                    }
                }
                if (z) {
                    callOnError(webSocketException);
                    callOnFrameError(webSocketException, webSocketFrame);
                }
                this.mWebSocket.sendFrame(createCloseFrame(webSocketException));
                return null;
            } catch (InterruptedIOException e2) {
                e = e2;
                if (this.mStopRequested) {
                    return null;
                }
                webSocketException = new WebSocketException(WebSocketError.INTERRUPTED_IN_READING, "Interruption occurred while a frame was being read from the web socket: " + e.getMessage(), e);
                z = true;
                if (webSocketException instanceof NoMoreFrameException) {
                }
                if (z) {
                }
                this.mWebSocket.sendFrame(createCloseFrame(webSocketException));
                return null;
            } catch (IOException e3) {
                e = e3;
                if (this.mStopRequested && isInterrupted()) {
                    return null;
                }
                webSocketException = new WebSocketException(WebSocketError.IO_ERROR_IN_READING, "An I/O error occurred while a frame was being read from the web socket: " + e.getMessage(), e);
                z = true;
                if (webSocketException instanceof NoMoreFrameException) {
                }
                if (z) {
                }
                this.mWebSocket.sendFrame(createCloseFrame(webSocketException));
                return null;
            }
        } catch (WebSocketException e4) {
            e = e4;
            webSocketFrame = null;
        } catch (InterruptedIOException e5) {
            e = e5;
            webSocketFrame = null;
        } catch (IOException e6) {
            e = e6;
            webSocketFrame = null;
        }
    }

    private void verifyFrame(WebSocketFrame webSocketFrame) throws WebSocketException {
        verifyReservedBits(webSocketFrame);
        verifyFrameOpcode(webSocketFrame);
        verifyFrameMask(webSocketFrame);
        verifyFrameFragmentation(webSocketFrame);
        verifyFrameSize(webSocketFrame);
    }

    private void verifyReservedBits(WebSocketFrame webSocketFrame) throws WebSocketException {
        if (this.mWebSocket.isExtended()) {
            return;
        }
        verifyReservedBit1(webSocketFrame);
        verifyReservedBit2(webSocketFrame);
        verifyReservedBit3(webSocketFrame);
    }

    private void verifyReservedBit1(WebSocketFrame webSocketFrame) throws WebSocketException {
        if ((this.mPMCE == null || !verifyReservedBit1ForPMCE(webSocketFrame)) && webSocketFrame.getRsv1()) {
            throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV1 bit of a frame is set unexpectedly.");
        }
    }

    private boolean verifyReservedBit1ForPMCE(WebSocketFrame webSocketFrame) throws WebSocketException {
        return webSocketFrame.isTextFrame() || webSocketFrame.isBinaryFrame();
    }

    private void verifyReservedBit2(WebSocketFrame webSocketFrame) throws WebSocketException {
        if (!webSocketFrame.getRsv2()) {
            return;
        }
        throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV2 bit of a frame is set unexpectedly.");
    }

    private void verifyReservedBit3(WebSocketFrame webSocketFrame) throws WebSocketException {
        if (!webSocketFrame.getRsv3()) {
            return;
        }
        throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV3 bit of a frame is set unexpectedly.");
    }

    private void verifyFrameOpcode(WebSocketFrame webSocketFrame) throws WebSocketException {
        int opcode = webSocketFrame.getOpcode();
        if (opcode == 0 || opcode == 1 || opcode == 2) {
            return;
        }
        switch (opcode) {
            case 8:
            case 9:
            case 10:
                return;
            default:
                if (this.mWebSocket.isExtended()) {
                    return;
                }
                WebSocketError webSocketError = WebSocketError.UNKNOWN_OPCODE;
                throw new WebSocketException(webSocketError, "A frame has an unknown opcode: 0x" + Integer.toHexString(webSocketFrame.getOpcode()));
        }
    }

    private void verifyFrameMask(WebSocketFrame webSocketFrame) throws WebSocketException {
        if (!webSocketFrame.getMask()) {
            return;
        }
        throw new WebSocketException(WebSocketError.FRAME_MASKED, "A frame from the server is masked.");
    }

    private void verifyFrameFragmentation(WebSocketFrame webSocketFrame) throws WebSocketException {
        if (webSocketFrame.isControlFrame()) {
            if (!webSocketFrame.getFin()) {
                throw new WebSocketException(WebSocketError.FRAGMENTED_CONTROL_FRAME, "A control frame is fragmented.");
            }
            return;
        }
        boolean z = this.mContinuation.size() != 0;
        if (webSocketFrame.isContinuationFrame()) {
            if (!z) {
                throw new WebSocketException(WebSocketError.UNEXPECTED_CONTINUATION_FRAME, "A continuation frame was detected although a continuation had not started.");
            }
        } else if (z) {
            throw new WebSocketException(WebSocketError.CONTINUATION_NOT_CLOSED, "A non-control frame was detected although the existing continuation had not been closed.");
        }
    }

    private void verifyFrameSize(WebSocketFrame webSocketFrame) throws WebSocketException {
        byte[] payload;
        if (webSocketFrame.isControlFrame() && (payload = webSocketFrame.getPayload()) != null && 125 < payload.length) {
            WebSocketError webSocketError = WebSocketError.TOO_LONG_CONTROL_FRAME_PAYLOAD;
            throw new WebSocketException(webSocketError, "The payload size of a control frame exceeds the maximum size (125 bytes): " + payload.length);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.example.websocket.com.neovisionaries.ws.client.ReadingThread$1 */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class C12651 {

        /* renamed from: $SwitchMap$com$example$websocket$com$neovisionaries$ws$client$WebSocketError */
        static final /* synthetic */ int[] f1252x723f0a13 = new int[WebSocketError.values().length];

        static {
            try {
                f1252x723f0a13[WebSocketError.INSUFFICENT_DATA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1252x723f0a13[WebSocketError.INVALID_PAYLOAD_LENGTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1252x723f0a13[WebSocketError.NO_MORE_FRAME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1252x723f0a13[WebSocketError.TOO_LONG_PAYLOAD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1252x723f0a13[WebSocketError.INSUFFICIENT_MEMORY_FOR_PAYLOAD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1252x723f0a13[WebSocketError.NON_ZERO_RESERVED_BITS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f1252x723f0a13[WebSocketError.UNEXPECTED_RESERVED_BIT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f1252x723f0a13[WebSocketError.UNKNOWN_OPCODE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f1252x723f0a13[WebSocketError.FRAME_MASKED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f1252x723f0a13[WebSocketError.FRAGMENTED_CONTROL_FRAME.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f1252x723f0a13[WebSocketError.UNEXPECTED_CONTINUATION_FRAME.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f1252x723f0a13[WebSocketError.CONTINUATION_NOT_CLOSED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f1252x723f0a13[WebSocketError.TOO_LONG_CONTROL_FRAME_PAYLOAD.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f1252x723f0a13[WebSocketError.INTERRUPTED_IN_READING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f1252x723f0a13[WebSocketError.IO_ERROR_IN_READING.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    private WebSocketFrame createCloseFrame(WebSocketException webSocketException) {
        int i = 1002;
        switch (C12651.f1252x723f0a13[webSocketException.getError().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            case 4:
            case 5:
                i = 1009;
                break;
            case 14:
            case 15:
            default:
                i = 1008;
                break;
        }
        return WebSocketFrame.createCloseFrame(i, webSocketException.getMessage());
    }

    private boolean handleFrame(WebSocketFrame webSocketFrame) {
        callOnFrame(webSocketFrame);
        int opcode = webSocketFrame.getOpcode();
        if (opcode != 0) {
            if (opcode == 1) {
                return handleTextFrame(webSocketFrame);
            }
            if (opcode == 2) {
                return handleBinaryFrame(webSocketFrame);
            }
            switch (opcode) {
                case 8:
                    return handleCloseFrame(webSocketFrame);
                case 9:
                    return handlePingFrame(webSocketFrame);
                case 10:
                    return handlePongFrame(webSocketFrame);
                default:
                    return true;
            }
        }
        return handleContinuationFrame(webSocketFrame);
    }

    private boolean handleContinuationFrame(WebSocketFrame webSocketFrame) {
        callOnContinuationFrame(webSocketFrame);
        this.mContinuation.add(webSocketFrame);
        if (!webSocketFrame.getFin()) {
            return true;
        }
        byte[] message = getMessage(this.mContinuation);
        if (message == null) {
            return false;
        }
        if (this.mContinuation.get(0).isTextFrame()) {
            callOnTextMessage(message);
        } else {
            callOnBinaryMessage(message);
        }
        this.mContinuation.clear();
        return true;
    }

    private byte[] getMessage(List<WebSocketFrame> list) {
        byte[] concatenatePayloads = concatenatePayloads(this.mContinuation);
        if (concatenatePayloads == null) {
            return null;
        }
        return (this.mPMCE == null || !list.get(0).getRsv1()) ? concatenatePayloads : decompress(concatenatePayloads);
    }

    private byte[] concatenatePayloads(List<WebSocketFrame> list) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (WebSocketFrame webSocketFrame : list) {
                byte[] payload = webSocketFrame.getPayload();
                if (payload != null && payload.length != 0) {
                    byteArrayOutputStream.write(payload);
                }
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | OutOfMemoryError e) {
            WebSocketError webSocketError = WebSocketError.MESSAGE_CONSTRUCTION_ERROR;
            WebSocketException webSocketException = new WebSocketException(webSocketError, "Failed to concatenate payloads of multiple frames to construct a message: " + e.getMessage(), e);
            callOnError(webSocketException);
            callOnMessageError(webSocketException, list);
            this.mWebSocket.sendFrame(WebSocketFrame.createCloseFrame(1009, webSocketException.getMessage()));
            return null;
        }
    }

    private byte[] getMessage(WebSocketFrame webSocketFrame) {
        byte[] payload = webSocketFrame.getPayload();
        return (this.mPMCE == null || !webSocketFrame.getRsv1()) ? payload : decompress(payload);
    }

    private byte[] decompress(byte[] bArr) {
        try {
            return this.mPMCE.decompress(bArr);
        } catch (WebSocketException e) {
            callOnError(e);
            callOnMessageDecompressionError(e, bArr);
            this.mWebSocket.sendFrame(WebSocketFrame.createCloseFrame(1003, e.getMessage()));
            return null;
        }
    }

    private boolean handleTextFrame(WebSocketFrame webSocketFrame) {
        callOnTextFrame(webSocketFrame);
        if (!webSocketFrame.getFin()) {
            this.mContinuation.add(webSocketFrame);
            return true;
        }
        callOnTextMessage(getMessage(webSocketFrame));
        return true;
    }

    private boolean handleBinaryFrame(WebSocketFrame webSocketFrame) {
        callOnBinaryFrame(webSocketFrame);
        if (!webSocketFrame.getFin()) {
            this.mContinuation.add(webSocketFrame);
            return true;
        }
        callOnBinaryMessage(getMessage(webSocketFrame));
        return true;
    }

    private boolean handleCloseFrame(WebSocketFrame webSocketFrame) {
        boolean z;
        StateManager stateManager = this.mWebSocket.getStateManager();
        this.mCloseFrame = webSocketFrame;
        synchronized (stateManager) {
            WebSocketState state = stateManager.getState();
            if (state == WebSocketState.CLOSING || state == WebSocketState.CLOSED) {
                z = false;
            } else {
                stateManager.changeToClosing(StateManager.CloseInitiator.SERVER);
                this.mWebSocket.sendFrame(webSocketFrame);
                z = true;
            }
        }
        if (z) {
            this.mWebSocket.getListenerManager().callOnStateChanged(WebSocketState.CLOSING);
        }
        callOnCloseFrame(webSocketFrame);
        return false;
    }

    private boolean handlePingFrame(WebSocketFrame webSocketFrame) {
        callOnPingFrame(webSocketFrame);
        this.mWebSocket.sendFrame(WebSocketFrame.createPongFrame(webSocketFrame.getPayload()));
        return true;
    }

    private boolean handlePongFrame(WebSocketFrame webSocketFrame) {
        callOnPongFrame(webSocketFrame);
        return true;
    }

    private void waitForCloseFrame() {
        if (!this.mNotWaitForCloseFrame && this.mCloseFrame == null) {
            scheduleClose();
            do {
                try {
                    WebSocketFrame readFrame = this.mWebSocket.getInput().readFrame();
                    if (readFrame.isCloseFrame()) {
                        this.mCloseFrame = readFrame;
                        return;
                    }
                } catch (Throwable unused) {
                    return;
                }
            } while (!isInterrupted());
        }
    }

    private void notifyFinished() {
        this.mWebSocket.onReadingThreadFinished(this.mCloseFrame);
    }

    private void scheduleClose() {
        synchronized (this.mCloseLock) {
            cancelCloseTask();
            scheduleCloseTask();
        }
    }

    private void scheduleCloseTask() {
        this.mCloseTask = new CloseTask(this, null);
        this.mCloseTimer = new Timer("ReadingThreadCloseTimer");
        this.mCloseTimer.schedule(this.mCloseTask, this.mCloseDelay);
    }

    private void cancelClose() {
        synchronized (this.mCloseLock) {
            cancelCloseTask();
        }
    }

    private void cancelCloseTask() {
        Timer timer = this.mCloseTimer;
        if (timer != null) {
            timer.cancel();
            this.mCloseTimer = null;
        }
        CloseTask closeTask = this.mCloseTask;
        if (closeTask != null) {
            closeTask.cancel();
            this.mCloseTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.example.websocket.com.neovisionaries.ws.client.ReadingThread$CloseTask */
    /* loaded from: classes2.dex */
    public class CloseTask extends TimerTask {
        private CloseTask() {
        }

        /* synthetic */ CloseTask(ReadingThread readingThread, C12651 c12651) {
            this();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            try {
                ReadingThread.this.mWebSocket.getSocket().close();
            } catch (Throwable unused) {
            }
        }
    }
}
