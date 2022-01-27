package com.the.firsttask.ui.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.the.firsttask.utils.ConstantHelper
import com.the.firsttask.database.MovieEntity
import com.the.firsttask.R

import com.the.firsttask.databinding.ActivityMoviedetailsBinding
import com.the.firsttask.utils.LanguageUtils
import com.the.firsttask.utils.ThemeUtils
import java.text.SimpleDateFormat

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviedetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(this)
        LanguageUtils.setLocale(this)
        binding = ActivityMoviedetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val model: MovieEntity
        val bundle = intent.getBundleExtra(ConstantHelper.BUNDLE_DETAILS_BUNDLE)
        model = bundle?.getSerializable(ConstantHelper.BUNDLE_DETAILS_MOVIE) as MovieEntity


        Glide.with(applicationContext).load(getString(R.string.imageurl_link) + model.backdropPath)
            .into(binding.imgMovieBanner)
        binding.tvPopularity.text = model.popularity.toString()
        binding.tvRating.text = ((model.voteAverage.div(2)).toInt() / 2).toString()
        binding.tvVote.text = model.voteCount.toString()
        binding.tvTitleMovie.text = model.title


        val formatter = SimpleDateFormat("yyyy-MM-dd").parse(model.releaseDate)
        val formateDate = SimpleDateFormat("dd-MMM-yyyy")
        binding.tvDate.text = getString(R.string.release_date)+"  "+formateDate.format(formatter)
        binding.tvLaungage.text = getString(R.string.language)+"  "+model.originalLanguage
        binding.tvOverviewDetails.text = model.overview

        if (model.adult) {
            binding.tvAdult.text = getString(R.string.adult)
        } else {
            binding.tvAdult.text = getString(R.string.UA)
        }

        binding.ivBack.setOnClickListener{
            super.onBackPressed()
        }


    }
}