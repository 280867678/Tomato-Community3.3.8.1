package android.databinding;

import android.databinding.CallbackRegistry;
import android.databinding.ObservableMap;

/* loaded from: classes2.dex */
public class MapChangeRegistry extends CallbackRegistry<ObservableMap.OnMapChangedCallback, ObservableMap, Object> {
    static {
        new CallbackRegistry.NotifierCallback<ObservableMap.OnMapChangedCallback, ObservableMap, Object>() { // from class: android.databinding.MapChangeRegistry.1
            @Override // android.databinding.CallbackRegistry.NotifierCallback
            public void onNotifyCallback(ObservableMap.OnMapChangedCallback onMapChangedCallback, ObservableMap observableMap, int i, Object obj) {
                onMapChangedCallback.onMapChanged(observableMap, obj);
            }
        };
    }
}
