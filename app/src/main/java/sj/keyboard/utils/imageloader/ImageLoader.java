package sj.keyboard.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;
import sj.keyboard.utils.imageloader.ImageBase;

/* loaded from: classes4.dex */
public class ImageLoader implements ImageBase {
    private static volatile Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
    private static volatile ImageLoader instance;
    protected final Context context;

    protected void displayImageFromContent(String str, ImageView imageView) throws FileNotFoundException {
    }

    protected void displayImageFromNetwork(String str, Object obj) throws IOException {
    }

    protected void displayImageFromOtherSource(String str, ImageView imageView) throws IOException {
    }

    public static ImageLoader getInstance(Context context) {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader(context);
                }
            }
        }
        return instance;
    }

    public ImageLoader(Context context) {
        this.context = context.getApplicationContext();
    }

    /* renamed from: sj.keyboard.utils.imageloader.ImageLoader$1 */
    /* loaded from: classes4.dex */
    static /* synthetic */ class C55741 {
        static final /* synthetic */ int[] $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme = new int[ImageBase.Scheme.values().length];

        static {
            try {
                $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.FILE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.ASSETS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.DRAWABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.HTTP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.HTTPS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.CONTENT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // sj.keyboard.utils.imageloader.ImageBase
    public void displayImage(String str, ImageView imageView) throws IOException {
        switch (C55741.$SwitchMap$sj$keyboard$utils$imageloader$ImageBase$Scheme[ImageBase.Scheme.ofUri(str).ordinal()]) {
            case 1:
                displayImageFromFile(str, imageView);
                return;
            case 2:
                displayImageFromAssets(str, imageView);
                return;
            case 3:
                displayImageFromDrawable(str, imageView);
                return;
            case 4:
            case 5:
                displayImageFromNetwork(str, imageView);
                return;
            case 6:
                displayImageFromContent(str, imageView);
                return;
            default:
                if (NUMBER_PATTERN.matcher(str).matches()) {
                    displayImageFromResource(Integer.parseInt(str), imageView);
                    return;
                } else {
                    displayImageFromOtherSource(str, imageView);
                    return;
                }
        }
    }

    protected void displayImageFromFile(String str, ImageView imageView) throws IOException {
        String crop = ImageBase.Scheme.FILE.crop(str);
        if (!new File(crop).exists()) {
            return;
        }
        try {
            Bitmap decodeFile = BitmapFactory.decodeFile(crop);
            if (imageView == null) {
                return;
            }
            imageView.setImageBitmap(decodeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void displayImageFromAssets(String str, ImageView imageView) throws IOException {
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(this.context.getAssets().open(ImageBase.Scheme.ASSETS.crop(str)));
            if (imageView == null) {
                return;
            }
            imageView.setImageBitmap(decodeStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void displayImageFromDrawable(String str, ImageView imageView) {
        String crop = ImageBase.Scheme.DRAWABLE.crop(str);
        int identifier = this.context.getResources().getIdentifier(crop, "mipmap", this.context.getPackageName());
        if (identifier <= 0) {
            identifier = this.context.getResources().getIdentifier(crop, "drawable", this.context.getPackageName());
        }
        if (identifier <= 0 || imageView == null) {
            return;
        }
        imageView.setImageResource(identifier);
    }

    protected void displayImageFromResource(int i, ImageView imageView) {
        if (i <= 0 || imageView == null) {
            return;
        }
        imageView.setImageResource(i);
    }
}
