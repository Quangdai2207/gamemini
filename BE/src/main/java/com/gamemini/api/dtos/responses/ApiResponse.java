package com.gamemini.api.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse<T> {
    private int status;
    private boolean success;
    private Object message;
    private Object meta;
    private T data;

    // TODO; RESPONSE OK
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(HttpStatus.OK.getReasonPhrase());
        body.setData(data);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(message);
        body.setData(data);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, Object meta, String message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(message);
        body.setMeta(meta);
        body.setData(data);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, Object meta) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(HttpStatus.OK.getReasonPhrase());
        body.setMeta(meta);
        body.setData(data);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok() {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(HttpStatus.OK.getReasonPhrase());
        body.setData(null);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(message);
        body.setData(null);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }


    // TODO: CREATED RESPONSE
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(HttpStatus.CREATED.getReasonPhrase());
        body.setData(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.CREATED.value());
        body.setSuccess(true);
        body.setMessage(message);
        body.setData(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created() {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.OK.value());
        body.setSuccess(true);
        body.setMessage(HttpStatus.CREATED.getReasonPhrase());
        body.setData(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    // TODO: NOT_FOUND RESPONSE
    public static <T> ResponseEntity<ApiResponse<T>> notFound() {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.NOT_FOUND.value());
        body.setSuccess(false);
        body.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
        body.setData(null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.NOT_FOUND.value());
        body.setSuccess(false);
        body.setMessage(message);
        body.setData(null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // TODO: INTERNAL_SERVER_ERROR RESPONSE
    public static <T> ResponseEntity<ApiResponse<T>> internalServerError() {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.setSuccess(false);
        body.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.setData(null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(Object message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.setSuccess(false);
        body.setMessage(message);
        body.setData(null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // TODO: BAD_REQUEST RESPONSE
    public static <T> ResponseEntity<ApiResponse<T>> badRequest() {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setSuccess(false);
        body.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(Object message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setSuccess(false);
        body.setMessage(message);
        body.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> unauthorize(T data) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.UNAUTHORIZED.value());
        body.setSuccess(false);
        body.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        body.setData(data);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> unauthorize() {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.UNAUTHORIZED.value());
        body.setSuccess(false);
        body.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> unauthorize(String message) {
        ApiResponse<T> body = new ApiResponse<>();
        body.setStatus(HttpStatus.UNAUTHORIZED.value());
        body.setSuccess(false);
        body.setMessage(message);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
