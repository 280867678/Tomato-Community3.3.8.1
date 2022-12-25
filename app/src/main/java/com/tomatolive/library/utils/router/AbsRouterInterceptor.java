package com.tomatolive.library.utils.router;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.activity.home.AnchorAuthResultActivity;
import com.tomatolive.library.p136ui.activity.home.TransparentDialogActivity;
import com.tomatolive.library.p136ui.activity.noble.NobilityOpenActivity;
import com.tomatolive.library.p136ui.activity.noble.NobilityPrivilegeActivity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.LiveManagerUtils;
import com.tomatolive.library.utils.router.callbacks.AnchorAuthCallBack;
import com.tomatolive.library.utils.router.callbacks.InterceptorCallback;

/* loaded from: classes4.dex */
public abstract class AbsRouterInterceptor implements IInterceptor {
    abstract void onProcess(String str, Intent intent, Context context, InterceptorCallback interceptorCallback);

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.tomatolive.library.utils.router.IInterceptor
    public void process(String str, Intent intent, Context context, InterceptorCallback interceptorCallback) {
        char c;
        switch (str.hashCode()) {
            case -1652202474:
                if (str.equals(ConstantUtils.ROUTER_TYPE_RANKING)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1060275016:
                if (str.equals(ConstantUtils.ROUTER_TYPE_MY_LIVE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -906336856:
                if (str.equals(ConstantUtils.ROUTER_TYPE_SEARCH)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -736437516:
                if (str.equals("openNobility")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -353951458:
                if (str.equals(ConstantUtils.ROUTER_TYPE_ATTENTION)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 96673:
                if (str.equals("all")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 104336008:
                if (str.equals(ConstantUtils.ROUTER_TYPE_MY_CAR)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 354942579:
                if (str.equals(ConstantUtils.ROUTER_TYPE_PREPARE_LIVE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 553250824:
                if (str.equals(ConstantUtils.ROUTER_TYPE_CAR_MALL)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 989204668:
                if (str.equals("recommend")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1417629671:
                if (str.equals(ConstantUtils.ROUTER_TYPE_LIVE_ROOM)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1531068502:
                if (str.equals(ConstantUtils.ROUTER_TYPE_MY_NOBILITY)) {
                    c = 4;
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
                dealMyCarJump(intent, context, interceptorCallback);
                return;
            case 1:
                dealPrepareLiveJump(intent, context, interceptorCallback);
                return;
            case 2:
                dealMyLiveJump(intent, context, interceptorCallback);
                return;
            case 3:
                dealOpenNobilityJump(intent, context, interceptorCallback);
                return;
            case 4:
                dealMyNobilityJump(intent, context, interceptorCallback);
                return;
            case 5:
                dealLiveRoomJump(intent, context, interceptorCallback);
                return;
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
                interceptorCallback.onContinue(intent);
                return;
            default:
                onProcess(str, intent, context, interceptorCallback);
                return;
        }
    }

    private void dealOpenNobilityJump(Intent intent, Context context, InterceptorCallback interceptorCallback) {
        if (!AppUtils.isConsumptionPermissionUser(context)) {
            return;
        }
        if (AppUtils.isNobilityUser()) {
            Intent intent2 = new Intent(context, NobilityPrivilegeActivity.class);
            intent2.addFlags(268435456);
            interceptorCallback.onContinue(intent2);
            return;
        }
        interceptorCallback.onContinue(intent);
    }

    private void dealLiveRoomJump(Intent intent, Context context, InterceptorCallback interceptorCallback) {
        if (TextUtils.isEmpty(UserInfoManager.getInstance().getToken())) {
            AppUtils.onLoginListener(context);
        } else if (AppUtils.isVisitor() && !SysConfigInfoManager.getInstance().isEnableVisitorLive()) {
            AppUtils.onLoginListener(context);
        } else {
            LiveEntity liveEntity = new LiveEntity();
            liveEntity.liveId = intent.getStringExtra(ConstantUtils.RESULT_ID);
            LiveManagerUtils.getInstance().initCurrentLiveItemInfo(liveEntity.liveId, liveEntity);
            interceptorCallback.onContinue(intent);
        }
    }

    private void dealMyNobilityJump(Intent intent, Context context, InterceptorCallback interceptorCallback) {
        if (!AppUtils.isConsumptionPermissionUser(context)) {
            return;
        }
        if (AppUtils.isNobilityUser()) {
            interceptorCallback.onContinue(intent);
            return;
        }
        Intent intent2 = new Intent(context, NobilityOpenActivity.class);
        intent2.addFlags(268435456);
        interceptorCallback.onContinue(intent2);
    }

    private void dealMyLiveJump(Intent intent, Context context, InterceptorCallback interceptorCallback) {
        if (!AppUtils.isConsumptionPermissionUser(context)) {
            return;
        }
        interceptorCallback.onContinue(intent);
    }

    private void dealPrepareLiveJump(final Intent intent, final Context context, final InterceptorCallback interceptorCallback) {
        if (!AppUtils.isLogin(context)) {
            return;
        }
        ApiRequestManager.getInstance().onAnchorAuth(new AnchorAuthCallBack() { // from class: com.tomatolive.library.utils.router.-$$Lambda$AbsRouterInterceptor$Zpyb2s0MHuO9JBODTm3zFK2FyS8
            @Override // com.tomatolive.library.utils.router.callbacks.AnchorAuthCallBack
            public final void onAnchorAuthCallBack(AnchorEntity anchorEntity) {
                AbsRouterInterceptor.lambda$dealPrepareLiveJump$0(context, interceptorCallback, intent, anchorEntity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$dealPrepareLiveJump$0(Context context, InterceptorCallback interceptorCallback, Intent intent, AnchorEntity anchorEntity) {
        int i = anchorEntity.isChecked;
        if (i == -2) {
            Intent intent2 = new Intent(context, TransparentDialogActivity.class);
            intent2.putExtra(ConstantUtils.RESULT_FLAG, TransparentDialogActivity.DIALOG_TYPE_AUTH);
            intent2.addFlags(268435456);
            interceptorCallback.onContinue(intent2);
        } else if (i == -1 || i == 0) {
            Intent intent3 = new Intent(context, AnchorAuthResultActivity.class);
            intent3.addFlags(268435456);
            intent3.putExtra(ConstantUtils.AUTH_TYPE, anchorEntity.isChecked);
            interceptorCallback.onContinue(intent3);
        } else if (i != 1) {
        } else {
            if (anchorEntity.isFrozenFlag()) {
                Intent intent4 = new Intent(context, TransparentDialogActivity.class);
                intent4.putExtra(ConstantUtils.RESULT_FLAG, TransparentDialogActivity.DIALOG_TYPE_WARN);
                intent4.addFlags(268435456);
                interceptorCallback.onContinue(intent4);
                return;
            }
            interceptorCallback.onContinue(intent);
        }
    }

    private void dealMyCarJump(Intent intent, Context context, InterceptorCallback interceptorCallback) {
        if (!AppUtils.isConsumptionPermissionUser(context)) {
            interceptorCallback.onInterrupt(null);
        } else {
            interceptorCallback.onContinue(intent);
        }
    }
}
