package sj.keyboard.utils.imageloader;

import android.widget.ImageView;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes4.dex */
public interface ImageBase {
    void displayImage(String str, ImageView imageView) throws IOException;

    /* loaded from: classes4.dex */
    public enum Scheme {
        HTTP("http"),
        HTTPS("https"),
        FILE("file"),
        CONTENT("content"),
        ASSETS("assets"),
        DRAWABLE("drawable"),
        UNKNOWN("");
        
        private String scheme;
        private String uriPrefix;

        Scheme(String str) {
            this.scheme = str;
            this.uriPrefix = str + "://";
        }

        public static Scheme ofUri(String str) {
            Scheme[] values;
            if (str != null) {
                for (Scheme scheme : values()) {
                    if (scheme.belongsTo(str)) {
                        return scheme;
                    }
                }
            }
            return UNKNOWN;
        }

        public String toUri(String str) {
            return this.uriPrefix + str;
        }

        public String crop(String str) {
            if (!belongsTo(str)) {
                throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", str, this.scheme));
            }
            return str.substring(this.uriPrefix.length());
        }

        protected boolean belongsTo(String str) {
            return str.toLowerCase(Locale.US).startsWith(this.uriPrefix);
        }

        public static String cropScheme(String str) throws IllegalArgumentException {
            return ofUri(str).crop(str);
        }
    }
}
