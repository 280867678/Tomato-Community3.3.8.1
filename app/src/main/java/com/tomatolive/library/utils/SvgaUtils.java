package com.tomatolive.library.utils;

import android.text.TextUtils;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tomatolive.library.download.ResHotLoadManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class SvgaUtils {
    public static void playHotLoadRes(final String str, final SVGAImageView sVGAImageView, final SVGAParser sVGAParser) {
        if (TextUtils.isEmpty(str) || sVGAImageView == null || sVGAParser == null) {
            return;
        }
        if (!FileUtils.isExist(str)) {
            ResHotLoadManager.getInstance().reLoad();
            return;
        }
        try {
            Observable.just(true).map(new Function() { // from class: com.tomatolive.library.utils.-$$Lambda$SvgaUtils$4HOGJ1ywwdLfQ_uNcSAbgfCRx_0
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo6755apply(Object obj) {
                    InputStream sVGAFileInputStream;
                    Boolean bool = (Boolean) obj;
                    sVGAFileInputStream = FileUtils.getSVGAFileInputStream(str);
                    return sVGAFileInputStream;
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<InputStream>() { // from class: com.tomatolive.library.utils.SvgaUtils.1
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(InputStream inputStream) {
                    if (inputStream == null) {
                        return;
                    }
                    SVGAParser.this.decodeFromInputStream(inputStream, str, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.utils.SvgaUtils.1.1
                        @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                        public void onError() {
                        }

                        @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                        public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                            sVGAImageView.setVisibility(0);
                            sVGAImageView.setVideoItem(sVGAVideoEntity);
                            sVGAImageView.startAnimation();
                        }
                    }, true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playHotLoadRes(final String str, final SVGAImageView sVGAImageView, final SVGAParser sVGAParser, final SVGACallback sVGACallback) {
        if (TextUtils.isEmpty(str)) {
            if (sVGACallback == null) {
                return;
            }
            sVGACallback.onFinished();
        } else if (sVGAImageView == null || sVGAParser == null) {
        } else {
            if (!FileUtils.isExist(str)) {
                if (sVGACallback != null) {
                    sVGACallback.onFinished();
                }
                ResHotLoadManager.getInstance().reLoad();
                return;
            }
            try {
                Observable.just(true).map(new Function() { // from class: com.tomatolive.library.utils.-$$Lambda$SvgaUtils$VEsMX_YVAnyZKqH2QO4nX2Q3vZg
                    @Override // io.reactivex.functions.Function
                    /* renamed from: apply */
                    public final Object mo6755apply(Object obj) {
                        InputStream sVGAFileInputStream;
                        Boolean bool = (Boolean) obj;
                        sVGAFileInputStream = FileUtils.getSVGAFileInputStream(str);
                        return sVGAFileInputStream;
                    }
                }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<InputStream>() { // from class: com.tomatolive.library.utils.SvgaUtils.2
                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                    public void accept(InputStream inputStream) {
                        if (inputStream == null) {
                            return;
                        }
                        SVGAParser.this.decodeFromInputStream(inputStream, str, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.utils.SvgaUtils.2.1
                            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                            public void onError() {
                                SVGACallback sVGACallback2 = sVGACallback;
                                if (sVGACallback2 != null) {
                                    sVGACallback2.onFinished();
                                }
                            }

                            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                            public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                                sVGAImageView.setVisibility(0);
                                sVGAImageView.setVideoItem(sVGAVideoEntity);
                                sVGAImageView.startAnimation();
                            }
                        }, true);
                    }

                    @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                    public void onError(Throwable th) {
                        super.onError(th);
                        SVGACallback sVGACallback2 = sVGACallback;
                        if (sVGACallback2 != null) {
                            sVGACallback2.onFinished();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void playAssetRes(String str, final SVGAImageView sVGAImageView, SVGAParser sVGAParser) {
        if (TextUtils.isEmpty(str) || sVGAImageView == null || sVGAParser == null) {
            return;
        }
        sVGAParser.decodeFromAssets(str, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.utils.SvgaUtils.3
            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onError() {
            }

            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                SVGAImageView.this.setVisibility(0);
                SVGAImageView.this.setImageDrawable(new SVGADrawable(sVGAVideoEntity));
                SVGAImageView.this.startAnimation();
            }
        });
    }

    public static void playAssetRes(String str, final SVGAImageView sVGAImageView, final SVGADynamicEntity sVGADynamicEntity, SVGAParser sVGAParser) {
        if (TextUtils.isEmpty(str) || sVGAImageView == null || sVGAParser == null) {
            return;
        }
        sVGAParser.decodeFromAssets(str, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.utils.SvgaUtils.4
            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onError() {
            }

            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                SVGAImageView.this.setVisibility(0);
                SVGAImageView.this.setVideoItem(sVGAVideoEntity, sVGADynamicEntity);
                SVGAImageView.this.startAnimation();
            }
        });
    }
}
