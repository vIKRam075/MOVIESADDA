package com.example.pipapplication.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pipapplication.data.interfaces.JsonParser
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject


@Entity
@Parcelize
data class MoviesResponse(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

//    val username: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("creator")
    val creator: List<CreatorItem?>? = null,

    @field:SerializedName("keywords")
    val keywords: String? = null,

    @field:SerializedName("director")
    val director: List<DirectorItem?>? = null,

    @field:SerializedName("@type")
    val type: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("@context")
    val context: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("actor")
    val actor: List<ActorItem?>? = null,

    @field:SerializedName("datePublished")
    val datePublished: String? = null,

    @field:SerializedName("duration")
    val duration: String? = null,

//    @field:SerializedName("trailer")
//    val trailer: Trailer? = null,

    @field:SerializedName("genre")
    val genre: List<String?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("contentRating")
    val contentRating: String? = null,

    @field:SerializedName("aggregateRating")
    val aggregateRating: AggregateRating? = null
) : Parcelable

@Parcelize
data class CreatorItem(

    @field:SerializedName("@type")
    val type: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class DirectorItem(

    @field:SerializedName("@type")
    val type: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class AggregateRating(

    @field:SerializedName("bestRating")
    val bestRating: String? = null,

    @field:SerializedName("@type")
    val type: String? = null,

    @field:SerializedName("ratingValue")
    val ratingValue: Double? = null,

    @field:SerializedName("ratingCount")
    val ratingCount: Int? = null,

    @field:SerializedName("worstRating")
    val worstRating: String? = null
) : Parcelable

@Parcelize
data class ActorItem(

    @field:SerializedName("@type")
    val type: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class Thumbnail(

    @field:SerializedName("contentUrl")
    val contentUrl: String? = null,

    @field:SerializedName("@type")
    val type: String? = null
) : Parcelable

@Parcelize
data class Trailer(

    @field:SerializedName("embedUrl")
    val embedUrl: String? = null,

    @field:SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null,

    @field:SerializedName("uploadDate")
    val uploadDate: String? = null,

    @field:SerializedName("@type")
    val type: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null
) : Parcelable

//custom object convertor
class CreatorItemTypeConverters {
    @TypeConverter
    fun fromSource(creatorItem: CreatorItem): String {
        return JSONObject().apply {
            put("@type", creatorItem.type)
            put("name", creatorItem.name)
            put("url", creatorItem.url)
        }.toString()
    }

    @TypeConverter
    fun toSource(creatorItem: String): CreatorItem {
        val json = JSONObject(creatorItem)
        return CreatorItem(json.getString("@type"), json.getString("name"), json.getString("url"))
    }
}


class DirectorItemTypeConverters {
    @TypeConverter
    fun fromSource(directorItem: DirectorItem): String {
        return JSONObject().apply {
            put("@type", directorItem.type)
            put("name", directorItem.name)
            put("url", directorItem.url)
        }.toString()
    }

    @TypeConverter
    fun toSource(directorItem: String): DirectorItem {
        val json = JSONObject(directorItem)
        return DirectorItem(json.getString("@type"), json.getString("name"), json.getString("url"))
    }
}



@ProvidedTypeConverter
class AggregateRatingConvertor(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun toMeaningJson(meaning: List<AggregateRating>): String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<AggregateRating>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<AggregateRating> {
        return jsonParser.fromJson<ArrayList<AggregateRating>>(
            json,
            object : TypeToken<ArrayList<AggregateRating>>() {}.type
        ) ?: emptyList()
    }
}

class ActorItemTypeConverters {
    @TypeConverter
    fun fromSource(actorItem: ActorItem): String {
        return JSONObject().apply {
            put("@type", actorItem.type)
            put("name", actorItem.name)
            put("url", actorItem.url)
        }.toString()
    }

    @TypeConverter
    fun toSource(actorItem: String): ActorItem {
        val json = JSONObject(actorItem)
        return ActorItem(json.getString("@type"), json.getString("name"), json.getString("url"))

    }
}


class ThumbnailTypeConverters {
    @TypeConverter
    fun fromSource(thumbnail: Thumbnail): String {
        return JSONObject().apply {
            put("contentUrl", thumbnail.contentUrl)
            put("@type", thumbnail.type)
        }.toString()
    }

    @TypeConverter
    fun toSource(thumbnail: String): Thumbnail {
        val json = JSONObject(thumbnail)
        return Thumbnail(json.getString("contentUrl"), json.getString("@type"))
    }
}

@ProvidedTypeConverter
class ThumbnailConverter(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun toMeaningJson(meaning: List<Thumbnail>): String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<Thumbnail>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Thumbnail> {
        return jsonParser.fromJson<ArrayList<Thumbnail>>(
            json,
            object : TypeToken<ArrayList<Thumbnail>>() {}.type
        ) ?: emptyList()
    }
}

@ProvidedTypeConverter
class TrailerConverter(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun toMeaningJson(meaning: List<Trailer>): String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<Trailer>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Trailer> {
        return jsonParser.fromJson<ArrayList<Trailer>>(
            json,
            object : TypeToken<ArrayList<Trailer>>() {}.type
        ) ?: emptyList()
    }
}
