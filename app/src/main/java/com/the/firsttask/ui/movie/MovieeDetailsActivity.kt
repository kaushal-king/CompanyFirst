package com.the.firsttask.ui.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.the.firsttask.ConstantHelper
import com.the.firsttask.R
import com.the.firsttask.dataclass.MovieDetailsDataClass
import com.the.firsttask.databinding.ActivityMovieedetailsBinding

class MovieeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieedetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMovieedetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val model: MovieDetailsDataClass
        val bundle = intent.getBundleExtra(ConstantHelper.BUNDLE_DETAILS_BUNDLE)
        model = bundle?.getSerializable(ConstantHelper.BUNDLE_DETAILS_MOVIE) as MovieDetailsDataClass


        Glide.with(applicationContext).load(getString(R.string.imageurl_link) + model.backdropPath)
            .into(binding.imgMovieBanner)
        binding.tvPopularity.text = model.popularity.toString()
        binding.tvRating.text = ((model.voteAverage.div(2)).toInt() / 2).toString()
        binding.tvVote.text = model.voteCount.toString()
        binding.tvTitleMovie.text = model.title
        binding.tvDate.text = getString(R.string.release_date)+ model.releaseDate
        binding.tvLaungage.text = getString(R.string.language)+ model.originalLanguage
        binding.tvOverviewDetails.text = model.overview

        if (model.adult) {
            binding.tvAdult.text = getString(R.string.adult)
        } else {
            binding.tvAdult.text = getString(R.string.UA)
        }


    }
}