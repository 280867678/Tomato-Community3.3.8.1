package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gen.p059mh.webapp_extensions.R$dimen;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Album;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumMediaCollection;
import com.gen.p059mh.webapp_extensions.matisse.internal.model.SelectedItemCollection;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumMediaAdapter;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.MediaGridInset;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.UIUtils;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.MediaSelectionFragment */
/* loaded from: classes2.dex */
public class MediaSelectionFragment extends Fragment implements AlbumMediaCollection.AlbumMediaCallbacks, AlbumMediaAdapter.CheckStateListener, AlbumMediaAdapter.OnMediaClickListener {
    public static final String EXTRA_ALBUM = "extra_album";
    private AlbumMediaAdapter mAdapter;
    private final AlbumMediaCollection mAlbumMediaCollection = new AlbumMediaCollection();
    private AlbumMediaAdapter.CheckStateListener mCheckStateListener;
    private AlbumMediaAdapter.OnMediaClickListener mOnMediaClickListener;
    private RecyclerView mRecyclerView;
    private SelectionProvider mSelectionProvider;

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.MediaSelectionFragment$SelectionProvider */
    /* loaded from: classes2.dex */
    public interface SelectionProvider {
        SelectedItemCollection provideSelectedItemCollection();
    }

    public static MediaSelectionFragment newInstance(Album album) {
        MediaSelectionFragment mediaSelectionFragment = new MediaSelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extra_album", album);
        mediaSelectionFragment.setArguments(bundle);
        return mediaSelectionFragment;
    }

    @Override // android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectionProvider) {
            this.mSelectionProvider = (SelectionProvider) context;
            if (context instanceof AlbumMediaAdapter.CheckStateListener) {
                this.mCheckStateListener = (AlbumMediaAdapter.CheckStateListener) context;
            }
            if (!(context instanceof AlbumMediaAdapter.OnMediaClickListener)) {
                return;
            }
            this.mOnMediaClickListener = (AlbumMediaAdapter.OnMediaClickListener) context;
            return;
        }
        throw new IllegalStateException("Context must implement SelectionProvider.");
    }

    @Override // android.support.p002v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(R$layout.fragment_web_sdk_media_selection, viewGroup, false);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recyclerview);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        int i;
        super.onActivityCreated(bundle);
        Album album = (Album) getArguments().getParcelable("extra_album");
        this.mAdapter = new AlbumMediaAdapter(getContext(), this.mSelectionProvider.provideSelectedItemCollection(), this.mRecyclerView);
        this.mAdapter.registerCheckStateListener(this);
        this.mAdapter.registerOnMediaClickListener(this);
        this.mRecyclerView.setHasFixedSize(true);
        SelectionSpec selectionSpec = SelectionSpec.getInstance();
        if (selectionSpec.gridExpectedSize > 0) {
            i = UIUtils.spanCount(getContext(), selectionSpec.gridExpectedSize);
        } else {
            i = selectionSpec.spanCount;
        }
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), i));
        this.mRecyclerView.addItemDecoration(new MediaGridInset(i, getResources().getDimensionPixelSize(R$dimen.media_grid_spacing), false));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAlbumMediaCollection.onCreate(getActivity(), this);
        this.mAlbumMediaCollection.load(album, selectionSpec.capture);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.gen.mh.webapp_extensions.matisse.internal.ui.MediaSelectionFragment.1
            @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i2) {
                super.onScrollStateChanged(recyclerView, i2);
                if (i2 == 0) {
                    if (MediaSelectionFragment.this.mRecyclerView.canScrollVertically(-1)) {
                        return;
                    }
                    MediaSelectionFragment.this.mAdapter.start();
                    return;
                }
                MediaSelectionFragment.this.mAdapter.stop();
            }
        });
    }

    @Override // android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.mAdapter.start();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onPause() {
        this.mAdapter.stop();
        super.onPause();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mAlbumMediaCollection.onDestroy();
    }

    public void refreshMediaGrid() {
        this.mAdapter.notifyDataSetChanged();
    }

    public void refreshSelection() {
        this.mAdapter.refreshSelection();
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumMediaCollection.AlbumMediaCallbacks
    public void onAlbumMediaLoad(Cursor cursor) {
        this.mAdapter.swapCursor(cursor);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.model.AlbumMediaCollection.AlbumMediaCallbacks
    public void onAlbumMediaReset() {
        this.mAdapter.swapCursor(null);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumMediaAdapter.CheckStateListener
    public void onUpdate() {
        AlbumMediaAdapter.CheckStateListener checkStateListener = this.mCheckStateListener;
        if (checkStateListener != null) {
            checkStateListener.onUpdate();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.AlbumMediaAdapter.OnMediaClickListener
    public void onMediaClick(Album album, Item item, int i) {
        AlbumMediaAdapter.OnMediaClickListener onMediaClickListener = this.mOnMediaClickListener;
        if (onMediaClickListener != null) {
            onMediaClickListener.onMediaClick((Album) getArguments().getParcelable("extra_album"), item, i);
        }
    }
}
