package com.one.tomato.mvp.p080ui.game.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.ViewPagerBottomSheetDialog;
import com.one.tomato.widget.ViewPagerBottomSheetBehavior;

/* renamed from: com.one.tomato.mvp.ui.game.view.ViewPagerBottomSheetDialogFragment */
/* loaded from: classes3.dex */
public abstract class ViewPagerBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public FrameLayout bottomSheet;
    protected View mRootView;

    protected void initData() {
    }

    protected void initView() {
    }

    protected abstract int setLayoutId();

    protected void setListener() {
    }

    @Override // android.support.design.widget.BottomSheetDialogFragment, android.support.p005v7.app.AppCompatDialogFragment, android.support.p002v4.app.DialogFragment
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        return new ViewPagerBottomSheetDialog(getContext(), R.style.postGameDialogStyle);
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
    }

    @Override // android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.mRootView = layoutInflater.inflate(setLayoutId(), viewGroup, false);
        return this.mRootView;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initData();
        initView();
        setListener();
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onStart() {
        super.onStart();
        this.bottomSheet = (FrameLayout) ((ViewPagerBottomSheetDialog) getDialog()).getDelegate().findViewById(R.id.design_bottom_sheet);
        ViewPagerBottomSheetBehavior.from(this.bottomSheet);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }
}
