package uz.appme.ussd.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_tariff.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.BaseRepository
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Tariff

class TariffsAdapter(
    private val provider: Provider,
    val lang : Lang,
    private val onItemSelected: (tariff: Tariff) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Tariff>? = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TariffViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_tariffs, parent, false)
        )

    }

    override fun getItemCount() = data?.size!!


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TariffViewHolder)
            data?.getOrNull(position)?.let {
                holder.bind(it )
            }
    }


    inner class TariffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tariff: Tariff) {

            itemView.setOnClickListener {
                onItemSelected(tariff)
            }
            itemView.textViewTariffName.text = if (lang == Lang.RU) tariff.nameRu else tariff.nameUz
            itemView.textViewPrice.text = if (lang == Lang.RU) tariff.subscriptionPriceRu else tariff.subscriptionPriceUz

            itemView.cardViewPrice.setCardBackgroundColor(Color.parseColor(provider?.color))


            itemView.recyclerViewLimits.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)

            itemView.recyclerViewLimits.adapter = LimitAdapter(
                provider , lang
            ) { onItemSelected(tariff) }.apply {
                this.data = tariff.limits
            }


            itemView.cardViewMain.setOnClickListener{
                onItemSelected(tariff)
            }





        }
    }


}