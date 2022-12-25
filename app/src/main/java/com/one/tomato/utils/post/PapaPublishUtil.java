package com.one.tomato.utils.post;

import android.content.Context;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.entity.event.PublishTypeEvent;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.p085ui.publish.PapaPublishStatusActivity;
import com.one.tomato.p085ui.publish.PublishActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.UserPermissionUtil;
import de.greenrobot.event.EventBus;

/* loaded from: classes3.dex */
public class PapaPublishUtil {
    private static PapaPublishUtil publishUtil;
    private PublishListener listener;
    private Context mContext;
    private PublishInfo publishInfo;
    private int publishType = -1;

    /* loaded from: classes3.dex */
    public interface PublishListener {
        void publishDefault();

        void publishFail(Context context, PublishInfo publishInfo);

        void publishIng(boolean z);

        void publishSuccess();
    }

    private PapaPublishUtil() {
    }

    public static PapaPublishUtil getInstance() {
        if (publishUtil == null) {
            publishUtil = new PapaPublishUtil();
        }
        return publishUtil;
    }

    public void setContext(Context context, PublishListener publishListener) {
        this.mContext = context;
        this.listener = publishListener;
        sysnPushState();
    }

    private void sysnPushState() {
        PublishListener publishListener;
        int i = this.publishType;
        if (i == 0) {
            PublishListener publishListener2 = this.listener;
            if (publishListener2 == null) {
                return;
            }
            publishListener2.publishDefault();
        } else if (i == 1) {
            PublishListener publishListener3 = this.listener;
            if (publishListener3 == null) {
                return;
            }
            publishListener3.publishIng(false);
        } else if (i == 2) {
            PublishListener publishListener4 = this.listener;
            if (publishListener4 == null) {
                return;
            }
            publishListener4.publishSuccess();
        } else if (i != 3) {
            if (i != 4 || (publishListener = this.listener) == null) {
                return;
            }
            publishListener.publishFail(this.mContext, this.publishInfo);
        } else {
            PublishListener publishListener5 = this.listener;
            if (publishListener5 == null) {
                return;
            }
            publishListener5.publishFail(this.mContext, this.publishInfo);
        }
    }

    public void startPublishActivity(PublishInfo publishInfo) {
        if (!UserPermissionUtil.getInstance().isPermissionEnable(1)) {
            return;
        }
        if (this.publishType == 1) {
            setPublishType(1);
            PapaPublishStatusActivity.startActivity(this.mContext, this.publishType, publishInfo, null);
            PublishListener publishListener = this.listener;
            if (publishListener == null) {
                return;
            }
            publishListener.publishIng(true);
            return;
        }
        PublishActivity.startActivity(this.mContext, publishInfo);
    }

    public void onPublishIng() {
        setPublishType(1);
        PublishListener publishListener = this.listener;
        if (publishListener != null) {
            publishListener.publishIng(false);
        }
    }

    public void onPublishSuccess() {
        setPublishType(2);
        PapaPublishStatusActivity.startActivity(this.mContext, this.publishType, this.publishInfo, null);
        PublishListener publishListener = this.listener;
        if (publishListener != null) {
            publishListener.publishSuccess();
        }
        UserInfoManager.setPublishCount(true);
        DBUtil.saveLevelBean(1);
        this.publishType = 0;
    }

    public void onPublishFail(PublishInfo publishInfo, ResponseThrowable responseThrowable) {
        this.publishInfo = publishInfo;
        setPublishType(4);
        PapaPublishStatusActivity.startActivity(this.mContext, this.publishType, publishInfo, responseThrowable);
        PublishListener publishListener = this.listener;
        if (publishListener != null) {
            publishListener.publishFail(this.mContext, publishInfo);
        }
        this.publishType = 0;
    }

    public void onPublishCancel() {
        setPublishType(0);
        this.publishInfo = null;
        PublishListener publishListener = this.listener;
        if (publishListener != null) {
            publishListener.publishDefault();
        }
        this.publishType = 0;
    }

    public void setPublishType(int i) {
        this.publishType = i;
        PublishTypeEvent publishTypeEvent = new PublishTypeEvent();
        publishTypeEvent.type = i;
        EventBus.getDefault().post(publishTypeEvent);
    }
}
