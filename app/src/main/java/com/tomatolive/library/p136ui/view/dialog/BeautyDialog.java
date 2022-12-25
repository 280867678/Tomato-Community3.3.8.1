package com.tomatolive.library.p136ui.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.BeautyDialog */
/* loaded from: classes3.dex */
public class BeautyDialog extends BaseBottomDialogFragment implements SeekBar.OnSeekBarChangeListener {
    public static final String BEAUTY_KEY = "beauty_key";
    public static final int BEAUTY_PARAM = 3;
    public static final int RUBBY_PARAM = 2;
    public static final String RUDDY_KEY = "ruddy_key";
    public static final int WHITEN_PARAM = 1;
    public static final String WHITE_KEY = "white_key";
    private SeekBar beautySeekBar;
    private BeautyParams mBeautyParams;
    private OnBeautyParamsChangeListener mBeautyParamsChangeListener;
    private SeekBar ruddySeekBar;
    private SeekBar whiteBar;

    /* renamed from: com.tomatolive.library.ui.view.dialog.BeautyDialog$OnBeautyParamsChangeListener */
    /* loaded from: classes3.dex */
    public interface OnBeautyParamsChangeListener {
        void onBeautyParamsChange(BeautyParams beautyParams, int i);

        void onDismiss();
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public static BeautyDialog newInstance(int i, int i2, int i3) {
        Bundle bundle = new Bundle();
        bundle.putInt(RUDDY_KEY, i);
        bundle.putInt(BEAUTY_KEY, i2);
        bundle.putInt(WHITE_KEY, i3);
        BeautyDialog beautyDialog = new BeautyDialog();
        beautyDialog.setArguments(bundle);
        return beautyDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_layout_beauty_dialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.ruddySeekBar = (SeekBar) view.findViewById(R$id.ruddy_seekbar);
        this.beautySeekBar = (SeekBar) view.findViewById(R$id.beauty_seekbar);
        this.whiteBar = (SeekBar) view.findViewById(R$id.whiten_seekbar);
        this.ruddySeekBar.setOnSeekBarChangeListener(this);
        this.beautySeekBar.setOnSeekBarChangeListener(this);
        this.whiteBar.setOnSeekBarChangeListener(this);
        this.mBeautyParams = new BeautyParams();
        this.ruddySeekBar.setProgress(getArguments().getInt(RUDDY_KEY) == -1 ? this.mBeautyParams.ruddy : getArguments().getInt(RUDDY_KEY));
        this.beautySeekBar.setProgress(getArguments().getInt(BEAUTY_KEY) == -1 ? this.mBeautyParams.beauty : getArguments().getInt(BEAUTY_KEY));
        this.whiteBar.setProgress(getArguments().getInt(WHITE_KEY) == -1 ? this.mBeautyParams.whiten : getArguments().getInt(WHITE_KEY));
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        OnBeautyParamsChangeListener onBeautyParamsChangeListener;
        int id = seekBar.getId();
        if (id == R$id.ruddy_seekbar) {
            OnBeautyParamsChangeListener onBeautyParamsChangeListener2 = this.mBeautyParamsChangeListener;
            if (onBeautyParamsChangeListener2 == null) {
                return;
            }
            BeautyParams beautyParams = this.mBeautyParams;
            beautyParams.ruddy = i;
            onBeautyParamsChangeListener2.onBeautyParamsChange(beautyParams, 2);
        } else if (id == R$id.beauty_seekbar) {
            OnBeautyParamsChangeListener onBeautyParamsChangeListener3 = this.mBeautyParamsChangeListener;
            if (onBeautyParamsChangeListener3 == null) {
                return;
            }
            BeautyParams beautyParams2 = this.mBeautyParams;
            beautyParams2.beauty = i;
            onBeautyParamsChangeListener3.onBeautyParamsChange(beautyParams2, 3);
        } else if (id != R$id.whiten_seekbar || (onBeautyParamsChangeListener = this.mBeautyParamsChangeListener) == null) {
        } else {
            BeautyParams beautyParams3 = this.mBeautyParams;
            beautyParams3.whiten = i;
            onBeautyParamsChangeListener.onBeautyParamsChange(beautyParams3, 1);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, android.support.p002v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        OnBeautyParamsChangeListener onBeautyParamsChangeListener = this.mBeautyParamsChangeListener;
        if (onBeautyParamsChangeListener != null) {
            onBeautyParamsChangeListener.onDismiss();
        }
    }

    public void setBeautyParamsListener(OnBeautyParamsChangeListener onBeautyParamsChangeListener) {
        this.mBeautyParamsChangeListener = onBeautyParamsChangeListener;
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.BeautyDialog$BeautyParams */
    /* loaded from: classes3.dex */
    public class BeautyParams {
        public int whiten = 7;
        public int ruddy = 5;
        public int beauty = 3;

        public BeautyParams() {
        }
    }
}
