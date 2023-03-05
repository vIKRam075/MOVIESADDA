package com.example.pipapplication.data.database.covertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pipapplication.data.interfaces.JsonParser
import com.example.pipapplication.data.model.ActorItem
import com.example.pipapplication.data.model.DirectorItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

@ProvidedTypeConverter
class ActorConverter/*(
    private val jsonParser: JsonParser
)*/ {
   /* @TypeConverter
    fun toMeaningJson(meaning: List<ActorItem>): String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<ActorItem>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<ActorItem> {
        return jsonParser.fromJson<ArrayList<ActorItem>>(
            json,
            object : TypeToken<ArrayList<ActorItem>>() {}.type
        ) ?: emptyList()
    }*/


    @TypeConverter
    fun stringToListServer(data: String?): List<ActorItem?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val gson = Gson()
        val listType: Type = object :
            TypeToken<List<ActorItem?>?>() {}.type
        return gson.fromJson<List<ActorItem?>>(data, listType)
    }

    @TypeConverter
    fun listServerToString(someObjects: List<ActorItem?>?): String? {
        val gson = Gson()
        return gson.toJson(someObjects)
    }

}
