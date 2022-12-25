package com.tomatolive.library.model;

import android.text.TextUtils;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.websocket.nvwebsocket.ConnectSocketParams;

/* loaded from: classes3.dex */
public class LiveInitInfoEntity {
    public AnchorEntity anchorDto;
    public GuardItemEntity guardDto;

    /* renamed from: k */
    public String f5842k;
    public LiveEndEntity lastLiveData;
    public LiveItemEntity liveDto;
    public String liveStatus;
    public UserEntity myUserDto;
    public EnterMessageEntity myselfEnterMessageDto;

    public boolean isLiving() {
        return TextUtils.equals(this.liveStatus, "1");
    }

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
        UserEntity userEntity = this.myUserDto;
        if (userEntity != null) {
            resultData.expGrade = userEntity.expGrade;
            resultData.role = userEntity.role;
            resultData.markUrls = userEntity.getMarkUrls();
        }
        GuardItemEntity guardItemEntity = this.guardDto;
        if (guardItemEntity != null) {
            resultData.guardType = guardItemEntity.userGuardType;
        }
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
        if (!AppUtils.isEnableJoinLevel(resultData.expGrade) && !AppUtils.isChatEnterMsg(resultData.role, resultData.guardType, resultData.carId, resultData.nobilityType)) {
            resultData.userId = "";
        }
        socketMessageEvent.resultData = resultData;
        return socketMessageEvent;
    }
}
