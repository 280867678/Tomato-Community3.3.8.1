package org.opengl.surface;

import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/* loaded from: classes4.dex */
public class TestActivity extends AppCompatActivity {
    GLSurface glSurface;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C5498R.C5502layout.activity_glsurface_test);
        this.glSurface = new GLSurface(this);
        ((LinearLayout) findViewById(C5498R.C5501id.root)).addView(this.glSurface);
        this.glSurface.setOnClickListener(new View.OnClickListener() { // from class: org.opengl.surface.TestActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.glSurface.onPause();
        this.glSurface.onStop();
    }
}
