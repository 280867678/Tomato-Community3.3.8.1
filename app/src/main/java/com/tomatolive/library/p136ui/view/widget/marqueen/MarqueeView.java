package com.tomatolive.library.p136ui.view.widget.marqueen;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ViewFlipper;
import com.tomatolive.library.R$anim;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.p136ui.view.widget.marqueen.utli.AnimationListenerAdapter;
import com.tomatolive.library.p136ui.view.widget.marqueen.utli.OnItemClickListener;
import com.tomatolive.library.p136ui.view.widget.marqueen.utli.Util;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/* renamed from: com.tomatolive.library.ui.view.widget.marqueen.MarqueeView */
/* loaded from: classes4.dex */
public class MarqueeView<T extends View, E> extends ViewFlipper implements Observer {
    private final int DEFAULT_ANIM_RES_IN;
    private final int DEFAULT_ANIM_RES_OUT;
    protected MarqueeFactory<T, E> factory;
    private boolean isJustOnceFlag;
    private final View.OnClickListener onClickListener;
    private OnItemClickListener<T, E> onItemClickListener;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.DEFAULT_ANIM_RES_IN = R$anim.fq_anim_marqueen_in_bottom;
        this.DEFAULT_ANIM_RES_OUT = R$anim.fq_anim_marqueen_out_top;
        this.isJustOnceFlag = true;
        this.onClickListener = new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.marqueen.MarqueeView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MarqueeView.this.onItemClickListener != null) {
                    MarqueeFactory<T, E> marqueeFactory = MarqueeView.this.factory;
                    if (marqueeFactory == null || Util.isEmpty(marqueeFactory.getData()) || MarqueeView.this.getChildCount() == 0) {
                        MarqueeView.this.onItemClickListener.onItemClickListener(null, null, -1);
                        return;
                    }
                    int displayedChild = MarqueeView.this.getDisplayedChild();
                    MarqueeView.this.onItemClickListener.onItemClickListener(MarqueeView.this.getCurrentView(), MarqueeView.this.factory.getData().get(displayedChild), displayedChild);
                }
            }
        };
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if (getInAnimation() == null || getOutAnimation() == null) {
            setInAnimation(getContext(), this.DEFAULT_ANIM_RES_IN);
            setOutAnimation(getContext(), this.DEFAULT_ANIM_RES_OUT);
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.MarqueeView);
        if (obtainStyledAttributes.hasValue(R$styleable.MarqueeView_marqueeAnimDuration)) {
            long j = obtainStyledAttributes.getInt(R$styleable.MarqueeView_marqueeAnimDuration, -1);
            getInAnimation().setDuration(j);
            getOutAnimation().setDuration(j);
        }
        obtainStyledAttributes.recycle();
        setOnClickListener(this.onClickListener);
    }

    public void setAnimDuration(long j) {
        if (getInAnimation() != null) {
            getInAnimation().setDuration(j);
        }
        if (getOutAnimation() != null) {
            getOutAnimation().setDuration(j);
        }
    }

    public void setInAndOutAnim(Animation animation, Animation animation2) {
        setInAnimation(animation);
        setOutAnimation(animation2);
    }

    public void setInAndOutAnim(@AnimRes int i, @AnimRes int i2) {
        setInAnimation(getContext(), i);
        setOutAnimation(getContext(), i2);
    }

    public void setMarqueeFactory(MarqueeFactory<T, E> marqueeFactory) {
        this.factory = marqueeFactory;
        marqueeFactory.attachedToMarqueeView(this);
        refreshChildViews();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refreshChildViews() {
        try {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            List<T> marqueeViews = this.factory.getMarqueeViews();
            for (int i = 0; i < marqueeViews.size(); i++) {
                addView(marqueeViews.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // java.util.Observer
    public void update(Observable observable, Object obj) {
        if (obj != null && MarqueeFactory.COMMAND_UPDATE_DATA.equals(obj.toString())) {
            Animation inAnimation = getInAnimation();
            if (inAnimation != null && inAnimation.hasStarted()) {
                inAnimation.setAnimationListener(new AnimationListenerAdapter() { // from class: com.tomatolive.library.ui.view.widget.marqueen.MarqueeView.1
                    @Override // com.tomatolive.library.p136ui.view.widget.marqueen.utli.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(final Animation animation) {
                        MarqueeView.this.post(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.marqueen.MarqueeView.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                MarqueeView.this.refreshChildViews();
                                Animation animation2 = animation;
                                if (animation2 != null) {
                                    animation2.setAnimationListener(null);
                                }
                            }
                        });
                    }
                });
            } else {
                refreshChildViews();
            }
        }
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener) {
        if (this.isJustOnceFlag) {
            super.setOnClickListener(onClickListener);
            this.isJustOnceFlag = false;
            return;
        }
        throw new UnsupportedOperationException("The setOnClickListener method is not supported,please use setOnItemClickListener method.");
    }

    public void setOnItemClickListener(OnItemClickListener<T, E> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
