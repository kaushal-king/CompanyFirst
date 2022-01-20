package com.the.firsttask.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the.firsttask.ConstantHelper
import com.the.firsttask.dataclass.MovieDetailsDataClass
import com.the.firsttask.ui.movie.MovieeDetailsActivity
import com.the.firsttask.R

class MovieCardAdapter(
    private val mList: List<MovieDetailsDataClass>,
    var mCtx: Context,
    var movieType: String
) : RecyclerView.Adapter<MovieCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_movie_card, parent, false)
        val viewHolder = ViewHolder(view)
        if (movieType == ConstantHelper.MOVIE_TYPE_POPULAR) {
            val layoutParams = viewHolder.movieCard.layoutParams
            layoutParams.height = 300
            layoutParams.width = 220
        }

        viewHolder.movie.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(mCtx, MovieeDetailsActivity::class.java)
                var model = mList.get(viewHolder.adapterPosition)
                val bundle = Bundle()
                bundle.putSerializable(ConstantHelper.BUNDLE_DETAILS_MOVIE, model)
                intent.putExtra(ConstantHelper.BUNDLE_DETAILS_BUNDLE, bundle)
                mCtx.startActivity(intent)
            }
        })

        viewHolder.imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(mCtx, MovieeDetailsActivity::class.java)
                var model = mList.get(viewHolder.adapterPosition)
                val bundle = Bundle()
                bundle.putSerializable(ConstantHelper.BUNDLE_DETAILS_MOVIE, model)
                intent.putExtra(ConstantHelper.BUNDLE_DETAILS_BUNDLE, bundle)
                mCtx.startActivity(intent)
            }
        })


        return viewHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

        Glide.with(mCtx).load(mCtx.getString(R.string.imageurl_link) + item.backdropPath)
            .into(holder.imageView)
        holder.movieName.text = item.title
        holder.rating.rating = (item.voteAverage.div(2)).toFloat()


    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.img_movie)
        var rating: RatingBar = itemView.findViewById(R.id.rb_movie)
        var movieName: TextView = itemView.findViewById(R.id.tv_moviename)
        var movieCard: CardView = itemView.findViewById(R.id.cv_movie)
        var movie: LinearLayout = itemView.findViewById(R.id.ll_movie)
    }

}