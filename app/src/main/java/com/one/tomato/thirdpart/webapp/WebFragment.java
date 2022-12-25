package com.one.tomato.thirdpart.webapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;

/* loaded from: classes3.dex */
public class WebFragment extends MainFragment {
    @Override // com.gen.p059mh.webapp_extensions.fragments.MainFragment, com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.gen.p059mh.webapp_extensions.fragments.MainFragment, com.gen.p059mh.webapp_extensions.fragments.WebAppFragment, com.gen.p059mh.webapps.WebViewLaunchFragment, com.gen.p059mh.webapps.listener.IWebFragmentController
    public void initializerPlugins() {
        super.initializerPlugins();
        registerPlugin(new AppToolsPlugin());
    }
}
