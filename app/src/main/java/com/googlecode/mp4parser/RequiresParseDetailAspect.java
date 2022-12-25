package com.googlecode.mp4parser;

/* loaded from: classes3.dex */
public class RequiresParseDetailAspect {
    public void before(Object obj) {
        if (obj instanceof AbstractBox) {
            AbstractBox abstractBox = (AbstractBox) obj;
            if (abstractBox.isParsed()) {
                return;
            }
            abstractBox.parseDetails();
            return;
        }
        throw new RuntimeException("Only methods in subclasses of " + AbstractBox.class.getName() + " can  be annotated with ParseDetail");
    }
}
