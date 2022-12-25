package com.tomatolive.library.websocket.nvwebsocket;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.model.BaseSocketEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.SendMessageEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.model.p135db.MsgDetailListEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.MD5Utils;
import com.tomatolive.library.utils.SignRequestUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.emoji.EmojiParser;
import java.util.List;
import java.util.UUID;

/* loaded from: classes4.dex */
public class MessageHelper {
    private MessageHelper() {
    }

    public static String makeGiftMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_GIFT_TYPE);
    }

    private static String createMsg(SendMessageEntity sendMessageEntity, String str) {
        Gson create = new GsonBuilder().disableHtmlEscaping().create();
        BaseSocketEntity baseSocketEntity = new BaseSocketEntity();
        baseSocketEntity.setMessageType(str);
        baseSocketEntity.setBusinessData(sendMessageEntity);
        baseSocketEntity.setRandomStr(SignRequestUtils.getRandomString());
        baseSocketEntity.setTimestampStr(SignRequestUtils.getTimestampString());
        baseSocketEntity.setSign(formatSign(create, sendMessageEntity, baseSocketEntity));
        return create.toJson(baseSocketEntity);
    }

    private static String formatSign(@NonNull Gson gson, SendMessageEntity sendMessageEntity, BaseSocketEntity baseSocketEntity) {
        String json = gson.toJson(sendMessageEntity);
        return MD5Utils.hash(json + baseSocketEntity.getTimestampStr() + baseSocketEntity.getRandomStr() + TomatoLiveSDK.getSingleton().SIGN_SOCKET_KEY);
    }

    public static String makeEnterMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_ENTER_TYPE);
    }

    public static SocketMessageEvent parseSocketData(String str) {
        SocketMessageEvent socketMessageEvent;
        try {
        } catch (Exception e) {
            e = e;
            socketMessageEvent = null;
        } catch (OutOfMemoryError e2) {
            e = e2;
            socketMessageEvent = null;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        BaseSocketEntity baseSocketEntity = (BaseSocketEntity) new Gson().fromJson(str, new TypeToken<BaseSocketEntity<SocketMessageEvent>>() { // from class: com.tomatolive.library.websocket.nvwebsocket.MessageHelper.1
        }.getType());
        socketMessageEvent = (SocketMessageEvent) baseSocketEntity.getBusinessData();
        try {
            socketMessageEvent.messageType = baseSocketEntity.getMessageType();
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return socketMessageEvent;
        } catch (OutOfMemoryError e4) {
            e = e4;
            e.printStackTrace();
            return socketMessageEvent;
        }
        return socketMessageEvent;
    }

    public static String makeChatMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_CHAT_TYPE);
    }

    public static String makeLeaveMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, "leave");
    }

    public static String makeBannedMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_BAN_POST_TYPE);
    }

    public static String makeSuperBannedMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_LIVEADMIN_BANPOST_TYPE);
    }

    public static String makeSuperGoOutMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_LIVEADMIN_GOOUT_TYPE);
    }

    public static String makeGrabGiftBoxMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_GRAB_GIFT_BOX_TYPE);
    }

    public static String makeGoOutMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_GOOUT_TYPE);
    }

    public static String makeCtrlMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_LIVECONTROL_TYPE);
    }

    public static String makePostIntervalMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_POSTINTERVAL_TYPE);
    }

    public static String makeSpeakLevelMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_LIVE_SETTING_TYPE);
    }

    public static String makeBannedAllMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_BANPOSTALL_TYPE);
    }

    public static String makeShieldMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_SHIELD_TYPE);
    }

    public static String makeNotifyMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_NOTIFY_TYPE);
    }

    public static String makePropSendMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_PROP_SEND_TYPE);
    }

    public static String makeChatReceiptMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_CHAT_RECEIPT_TYPE);
    }

    public static String makeUserPrivateMsgMessage(SendMessageEntity sendMessageEntity) {
        if (sendMessageEntity == null) {
            return null;
        }
        return createMsg(sendMessageEntity, ConnectSocketParams.MESSAGE_USER_PRIVATE_MESSAGE_RECEIPT);
    }

    public static SendMessageEntity convertToGiftMsg(GiftItemEntity giftItemEntity) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.effectType = String.valueOf(giftItemEntity.effect_type);
        sendMessageEntity.markId = giftItemEntity.markId;
        sendMessageEntity.sex = UserInfoManager.getInstance().getUserSex();
        sendMessageEntity.giftNum = String.valueOf(giftItemEntity.giftNum);
        sendMessageEntity.giftName = giftItemEntity.name;
        sendMessageEntity.anchorId = giftItemEntity.anchorId;
        sendMessageEntity.anchorName = giftItemEntity.anchorName;
        sendMessageEntity.avatar = UserInfoManager.getInstance().getAvatar();
        sendMessageEntity.createTime = String.valueOf(System.currentTimeMillis());
        sendMessageEntity.price = giftItemEntity.price;
        sendMessageEntity.liveCount = giftItemEntity.liveCount;
        sendMessageEntity.clientIp = giftItemEntity.clientIp;
        sendMessageEntity.appId = UserInfoManager.getInstance().getAppId();
        sendMessageEntity.uuid = UUID.randomUUID().toString();
        sendMessageEntity.guardType = String.valueOf(giftItemEntity.guardType);
        sendMessageEntity.boxType = giftItemEntity.boxType;
        sendMessageEntity.giftCostType = giftItemEntity.giftCostType;
        sendMessageEntity.isStarGift = giftItemEntity.isStarGift;
        sendMessageEntity.followStatus = AppUtils.isAttentionAnchor(giftItemEntity.anchorId) ? "1" : "0";
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToBanMsg(String str, String str2, String str3, String str4) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.targetId = str;
        sendMessageEntity.targetName = str2;
        sendMessageEntity.duration = str3;
        sendMessageEntity.banPostStatus = str4;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToSuperBanMsg(String str, String str2, String str3) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.userId = UserInfoManager.getInstance().getUserId();
        sendMessageEntity.userName = UserInfoManager.getInstance().getUserNickname();
        sendMessageEntity.liveId = str;
        sendMessageEntity.targetId = str2;
        sendMessageEntity.targetName = str3;
        sendMessageEntity.banPostStatus = "1";
        sendMessageEntity.role = "5";
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToSuperGoOutMsg(String str, String str2, String str3) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.userId = UserInfoManager.getInstance().getUserId();
        sendMessageEntity.userName = UserInfoManager.getInstance().getUserNickname();
        sendMessageEntity.liveId = str;
        sendMessageEntity.targetId = str2;
        sendMessageEntity.targetName = str3;
        sendMessageEntity.role = "5";
        sendMessageEntity.goOutStatus = "1";
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToGrabGiftBoxMsg(String str) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.giftBoxUniqueCode = str;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToKickOutMsg(String str, String str2, String str3) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.userName = UserInfoManager.getInstance().getUserNickname();
        sendMessageEntity.targetId = str2;
        sendMessageEntity.targetName = str3;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToCtrlMsg(String str, String str2, String str3, boolean z) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.targetId = str2;
        sendMessageEntity.targetName = str3;
        sendMessageEntity.action = z ? "1" : "2";
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToShieldMsg(String str, String str2, boolean z) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.targetId = str2;
        sendMessageEntity.shieldStatus = z ? "1" : "2";
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToChatMsg(String str, int i) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.content = str;
        String str2 = "1";
        sendMessageEntity.openDanmu = i == 1 ? str2 : "0";
        if (i != 2) {
            str2 = "0";
        }
        sendMessageEntity.openNobilityDanmu = str2;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToPrivateChatMsg(MsgDetailListEntity msgDetailListEntity) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.content = msgDetailListEntity.msg;
        sendMessageEntity.openDanmu = "0";
        sendMessageEntity.openNobilityDanmu = "0";
        sendMessageEntity.messageId = msgDetailListEntity.messageId;
        sendMessageEntity.privateMsg = "1";
        sendMessageEntity.recipientId = msgDetailListEntity.targetId;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToEnterMsg(String str, String str2, boolean z, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.userId = UserInfoManager.getInstance().getUserId();
        sendMessageEntity.liveId = str2;
        sendMessageEntity.enterType = str;
        sendMessageEntity.appId = UserInfoManager.getInstance().getAppId();
        sendMessageEntity.isReconnect = z ? "1" : "0";
        sendMessageEntity.expGrade = str3;
        sendMessageEntity.userName = UserInfoManager.getInstance().getUserNickname();
        sendMessageEntity.avatar = UserInfoManager.getInstance().getAvatar();
        sendMessageEntity.sex = UserInfoManager.getInstance().getUserSex();
        sendMessageEntity.role = str4;
        sendMessageEntity.guardType = str5;
        sendMessageEntity.carId = str6;
        sendMessageEntity.carName = str7;
        sendMessageEntity.carIcon = str8;
        sendMessageEntity.carOnlineUrl = str9;
        sendMessageEntity.carResUrl = str10;
        sendMessageEntity.isPlayCarAnim = str11;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToNotifyMsg(String str, String str2) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.type = "2";
        sendMessageEntity.typeMsg = EmojiParser.removeAllEmojis(str2);
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToSpeakLevelMsg(String str, String str2) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.liveId = str;
        sendMessageEntity.changeType = ConnectSocketParams.MESSAGE_SPEAKLEVEL_TYPE;
        sendMessageEntity.changeValue = str2;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToAllBanMsg(String str, boolean z) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.banPostAllStatus = z ? "1" : "2";
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToPostInterval(String str, String str2) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.postIntervalTimes = str2;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToPropSend(String str, String str2, String str3) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.count = str;
        sendMessageEntity.propId = str2;
        sendMessageEntity.liveCount = str3;
        return sendMessageEntity;
    }

    public static SendMessageEntity convertToChatReceiptMsg(String str, String str2) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.senderId = str;
        sendMessageEntity.messageId = str2;
        sendMessageEntity.status = "1";
        return sendMessageEntity;
    }

    public static SendMessageEntity convertUserPrivateMsgReceiptMsg(String str, List<String> list) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        sendMessageEntity.offlineFlag = str;
        sendMessageEntity.messageIdList = list;
        return sendMessageEntity;
    }
}
