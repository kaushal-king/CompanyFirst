import com.google.gson.annotations.SerializedName
import com.the.firsttask.dataclass.MovieDetailsDataClass


data class MovielistDataClass(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDetailsDataClass>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)