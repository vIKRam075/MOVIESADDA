package com.example.pipapplication.data.database.covertors

import androidx.room.TypeConverter
import com.example.pipapplication.data.model.Thumbnail
import com.example.pipapplication.data.model.Trailer
import org.json.JSONObject


class TrailerTypeConverters {
    @TypeConverter
    fun fromSource(trailer: Trailer): String {
        return JSONObject().apply {
            put("embedUrl", trailer.embedUrl)
            put("thumbnail", trailer.thumbnail)
            put("uploadDate", trailer.uploadDate)
            put("@type", trailer.type)
            put("name", trailer.name)
            put("description", trailer.description)
            put("thumbnailUrl", trailer.thumbnailUrl)
        }.toString()
    }

    @TypeConverter
    fun toSource(trailer: String): Trailer {
        val json = JSONObject(trailer)
        return Trailer(
            json.getString("embedUrl"),
            json.get("thumbnail") as Thumbnail,
            json.getString("uploadDate"),
            json.getString("@type"),
            json.getString("name"),
            json.getString("description"),
            json.getString("thumbnailUrl")
        )
    }
}
