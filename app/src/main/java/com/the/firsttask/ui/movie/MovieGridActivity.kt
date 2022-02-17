package com.the.firsttask.ui.movie

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.the.firsttask.R
import com.the.firsttask.adapter.MovieGridAdapter
import com.the.firsttask.database.MovieEntity
import com.the.firsttask.databinding.ActivityMovieGridBinding
import com.the.firsttask.utils.ConstantHelper
import com.the.firsttask.utils.LanguageUtils
import com.the.firsttask.utils.MyFirebaseAnalytics
import com.the.firsttask.utils.ThemeUtils

class MovieGridActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieGridBinding
    private var adapter: MovieGridAdapter? = null
    private var listMovie: List<MovieEntity>? = null
    private lateinit var movieType: String
    private lateinit var view: ConstraintLayout
    private lateinit var viewModel: MovieListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(this)
        LanguageUtils.setLocale(this)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_movie_grid)
//        binding = ActivityMovieGridBinding.inflate(layoutInflater)
//        view = binding.root
//        setContentView(view)


        viewModel = ViewModelProvider(this@MovieGridActivity).get(MovieListViewModel::class.java)

        movieType = intent.getStringExtra(ConstantHelper.BUNDLE_MOVIE_TYPE).toString()
        if (movieType == ConstantHelper.MOVIE_TYPE_TOP) {
            binding.tvListTitle.text = getString(R.string.top_rated_movie)
        } else {
            binding.tvListTitle.text = getString(R.string.popular_movie)
        }
        binding.ibSearchBack.visibility = View.VISIBLE
        binding.ibSearch.setOnClickListener {
            binding.tvListTitle.visibility = View.GONE
            binding.ibSearch.visibility = View.GONE
            binding.etSearch.visibility = View.VISIBLE
        }

        binding.ibSearchBack.setOnClickListener {
            if (binding.etSearch.visibility == View.GONE) {
                onBackPressed()
            } else {
                binding.etSearch.setText("")
                searchToggle()
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchMovie(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                searchToggle()
            }
            true
        }

        binding.cvProgressGrid.visibility = View.VISIBLE
        loadMovie()

        binding.srMovieGrid.setOnRefreshListener {
            binding.srMovieGrid.isRefreshing=true
            loadMovie()
            if (binding.etSearch.visibility != View.GONE) {

                binding.etSearch.setText("")
                searchToggle()
            }

        }


    }


    private fun searchMovie(searchText: String) {

        val filterList: List<MovieEntity> =
            listMovie!!.filter { movie ->
                movie.title.trim().contains(searchText, ignoreCase = true)
            }

        if (filterList.isEmpty()) {
            adapter?.filterList(emptyList())
            //  Toast.makeText(applicationContext,getString(R.string.toast_filter_message),Toast.LENGTH_LONG).show()
        } else {
            adapter?.filterList(filterList)
        }

    }

    private fun loadMovie() {
        binding.cvProgressGrid.visibility = View.VISIBLE
        viewModel.getAllMovie()
        viewModel.getAllMovieObservers().observe(this@MovieGridActivity, { movi ->
            listMovie = movi.filter { movieEntity -> movieEntity.type == movieType }
            setMovieView(listMovie!!)
        })

    }

    private fun setMovieView(listMovie: List<MovieEntity>) {
        if (listMovie.isNotEmpty()) {
            adapter = MovieGridAdapter(listMovie, this@MovieGridActivity, this)
            binding.rvMovieList.adapter = adapter
            binding.rvMovieList.adapter?.notifyDataSetChanged()
            binding.cvProgressGrid.visibility = View.GONE
            binding.srMovieGrid.isRefreshing=false
        } else {
            Toast.makeText(this@MovieGridActivity, getString(R.string.toast_filter_message), Toast.LENGTH_LONG).show()
            binding.cvProgressGrid.visibility = View.GONE
            binding.srMovieGrid.isRefreshing=false
        }
    }


    override fun onBackPressed() {
        if (binding.etSearch.text.isNotEmpty()) {
            searchToggle()
            binding.etSearch.setText("")
        } else {
            searchToggle()
            binding.etSearch.setText("")
            super.onBackPressed()
        }

    }

    private fun searchToggle() {
        binding.etSearch.clearFocus()
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        searchMovie(binding.etSearch.text.toString())
        binding.tvListTitle.visibility = View.VISIBLE
        binding.ibSearch.visibility = View.VISIBLE
        binding.etSearch.visibility = View.GONE


    }


    override fun onResume() {
        MyFirebaseAnalytics.addScreenView("All $movieType Movie Screen","MainActivity")


        super.onResume()
    }

}