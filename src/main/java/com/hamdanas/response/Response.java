package com.hamdanas.response;

import com.google.gson.annotations.Expose;
import com.hamdanas.utilities.Constants;

public class Response extends BaseResponse {

    @Expose
    private Object result;

    public Response() {

    }

    public Response(Object result) {
        this.code = Constants.RESPONSE_OK;
        this.message = "";
        this.result = result;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.result = null;
    }

    public Response(int code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
