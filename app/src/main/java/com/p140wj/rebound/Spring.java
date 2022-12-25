package com.p140wj.rebound;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* renamed from: com.wj.rebound.Spring */
/* loaded from: classes4.dex */
public class Spring {

    /* renamed from: ID */
    private static int f5885ID;
    private double mEndValue;
    private final String mId;
    private boolean mOvershootClampingEnabled;
    private SpringConfig mSpringConfig;
    private final BaseSpringSystem mSpringSystem;
    private double mStartValue;
    private final PhysicsState mCurrentState = new PhysicsState();
    private final PhysicsState mPreviousState = new PhysicsState();
    private final PhysicsState mTempState = new PhysicsState();
    private boolean mWasAtRest = true;
    private double mRestSpeedThreshold = 0.005d;
    private double mDisplacementFromRestThreshold = 0.005d;
    private double mTimeAccumulator = 0.0d;
    private final CopyOnWriteArraySet<SpringListener> mListeners = new CopyOnWriteArraySet<>();

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.wj.rebound.Spring$PhysicsState */
    /* loaded from: classes4.dex */
    public static class PhysicsState {
        double position;
        double velocity;

        private PhysicsState() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Spring(BaseSpringSystem baseSpringSystem) {
        if (baseSpringSystem == null) {
            throw new IllegalArgumentException("Spring cannot be created outside of a BaseSpringSystem");
        }
        this.mSpringSystem = baseSpringSystem;
        StringBuilder sb = new StringBuilder();
        sb.append("spring:");
        int i = f5885ID;
        f5885ID = i + 1;
        sb.append(i);
        this.mId = sb.toString();
        setSpringConfig(SpringConfig.defaultConfig);
    }

    public String getId() {
        return this.mId;
    }

    public Spring setSpringConfig(SpringConfig springConfig) {
        if (springConfig == null) {
            throw new IllegalArgumentException("springConfig is required");
        }
        this.mSpringConfig = springConfig;
        return this;
    }

    public Spring setCurrentValue(double d) {
        setCurrentValue(d, true);
        return this;
    }

    public Spring setCurrentValue(double d, boolean z) {
        this.mStartValue = d;
        this.mCurrentState.position = d;
        this.mSpringSystem.activateSpring(getId());
        Iterator<SpringListener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onSpringUpdate(this);
        }
        if (z) {
            setAtRest();
        }
        return this;
    }

    public double getCurrentValue() {
        return this.mCurrentState.position;
    }

    private double getDisplacementDistanceForState(PhysicsState physicsState) {
        return Math.abs(this.mEndValue - physicsState.position);
    }

    public Spring setEndValue(double d) {
        if (this.mEndValue != d || !isAtRest()) {
            this.mStartValue = getCurrentValue();
            this.mEndValue = d;
            this.mSpringSystem.activateSpring(getId());
            Iterator<SpringListener> it2 = this.mListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onSpringEndStateChange(this);
            }
            return this;
        }
        return this;
    }

    public double getEndValue() {
        return this.mEndValue;
    }

    public Spring setVelocity(double d) {
        PhysicsState physicsState = this.mCurrentState;
        if (d == physicsState.velocity) {
            return this;
        }
        physicsState.velocity = d;
        this.mSpringSystem.activateSpring(getId());
        return this;
    }

    public boolean isOvershooting() {
        return this.mSpringConfig.tension > 0.0d && ((this.mStartValue < this.mEndValue && getCurrentValue() > this.mEndValue) || (this.mStartValue > this.mEndValue && getCurrentValue() < this.mEndValue));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void advance(double d) {
        double d2;
        boolean z;
        boolean isAtRest = isAtRest();
        if (!isAtRest || !this.mWasAtRest) {
            double d3 = 0.064d;
            if (d <= 0.064d) {
                d3 = d;
            }
            this.mTimeAccumulator += d3;
            SpringConfig springConfig = this.mSpringConfig;
            double d4 = springConfig.tension;
            double d5 = springConfig.friction;
            PhysicsState physicsState = this.mCurrentState;
            double d6 = physicsState.position;
            double d7 = physicsState.velocity;
            PhysicsState physicsState2 = this.mTempState;
            double d8 = physicsState2.position;
            double d9 = physicsState2.velocity;
            boolean z2 = isAtRest;
            while (true) {
                d2 = this.mTimeAccumulator;
                if (d2 < 0.001d) {
                    break;
                }
                this.mTimeAccumulator = d2 - 0.001d;
                if (this.mTimeAccumulator < 0.001d) {
                    PhysicsState physicsState3 = this.mPreviousState;
                    physicsState3.position = d6;
                    physicsState3.velocity = d7;
                }
                double d10 = this.mEndValue;
                double d11 = ((d10 - d8) * d4) - (d5 * d7);
                double d12 = d7 + (d11 * 0.001d * 0.5d);
                double d13 = ((d10 - (((d7 * 0.001d) * 0.5d) + d6)) * d4) - (d5 * d12);
                double d14 = d7 + (d13 * 0.001d * 0.5d);
                double d15 = ((d10 - (d6 + ((d12 * 0.001d) * 0.5d))) * d4) - (d5 * d14);
                double d16 = d6 + (d14 * 0.001d);
                double d17 = d7 + (d15 * 0.001d);
                d6 += (d7 + ((d12 + d14) * 2.0d) + d17) * 0.16666666666666666d * 0.001d;
                d7 += (d11 + ((d13 + d15) * 2.0d) + (((d10 - d16) * d4) - (d5 * d17))) * 0.16666666666666666d * 0.001d;
                d8 = d16;
                d9 = d17;
            }
            PhysicsState physicsState4 = this.mTempState;
            physicsState4.position = d8;
            physicsState4.velocity = d9;
            PhysicsState physicsState5 = this.mCurrentState;
            physicsState5.position = d6;
            physicsState5.velocity = d7;
            if (d2 > 0.0d) {
                interpolate(d2 / 0.001d);
            }
            boolean z3 = true;
            if (isAtRest() || (this.mOvershootClampingEnabled && isOvershooting())) {
                if (d4 > 0.0d) {
                    double d18 = this.mEndValue;
                    this.mStartValue = d18;
                    this.mCurrentState.position = d18;
                } else {
                    this.mEndValue = this.mCurrentState.position;
                    this.mStartValue = this.mEndValue;
                }
                setVelocity(0.0d);
                z2 = true;
            }
            if (this.mWasAtRest) {
                this.mWasAtRest = false;
                z = true;
            } else {
                z = false;
            }
            if (z2) {
                this.mWasAtRest = true;
            } else {
                z3 = false;
            }
            Iterator<SpringListener> it2 = this.mListeners.iterator();
            while (it2.hasNext()) {
                SpringListener next = it2.next();
                if (z) {
                    next.onSpringActivate(this);
                }
                next.onSpringUpdate(this);
                if (z3) {
                    next.onSpringAtRest(this);
                }
            }
        }
    }

    public boolean systemShouldAdvance() {
        return !isAtRest() || !wasAtRest();
    }

    public boolean wasAtRest() {
        return this.mWasAtRest;
    }

    public boolean isAtRest() {
        return Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringConfig.tension == 0.0d);
    }

    public Spring setAtRest() {
        PhysicsState physicsState = this.mCurrentState;
        double d = physicsState.position;
        this.mEndValue = d;
        this.mTempState.position = d;
        physicsState.velocity = 0.0d;
        return this;
    }

    private void interpolate(double d) {
        PhysicsState physicsState = this.mCurrentState;
        PhysicsState physicsState2 = this.mPreviousState;
        double d2 = 1.0d - d;
        physicsState.position = (physicsState.position * d) + (physicsState2.position * d2);
        physicsState.velocity = (physicsState.velocity * d) + (physicsState2.velocity * d2);
    }

    public Spring addListener(SpringListener springListener) {
        if (springListener == null) {
            throw new IllegalArgumentException("newListener is required");
        }
        this.mListeners.add(springListener);
        return this;
    }
}
