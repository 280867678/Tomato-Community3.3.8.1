package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.PhotoMetadataUtils;
import com.gen.p059mh.webapp_extensions.matisse.listener.OnFragmentInteractionListener;
import com.iceteck.silicompressorr.FileUtils;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.PreviewItemFragment */
/* loaded from: classes2.dex */
public class PreviewItemFragment extends Fragment {
    private static final String ARGS_ITEM = "args_item";
    private OnFragmentInteractionListener mListener;

    public static PreviewItemFragment newInstance(Item item) {
        PreviewItemFragment previewItemFragment = new PreviewItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ITEM, item);
        previewItemFragment.setArguments(bundle);
        return previewItemFragment;
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R$layout.fragment_web_sdk_preview_item, viewGroup, false);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        final Item item = (Item) getArguments().getParcelable(ARGS_ITEM);
        if (item == null) {
            return;
        }
        View findViewById = view.findViewById(R$id.video_play_button);
        if (item.isVideo()) {
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.matisse.internal.ui.PreviewItemFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(item.uri, FileUtils.MIME_TYPE_VIDEO);
                    try {
                        PreviewItemFragment.this.startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(PreviewItemFragment.this.getContext(), R$string.error_no_video_activity, 0).show();
                    }
                }
            });
        } else {
            findViewById.setVisibility(8);
        }
        ImageViewTouch imageViewTouch = (ImageViewTouch) view.findViewById(R$id.image_view);
        imageViewTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        imageViewTouch.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() { // from class: com.gen.mh.webapp_extensions.matisse.internal.ui.PreviewItemFragment.2
            @Override // it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchSingleTapListener
            public void onSingleTapConfirmed() {
                if (PreviewItemFragment.this.mListener != null) {
                    PreviewItemFragment.this.mListener.onClick();
                }
            }
        });
        Point bitmapSize = PhotoMetadataUtils.getBitmapSize(item.getContentUri(), getActivity());
        if (item.isGif()) {
            SelectionSpec.getInstance().imageEngine.loadGifImage(getContext(), bitmapSize.x, bitmapSize.y, imageViewTouch, item.getContentUri());
        } else {
            SelectionSpec.getInstance().imageEngine.loadImage(getContext(), bitmapSize.x, bitmapSize.y, imageViewTouch, item.getContentUri());
        }
    }

    public void resetView() {
        if (getView() != null) {
            ((ImageViewTouch) getView().findViewById(R$id.image_view)).resetMatrix();
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
