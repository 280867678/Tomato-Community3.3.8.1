package com.googlecode.mp4parser.boxes.mp4;

import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BaseDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ObjectDescriptorFactory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes3.dex */
public class AbstractDescriptorBox extends AbstractFullBox {
    private static Logger log = Logger.getLogger(AbstractDescriptorBox.class.getName());
    protected ByteBuffer data;
    protected BaseDescriptor descriptor;

    public AbstractDescriptorBox(String str) {
        super(str);
    }

    public ByteBuffer getData() {
        return this.data;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        this.data.rewind();
        byteBuffer.put(this.data);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return this.data.limit() + 4;
    }

    public BaseDescriptor getDescriptor() {
        return this.descriptor;
    }

    public String getDescriptorAsString() {
        return this.descriptor.toString();
    }

    public void setDescriptor(BaseDescriptor baseDescriptor) {
        this.descriptor = baseDescriptor;
    }

    public void setData(ByteBuffer byteBuffer) {
        this.data = byteBuffer;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.data = byteBuffer.slice();
        byteBuffer.position(byteBuffer.position() + byteBuffer.remaining());
        try {
            this.data.rewind();
            this.descriptor = ObjectDescriptorFactory.createFrom(-1, this.data);
        } catch (IOException e) {
            log.log(Level.WARNING, "Error parsing ObjectDescriptor", (Throwable) e);
        } catch (IndexOutOfBoundsException e2) {
            log.log(Level.WARNING, "Error parsing ObjectDescriptor", (Throwable) e2);
        }
    }
}
