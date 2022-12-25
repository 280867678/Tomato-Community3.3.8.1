package com.one.tomato.utils.post;

import android.content.Context;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.entity.event.PublishTypeEvent;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.p085ui.publish.PublishStatusActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.UserInfoManager;
import com.one.tomato.utils.UserPermissionUtil;
import de.greenrobot.event.EventBus;

/* loaded from: classes3.dex */
public class PublishUtil {
    private static PublishUtil publishUtil;
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

    private PublishUtil() {
    }

    public static PublishUtil getInstance() {
        if (publishUtil == null) {
            publishUtil = new PublishUtil();
        }
        return publishUtil;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public boolean startPublishActivity(PublishInfo publishInfo) {
        if (UserPermissionUtil.getInstance().isPermissionEnable(1) && this.publishType == 1) {
            setPublishType(1);
            PublishStatusActivity.startActivity(this.mContext, this.publishType, publishInfo, null);
            PublishListener publishListener = this.listener;
            if (publishListener != null) {
                publishListener.publishIng(true);
            }
            return true;
        }
        return false;
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
        PublishStatusActivity.startActivity(this.mContext, this.publishType, this.publishInfo, null);
        PublishListener publishListener = this.listener;
        if (publishListener != null) {
            publishListener.publishSuccess();
        }
        UserInfoManager.setPublishCount(true);
        DBUtil.saveLevelBean(1);
    }

    public void onPublishFail(PublishInfo publishInfo, ResponseThrowable responseThrowable) {
        this.publishInfo = publishInfo;
        setPublishType(4);
        PublishStatusActivity.startActivity(this.mContext, this.publishType, publishInfo, responseThrowable);
        PublishListener publishListener = this.listener;
        if (publishListener != null) {
            publishListener.publishFail(this.mContext, publishInfo);
        }
    }

    public void onPublishCancel() {
        setPublishType(0);
        this.publishInfo = null;
        PublishListener publishListener = this.listener;
        if (publishListener != null) {
            publishListener.publishDefault();
        }
    }

    public int getPublishType() {
        return this.publishType;
    }

    public void setPublishType(int i) {
        this.publishType = i;
        PublishTypeEvent publishTypeEvent = new PublishTypeEvent();
        publishTypeEvent.type = i;
        EventBus.getDefault().post(publishTypeEvent);
    }

    public void setPublishListener(PublishListener publishListener) {
        this.listener = publishListener;
    }
}
