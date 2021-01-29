package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_limits.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Limit
import uz.appme.ussd.model.data.Operator

class LimitAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Limit> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang = "uz"

    var operator: Operator? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LimitViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_limits, parent, false)
        )
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LimitViewHolder)
            data.getOrNull(position)?.let {
                holder.bind(it)
            }
    }

    inner class LimitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(limit: Limit) {
            itemView.textViewLimit.text = if (lang == "uz") limit.nameUz else limit.nameRu
            itemView.textViewLimitDate.text = if (lang == "uz") limit.valueUz else limit.valueRu
        }
    }
}