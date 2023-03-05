package com.example.pipapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    val url: String,
    val name: String,
    val contentRating: String,
    val description: String,
    val datePublished: String,
    val duration: String,
    val image: String,
    val trailerDescription: String,
    val trailerEmbedUrl: String,
    val trailerName: String,
    val trailerThumbnailUrl: String,
    val aggregateRatingBest: String,
    val aggregateRatingCount: Int,
    val aggregateRatingValue: Double
)

data class Person(
    val name: String,
    val type: String,
    val url: String
)

data class Creator(
    val name: String?,
    val type: String?,
    val url: String?
)

data class Director(
    val name: String?,
    val type: String?,
    val url: String?
)

data class NewThumbnail(
    val contentUrl: String?,
    val type: String?
)

data class NewTrailer(
    val description: String?,
    val embedUrl: String?,
    val name: String?,
    val thumbnail: NewThumbnail?,
    val thumbnailUrl: String?,
    val type: String?,
    val uploadDate: String?
)

data class NewAggregateRating(
    val bestRating: String?,
    val ratingCount: Int?,
    val ratingValue: Double?,
    val type: String?,
    val worstRating: String?
)

data class Movie(
    val actor: List<Person>?,
    val aggregateRating: NewAggregateRating?,
    val contentRating: String?,
    val context: String?,
    val creator: List<Creator>?,
    val datePublished: String?,
    val description: String?,
    val director: List<Director>?,
    val duration: String?,
    val genre: List<String>?,
    val image: String?,
    val keywords: String?,
    val name: String?,
    val trailer: NewTrailer?,
    val type: String?,
    val url: String?
)
