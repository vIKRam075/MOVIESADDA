package com.example.pipapplication.data.database.covertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pipapplication.data.interfaces.JsonParser
import com.example.pipapplication.data.model.CreatorItem
import com.example.pipapplication.data.model.DirectorItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

@ProvidedTypeConverter
class DirectorConvertor/*(
    private val jsonParser: JsonParser
) */ {
/*    @TypeConverter
    fun toMeaningJson(meaning: List<DirectorItem>): String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<DirectorItem>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<DirectorItem> {
        return jsonParser.fromJson<ArrayList<DirectorItem>>(
            json,
            object : TypeToken<ArrayList<DirectorItem>>() {}.type
        ) ?: emptyList()
    }*/

    @TypeConverter
    fun stringToListServer(data: String?): List<DirectorItem?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val gson = Gson()
        val listType: Type = object :
            TypeToken<List<DirectorItem?>?>() {}.type
        return gson.fromJson<List<DirectorItem?>>(data, listType)
    }

    @TypeConverter
    fun listServerToString(someObjects: List<DirectorItem?>?): String? {
        val gson = Gson()
        return gson.toJson(someObjects)
    }

}
