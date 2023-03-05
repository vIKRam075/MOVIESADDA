package com.example.pipapplication.data.database.covertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pipapplication.data.interfaces.JsonParser
import com.example.pipapplication.data.model.CreatorItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

//custom list object convertor
@ProvidedTypeConverter
class CreatorConvertor
//    (
//    private val jsonParser: JsonParser
//)
{
    /*    @TypeConverter
        fun toCreatorsJson(meaning: List<CreatorItem>): String {
            return jsonParser.toJson(
                meaning,
                object : TypeToken<ArrayList<CreatorItem>>() {}.type
            ) ?: "[]"
        }

        @TypeConverter
        fun fromCreatorsJson(json: String): List<CreatorItem> {
            return jsonParser.fromJson<ArrayList<CreatorItem>>(
                json,
                object : TypeToken<ArrayList<CreatorItem>>() {}.type
            ) ?: emptyList()
        }*/
    @TypeConverter
    fun stringToListServer(data: String?): List<CreatorItem?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val gson = Gson()
        val listType: Type = object :
            TypeToken<List<CreatorItem?>?>() {}.type
        return gson.fromJson<List<CreatorItem?>>(data, listType)
    }

    @TypeConverter
    fun listServerToString(someObjects: List<CreatorItem?>?): String? {
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}
