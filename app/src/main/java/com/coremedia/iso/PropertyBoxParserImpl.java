package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.UserBox;
import com.one.tomato.entity.p079db.UserChannelBean;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class PropertyBoxParserImpl extends AbstractBoxParser {
    static String[] EMPTY_STRING_ARRAY = new String[0];
    String clazzName;
    Properties mapping;
    String[] param;
    Pattern constuctorPattern = Pattern.compile("(.*)\\((.*?)\\)");
    StringBuilder buildLookupStrings = new StringBuilder();

    public PropertyBoxParserImpl(String... strArr) {
        InputStream resourceAsStream = PropertyBoxParserImpl.class.getResourceAsStream("/isoparser-default.properties");
        try {
            this.mapping = new Properties();
            try {
                this.mapping.load(resourceAsStream);
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                Enumeration<URL> resources = (contextClassLoader == null ? ClassLoader.getSystemClassLoader() : contextClassLoader).getResources("isoparser-custom.properties");
                while (resources.hasMoreElements()) {
                    resourceAsStream = resources.nextElement().openStream();
                    try {
                        this.mapping.load(resourceAsStream);
                        resourceAsStream.close();
                    } finally {
                        resourceAsStream.close();
                    }
                }
                for (String str : strArr) {
                    this.mapping.load(getClass().getResourceAsStream(str));
                }
                try {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            try {
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    public PropertyBoxParserImpl(Properties properties) {
        this.mapping = properties;
    }

    @Override // com.coremedia.iso.AbstractBoxParser
    public Box createBox(String str, byte[] bArr, String str2) {
        invoke(str, bArr, str2);
        try {
            Class<?> cls = Class.forName(this.clazzName);
            if (this.param.length > 0) {
                Class<?>[] clsArr = new Class[this.param.length];
                Object[] objArr = new Object[this.param.length];
                for (int i = 0; i < this.param.length; i++) {
                    if ("userType".equals(this.param[i])) {
                        objArr[i] = bArr;
                        clsArr[i] = byte[].class;
                    } else if ("type".equals(this.param[i])) {
                        objArr[i] = str;
                        clsArr[i] = String.class;
                    } else if ("parent".equals(this.param[i])) {
                        objArr[i] = str2;
                        clsArr[i] = String.class;
                    } else {
                        throw new InternalError("No such param: " + this.param[i]);
                    }
                }
                return (Box) cls.getConstructor(clsArr).newInstance(objArr);
            }
            return (Box) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException(e3);
        } catch (NoSuchMethodException e4) {
            throw new RuntimeException(e4);
        } catch (InvocationTargetException e5) {
            throw new RuntimeException(e5);
        }
    }

    public void invoke(String str, byte[] bArr, String str2) {
        String property;
        if (bArr != null) {
            if (!UserBox.TYPE.equals(str)) {
                throw new RuntimeException("we have a userType but no uuid box type. Something's wrong");
            }
            Properties properties = this.mapping;
            property = properties.getProperty("uuid[" + Hex.encodeHex(bArr).toUpperCase() + "]");
            if (property == null) {
                Properties properties2 = this.mapping;
                property = properties2.getProperty(str2 + "-uuid[" + Hex.encodeHex(bArr).toUpperCase() + "]");
            }
            if (property == null) {
                property = this.mapping.getProperty(UserBox.TYPE);
            }
        } else {
            property = this.mapping.getProperty(str);
            if (property == null) {
                StringBuilder sb = this.buildLookupStrings;
                sb.append(str2);
                sb.append('-');
                sb.append(str);
                String sb2 = sb.toString();
                this.buildLookupStrings.setLength(0);
                property = this.mapping.getProperty(sb2);
            }
        }
        if (property == null) {
            property = this.mapping.getProperty(UserChannelBean.TYPE_DEFAULT);
        }
        if (property == null) {
            throw new RuntimeException("No box object found for " + str);
        } else if (!property.endsWith(")")) {
            this.param = EMPTY_STRING_ARRAY;
            this.clazzName = property;
        } else {
            Matcher matcher = this.constuctorPattern.matcher(property);
            if (!matcher.matches()) {
                throw new RuntimeException("Cannot work with that constructor: " + property);
            }
            this.clazzName = matcher.group(1);
            if (matcher.group(2).length() == 0) {
                this.param = EMPTY_STRING_ARRAY;
            } else {
                this.param = matcher.group(2).length() > 0 ? matcher.group(2).split(",") : new String[0];
            }
        }
    }
}
