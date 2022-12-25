package com.gen.p059mh.webapp_extensions.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$style;
import com.gen.p059mh.webapp_extensions.adapter.SheetAdapter;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.dialog.SelectDialog */
/* loaded from: classes2.dex */
public class SelectDialog extends Dialog {
    private List<String> mList;
    private RecyclerView rvRecyclerContent;
    private SheetAdapter sheetAdapter;
    private SheetCallBack sheetCallBack;

    public void setSheetCallBack(SheetCallBack sheetCallBack) {
        this.sheetCallBack = sheetCallBack;
    }

    public SelectDialog(@NonNull Context context) {
        super(context, R$style.clear_dialog_theme);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.dialog_web_sdk_sheet);
        getWindow().setLayout(-1, -2);
        getWindow().setGravity(80);
        initView();
        setupView();
        setCanceledOnTouchOutside(true);
    }

    private void initView() {
        this.rvRecyclerContent = (RecyclerView) findViewById(R$id.rv_recycler_content);
    }

    private void setupView() {
        this.rvRecyclerContent.setLayoutManager(new LinearLayoutManager(getContext()));
        this.sheetAdapter = new SheetAdapter();
        this.rvRecyclerContent.setAdapter(this.sheetAdapter);
        List<String> list = this.mList;
        if (list != null) {
            this.sheetAdapter.refreshData(list);
        }
        this.sheetAdapter.setSheetCallBack(new SheetCallBack() { // from class: com.gen.mh.webapp_extensions.views.dialog.SelectDialog.1
            @Override // com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack
            public void onCancel() {
            }

            @Override // com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack
            public void onSelect(int i, String str) {
                if (SelectDialog.this.sheetCallBack != null) {
                    SelectDialog.this.sheetCallBack.onSelect(i, str);
                }
                SelectDialog.this.dismiss();
            }
        });
        setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.gen.mh.webapp_extensions.views.dialog.SelectDialog.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                if (SelectDialog.this.sheetCallBack != null) {
                    SelectDialog.this.sheetCallBack.onCancel();
                }
            }
        });
    }

    public void refreshData(List<String> list) {
        if (list != null) {
            this.mList = list;
        }
        SheetAdapter sheetAdapter = this.sheetAdapter;
        if (sheetAdapter != null) {
            sheetAdapter.refreshData(this.mList);
        }
    }
}
