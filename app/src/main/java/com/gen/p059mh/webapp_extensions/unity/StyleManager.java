package com.gen.p059mh.webapp_extensions.unity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.widget.SwipeRefreshLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$drawable;
import com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.unity.StyleManager;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.C2516Ad;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.unity.StyleManager */
/* loaded from: classes2.dex */
public class StyleManager extends Unity {
    ImageView backImageView;
    int color;
    Context context;
    int lastSelectIndex;
    LinearLayout rootToolBar;
    int selectedColor;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout tabLayout;
    TextView titleView;
    RelativeLayout toolBarLayout;
    Unity.Method showTabBar = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$vZSkiuq6yciYkoQScRIX_O70XKU
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public final void call(Unity.MethodCallback methodCallback, Object[] objArr) {
            StyleManager.this.lambda$new$1$StyleManager(methodCallback, objArr);
        }
    };
    Unity.Method hideTabBar = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$ln-cnpu60c9XbP-OyQ2ApWgqV_M
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public final void call(Unity.MethodCallback methodCallback, Object[] objArr) {
            StyleManager.this.lambda$new$3$StyleManager(methodCallback, objArr);
        }
    };
    Unity.Method showTabBarRedDot = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$KEa0OFb6qBi1o_WP_TCzwQjnapc
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public final void call(Unity.MethodCallback methodCallback, Object[] objArr) {
            StyleManager.this.lambda$new$5$StyleManager(methodCallback, objArr);
        }
    };
    Unity.Method hideTabBarRedDot = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$HwHC_6iDVKczDP6S7qIY2E69ARU
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public final void call(Unity.MethodCallback methodCallback, Object[] objArr) {
            StyleManager.this.lambda$new$7$StyleManager(methodCallback, objArr);
        }
    };
    Unity.Method setTabBarBadge = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$Q8oFPGUpa_Erjc-VP5LIEJs8xaI
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public final void call(Unity.MethodCallback methodCallback, Object[] objArr) {
            StyleManager.this.lambda$new$9$StyleManager(methodCallback, objArr);
        }
    };
    Unity.Method setBackButtonHidden = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final boolean booleanValue = ((Boolean) ((List) objArr[0]).get(0)).booleanValue();
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    StyleManager styleManager = StyleManager.this;
                    if (styleManager.rootToolBar != null) {
                        styleManager.backImageView.setVisibility(booleanValue ? 8 : 0);
                    }
                }
            });
            methodCallback.run("success");
        }
    };
    Unity.Method setupView = new C15732();
    Unity.Method setNavigationBarHidden = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final boolean booleanValue = ((Boolean) ((List) objArr[0]).get(0)).booleanValue();
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.3.1
                @Override // java.lang.Runnable
                public void run() {
                    LinearLayout linearLayout = StyleManager.this.rootToolBar;
                    if (linearLayout != null) {
                        linearLayout.setVisibility(booleanValue ? 8 : 0);
                    }
                }
            });
            methodCallback.run("success");
        }
    };
    Unity.Method setNavigationBgColor = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.4
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final String str = (String) ((List) objArr[0]).get(0);
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.4.1
                @Override // java.lang.Runnable
                public void run() {
                    LinearLayout linearLayout = StyleManager.this.rootToolBar;
                    if (linearLayout != null) {
                        linearLayout.setBackgroundColor(Utils.colorFromCSS(str));
                    }
                }
            });
            methodCallback.run("success");
        }
    };
    Unity.Method setNavigationTextColor = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.5
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final int colorFromCSS = Utils.colorFromCSS((String) ((List) objArr[0]).get(0));
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.5.1
                @Override // java.lang.Runnable
                public void run() {
                    StyleManager styleManager = StyleManager.this;
                    if (styleManager.rootToolBar != null) {
                        styleManager.titleView.setTextColor(colorFromCSS);
                        StyleManager styleManager2 = StyleManager.this;
                        styleManager2.backImageView.setImageDrawable(Utils.tintDrawable(ContextCompat.getDrawable(styleManager2.context, R$drawable.video_back), ColorStateList.valueOf(colorFromCSS)));
                    }
                }
            });
            methodCallback.run("success");
        }
    };
    Unity.Method setTitle = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.6
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final String str = (String) ((List) objArr[0]).get(0);
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.6.1
                @Override // java.lang.Runnable
                public void run() {
                    StyleManager styleManager = StyleManager.this;
                    if (styleManager.rootToolBar != null) {
                        styleManager.titleView.setText(str);
                    }
                }
            });
            methodCallback.run("success");
        }
    };
    Unity.Method setBackgroundColor = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.7
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            final int colorFromCSS = Utils.colorFromCSS((String) ((List) objArr[0]).get(0));
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.7.1
                @Override // java.lang.Runnable
                public void run() {
                    StyleManager styleManager = StyleManager.this;
                    if (styleManager.rootToolBar != null) {
                        styleManager.getWebViewFragment().getWebParentContainer().setBackgroundColor(colorFromCSS);
                    }
                }
            });
            methodCallback.run("success");
        }
    };
    Unity.Method setupTabBar = new C15868();
    Unity.Method enablePullRefresh = new C15909();
    Unity.Method startPullDownRefresh = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.10
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            StyleManager styleManager = StyleManager.this;
            if (styleManager.swipeRefreshLayout != null) {
                styleManager.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        StyleManager.this.swipeRefreshLayout.setEnabled(true);
                        StyleManager.this.swipeRefreshLayout.setRefreshing(true);
                        StyleManager.this.event("refresh", null, "success");
                    }
                });
            }
            methodCallback.run(null);
        }
    };
    Unity.Method stopPullDownRefresh = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.11
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            StyleManager styleManager = StyleManager.this;
            if (styleManager.swipeRefreshLayout != null) {
                styleManager.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        StyleManager.this.swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
            methodCallback.run(null);
        }
    };
    Unity.Method processConfig = new C157212();

    public StyleManager() {
        registerMethod("setBackButtonHidden", this.setBackButtonHidden);
        registerMethod("setupView", this.setupView);
        registerMethod("setNavigationBarHidden", this.setNavigationBarHidden);
        registerMethod("setNavigationBgColor", this.setNavigationBgColor);
        registerMethod("setNavigationTextColor", this.setNavigationTextColor);
        registerMethod("setTitle", this.setTitle);
        registerMethod("setBackgroundColor", this.setBackgroundColor);
        registerMethod("setupTabBar", this.setupTabBar);
        registerMethod("enablePullRefresh", this.enablePullRefresh);
        registerMethod("startPullDownRefresh", this.startPullDownRefresh);
        registerMethod("stopPullDownRefresh", this.stopPullDownRefresh);
        registerMethod("processConfig", this.processConfig);
        registerMethod("showTabBar", this.showTabBar);
        registerMethod("hideTabBar", this.hideTabBar);
        registerMethod("showTabBarRedDot", this.showTabBarRedDot);
        registerMethod("hideTabBarRedDot", this.hideTabBarRedDot);
        registerMethod("setTabBarBadge", this.setTabBarBadge);
    }

    public /* synthetic */ void lambda$new$1$StyleManager(Unity.MethodCallback methodCallback, Object[] objArr) {
        final boolean booleanValue = ((Boolean) ((Map) ((List) objArr[0]).get(0)).get("animation")).booleanValue();
        if (getWebViewFragment().getAppController() != null) {
            getWebViewFragment().getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$zsDBRrHq1nBkOeGjdg1aUFEEdmY
                @Override // java.lang.Runnable
                public final void run() {
                    StyleManager.this.lambda$null$0$StyleManager(booleanValue);
                }
            });
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        methodCallback.run(hashMap);
    }

    public /* synthetic */ void lambda$null$0$StyleManager(boolean z) {
        getWebViewFragment().getAppController().showTabBar(true, z);
    }

    public /* synthetic */ void lambda$new$3$StyleManager(Unity.MethodCallback methodCallback, Object[] objArr) {
        final boolean booleanValue = ((Boolean) ((Map) ((List) objArr[0]).get(0)).get("animation")).booleanValue();
        if (getWebViewFragment().getAppController() != null) {
            getWebViewFragment().getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$dupVgGFa963nJyGuy72Esil9Gd4
                @Override // java.lang.Runnable
                public final void run() {
                    StyleManager.this.lambda$null$2$StyleManager(booleanValue);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$2$StyleManager(boolean z) {
        getWebViewFragment().getAppController().showTabBar(false, z);
    }

    public /* synthetic */ void lambda$new$5$StyleManager(Unity.MethodCallback methodCallback, Object[] objArr) {
        final int intValue = ((Number) ((Map) ((List) objArr[0]).get(0)).get(DatabaseFieldConfigLoader.FIELD_NAME_INDEX)).intValue();
        if (getWebViewFragment().getAppController() != null) {
            getWebViewFragment().getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$skdIWG7iUmQnTpDhySx_dOyLWlg
                @Override // java.lang.Runnable
                public final void run() {
                    StyleManager.this.lambda$null$4$StyleManager(intValue);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$4$StyleManager(int i) {
        getWebViewFragment().getAppController().showTabBarRedDot(true, i);
    }

    public /* synthetic */ void lambda$new$7$StyleManager(Unity.MethodCallback methodCallback, Object[] objArr) {
        final int intValue = ((Number) ((Map) ((List) objArr[0]).get(0)).get(DatabaseFieldConfigLoader.FIELD_NAME_INDEX)).intValue();
        if (getWebViewFragment().getAppController() != null) {
            getWebViewFragment().getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$NlQlacP8yHU_ma_uGF18PcEd3MA
                @Override // java.lang.Runnable
                public final void run() {
                    StyleManager.this.lambda$null$6$StyleManager(intValue);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$6$StyleManager(int i) {
        getWebViewFragment().getAppController().showTabBarRedDot(false, i);
    }

    public /* synthetic */ void lambda$new$9$StyleManager(Unity.MethodCallback methodCallback, Object[] objArr) {
        final int intValue = ((Number) ((Map) ((List) objArr[0]).get(0)).get(DatabaseFieldConfigLoader.FIELD_NAME_INDEX)).intValue();
        final String str = (String) ((Map) ((List) objArr[0]).get(0)).get("text");
        if (getWebViewFragment().getAppController() != null) {
            getWebViewFragment().getHandler().post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$W55oba1_MbXO-etLFhQF5axMq4M
                @Override // java.lang.Runnable
                public final void run() {
                    StyleManager.this.lambda$null$8$StyleManager(intValue, str);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$8$StyleManager(int i, String str) {
        getWebViewFragment().getAppController().setTabBarBadge(true, i, str);
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        this.context = getWebViewFragment().getContext();
    }

    /* renamed from: com.gen.mh.webapp_extensions.unity.StyleManager$2 */
    /* loaded from: classes2.dex */
    class C15732 implements Unity.Method {
        C15732() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            StyleManager styleManager = StyleManager.this;
            if (styleManager.context == null) {
                return;
            }
            styleManager.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.2.1
                @Override // java.lang.Runnable
                public void run() {
                    StyleManager styleManager2 = StyleManager.this;
                    styleManager2.rootToolBar = new LinearLayout(styleManager2.context);
                    StyleManager.this.rootToolBar.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                    StyleManager.this.rootToolBar.setOrientation(1);
                    StyleManager styleManager3 = StyleManager.this;
                    styleManager3.rootToolBar.setPadding(0, Utils.getStatusBarHeight(styleManager3.context), 0, 0);
                    StyleManager styleManager4 = StyleManager.this;
                    styleManager4.toolBarLayout = new RelativeLayout(styleManager4.context);
                    StyleManager styleManager5 = StyleManager.this;
                    styleManager5.rootToolBar.addView(styleManager5.toolBarLayout);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                    StyleManager styleManager6 = StyleManager.this;
                    styleManager6.backImageView = new ImageView(styleManager6.context);
                    StyleManager.this.backImageView.setVisibility(8);
                    int d2p = (int) Utils.d2p(StyleManager.this.context, 12);
                    StyleManager.this.backImageView.setPadding(d2p, 0, d2p, 0);
                    StyleManager.this.backImageView.setImageResource(R$drawable.video_back);
                    layoutParams.addRule(15);
                    StyleManager.this.backImageView.setLayoutParams(layoutParams);
                    StyleManager styleManager7 = StyleManager.this;
                    styleManager7.toolBarLayout.addView(styleManager7.backImageView);
                    StyleManager.this.backImageView.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.2.1.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            Logger.m4112i("onBackClick", StyleManager.this.getId());
                            StyleManager.this.event("backclick", null, new Object[0]);
                        }
                    });
                    RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                    StyleManager styleManager8 = StyleManager.this;
                    styleManager8.titleView = new TextView(styleManager8.context);
                    StyleManager.this.titleView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
                    StyleManager.this.titleView.setTextSize(16.0f);
                    layoutParams2.addRule(13);
                    layoutParams2.addRule(15);
                    StyleManager.this.titleView.setLayoutParams(layoutParams2);
                    StyleManager styleManager9 = StyleManager.this;
                    styleManager9.toolBarLayout.addView(styleManager9.titleView);
                    StyleManager.this.toolBarLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) Utils.d2p(StyleManager.this.context, 48)));
                    StyleManager.this.getWebViewFragment().getWebParentContainer().addView(StyleManager.this.rootToolBar, 0);
                }
            });
            methodCallback.run("success");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.StyleManager$8 */
    /* loaded from: classes2.dex */
    public class C15868 implements Unity.Method {
        C15868() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            if (StyleManager.this.context == null) {
                return;
            }
            Map map = (Map) ((List) objArr[0]).get(0);
            StyleManager.this.color = Utils.colorFromCSS(map.get("color").toString());
            StyleManager.this.selectedColor = Utils.colorFromCSS(map.get("selectedColor").toString());
            final int colorFromCSS = Utils.colorFromCSS(map.get("backgroundColor").toString());
            final List list = (List) map.get(C2516Ad.TYPE_LIST);
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.8.1
                @Override // java.lang.Runnable
                public void run() {
                    StyleManager styleManager = StyleManager.this;
                    styleManager.tabLayout = new LinearLayout(styleManager.context);
                    StyleManager styleManager2 = StyleManager.this;
                    styleManager2.tabLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) Utils.d2p(styleManager2.context, 65)));
                    StyleManager styleManager3 = StyleManager.this;
                    styleManager3.tabLayout.setPadding(0, (int) Utils.d2p(styleManager3.context, 5), 0, (int) Utils.d2p(StyleManager.this.context, 5));
                    StyleManager.this.tabLayout.setBackgroundColor(colorFromCSS);
                    for (final int i = 0; i < list.size(); i++) {
                        Map map2 = (Map) list.get(i);
                        LinearLayout linearLayout = new LinearLayout(StyleManager.this.context);
                        linearLayout.setGravity(17);
                        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
                        linearLayout.setOrientation(1);
                        final TextView textView = new TextView(StyleManager.this.context);
                        textView.setText(map2.get("text").toString());
                        textView.setTextColor(StyleManager.this.color);
                        textView.setTextSize(12.0f);
                        textView.setGravity(17);
                        linearLayout.addView(textView);
                        final ImageView imageView = new ImageView(StyleManager.this.context);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams((int) Utils.d2p(StyleManager.this.context, 25), (int) Utils.d2p(StyleManager.this.context, 25)));
                        Logger.m4113i(ResourcesLoader.WORK_HOST + File.separator + map2.get("iconPath").toString());
                        StyleManager.this.lastSelectIndex = 0;
                        ImageEngine imageEngine = SelectionSpec.getInstance().imageEngine;
                        Context context = imageView.getContext();
                        imageEngine.load(context, imageView, "http://" + ResourcesLoader.WORK_HOST + File.separator + map2.get("iconPath").toString(), new CommonCallBack<Drawable>() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.8.1.1
                            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                            public void onResult(Drawable drawable) {
                                if (i == 0) {
                                    imageView.setImageDrawable(Utils.tintDrawable(drawable, ColorStateList.valueOf(StyleManager.this.selectedColor)));
                                    textView.setTextColor(StyleManager.this.selectedColor);
                                    return;
                                }
                                imageView.setImageDrawable(Utils.tintDrawable(drawable, ColorStateList.valueOf(StyleManager.this.color)));
                                textView.setTextColor(StyleManager.this.color);
                            }

                            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                            public void onFailure(Exception exc) {
                                exc.printStackTrace();
                            }
                        });
                        linearLayout.addView(imageView, 0);
                        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.8.1.2
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                if (StyleManager.this.checkCanClick(i)) {
                                    StyleManager styleManager4 = StyleManager.this;
                                    if (styleManager4.setTabBarSelectPosition(styleManager4.lastSelectIndex, i)) {
                                        StyleManager.this.event("tabchange", null, Integer.valueOf(i));
                                    }
                                    StyleManager.this.lastSelectIndex = i;
                                    return;
                                }
                                Logger.m4109w("drawable  初始化 尚未完成 点击选中染色失败");
                            }
                        });
                        StyleManager.this.tabLayout.addView(linearLayout);
                    }
                    StyleManager.this.getWebViewFragment().getWebParentContainer().addView(StyleManager.this.tabLayout);
                }
            });
            methodCallback.run("success");
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.unity.StyleManager$9 */
    /* loaded from: classes2.dex */
    class C15909 implements Unity.Method {
        C15909() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            if (StyleManager.this.context == null) {
                return;
            }
            final boolean booleanValue = ((Boolean) ((List) objArr[0]).get(0)).booleanValue();
            StyleManager.this.getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.9.1
                @Override // java.lang.Runnable
                public void run() {
                    if (booleanValue) {
                        StyleManager styleManager = StyleManager.this;
                        if (styleManager.swipeRefreshLayout == null) {
                            styleManager.getWebViewFragment().getWebParentContainer().removeView(StyleManager.this.getWebViewFragment().provideView());
                            StyleManager styleManager2 = StyleManager.this;
                            styleManager2.swipeRefreshLayout = new SwipeRefreshLayout(styleManager2.context);
                            StyleManager.this.swipeRefreshLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
                            StyleManager.this.getWebViewFragment().getWebParentContainer().addView(StyleManager.this.swipeRefreshLayout, 1);
                            StyleManager.this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.gen.mh.webapp_extensions.unity.StyleManager.9.1.1
                                @Override // android.support.p002v4.widget.SwipeRefreshLayout.OnRefreshListener
                                public void onRefresh() {
                                    StyleManager.this.event("refresh", null, "success");
                                }
                            });
                        }
                        StyleManager.this.swipeRefreshLayout.setEnabled(true);
                        return;
                    }
                    SwipeRefreshLayout swipeRefreshLayout = StyleManager.this.swipeRefreshLayout;
                    if (swipeRefreshLayout == null) {
                        return;
                    }
                    swipeRefreshLayout.setEnabled(false);
                }
            });
            methodCallback.run("success");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.unity.StyleManager$12 */
    /* loaded from: classes2.dex */
    public class C157212 implements Unity.Method {
        C157212() {
        }

        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            SwipeRefreshLayout swipeRefreshLayout;
            Object obj = ((List) objArr[0]).get(0);
            if (obj instanceof Map) {
                final Map map = (Map) obj;
                if (map.containsKey("navigationBarTitleText")) {
                    StyleManager styleManager = StyleManager.this;
                    if (styleManager.rootToolBar != null) {
                        styleManager.titleView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$12$QQTIyY3gbXV4yl_LjEPKalzbejQ
                            @Override // java.lang.Runnable
                            public final void run() {
                                StyleManager.C157212.this.lambda$call$0$StyleManager$12(map);
                            }
                        });
                    }
                }
                if (map.containsKey("disableScroll") && (swipeRefreshLayout = StyleManager.this.swipeRefreshLayout) != null) {
                    swipeRefreshLayout.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.unity.-$$Lambda$StyleManager$12$yevNWAFydwK21E-wtLllQy2WbCs
                        @Override // java.lang.Runnable
                        public final void run() {
                            StyleManager.C157212.this.lambda$call$1$StyleManager$12(map);
                        }
                    });
                }
            }
            methodCallback.run("success");
        }

        public /* synthetic */ void lambda$call$0$StyleManager$12(Map map) {
            StyleManager.this.titleView.setText(map.get("navigationBarTitleText").toString());
        }

        public /* synthetic */ void lambda$call$1$StyleManager$12(Map map) {
            StyleManager.this.swipeRefreshLayout.setEnabled(((Boolean) map.get("disableScroll")).booleanValue());
        }
    }

    boolean checkCanClick(int i) {
        LinearLayout linearLayout;
        ImageView imageView;
        LinearLayout linearLayout2;
        ImageView imageView2;
        int i2 = this.lastSelectIndex;
        return (i2 == i || (linearLayout = (LinearLayout) this.tabLayout.getChildAt(i2)) == null || (imageView = (ImageView) linearLayout.getChildAt(0)) == null || imageView.getDrawable() == null || (linearLayout2 = (LinearLayout) this.tabLayout.getChildAt(i)) == null || (imageView2 = (ImageView) linearLayout2.getChildAt(0)) == null || imageView2.getDrawable() == null) ? false : true;
    }

    public boolean setTabBarSelectPosition(int i, int i2) {
        if (i == i2) {
            return false;
        }
        LinearLayout linearLayout = (LinearLayout) this.tabLayout.getChildAt(i);
        ImageView imageView = (ImageView) linearLayout.getChildAt(0);
        TextView textView = (TextView) linearLayout.getChildAt(1);
        if (this.color != 0 && this.selectedColor != 0) {
            Utils.tintDrawable(imageView.getDrawable(), ColorStateList.valueOf(this.color));
            textView.setTextColor(this.color);
        }
        LinearLayout linearLayout2 = (LinearLayout) this.tabLayout.getChildAt(i2);
        ImageView imageView2 = (ImageView) linearLayout2.getChildAt(0);
        TextView textView2 = (TextView) linearLayout2.getChildAt(1);
        if (this.color != 0 && this.selectedColor != 0) {
            Utils.tintDrawable(imageView2.getDrawable(), ColorStateList.valueOf(this.selectedColor));
            textView2.setTextColor(this.selectedColor);
        }
        return true;
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void unload() {
        super.unload();
    }
}
