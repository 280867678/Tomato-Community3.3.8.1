package com.netease.nis.captcha;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/* renamed from: com.netease.nis.captcha.c */
/* loaded from: classes3.dex */
public class DialogC2407c extends Dialog {

    /* renamed from: a */
    private Context f1640a;

    /* renamed from: b */
    private TextView f1641b;

    /* renamed from: c */
    private ImageView f1642c;

    /* renamed from: d */
    private RelativeLayout f1643d;

    /* renamed from: e */
    private boolean f1644e = false;

    /* renamed from: f */
    private int f1645f;

    /* renamed from: g */
    private String f1646g;

    /* renamed from: h */
    private int f1647h;

    public DialogC2407c(@NonNull Context context) {
        super(context);
        this.f1640a = context;
    }

    /* renamed from: a */
    private void m3817a() {
        setContentView(C2398R.C2402layout.dialog_captcha_tip);
        this.f1641b = (TextView) findViewById(C2398R.C2401id.tv_status);
        this.f1642c = (ImageView) findViewById(C2398R.C2401id.iv_loading);
        this.f1643d = (RelativeLayout) findViewById(C2398R.C2401id.tip_dialog_rl);
        this.f1643d.setOnClickListener(new View.OnClickListener() { // from class: com.netease.nis.captcha.c.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (C2409d.m3804a(DialogC2407c.this.f1640a)) {
                    if (DialogC2407c.this.f1644e) {
                        Captcha.getInstance().m3845a();
                    } else {
                        Captcha.getInstance().m3840c().m3823c();
                    }
                    DialogC2407c.this.m3813b();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m3813b() {
        int i = this.f1645f;
        if (i == 0) {
            if (!TextUtils.isEmpty(this.f1646g)) {
                m3810b(this.f1646g);
                return;
            }
            i = C2398R.string.tip_loading;
        }
        m3808c(i);
    }

    /* renamed from: c */
    private void m3809c() {
        Context context;
        int i = this.f1647h;
        if (i == 0 || (context = this.f1640a) == null) {
            this.f1642c.setImageResource(C2398R.C2400drawable.nis_captcha_anim_loading);
        } else {
            this.f1642c.setImageDrawable(ContextCompat.getDrawable(context, i));
        }
        ((AnimationDrawable) this.f1642c.getDrawable()).start();
    }

    /* renamed from: d */
    private void m3806d() {
        try {
            AnimationDrawable animationDrawable = (AnimationDrawable) this.f1642c.getDrawable();
            if (animationDrawable == null) {
                return;
            }
            animationDrawable.stop();
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public void m3816a(int i) {
        this.f1645f = i;
    }

    /* renamed from: a */
    public void m3814a(String str) {
        this.f1646g = str;
    }

    /* renamed from: b */
    public void m3812b(int i) {
        this.f1647h = i;
    }

    /* renamed from: b */
    public void m3810b(String str) {
        this.f1641b.setText(str);
        if (TextUtils.isEmpty(this.f1646g) || !this.f1646g.equals(str)) {
            return;
        }
        m3809c();
    }

    /* renamed from: c */
    public void m3808c(int i) {
        TextView textView = this.f1641b;
        if (textView == null) {
            return;
        }
        textView.setText(i);
        if (i == C2398R.string.tip_init_timeout || i == C2398R.string.tip_load_failed || i == C2398R.string.tip_no_network) {
            this.f1642c.setImageResource(C2398R.mipmap.ic_error);
            if (i != C2398R.string.tip_no_network) {
                return;
            }
            this.f1644e = true;
        } else if (i != C2398R.string.tip_loading && i != this.f1645f) {
        } else {
            m3809c();
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        try {
            if (this.f1640a == null || ((Activity) this.f1640a).isFinishing()) {
                return;
            }
            super.dismiss();
        } catch (Exception e) {
            C2409d.m3798b(Captcha.TAG, "Captcha Tip Dialog dismiss Error: %s", e.toString());
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        m3817a();
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        m3809c();
        m3813b();
        this.f1644e = false;
    }

    @Override // android.app.Dialog
    protected void onStop() {
        m3806d();
        super.onStop();
    }

    @Override // android.app.Dialog
    public void show() {
        try {
            if (this.f1640a == null || ((Activity) this.f1640a).isFinishing()) {
                return;
            }
            super.show();
        } catch (Exception e) {
            C2409d.m3798b("Captcha Tip Dialog show Error:%s", e.toString());
        }
    }
}
