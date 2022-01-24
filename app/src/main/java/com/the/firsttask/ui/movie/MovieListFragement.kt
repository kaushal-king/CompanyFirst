package com.the.firsttask.ui.movie

import MovielistDataClass
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.the.firsttask.ConstantHelper
import com.the.firsttask.DataBase.MovieEntity
import com.the.firsttask.R
import com.the.firsttask.adapter.MovieCardAdapter
import com.the.firsttask.api.Api
import com.the.firsttask.api.ApiClient
import com.the.firsttask.databinding.FragmentMovieListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieListFragement : Fragment() {


    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    var adapter: MovieCardAdapter? = null

    lateinit var listMovie: List<MovieEntity>
    lateinit var viewModel: MovieListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        viewModel.getAllMovieObservers().observe(viewLifecycleOwner, Observer {
            Log.e("pg", "progress: visible")

            //popular Movie
            binding.cvProgressPopular.visibility = View.VISIBLE
            var popularList =
                it.filter { movieEntity -> movieEntity.type == ConstantHelper.MOVIE_TYPE_POPULAR }
            adapter = MovieCardAdapter(
                popularList.take(4),
                requireActivity(),
                ConstantHelper.MOVIE_TYPE_POPULAR
            )
            binding.rvPopular.adapter = adapter
            binding.rvPopular.adapter?.notifyDataSetChanged()
            binding.cvProgressPopular.visibility = View.GONE

            //top rated movie

            binding.cvProgressTop.visibility = View.VISIBLE
            var topList =
                it.filter { movieEntity -> movieEntity.type == ConstantHelper.MOVIE_TYPE_TOP }
            adapter =
                MovieCardAdapter(topList.take(4), requireActivity(), ConstantHelper.MOVIE_TYPE_TOP)
            binding.rvTopRate.adapter = adapter
            binding.rvTopRate.adapter?.notifyDataSetChanged()
            binding.cvProgressTop.visibility = View.GONE

            Log.e("pg", "progress: gone")
        })

        binding.cvProgressPopular.visibility = View.VISIBLE
//        binding.cvProgressTop.visibility=View.VISIBLE
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
        var client = ApiClient()
        var api = client.getClient()?.create(Api::class.java)
        var call = api?.popularList(getString(R.string.apikey))
        call?.enqueue(object : Callback<MovielistDataClass> {
            override fun onResponse(
                call: Call<MovielistDataClass>,
                response: Response<MovielistDataClass>
            ) {
                if (response.isSuccessful) {
                    listMovie = response.body()?.results as List<MovieEntity>
                    listMovie.forEach { movieEntity ->
                        movieEntity.type = ConstantHelper.MOVIE_TYPE_POPULAR
                    }
                    viewModel.insertMoviesInfo(listMovie)

                } else {
                    Toast.makeText(activity, getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()


                }
            }

            override fun onFailure(call: Call<MovielistDataClass>, t: Throwable) {

                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
                    .show()

            }
        })

    }

    private fun loadTopRated() {


        var client = ApiClient()
        var api = client.getClient()?.create(Api::class.java)
        var call = api?.topRatedlist(getString(R.string.apikey))
        call?.enqueue(object : Callback<MovielistDataClass> {
            override fun onResponse(
                call: Call<MovielistDataClass>,
                response: Response<MovielistDataClass>
            ) {
                if (response.isSuccessful) {
                    listMovie = response.body()?.results as List<MovieEntity>
                    listMovie.forEach { movieEntity ->
                        movieEntity.type = ConstantHelper.MOVIE_TYPE_TOP
                    }
                    viewModel.insertMoviesInfo(listMovie)

                } else {
                    Toast.makeText(activity, getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<MovielistDataClass>, t: Throwable) {

                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
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