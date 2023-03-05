package com.example.pipapplication.utils

import com.example.pipapplication.data.interfaces.JsonParser
import com.google.gson.Gson
import java.lang.reflect.Type

//for room custom type convertor
class GsonParser(
    private val gson: Gson
): JsonParser {
    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }
}