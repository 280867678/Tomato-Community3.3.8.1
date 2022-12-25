package com.tomatolive.library.websocket.nvwebsocket;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes4.dex */
public class WsReceiver implements Handler.Callback {
    private static final int LOOP_GET_ANIM = 0;
    private static final int LOOP_GET_BIG_ANIM = 4;
    private static final int LOOP_GET_DATA = 2;
    private static final int LOOP_GET_LOCAL_BIG_ANIM = 3;
    private ConcurrentLinkedQueue<SocketMessageEvent> animQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<GiftItemEntity> bigAnimQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<GiftItemEntity> localBigAnimQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<String> metaDataQueue = new ConcurrentLinkedQueue<>();
    private Handler receiveMsgHandler;
    private HandlerThread receiveMsgHandlerThread;
    private WsManager wsManager;

    public WsReceiver(WsManager wsManager) {
        this.wsManager = wsManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void startReceiveThread() {
        HandlerThread handlerThread = this.receiveMsgHandlerThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            this.receiveMsgHandlerThread = new HandlerThread("receiveMsgHandler");
            this.receiveMsgHandlerThread.setPriority(4);
            this.receiveMsgHandlerThread.start();
            this.receiveMsgHandler = new Handler(this.receiveMsgHandlerThread.getLooper(), this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stopReceiveThread() {
        Handler handler = this.receiveMsgHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        HandlerThread handlerThread = this.receiveMsgHandlerThread;
        if (handlerThread != null) {
            try {
                if (Build.VERSION.SDK_INT >= 18) {
                    handlerThread.quitSafely();
                } else {
                    handlerThread.quit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        clearAnimQueue();
    }

    public void putMsg(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.metaDataQueue.offer(str);
        notifyReceiveMsg(2);
    }

    private void notifyReceiveMsg(int i) {
        HandlerThread handlerThread;
        Handler handler = this.receiveMsgHandler;
        if (handler == null || handler.hasMessages(i) || (handlerThread = this.receiveMsgHandlerThread) == null || !handlerThread.isAlive()) {
            return;
        }
        this.receiveMsgHandler.sendEmptyMessage(i);
    }

    public void putLocalAnimMsg(GiftItemEntity giftItemEntity) {
        ConcurrentLinkedQueue<GiftItemEntity> concurrentLinkedQueue = this.localBigAnimQueue;
        if (concurrentLinkedQueue == null || giftItemEntity == null) {
            return;
        }
        if (concurrentLinkedQueue.size() == 50) {
            this.localBigAnimQueue.poll();
        }
        this.localBigAnimQueue.offer(giftItemEntity);
        notifyReceiveMsg(3);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        GiftItemEntity poll;
        GiftItemEntity poll2;
        int i = message.what;
        if (i == 0) {
            SocketMessageEvent poll3 = this.animQueue.poll();
            if (poll3 == null || this.wsManager.getBackgroundSocketCallBack() == null) {
                return true;
            }
            this.wsManager.getBackgroundSocketCallBack().onBackThreadReceiveMessage(poll3);
            return true;
        } else if (i == 2) {
            dealMsgOnLoop();
            return true;
        } else if (i == 3) {
            if (!this.wsManager.isBigAnimFinished() || (poll = this.localBigAnimQueue.poll()) == null) {
                return true;
            }
            this.wsManager.setAnimFinish(false);
            if (this.wsManager.getBackgroundSocketCallBack() == null) {
                return true;
            }
            this.wsManager.getBackgroundSocketCallBack().onBackThreadReceiveBigAnimMsg(poll);
            return true;
        } else if (i != 4 || !this.wsManager.isBigAnimFinished()) {
            return true;
        } else {
            ConcurrentLinkedQueue<GiftItemEntity> concurrentLinkedQueue = this.localBigAnimQueue;
            if (concurrentLinkedQueue != null && !concurrentLinkedQueue.isEmpty()) {
                poll2 = null;
                notifyReceiveMsg(3);
            } else {
                poll2 = this.bigAnimQueue.poll();
            }
            if (poll2 == null) {
                return true;
            }
            this.wsManager.setAnimFinish(false);
            if (this.wsManager.getBackgroundSocketCallBack() == null) {
                return true;
            }
            this.wsManager.getBackgroundSocketCallBack().onBackThreadReceiveBigAnimMsg(poll2);
            return true;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void dealMsgOnLoop() {
        char c;
        SocketMessageEvent parseSocketData = MessageHelper.parseSocketData(this.metaDataQueue.poll());
        if (parseSocketData != null) {
            String str = parseSocketData.messageType;
            switch (str.hashCode()) {
                case -1981791680:
                    if (str.equals(ConnectSocketParams.MESSAGE_MSG_BROADCAST_TYPE)) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case -1758755289:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_MATCH_TIMEOUT_TYPE)) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case -1603387347:
                    if (str.equals(ConnectSocketParams.MESSAGE_OPEN_NOBILITY_BROADCAST_TYPE)) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case -1598856750:
                    if (str.equals(ConnectSocketParams.MESSAGE_BANPOSTALL_TYPE)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1483460454:
                    if (str.equals(ConnectSocketParams.MESSAGE_CONVERT_PAID_ROOM)) {
                        c = '1';
                        break;
                    }
                    c = 65535;
                    break;
                case -1330790228:
                    if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_ACCEPT)) {
                        c = '5';
                        break;
                    }
                    c = 65535;
                    break;
                case -1302490523:
                    if (str.equals(ConnectSocketParams.MESSAGE_CONSUME_NOTIFY_TYPE)) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case -1299831264:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_MATCH_CANCEL_TYPE)) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case -1268961704:
                    if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_CHARGE)) {
                        c = '7';
                        break;
                    }
                    c = 65535;
                    break;
                case -1256385881:
                    if (str.equals(ConnectSocketParams.MESSAGE_TOKEN_INVALID_NOTIFY_TYPE)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1244355024:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_LIANMAI_END_TYPE)) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case -1242004544:
                    if (str.equals(ConnectSocketParams.MESSAGE_CHAT_RECEIPT_TYPE)) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case -1052404422:
                    if (str.equals(ConnectSocketParams.MESSAGE_UPDATE_PAID_ROOM_PULL_STREAM_URL)) {
                        c = '2';
                        break;
                    }
                    c = 65535;
                    break;
                case -1039689911:
                    if (str.equals(ConnectSocketParams.MESSAGE_NOTIFY_TYPE)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -1033917736:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_LIANMAI_SUCCESS_TYPE)) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case -993690229:
                    if (str.equals(ConnectSocketParams.MESSAGE_PROP_SEND_TYPE)) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -992867598:
                    if (str.equals(ConnectSocketParams.MESSAGE_GRAB_GIFTBOX_BROADCAST_TYPE)) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case -941691210:
                    if (str.equals(ConnectSocketParams.MESSAGE_UNIVERSAL_BROADCAST_TYPE)) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -903340183:
                    if (str.equals(ConnectSocketParams.MESSAGE_SHIELD_TYPE)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -842142792:
                    if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_REFUSE)) {
                        c = '6';
                        break;
                    }
                    c = 65535;
                    break;
                case -634778976:
                    if (str.equals(ConnectSocketParams.MESSAGE_FORBID_LIVE_TYPE)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -566357442:
                    if (str.equals(ConnectSocketParams.MESSAGE_CHAT_BUY_LIVE_TICKET_TYPE)) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -535278809:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_START_TYPE)) {
                        c = '&';
                        break;
                    }
                    c = 65535;
                    break;
                case -370196576:
                    if (str.equals(ConnectSocketParams.MESSAGE_GENERAL_FLUTTERSCREEN_BROADCAST_TYPE)) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -355282718:
                    if (str.equals(ConnectSocketParams.MESSAGE_LIVE_DRAW_FINISHED)) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case -339185956:
                    if (str.equals(ConnectSocketParams.MESSAGE_BALANCE_TYPE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -337843889:
                    if (str.equals(ConnectSocketParams.MESSAGE_BAN_POST_TYPE)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -236148015:
                    if (str.equals(ConnectSocketParams.MESSAGE_LIVECONTROL_TYPE)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -148968596:
                    if (str.equals(ConnectSocketParams.MESSAGE_TURNTABLE_STATUS_UPDATE_TYPE)) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case -21216891:
                    if (str.equals(ConnectSocketParams.MESSAGE_POSTINTERVAL_TYPE)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 3052376:
                    if (str.equals(ConnectSocketParams.MESSAGE_CHAT_TYPE)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3172656:
                    if (str.equals(ConnectSocketParams.MESSAGE_GIFT_TYPE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3641990:
                    if (str.equals(ConnectSocketParams.MESSAGE_WARN_TYPE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 16548271:
                    if (str.equals(ConnectSocketParams.MESSAGE_USER_PRIVATE_MESSAGE)) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                case 96667762:
                    if (str.equals(ConnectSocketParams.MESSAGE_ENTER_TYPE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 98509126:
                    if (str.equals(ConnectSocketParams.MESSAGE_GOOUT_TYPE)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 99368259:
                    if (str.equals(ConnectSocketParams.MESSAGE_LIVEADMIN_GOOUT_TYPE)) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 102846135:
                    if (str.equals("leave")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 106691808:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_END_TYPE)) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case 129232527:
                    if (str.equals(ConnectSocketParams.MESSAGE_GRAB_GIFT_BOX_TYPE)) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 132753966:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_FIRST_KILL_TYPE)) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case 317295308:
                    if (str.equals(ConnectSocketParams.MESSAGE_USER_GRADE_TYPE)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 395254178:
                    if (str.equals(ConnectSocketParams.MESSAGE_NOBILITY_TRUMPET_BROADCAST_TYPE)) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case 441119852:
                    if (str.equals(ConnectSocketParams.MESSAGE_PUT_GIFT_BOX_TYPE)) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 441614241:
                    if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_SHOW)) {
                        c = '4';
                        break;
                    }
                    c = 65535;
                    break;
                case 487782924:
                    if (str.equals(ConnectSocketParams.MESSAGE_LIVEADMIN_BANPOST_TYPE)) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 692428113:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_INVITING_TYPE)) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case 720694193:
                    if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_CHARGE_COMPLETE)) {
                        c = '9';
                        break;
                    }
                    c = 65535;
                    break;
                case 798249924:
                    if (str.equals(ConnectSocketParams.MESSAGE_LIVE_SETTING_TYPE)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 805483582:
                    if (str.equals(ConnectSocketParams.MESSAGE_START_INTIMATE_TASK)) {
                        c = '3';
                        break;
                    }
                    c = 65535;
                    break;
                case 1225787890:
                    if (str.equals(ConnectSocketParams.MESSAGE_LIVE_DRAW_START)) {
                        c = '.';
                        break;
                    }
                    c = 65535;
                    break;
                case 1316826486:
                    if (str.equals(ConnectSocketParams.MESSAGE_INTIMATE_TASK_BID_FAILED)) {
                        c = '8';
                        break;
                    }
                    c = 65535;
                    break;
                case 1374344608:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_ASSIST_KING)) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                case 1585377747:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_NOTIFY_FP_TYPE)) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case 1672224412:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_MATCH_REJECT_TYPE)) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 1680327801:
                    if (str.equals(ConnectSocketParams.MESSAGE_GIFT_TRUMPET_TYPE)) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1982953673:
                    if (str.equals(ConnectSocketParams.MESSAGE_PK_MATCH_SUCCESS_TYPE)) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case 2021199175:
                    if (str.equals(ConnectSocketParams.MESSAGE_GRAB_GIFTBOX_NOTIFIED_TYPE)) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.animQueue.offer(parseSocketData);
                    notifyReceiveMsg(0);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case ' ':
                case '!':
                case '\"':
                case '#':
                case '$':
                case '%':
                case '&':
                case '\'':
                case '(':
                case ')':
                case '*':
                case '+':
                case ',':
                case '-':
                case '.':
                case '/':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (this.wsManager.getBackgroundSocketCallBack() != null) {
                        this.wsManager.getBackgroundSocketCallBack().onBackThreadReceiveMessage(parseSocketData);
                        break;
                    }
                    break;
            }
        }
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = this.metaDataQueue;
        if (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
            return;
        }
        notifyReceiveMsg(2);
    }

    public void notifyBigAnim() {
        notifyReceiveMsg(4);
    }

    public void notifyAnim() {
        notifyReceiveMsg(0);
    }

    public void putReceiveBigAnim(GiftItemEntity giftItemEntity) {
        ConcurrentLinkedQueue<GiftItemEntity> concurrentLinkedQueue = this.bigAnimQueue;
        if (concurrentLinkedQueue == null || giftItemEntity == null) {
            return;
        }
        if (concurrentLinkedQueue.size() == 50) {
            this.bigAnimQueue.poll();
        }
        this.bigAnimQueue.offer(giftItemEntity);
        notifyReceiveMsg(4);
    }

    public void clearAnimQueue() {
        this.metaDataQueue.clear();
        this.localBigAnimQueue.clear();
        this.bigAnimQueue.clear();
        this.animQueue.clear();
    }
}
