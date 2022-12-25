package io.requery.android.database.sqlite;

import java.util.Locale;

/* loaded from: classes4.dex */
class SQLiteStatementType {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getSqlStatementType(String str) {
        char c;
        String trim = str.trim();
        if (trim.length() < 3) {
            return 99;
        }
        String upperCase = trim.substring(0, 3).toUpperCase(Locale.US);
        switch (upperCase.hashCode()) {
            case 64905:
                if (upperCase.equals("ALT")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 64948:
                if (upperCase.equals("ANA")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 65153:
                if (upperCase.equals("ATT")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 65636:
                if (upperCase.equals("BEG")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 66913:
                if (upperCase.equals("COM")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 66998:
                if (upperCase.equals("CRE")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 67563:
                if (upperCase.equals("DEL")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 67571:
                if (upperCase.equals("DET")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 67969:
                if (upperCase.equals("DRO")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 68795:
                if (upperCase.equals("END")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 72654:
                if (upperCase.equals("INS")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 79487:
                if (upperCase.equals("PRA")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 81021:
                if (upperCase.equals("REP")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 81327:
                if (upperCase.equals("ROL")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 81978:
                if (upperCase.equals("SEL")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 84233:
                if (upperCase.equals("UPD")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
            case 2:
            case 3:
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
            case 7:
                return 5;
            case '\b':
                return 6;
            case '\t':
                return 4;
            case '\n':
                return 7;
            case 11:
            case '\f':
            case '\r':
                return 8;
            case 14:
            case 15:
                return 9;
            default:
                return 99;
        }
    }
}
