package com.amazonaws.regions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Deprecated
/* loaded from: classes2.dex */
public class RegionMetadataParser {
    @Deprecated
    public List<Region> parseRegionMetadata(InputStream inputStream) throws IOException {
        return internalParse(inputStream, false);
    }

    private static List<Region> internalParse(InputStream inputStream, boolean z) throws IOException {
        try {
            try {
                Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
                NodeList elementsByTagName = parse.getElementsByTagName("Region");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < elementsByTagName.getLength(); i++) {
                    Node item = elementsByTagName.item(i);
                    if (item.getNodeType() == 1) {
                        arrayList.add(parseRegionElement((Element) item, z));
                    }
                }
                return arrayList;
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e2) {
            throw new IOException("Unable to parse region metadata file: " + e2.getMessage(), e2);
        }
    }

    private static Region parseRegionElement(Element element, boolean z) {
        Region region = new Region(getChildElementValue("Name", element), getChildElementValue("Domain", element));
        NodeList elementsByTagName = element.getElementsByTagName("Endpoint");
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            addRegionEndpoint(region, (Element) elementsByTagName.item(i), z);
        }
        return region;
    }

    private static void addRegionEndpoint(Region region, Element element, boolean z) {
        String childElementValue = getChildElementValue("ServiceName", element);
        String childElementValue2 = getChildElementValue("Hostname", element);
        String childElementValue3 = getChildElementValue("Http", element);
        String childElementValue4 = getChildElementValue("Https", element);
        if (z && !verifyLegacyEndpoint(childElementValue2)) {
            throw new IllegalStateException("Invalid service endpoint (" + childElementValue2 + ") is detected.");
        }
        region.getServiceEndpoints().put(childElementValue, childElementValue2);
        region.getHttpSupport().put(childElementValue, Boolean.valueOf("true".equals(childElementValue3)));
        region.getHttpsSupport().put(childElementValue, Boolean.valueOf("true".equals(childElementValue4)));
    }

    private static String getChildElementValue(String str, Element element) {
        Node item = element.getElementsByTagName(str).item(0);
        if (item == null) {
            return null;
        }
        return item.getChildNodes().item(0).getNodeValue();
    }

    private static boolean verifyLegacyEndpoint(String str) {
        return str.endsWith(".amazonaws.com");
    }
}
