package com.crm.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.ResponseTransformer;

public class CommonUtil {

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
        return CommonUtil.getJsonConvertor().toJson(object);
    }

    public static ResponseTransformer getJsonTransformer() {
        return CommonUtil::toJson;
    }

}
