package android.support.p006v8.renderscript;

import android.os.Build;
import android.support.p006v8.renderscript.Script;

/* renamed from: android.support.v8.renderscript.ScriptIntrinsicConvolve5x5 */
/* loaded from: classes2.dex */
public class ScriptIntrinsicConvolve5x5 extends ScriptIntrinsic {
    private static final int INTRINSIC_API_LEVEL = 19;
    private Allocation mInput;
    private final float[] mValues = new float[25];

    ScriptIntrinsicConvolve5x5(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicConvolve5x5 create(RenderScript renderScript, Element element) {
        if (!element.isCompatible(Element.m5581U8(renderScript)) && !element.isCompatible(Element.U8_2(renderScript)) && !element.isCompatible(Element.U8_3(renderScript)) && !element.isCompatible(Element.U8_4(renderScript)) && !element.isCompatible(Element.F32(renderScript)) && !element.isCompatible(Element.F32_2(renderScript)) && !element.isCompatible(Element.F32_3(renderScript)) && !element.isCompatible(Element.F32_4(renderScript))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        boolean z = renderScript.isUseNative() && Build.VERSION.SDK_INT < 19;
        ScriptIntrinsicConvolve5x5 scriptIntrinsicConvolve5x5 = new ScriptIntrinsicConvolve5x5(renderScript.nScriptIntrinsicCreate(4, element.getID(renderScript), z), renderScript);
        scriptIntrinsicConvolve5x5.setIncSupp(z);
        return scriptIntrinsicConvolve5x5;
    }

    public void setInput(Allocation allocation) {
        this.mInput = allocation;
        setVar(1, allocation);
    }

    public void setCoefficients(float[] fArr) {
        FieldPacker fieldPacker = new FieldPacker(100);
        int i = 0;
        while (true) {
            float[] fArr2 = this.mValues;
            if (i < fArr2.length) {
                fArr2[i] = fArr[i];
                fieldPacker.addF32(fArr2[i]);
                i++;
            } else {
                setVar(0, fieldPacker);
                return;
            }
        }
    }

    public void forEach(Allocation allocation) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null);
    }

    public void forEach(Allocation allocation, Script.LaunchOptions launchOptions) {
        forEach(0, (Allocation) null, allocation, (FieldPacker) null, launchOptions);
    }

    public Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
