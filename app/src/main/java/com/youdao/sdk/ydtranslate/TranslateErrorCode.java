package com.youdao.sdk.ydtranslate;

import com.tencent.rtmp.TXLiveConstants;
import com.zzz.ipfssdk.callback.exception.CodeState;

/* loaded from: classes4.dex */
public enum TranslateErrorCode {
    NO_OFFLINE_RESOURCE_ERROR("No offline resource.", 5),
    CONTEXT_ERROR("context is null.", 4),
    JSON_PARSE_PARAMETER_ERROR("inputs and parameters are mismatch.", 3),
    JSON_PARSE_ERROR("parse errorcode error.", 2),
    HTTP_REQUEST_ERROR("Http request error.", 1),
    STORAGE_PERMISSION_ERROR("Storage permission request.", 0),
    INPUT_PARAM_ILLEGAL("input paramters is illegal", 100),
    INPUT_PARAM_ILLEGAL_MUST("input paramters must be filled", 101),
    INPUT_PARAM_ILLEGAL_NOT_SPPORT_LANG("not support langage", 102),
    INPUT_PARAM_ILLEGAL_TEXT_TOO_LONG("input text is too long", 103),
    INPUT_PARAM_ILLEGAL_VER_NOT_SUPPORTED("api version is not supported", 104),
    INPUT_PARAM_ILLEGAL_SIGN_NOT_SUPPORTED("request sign is not supported", 105),
    INPUT_PARAM_ILLEGAL_RESPONSE("response is illegal", 106),
    INPUT_PARAM_ILLEGAL_ENCRYPT("encryption is illegal", 107),
    INPUT_PARAM_ILLEGAL_KEY_INVALID("app key is not valid", 108),
    INVALID_BATCH_LOG("batchlog is not valid", 109),
    INVALID_INSTANCE_KEY("instance is not valid", 110),
    INVALID_DEVELOPERID("develop id is not valid", 111),
    INVALID_PRODUCTID("productid is not valid", 112),
    INVALID_TEXTS_INPUT("request text is null", 113),
    INPUT_DECRYPTION_ERROR("server decryption fails.", 201),
    INPUT_DECRYPTION_ERROR_SIGN("server sign do not matchs.", 202),
    INVALID_IP("invalid access ip.", 203),
    SERVER_LOOKUP_DICT_ERROR("lookup of dict error.", 301),
    SERVER_LOOKUP_MINORITY_ERROR("lookup of minority language error.", 302),
    SERVER_LOOKUP_ERROR("lookup of server error.", 303),
    ACCOUNT_OVERDUE_BILL("acount overdue bills error.", 401),
    TRANS_MAX_QUERY_COUNT_ERROR("more than the maximum number of queries.", 411),
    TRANS_MAX_QUERY_LENGTH_ERROR("more than the maximum characters of queries.", 412),
    TRANS_LANGUAGE_ERROR("not support translate language.", 2003),
    TRANS_CHARACTER_ERROR("synthetic character is too long.", 2004),
    TRANS_SPEECH_FORMAT_ERROR("unsupported audio file types.", TXLiveConstants.PLAY_EVT_PLAY_PROGRESS),
    TRANS_SPEECH_SOUND_ERROR("unsupported sound file types.", TXLiveConstants.PLAY_EVT_PLAY_END),
    TRANS_SPEECH_DECRYPT_ERROR("decryption failure.", 2201),
    TRANS_SPEECH_SERVER_ERROR("server failure.", 2301),
    TRANS_SPEECH_FREQUENCY_ERROR("limited access frequency, please visit later.", 2411),
    TRANS_SPEECH_MAX_QUERY_LENGTH_ERROR("more than the maximum characters of queries.", 2412),
    TRANS_SPEECH_SOUND_FORMAT_ERROR("unsupported audio types.", 3001),
    TRANS_SPEECH_SOUND_RATE_ERROR("unsupported audio rate.", 3002),
    TRANS_SPEECH_SOUND_CHANNEL_ERROR("unsupported audio channel.", 3003),
    TRANS_SPEECH_SOUND_UPLOADTYPE_ERROR("unsupported audio translate type.", 3004),
    TRANS_SPEECH_SOUND_LANGUAGE_ERROR("unsupported audio translate type.", 3005),
    TRANS_SPEECH_SOUND_RECOGNIZE_ERROR("unsupported audio translate type.", CodeState.CODES.CODE_QUERY_RESOURCE_P2P_DISABLE),
    TRANS_SPEECH_SOUND_LARGE_ERROR("audio file is too large.", CodeState.CODES.CODE_QUERY_RESOURCE_PARAM_ERROR),
    TRANS_SPEECH_SOUND_LONG_ERROR("audio file is too long.", CodeState.CODES.CODE_QUERY_RESOURCE_NORMAL),
    TRANS_SPEECH_SOUND_AUDIO_TYPE_ERROR("audio file formate is not support.", CodeState.CODES.CODE_QUERY_RESOURCE_HTTP_REQUEST_ERR),
    TRANS_SPEECH_SOUND_VOICE_TYPE_ERROR("audio voice is not support.", CodeState.CODES.CODE_QUERY_RESOURCE_HTTP_REQUEST_ERR),
    TRANS_SPEECH_SOUND_DECRYPT_ERROR("audio decrypt error.", 3201),
    TRANS_SPEECH_RECOGNIZE_ERROR("audio recognize error.", 3301),
    TRANS_SPEECH_TRANSLATE_ERROR("audio translate error.", 3302),
    TRANS_SPEECH_TRANSLATE_SERVER_ERROR("audio translate server error.", 3303),
    TRANS_SPEECH_TRANSLATE_FREQUENCY_ERROR("limited access frequency, please visit later.", 3411),
    TRANS_SPEECH_TRANSLATE_MAX_QUERY_LENGTH_ERROR("more than the maximum characters of queries.", 3412),
    TRANS_UNSUPPORT_OCRTYPE("unsupported ocr types.", 5001),
    TRANS_UNSUPPORT_OCR_IMG_TYPE("unsupported ocr image type.", 5002),
    TRANS_UNSUPPORT_OCR_LANG_TYPE("unsupported ocr language type.", 5003),
    TRANS_QUERY_IMAGE_TOO_LARGE("ocr image is too large.", 5004),
    TRANS_UNSUPPORT_OCR_IMG_FILE("unsupported ocr image file.", 5005),
    TRANS_EMPTY_IMG_FILE("image file is null.", 5006),
    TRANS_IMG_DECRYPT_ERROR("decrypt image error.", 5201),
    TRANS_OCR_PARA_QUERY_FAILED("ocr translate error.", 5301),
    TRANS_OCR_MAX_QUERY_COUNT_ERROR("more than the maximum sizes of queries.", 5411),
    TRANS_OCR_MAX_QUERY_SIZE_ERROR("more than the maximum characters of queries.", 5412),
    UN_SPECIFIC_ERROR("un specific error.");
    
    private final int code;
    private final String message;

    public int getCode() {
        return this.code;
    }

    TranslateErrorCode(String str) {
        this.message = str;
        this.code = 0;
    }

    TranslateErrorCode(String str, int i) {
        this.message = str;
        this.code = i;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.message;
    }
}
