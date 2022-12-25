package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapp_extensions.utils.SoftKeyboardUtils;
import com.gen.p059mh.webapps.unity.Function;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.SoftKeyBoardListener;
import java.util.HashMap;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.unity.KeyboardManager */
/* loaded from: classes2.dex */
public class KeyboardManager extends Unity implements SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
    Function onChange;
    Unity.Method onComplete;
    Unity.Method onFail;
    Unity.Method onSuccess;
    Unity.Method onKeyboardHeightChange = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.KeyboardManager.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            KeyboardManager.this.onChange = (Function) ((List) objArr[0]).get(0);
            methodCallback.run(null);
        }
    };
    Unity.Method hideKeyboard = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.KeyboardManager.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            SoftKeyboardUtils.hideSoftInput(KeyboardManager.this.getWebViewFragment().getActivity());
            KeyboardManager.this.onSuccess = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.KeyboardManager.2.1
                @Override // com.gen.p059mh.webapps.unity.Unity.Method
                public void call(Unity.MethodCallback methodCallback2, Object... objArr2) {
                    Logger.m4113i("onSuccess");
                    methodCallback2.run(null);
                }
            };
            KeyboardManager.this.onComplete = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.KeyboardManager.2.2
                @Override // com.gen.p059mh.webapps.unity.Unity.Method
                public void call(Unity.MethodCallback methodCallback2, Object... objArr2) {
                    Logger.m4113i("onComplete");
                    methodCallback2.run(null);
                }
            };
            KeyboardManager.this.onFail = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.KeyboardManager.2.3
                @Override // com.gen.p059mh.webapps.unity.Unity.Method
                public void call(Unity.MethodCallback methodCallback2, Object... objArr2) {
                    Logger.m4113i("onFail");
                    methodCallback2.run(null);
                }
            };
            HashMap hashMap = new HashMap();
            hashMap.put("success", KeyboardManager.this.onSuccess);
            hashMap.put("complete", KeyboardManager.this.onComplete);
            hashMap.put("fail", KeyboardManager.this.onFail);
            methodCallback.run(hashMap);
        }
    };

    public KeyboardManager() {
        registerMethod("onKeyboardHeightChange", this.onKeyboardHeightChange);
        registerMethod("hideKeyboard", this.hideKeyboard);
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        getWebViewFragment().getSoftKeyboardController().addListener(this);
    }

    @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
    public void keyBoardShow(int i) {
        if (this.onChange != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("height", Integer.valueOf(i));
            this.onChange.invoke(null, hashMap);
        }
    }

    @Override // com.gen.p059mh.webapps.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
    public void keyBoardHide(int i) {
        if (this.onChange != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("height", 0);
            this.onChange.invoke(null, hashMap);
        }
    }
}
