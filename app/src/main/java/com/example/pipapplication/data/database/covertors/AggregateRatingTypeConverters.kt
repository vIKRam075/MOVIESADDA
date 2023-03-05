package com.example.pipapplication.data.database.covertors

import androidx.room.TypeConverter
import com.example.pipapplication.data.model.AggregateRating
import org.json.JSONObject

class AggregateRatingTypeConverters {

    @TypeConverter
    fun fromSource(aggregateRating: AggregateRating): String {
        return JSONObject().apply {
            put("bestRating", aggregateRating.bestRating)
            put("@type", aggregateRating.type)
            put("ratingValue", aggregateRating.ratingValue)
            put("ratingCount", aggregateRating.ratingCount)
            put("worstRating", aggregateRating.worstRating)
        }.toString()
    }

    @TypeConverter
    fun toSource(aggregateRating: String): AggregateRating {
        val json = JSONObject(aggregateRating)
        return AggregateRating(
            json.getString("bestRating"),
            json.getString("@type"),
            json.getDouble("ratingValue"),
            json.getInt("ratingCount"),
            json.getString("worstRating")
        )
    }
}
