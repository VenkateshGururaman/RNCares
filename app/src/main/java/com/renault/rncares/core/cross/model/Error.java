package com.renault.rncares.core.cross.model;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class Error {

    public static String CODE_EXCEPTION = "0";
    public static String CODE_ERROR = "0";
    private static String MESSAGE_EXCEPTION = "0";

    private String code;
    private String message;

    private final String TAG = this.getClass().getSimpleName();

    public Error() {
        this(CODE_ERROR, MESSAGE_EXCEPTION);
    }

    public Error(String message) {
        this(CODE_EXCEPTION, message);
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error fromJson(String json) throws IOException {
        Error result = null;
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Error> jsonAdapter = moshi.adapter(Error.class);
        result = jsonAdapter.fromJson(json);
        return result;
    }

    public Error fromException(Exception exception) {
        return new Error(CODE_EXCEPTION, exception.getMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Error error = (Error) o;
        return code != null ? code.equals(error.code) : error.code == null &&
                (message != null ? message.equals(error.message) : error.message == null);
    }

    @Override
    public String toString() {
        return TAG + "{" +
                "Code = '" + code + '\'' +
                " message = '" + message + '\'' +
                '}';
    }

}
