package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapp_extensions.unity.ModalView;
import com.gen.p059mh.webapp_extensions.views.dialog.ModalDialog;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.unity.ModalView */
/* loaded from: classes2.dex */
public class ModalView extends Unity {
    ModalDialog dialog;
    private Object params;
    Unity.Method destroy = new C15591();
    Unity.Method postMessage = new C15602();

    public ModalView() {
        registerMethod("postMessage", this.postMessage);
        registerMethod("destroy", this.destroy);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.ModalView$1 */
    /* loaded from: classes2.dex */
    public class C15591 implements Unity.Method {
        C15591() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            ModalView.this.getWebViewFragment().getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$ModalView$1$ftJfKoQ3f98KJR59Ovo2mM-D6_A
                @Override // java.lang.Runnable
                public final void run() {
                    ModalView.C15591.this.lambda$call$0$ModalView$1();
                }
            });
            methodCallback.run(null);
        }

        public /* synthetic */ void lambda$call$0$ModalView$1() {
            ModalDialog modalDialog = ModalView.this.dialog;
            if (modalDialog != null) {
                modalDialog.dismiss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.ModalView$2 */
    /* loaded from: classes2.dex */
    public class C15602 implements Unity.Method {
        C15602() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4115e(objArr[0].toString());
            ModalView.this.params = ((List) objArr[0]).get(0);
            Logger.m4115e("xxx postMessage =" + new Gson().toJson(ModalView.this.params));
            ModalView modalView = ModalView.this;
            if (modalView.dialog != null) {
                modalView.getWebViewFragment().getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$ModalView$2$d9P3_KThSjvzSUnEQA5rEAiuJDc
                    @Override // java.lang.Runnable
                    public final void run() {
                        ModalView.C15602.this.lambda$call$0$ModalView$2();
                    }
                });
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }

        public /* synthetic */ void lambda$call$0$ModalView$2() {
            ModalView modalView = ModalView.this;
            modalView.dialog.postData(modalView.params);
        }
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        final String str = (String) ((List) obj).get(0);
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.ModalView.3
            @Override // java.lang.Runnable
            public void run() {
                ModalView modalView = ModalView.this;
                if (modalView.dialog == null) {
                    modalView.dialog = new ModalDialog(modalView.getWebViewFragment().getContext(), new ModalDialog.ModalCallBack() { // from class: com.gen.mh.webapp_extensions.unity.ModalView.3.1
                        @Override // com.gen.p059mh.webapp_extensions.views.dialog.ModalDialog.ModalCallBack
                        public void onEvent(String str2, Object obj2) {
                            ModalView.this.event(str2, null, obj2);
                        }
                    });
                }
                ModalView.this.dialog.show();
                ModalView.this.dialog.loadUrl("http://" + ResourcesLoader.WORK_HOST + str);
                if (ModalView.this.params != null) {
                    ModalView modalView2 = ModalView.this;
                    modalView2.dialog.postData(modalView2.params);
                }
            }
        });
    }
}
