import com.google.gson.annotations.SerializedName
import com.the.firsttask.database.MovieEntity


data class MovielistDataClass(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieEntity>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)