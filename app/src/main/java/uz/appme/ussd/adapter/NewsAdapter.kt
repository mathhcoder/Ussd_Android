package uz.appme.ussd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_news_image.view.*
import kotlinx.android.synthetic.main.cell_news_simple.view.*
import uz.appme.ussd.R
import uz.appme.ussd.data.News
import uz.appme.ussd.ui.RU
import uz.appme.ussd.ui.TIME_FORMAT
import uz.appme.ussd.ui.UZ
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(
    private val onItemSelected: (news: News) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data : List<News> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang: String = UZ
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return if (data.getOrNull(position)?.image?.isNotEmpty() == true) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == 0) {
            SimpleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_news_simple, parent, false)
            )
        } else {
            return ImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_news_image, parent, false)
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
            itemView.textViewNameService.text = if (lang == RU) news.titleRu else news.titleUz
            itemView.textViewDescritionService.text = if (lang == RU) news.bodyRu else news.bodyUz
            itemView.textViewDate.text =
                SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(news.date)
            itemView.setOnClickListener { onItemSelected(news) }

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

            itemView.textViewTitleImage.text = if (lang == RU) news.titleRu else news.titleUz
            itemView.textViewBodyImage.text = if (lang == RU) news.bodyRu else news.bodyUz
            itemView.textViewDateImage.text =
                SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(news.date)

            itemView.setOnClickListener {
                onItemSelected(news)
            }
        }
    }

}