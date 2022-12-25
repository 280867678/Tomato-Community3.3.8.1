package com.tomatolive.library.model;

import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.websocket.nvwebsocket.ConnectSocketParams;

/* loaded from: classes3.dex */
public class AnchorStartLiveEntity extends LiveEntity {
    public EnterMessageEntity myselfEnterMessageDto;

    public SocketMessageEvent formatMyselfEnterMessageEvent() {
        SocketMessageEvent socketMessageEvent = new SocketMessageEvent();
        socketMessageEvent.code = "1";
        socketMessageEvent.messageType = ConnectSocketParams.MESSAGE_ENTER_TYPE;
        SocketMessageEvent.ResultData resultData = new SocketMessageEvent.ResultData();
        resultData.userId = UserInfoManager.getInstance().getUserId();
        resultData.userName = UserInfoManager.getInstance().getUserNickname();
        resultData.avatar = UserInfoManager.getInstance().getAvatar();
        resultData.sex = UserInfoManager.getInstance().getUserSex();
        resultData.appId = UserInfoManager.getInstance().getAppId();
        resultData.openId = UserInfoManager.getInstance().getAppOpenId();
        resultData.isEnterHide = UserInfoManager.getInstance().isEnterHide() ? 1 : 0;
        resultData.role = "1";
        resultData.expGrade = this.expGrade;
        resultData.markUrls = this.markUrls;
        EnterMessageEntity enterMessageEntity = this.myselfEnterMessageDto;
        if (enterMessageEntity != null) {
            resultData.userRole = enterMessageEntity.userRole;
            resultData.nobilityType = NumberUtils.string2int(enterMessageEntity.nobilityType);
            EnterMessageEntity enterMessageEntity2 = this.myselfEnterMessageDto;
            resultData.isPlayNobilityEnterAnimation = enterMessageEntity2.isPlayNobilityEnterAnimation;
            if (AppUtils.hasCar(enterMessageEntity2.carId)) {
                EnterMessageEntity enterMessageEntity3 = this.myselfEnterMessageDto;
                resultData.carId = enterMessageEntity3.carId;
                resultData.carName = enterMessageEntity3.carName;
                resultData.carOnlineUrl = enterMessageEntity3.carOnlineUrl;
                resultData.carIcon = enterMessageEntity3.carIcon;
                resultData.isPlayCarAnim = enterMessageEntity3.isPlayCarAnim;
            }
        }
        socketMessageEvent.resultData = resultData;
        return socketMessageEvent;
    }
}
