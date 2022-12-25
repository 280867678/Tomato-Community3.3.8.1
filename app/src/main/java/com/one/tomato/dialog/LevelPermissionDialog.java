package com.one.tomato.dialog;

import android.content.Context;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.p085ui.task.TaskCenterActivity;

/* loaded from: classes3.dex */
public class LevelPermissionDialog extends CustomAlertDialog {
    public LevelPermissionDialog(final Context context, int i) {
        super(context);
        setTitle(R.string.level_tip_dialog);
        if (i == 1) {
            setMessage(R.string.level_to_publish);
        } else if (i == 2) {
            setMessage(R.string.level_to_download);
        } else if (i == 3) {
            setMessage(R.string.level_to_comment);
        }
        setCancelButtonTextColor(R.color.white);
        setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner30_disable);
        setCancelButton(R.string.common_cancel);
        setConfirmButtonTextColor(R.color.white);
        setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        setConfirmButton(R.string.level_to_get_expire, new View.OnClickListener() { // from class: com.one.tomato.dialog.LevelPermissionDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LevelPermissionDialog.this.dismiss();
                TaskCenterActivity.startEarnActivity(context);
            }
        });
    }
}
