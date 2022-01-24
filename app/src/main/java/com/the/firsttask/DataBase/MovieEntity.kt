package com.the.firsttask.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "movieinfo")
data class MovieEntity (
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id:Int=0,
    @SerializedName("adult")
    @ColumnInfo(name = "adult")  val adult: Boolean,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdropPath") val backdropPath: String,
    @SerializedName("original_language")
    @ColumnInfo(name = "originalLanguage") val originalLanguage: String,
    @SerializedName("original_title")
    @ColumnInfo(name = "originalTitle") val originalTitle: String,
    @SerializedName("overview")
    @ColumnInfo(name = "overview")  val overview: String,
    @SerializedName("popularity")
    @ColumnInfo(name = "popularity") val popularity: Double,
    @SerializedName("poster_path")
    @ColumnInfo(name = "posterPath") val posterPath: String,
    @SerializedName("release_date")
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @SerializedName("title")
    @ColumnInfo(name = "title") val title: String,
    @SerializedName("video")
    @ColumnInfo(name = "video") val video: Boolean,
    @SerializedName("vote_average")
    @ColumnInfo(name = "voteAverage") val voteAverage: Double,
    @SerializedName("vote_count")
    @ColumnInfo(name = "voteCount") val voteCount: Int,
    @ColumnInfo(name = "movietype") var type: String,
): Serializable


