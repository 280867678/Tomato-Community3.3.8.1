package com.eclipsesource.p056v8.debug;

/* renamed from: com.eclipsesource.v8.debug.StepAction */
/* loaded from: classes2.dex */
public enum StepAction {
    STEP_OUT(0),
    STEP_NEXT(1),
    STEP_IN(2),
    STEP_FRAME(3);
    
    int index;

    StepAction(int i) {
        this.index = i;
    }
}
