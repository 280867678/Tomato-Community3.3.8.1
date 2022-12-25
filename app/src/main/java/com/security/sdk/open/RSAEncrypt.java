package com.security.sdk.open;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes3.dex */
public class RSAEncrypt {
    public static final String DEVICE_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDAEBUGPDtCuqTB\nQPdo4Ftrrq+BxisBbJr6wVhfYRnMQt0IpKrcL1vtNFbRlRyHssdPhUj8ZjGwuWEV\nnREZ+Xfg/+nlKbx0dwSnoWawEqDmx2nKhQ4H3zjqLP+nO2/ycWvpQreiSV98+wUk\n0TpL13+ZQZNcT8oOF726maSxCkLLe8BTvFYOms3u3rpqSG9kX9BJzCPfq5Xt/f45\nJdi25UYSO5p/W5LifoG02PPgT/vyUvqoF5PmhPfgkBcgodLE3K/0dW8QUrA3fOGH\nOy1aJ0AVQEvpJs7cH2PulZCMSFyj9byH7/1X/jdaPWKEbXcEfiPmPvC4LyK6SU2g\ny/TKVXAfAgMBAAECggEBAJW68+kMg9ad01ul3juwzRTB9qlhaJ2PobLjkJWrCYWo\nitD2Lw3BDhV6vERfzwIrah6gdinvMcXof7tt26s4RTDv0dSKGRVixAo7VebmXvIL\nfHoaCLoyWBiYRCaHu9pyegI7tRWbxgmVWIk/ZAdEVYGREeThf1BFSfBzX2V17mTb\nFo2v+zjpY5lDEarSsb0bT7UKv/ettvfkzX9Ec1gzX5C8Ou5HoZQruVWvhi4sQNbi\nCt7Js6mqRgvtplkcBNr2xrC15dduZDRQlEW3Cxw3hsOAGH/hCnA2CVlAQCfTZdFI\nFalNlmEQ5c5qJc/METy70wuZrUjHJNn+jQGGmNwf+4ECgYEA7ln+8n2qnuV/Bsie\nmWjSgQTRYrWO8Sqn5qGOq0dITMRX41O7JNDrqftmjSu2pa4OgDRLn1qI5yPiAb+c\naUr6FBdgk/xKcs+Q2d/iDzkdY+FBfJPbpEWub8vOvROALI88YHJ6SVGeCTgJOWgl\nHPdXCswjo18l+cFVPd8Sgi6d/tECgYEAzkisctqcTeNww1XNNSoVu7yKF4O2ZQpj\nJxrB6iF5ik9A36nMzeRKpJHHZrJsFiK+C8ZT8yrFF0KA+huQEx55Zg17Cd7MecJ9\nNpTK2n111UDZIWLqJm7BULwXkcC3SLCAzkH6OW7cFlOrFx9wnIwyBa8TyCeRvXCg\n+KXJlv3Mm+8CgYATQAd8NDQMg0KAHMHaaAHgU7BJhZpW1fgbg17PbIFlWdIzVPbf\nYVQ3NUuyCR7GQMmBBzg4VYqLCHXfaEdMpbaO2th8+zpUDXmIbzGFwzohb/bnBdYO\nbWZKNy6qTKHkd2kL7RL9Su81lWYBUez1avCHubcDIdMiWb/1sEeBZhxykQKBgFzr\njYqbAMdxCS1kK/CjyXBsqEOZWALBoBJQUkM12vq7Ynydjxy7lgf1hr2y5089TSAe\nsVgazvcARPrkiYyjMIoQzniOvMqoGiEI+ytaUkbCVExesxymzTuhznggOC9tGkhO\nnfla/5arD6l1xxMtb6bxy1ZgxJxx7IC0pU73ekBPAoGAG8J5yWcgfcQ0EGv5REZy\nc3BhAM95PqfQkFdXVJf4rbboIkImwMgexYFragsEKzOycnYGS3GG4beYKwuDoOmM\nExNA4vX6dYReeqtqchwcDpJCoyugQwzOHaRRPHoFnSULd8oZ6A20gVGvHhAdLvJD\ni5P/bYQsa90JiBJ7etYHAso=\n";
    public static final String DEVICE_PUBLIC_KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAl4b5N5HSH+uzMEeCg317\n5zJ6dOqEw1poLnn0g4M5m8u+e89z45H1/6yNi5MNvmTwDaqcxkrW0HLvpSJpNaA3\nzaT/UnGw9o6eOKsi0p46aVBmPzx63CclO4DMDhNtIVs4VPctFe2d052eLOnUdNmR\nkdXcKuy9o4fmFrWfWVCOujiqkUTxNcbjJ6+1Yrg83xVmgJ2eeJsUiQCeFvpyMP9n\niIW0eaVkcLi/nH7//nwziX4o8ck1eL9L/3PnL6fC/4f2uwmZxpv/STvVbZ52uTr8\nkLD2ugs3ZKI9fEQQlQugPwMpGtIoZ0fyyo75UCy5Ko2eD0xJjMAz7FAwnYRzTr9+\nFRw1p+tGtFrHJvkqG1SsmxJxr0gzmfj5iw6oIGudoEUFWtEmLXP4Tc9AIrFgib+k\new4UCrZDkZ4E5Xj7YnWW/jSx6e1SP613BLZcN4C9fEKvMgDUqa/pnPCvpf0QuyZu\nfVD5roznTD5oD4IgChE5CJHpG4964qdqVKGlRD3v7bu9JpKUFdHNjdyT2t4JskAr\nlcLgBx2LcEzaA0W2e2fTdIDgf2kjMFy59309QauXae4uvXN0veKyPkQ7+iiHJB8f\nyk8HSJrCu/QqNJ8YrjhaczlDM0zJr2E9PSg5jftrGbI8rnSSFEQ2EnCUdwgE/cvI\nk1tRNPzhe8Gg25GTOS/MsvECAwEAAQ==\n";
    private static final int MAX_DECRYPT_BLOCK = 256;
    private static final int MAX_ENCRYPT_BLOCK = 245;

    public static String decrypt(String str) {
        try {
            return decrypt(str, getPrivateKey(DEVICE_PRIVATE_KEY));
        } catch (Exception unused) {
            return str;
        }
    }

    public static String decrypt(String str, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, privateKey);
            byte[] decode = Base64.decode(str, 2);
            int length = decode.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = length - i;
                if (i3 <= 0) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return new String(byteArray);
                }
                byte[] doFinal = i3 > 256 ? cipher.doFinal(decode, i, 256) : cipher.doFinal(decode, i, i3);
                byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                i2++;
                i = i2 * 256;
            }
        } catch (Exception unused) {
            return str;
        }
    }

    public static String encrypt(String str) {
        try {
            return encrypt(str, getPublicKey(DEVICE_PUBLIC_KEY));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encrypt(String str, PublicKey publicKey) {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, publicKey);
        byte[] bytes = str.getBytes("UTF-8");
        int length = bytes.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = length - i;
            if (i3 <= 0) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return new String(Base64.encode(byteArray, 2));
            }
            byte[] doFinal = i3 > MAX_ENCRYPT_BLOCK ? cipher.doFinal(bytes, i, MAX_ENCRYPT_BLOCK) : cipher.doFinal(bytes, i, i3);
            byteArrayOutputStream.write(doFinal, 0, doFinal.length);
            i2++;
            i = i2 * MAX_ENCRYPT_BLOCK;
        }
    }

    public static PrivateKey getPrivateKey(String str) {
        return KeyFactory.getInstance("RSA", "BC").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str, 2)));
    }

    public static PublicKey getPublicKey(String str) {
        return KeyFactory.getInstance("RSA", "BC").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 2)));
    }
}
