package com.gamemini.api.enums;

import lombok.Getter;

public enum HttpStatus {

    SUCCESS(200, "ok"),
    CREATED(201, "created successfully"),
    NO_CONTENT(204, "no content"),
    BAD_REQUEST(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not found"),
    METHOD_NOT_ALLOWED(405, "method not allowed"),
    NOT_ACCEPTABLE(406, "not acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "proxy authentication required"),
    REQUEST_TIMEOUT(408, "request timeout"),
    CONFLICT(409, "conflict"),
    GONE(410, "gone"),
    LENGTH_REQUIRED(411, "length required"),
    PRECONDITION_FAILED(412, "precondition failed"),
    REQUEST_ENTITY_TOO_LARGE(413, "request entity too large"),
    REQUEST_URI_TOO_LONG(414, "request uri too long"),
    UNSUPPORTED_MEDIA_TYPE(415, "unsupported media type"),
    INTERNAL_SERVER_ERROR(500, "internal server error");

    @Getter
    private final int code;

    @Getter
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static HttpStatus fromCode(int code) {
        for (HttpStatus httpStatus : values()) {
            if (httpStatus.code == code) return httpStatus;
        }
        return INTERNAL_SERVER_ERROR;
    }
}
