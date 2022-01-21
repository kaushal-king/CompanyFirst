package com.the.firsttask.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the.firsttask.ConstantHelper
import com.the.firsttask.dataclass.MovieDetailsDataClass
import com.the.firsttask.ui.movie.MovieeDetailsActivity
import com.the.firsttask.R

class MovieGridAdapter(
    private var mList: List<MovieDetailsDataClass>,
    var mCtx: Context,
    var movieType: String
) : RecyclerView.Adapter<MovieGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_movie_card, parent, false)
        val viewholder: ViewHolder = ViewHolder(view)


        val layoutParams = viewholder.movieCard.layoutParams
        layoutParams.height = 300
        layoutParams.width = GridLayout.LayoutParams.MATCH_PARENT
        val layoutParamsMain = viewholder.mainMovieLayout.layoutParams
        layoutParamsMain.width = LinearLayout.LayoutParams.MATCH_PARENT


        viewholder.movie.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(mCtx, MovieeDetailsActivity::class.java)
                var model = mList.get(viewholder.adapterPosition)
                val bundle = Bundle()
                bundle.putSerializable(ConstantHelper.BUNDLE_DETAILS_MOVIE, model)
                intent.putExtra(ConstantHelper.BUNDLE_DETAILS_BUNDLE, bundle)
                mCtx.startActivity(intent)
            }
        })

        viewholder.imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(mCtx, MovieeDetailsActivity::class.java)
                var model = mList.get(viewholder.adapterPosition)
                val bundle = Bundle()
                bundle.putSerializable(ConstantHelper.BUNDLE_DETAILS_MOVIE, model)
                intent.putExtra(ConstantHelper.BUNDLE_DETAILS_BUNDLE, bundle)
                mCtx.startActivity(intent)
            }
        })


        return viewholder

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

    fun filterList(filterllist: List<MovieDetailsDataClass?>) {
        mList = filterllist as List<MovieDetailsDataClass>
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.img_movie)
        var rating: RatingBar = itemView.findViewById(R.id.rb_movie)
        var movieName: TextView = itemView.findViewById(R.id.tv_moviename)
        var movieCard: CardView = itemView.findViewById(R.id.cv_movie)
        var movie: LinearLayout = itemView.findViewById(R.id.ll_movie)
        var mainMovieLayout: LinearLayout = itemView.findViewById(R.id.movie_cardlayout)
    }


}