package android.support.p002v4.media.session;

import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

@RequiresApi(23)
/* renamed from: android.support.v4.media.session.MediaControllerCompatApi23 */
/* loaded from: classes2.dex */
class MediaControllerCompatApi23 {

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi23$TransportControls */
    /* loaded from: classes2.dex */
    public static class TransportControls {
        public static void playFromUri(Object obj, Uri uri, Bundle bundle) {
            ((MediaController.TransportControls) obj).playFromUri(uri, bundle);
        }

        private TransportControls() {
        }
    }

    private MediaControllerCompatApi23() {
    }
}
