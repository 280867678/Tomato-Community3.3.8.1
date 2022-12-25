package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.widget.matisse.MimeType;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Album;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Item;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.SelectionSpec;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.model.AlbumMediaCollection;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.model.SelectedItemCollection;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumMediaAdapter;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.MediaGridInset;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.UIUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.MediaSelectionFragment */
/* loaded from: classes4.dex */
public class MediaSelectionFragment extends Fragment implements AlbumMediaCollection.AlbumMediaCallbacks, AlbumMediaAdapter.CheckStateListener, AlbumMediaAdapter.OnMediaClickListener {
    public static final String EXTRA_ALBUM = "extra_album";
    private AlbumMediaAdapter mAdapter;
    private AlbumMediaAdapter.CheckStateListener mCheckStateListener;
    private AlbumMediaAdapter.OnMediaClickListener mOnMediaClickListener;
    private RecyclerView mRecyclerView;
    private SelectionProvider mSelectionProvider;
    private final AlbumMediaCollection mAlbumMediaCollection = new AlbumMediaCollection();
    private Handler mMainHandler = new Handler();
    List<Item> items = new ArrayList();
    Set<Item> itemSet = new HashSet();

    /* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.MediaSelectionFragment$SelectionProvider */
    /* loaded from: classes4.dex */
    public interface SelectionProvider {
        SelectedItemCollection provideSelectedItemCollection();
    }

    public void refreshSelection() {
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
        return layoutInflater.inflate(R$layout.fq_matisse_fragment_media_selection, viewGroup, false);
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
        this.mRecyclerView.addItemDecoration(new MediaGridInset(i, getResources().getDimensionPixelSize(R$dimen.fq_matisse_media_grid_spacing), false));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAlbumMediaCollection.onCreate(getActivity(), this);
        this.mAlbumMediaCollection.load(album, selectionSpec.capture);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mAlbumMediaCollection.onDestroy();
    }

    public void refreshMediaGrid() {
        this.mAdapter.notifyDataSetChanged();
    }

    private static boolean isSelectableType(Context context, Item item) {
        if (context == null) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        for (MimeType mimeType : SelectionSpec.getInstance().mimeTypeSet) {
            if (mimeType.checkType(contentResolver, item.getContentUri())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.model.AlbumMediaCollection.AlbumMediaCallbacks
    public void onAlbumMediaLoad(Cursor cursor) {
        this.mAdapter.swapCursor(cursor);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.model.AlbumMediaCollection.AlbumMediaCallbacks
    public void onAlbumMediaReset() {
        this.mAdapter.swapCursor(null);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumMediaAdapter.CheckStateListener
    public void onUpdate() {
        AlbumMediaAdapter.CheckStateListener checkStateListener = this.mCheckStateListener;
        if (checkStateListener != null) {
            checkStateListener.onUpdate();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter.AlbumMediaAdapter.OnMediaClickListener
    public void onMediaClick(Album album, Item item, int i) {
        AlbumMediaAdapter.OnMediaClickListener onMediaClickListener = this.mOnMediaClickListener;
        if (onMediaClickListener != null) {
            onMediaClickListener.onMediaClick((Album) getArguments().getParcelable("extra_album"), item, i);
        }
    }
}
