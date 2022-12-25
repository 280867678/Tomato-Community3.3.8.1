package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.Utf8;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.util.Iso639;
import com.mp4parser.iso14496.part30.XMLSubtitleSampleEntry;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: classes3.dex */
public class SMPTETTTrackImpl extends AbstractTrack {
    public static final String SMPTE_TT_NAMESPACE = "http://www.smpte-ra.org/schemas/2052-1/2010/smpte-tt";
    boolean containsImages;
    private long[] sampleDurations;
    TrackMetaData trackMetaData = new TrackMetaData();
    SampleDescriptionBox sampleDescriptionBox = new SampleDescriptionBox();
    XMLSubtitleSampleEntry XMLSubtitleSampleEntry = new XMLSubtitleSampleEntry();
    List<Sample> samples = new ArrayList();
    SubSampleInformationBox subSampleInformationBox = new SubSampleInformationBox();

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public String getHandler() {
        return "subt";
    }

    static long toTime(String str) {
        Matcher matcher = Pattern.compile("([0-9][0-9]):([0-9][0-9]):([0-9][0-9])([\\.:][0-9][0-9]?[0-9]?)?").matcher(str);
        if (matcher.matches()) {
            String group = matcher.group(1);
            String group2 = matcher.group(2);
            String group3 = matcher.group(3);
            String group4 = matcher.group(4);
            if (group4 == null) {
                group4 = ".000";
            }
            String replace = group4.replace(":", ".");
            return (long) ((Long.parseLong(group) * 60 * 60 * 1000) + (Long.parseLong(group2) * 60 * 1000) + (Long.parseLong(group3) * 1000) + (Double.parseDouble("0" + replace) * 1000.0d));
        }
        throw new RuntimeException("Cannot match " + str + " to time expression");
    }

    public static String getLanguage(Document document) {
        return document.getDocumentElement().getAttribute("xml:lang");
    }

    public static long earliestTimestamp(Document document) {
        XPathFactory newInstance = XPathFactory.newInstance();
        TextTrackNamespaceContext textTrackNamespaceContext = new TextTrackNamespaceContext();
        XPath newXPath = newInstance.newXPath();
        newXPath.setNamespaceContext(textTrackNamespaceContext);
        try {
            NodeList nodeList = (NodeList) newXPath.compile("//*[@begin]").evaluate(document, XPathConstants.NODESET);
            long j = 0;
            for (int i = 0; i < nodeList.getLength(); i++) {
                j = Math.min(toTime(nodeList.item(i).getAttributes().getNamedItem("begin").getNodeValue()), j);
            }
            return j;
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public static long latestTimestamp(Document document) {
        long time;
        XPathFactory newInstance = XPathFactory.newInstance();
        TextTrackNamespaceContext textTrackNamespaceContext = new TextTrackNamespaceContext();
        XPath newXPath = newInstance.newXPath();
        newXPath.setNamespaceContext(textTrackNamespaceContext);
        try {
            NodeList nodeList = (NodeList) newXPath.compile("//*[@begin]").evaluate(document, XPathConstants.NODESET);
            long j = 0;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node item = nodeList.item(i);
                String nodeValue = item.getAttributes().getNamedItem("begin").getNodeValue();
                if (item.getAttributes().getNamedItem("dur") != null) {
                    time = toTime(nodeValue) + toTime(item.getAttributes().getNamedItem("dur").getNodeValue());
                } else if (item.getAttributes().getNamedItem("end") != null) {
                    time = toTime(item.getAttributes().getNamedItem("end").getNodeValue());
                } else {
                    throw new RuntimeException("neither end nor dur attribute is present");
                }
                j = Math.max(time, j);
            }
            return j;
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SMPTETTTrackImpl(File... fileArr) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        super(r1[0].getName());
        File[] fileArr2 = fileArr;
        this.sampleDurations = new long[fileArr2.length];
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        newInstance.setNamespaceAware(true);
        DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();
        C20991 c20991 = null;
        long j = 0;
        int i = 0;
        String str = null;
        while (i < fileArr2.length) {
            final File file = fileArr2[i];
            SubSampleInformationBox.SubSampleEntry subSampleEntry = new SubSampleInformationBox.SubSampleEntry();
            this.subSampleInformationBox.getEntries().add(subSampleEntry);
            subSampleEntry.setSampleDelta(1L);
            Document parse = newDocumentBuilder.parse(file);
            String language = getLanguage(parse);
            if (str == null) {
                str = language;
            } else if (!str.equals(language)) {
                throw new RuntimeException("Within one Track all sample documents need to have the same language");
            }
            XPathFactory newInstance2 = XPathFactory.newInstance();
            TextTrackNamespaceContext textTrackNamespaceContext = new TextTrackNamespaceContext();
            XPath newXPath = newInstance2.newXPath();
            newXPath.setNamespaceContext(textTrackNamespaceContext);
            long latestTimestamp = latestTimestamp(parse);
            this.sampleDurations[i] = latestTimestamp - j;
            NodeList nodeList = (NodeList) newXPath.compile("/ttml:tt/ttml:body/ttml:div/@smpte:backgroundImage").evaluate(parse, XPathConstants.NODESET);
            HashMap hashMap = new HashMap();
            HashSet hashSet = new HashSet();
            for (int i2 = 0; i2 < nodeList.getLength(); i2++) {
                hashSet.add(nodeList.item(i2).getNodeValue());
            }
            ArrayList<String> arrayList = new ArrayList(hashSet);
            Collections.sort(arrayList);
            int i3 = 1;
            for (String str2 : arrayList) {
                hashMap.put(str2, "urn:dece:container:subtitleimageindex:" + i3 + str2.substring(str2.lastIndexOf(".")));
                i3++;
            }
            if (!arrayList.isEmpty()) {
                final String str3 = new String(streamToByteArray(new FileInputStream(file)));
                for (Map.Entry entry : hashMap.entrySet()) {
                    str3 = str3.replace((CharSequence) entry.getKey(), (CharSequence) entry.getValue());
                }
                final ArrayList arrayList2 = new ArrayList();
                this.samples.add(new Sample() { // from class: com.googlecode.mp4parser.authoring.tracks.SMPTETTTrackImpl.1
                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public void writeTo(WritableByteChannel writableByteChannel) throws IOException {
                        writableByteChannel.write(ByteBuffer.wrap(Utf8.convert(str3)));
                        for (File file2 : arrayList2) {
                            FileInputStream fileInputStream = new FileInputStream(file2);
                            byte[] bArr = new byte[8096];
                            while (true) {
                                int read = fileInputStream.read(bArr);
                                if (-1 != read) {
                                    writableByteChannel.write(ByteBuffer.wrap(bArr, 0, read));
                                }
                            }
                        }
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public long getSize() {
                        long length = Utf8.convert(str3).length;
                        for (File file2 : arrayList2) {
                            length += file2.length();
                        }
                        return length;
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public ByteBuffer asByteBuffer() {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        try {
                            writeTo(Channels.newChannel(byteArrayOutputStream));
                            return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                SubSampleInformationBox.SubSampleEntry.SubsampleEntry subsampleEntry = new SubSampleInformationBox.SubSampleEntry.SubsampleEntry();
                subsampleEntry.setSubsampleSize(Utf8.utf8StringLengthInBytes(str3));
                subSampleEntry.getSubsampleEntries().add(subsampleEntry);
                for (String str4 : arrayList) {
                    File file2 = new File(file.getParentFile(), str4);
                    arrayList2.add(file2);
                    SubSampleInformationBox.SubSampleEntry.SubsampleEntry subsampleEntry2 = new SubSampleInformationBox.SubSampleEntry.SubsampleEntry();
                    subsampleEntry2.setSubsampleSize(file2.length());
                    subSampleEntry.getSubsampleEntries().add(subsampleEntry2);
                }
            } else {
                this.samples.add(new Sample() { // from class: com.googlecode.mp4parser.authoring.tracks.SMPTETTTrackImpl.2
                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public void writeTo(WritableByteChannel writableByteChannel) throws IOException {
                        Channels.newOutputStream(writableByteChannel).write(SMPTETTTrackImpl.this.streamToByteArray(new FileInputStream(file)));
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public long getSize() {
                        return file.length();
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public ByteBuffer asByteBuffer() {
                        try {
                            return ByteBuffer.wrap(SMPTETTTrackImpl.this.streamToByteArray(new FileInputStream(file)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            i++;
            fileArr2 = fileArr;
            j = latestTimestamp;
            c20991 = null;
        }
        this.trackMetaData.setLanguage(Iso639.convert2to3(str));
        this.XMLSubtitleSampleEntry.setNamespace(SMPTE_TT_NAMESPACE);
        this.XMLSubtitleSampleEntry.setSchemaLocation(SMPTE_TT_NAMESPACE);
        if (this.containsImages) {
            this.XMLSubtitleSampleEntry.setAuxiliaryMimeTypes("image/png");
        } else {
            this.XMLSubtitleSampleEntry.setAuxiliaryMimeTypes("");
        }
        this.sampleDescriptionBox.addBox(this.XMLSubtitleSampleEntry);
        this.trackMetaData.setTimescale(30000L);
        this.trackMetaData.setLayer(65535);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] streamToByteArray(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 != read) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public long[] getSampleDurations() {
        long[] jArr = new long[this.sampleDurations.length];
        for (int i = 0; i < jArr.length; i++) {
            jArr[i] = (this.sampleDurations[i] * this.trackMetaData.getTimescale()) / 1000;
        }
        return jArr;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public List<Sample> getSamples() {
        return this.samples;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.subSampleInformationBox;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class TextTrackNamespaceContext implements NamespaceContext {
        private TextTrackNamespaceContext() {
        }

        @Override // javax.xml.namespace.NamespaceContext
        public String getNamespaceURI(String str) {
            if (str.equals("ttml")) {
                return "http://www.w3.org/ns/ttml";
            }
            if (!str.equals("smpte")) {
                return null;
            }
            return SMPTETTTrackImpl.SMPTE_TT_NAMESPACE;
        }

        @Override // javax.xml.namespace.NamespaceContext
        public Iterator getPrefixes(String str) {
            return Arrays.asList("ttml", "smpte").iterator();
        }

        @Override // javax.xml.namespace.NamespaceContext
        public String getPrefix(String str) {
            if (str.equals("http://www.w3.org/ns/ttml")) {
                return "ttml";
            }
            if (!str.equals(SMPTETTTrackImpl.SMPTE_TT_NAMESPACE)) {
                return null;
            }
            return "smpte";
        }
    }
}
