package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.WebSocketError */
/* loaded from: classes2.dex */
public enum WebSocketError {
    NOT_IN_CREATED_STATE,
    SOCKET_INPUT_STREAM_FAILURE,
    SOCKET_OUTPUT_STREAM_FAILURE,
    OPENING_HAHDSHAKE_REQUEST_FAILURE,
    OPENING_HANDSHAKE_RESPONSE_FAILURE,
    STATUS_LINE_EMPTY,
    STATUS_LINE_BAD_FORMAT,
    NOT_SWITCHING_PROTOCOLS,
    HTTP_HEADER_FAILURE,
    NO_UPGRADE_HEADER,
    NO_WEBSOCKET_IN_UPGRADE_HEADER,
    NO_CONNECTION_HEADER,
    NO_UPGRADE_IN_CONNECTION_HEADER,
    NO_SEC_WEBSOCKET_ACCEPT_HEADER,
    UNEXPECTED_SEC_WEBSOCKET_ACCEPT_HEADER,
    EXTENSION_PARSE_ERROR,
    UNSUPPORTED_EXTENSION,
    EXTENSIONS_CONFLICT,
    UNSUPPORTED_PROTOCOL,
    INSUFFICENT_DATA,
    INVALID_PAYLOAD_LENGTH,
    TOO_LONG_PAYLOAD,
    INSUFFICIENT_MEMORY_FOR_PAYLOAD,
    INTERRUPTED_IN_READING,
    IO_ERROR_IN_READING,
    IO_ERROR_IN_WRITING,
    FLUSH_ERROR,
    NON_ZERO_RESERVED_BITS,
    UNEXPECTED_RESERVED_BIT,
    FRAME_MASKED,
    UNKNOWN_OPCODE,
    FRAGMENTED_CONTROL_FRAME,
    UNEXPECTED_CONTINUATION_FRAME,
    CONTINUATION_NOT_CLOSED,
    TOO_LONG_CONTROL_FRAME_PAYLOAD,
    MESSAGE_CONSTRUCTION_ERROR,
    TEXT_MESSAGE_CONSTRUCTION_ERROR,
    UNEXPECTED_ERROR_IN_READING_THREAD,
    UNEXPECTED_ERROR_IN_WRITING_THREAD,
    PERMESSAGE_DEFLATE_UNSUPPORTED_PARAMETER,
    PERMESSAGE_DEFLATE_INVALID_MAX_WINDOW_BITS,
    COMPRESSION_ERROR,
    DECOMPRESSION_ERROR,
    SOCKET_CONNECT_ERROR,
    PROXY_HANDSHAKE_ERROR,
    SOCKET_OVERLAY_ERROR,
    SSL_HANDSHAKE_ERROR,
    NO_MORE_FRAME,
    HOSTNAME_UNVERIFIED
}
