package com.example.pipapplication.data.interfaces

import java.lang.reflect.Type

//for room custom type convertor
interface JsonParser {
    fun <T> fromJson(json: String, type: Type): T?
    fun <T> toJson(obj: T, type: Type): String?
}