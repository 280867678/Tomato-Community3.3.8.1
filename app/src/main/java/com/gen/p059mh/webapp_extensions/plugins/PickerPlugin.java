package com.gen.p059mh.webapp_extensions.plugins;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.views.dialog.SelectDialog;
import com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack;
import com.gen.p059mh.webapps.Plugin;
import com.google.gson.Gson;
import com.one.tomato.entity.C2516Ad;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.PickerPlugin */
/* loaded from: classes2.dex */
public class PickerPlugin extends Plugin {
    SelectDialog sheetDialog;

    public PickerPlugin() {
        super("picker");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        if (map != null) {
            String valueOf = String.valueOf(map.get("mode"));
            char c = 65535;
            int hashCode = valueOf.hashCode();
            if (hashCode != 3076014) {
                if (hashCode != 3560141) {
                    if (hashCode == 1191572447 && valueOf.equals("selector")) {
                        c = 0;
                    }
                } else if (valueOf.equals(AopConstants.TIME_KEY)) {
                    c = 1;
                }
            } else if (valueOf.equals("date")) {
                c = 2;
            }
            if (c == 0) {
                initSelector(map.get("items"), pluginCallback);
            } else if (c == 1) {
                initTime(map.get(C2516Ad.TYPE_START), map.get("end"), pluginCallback);
            } else if (c != 2) {
            } else {
                initDate(map.get(C2516Ad.TYPE_START), map.get("end"), pluginCallback);
            }
        }
    }

    private void initDate(Object obj, final Object obj2, final Plugin.PluginCallback pluginCallback) {
        final Date date;
        if (obj == null || obj.equals("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            pluginCallback.response(hashMap);
            return;
        }
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date();
        try {
            date = simpleDateFormat.parse(obj.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            date = date2;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getWebViewFragment().getContext(), 0, new DatePickerDialog.OnDateSetListener() { // from class: com.gen.mh.webapp_extensions.plugins.PickerPlugin.1
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                Date parse;
                String str = i + "-" + (i2 + 1) + "-" + i3;
                HashMap hashMap2 = new HashMap();
                HashMap hashMap3 = new HashMap();
                try {
                    parse = simpleDateFormat.parse(str);
                    hashMap2.put("success", true);
                } catch (ParseException e2) {
                    e2.printStackTrace();
                    hashMap2.put("success", false);
                    hashMap3.put("type", "cancel");
                }
                if (date.getTime() > parse.getTime() || parse.getTime() > simpleDateFormat.parse(obj2.toString()).getTime()) {
                    Toast.makeText(PickerPlugin.this.getWebViewFragment().getContext(), "请选择正确的日期", 0).show();
                    hashMap3.put("type", "cancel");
                    return;
                }
                hashMap3.put("type", "change");
                hashMap3.put("value", simpleDateFormat.format(parse));
                hashMap2.put(AopConstants.APP_PROPERTIES_KEY, hashMap3);
                pluginCallback.response(hashMap2);
            }
        }, calendar.get(1), calendar.get(2), calendar.get(5));
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener(this) { // from class: com.gen.mh.webapp_extensions.plugins.PickerPlugin.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("type", "cancel");
                HashMap hashMap3 = new HashMap();
                hashMap3.put("success", true);
                hashMap3.put(AopConstants.APP_PROPERTIES_KEY, hashMap2);
                pluginCallback.response(hashMap3);
            }
        });
        datePickerDialog.show();
    }

    private void initTime(Object obj, final Object obj2, final Plugin.PluginCallback pluginCallback) {
        Date date;
        if (obj == null || obj.equals("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            pluginCallback.response(hashMap);
            return;
        }
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date2 = new Date();
        try {
            date = simpleDateFormat.parse(obj.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            date = date2;
        }
        final Date date3 = date;
        TimePickerDialog timePickerDialog = new TimePickerDialog(getWebViewFragment().getContext(), 0, new TimePickerDialog.OnTimeSetListener() { // from class: com.gen.mh.webapp_extensions.plugins.PickerPlugin.3
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                Date parse;
                String str = i + ":" + i2;
                HashMap hashMap2 = new HashMap();
                HashMap hashMap3 = new HashMap();
                try {
                    parse = simpleDateFormat.parse(str);
                    hashMap3.put("success", true);
                } catch (ParseException e2) {
                    e2.printStackTrace();
                    hashMap3.put("success", false);
                    hashMap2.put("type", "cancel");
                }
                if (date3.getTime() > parse.getTime() || parse.getTime() > simpleDateFormat.parse(obj2.toString()).getTime()) {
                    Toast.makeText(PickerPlugin.this.getWebViewFragment().getContext(), "请选择正确的时间", 0).show();
                    hashMap2.put("type", "cancel");
                    return;
                }
                hashMap2.put("type", "change");
                hashMap2.put("value", simpleDateFormat.format(parse));
                hashMap3.put(AopConstants.APP_PROPERTIES_KEY, hashMap2);
                pluginCallback.response(hashMap3);
            }
        }, date.getHours(), date.getMinutes(), true);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener(this) { // from class: com.gen.mh.webapp_extensions.plugins.PickerPlugin.4
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("type", "cancel");
                HashMap hashMap3 = new HashMap();
                hashMap3.put("success", true);
                hashMap3.put(AopConstants.APP_PROPERTIES_KEY, hashMap2);
                pluginCallback.response(hashMap3);
            }
        });
        timePickerDialog.show();
    }

    private void initSelector(Object obj, final Plugin.PluginCallback pluginCallback) {
        if (obj != null) {
            List<String> list = (List) obj;
            if (this.sheetDialog == null) {
                this.sheetDialog = new SelectDialog(getWebViewFragment().getContext());
                this.sheetDialog.setSheetCallBack(new SheetCallBack(this) { // from class: com.gen.mh.webapp_extensions.plugins.PickerPlugin.5
                    @Override // com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack
                    public void onSelect(int i, String str) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("type", "change");
                        hashMap.put("value", Integer.valueOf(i));
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put("success", true);
                        hashMap2.put(AopConstants.APP_PROPERTIES_KEY, hashMap);
                        pluginCallback.response(hashMap2);
                    }

                    @Override // com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack
                    public void onCancel() {
                        HashMap hashMap = new HashMap();
                        hashMap.put("type", "cancel");
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put("success", true);
                        hashMap2.put(AopConstants.APP_PROPERTIES_KEY, hashMap);
                        pluginCallback.response(hashMap2);
                    }
                });
            }
            this.sheetDialog.show();
            this.sheetDialog.refreshData(list);
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", false);
        pluginCallback.response(hashMap);
    }
}
