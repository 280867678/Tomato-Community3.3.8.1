package com.shizhefei.view.largeimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Build;
import android.support.p002v4.util.Pools;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import com.shizhefei.view.largeimage.TaskQueue;
import com.shizhefei.view.largeimage.factory.BitmapDecoderFactory;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class BlockImageLoader {
    private static int BASE_BLOCKSIZE;
    public static boolean DEBUG;
    private static Pools.SynchronizedPool<Bitmap> bitmapPool = new Pools.SynchronizedPool<>(6);
    private Context context;
    private LoadData mLoadData;
    private OnImageLoadListener onImageLoadListener;
    private OnLoadStateChangeListener onLoadStateChangeListener;
    private Pools.SimplePool<BlockData> blockDataPool = new Pools.SimplePool<>(64);
    private Pools.SimplePool<DrawData> drawDataPool = new Pools.SimplePool<>(64);
    private SparseIntArray sparseIntArray = new SparseIntArray();
    private TaskQueue taskQueue = new TaskQueue();

    /* loaded from: classes3.dex */
    public interface OnImageLoadListener {
        void onBlockImageLoadFinished();

        void onLoadFail(Exception exc);

        void onLoadImageSize(int i, int i2);
    }

    /* loaded from: classes3.dex */
    public interface OnLoadStateChangeListener {
        void onLoadFinished(int i, Object obj, boolean z, Throwable th);

        void onLoadStart(int i, Object obj);
    }

    private int getNearScale(int i) {
        int i2 = 1;
        while (i2 < i) {
            i2 *= 2;
        }
        return i2;
    }

    private boolean isUnRunning(TaskQueue.Task task) {
        return task == null;
    }

    public BlockImageLoader(Context context) {
        this.context = context;
        if (BASE_BLOCKSIZE <= 0) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i = displayMetrics.heightPixels;
            int i2 = displayMetrics.widthPixels;
            BASE_BLOCKSIZE = ((i + i2) / 4) + ((i + i2) % 4 == 0 ? 2 : 1);
        }
    }

    public boolean hasLoad() {
        LoadData loadData = this.mLoadData;
        return (loadData == null || loadData.mDecoder == null) ? false : true;
    }

    public void setOnImageLoadListener(OnImageLoadListener onImageLoadListener) {
        this.onImageLoadListener = onImageLoadListener;
    }

    public void setOnLoadStateChangeListener(OnLoadStateChangeListener onLoadStateChangeListener) {
        this.onLoadStateChangeListener = onLoadStateChangeListener;
    }

    public void setBitmapDecoderFactory(BitmapDecoderFactory bitmapDecoderFactory) {
        LoadData loadData = this.mLoadData;
        if (loadData != null) {
            release(loadData);
        }
        this.mLoadData = new LoadData(bitmapDecoderFactory);
    }

    private void release(LoadData loadData) {
        if (DEBUG) {
            Log.d("Loader", "release loadData:" + loadData);
        }
        cancelTask(loadData.task);
        loadData.task = null;
        recycleMap(loadData.smallDataMap);
        recycleMap(loadData.currentScaleDataMap);
    }

    public void stopLoad() {
        if (this.mLoadData != null) {
            if (DEBUG) {
                Log.d("Loader", "stopLoad ");
            }
            cancelTask(this.mLoadData.task);
            this.mLoadData.task = null;
            Map<Position, BlockData> map = this.mLoadData.currentScaleDataMap;
            if (map == null) {
                return;
            }
            for (BlockData blockData : map.values()) {
                cancelTask(blockData.task);
                blockData.task = null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:172:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0282 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x027f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0297  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void loadImageBlocks(List<DrawData> list, float f, Rect rect, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        String str;
        int i7;
        int i8;
        int i9;
        BlockImageLoader blockImageLoader;
        int i10;
        String str2;
        int i11;
        int i12;
        LinkedList linkedList;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        LoadData loadData = this.mLoadData;
        int nearScale = getNearScale(f);
        int i19 = loadData.currentScale;
        for (DrawData drawData : list) {
            drawData.bitmap = null;
            this.drawDataPool.release(drawData);
        }
        list.clear();
        if (loadData.mDecoder == null) {
            if (!isUnRunning(loadData.task)) {
                return;
            }
            loadData.task = new LoadImageInfoTask(loadData, this.onImageLoadListener, this.onLoadStateChangeListener);
            exeTask(loadData.task);
            return;
        }
        int i20 = loadData.imageWidth;
        int i21 = loadData.imageHeight;
        BitmapRegionDecoder bitmapRegionDecoder = loadData.mDecoder;
        if (loadData.thumbnailBlockData == null) {
            int ceil = (int) Math.ceil(Math.sqrt(((i20 * i21) * 1.0d) / ((this.context.getResources().getDisplayMetrics().widthPixels / 2) * (this.context.getResources().getDisplayMetrics().heightPixels / 2))));
            int nearScale2 = getNearScale(ceil);
            if (nearScale2 < ceil) {
                nearScale2 *= 2;
            }
            loadData.thumbnailScale = nearScale2;
            loadData.thumbnailBlockData = new BlockData();
        }
        int i22 = loadData.thumbnailScale;
        if (loadData.thumbnailBlockData.bitmap != null) {
            i3 = i19;
            i4 = nearScale;
            DrawData acquire = this.drawDataPool.acquire();
            if (acquire == null) {
                acquire = new DrawData();
            }
            acquire.imageRect.set(rect);
            int dip2px = (int) (dip2px(this.context, 100.0f) * f);
            Rect rect2 = acquire.imageRect;
            rect2.right += dip2px;
            rect2.top -= dip2px;
            rect2.left -= dip2px;
            rect2.bottom += dip2px;
            if (rect2.left < 0) {
                rect2.left = 0;
            }
            Rect rect3 = acquire.imageRect;
            if (rect3.top < 0) {
                rect3.top = 0;
            }
            Rect rect4 = acquire.imageRect;
            if (rect4.right > i20) {
                rect4.right = i20;
            }
            Rect rect5 = acquire.imageRect;
            if (rect5.bottom > i21) {
                rect5.bottom = i21;
            }
            float f2 = i22;
            acquire.srcRect.left = (int) Math.abs((acquire.imageRect.left * 1.0f) / f2);
            acquire.srcRect.right = (int) Math.abs((acquire.imageRect.right * 1.0f) / f2);
            acquire.srcRect.top = (int) Math.abs((acquire.imageRect.top * 1.0f) / f2);
            acquire.srcRect.bottom = (int) Math.abs((acquire.imageRect.bottom * 1.0f) / f2);
            acquire.bitmap = loadData.thumbnailBlockData.bitmap;
            list.add(acquire);
        } else if (isUnRunning(loadData.thumbnailBlockData.task)) {
            i3 = i19;
            i4 = nearScale;
            loadData.thumbnailBlockData.task = new LoadThumbnailTask(loadData, bitmapRegionDecoder, i22, i20, i21, this.onImageLoadListener, this.onLoadStateChangeListener);
            exeTask(loadData.thumbnailBlockData.task);
        } else {
            i3 = i19;
            i4 = nearScale;
        }
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("loadImageBlocks ---------- imageRect:");
            sb.append(rect);
            sb.append(" imageScale:");
            sb.append(f);
            sb.append(" currentScale:");
            i5 = i4;
            sb.append(i5);
            Log.d("Loader", sb.toString());
        } else {
            i5 = i4;
        }
        if (i22 <= i5) {
            return;
        }
        int i23 = BASE_BLOCKSIZE;
        int i24 = i23 * i5;
        int i25 = i23 * i5;
        int i26 = (i21 / i25) + (i21 % i25 == 0 ? 0 : 1);
        int i27 = (i20 / i25) + (i20 % i25 == 0 ? 0 : 1);
        int i28 = rect.top / i25;
        int i29 = rect.bottom;
        int i30 = (i29 / i25) + (i29 % i25 == 0 ? 0 : 1);
        int i31 = i22;
        int i32 = rect.left / i25;
        int i33 = rect.right;
        int i34 = (i33 / i25) + (i33 % i25 == 0 ? 0 : 1);
        if (i28 < 0) {
            i28 = 0;
        }
        if (i32 < 0) {
            i32 = 0;
        }
        if (i30 > i26) {
            i30 = i26;
        }
        int i35 = i20;
        if (i34 > i27) {
            i34 = i27;
        }
        int i36 = i34 - i32;
        int i37 = i30 - i28;
        if (i36 * i37 <= 16) {
            int i38 = i25 - (rect.top % i25);
            int i39 = rect.bottom % i25;
            str = "Loader";
            if (i36 == 1) {
                i14 = 0;
            } else if (i36 == 2) {
                i14 = i25 / 2;
            } else {
                i14 = i36 == 3 ? (i25 / 8) * 7 : Integer.MAX_VALUE;
            }
            if (i38 > i39) {
                if (i38 > i14) {
                    i16 = i28 + 1;
                    i15 = i30;
                    int i40 = i25 - (rect.left % i25);
                    int i41 = rect.right % i25;
                    i6 = i28;
                    if (i37 == 1) {
                        i17 = 0;
                    } else if (i37 == 2) {
                        i17 = i25 / 2;
                    } else {
                        i17 = i37 == 3 ? (i25 / 8) * 7 : Integer.MAX_VALUE;
                    }
                    i18 = (i40 <= i41 ? i41 <= i17 : i40 <= i17) ? i32 : i32 + 1;
                    int i42 = i16 < 0 ? 0 : i16;
                    if (i18 < 0) {
                        i18 = 0;
                    }
                    if (i15 <= i26) {
                        i26 = i15;
                    }
                    if (i34 <= i27) {
                        i27 = i34;
                    }
                    i7 = i18;
                    i8 = i26;
                    i9 = i42;
                }
            } else if (i39 > i14) {
                i15 = i30 + 1;
                i16 = i28;
                int i402 = i25 - (rect.left % i25);
                int i412 = rect.right % i25;
                i6 = i28;
                if (i37 == 1) {
                }
                if (i402 <= i412) {
                }
                if (i16 < 0) {
                }
                if (i18 < 0) {
                }
                if (i15 <= i26) {
                }
                if (i34 <= i27) {
                }
                i7 = i18;
                i8 = i26;
                i9 = i42;
            }
            i16 = i28;
            i15 = i30;
            int i4022 = i25 - (rect.left % i25);
            int i4122 = rect.right % i25;
            i6 = i28;
            if (i37 == 1) {
            }
            if (i4022 <= i4122) {
            }
            if (i16 < 0) {
            }
            if (i18 < 0) {
            }
            if (i15 <= i26) {
            }
            if (i34 <= i27) {
            }
            i7 = i18;
            i8 = i26;
            i9 = i42;
        } else {
            i6 = i28;
            str = "Loader";
            i7 = i32;
            i27 = i34;
            i8 = i30;
            i9 = i6;
        }
        LinkedList linkedList2 = new LinkedList();
        Map<Position, BlockData> map = loadData.currentScaleDataMap;
        if (map == null || (i13 = i3) == i5) {
            blockImageLoader = this;
            i10 = i32;
            str2 = str;
        } else {
            Map<Position, BlockData> map2 = loadData.smallDataMap;
            if (DEBUG) {
                StringBuilder sb2 = new StringBuilder();
                i10 = i32;
                sb2.append("preScale:");
                sb2.append(i13);
                sb2.append(" currentScale:");
                sb2.append(i5);
                sb2.append(" ds:");
                sb2.append((i5 * 1.0f) / i13);
                String sb3 = sb2.toString();
                str2 = str;
                Log.d(str2, sb3);
            } else {
                i10 = i32;
                str2 = str;
            }
            if (i5 == i13 * 2) {
                loadData.currentScaleDataMap = map2;
                blockImageLoader = this;
                blockImageLoader.recycleMap(map);
                loadData.smallDataMap = map;
                if (DEBUG) {
                    Log.d(str2, "相当于图片通过手势缩小了2倍，原先相对模糊的small 已经被定义为 当前的缩放度");
                }
            } else {
                blockImageLoader = this;
                if (i5 == i13 / 2) {
                    loadData.smallDataMap = map;
                    blockImageLoader.recycleMap(map2);
                    loadData.currentScaleDataMap = map2;
                    if (DEBUG) {
                        Log.d(str2, "相当于通过手势放大了2倍，原先相对清晰的large 已经被定义为 当前的缩放度");
                    }
                } else {
                    blockImageLoader.recycleMap(map2);
                    blockImageLoader.recycleMap(map);
                    if (DEBUG) {
                        Log.d(str2, "相对原先 缩小倍数过多，放大倍数过多，这种情况是直接设置scale，通过手势都会走上面的倍数");
                    }
                }
            }
        }
        loadData.currentScale = i5;
        if (loadData.currentScaleDataMap == null) {
            loadData.currentScaleDataMap = new HashMap();
        }
        Position position = new Position();
        ArrayList arrayList = new ArrayList();
        Map<Position, BlockData> map3 = loadData.currentScaleDataMap;
        loadData.currentScaleDataMap = new HashMap();
        int i43 = i6;
        while (i43 < i30) {
            int i44 = i10;
            while (i44 < i34) {
                position.set(i43, i44);
                int i45 = i7;
                int i46 = i43;
                int i47 = i9;
                Map<Position, BlockData> map4 = map3;
                int i48 = i10;
                int i49 = i30;
                int i50 = i31;
                ArrayList arrayList2 = arrayList;
                int i51 = i8;
                int i52 = i34;
                int i53 = i5;
                int i54 = i35;
                int i55 = i5;
                Position position2 = position;
                int i56 = i27;
                String str3 = str2;
                LinkedList linkedList3 = linkedList2;
                LoadData loadData2 = loadData;
                BlockData addRequestBlock = addRequestBlock(position, map3.get(position), loadData.currentScaleDataMap, i53, i54, i21, bitmapRegionDecoder);
                if (addRequestBlock.bitmap != null) {
                    DrawData acquire2 = this.drawDataPool.acquire();
                    if (acquire2 == null) {
                        acquire2 = new DrawData();
                    }
                    Rect rect6 = acquire2.imageRect;
                    rect6.left = i44 * i24;
                    rect6.top = i46 * i24;
                    rect6.right = rect6.left + (addRequestBlock.realImageRect.width() * i55);
                    rect6.bottom = rect6.top + (addRequestBlock.realImageRect.height() * i55);
                    acquire2.srcRect.set(0, 0, addRequestBlock.realImageRect.width(), addRequestBlock.realImageRect.height());
                    acquire2.bitmap = addRequestBlock.bitmap;
                    arrayList2.add(acquire2);
                    if (DEBUG) {
                        Log.d(str3, "cache add--  添加  normal position :" + position2 + " src:" + acquire2.srcRect + " imageRect:" + acquire2.imageRect + " w:" + acquire2.imageRect.width() + " h:" + acquire2.imageRect.height());
                    }
                    linkedList = linkedList3;
                } else {
                    linkedList = linkedList3;
                    linkedList.add(new Position(i46, i44));
                }
                i44++;
                i34 = i52;
                linkedList2 = linkedList;
                i43 = i46;
                blockImageLoader = this;
                position = position2;
                str2 = str3;
                arrayList = arrayList2;
                i27 = i56;
                i8 = i51;
                i35 = i54;
                i7 = i45;
                map3 = map4;
                i31 = i50;
                i10 = i48;
                loadData = loadData2;
                i30 = i49;
                i5 = i55;
                i9 = i47;
            }
            i43++;
            linkedList2 = linkedList2;
            i27 = i27;
            i8 = i8;
            i7 = i7;
            i31 = i31;
            i10 = i10;
            loadData = loadData;
            i30 = i30;
            i5 = i5;
            i9 = i9;
        }
        int i57 = i10;
        Map<Position, BlockData> map5 = map3;
        int i58 = i34;
        LoadData loadData3 = loadData;
        int i59 = i9;
        int i60 = i7;
        int i61 = i5;
        LinkedList linkedList4 = linkedList2;
        int i62 = i27;
        int i63 = i30;
        int i64 = i31;
        int i65 = i35;
        ArrayList arrayList3 = arrayList;
        Position position3 = position;
        String str4 = str2;
        int i66 = i8;
        int i67 = i59;
        while (true) {
            i11 = i6;
            if (i67 >= i11) {
                break;
            }
            int i68 = i62;
            int i69 = i60;
            while (i69 < i68) {
                position3.set(i67, i69);
                Map<Position, BlockData> map6 = map5;
                LoadData loadData4 = loadData3;
                addRequestBlock(position3, map6.get(position3), loadData4.currentScaleDataMap, i61, i65, i21, bitmapRegionDecoder);
                i69++;
                i68 = i68;
                linkedList4 = linkedList4;
                loadData3 = loadData4;
                map5 = map6;
                arrayList3 = arrayList3;
                i11 = i11;
            }
            i6 = i11;
            i67++;
            arrayList3 = arrayList3;
            i62 = i68;
        }
        LinkedList linkedList5 = linkedList4;
        int i70 = i62;
        LoadData loadData5 = loadData3;
        ArrayList arrayList4 = arrayList3;
        Map<Position, BlockData> map7 = map5;
        int i71 = i66;
        for (int i72 = i63; i72 < i71; i72++) {
            int i73 = i60;
            while (i73 < i70) {
                position3.set(i72, i73);
                addRequestBlock(position3, map7.get(position3), loadData5.currentScaleDataMap, i61, i65, i21, bitmapRegionDecoder);
                i73++;
                i71 = i71;
            }
        }
        int i74 = i71;
        int i75 = i11;
        while (i75 < i63) {
            int i76 = i60;
            while (true) {
                i12 = i57;
                if (i76 < i12) {
                    position3.set(i75, i76);
                    i57 = i12;
                    addRequestBlock(position3, map7.get(position3), loadData5.currentScaleDataMap, i61, i65, i21, bitmapRegionDecoder);
                    i76++;
                    i75 = i75;
                }
            }
            i57 = i12;
            i75++;
        }
        int i77 = i11;
        while (i77 < i63) {
            int i78 = i58;
            while (i78 < i70) {
                position3.set(i77, i78);
                addRequestBlock(position3, map7.get(position3), loadData5.currentScaleDataMap, i61, i65, i21, bitmapRegionDecoder);
                i78++;
                i77 = i77;
            }
            i77++;
        }
        map7.keySet().removeAll(loadData5.currentScaleDataMap.keySet());
        if (DEBUG) {
            Log.d(str4, "preCurrentDataMap: " + map7.toString() + " needShowPositions：" + linkedList5);
        }
        recycleMap(map7);
        list.addAll(loadSmallDatas(loadData5, i61, linkedList5, i11, i63, i57, i58));
        list.addAll(arrayList4);
        if (!DEBUG) {
            return;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("detail current scale:");
        sb4.append(i61);
        sb4.append(" startRow:");
        sb4.append(i11);
        sb4.append(" endRow:");
        sb4.append(i63);
        sb4.append(" startCol:");
        sb4.append(i57);
        sb4.append(" endCol:");
        sb4.append(i58);
        sb4.append(" blockSize:");
        sb4.append(i24);
        sb4.append(" size:");
        sb4.append(loadData5.currentScaleDataMap.size());
        sb4.append(" small size:");
        Map<Position, BlockData> map8 = loadData5.smallDataMap;
        sb4.append(map8 == null ? "null" : Integer.valueOf(map8.size()));
        Log.d(str4, sb4.toString());
        Log.d(str4, "detail thumbnailScale:" + i64 + " cacheStartRow:" + i59 + " cacheEndRow:" + i74 + " cacheStartCol:" + i60 + " cacheEndCol:" + i70 + " draDataList.size:" + list.size());
        StringBuilder sb5 = new StringBuilder();
        sb5.append("detail imageRect:");
        sb5.append(rect);
        Log.d(str4, sb5.toString());
        this.sparseIntArray.put(loadData5.currentScaleDataMap.size(), this.sparseIntArray.get(loadData5.currentScaleDataMap.size(), 0) + 1);
        StringBuilder sb6 = new StringBuilder();
        sb6.append("detail 统计次数 ");
        for (int i79 = 0; i79 < this.sparseIntArray.size(); i79++) {
            sb6.append("size:" + this.sparseIntArray.keyAt(i79) + "->time:" + this.sparseIntArray.valueAt(i79) + ConstantUtils.PLACEHOLDER_STR_TWO);
        }
        Log.d(str4, sb6.toString());
    }

    private List<DrawData> loadSmallDatas(LoadData loadData, int i, List<Position> list, int i2, int i3, int i4, int i5) {
        Iterator<Map.Entry<Position, BlockData>> it2;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        String str;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        String str2;
        String str3;
        int i18;
        int i19;
        int i20;
        LoadData loadData2 = loadData;
        ArrayList arrayList = new ArrayList();
        String str4 = "Loader";
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("之前 loadData.largeDataMap :");
            Map<Position, BlockData> map = loadData2.smallDataMap;
            sb.append(map == null ? "null" : Integer.valueOf(map.size()));
            Log.d(str4, sb.toString());
        }
        Position position = new Position();
        Map<Position, BlockData> map2 = loadData2.smallDataMap;
        if (map2 != null && !map2.isEmpty()) {
            int i21 = i * 2;
            int i22 = i21 / i;
            int i23 = BASE_BLOCKSIZE * i;
            int i24 = i2 / 2;
            int i25 = i3 / 2;
            int i26 = i4 / 2;
            int i27 = i5 / 2;
            Iterator<Map.Entry<Position, BlockData>> it3 = loadData2.smallDataMap.entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry<Position, BlockData> next = it3.next();
                Position key = next.getKey();
                BlockData value = next.getValue();
                if (DEBUG) {
                    StringBuilder sb2 = new StringBuilder();
                    it2 = it3;
                    sb2.append("cache add-- 遍历 largeDataMap position :");
                    sb2.append(key);
                    Log.d(str4, sb2.toString());
                } else {
                    it2 = it3;
                }
                cancelTask(value.task);
                loadData2.task = null;
                if (list.isEmpty()) {
                    it3 = it2;
                } else {
                    if (value.bitmap != null && (i11 = key.row) >= i24 && i11 <= i25 && (i12 = key.col) >= i26 && i12 <= i27) {
                        int i28 = i11 * i22;
                        int i29 = i28 + i22;
                        int i30 = i12 * i22;
                        i7 = i24;
                        int i31 = i30 + i22;
                        i8 = i25;
                        int width = value.realImageRect.width();
                        i9 = i26;
                        int height = value.realImageRect.height();
                        i10 = i27;
                        int ceil = (int) Math.ceil((BASE_BLOCKSIZE * 1.0f) / i22);
                        int i32 = i28;
                        int i33 = 0;
                        while (i32 < i29) {
                            int i34 = i29;
                            int i35 = i33 * ceil;
                            if (i35 >= height) {
                                break;
                            }
                            int i36 = i22;
                            int i37 = i30;
                            int i38 = 0;
                            while (true) {
                                i13 = i31;
                                if (i37 >= i31) {
                                    i14 = width;
                                    i15 = height;
                                    i16 = ceil;
                                    i17 = i30;
                                    str2 = str4;
                                    break;
                                }
                                int i39 = i38 * ceil;
                                if (i39 >= width) {
                                    str2 = str4;
                                    i14 = width;
                                    i15 = height;
                                    i16 = ceil;
                                    i17 = i30;
                                    break;
                                }
                                position.set(i32, i37);
                                int i40 = i30;
                                if (list.remove(position)) {
                                    int i41 = i39 + ceil;
                                    String str5 = str4;
                                    int i42 = i35 + ceil;
                                    if (i41 > width) {
                                        i41 = width;
                                    }
                                    i18 = width;
                                    if (i42 > height) {
                                        i42 = height;
                                    }
                                    DrawData acquire = this.drawDataPool.acquire();
                                    if (acquire == null) {
                                        acquire = new DrawData();
                                    }
                                    i19 = height;
                                    acquire.bitmap = value.bitmap;
                                    Rect rect = acquire.imageRect;
                                    i20 = ceil;
                                    rect.left = i37 * i23;
                                    rect.top = i32 * i23;
                                    rect.right = rect.left + ((i41 - i39) * i21);
                                    rect.bottom = rect.top + ((i42 - i35) * i21);
                                    acquire.srcRect.set(i39, i35, i41, i42);
                                    acquire.bitmap = value.bitmap;
                                    arrayList.add(acquire);
                                    if (DEBUG) {
                                        str3 = str5;
                                        Log.d(str3, "cache add--添加  smallDataMap position :" + key + " 到 当前currentScalePosition:" + position + " src:" + acquire.srcRect + "w:" + acquire.srcRect.width() + " h:" + acquire.srcRect.height() + " imageRect:" + acquire.imageRect + " w:" + acquire.imageRect.width() + " h:" + acquire.imageRect.height());
                                    } else {
                                        str3 = str5;
                                    }
                                } else {
                                    str3 = str4;
                                    i18 = width;
                                    i19 = height;
                                    i20 = ceil;
                                }
                                i37++;
                                i38++;
                                str4 = str3;
                                i31 = i13;
                                i30 = i40;
                                width = i18;
                                height = i19;
                                ceil = i20;
                            }
                            i32++;
                            i33++;
                            str4 = str2;
                            i29 = i34;
                            i22 = i36;
                            i31 = i13;
                            i30 = i17;
                            width = i14;
                            height = i15;
                            ceil = i16;
                        }
                        str = str4;
                        i6 = i22;
                    } else {
                        i6 = i22;
                        i7 = i24;
                        i8 = i25;
                        i9 = i26;
                        i10 = i27;
                        str = str4;
                        it2.remove();
                        recycleBlock(value);
                    }
                    loadData2 = loadData;
                    it3 = it2;
                    i25 = i8;
                    i26 = i9;
                    i27 = i10;
                    str4 = str;
                    i22 = i6;
                    i24 = i7;
                }
            }
        }
        return arrayList;
    }

    private void exeTask(TaskQueue.Task task) {
        this.taskQueue.addTask(task);
    }

    private void cancelTask(TaskQueue.Task task) {
        if (task != null) {
            this.taskQueue.cancelTask(task);
        }
    }

    private void recycleMap(Map<Position, BlockData> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<Position, BlockData> entry : map.entrySet()) {
            recycleBlock(entry.getValue());
        }
        map.clear();
    }

    private void recycleBlock(BlockData blockData) {
        cancelTask(blockData.task);
        blockData.task = null;
        Bitmap bitmap = blockData.bitmap;
        if (bitmap != null) {
            bitmapPool.release(bitmap);
            blockData.bitmap = null;
        }
        this.blockDataPool.release(blockData);
    }

    private BlockData addRequestBlock(Position position, BlockData blockData, Map<Position, BlockData> map, int i, int i2, int i3, BitmapRegionDecoder bitmapRegionDecoder) {
        BlockData blockData2;
        if (blockData == null) {
            blockData2 = this.blockDataPool.acquire();
            if (blockData2 == null) {
                blockData2 = new BlockData(new Position(position.row, position.col));
            } else {
                Position position2 = blockData2.position;
                if (position2 == null) {
                    blockData2.position = new Position(position.row, position.col);
                } else {
                    position2.set(position.row, position.col);
                }
            }
        } else {
            blockData2 = blockData;
        }
        if (blockData2.bitmap == null && isUnRunning(blockData2.task)) {
            blockData2.task = new LoadBlockTask(blockData2.position, blockData2, i, i2, i3, bitmapRegionDecoder, this.onImageLoadListener, this.onLoadStateChangeListener);
            exeTask(blockData2.task);
        }
        map.put(blockData2.position, blockData2);
        return blockData2;
    }

    private int getNearScale(float f) {
        return getNearScale(Math.round(f));
    }

    static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getWidth() {
        LoadData loadData = this.mLoadData;
        if (loadData == null) {
            return 0;
        }
        return loadData.imageWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getHeight() {
        LoadData loadData = this.mLoadData;
        if (loadData == null) {
            return 0;
        }
        return loadData.imageHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class LoadData {
        int currentScale;
        Map<Position, BlockData> currentScaleDataMap;
        private int imageHeight;
        private int imageWidth;
        private BitmapRegionDecoder mDecoder;
        private BitmapDecoderFactory mFactory;
        Map<Position, BlockData> smallDataMap;
        private LoadImageInfoTask task;
        private volatile BlockData thumbnailBlockData;
        private volatile int thumbnailScale;

        LoadData(BitmapDecoderFactory bitmapDecoderFactory) {
            this.mFactory = bitmapDecoderFactory;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class BlockData {
        Bitmap bitmap;
        Position position;
        Rect realImageRect = new Rect();
        TaskQueue.Task task;

        BlockData() {
        }

        BlockData(Position position) {
            this.position = position;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class DrawData {
        Bitmap bitmap;
        Rect srcRect = new Rect();
        Rect imageRect = new Rect();

        DrawData() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class Position {
        int col;
        int row;

        Position() {
        }

        Position(int i, int i2) {
            this.row = i;
            this.col = i2;
        }

        Position set(int i, int i2) {
            this.row = i;
            this.col = i2;
            return this;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Position) {
                Position position = (Position) obj;
                return this.row == position.row && this.col == position.col;
            }
            return false;
        }

        public int hashCode() {
            return ((629 + this.row) * 37) + this.col;
        }

        public String toString() {
            return "row:" + this.row + " col:" + this.col;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class LoadImageInfoTask extends TaskQueue.Task {
        private volatile BitmapRegionDecoder decoder;

        /* renamed from: e */
        private volatile Exception f1884e;
        private volatile int imageHeight;
        private LoadData imageInfo;
        private volatile int imageWidth;
        private BitmapDecoderFactory mFactory;
        private OnImageLoadListener onImageLoadListener;
        private OnLoadStateChangeListener onLoadStateChangeListener;

        LoadImageInfoTask(LoadData loadData, OnImageLoadListener onImageLoadListener, OnLoadStateChangeListener onLoadStateChangeListener) {
            this.imageInfo = loadData;
            this.mFactory = this.imageInfo.mFactory;
            this.onImageLoadListener = onImageLoadListener;
            this.onLoadStateChangeListener = onLoadStateChangeListener;
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "start LoadImageInfoTask:imageW:" + this.imageWidth + " imageH:" + this.imageHeight);
            }
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            OnLoadStateChangeListener onLoadStateChangeListener = this.onLoadStateChangeListener;
            if (onLoadStateChangeListener != null) {
                onLoadStateChangeListener.onLoadStart(0, null);
            }
        }

        @Override // com.shizhefei.view.largeimage.TaskQueue.Task
        protected void doInBackground() {
            try {
                this.decoder = this.mFactory.made();
                this.imageWidth = this.decoder.getWidth();
                this.imageHeight = this.decoder.getHeight();
                if (!BlockImageLoader.DEBUG) {
                    return;
                }
                Log.d("Loader", "LoadImageInfoTask doInBackground");
            } catch (Exception e) {
                e.printStackTrace();
                this.f1884e = e;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.shizhefei.view.largeimage.TaskQueue.Task, android.os.AsyncTask
        public void onCancelled() {
            super.onCancelled();
            this.onLoadStateChangeListener = null;
            this.onImageLoadListener = null;
            this.mFactory = null;
            this.imageInfo = null;
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "LoadImageInfoTask: onCancelled");
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.shizhefei.view.largeimage.TaskQueue.Task
        public void onPostExecute() {
            super.onPostExecute();
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "onPostExecute LoadImageInfoTask:" + this.f1884e + " imageW:" + this.imageWidth + " imageH:" + this.imageHeight + " e:" + this.f1884e);
            }
            this.imageInfo.task = null;
            if (this.f1884e == null) {
                this.imageInfo.imageWidth = this.imageWidth;
                this.imageInfo.imageHeight = this.imageHeight;
                this.imageInfo.mDecoder = this.decoder;
                this.onImageLoadListener.onLoadImageSize(this.imageWidth, this.imageHeight);
            } else {
                this.onImageLoadListener.onLoadFail(this.f1884e);
            }
            OnLoadStateChangeListener onLoadStateChangeListener = this.onLoadStateChangeListener;
            if (onLoadStateChangeListener != null) {
                onLoadStateChangeListener.onLoadFinished(0, null, this.f1884e == null, this.f1884e);
            }
            this.onLoadStateChangeListener = null;
            this.onImageLoadListener = null;
            this.mFactory = null;
            this.imageInfo = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class LoadBlockTask extends TaskQueue.Task {
        private volatile Bitmap bitmap;
        private BlockData blockData;
        private volatile Rect clipImageRect;
        private BitmapRegionDecoder decoder;
        private int imageHeight;
        private int imageWidth;
        private OnImageLoadListener onImageLoadListener;
        private OnLoadStateChangeListener onLoadStateChangeListener;
        private Position position;
        private int scale;
        private volatile Throwable throwable;

        LoadBlockTask(Position position, BlockData blockData, int i, int i2, int i3, BitmapRegionDecoder bitmapRegionDecoder, OnImageLoadListener onImageLoadListener, OnLoadStateChangeListener onLoadStateChangeListener) {
            this.blockData = blockData;
            this.scale = i;
            this.position = position;
            this.imageWidth = i2;
            this.imageHeight = i3;
            this.decoder = bitmapRegionDecoder;
            this.onImageLoadListener = onImageLoadListener;
            this.onLoadStateChangeListener = onLoadStateChangeListener;
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "start LoadBlockTask position:" + position + " currentScale:" + i);
            }
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            OnLoadStateChangeListener onLoadStateChangeListener = this.onLoadStateChangeListener;
            if (onLoadStateChangeListener != null) {
                onLoadStateChangeListener.onLoadStart(2, this.position);
            }
        }

        @Override // com.shizhefei.view.largeimage.TaskQueue.Task
        protected void doInBackground() {
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "doInBackground：" + Thread.currentThread() + ConstantUtils.PLACEHOLDER_STR_ONE + Thread.currentThread().getId());
            }
            int i = BlockImageLoader.BASE_BLOCKSIZE * this.scale;
            Position position = this.position;
            int i2 = position.col * i;
            int i3 = i2 + i;
            int i4 = position.row * i;
            int i5 = i + i4;
            int i6 = this.imageWidth;
            if (i3 > i6) {
                i3 = i6;
            }
            int i7 = this.imageHeight;
            if (i5 > i7) {
                i5 = i7;
            }
            this.clipImageRect = new Rect(i2, i4, i3, i5);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                if (Build.VERSION.SDK_INT >= 11) {
                    options.inBitmap = BlockImageLoader.acquireBitmap();
                    options.inMutable = true;
                }
                options.inSampleSize = this.scale;
                this.bitmap = this.decoder.decodeRegion(this.clipImageRect, options);
            } catch (Exception e) {
                if (BlockImageLoader.DEBUG) {
                    Log.d("Loader", this.position.toString() + ConstantUtils.PLACEHOLDER_STR_ONE + this.clipImageRect.toShortString());
                }
                this.throwable = e;
                e.printStackTrace();
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                this.throwable = e2;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.shizhefei.view.largeimage.TaskQueue.Task, android.os.AsyncTask
        public void onCancelled() {
            super.onCancelled();
            if (this.bitmap != null) {
                BlockImageLoader.bitmapPool.release(this.bitmap);
                this.bitmap = null;
            }
            this.decoder = null;
            this.blockData = null;
            this.onImageLoadListener = null;
            this.onLoadStateChangeListener = null;
            this.position = null;
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "onCancelled LoadBlockTask position:" + this.position + " currentScale:" + this.scale + " bit:");
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.shizhefei.view.largeimage.TaskQueue.Task
        public void onPostExecute() {
            String str;
            super.onPostExecute();
            if (BlockImageLoader.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("finish LoadBlockTask position:");
                sb.append(this.position);
                sb.append(" currentScale:");
                sb.append(this.scale);
                sb.append(" bitmap: ");
                if (this.bitmap == null) {
                    str = "";
                } else {
                    str = this.bitmap.getWidth() + " bitH:" + this.bitmap.getHeight();
                }
                sb.append(str);
                Log.d("Loader", sb.toString());
            }
            this.blockData.task = null;
            boolean z = false;
            if (this.bitmap != null) {
                this.blockData.bitmap = this.bitmap;
                this.blockData.realImageRect.set(0, 0, this.clipImageRect.width() / this.scale, this.clipImageRect.height() / this.scale);
                OnImageLoadListener onImageLoadListener = this.onImageLoadListener;
                if (onImageLoadListener != null) {
                    onImageLoadListener.onBlockImageLoadFinished();
                }
            }
            OnLoadStateChangeListener onLoadStateChangeListener = this.onLoadStateChangeListener;
            if (onLoadStateChangeListener != null) {
                Position position = this.position;
                if (this.throwable == null) {
                    z = true;
                }
                onLoadStateChangeListener.onLoadFinished(2, position, z, this.throwable);
            }
            this.decoder = null;
            this.blockData = null;
            this.onImageLoadListener = null;
            this.onLoadStateChangeListener = null;
            this.position = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap acquireBitmap() {
        Bitmap acquire = bitmapPool.acquire();
        if (acquire == null) {
            int i = BASE_BLOCKSIZE;
            return Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        }
        return acquire;
    }

    /* loaded from: classes3.dex */
    private static class LoadThumbnailTask extends TaskQueue.Task {
        private volatile Bitmap bitmap;
        private BitmapRegionDecoder decoder;
        private int imageHeight;
        private int imageWidth;
        private LoadData loadData;
        private OnImageLoadListener onImageLoadListener;
        private OnLoadStateChangeListener onLoadStateChangeListener;
        private int scale;
        private volatile Throwable throwable;

        LoadThumbnailTask(LoadData loadData, BitmapRegionDecoder bitmapRegionDecoder, int i, int i2, int i3, OnImageLoadListener onImageLoadListener, OnLoadStateChangeListener onLoadStateChangeListener) {
            this.loadData = loadData;
            this.scale = i;
            this.imageWidth = i2;
            this.imageHeight = i3;
            this.decoder = bitmapRegionDecoder;
            this.onImageLoadListener = onImageLoadListener;
            this.onLoadStateChangeListener = onLoadStateChangeListener;
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "LoadThumbnailTask LoadThumbnailTask thumbnailScale:" + i);
            }
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            OnLoadStateChangeListener onLoadStateChangeListener = this.onLoadStateChangeListener;
            if (onLoadStateChangeListener != null) {
                onLoadStateChangeListener.onLoadStart(1, null);
            }
        }

        @Override // com.shizhefei.view.largeimage.TaskQueue.Task
        protected void doInBackground() {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = this.scale;
            try {
                this.bitmap = this.decoder.decodeRegion(new Rect(0, 0, this.imageWidth, this.imageHeight), options);
            } catch (Exception e) {
                e.printStackTrace();
                this.throwable = e;
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                this.throwable = e2;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.shizhefei.view.largeimage.TaskQueue.Task, android.os.AsyncTask
        public void onCancelled() {
            super.onCancelled();
            this.onImageLoadListener = null;
            this.onLoadStateChangeListener = null;
            this.loadData = null;
            this.decoder = null;
            if (BlockImageLoader.DEBUG) {
                Log.d("Loader", "onCancelled LoadThumbnailTask thumbnailScale:" + this.scale);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.shizhefei.view.largeimage.TaskQueue.Task
        public void onPostExecute() {
            String str;
            super.onPostExecute();
            if (BlockImageLoader.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("LoadThumbnailTask bitmap:");
                sb.append(this.bitmap);
                sb.append(" currentScale:");
                sb.append(this.scale);
                sb.append(" bitW:");
                if (this.bitmap == null) {
                    str = "";
                } else {
                    str = this.bitmap.getWidth() + " bitH:" + this.bitmap.getHeight();
                }
                sb.append(str);
                Log.d("Loader", sb.toString());
            }
            this.loadData.thumbnailBlockData.task = null;
            if (this.bitmap != null) {
                if (this.loadData.thumbnailBlockData == null) {
                    this.loadData.thumbnailBlockData = new BlockData();
                }
                this.loadData.thumbnailBlockData.bitmap = this.bitmap;
                OnImageLoadListener onImageLoadListener = this.onImageLoadListener;
                if (onImageLoadListener != null) {
                    onImageLoadListener.onBlockImageLoadFinished();
                }
            }
            OnLoadStateChangeListener onLoadStateChangeListener = this.onLoadStateChangeListener;
            if (onLoadStateChangeListener != null) {
                onLoadStateChangeListener.onLoadFinished(1, null, this.throwable == null, this.throwable);
            }
            this.onImageLoadListener = null;
            this.onLoadStateChangeListener = null;
            this.loadData = null;
            this.decoder = null;
        }
    }
}
