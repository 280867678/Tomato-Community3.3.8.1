package kotlin.p143io;

import java.io.File;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Exceptions.kt */
/* renamed from: kotlin.io.FileSystemException */
/* loaded from: classes4.dex */
public class FileSystemException extends IOException {
    private final File file;
    private final File other;
    private final String reason;

    public final File getFile() {
        return this.file;
    }

    public final File getOther() {
        return this.other;
    }

    public /* synthetic */ FileSystemException(File file, File file2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? null : file2, (i & 4) != 0 ? null : str);
    }

    public final String getReason() {
        return this.reason;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FileSystemException(File file, File file2, String str) {
        super(r0);
        String constructMessage;
        Intrinsics.checkParameterIsNotNull(file, "file");
        constructMessage = ExceptionsKt.constructMessage(file, file2, str);
        this.file = file;
        this.other = file2;
        this.reason = str;
    }
}
