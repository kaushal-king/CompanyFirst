package com.the.firsttask.ui.movie

import MovielistDataClass
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.the.firsttask.ConstantHelper
import com.the.firsttask.R
import com.the.firsttask.api.Api
import com.the.firsttask.api.ApiClient
import com.the.firsttask.dataclass.MovieDetailsDataClass
import com.the.firsttask.adapter.MovieGridAdapter
import com.the.firsttask.databinding.ActivityMovieGridBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieGridActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieGridBinding
    var adapter: MovieGridAdapter? = null
    lateinit var listMovie: List<MovieDetailsDataClass>
    lateinit var movieType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieGridBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        movieType = intent.getStringExtra(ConstantHelper.BUNDLE_MOVIE_TYPE).toString()
        if (movieType == ConstantHelper.MOVIE_TYPE_TOP) {
            binding.tvListTitle.text = getString(R.string.top_rated_movie)
        } else {
            binding.tvListTitle.text = getString(R.string.popular_movie)
        }

        loadMovie()

    }


    private fun loadMovie() {
        var client = ApiClient()
        var api = client.getClient()?.create(Api::class.java)
        var call = api?.popularList(getString(R.string.apikey))
        call?.enqueue(object : Callback<MovielistDataClass> {
            override fun onResponse(
                call: Call<MovielistDataClass>,
                response: Response<MovielistDataClass>
            ) {
                if (response.isSuccessful) {
                    listMovie = response.body()?.results as List<MovieDetailsDataClass>
                    adapter = MovieGridAdapter(listMovie, this@MovieGridActivity, movieType)
                    binding.rvMovieList.adapter = adapter
                } else {
                    Toast.makeText(applicationContext, getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<MovielistDataClass>, t: Throwable) {

                Toast.makeText(applicationContext, t.localizedMessage + "", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

}