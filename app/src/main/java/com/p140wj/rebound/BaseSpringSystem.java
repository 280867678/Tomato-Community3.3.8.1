package com.p140wj.rebound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* renamed from: com.wj.rebound.BaseSpringSystem */
/* loaded from: classes4.dex */
public class BaseSpringSystem {
    private final SpringLooper mSpringLooper;
    private final Map<String, Spring> mSpringRegistry = new HashMap();
    private final Set<Spring> mActiveSprings = new CopyOnWriteArraySet();
    private final CopyOnWriteArraySet<SpringSystemListener> mListeners = new CopyOnWriteArraySet<>();
    private boolean mIdle = true;

    public BaseSpringSystem(SpringLooper springLooper) {
        if (springLooper == null) {
            throw new IllegalArgumentException("springLooper is required");
        }
        this.mSpringLooper = springLooper;
        this.mSpringLooper.setSpringSystem(this);
    }

    public boolean getIsIdle() {
        return this.mIdle;
    }

    public Spring createSpring() {
        Spring spring = new Spring(this);
        registerSpring(spring);
        return spring;
    }

    void registerSpring(Spring spring) {
        if (spring == null) {
            throw new IllegalArgumentException("spring is required");
        }
        if (this.mSpringRegistry.containsKey(spring.getId())) {
            throw new IllegalArgumentException("spring is already registered");
        }
        this.mSpringRegistry.put(spring.getId(), spring);
    }

    void advance(double d) {
        for (Spring spring : this.mActiveSprings) {
            if (spring.systemShouldAdvance()) {
                spring.advance(d / 1000.0d);
            } else {
                this.mActiveSprings.remove(spring);
            }
        }
    }

    public void loop(double d) {
        Iterator<SpringSystemListener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onBeforeIntegrate(this);
        }
        advance(d);
        if (this.mActiveSprings.isEmpty()) {
            this.mIdle = true;
        }
        Iterator<SpringSystemListener> it3 = this.mListeners.iterator();
        while (it3.hasNext()) {
            it3.next().onAfterIntegrate(this);
        }
        if (this.mIdle) {
            this.mSpringLooper.stop();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void activateSpring(String str) {
        Spring spring = this.mSpringRegistry.get(str);
        if (spring == null) {
            throw new IllegalArgumentException("springId " + str + " does not reference a registered spring");
        }
        this.mActiveSprings.add(spring);
        if (!getIsIdle()) {
            return;
        }
        this.mIdle = false;
        this.mSpringLooper.start();
    }
}
