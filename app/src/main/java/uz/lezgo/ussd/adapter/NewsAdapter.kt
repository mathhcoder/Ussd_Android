package uz.lezgo.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_news_image.view.*
import kotlinx.android.synthetic.main.cell_news_simple.view.*


import uz.lezgo.ussd.data.News
import uz.lezgo.ussd.R.layout.cell_news_image
import uz.lezgo.ussd.R.layout.cell_news_simple
import uz.lezgo.ussd.data.ProviderModel

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewsAdapter(
    private val onItemSelected: (news: News) -> (Unit)
) :   RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var data  = ArrayList<News>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang: String = "uz"
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var provider : ProviderModel?= null

    override fun getItemViewType(position: Int): Int {
        return if (data.getOrNull(position)?.image?.isNotEmpty() == true) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == 0) {
            SimpleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(cell_news_simple, parent, false)
            )
        } else {
            return ImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(cell_news_image, parent, false)
            )
        }

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SimpleViewHolder -> {
                data.getOrNull(position)?.let {
                    holder.bind(it)
                }
            }
            is ImageViewHolder -> {
                data.getOrNull(position)?.let {
                    holder.bind(it)
                }
            }
        }
    }


    inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(news: News) {
            itemView.textViewTitle.text =if(lang == "ru")news.titleRu else  news.titleUz
            itemView.textViewBody.text = if(lang == "ru")news.bodyRu else  news.bodyUz
            itemView.cardTime.setCardBackgroundColor(Color.parseColor(provider?.color))
            itemView.textViewDate.text =
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(news.date)

            itemView.setOnClickListener {
                onItemSelected(news)
            }
        }
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(news: News) {

            news.image?.let {


                Glide.with(itemView.context)
                    .load(news.image)
                    .centerCrop()
                    .into(itemView.imageViewNews)

            }

            itemView.textViewTitleImage.text = if(lang == "ru") news.titleRu else news.titleUz
            itemView.textViewBodyImage.text = if(lang == "ru") news.bodyRu else news.bodyUz
            itemView.textViewDateImage.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(news.date)
            itemView.cardTimeImage.setCardBackgroundColor(Color.parseColor(provider?.color))

            itemView.setOnClickListener {
                onItemSelected(news)
            }
        }
    }

}