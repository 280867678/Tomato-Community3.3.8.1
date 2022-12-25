package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.p002v4.app.DialogFragment;
import android.support.p005v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.AspectRatioFragment */
/* loaded from: classes2.dex */
public class AspectRatioFragment extends DialogFragment {
    private Listener mListener;

    /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.AspectRatioFragment$Listener */
    /* loaded from: classes2.dex */
    public interface Listener {
        void onAspectRatioSelected(@NonNull AspectRatio aspectRatio);
    }

    public static AspectRatioFragment newInstance(Set<AspectRatio> set, AspectRatio aspectRatio) {
        AspectRatioFragment aspectRatioFragment = new AspectRatioFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("aspect_ratios", (Parcelable[]) set.toArray(new AspectRatio[set.size()]));
        bundle.putParcelable("current_aspect_ratio", aspectRatio);
        aspectRatioFragment.setArguments(bundle);
        return aspectRatioFragment;
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mListener = (Listener) context;
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }

    @Override // android.support.p002v4.app.DialogFragment
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        final AspectRatio[] aspectRatioArr = (AspectRatio[]) arguments.getParcelableArray("aspect_ratios");
        if (aspectRatioArr == null) {
            throw new RuntimeException("No ratios");
        }
        Arrays.sort(aspectRatioArr);
        return new AlertDialog.Builder(getActivity()).setAdapter(new AspectRatioAdapter(aspectRatioArr, (AspectRatio) arguments.getParcelable("current_aspect_ratio")), new DialogInterface.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.AspectRatioFragment.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                AspectRatioFragment.this.mListener.onAspectRatioSelected(aspectRatioArr[i]);
            }
        }).create();
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.AspectRatioFragment$AspectRatioAdapter */
    /* loaded from: classes2.dex */
    private static class AspectRatioAdapter extends BaseAdapter {
        private final AspectRatio mCurrentRatio;
        private final AspectRatio[] mRatios;

        AspectRatioAdapter(AspectRatio[] aspectRatioArr, AspectRatio aspectRatio) {
            this.mRatios = aspectRatioArr;
            this.mCurrentRatio = aspectRatio;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mRatios.length;
        }

        @Override // android.widget.Adapter
        /* renamed from: getItem */
        public AspectRatio mo6186getItem(int i) {
            return this.mRatios[i];
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return mo6186getItem(i).hashCode();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(17367043, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView) view.findViewById(16908308);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            AspectRatio mo6186getItem = mo6186getItem(i);
            StringBuilder sb = new StringBuilder(mo6186getItem.toString());
            if (mo6186getItem.equals(this.mCurrentRatio)) {
                sb.append(" *");
            }
            viewHolder.text.setText(sb);
            return view;
        }

        /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.AspectRatioFragment$AspectRatioAdapter$ViewHolder */
        /* loaded from: classes2.dex */
        private static class ViewHolder {
            TextView text;

            private ViewHolder() {
            }
        }
    }
}
