package com.googlecode.mp4parser.boxes.apple;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import com.googlecode.mp4parser.annotations.DoNotParseDetail;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.p136ui.view.widget.tagview.ColorFactory;
import com.tomatolive.library.utils.ConstantUtils;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes3.dex */
public abstract class AppleDataBox extends AbstractBox {
    private static HashMap<String, String> language = new HashMap<>();
    int dataCountry;
    int dataLanguage;
    int dataType;

    protected abstract int getDataLength();

    protected abstract void parseData(ByteBuffer byteBuffer);

    protected abstract byte[] writeData();

    /* JADX INFO: Access modifiers changed from: protected */
    public AppleDataBox(String str, int i) {
        super(str);
        this.dataType = i;
    }

    public String getLanguageString() {
        HashMap<String, String> hashMap = language;
        String str = hashMap.get("" + this.dataLanguage);
        if (str == null) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[2]);
            IsoTypeWriter.writeUInt16(wrap, this.dataLanguage);
            wrap.reset();
            return new Locale(IsoTypeReader.readIso639(wrap)).getDisplayLanguage();
        }
        return str;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return getDataLength() + 16;
    }

    public int getDataType() {
        return this.dataType;
    }

    public int getDataCountry() {
        return this.dataCountry;
    }

    public void setDataCountry(int i) {
        this.dataCountry = i;
    }

    public int getDataLanguage() {
        return this.dataLanguage;
    }

    public void setDataLanguage(int i) {
        this.dataLanguage = i;
    }

    @DoNotParseDetail
    protected ByteBuffer parseDataLength4ccTypeCountryLanguageAndReturnRest(ByteBuffer byteBuffer) {
        int i = byteBuffer.getInt();
        byteBuffer.getInt();
        this.dataType = byteBuffer.getInt();
        this.dataCountry = byteBuffer.getShort();
        int i2 = this.dataCountry;
        if (i2 < 0) {
            this.dataCountry = i2 + 65536;
        }
        this.dataLanguage = byteBuffer.getShort();
        int i3 = this.dataLanguage;
        if (i3 < 0) {
            this.dataLanguage = i3 + 65536;
        }
        int i4 = i - 16;
        ByteBuffer byteBuffer2 = (ByteBuffer) byteBuffer.duplicate().slice().limit(i4);
        byteBuffer.position(i4 + byteBuffer.position());
        return byteBuffer2;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        parseData(parseDataLength4ccTypeCountryLanguageAndReturnRest(byteBuffer));
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeDataLength4ccTypeCountryLanguage(byteBuffer);
        byteBuffer.put(writeData());
    }

    @DoNotParseDetail
    protected void writeDataLength4ccTypeCountryLanguage(ByteBuffer byteBuffer) {
        byteBuffer.putInt(getDataLength() + 16);
        byteBuffer.put(AopConstants.APP_PROPERTIES_KEY.getBytes());
        byteBuffer.putInt(this.dataType);
        IsoTypeWriter.writeUInt16(byteBuffer, this.dataCountry);
        IsoTypeWriter.writeUInt16(byteBuffer, this.dataLanguage);
    }

    static {
        language.put("0", "English");
        language.put("1", "French");
        language.put("2", "German");
        language.put("3", "Italian");
        language.put("4", "Dutch");
        language.put("5", "Swedish");
        language.put("6", "Spanish");
        language.put("7", "Danish");
        language.put("8", "Portuguese");
        language.put("9", "Norwegian");
        language.put("10", "Hebrew");
        language.put("11", "Japanese");
        language.put("12", "Arabic");
        language.put("13", "Finnish");
        language.put("14", "Greek");
        language.put(ConstantUtils.DEFAULT_CHARACTER_LIMIT, "Icelandic");
        language.put("16", "Maltese");
        language.put("17", "Turkish");
        language.put("18", "Croatian");
        language.put("19", "Traditional_Chinese");
        language.put("20", "Urdu");
        language.put("21", "Hindi");
        language.put("22", "Thai");
        language.put("23", "Korean");
        language.put("24", "Lithuanian");
        language.put("25", "Polish");
        language.put("26", "Hungarian");
        language.put("27", "Estonian");
        language.put("28", "Lettish");
        language.put("29", "Sami");
        language.put(ConstantUtils.CAR_TIMES_30, "Faroese");
        language.put("31", "Farsi");
        language.put("32", "Russian");
        language.put(ColorFactory.BG_COLOR_ALPHA, "Simplified_Chinese");
        language.put("34", "Flemish");
        language.put("35", "Irish");
        language.put("36", "Albanian");
        language.put("37", "Romanian");
        language.put("38", "Czech");
        language.put("39", "Slovak");
        language.put("40", "Slovenian");
        language.put("41", "Yiddish");
        language.put("42", "Serbian");
        language.put("43", "Macedonian");
        language.put("44", "Bulgarian");
        language.put("45", "Ukrainian");
        language.put("46", "Belarusian");
        language.put("47", "Uzbek");
        language.put("48", "Kazakh");
        language.put("49", "Azerbaijani");
        language.put("50", "AzerbaijanAr");
        language.put("51", "Armenian");
        language.put("52", "Georgian");
        language.put("53", "Moldavian");
        language.put("54", "Kirghiz");
        language.put("55", "Tajiki");
        language.put("56", "Turkmen");
        language.put("57", "Mongolian");
        language.put("58", "MongolianCyr");
        language.put("59", "Pashto");
        language.put("60", "Kurdish");
        language.put("61", "Kashmiri");
        language.put("62", "Sindhi");
        language.put("63", "Tibetan");
        language.put("64", "Nepali");
        language.put("65", "Sanskrit");
        language.put("66", "Marathi");
        language.put("67", "Bengali");
        language.put("68", "Assamese");
        language.put("69", "Gujarati");
        language.put("70", "Punjabi");
        language.put("71", "Oriya");
        language.put("72", "Malayalam");
        language.put("73", "Kannada");
        language.put("74", "Tamil");
        language.put("75", "Telugu");
        language.put("76", "Sinhala");
        language.put("77", "Burmese");
        language.put("78", "Khmer");
        language.put("79", "Lao");
        language.put("80", "Vietnamese");
        language.put("81", "Indonesian");
        language.put("82", "Tagalog");
        language.put("83", "MalayRoman");
        language.put("84", "MalayArabic");
        language.put("85", "Amharic");
        language.put("87", "Galla");
        language.put("87", "Oromo");
        language.put(ColorFactory.BD_COLOR_ALPHA, "Somali");
        language.put("89", "Swahili");
        language.put("90", "Kinyarwanda");
        language.put("91", "Rundi");
        language.put("92", "Nyanja");
        language.put("93", "Malagasy");
        language.put("94", "Esperanto");
        language.put("128", "Welsh");
        language.put("129", "Basque");
        language.put("130", "Catalan");
        language.put("131", "Latin");
        language.put("132", "Quechua");
        language.put("133", "Guarani");
        language.put("134", "Aymara");
        language.put("135", "Tatar");
        language.put("136", "Uighur");
        language.put("137", "Dzongkha");
        language.put("138", "JavaneseRom");
        language.put("32767", "Unspecified");
    }
}
