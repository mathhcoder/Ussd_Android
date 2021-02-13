package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_limits.view.*
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Limit
import uz.appme.ussd.model.data.Provider

class TariffLimitAdapter(val provider: Provider, val lang: Lang, val onSelected: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Limit> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LimitViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_tariff_limit, parent, false)
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
            itemView.textViewName.text = if (lang == Lang.RU) limit.nameRu else limit.nameUz
            itemView.textViewInfo.text = if (lang == Lang.RU) limit.valueRu else limit.valueUz
            itemView.setOnClickListener {
                onSelected()
            }

            limit.image?.let {

                val image = if (!it.startsWith("http")) {
                    BuildConfig.BASE_IMAGE_URL + it
                } else it

                Glide.with(itemView)
                    .load(image)
                    .centerCrop()
                    .into(itemView.imageView)

            }
        }
    }
}