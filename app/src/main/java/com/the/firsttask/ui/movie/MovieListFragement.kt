package com.the.firsttask.ui.movie

import MovielistDataClass
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.the.firsttask.ConstantHelper
import com.the.firsttask.R
import com.the.firsttask.adapter.MovieCardAdapter
import com.the.firsttask.api.Api
import com.the.firsttask.api.ApiClient
import com.the.firsttask.databinding.FragmentMovieListBinding

import com.the.firsttask.dataclass.MovieDetailsDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieListFragement : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
//        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    var adapter: MovieCardAdapter? = null
    lateinit var listMovie: List<MovieDetailsDataClass>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentMovieListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cvProgressPopular.visibility=View.VISIBLE
        binding.cvProgressTop.visibility=View.VISIBLE
        loadTopRated()
        loadPopular()



        binding.tvTopAll.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(activity, MovieGridActivity::class.java)
                intent.putExtra(ConstantHelper.BUNDLE_MOVIE_TYPE, ConstantHelper.MOVIE_TYPE_TOP)
                startActivity(intent)
            }
        }
        )



        binding.tvPopularAll.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(activity, MovieGridActivity::class.java)
                intent.putExtra(ConstantHelper.BUNDLE_MOVIE_TYPE, ConstantHelper.MOVIE_TYPE_POPULAR)
                startActivity(intent)
            }
        })



        return root
    }

    private fun loadPopular() {
        binding.cvProgressPopular.visibility=View.VISIBLE

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
                    listMovie=listMovie.take(4)
                    adapter = MovieCardAdapter(listMovie, activity!!, "popular")
                    binding.rvPopular.adapter = adapter
                    binding.cvProgressPopular.visibility=View.GONE

                } else {
                    Toast.makeText(activity, getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()


                }
            }

            override fun onFailure(call: Call<MovielistDataClass>, t: Throwable) {

                Toast.makeText(activity, t.localizedMessage + "", Toast.LENGTH_SHORT)
                    .show()

            }
        })

    }

    private fun loadTopRated() {

        binding.cvProgressTop.visibility=View.VISIBLE
        var client = ApiClient()
        var api = client.getClient()?.create(Api::class.java)
        var call = api?.topRatedlist(getString(R.string.apikey))
        call?.enqueue(object : Callback<MovielistDataClass> {
            override fun onResponse(
                call: Call<MovielistDataClass>,
                response: Response<MovielistDataClass>
            ) {
                if (response.isSuccessful) {
                    listMovie = response.body()?.results as List<MovieDetailsDataClass>
                    listMovie=listMovie.take(4)
                    adapter = MovieCardAdapter(listMovie, activity!!, "top")
                    binding.rvTopRate.adapter = adapter
                    binding.cvProgressTop.visibility=View.GONE
                } else {
                    Toast.makeText(activity, getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<MovielistDataClass>, t: Throwable) {

                Toast.makeText(activity, t.localizedMessage + "", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }




//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//
//           activity?.menuInflater?.inflate(R.menu.drawer, menu)
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}