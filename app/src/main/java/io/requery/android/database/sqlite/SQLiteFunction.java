package io.requery.android.database.sqlite;

import io.requery.android.database.sqlite.SQLiteDatabase;

/* loaded from: classes4.dex */
public class SQLiteFunction {
    private final MyArgs args;
    public final SQLiteDatabase.Function callback;
    final int flags;
    public final String name;
    public final int numArgs;
    private final MyResult result;

    static native byte[] nativeGetArgBlob(long j, int i);

    static native double nativeGetArgDouble(long j, int i);

    static native int nativeGetArgInt(long j, int i);

    static native long nativeGetArgLong(long j, int i);

    static native String nativeGetArgString(long j, int i);

    static native void nativeSetResultBlob(long j, byte[] bArr);

    static native void nativeSetResultDouble(long j, double d);

    static native void nativeSetResultError(long j, String str);

    static native void nativeSetResultInt(long j, int i);

    static native void nativeSetResultLong(long j, long j2);

    static native void nativeSetResultNull(long j);

    static native void nativeSetResultString(long j, String str);

    public SQLiteFunction(String str, int i, SQLiteDatabase.Function function) {
        this(str, i, function, 0);
    }

    public SQLiteFunction(String str, int i, SQLiteDatabase.Function function, int i2) {
        this.args = new MyArgs();
        this.result = new MyResult();
        if (str == null) {
            throw new IllegalArgumentException("name must not be null.");
        }
        this.name = str;
        this.numArgs = i;
        this.callback = function;
        this.flags = i2;
    }

    private void dispatchCallback(long j, long j2, int i) {
        MyResult myResult = this.result;
        myResult.contextPtr = j;
        MyArgs myArgs = this.args;
        myArgs.argsPtr = j2;
        myArgs.argsCount = i;
        try {
            this.callback.callback(myArgs, myResult);
            if (!this.result.isSet) {
                this.result.setNull();
            }
        } finally {
            MyResult myResult2 = this.result;
            myResult2.contextPtr = 0L;
            myResult2.isSet = false;
            MyArgs myArgs2 = this.args;
            myArgs2.argsPtr = 0L;
            myArgs2.argsCount = 0;
        }
    }

    /* loaded from: classes4.dex */
    private static class MyArgs implements SQLiteDatabase.Function.Args {
        int argsCount;
        long argsPtr;

        private MyArgs() {
        }
    }

    /* loaded from: classes4.dex */
    private static class MyResult implements SQLiteDatabase.Function.Result {
        long contextPtr;
        boolean isSet;

        private MyResult() {
        }

        public void setNull() {
            checkSet();
            SQLiteFunction.nativeSetResultNull(this.contextPtr);
        }

        private void checkSet() {
            if (this.isSet) {
                throw new IllegalStateException("Result is already set");
            }
            this.isSet = true;
        }
    }
}
