package com.one.tomato.dialog;

import android.content.Context;
import android.support.p002v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.p080ui.p082up.view.UpHomeActivity;
import com.one.tomato.utils.AppUtil;
import com.zyyoona7.popup.EasyPopup;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShowUpPopWindow.kt */
/* loaded from: classes3.dex */
public final class ShowUpPopWindow {
    private int currentFlag;
    private EasyPopup easyPop;
    private ImageView image;
    private ImageView image_arrow;
    private TextView text_desc;
    private TextView text_name;

    static {
        new Companion(null);
    }

    public ShowUpPopWindow(final Context context) {
        EasyPopup create = EasyPopup.create();
        create.setContentView(context, R.layout.show_up_popwindow);
        EasyPopup easyPopup = create;
        easyPopup.setFocusAndOutsideEnable(true);
        EasyPopup easyPopup2 = easyPopup;
        easyPopup2.setBackgroundDimEnable(true);
        EasyPopup easyPopup3 = easyPopup2;
        easyPopup3.setDimValue(0.4f);
        EasyPopup easyPopup4 = easyPopup3;
        easyPopup4.setDimColor(ViewCompat.MEASURED_STATE_MASK);
        EasyPopup easyPopup5 = easyPopup4;
        easyPopup5.apply();
        this.easyPop = easyPopup5;
        EasyPopup easyPopup6 = this.easyPop;
        RelativeLayout relativeLayout = null;
        this.text_name = easyPopup6 != null ? (TextView) easyPopup6.findViewById(R.id.text_name) : null;
        EasyPopup easyPopup7 = this.easyPop;
        this.image = easyPopup7 != null ? (ImageView) easyPopup7.findViewById(R.id.image) : null;
        EasyPopup easyPopup8 = this.easyPop;
        this.image_arrow = easyPopup8 != null ? (ImageView) easyPopup8.findViewById(R.id.image_arrow) : null;
        EasyPopup easyPopup9 = this.easyPop;
        this.text_desc = easyPopup9 != null ? (TextView) easyPopup9.findViewById(R.id.text_desc) : null;
        EasyPopup easyPopup10 = this.easyPop;
        relativeLayout = easyPopup10 != null ? (RelativeLayout) easyPopup10.findViewById(R.id.relate_show_pop) : relativeLayout;
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ShowUpPopWindow.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (ShowUpPopWindow.this.getCurrentFlag() == 6) {
                        return;
                    }
                    if (ShowUpPopWindow.this.getCurrentFlag() == 7) {
                        UpHomeActivity.Companion.startAct(context, 2);
                    } else {
                        UpHomeActivity.Companion.startAct(context, 1);
                    }
                }
            });
        }
    }

    /* compiled from: ShowUpPopWindow.kt */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final int getCurrentFlag() {
        return this.currentFlag;
    }

    public final void showDown(View anchorView, int i) {
        Intrinsics.checkParameterIsNotNull(anchorView, "anchorView");
        this.currentFlag = i;
        switch (i) {
            case 1:
                ImageView imageView = this.image;
                if (imageView != null) {
                    imageView.setImageResource(R.drawable.my_up_y1);
                }
                TextView textView = this.text_name;
                if (textView != null) {
                    textView.setText(AppUtil.getString(R.string.my_up_y1));
                    break;
                }
                break;
            case 2:
                ImageView imageView2 = this.image;
                if (imageView2 != null) {
                    imageView2.setImageResource(R.drawable.my_up_y2);
                }
                TextView textView2 = this.text_name;
                if (textView2 != null) {
                    textView2.setText(AppUtil.getString(R.string.my_up_y2));
                    break;
                }
                break;
            case 3:
                ImageView imageView3 = this.image;
                if (imageView3 != null) {
                    imageView3.setImageResource(R.drawable.my_up_y3);
                }
                TextView textView3 = this.text_name;
                if (textView3 != null) {
                    textView3.setText(AppUtil.getString(R.string.my_up_y3));
                    break;
                }
                break;
            case 4:
                ImageView imageView4 = this.image;
                if (imageView4 != null) {
                    imageView4.setImageResource(R.drawable.my_up_y4);
                }
                TextView textView4 = this.text_name;
                if (textView4 != null) {
                    textView4.setText(AppUtil.getString(R.string.my_up_y4));
                    break;
                }
                break;
            case 5:
                ImageView imageView5 = this.image;
                if (imageView5 != null) {
                    imageView5.setImageResource(R.drawable.my_up_y5);
                }
                TextView textView5 = this.text_name;
                if (textView5 != null) {
                    textView5.setText(AppUtil.getString(R.string.my_up_y5));
                    break;
                }
                break;
            case 6:
                ImageView imageView6 = this.image;
                if (imageView6 != null) {
                    imageView6.setImageResource(R.drawable.up_gold_porter);
                }
                TextView textView6 = this.text_name;
                if (textView6 != null) {
                    textView6.setText(AppUtil.getString(R.string.my_gold_porter));
                }
                TextView textView7 = this.text_desc;
                if (textView7 != null) {
                    textView7.setVisibility(0);
                }
                TextView textView8 = this.text_desc;
                if (textView8 != null) {
                    textView8.setText(AppUtil.getString(R.string.up_gold_porter));
                }
                ImageView imageView7 = this.image_arrow;
                if (imageView7 != null) {
                    imageView7.setVisibility(8);
                    break;
                }
                break;
            case 7:
                ImageView imageView8 = this.image;
                if (imageView8 != null) {
                    imageView8.setImageResource(R.drawable.up_original);
                }
                TextView textView9 = this.text_name;
                if (textView9 != null) {
                    textView9.setText(AppUtil.getString(R.string.up_original));
                    break;
                }
                break;
            case 8:
                ImageView imageView9 = this.image;
                if (imageView9 != null) {
                    imageView9.setImageResource(R.drawable.my_up_y1);
                }
                TextView textView10 = this.text_name;
                if (textView10 != null) {
                    textView10.setText(AppUtil.getString(R.string.up_medal_8));
                    break;
                }
                break;
            case 9:
                ImageView imageView10 = this.image;
                if (imageView10 != null) {
                    imageView10.setImageResource(R.drawable.my_up_y2);
                }
                TextView textView11 = this.text_name;
                if (textView11 != null) {
                    textView11.setText(AppUtil.getString(R.string.up_medal_9));
                    break;
                }
                break;
            case 10:
                ImageView imageView11 = this.image;
                if (imageView11 != null) {
                    imageView11.setImageResource(R.drawable.my_up_y3);
                }
                TextView textView12 = this.text_name;
                if (textView12 != null) {
                    textView12.setText(AppUtil.getString(R.string.up_medal_10));
                    break;
                }
                break;
            case 11:
                ImageView imageView12 = this.image;
                if (imageView12 != null) {
                    imageView12.setImageResource(R.drawable.my_up_y3_v);
                }
                TextView textView13 = this.text_name;
                if (textView13 != null) {
                    textView13.setText(AppUtil.getString(R.string.up_medal_11));
                    break;
                }
                break;
            case 12:
                ImageView imageView13 = this.image;
                if (imageView13 != null) {
                    imageView13.setImageResource(R.drawable.my_up_y4);
                }
                TextView textView14 = this.text_name;
                if (textView14 != null) {
                    textView14.setText(AppUtil.getString(R.string.up_medal_12));
                    break;
                }
                break;
            case 13:
                ImageView imageView14 = this.image;
                if (imageView14 != null) {
                    imageView14.setImageResource(R.drawable.my_up_y5);
                }
                TextView textView15 = this.text_name;
                if (textView15 != null) {
                    textView15.setText(AppUtil.getString(R.string.up_medal_13));
                    break;
                }
                break;
        }
        EasyPopup easyPopup = this.easyPop;
        if (easyPopup != null) {
            easyPopup.showAtAnchorView(anchorView, 2, 0);
        }
    }
}
