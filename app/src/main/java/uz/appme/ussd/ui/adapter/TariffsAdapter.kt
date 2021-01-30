package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_tariffs.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Tariff

class TariffsAdapter(
    private val provider: Provider,
    private val lang: Lang,
    private val onItemSelected: (tariff: Tariff) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Tariff> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val adapterLimit by lazy {
        LimitAdapter(provider, lang)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TariffViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_tariffs, parent, false)
        )

    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TariffViewHolder)
            data.getOrNull(position)?.let {
                holder.bind(it)
            }
    }


    inner class TariffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tariff: Tariff) {

            itemView.textViewTariffName.text = if (lang == Lang.RU) tariff.nameRu else tariff.nameUz
            itemView.textViewPrice.text =
                if (lang == Lang.RU) tariff.subscriptionPriceRu else tariff.subscriptionPriceUz
//           itemView.cardViewPrice.setCardBackgroundColor(Color.parseColor(provider?.color))

            itemView.recyclerViewLimits.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            itemView.recyclerViewLimits.adapter = adapterLimit
//           adapterLimt.data =

            itemView.recyclerViewLimits.setOnClickListener({
                onItemSelected(tariff)
            })


        }
    }


}
