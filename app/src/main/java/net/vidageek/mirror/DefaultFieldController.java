package net.vidageek.mirror;

import java.lang.reflect.Field;
import net.vidageek.mirror.dsl.FieldController;
import net.vidageek.mirror.provider.ReflectionProvider;
import net.vidageek.mirror.reflect.DefaultAllMemberHandler;
import net.vidageek.mirror.reflect.DefaultFieldHandler;
import net.vidageek.mirror.reflect.dsl.AllMemberHandler;
import net.vidageek.mirror.reflect.dsl.FieldHandler;

/* loaded from: classes4.dex */
public class DefaultFieldController implements FieldController {
    private final Field field;
    private final ReflectionProvider provider;

    public DefaultFieldController(ReflectionProvider reflectionProvider, Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Argument field cannot be null.");
        }
        this.provider = reflectionProvider;
        this.field = field;
    }

    @Override // net.vidageek.mirror.dsl.FieldController
    public FieldHandler reflect() {
        return new DefaultFieldHandler(this.provider, this.field);
    }

    @Override // net.vidageek.mirror.dsl.FieldController
    public AllMemberHandler reflectAll() {
        return new DefaultAllMemberHandler(this.provider, this.field);
    }
}
