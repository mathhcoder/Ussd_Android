package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_limits.view.*
import kotlinx.android.synthetic.main.cell_news_image.view.*
import kotlinx.android.synthetic.main.cell_news_simple.view.*
import kotlinx.android.synthetic.main.cell_operator.view.*
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.R
import android.util.Log
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.News
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(
    private val onItemSelected: (news: News) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TIME_FORMAT = "dd.MM.yyyy"

    var data: List<News> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang: Lang = Lang.UZ
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
            itemView.textViewTitleNewsSimple.text = if (lang == Lang.UZ) news.titleUz else news.titleRu
            itemView.textViewBodyNewsSimple.text =
                if (lang == Lang.UZ) news.bodyUz else news.bodyRu
            itemView.textViewDateSimple.text =
                SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(news.date)
            itemView.setOnClickListener { onItemSelected(news) }

        }
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(news: News) {

            news.image?.let {

                val image = if (!it.startsWith("http")) {
                    BuildConfig.BASE_IMAGE_URL + it
                } else it

                try{
                    Glide.with(itemView)
                        .load(image)
                        .centerCrop()
                        .into(itemView.imageViewNews)
                }catch (e:Exception){
                    Log.e("catch" , e.message)
                }

            }

            itemView.textViewTitleImage.text = if (lang == Lang.UZ) news.titleUz else news.titleRu
            itemView.textViewDescription.text = if (lang == Lang.UZ) news.bodyUz else news.bodyRu
            itemView.textViewDateImage.text =
                SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(news.date)

            itemView.setOnClickListener {
                onItemSelected(news)
            }
        }
    }

}