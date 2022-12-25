package com.one.tomato.utils;

import android.content.Context;
import android.util.AttributeSet;
import com.dueeeke.videoplayer.controller.GestureVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.dueeeke.videoplayer.player.BaseIjkVideoView;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BaseToTVideoController.kt */
/* loaded from: classes3.dex */
public abstract class BaseToTVideoController extends GestureVideoController {
    static {
        new Companion(null);
    }

    public abstract Map<String, Object> getVideoErrorInfo();

    public abstract boolean isReplay();

    /* compiled from: BaseToTVideoController.kt */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseToTVideoController(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseToTVideoController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseToTVideoController(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayState(int i) {
        String str;
        super.setPlayState(i);
        if (i == -1) {
            MediaPlayerControl mMediaPlayer = this.mMediaPlayer;
            Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer, "mMediaPlayer");
            long currentPosition = mMediaPlayer.getCurrentPosition();
            MediaPlayerControl mMediaPlayer2 = this.mMediaPlayer;
            Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer2, "mMediaPlayer");
            if (currentPosition == mMediaPlayer2.getDuration()) {
                MediaPlayerControl mMediaPlayer3 = this.mMediaPlayer;
                Intrinsics.checkExpressionValueIsNotNull(mMediaPlayer3, "mMediaPlayer");
                if (((int) mMediaPlayer3.getDuration()) != 0) {
                    if (!isReplay()) {
                        return;
                    }
                    this.mMediaPlayer.replay(true);
                    return;
                }
            }
            Map<String, Object> videoErrorInfo = getVideoErrorInfo();
            if (videoErrorInfo == null) {
                return;
            }
            Object obj = videoErrorInfo.get("postion");
            if (obj == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            int intValue = ((Integer) obj).intValue();
            Object obj2 = videoErrorInfo.get(DatabaseFieldConfigLoader.FIELD_NAME_ID);
            if (obj2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            int intValue2 = ((Integer) obj2).intValue();
            Object obj3 = videoErrorInfo.get("type");
            if (obj3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            }
            String str2 = (String) obj3;
            MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
            if (mediaPlayerControl == null || !(mediaPlayerControl instanceof BaseIjkVideoView)) {
                str = "";
            } else if (mediaPlayerControl == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.dueeeke.videoplayer.player.BaseIjkVideoView");
            } else {
                str = ((BaseIjkVideoView) mediaPlayerControl).getUrl();
                Intrinsics.checkExpressionValueIsNotNull(str, "(mMediaPlayer as BaseIjkVideoView).url");
            }
            DataUploadUtil.uploadPlayError(str, intValue2, str2, intValue);
        }
    }
}
