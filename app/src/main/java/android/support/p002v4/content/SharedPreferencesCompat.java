package android.support.p002v4.content;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@Deprecated
/* renamed from: android.support.v4.content.SharedPreferencesCompat */
/* loaded from: classes2.dex */
public final class SharedPreferencesCompat {

    @Deprecated
    /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat */
    /* loaded from: classes2.dex */
    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper = new Helper();

        /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat$Helper */
        /* loaded from: classes2.dex */
        private static class Helper {
            Helper() {
            }

            public void apply(@NonNull SharedPreferences.Editor editor) {
                try {
                    editor.apply();
                } catch (AbstractMethodError unused) {
                    editor.commit();
                }
            }
        }

        private EditorCompat() {
        }

        @Deprecated
        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        @Deprecated
        public void apply(@NonNull SharedPreferences.Editor editor) {
            this.mHelper.apply(editor);
        }
    }

    private SharedPreferencesCompat() {
    }
}
