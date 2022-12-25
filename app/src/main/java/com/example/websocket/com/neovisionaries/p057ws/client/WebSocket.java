package com.example.websocket.com.neovisionaries.p057ws.client;

import com.example.websocket.com.neovisionaries.p057ws.client.StateManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.WebSocket */
/* loaded from: classes2.dex */
public class WebSocket {
    private static final long DEFAULT_CLOSE_DELAY = 10000;
    private List<WebSocketExtension> mAgreedExtensions;
    private String mAgreedProtocol;
    private WebSocketFrame mClientCloseFrame;
    private boolean mDirectTextMessage;
    private boolean mExtended;
    private int mFrameQueueSize;
    private HandshakeBuilder mHandshakeBuilder;
    private WebSocketInputStream mInput;
    private int mMaxPayloadSize;
    private boolean mOnConnectedCalled;
    private WebSocketOutputStream mOutput;
    private PerMessageCompressionExtension mPerMessageCompressionExtension;
    private ReadingThread mReadingThread;
    private boolean mReadingThreadFinished;
    private boolean mReadingThreadStarted;
    private WebSocketFrame mServerCloseFrame;
    private Map<String, List<String>> mServerHeaders;
    private final SocketConnector mSocketConnector;
    private final WebSocketFactory mWebSocketFactory;
    private WritingThread mWritingThread;
    private boolean mWritingThreadFinished;
    private boolean mWritingThreadStarted;
    private final Object mThreadsLock = new Object();
    private boolean mAutoFlush = true;
    private boolean mMissingCloseFrameAllowed = true;
    private Object mOnConnectedCalledLock = new Object();
    private final StateManager mStateManager = new StateManager();
    private final ListenerManager mListenerManager = new ListenerManager(this);
    private final PingSender mPingSender = new PingSender(this, new CounterPayloadGenerator());
    private final PongSender mPongSender = new PongSender(this, new CounterPayloadGenerator());

    /* JADX INFO: Access modifiers changed from: package-private */
    public WebSocket(WebSocketFactory webSocketFactory, boolean z, String str, String str2, String str3, SocketConnector socketConnector) {
        this.mWebSocketFactory = webSocketFactory;
        this.mSocketConnector = socketConnector;
        this.mHandshakeBuilder = new HandshakeBuilder(z, str, str2, str3);
    }

    public WebSocket recreate() throws IOException {
        return recreate(this.mSocketConnector.getConnectionTimeout());
    }

    public WebSocket recreate(int i) throws IOException {
        if (i < 0) {
            throw new IllegalArgumentException("The given timeout value is negative.");
        }
        WebSocket createSocket = this.mWebSocketFactory.createSocket(getURI(), i);
        createSocket.mHandshakeBuilder = new HandshakeBuilder(this.mHandshakeBuilder);
        createSocket.setPingInterval(getPingInterval());
        createSocket.setPongInterval(getPongInterval());
        createSocket.setPingPayloadGenerator(getPingPayloadGenerator());
        createSocket.setPongPayloadGenerator(getPongPayloadGenerator());
        createSocket.mExtended = this.mExtended;
        createSocket.mAutoFlush = this.mAutoFlush;
        createSocket.mMissingCloseFrameAllowed = this.mMissingCloseFrameAllowed;
        createSocket.mDirectTextMessage = this.mDirectTextMessage;
        createSocket.mFrameQueueSize = this.mFrameQueueSize;
        List<WebSocketListener> listeners = this.mListenerManager.getListeners();
        synchronized (listeners) {
            createSocket.addListeners(listeners);
        }
        return createSocket;
    }

    protected void finalize() throws Throwable {
        if (isInState(WebSocketState.CREATED)) {
            finish();
        }
        super.finalize();
    }

    public WebSocketState getState() {
        WebSocketState state;
        synchronized (this.mStateManager) {
            state = this.mStateManager.getState();
        }
        return state;
    }

    public boolean isOpen() {
        return isInState(WebSocketState.OPEN);
    }

    private boolean isInState(WebSocketState webSocketState) {
        boolean z;
        synchronized (this.mStateManager) {
            z = this.mStateManager.getState() == webSocketState;
        }
        return z;
    }

    public WebSocket addProtocol(String str) {
        this.mHandshakeBuilder.addProtocol(str);
        return this;
    }

    public WebSocket removeProtocol(String str) {
        this.mHandshakeBuilder.removeProtocol(str);
        return this;
    }

    public WebSocket clearProtocols() {
        this.mHandshakeBuilder.clearProtocols();
        return this;
    }

    public WebSocket addExtension(WebSocketExtension webSocketExtension) {
        this.mHandshakeBuilder.addExtension(webSocketExtension);
        return this;
    }

    public WebSocket addExtension(String str) {
        this.mHandshakeBuilder.addExtension(str);
        return this;
    }

    public WebSocket removeExtension(WebSocketExtension webSocketExtension) {
        this.mHandshakeBuilder.removeExtension(webSocketExtension);
        return this;
    }

    public WebSocket removeExtensions(String str) {
        this.mHandshakeBuilder.removeExtensions(str);
        return this;
    }

    public WebSocket clearExtensions() {
        this.mHandshakeBuilder.clearExtensions();
        return this;
    }

    public WebSocket addHeader(String str, String str2) {
        this.mHandshakeBuilder.addHeader(str, str2);
        return this;
    }

    public WebSocket removeHeaders(String str) {
        this.mHandshakeBuilder.removeHeaders(str);
        return this;
    }

    public WebSocket clearHeaders() {
        this.mHandshakeBuilder.clearHeaders();
        return this;
    }

    public WebSocket setUserInfo(String str) {
        this.mHandshakeBuilder.setUserInfo(str);
        return this;
    }

    public WebSocket setUserInfo(String str, String str2) {
        this.mHandshakeBuilder.setUserInfo(str, str2);
        return this;
    }

    public WebSocket clearUserInfo() {
        this.mHandshakeBuilder.clearUserInfo();
        return this;
    }

    public boolean isExtended() {
        return this.mExtended;
    }

    public WebSocket setExtended(boolean z) {
        this.mExtended = z;
        return this;
    }

    public boolean isAutoFlush() {
        return this.mAutoFlush;
    }

    public WebSocket setAutoFlush(boolean z) {
        this.mAutoFlush = z;
        return this;
    }

    public boolean isMissingCloseFrameAllowed() {
        return this.mMissingCloseFrameAllowed;
    }

    public WebSocket setMissingCloseFrameAllowed(boolean z) {
        this.mMissingCloseFrameAllowed = z;
        return this;
    }

    public boolean isDirectTextMessage() {
        return this.mDirectTextMessage;
    }

    public WebSocket setDirectTextMessage(boolean z) {
        this.mDirectTextMessage = z;
        return this;
    }

    public WebSocket flush() {
        synchronized (this.mStateManager) {
            WebSocketState state = this.mStateManager.getState();
            if (state == WebSocketState.OPEN || state == WebSocketState.CLOSING) {
                WritingThread writingThread = this.mWritingThread;
                if (writingThread != null) {
                    writingThread.queueFlush();
                }
                return this;
            }
            return this;
        }
    }

    public int getFrameQueueSize() {
        return this.mFrameQueueSize;
    }

    public WebSocket setFrameQueueSize(int i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException("size must not be negative.");
        }
        this.mFrameQueueSize = i;
        return this;
    }

    public int getMaxPayloadSize() {
        return this.mMaxPayloadSize;
    }

    public WebSocket setMaxPayloadSize(int i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException("size must not be negative.");
        }
        this.mMaxPayloadSize = i;
        return this;
    }

    public long getPingInterval() {
        return this.mPingSender.getInterval();
    }

    public WebSocket setPingInterval(long j) {
        this.mPingSender.setInterval(j);
        return this;
    }

    public long getPongInterval() {
        return this.mPongSender.getInterval();
    }

    public WebSocket setPongInterval(long j) {
        this.mPongSender.setInterval(j);
        return this;
    }

    public PayloadGenerator getPingPayloadGenerator() {
        return this.mPingSender.getPayloadGenerator();
    }

    public WebSocket setPingPayloadGenerator(PayloadGenerator payloadGenerator) {
        this.mPingSender.setPayloadGenerator(payloadGenerator);
        return this;
    }

    public PayloadGenerator getPongPayloadGenerator() {
        return this.mPongSender.getPayloadGenerator();
    }

    public WebSocket setPongPayloadGenerator(PayloadGenerator payloadGenerator) {
        this.mPongSender.setPayloadGenerator(payloadGenerator);
        return this;
    }

    public String getPingSenderName() {
        return this.mPingSender.getTimerName();
    }

    public WebSocket setPingSenderName(String str) {
        this.mPingSender.setTimerName(str);
        return this;
    }

    public String getPongSenderName() {
        return this.mPongSender.getTimerName();
    }

    public WebSocket setPongSenderName(String str) {
        this.mPongSender.setTimerName(str);
        return this;
    }

    public WebSocket addListener(WebSocketListener webSocketListener) {
        this.mListenerManager.addListener(webSocketListener);
        return this;
    }

    public WebSocket addListeners(List<WebSocketListener> list) {
        this.mListenerManager.addListeners(list);
        return this;
    }

    public WebSocket removeListener(WebSocketListener webSocketListener) {
        this.mListenerManager.removeListener(webSocketListener);
        return this;
    }

    public WebSocket removeListeners(List<WebSocketListener> list) {
        this.mListenerManager.removeListeners(list);
        return this;
    }

    public WebSocket clearListeners() {
        this.mListenerManager.clearListeners();
        return this;
    }

    public Socket getSocket() {
        return this.mSocketConnector.getSocket();
    }

    public URI getURI() {
        return this.mHandshakeBuilder.getURI();
    }

    public WebSocket connect() throws WebSocketException {
        changeStateOnConnect();
        try {
            this.mSocketConnector.connect();
            this.mServerHeaders = shakeHands();
            this.mPerMessageCompressionExtension = findAgreedPerMessageCompressionExtension();
            this.mStateManager.setState(WebSocketState.OPEN);
            this.mListenerManager.callOnStateChanged(WebSocketState.OPEN);
            startThreads();
            return this;
        } catch (WebSocketException e) {
            this.mSocketConnector.closeSilently();
            this.mStateManager.setState(WebSocketState.CLOSED);
            this.mListenerManager.callOnStateChanged(WebSocketState.CLOSED);
            throw e;
        }
    }

    public Future<WebSocket> connect(ExecutorService executorService) {
        return executorService.submit(connectable());
    }

    public Callable<WebSocket> connectable() {
        return new Connectable(this);
    }

    public WebSocket connectAsynchronously() {
        ConnectThread connectThread = new ConnectThread(this);
        ListenerManager listenerManager = this.mListenerManager;
        if (listenerManager != null) {
            listenerManager.callOnThreadCreated(ThreadType.CONNECT_THREAD, connectThread);
        }
        connectThread.start();
        return this;
    }

    public WebSocket disconnect() {
        return disconnect(1000, null);
    }

    public WebSocket disconnect(int i) {
        return disconnect(i, null);
    }

    public WebSocket disconnect(String str) {
        return disconnect(1000, str);
    }

    public WebSocket disconnect(int i, String str) {
        return disconnect(i, str, DEFAULT_CLOSE_DELAY);
    }

    public WebSocket disconnect(int i, String str, long j) {
        synchronized (this.mStateManager) {
            int i2 = C12661.f1253x7304fc9c[this.mStateManager.getState().ordinal()];
            if (i2 == 1) {
                finishAsynchronously();
                return this;
            } else if (i2 != 2) {
                return this;
            } else {
                this.mStateManager.changeToClosing(StateManager.CloseInitiator.CLIENT);
                sendFrame(WebSocketFrame.createCloseFrame(i, str));
                this.mListenerManager.callOnStateChanged(WebSocketState.CLOSING);
                if (j < 0) {
                    j = DEFAULT_CLOSE_DELAY;
                }
                stopThreads(j);
                return this;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.example.websocket.com.neovisionaries.ws.client.WebSocket$1 */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class C12661 {

        /* renamed from: $SwitchMap$com$example$websocket$com$neovisionaries$ws$client$WebSocketState */
        static final /* synthetic */ int[] f1253x7304fc9c = new int[WebSocketState.values().length];

        static {
            try {
                f1253x7304fc9c[WebSocketState.CREATED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1253x7304fc9c[WebSocketState.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public List<WebSocketExtension> getAgreedExtensions() {
        return this.mAgreedExtensions;
    }

    public String getAgreedProtocol() {
        return this.mAgreedProtocol;
    }

    public WebSocket sendFrame(WebSocketFrame webSocketFrame) {
        if (webSocketFrame == null) {
            return this;
        }
        synchronized (this.mStateManager) {
            WebSocketState state = this.mStateManager.getState();
            if (state != WebSocketState.OPEN && state != WebSocketState.CLOSING) {
                return this;
            }
            WritingThread writingThread = this.mWritingThread;
            if (writingThread == null) {
                return this;
            }
            List<WebSocketFrame> splitIfNecessary = splitIfNecessary(webSocketFrame);
            if (splitIfNecessary == null) {
                writingThread.queueFrame(webSocketFrame);
            } else {
                for (WebSocketFrame webSocketFrame2 : splitIfNecessary) {
                    writingThread.queueFrame(webSocketFrame2);
                }
            }
            return this;
        }
    }

    private List<WebSocketFrame> splitIfNecessary(WebSocketFrame webSocketFrame) {
        return WebSocketFrame.splitIfNecessary(webSocketFrame, this.mMaxPayloadSize, this.mPerMessageCompressionExtension);
    }

    public WebSocket sendContinuation() {
        return sendFrame(WebSocketFrame.createContinuationFrame());
    }

    public WebSocket sendContinuation(boolean z) {
        return sendFrame(WebSocketFrame.createContinuationFrame().setFin(z));
    }

    public WebSocket sendContinuation(String str) {
        return sendFrame(WebSocketFrame.createContinuationFrame(str));
    }

    public WebSocket sendContinuation(String str, boolean z) {
        return sendFrame(WebSocketFrame.createContinuationFrame(str).setFin(z));
    }

    public WebSocket sendContinuation(byte[] bArr) {
        return sendFrame(WebSocketFrame.createContinuationFrame(bArr));
    }

    public WebSocket sendContinuation(byte[] bArr, boolean z) {
        return sendFrame(WebSocketFrame.createContinuationFrame(bArr).setFin(z));
    }

    public WebSocket sendText(String str) {
        return sendFrame(WebSocketFrame.createTextFrame(str));
    }

    public WebSocket sendText(String str, boolean z) {
        return sendFrame(WebSocketFrame.createTextFrame(str).setFin(z));
    }

    public WebSocket sendBinary(byte[] bArr) {
        return sendFrame(WebSocketFrame.createBinaryFrame(bArr));
    }

    public WebSocket sendBinary(byte[] bArr, boolean z) {
        return sendFrame(WebSocketFrame.createBinaryFrame(bArr).setFin(z));
    }

    public WebSocket sendClose() {
        return sendFrame(WebSocketFrame.createCloseFrame());
    }

    public WebSocket sendClose(int i) {
        return sendFrame(WebSocketFrame.createCloseFrame(i));
    }

    public WebSocket sendClose(int i, String str) {
        return sendFrame(WebSocketFrame.createCloseFrame(i, str));
    }

    public WebSocket sendPing() {
        return sendFrame(WebSocketFrame.createPingFrame());
    }

    public WebSocket sendPing(byte[] bArr) {
        return sendFrame(WebSocketFrame.createPingFrame(bArr));
    }

    public WebSocket sendPing(String str) {
        return sendFrame(WebSocketFrame.createPingFrame(str));
    }

    public WebSocket sendPong() {
        return sendFrame(WebSocketFrame.createPongFrame());
    }

    public WebSocket sendPong(byte[] bArr) {
        return sendFrame(WebSocketFrame.createPongFrame(bArr));
    }

    public WebSocket sendPong(String str) {
        return sendFrame(WebSocketFrame.createPongFrame(str));
    }

    private void changeStateOnConnect() throws WebSocketException {
        synchronized (this.mStateManager) {
            if (this.mStateManager.getState() != WebSocketState.CREATED) {
                throw new WebSocketException(WebSocketError.NOT_IN_CREATED_STATE, "The current state of the WebSocket is not CREATED.");
            }
            this.mStateManager.setState(WebSocketState.CONNECTING);
        }
        this.mListenerManager.callOnStateChanged(WebSocketState.CONNECTING);
    }

    private Map<String, List<String>> shakeHands() throws WebSocketException {
        Socket socket = this.mSocketConnector.getSocket();
        WebSocketInputStream openInputStream = openInputStream(socket);
        WebSocketOutputStream openOutputStream = openOutputStream(socket);
        String generateWebSocketKey = generateWebSocketKey();
        writeHandshake(openOutputStream, generateWebSocketKey);
        Map<String, List<String>> readHandshake = readHandshake(openInputStream, generateWebSocketKey);
        this.mInput = openInputStream;
        this.mOutput = openOutputStream;
        return readHandshake;
    }

    private WebSocketInputStream openInputStream(Socket socket) throws WebSocketException {
        try {
            return new WebSocketInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            WebSocketError webSocketError = WebSocketError.SOCKET_INPUT_STREAM_FAILURE;
            throw new WebSocketException(webSocketError, "Failed to get the input stream of the raw socket: " + e.getMessage(), e);
        }
    }

    private WebSocketOutputStream openOutputStream(Socket socket) throws WebSocketException {
        try {
            return new WebSocketOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            WebSocketError webSocketError = WebSocketError.SOCKET_OUTPUT_STREAM_FAILURE;
            throw new WebSocketException(webSocketError, "Failed to get the output stream from the raw socket: " + e.getMessage(), e);
        }
    }

    private static String generateWebSocketKey() {
        byte[] bArr = new byte[16];
        Misc.nextBytes(bArr);
        return Base64.encode(bArr);
    }

    private void writeHandshake(WebSocketOutputStream webSocketOutputStream, String str) throws WebSocketException {
        this.mHandshakeBuilder.setKey(str);
        String buildRequestLine = this.mHandshakeBuilder.buildRequestLine();
        List<String[]> buildHeaders = this.mHandshakeBuilder.buildHeaders();
        String build = HandshakeBuilder.build(buildRequestLine, buildHeaders);
        this.mListenerManager.callOnSendingHandshake(buildRequestLine, buildHeaders);
        try {
            webSocketOutputStream.write(build);
            webSocketOutputStream.flush();
        } catch (IOException e) {
            WebSocketError webSocketError = WebSocketError.OPENING_HAHDSHAKE_REQUEST_FAILURE;
            throw new WebSocketException(webSocketError, "Failed to send an opening handshake request to the server: " + e.getMessage(), e);
        }
    }

    private Map<String, List<String>> readHandshake(WebSocketInputStream webSocketInputStream, String str) throws WebSocketException {
        return new HandshakeReader(this).readHandshake(webSocketInputStream, str);
    }

    private void startThreads() {
        ReadingThread readingThread = new ReadingThread(this);
        WritingThread writingThread = new WritingThread(this);
        synchronized (this.mThreadsLock) {
            this.mReadingThread = readingThread;
            this.mWritingThread = writingThread;
        }
        readingThread.callOnThreadCreated();
        writingThread.callOnThreadCreated();
        readingThread.start();
        writingThread.start();
    }

    private void stopThreads(long j) {
        ReadingThread readingThread;
        WritingThread writingThread;
        synchronized (this.mThreadsLock) {
            readingThread = this.mReadingThread;
            writingThread = this.mWritingThread;
            this.mReadingThread = null;
            this.mWritingThread = null;
        }
        if (readingThread != null) {
            readingThread.requestStop(j);
        }
        if (writingThread != null) {
            writingThread.requestStop();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WebSocketInputStream getInput() {
        return this.mInput;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WebSocketOutputStream getOutput() {
        return this.mOutput;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public StateManager getStateManager() {
        return this.mStateManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ListenerManager getListenerManager() {
        return this.mListenerManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public HandshakeBuilder getHandshakeBuilder() {
        return this.mHandshakeBuilder;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAgreedExtensions(List<WebSocketExtension> list) {
        this.mAgreedExtensions = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAgreedProtocol(String str) {
        this.mAgreedProtocol = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onReadingThreadStarted() {
        boolean z;
        synchronized (this.mThreadsLock) {
            this.mReadingThreadStarted = true;
            z = this.mWritingThreadStarted;
        }
        callOnConnectedIfNotYet();
        if (z) {
            onThreadsStarted();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onWritingThreadStarted() {
        boolean z;
        synchronized (this.mThreadsLock) {
            this.mWritingThreadStarted = true;
            z = this.mReadingThreadStarted;
        }
        callOnConnectedIfNotYet();
        if (z) {
            onThreadsStarted();
        }
    }

    private void callOnConnectedIfNotYet() {
        synchronized (this.mOnConnectedCalledLock) {
            if (this.mOnConnectedCalled) {
                return;
            }
            this.mOnConnectedCalled = true;
            this.mListenerManager.callOnConnected(this.mServerHeaders);
        }
    }

    private void onThreadsStarted() {
        this.mPingSender.start();
        this.mPongSender.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onReadingThreadFinished(WebSocketFrame webSocketFrame) {
        synchronized (this.mThreadsLock) {
            this.mReadingThreadFinished = true;
            this.mServerCloseFrame = webSocketFrame;
            if (!this.mWritingThreadFinished) {
                return;
            }
            onThreadsFinished();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onWritingThreadFinished(WebSocketFrame webSocketFrame) {
        synchronized (this.mThreadsLock) {
            this.mWritingThreadFinished = true;
            this.mClientCloseFrame = webSocketFrame;
            if (!this.mReadingThreadFinished) {
                return;
            }
            onThreadsFinished();
        }
    }

    private void onThreadsFinished() {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void finish() {
        this.mPingSender.stop();
        this.mPongSender.stop();
        try {
            this.mSocketConnector.getSocket().close();
        } catch (Throwable unused) {
        }
        synchronized (this.mStateManager) {
            this.mStateManager.setState(WebSocketState.CLOSED);
        }
        this.mListenerManager.callOnStateChanged(WebSocketState.CLOSED);
        this.mListenerManager.callOnDisconnected(this.mServerCloseFrame, this.mClientCloseFrame, this.mStateManager.getClosedByServer());
    }

    private void finishAsynchronously() {
        FinishThread finishThread = new FinishThread(this);
        finishThread.callOnThreadCreated();
        finishThread.start();
    }

    private PerMessageCompressionExtension findAgreedPerMessageCompressionExtension() {
        List<WebSocketExtension> list = this.mAgreedExtensions;
        if (list == null) {
            return null;
        }
        for (WebSocketExtension webSocketExtension : list) {
            if (webSocketExtension instanceof PerMessageCompressionExtension) {
                return (PerMessageCompressionExtension) webSocketExtension;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PerMessageCompressionExtension getPerMessageCompressionExtension() {
        return this.mPerMessageCompressionExtension;
    }
}
