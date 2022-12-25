package com.gen.p059mh.webapp_extensions.matisse.internal.model;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.IncapableCause;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.PathUtils;
import com.gen.p059mh.webapp_extensions.matisse.internal.utils.PhotoMetadataUtils;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.model.SelectedItemCollection */
/* loaded from: classes2.dex */
public class SelectedItemCollection {
    public static final int COLLECTION_IMAGE = 1;
    public static final int COLLECTION_MIXED = 3;
    public static final int COLLECTION_UNDEFINED = 0;
    public static final int COLLECTION_VIDEO = 2;
    public static final String STATE_COLLECTION_TYPE = "state_collection_type";
    public static final String STATE_SELECTION = "state_selection";
    private int mCollectionType = 0;
    private final Context mContext;
    private Set<Item> mItems;

    public SelectedItemCollection(Context context) {
        this.mContext = context;
    }

    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.mItems = new LinkedHashSet();
            return;
        }
        this.mItems = new LinkedHashSet(bundle.getParcelableArrayList("state_selection"));
        this.mCollectionType = bundle.getInt("state_collection_type", 0);
    }

    public void setDefaultSelection(List<Item> list) {
        this.mItems.addAll(list);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList("state_selection", new ArrayList<>(this.mItems));
        bundle.putInt("state_collection_type", this.mCollectionType);
    }

    public Bundle getDataWithBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("state_selection", new ArrayList<>(this.mItems));
        bundle.putInt("state_collection_type", this.mCollectionType);
        return bundle;
    }

    public boolean add(Item item) {
        if (typeConflict(item)) {
            throw new IllegalArgumentException("Can't select images and videos at the same time.");
        }
        boolean add = this.mItems.add(item);
        if (add) {
            int i = this.mCollectionType;
            if (i == 0) {
                if (item.isImage()) {
                    this.mCollectionType = 1;
                } else if (item.isVideo()) {
                    this.mCollectionType = 2;
                }
            } else if (i == 1) {
                if (item.isVideo()) {
                    this.mCollectionType = 3;
                }
            } else if (i == 2 && item.isImage()) {
                this.mCollectionType = 3;
            }
        }
        return add;
    }

    public boolean remove(Item item) {
        boolean remove = this.mItems.remove(item);
        if (remove) {
            if (this.mItems.size() == 0) {
                this.mCollectionType = 0;
            } else if (this.mCollectionType == 3) {
                refineCollectionType();
            }
        }
        return remove;
    }

    public void overwrite(ArrayList<Item> arrayList, int i) {
        if (arrayList.size() == 0) {
            this.mCollectionType = 0;
        } else {
            this.mCollectionType = i;
        }
        this.mItems.clear();
        this.mItems.addAll(arrayList);
    }

    public List<Item> asList() {
        return new ArrayList(this.mItems);
    }

    public List<Uri> asListOfUri() {
        ArrayList arrayList = new ArrayList();
        for (Item item : this.mItems) {
            arrayList.add(item.getContentUri());
        }
        return arrayList;
    }

    public List<String> asListOfString() {
        ArrayList arrayList = new ArrayList();
        for (Item item : this.mItems) {
            arrayList.add(PathUtils.getPath(this.mContext, item.getContentUri()));
        }
        return arrayList;
    }

    public boolean isEmpty() {
        Set<Item> set = this.mItems;
        return set == null || set.isEmpty();
    }

    public boolean isSelected(Item item) {
        return this.mItems.contains(item);
    }

    public IncapableCause isAcceptable(Item item) {
        String string;
        if (maxSelectableReached()) {
            int currentMaxSelectable = currentMaxSelectable();
            try {
                string = this.mContext.getString(R$string.error_over_count, Integer.valueOf(currentMaxSelectable));
            } catch (Resources.NotFoundException unused) {
                string = this.mContext.getString(R$string.error_over_count, Integer.valueOf(currentMaxSelectable));
            } catch (NoClassDefFoundError unused2) {
                string = this.mContext.getString(R$string.error_over_count, Integer.valueOf(currentMaxSelectable));
            }
            return new IncapableCause(string);
        } else if (typeConflict(item)) {
            return new IncapableCause(this.mContext.getString(R$string.error_type_conflict));
        } else {
            return PhotoMetadataUtils.isAcceptable(this.mContext, item);
        }
    }

    public boolean maxSelectableReached() {
        return this.mItems.size() == currentMaxSelectable();
    }

    private int currentMaxSelectable() {
        SelectionSpec selectionSpec = SelectionSpec.getInstance();
        int i = selectionSpec.maxSelectable;
        if (i > 0) {
            return i;
        }
        int i2 = this.mCollectionType;
        if (i2 == 1) {
            return selectionSpec.maxImageSelectable;
        }
        return i2 == 2 ? selectionSpec.maxVideoSelectable : i;
    }

    public int getCollectionType() {
        return this.mCollectionType;
    }

    private void refineCollectionType() {
        boolean z = false;
        boolean z2 = false;
        for (Item item : this.mItems) {
            if (item.isImage() && !z) {
                z = true;
            }
            if (item.isVideo() && !z2) {
                z2 = true;
            }
        }
        if (z && z2) {
            this.mCollectionType = 3;
        } else if (z) {
            this.mCollectionType = 1;
        } else if (z2) {
            this.mCollectionType = 2;
        }
    }

    public boolean typeConflict(Item item) {
        int i;
        int i2;
        if (SelectionSpec.getInstance().mediaTypeExclusive) {
            if (item.isImage() && ((i2 = this.mCollectionType) == 2 || i2 == 3)) {
                return true;
            }
            if (item.isVideo() && ((i = this.mCollectionType) == 1 || i == 3)) {
                return true;
            }
        }
        return false;
    }

    public int count() {
        return this.mItems.size();
    }

    public int checkedNumOf(Item item) {
        int indexOf = new ArrayList(this.mItems).indexOf(item);
        if (indexOf == -1) {
            return Integer.MIN_VALUE;
        }
        return indexOf + 1;
    }
}
