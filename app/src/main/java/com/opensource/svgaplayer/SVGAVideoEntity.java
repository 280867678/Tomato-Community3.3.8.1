package com.opensource.svgaplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import com.opensource.svgaplayer.entities.SVGAAudioEntity;
import com.opensource.svgaplayer.entities.SVGAVideoSpriteEntity;
import com.opensource.svgaplayer.proto.AudioEntity;
import com.opensource.svgaplayer.proto.MovieEntity;
import com.opensource.svgaplayer.proto.MovieParams;
import com.opensource.svgaplayer.proto.SpriteEntity;
import com.opensource.svgaplayer.utils.SVGARect;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.Iterables;
import kotlin.collections._Arrays;
import kotlin.collections._Collections;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.ranges.Ranges;
import kotlin.text.StringsJVM;
import okio.ByteString;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SVGAVideoEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAVideoEntity {
    private int FPS;
    private List<SVGAAudioEntity> audios;
    private File cacheDir;
    private int frames;
    private MovieEntity movieItem;
    private SoundPool soundPool;
    private List<SVGAVideoSpriteEntity> sprites;
    private SVGARect videoSize;
    private boolean antiAlias = true;
    private HashMap<String, Bitmap> images = new HashMap<>();

    protected final void finalize() {
        SoundPool soundPool = this.soundPool;
        if (soundPool != null) {
            soundPool.release();
        }
        this.soundPool = null;
        this.images.clear();
    }

    public final boolean getAntiAlias() {
        return this.antiAlias;
    }

    public final void setAntiAlias(boolean z) {
        this.antiAlias = z;
    }

    public final SVGARect getVideoSize() {
        return this.videoSize;
    }

    public final int getFPS() {
        return this.FPS;
    }

    public final int getFrames() {
        return this.frames;
    }

    public final List<SVGAVideoSpriteEntity> getSprites$library_release() {
        return this.sprites;
    }

    public final List<SVGAAudioEntity> getAudios$library_release() {
        return this.audios;
    }

    public final SoundPool getSoundPool$library_release() {
        return this.soundPool;
    }

    public final HashMap<String, Bitmap> getImages$library_release() {
        return this.images;
    }

    public SVGAVideoEntity(JSONObject obj, File cacheDir) {
        List<SVGAVideoSpriteEntity> emptyList;
        List<SVGAAudioEntity> emptyList2;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        Intrinsics.checkParameterIsNotNull(cacheDir, "cacheDir");
        this.videoSize = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        this.FPS = 15;
        emptyList = CollectionsKt__CollectionsKt.emptyList();
        this.sprites = emptyList;
        emptyList2 = CollectionsKt__CollectionsKt.emptyList();
        this.audios = emptyList2;
        this.cacheDir = cacheDir;
        JSONObject optJSONObject = obj.optJSONObject("movie");
        if (optJSONObject != null) {
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("viewBox");
            if (optJSONObject2 != null) {
                this.videoSize = new SVGARect(0.0d, 0.0d, optJSONObject2.optDouble("width", 0.0d), optJSONObject2.optDouble("height", 0.0d));
            }
            this.FPS = optJSONObject.optInt("fps", 20);
            this.frames = optJSONObject.optInt("frames", 0);
        }
        try {
            resetImages(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
        }
        resetSprites(obj);
    }

    public SVGAVideoEntity(MovieEntity obj, File cacheDir) {
        List<SVGAVideoSpriteEntity> emptyList;
        List<SVGAAudioEntity> emptyList2;
        Float f;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        Intrinsics.checkParameterIsNotNull(cacheDir, "cacheDir");
        this.videoSize = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        this.FPS = 15;
        emptyList = CollectionsKt__CollectionsKt.emptyList();
        this.sprites = emptyList;
        emptyList2 = CollectionsKt__CollectionsKt.emptyList();
        this.audios = emptyList2;
        this.movieItem = obj;
        this.cacheDir = cacheDir;
        MovieParams movieParams = obj.params;
        if (movieParams != null) {
            Float f2 = movieParams.viewBoxWidth;
            float f3 = 0.0f;
            this.videoSize = new SVGARect(0.0d, 0.0d, f2 != null ? f2.floatValue() : 0.0f, movieParams.viewBoxHeight != null ? f.floatValue() : f3);
            Integer num = movieParams.fps;
            this.FPS = num != null ? num.intValue() : 20;
            Integer num2 = movieParams.frames;
            this.frames = num2 != null ? num2.intValue() : 0;
        }
        try {
            resetImages(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
        }
        resetSprites(obj);
    }

    public final void prepare$library_release(Functions<Unit> callback) {
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        MovieEntity movieEntity = this.movieItem;
        if (movieEntity != null) {
            resetAudios(movieEntity, new SVGAVideoEntity$prepare$$inlined$let$lambda$1(this, callback));
        } else {
            callback.mo6822invoke();
        }
    }

    private final void resetImages(JSONObject jSONObject) {
        BitmapFactory.Options options;
        Bitmap bitmap;
        String replace$default;
        Bitmap bitmap2;
        BitmapFactory.Options options2;
        BitmapFactory.Options options3;
        BitmapFactory.Options options4;
        JSONObject optJSONObject = jSONObject.optJSONObject("images");
        if (optJSONObject != null) {
            Iterator<String> keys = optJSONObject.keys();
            Intrinsics.checkExpressionValueIsNotNull(keys, "imgObjects.keys()");
            while (keys.hasNext()) {
                String imageKey = keys.next();
                options = SVGAVideoEntityKt.options;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                String str = this.cacheDir.getAbsolutePath() + "/" + optJSONObject.get(imageKey);
                if (new File(str).exists()) {
                    options4 = SVGAVideoEntityKt.options;
                    bitmap = BitmapFactory.decodeFile(str, options4);
                } else {
                    bitmap = null;
                }
                Intrinsics.checkExpressionValueIsNotNull(imageKey, "imageKey");
                replace$default = StringsJVM.replace$default(imageKey, ".matte", "", false, 4, null);
                if (bitmap != null) {
                    this.images.put(replace$default, bitmap);
                } else {
                    String str2 = this.cacheDir.getAbsolutePath() + "/" + optJSONObject.get(imageKey) + ".png";
                    if (new File(str2).exists()) {
                        options3 = SVGAVideoEntityKt.options;
                        bitmap2 = BitmapFactory.decodeFile(str2, options3);
                    } else {
                        bitmap2 = null;
                    }
                    if (bitmap2 != null) {
                        this.images.put(replace$default, bitmap2);
                    } else {
                        String str3 = this.cacheDir.getAbsolutePath() + "/" + imageKey + ".png";
                        if (!new File(str3).exists()) {
                            str3 = null;
                        }
                        if (str3 != null) {
                            options2 = SVGAVideoEntityKt.options;
                            Bitmap decodeFile = BitmapFactory.decodeFile(str3, options2);
                            if (decodeFile != null) {
                                this.images.put(replace$default, decodeFile);
                            }
                        }
                    }
                }
            }
        }
    }

    private final void resetImages(MovieEntity movieEntity) {
        Set<Map.Entry<String, ByteString>> entrySet;
        BitmapFactory.Options options;
        List<Byte> slice;
        BitmapFactory.Options options2;
        Bitmap bitmap;
        BitmapFactory.Options options3;
        BitmapFactory.Options options4;
        Map<String, ByteString> map = movieEntity.images;
        if (map == null || (entrySet = map.entrySet()) == null) {
            return;
        }
        Iterator<T> it2 = entrySet.iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            String imageKey = (String) entry.getKey();
            options = SVGAVideoEntityKt.options;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            byte[] byteArray = ((ByteString) entry.getValue()).toByteArray();
            Intrinsics.checkExpressionValueIsNotNull(byteArray, "byteArray");
            if (byteArray.length >= 4) {
                slice = _Arrays.slice(byteArray, new Ranges(0, 3));
                if (slice.get(0).byteValue() != 73 || slice.get(1).byteValue() != 68 || slice.get(2).byteValue() != 51) {
                    int length = byteArray.length;
                    options2 = SVGAVideoEntityKt.options;
                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, length, options2);
                    if (decodeByteArray != null) {
                        HashMap<String, Bitmap> hashMap = this.images;
                        Intrinsics.checkExpressionValueIsNotNull(imageKey, "imageKey");
                        hashMap.put(imageKey, decodeByteArray);
                    } else {
                        String utf8 = ((ByteString) entry.getValue()).utf8();
                        if (utf8 != null) {
                            String str = this.cacheDir.getAbsolutePath() + "/" + utf8;
                            if (new File(str).exists()) {
                                options4 = SVGAVideoEntityKt.options;
                                bitmap = BitmapFactory.decodeFile(str, options4);
                            } else {
                                bitmap = null;
                            }
                            if (bitmap != null) {
                                this.images.put(imageKey, bitmap);
                            } else {
                                String str2 = this.cacheDir.getAbsolutePath() + "/" + imageKey + ".png";
                                if (!new File(str2).exists()) {
                                    str2 = null;
                                }
                                if (str2 != null) {
                                    options3 = SVGAVideoEntityKt.options;
                                    Bitmap decodeFile = BitmapFactory.decodeFile(str2, options3);
                                    if (decodeFile != null) {
                                        this.images.put(imageKey, decodeFile);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private final void resetSprites(JSONObject jSONObject) {
        List<SVGAVideoSpriteEntity> list;
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("sprites");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    arrayList.add(new SVGAVideoSpriteEntity(optJSONObject));
                }
            }
        }
        list = _Collections.toList(arrayList);
        this.sprites = list;
    }

    private final void resetSprites(MovieEntity movieEntity) {
        List<SVGAVideoSpriteEntity> emptyList;
        int collectionSizeOrDefault;
        List<SpriteEntity> list = movieEntity.sprites;
        if (list == null) {
            emptyList = CollectionsKt__CollectionsKt.emptyList();
        } else {
            collectionSizeOrDefault = Iterables.collectionSizeOrDefault(list, 10);
            emptyList = new ArrayList<>(collectionSizeOrDefault);
            for (SpriteEntity it2 : list) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                emptyList.add(new SVGAVideoSpriteEntity(it2));
            }
        }
        this.sprites = emptyList;
    }

    private final void resetAudios(final MovieEntity movieEntity, final Functions<Unit> functions) {
        SoundPool soundPool;
        int collectionSizeOrDefault;
        SoundPool soundPool2;
        HashMap hashMap;
        Set<Map.Entry<String, ByteString>> entrySet;
        List<Byte> slice;
        List<AudioEntity> list = movieEntity.audios;
        if (list != null) {
            if (!(!list.isEmpty())) {
                list = null;
            }
            final List<AudioEntity> list2 = list;
            if (list2 != null) {
                final Ref$IntRef ref$IntRef = new Ref$IntRef();
                ref$IntRef.element = 0;
                if (Build.VERSION.SDK_INT >= 21) {
                    soundPool = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(1).build()).setMaxStreams(Math.min(12, list2.size())).build();
                } else {
                    soundPool = new SoundPool(Math.min(12, list2.size()), 3, 0);
                }
                HashMap hashMap2 = new HashMap();
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(list2, this, functions, movieEntity) { // from class: com.opensource.svgaplayer.SVGAVideoEntity$resetAudios$$inlined$let$lambda$1
                    final /* synthetic */ List $audios;
                    final /* synthetic */ Functions $completionBlock$inlined;

                    /* JADX INFO: Access modifiers changed from: package-private */
                    {
                        this.$completionBlock$inlined = functions;
                    }

                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool3, int i, int i2) {
                        Ref$IntRef ref$IntRef2 = Ref$IntRef.this;
                        ref$IntRef2.element++;
                        if (ref$IntRef2.element >= this.$audios.size()) {
                            this.$completionBlock$inlined.mo6822invoke();
                        }
                    }
                });
                HashMap hashMap3 = new HashMap();
                Map<String, ByteString> map = movieEntity.images;
                if (map != null && (entrySet = map.entrySet()) != null) {
                    Iterator<T> it2 = entrySet.iterator();
                    while (it2.hasNext()) {
                        Map.Entry entry = (Map.Entry) it2.next();
                        String imageKey = (String) entry.getKey();
                        byte[] byteArray = ((ByteString) entry.getValue()).toByteArray();
                        Intrinsics.checkExpressionValueIsNotNull(byteArray, "byteArray");
                        if (byteArray.length >= 4) {
                            slice = _Arrays.slice(byteArray, new Ranges(0, 3));
                            if (slice.get(0).byteValue() == 73 && slice.get(1).byteValue() == 68 && slice.get(2).byteValue() == 51) {
                                Intrinsics.checkExpressionValueIsNotNull(imageKey, "imageKey");
                                hashMap3.put(imageKey, byteArray);
                            }
                        }
                    }
                }
                if (hashMap3.size() > 0) {
                    for (Map.Entry entry2 : hashMap3.entrySet()) {
                        File tmpFile = File.createTempFile((String) entry2.getKey(), ".mp3");
                        FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                        fileOutputStream.write((byte[]) entry2.getValue());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Object key = entry2.getKey();
                        Intrinsics.checkExpressionValueIsNotNull(tmpFile, "tmpFile");
                        hashMap2.put(key, tmpFile);
                    }
                }
                collectionSizeOrDefault = Iterables.collectionSizeOrDefault(list2, 10);
                ArrayList arrayList = new ArrayList(collectionSizeOrDefault);
                for (AudioEntity audio : list2) {
                    Intrinsics.checkExpressionValueIsNotNull(audio, "audio");
                    SVGAAudioEntity sVGAAudioEntity = new SVGAAudioEntity(audio);
                    File file = (File) hashMap2.get(audio.audioKey);
                    if (file != null) {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        FileDescriptor fd = fileInputStream.getFD();
                        Integer num = audio.startTime;
                        double intValue = num != null ? num.intValue() : 0;
                        Integer num2 = audio.totalTime;
                        int intValue2 = num2 != null ? num2.intValue() : 0;
                        soundPool2 = soundPool;
                        hashMap = hashMap2;
                        sVGAAudioEntity.setSoundID(Integer.valueOf(soundPool.load(fd, (long) ((intValue / intValue2) * fileInputStream.available()), fileInputStream.available(), 1)));
                        fileInputStream.close();
                    } else {
                        soundPool2 = soundPool;
                        hashMap = hashMap2;
                    }
                    arrayList.add(sVGAAudioEntity);
                    soundPool = soundPool2;
                    hashMap2 = hashMap;
                }
                this.audios = arrayList;
                this.soundPool = soundPool;
                return;
            }
        }
        functions.mo6822invoke();
    }
}
