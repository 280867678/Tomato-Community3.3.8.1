package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapp_extensions.views.wheelView.Builder;
import com.gen.p059mh.webapp_extensions.views.wheelView.WheelPicker;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.PickerView */
/* loaded from: classes2.dex */
public class PickerView extends NativeViewPlugin.NativeView {
    LinearLayout linearLayout;

    public PickerView(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        Logger.m4112i("onInitialize", obj.toString());
        List<List> list = obj != null ? (List) ((Map) obj).get("columns") : null;
        this.linearLayout = new LinearLayout(getContext());
        this.linearLayout.setGravity(17);
        this.linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        addView(this.linearLayout);
        Builder createBuilder = Builder.createBuilder();
        createBuilder.setHasAtmospheric(true);
        createBuilder.setCurved(true);
        createBuilder.setCyclic(true);
        createBuilder.setmItemTextColor(Utils.colorFromCSS("#b9b9b9"));
        createBuilder.setmSelectedItemTextColor(Utils.colorFromCSS("#000000"));
        createBuilder.setmItemTextSizeDp(20);
        if (list != null) {
            for (List list2 : list) {
                WheelPicker wheelPicker = new WheelPicker(getContext());
                wheelPicker.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1.0f));
                wheelPicker.initBuild(createBuilder);
                wheelPicker.setData(list2);
                this.linearLayout.addView(wheelPicker);
            }
        }
    }
}
