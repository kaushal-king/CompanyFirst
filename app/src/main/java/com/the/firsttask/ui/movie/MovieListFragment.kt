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
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.the.firsttask.R
import com.the.firsttask.adapter.MovieCardAdapter
import com.the.firsttask.api.Api
import com.the.firsttask.api.ApiClient
import com.the.firsttask.database.MovieEntity
import com.the.firsttask.databinding.FragmentMovieListBinding
import com.the.firsttask.utils.ConstantHelper
import com.the.firsttask.utils.MyFirebaseAnalytics
import com.the.firsttask.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieListFragment : Fragment() {


    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private var adapter: MovieCardAdapter? = null
    lateinit var listMovie: List<MovieEntity>
    lateinit var viewModel: MovieListViewModel
    lateinit var lastNetworkState:String
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        firebaseAnalytics=Firebase.analytics


        lastNetworkState=ConstantHelper.NETWORK_LOST
        loadOffline()


        binding.cvProgressPopular.visibility = View.VISIBLE



//        binding.cvProgressTop.visibility=View.VISIBLE


        binding.tvTopAll.setOnClickListener {
            val intent = Intent(activity, MovieGridActivity::class.java)
            intent.putExtra(ConstantHelper.BUNDLE_MOVIE_TYPE, ConstantHelper.MOVIE_TYPE_TOP)
            startActivity(intent)
            logSeeAllFirebase("TopMovie")
        }



        binding.tvPopularAll.setOnClickListener {
            val intent = Intent(activity, MovieGridActivity::class.java)
            intent.putExtra(ConstantHelper.BUNDLE_MOVIE_TYPE, ConstantHelper.MOVIE_TYPE_POPULAR)
            startActivity(intent)
            logSeeAllFirebase("PopularMovie")
        }

        binding.srMovieList.setOnRefreshListener {
            binding.srMovieList.isRefreshing=true
            loadPopular()
            loadTopRated()

        }



        return root
    }


    private fun loadOffline() {
     viewModel.getAllMovieObservers().observe(viewLifecycleOwner) {


            //popular Movie
            binding.cvProgressPopular.visibility = View.VISIBLE
            val popularList =
                it.filter { movieEntity -> movieEntity.type == ConstantHelper.MOVIE_TYPE_POPULAR }
            adapter = MovieCardAdapter(
                popularList.take(4),
                requireActivity(),
                ConstantHelper.MOVIE_TYPE_POPULAR,requireActivity()
            )
            binding.rvPopular.adapter = adapter
            binding.rvPopular.adapter?.notifyDataSetChanged()
            binding.cvProgressPopular.visibility = View.GONE

            //top rated movie

            binding.cvProgressTop.visibility = View.VISIBLE
            val topList =
                it.filter { movieEntity -> movieEntity.type == ConstantHelper.MOVIE_TYPE_TOP }
            adapter =
                MovieCardAdapter(topList.take(4), requireActivity(), ConstantHelper.MOVIE_TYPE_TOP,requireActivity())
            binding.rvTopRate.adapter = adapter
            binding.rvTopRate.adapter?.notifyDataSetChanged()
            binding.cvProgressTop.visibility = View.GONE


        }

    }

    private fun loadPopular() {
        val client = ApiClient()
        val api = client.getClient()?.create(Api::class.java)
        val call = api?.popularList(getString(R.string.apikey))
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
                    binding.srMovieList.isRefreshing=false


                } else {
                    Toast.makeText(activity,
                        getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()
                    binding.srMovieList.isRefreshing=false

                }
            }

            override fun onFailure(call: Call<MovielistDataClass>, t: Throwable) {

                Toast.makeText(activity, getString(R.string.toast_network_error), Toast.LENGTH_SHORT)
                    .show()
                binding.srMovieList.isRefreshing=false
            }
        })

    }

    private fun loadTopRated() {


        val client = ApiClient()
        val api = client.getClient()?.create(Api::class.java)
        val call = api?.topRatedlist(getString(R.string.apikey))
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
                    binding.srMovieList.isRefreshing=false

                } else {
                    Toast.makeText(activity, getString(R.string.toast_message), Toast.LENGTH_SHORT)
                        .show()
                    binding.srMovieList.isRefreshing=false

                }
            }

            override fun onFailure(call: Call<MovielistDataClass>, t: Throwable) {

                Toast.makeText(activity, getString(R.string.toast_network_error), Toast.LENGTH_SHORT)
                    .show()
                binding.srMovieList.isRefreshing=false
            }
        })

    }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//
//           activity?.menuInflater?.inflate(R.menu.drawer, menu)
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onPause() {
        NetworkUtils.getNetworkState().removeObservers(requireActivity())
        super.onPause()
    }




    override fun onResume() {

        NetworkUtils.getNetworkState().observe(requireActivity()) { networkState ->
            when (networkState) {
                ConstantHelper.NETWORK_CONNECT -> {
                    if(lastNetworkState!=ConstantHelper.NETWORK_CONNECT){
                        loadTopRated()
                        loadPopular()

                    }
                    lastNetworkState=ConstantHelper.NETWORK_CONNECT
                }
                ConstantHelper.NETWORK_LOST -> {
                    lastNetworkState=ConstantHelper.NETWORK_LOST

                }

            }
        }

        MyFirebaseAnalytics.addScreenView("MovieListScreen","MainActivity")


        super.onResume()
    }

    fun logSeeAllFirebase(movieType:String){
        var bundle=Bundle()
        if(movieType=="TopMovie"){
            bundle.putInt("id",R.id.tv_top_all)
        }
        else{
            bundle.putInt("id",R.id.tv_popular_all)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM){
            param(FirebaseAnalytics.Param.ITEM_ID,bundle )
            param(FirebaseAnalytics.Param.ITEM_NAME, movieType)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "TextView")
        }

    }




}