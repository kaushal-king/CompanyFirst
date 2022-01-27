package com.the.firsttask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "movieinfo")
data class MovieEntity(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int = 0,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "movietype") var type: String,
) : Serializable


