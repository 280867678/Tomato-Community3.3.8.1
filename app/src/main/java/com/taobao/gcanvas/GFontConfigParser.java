package com.taobao.gcanvas;

import android.os.Build;
import android.util.Log;
import com.taobao.gcanvas.util.GLog;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes3.dex */
public class GFontConfigParser {
    private boolean mIsInitialized;
    private HashMap<List<String>, List<String>> mFontFamilies = null;
    private List<String> mFallbackFonts = null;

    public String getSystemFontLocation() {
        return "/system/fonts/";
    }

    public GFontConfigParser() {
        this.mIsInitialized = false;
        if (!this.mIsInitialized) {
            readConfigFile();
            readFallbackConfigFile();
            this.mIsInitialized = true;
        }
    }

    public HashMap<List<String>, List<String>> getFontFamilies() {
        return this.mFontFamilies;
    }

    public List<String> getFallbackFontsList() {
        return this.mFallbackFonts;
    }

    public String getFallbackFont() {
        return new File("/system/fonts/DroidSansFallbackBBK.ttf").exists() ? "DroidSansFallbackBBK.ttf" : new File("/system/fonts/NotoSansHans-Regular.otf").exists() ? "NotoSansHans-Regular.otf" : new File("/system/fonts/NotoSansSC-Regular.otf").exists() ? "NotoSansSC-Regular.otf" : new File("/system/fonts/NotoSansCJK-Regular.ttc").exists() ? "NotoSansCJK-Regular.ttc" : new File("/system/fonts/DroidSansFallback.ttf").exists() ? "DroidSansFallback.ttf" : "DroidSans.ttf";
    }

    private void readFallbackConfigFile() {
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            File file = new File("/system/etc/fallback_fonts.xml");
            if (!file.exists()) {
                return;
            }
            parseFallbackXML(newDocumentBuilder.parse(file));
        } catch (Exception e) {
            Log.e("GFontConfigParser", "readFallbackConfigFile " + e.getMessage());
        }
    }

    private void readConfigFile() {
        Document parse;
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            if (Build.VERSION.SDK_INT >= 21) {
                parse = newDocumentBuilder.parse(new File("/system/etc/fonts.xml"));
            } else {
                parse = newDocumentBuilder.parse(new File("/system/etc/system_fonts.xml"));
            }
            parseXML(parse);
        } catch (Exception e) {
            GLog.m3563e("GFontConfigParser", "readConfigFile exception:" + e.getMessage());
        }
    }

    private void parseXML(Document document) throws Exception {
        Node node;
        NodeList nodeList;
        HashMap<List<String>, List<String>> hashMap = this.mFontFamilies;
        if (hashMap == null) {
            this.mFontFamilies = new HashMap<>();
        } else {
            hashMap.clear();
        }
        Element documentElement = document.getDocumentElement();
        if (!documentElement.getTagName().equals("familyset")) {
            GLog.m3561w("GFontConfigParser", "Can't find familyset.");
        } else if (Build.VERSION.SDK_INT >= 21) {
            NodeList elementsByTagName = documentElement.getElementsByTagName("family");
            int length = elementsByTagName.getLength();
            int i = 0;
            while (i < length) {
                Node item = elementsByTagName.item(i);
                item.getAttributes();
                Node namedItem = item.getAttributes().getNamedItem("name");
                if (namedItem == null) {
                    return;
                }
                NodeList elementsByTagName2 = ((Element) item).getElementsByTagName("font");
                if (elementsByTagName2 == null) {
                    GLog.m3561w("GFontConfigParser", "nameset or fileset is invalid.");
                    return;
                }
                int length2 = elementsByTagName2.getLength();
                ArrayList<String> arrayList = new ArrayList();
                arrayList.add(namedItem.getNodeValue());
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < length2; i2++) {
                    Node item2 = elementsByTagName2.item(i2);
                    if (item2 instanceof Element) {
                        arrayList2.add(((Element) item2).getTextContent());
                    }
                }
                NodeList elementsByTagName3 = documentElement.getElementsByTagName("alias");
                int length3 = elementsByTagName3.getLength();
                for (int i3 = 0; i3 < length3; i3++) {
                    Node item3 = elementsByTagName3.item(i3);
                    Node namedItem2 = item3.getAttributes().getNamedItem("name");
                    Node namedItem3 = item3.getAttributes().getNamedItem("to");
                    if (namedItem2 != null && namedItem3 != null) {
                        ArrayList arrayList3 = new ArrayList();
                        for (String str : arrayList) {
                            NodeList nodeList2 = elementsByTagName;
                            int i4 = length;
                            if (str.equals(namedItem3.getNodeValue())) {
                                arrayList3.add(namedItem2.getNodeValue());
                            }
                            length = i4;
                            elementsByTagName = nodeList2;
                        }
                        arrayList.addAll(arrayList3);
                    }
                    this.mFontFamilies.put(arrayList, arrayList2);
                    i++;
                    length = length;
                    elementsByTagName = elementsByTagName;
                }
                this.mFontFamilies.put(arrayList, arrayList2);
                i++;
                length = length;
                elementsByTagName = elementsByTagName;
            }
        } else {
            NodeList elementsByTagName4 = documentElement.getElementsByTagName("family");
            int length4 = elementsByTagName4.getLength();
            int i5 = 0;
            while (i5 < length4) {
                Node item4 = elementsByTagName4.item(i5);
                Node node2 = null;
                if (item4 instanceof Element) {
                    Element element = (Element) item4;
                    NodeList elementsByTagName5 = element.getElementsByTagName("nameset");
                    NodeList elementsByTagName6 = element.getElementsByTagName("fileset");
                    if (elementsByTagName5 == null || elementsByTagName6 == null || elementsByTagName5.getLength() != 1 || elementsByTagName6.getLength() != 1) {
                        GLog.m3561w("GFontConfigParser", "nameset or fileset node doesn't exist.");
                        return;
                    } else {
                        node2 = elementsByTagName5.item(0);
                        node = elementsByTagName6.item(0);
                    }
                } else {
                    node = null;
                }
                if (node2 == null || node == null) {
                    GLog.m3561w("GFontConfigParser", "nameset or fileset is invalid.");
                    return;
                }
                NodeList childNodes = node2.getChildNodes();
                NodeList childNodes2 = node.getChildNodes();
                if (childNodes == null || childNodes2 == null) {
                    GLog.m3561w("GFontConfigParser", "nameset or fileset is empty.");
                    return;
                }
                int length5 = childNodes.getLength();
                int length6 = childNodes2.getLength();
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                int i6 = 0;
                while (i6 < length6) {
                    Node item5 = childNodes2.item(i6);
                    if (item5 instanceof Element) {
                        Element element2 = (Element) item5;
                        nodeList = elementsByTagName4;
                        if (element2.getTagName().equals("file")) {
                            arrayList5.add(element2.getTextContent());
                        }
                    } else {
                        nodeList = elementsByTagName4;
                    }
                    i6++;
                    elementsByTagName4 = nodeList;
                }
                NodeList nodeList3 = elementsByTagName4;
                for (int i7 = 0; i7 < length5; i7++) {
                    Node item6 = childNodes.item(i7);
                    if (item6 instanceof Element) {
                        Element element3 = (Element) item6;
                        if (element3.getTagName().equals("name")) {
                            arrayList4.add(element3.getTextContent());
                        }
                    }
                }
                this.mFontFamilies.put(arrayList4, arrayList5);
                i5++;
                elementsByTagName4 = nodeList3;
            }
        }
    }

    private void parseFallbackXML(Document document) throws Exception {
        List<String> list = this.mFallbackFonts;
        if (list == null) {
            this.mFallbackFonts = new ArrayList();
        } else {
            list.clear();
        }
        Element documentElement = document.getDocumentElement();
        if (!documentElement.getTagName().equals("familyset")) {
            GLog.m3561w("GFontConfigParser", "Can't find familyset.");
            return;
        }
        NodeList elementsByTagName = documentElement.getElementsByTagName("family");
        int length = elementsByTagName.getLength();
        for (int i = 0; i < length; i++) {
            Node item = elementsByTagName.item(i);
            Node node = null;
            if (item instanceof Element) {
                NodeList elementsByTagName2 = ((Element) item).getElementsByTagName("fileset");
                if (elementsByTagName2 == null || elementsByTagName2.getLength() != 1) {
                    GLog.m3561w("GFontConfigParser", "nameset or fileset node doesn't exist.");
                    return;
                }
                node = elementsByTagName2.item(0);
            }
            if (node == null) {
                GLog.m3561w("GFontConfigParser", "nameset or fileset is invalid.");
                return;
            }
            NodeList childNodes = node.getChildNodes();
            if (childNodes == null) {
                GLog.m3561w("GFontConfigParser", "nameset or fileset is empty.");
                return;
            }
            int length2 = childNodes.getLength();
            for (int i2 = 0; i2 < length2; i2++) {
                Node item2 = childNodes.item(i2);
                if (item2 instanceof Element) {
                    Element element = (Element) item2;
                    if (element.getTagName().equals("file")) {
                        this.mFallbackFonts.add(element.getTextContent());
                    }
                }
            }
        }
    }
}
