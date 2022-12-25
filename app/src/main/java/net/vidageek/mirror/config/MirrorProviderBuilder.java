package net.vidageek.mirror.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.provider.java.DefaultMirrorReflectionProvider;

/* loaded from: classes4.dex */
public final class MirrorProviderBuilder {
    private final InputStream configurationFile;

    public MirrorProviderBuilder(InputStream inputStream) {
        this.configurationFile = inputStream;
    }

    public ReflectionProvider createProvider() {
        InputStream inputStream = this.configurationFile;
        if (inputStream == null) {
            return new DefaultMirrorReflectionProvider();
        }
        return (ReflectionProvider) new Mirror(new DefaultMirrorReflectionProvider()).m76on(processProperties(inputStream).get(Item.REFLECTION_PROVIDER)).invoke().constructor().withoutArgs();
    }

    private Map<Item, String> processProperties(InputStream inputStream) {
        Item[] values;
        HashMap hashMap = new HashMap();
        hashMap.put(Item.REFLECTION_PROVIDER, DefaultMirrorReflectionProvider.class.getName());
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            for (Item item : Item.values()) {
                if (properties.containsKey(item.getPropertyKey())) {
                    hashMap.put(item, properties.getProperty(item.getPropertyKey()).trim());
                }
            }
            return hashMap;
        } catch (IOException e) {
            throw new MirrorException("could not ready file " + inputStream, e);
        }
    }
}
