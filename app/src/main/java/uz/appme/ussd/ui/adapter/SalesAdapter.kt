package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Sale

class SalesAdapter(
    private val onItemSelected: (sale: Sale) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Sale> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang: Lang = Lang.UZ
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SaleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_news_simple, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        data.getOrNull(position)?.let {
            (holder as SaleViewHolder).bind(it)
        }

    }

    inner class SaleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(sale: Sale) {
//            itemView.textViewNameService.text = if (lang == Lang.UZ) news.titleUz else news.titleRu
//            itemView.textViewDescritionService.text =
//                if (lang == Lang.UZ) news.bodyUz else news.bodyRu
//            itemView.textViewDate.text =
//                SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(news.date)
//            itemView.setOnClickListener { onItemSelected(news) }

        }
    }

}