package com.example.websocket.com.neovisionaries.p057ws.client;

import com.example.websocket.com.neovisionaries.p057ws.client.StateManager;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.example.websocket.com.neovisionaries.ws.client.WritingThread */
/* loaded from: classes2.dex */
public class WritingThread extends WebSocketThread {
    private static final int FLUSH_THRESHOLD = 1000;
    private static final int SHOULD_CONTINUE = 2;
    private static final int SHOULD_FLUSH = 3;
    private static final int SHOULD_SEND = 0;
    private static final int SHOULD_STOP = 1;
    private WebSocketFrame mCloseFrame;
    private boolean mFlushNeeded;
    private final LinkedList<WebSocketFrame> mFrames = new LinkedList<>();
    private final PerMessageCompressionExtension mPMCE;
    private boolean mStopRequested;
    private boolean mStopped;

    public WritingThread(WebSocket webSocket) {
        super("WritingThread", webSocket, ThreadType.WRITING_THREAD);
        this.mPMCE = webSocket.getPerMessageCompressionExtension();
    }

    @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketThread
    public void runMain() {
        try {
            main();
        } catch (Throwable th) {
            WebSocketError webSocketError = WebSocketError.UNEXPECTED_ERROR_IN_WRITING_THREAD;
            WebSocketException webSocketException = new WebSocketException(webSocketError, "An uncaught throwable was detected in the writing thread: " + th.getMessage(), th);
            ListenerManager listenerManager = this.mWebSocket.getListenerManager();
            listenerManager.callOnError(webSocketException);
            listenerManager.callOnUnexpectedError(webSocketException);
        }
        synchronized (this) {
            this.mStopped = true;
            notifyAll();
        }
        notifyFinished();
    }

    private void main() {
        this.mWebSocket.onWritingThreadStarted();
        while (true) {
            int waitForFrames = waitForFrames();
            if (waitForFrames != 1) {
                if (waitForFrames == 3) {
                    flushIgnoreError();
                } else if (waitForFrames == 2) {
                    continue;
                } else {
                    try {
                        sendFrames(false);
                    } catch (WebSocketException unused) {
                    }
                }
            }
            try {
                sendFrames(true);
                return;
            } catch (WebSocketException unused2) {
                return;
            }
        }
    }

    public void requestStop() {
        synchronized (this) {
            this.mStopRequested = true;
            notifyAll();
        }
    }

    public boolean queueFrame(WebSocketFrame webSocketFrame) {
        int frameQueueSize;
        synchronized (this) {
            while (!this.mStopped) {
                if (!this.mStopRequested && this.mCloseFrame == null && !webSocketFrame.isControlFrame() && (frameQueueSize = this.mWebSocket.getFrameQueueSize()) != 0 && this.mFrames.size() >= frameQueueSize) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                    }
                }
                if (isHighPriorityFrame(webSocketFrame)) {
                    addHighPriorityFrame(webSocketFrame);
                } else {
                    this.mFrames.addLast(webSocketFrame);
                }
                notifyAll();
                return true;
            }
            return false;
        }
    }

    private static boolean isHighPriorityFrame(WebSocketFrame webSocketFrame) {
        return webSocketFrame.isPingFrame() || webSocketFrame.isPongFrame();
    }

    private void addHighPriorityFrame(WebSocketFrame webSocketFrame) {
        Iterator<WebSocketFrame> it2 = this.mFrames.iterator();
        int i = 0;
        while (it2.hasNext() && isHighPriorityFrame(it2.next())) {
            i++;
        }
        this.mFrames.add(i, webSocketFrame);
    }

    public void queueFlush() {
        synchronized (this) {
            this.mFlushNeeded = true;
            notifyAll();
        }
    }

    private void flushIgnoreError() {
        try {
            flush();
        } catch (IOException unused) {
        }
    }

    private void flush() throws IOException {
        this.mWebSocket.getOutput().flush();
    }

    private int waitForFrames() {
        synchronized (this) {
            if (this.mStopRequested) {
                return 1;
            }
            if (this.mCloseFrame != null) {
                return 1;
            }
            if (this.mFrames.size() == 0) {
                if (this.mFlushNeeded) {
                    this.mFlushNeeded = false;
                    return 3;
                }
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
            if (this.mStopRequested) {
                return 1;
            }
            if (this.mFrames.size() != 0) {
                return 0;
            }
            if (!this.mFlushNeeded) {
                return 2;
            }
            this.mFlushNeeded = false;
            return 3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002b, code lost:
        if (r2.isPongFrame() == false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x003a, code lost:
        doFlush();
        r0 = java.lang.System.currentTimeMillis();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0032, code lost:
        if (isFlushNeeded(r5) != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0035, code lost:
        r0 = flushIfLongInterval(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
        sendFrame(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0025, code lost:
        if (r2.isPingFrame() != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendFrames(boolean z) throws WebSocketException {
        long currentTimeMillis = System.currentTimeMillis();
        while (true) {
            synchronized (this) {
                WebSocketFrame poll = this.mFrames.poll();
                notifyAll();
                if (poll == null) {
                    break;
                }
            }
        }
        if (isFlushNeeded(z)) {
            doFlush();
        }
    }

    private boolean isFlushNeeded(boolean z) {
        return z || this.mWebSocket.isAutoFlush() || this.mFlushNeeded || this.mCloseFrame != null;
    }

    private long flushIfLongInterval(long j) throws WebSocketException {
        long currentTimeMillis = System.currentTimeMillis();
        if (1000 < currentTimeMillis - j) {
            doFlush();
            return currentTimeMillis;
        }
        return j;
    }

    private void doFlush() throws WebSocketException {
        try {
            flush();
            synchronized (this) {
                this.mFlushNeeded = false;
            }
        } catch (IOException e) {
            WebSocketError webSocketError = WebSocketError.FLUSH_ERROR;
            WebSocketException webSocketException = new WebSocketException(webSocketError, "Flushing frames to the server failed: " + e.getMessage(), e);
            ListenerManager listenerManager = this.mWebSocket.getListenerManager();
            listenerManager.callOnError(webSocketException);
            listenerManager.callOnSendError(webSocketException, null);
            throw webSocketException;
        }
    }

    private void sendFrame(WebSocketFrame webSocketFrame) throws WebSocketException {
        boolean z;
        WebSocketFrame compressFrame = WebSocketFrame.compressFrame(webSocketFrame, this.mPMCE);
        this.mWebSocket.getListenerManager().callOnSendingFrame(compressFrame);
        if (this.mCloseFrame != null) {
            z = true;
        } else {
            if (compressFrame.isCloseFrame()) {
                this.mCloseFrame = compressFrame;
            }
            z = false;
        }
        if (z) {
            this.mWebSocket.getListenerManager().callOnFrameUnsent(compressFrame);
            return;
        }
        if (compressFrame.isCloseFrame()) {
            changeToClosing();
        }
        try {
            this.mWebSocket.getOutput().write(compressFrame);
            this.mWebSocket.getListenerManager().callOnFrameSent(compressFrame);
        } catch (IOException e) {
            WebSocketError webSocketError = WebSocketError.IO_ERROR_IN_WRITING;
            WebSocketException webSocketException = new WebSocketException(webSocketError, "An I/O error occurred when a frame was tried to be sent: " + e.getMessage(), e);
            ListenerManager listenerManager = this.mWebSocket.getListenerManager();
            listenerManager.callOnError(webSocketException);
            listenerManager.callOnSendError(webSocketException, compressFrame);
            throw webSocketException;
        }
    }

    private void changeToClosing() {
        boolean z;
        StateManager stateManager = this.mWebSocket.getStateManager();
        synchronized (stateManager) {
            WebSocketState state = stateManager.getState();
            if (state == WebSocketState.CLOSING || state == WebSocketState.CLOSED) {
                z = false;
            } else {
                stateManager.changeToClosing(StateManager.CloseInitiator.CLIENT);
                z = true;
            }
        }
        if (z) {
            this.mWebSocket.getListenerManager().callOnStateChanged(WebSocketState.CLOSING);
        }
    }

    private void notifyFinished() {
        this.mWebSocket.onWritingThreadFinished(this.mCloseFrame);
    }
}
