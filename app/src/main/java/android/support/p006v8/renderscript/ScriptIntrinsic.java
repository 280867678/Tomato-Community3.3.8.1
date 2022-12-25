package android.support.p006v8.renderscript;

/* renamed from: android.support.v8.renderscript.ScriptIntrinsic */
/* loaded from: classes2.dex */
public abstract class ScriptIntrinsic extends Script {
    /* JADX INFO: Access modifiers changed from: package-private */
    public ScriptIntrinsic(long j, RenderScript renderScript) {
        super(j, renderScript);
        if (j != 0) {
            return;
        }
        throw new RSRuntimeException("Loading of ScriptIntrinsic failed.");
    }
}
