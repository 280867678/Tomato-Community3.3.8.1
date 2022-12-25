package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui;

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
import com.iceteck.silicompressorr.FileUtils;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Item;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.SelectionSpec;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.PhotoMetadataUtils;
import com.tomatolive.library.p136ui.view.widget.matisse.listener.OnFragmentInteractionListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.PreviewItemFragment */
/* loaded from: classes4.dex */
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
        return layoutInflater.inflate(R$layout.fq_matisse_fragment_preview_item, viewGroup, false);
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
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.matisse.internal.ui.PreviewItemFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(item.uri, FileUtils.MIME_TYPE_VIDEO);
                    try {
                        PreviewItemFragment.this.startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        Toast.makeText(PreviewItemFragment.this.getContext(), R$string.fq_matisse_error_no_video_activity, 0).show();
                    }
                }
            });
        } else {
            findViewById.setVisibility(8);
        }
        ImageViewTouch imageViewTouch = (ImageViewTouch) view.findViewById(R$id.image_view);
        imageViewTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        imageViewTouch.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() { // from class: com.tomatolive.library.ui.view.widget.matisse.internal.ui.PreviewItemFragment.2
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
