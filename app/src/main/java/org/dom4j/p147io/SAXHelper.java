package org.dom4j.p147io;

import java.io.PrintStream;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/* renamed from: org.dom4j.io.SAXHelper */
/* loaded from: classes4.dex */
class SAXHelper {
    private static boolean loggedWarning = true;

    protected SAXHelper() {
    }

    public static boolean setParserProperty(XMLReader xMLReader, String str, Object obj) {
        try {
            xMLReader.setProperty(str, obj);
            return true;
        } catch (SAXNotRecognizedException | SAXNotSupportedException unused) {
            return false;
        }
    }

    public static boolean setParserFeature(XMLReader xMLReader, String str, boolean z) {
        try {
            xMLReader.setFeature(str, z);
            return true;
        } catch (SAXNotRecognizedException | SAXNotSupportedException unused) {
            return false;
        }
    }

    public static XMLReader createXMLReader(boolean z) throws SAXException {
        XMLReader createXMLReaderViaJAXP = createXMLReaderViaJAXP(z, true);
        if (createXMLReaderViaJAXP == null) {
            try {
                createXMLReaderViaJAXP = XMLReaderFactory.createXMLReader();
            } catch (Exception e) {
                if (isVerboseErrorReporting()) {
                    System.out.println("Warning: Caught exception attempting to use SAX to load a SAX XMLReader ");
                    PrintStream printStream = System.out;
                    printStream.println("Warning: Exception was: " + e);
                    System.out.println("Warning: I will print the stack trace then carry on using the default SAX parser");
                    e.printStackTrace();
                }
                throw new SAXException(e);
            }
        }
        if (createXMLReaderViaJAXP == null) {
            throw new SAXException("Couldn't create SAX reader");
        }
        setParserFeature(createXMLReaderViaJAXP, "http://xml.org/sax/features/namespaces", true);
        setParserFeature(createXMLReaderViaJAXP, "http://xml.org/sax/features/namespace-prefixes", false);
        setParserFeature(createXMLReaderViaJAXP, "http://xml.org/sax/properties/external-general-entities", false);
        setParserFeature(createXMLReaderViaJAXP, "http://xml.org/sax/properties/external-parameter-entities", false);
        setParserFeature(createXMLReaderViaJAXP, "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        setParserFeature(createXMLReaderViaJAXP, "http://xml.org/sax/features/use-locator2", true);
        return createXMLReaderViaJAXP;
    }

    protected static XMLReader createXMLReaderViaJAXP(boolean z, boolean z2) {
        try {
            return JAXPHelper.createXMLReader(z, z2);
        } catch (Throwable th) {
            if (loggedWarning) {
                return null;
            }
            loggedWarning = true;
            if (!isVerboseErrorReporting()) {
                return null;
            }
            System.out.println("Warning: Caught exception attempting to use JAXP to load a SAX XMLReader");
            PrintStream printStream = System.out;
            printStream.println("Warning: Exception was: " + th);
            th.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean isVerboseErrorReporting() {
        try {
            String property = System.getProperty("org.dom4j.verbose");
            if (property != null) {
                if (property.equalsIgnoreCase("true")) {
                }
            }
        } catch (Exception unused) {
        }
        return true;
    }
}
