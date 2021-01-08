package uz.lezgo.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_limits.view.*
import kotlinx.android.synthetic.main.cell_news_simple.view.*
import uz.lezgo.ussd.R
import uz.lezgo.ussd.data.Limit
import uz.lezgo.ussd.data.News
import uz.lezgo.ussd.data.ProviderModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LimitAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data =  ArrayList<Limit>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var lang = "uz"

    var provider : ProviderModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LimitViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_limits, parent, false)
        )
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is LimitViewHolder)
            data.getOrNull(position)?.let{
                holder.bind(it)
            }
    }

    inner class LimitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(limit: Limit) {
            itemView.textViewLimit.text = if(lang == "uz") limit.limitUz else limit.limitRu
            itemView.textViewLimitDate.text = if(lang == "uz") limit.timeUz else limit.timeRu
        }
    }
}