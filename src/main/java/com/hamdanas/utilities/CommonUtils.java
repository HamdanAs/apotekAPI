package com.hamdanas.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.ResponseTransformer;

public class CommonUtils {

    public static Gson getJsonConvertor(boolean serializeNulls) {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().disableHtmlEscaping();

        if (serializeNulls) {
            builder.serializeNulls();
        }

        return builder.create();
    }

    public static Gson getJsonConvertor() {
        return getJsonConvertor(false);
    }

    public static String toJson(Object object) {
        return CommonUtils.getJsonConvertor().toJson(object);
    }

    public static ResponseTransformer getJsonTransformer() {
        return CommonUtils::toJson;
    }

}
