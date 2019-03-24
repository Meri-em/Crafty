package com.crafty.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CraftyResponse<T> implements Serializable {
	
    private static final long serialVersionUID = 2442097946474456470L;

    private T data;

    private Map<String, ?> meta;

    private String message;


    /**
     * Default constructor so the class can be de-serialized from json
     */
    public CraftyResponse() {
        super();
    }

    private CraftyResponse(T data, Map<String, ?> meta, String message) {
        super();
        this.data = data;
        this.meta = meta;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, ?> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, ?> meta) {
        this.meta = meta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> CraftyResponse<T> build(T data, Map<String, Object> meta, String message) {
        return new CraftyResponse<>(data, meta, message);
    }

    public static <T> CraftyResponse<T> build(T data, Map<String, Object> meta) {
        return new CraftyResponse<>(data, meta, null);
    }

    public static <T> CraftyResponse<T> build(T data, String message) {
        return new CraftyResponse<>(data, new HashMap<>(), message);
    }

    public static <T> CraftyResponse<T> build(T data) {
        return new CraftyResponse<>(data, new HashMap<>(), null);
    }

    @Override
    public String toString() {
        return "CraftyResponse {" +
            "data=" + data +
            ", meta=" + meta +
            ", message='" + message + '\'' +
            '}';
    }
}

