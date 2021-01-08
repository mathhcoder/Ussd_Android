package tech.appme.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_tariffs.view.*
import tech.appme.ussd.R
import tech.appme.ussd.data.News
import tech.appme.ussd.data.Provider
import tech.appme.ussd.data.Tariff

class TariffsAdapter(
    private val onItemSelected: (news: News) -> (Unit)
) :   RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var data  = arrayListOf<Tariff>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    var lang = "uz"

    private val adatper by lazy{
        LimitAdapter()
    }
    var provider : Provider? = null
        set(value) {
            field = value
            adatper.provider = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TariffViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_tariffs, parent, false)
            )

    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TariffViewHolder)
            data.getOrNull(position)?.let{
                holder.bind(it)
            }
    }


    inner class TariffViewHolder(view : View) : RecyclerView.ViewHolder(view){
       fun bind(tariff : Tariff  ){

           itemView.textViewTariffName.text = if(lang == "uz") tariff.nameUz else tariff.nameRu
           itemView.textViewPrice.text = if(lang == "uz") tariff.priceUz else tariff.priceRu
           itemView.cardViewPrice.setCardBackgroundColor(Color.parseColor(provider?.color))

           itemView.recyclerViewLimits.layoutManager = LinearLayoutManager(itemView.context , LinearLayoutManager.VERTICAL , false)
           itemView.recyclerViewLimits.adapter = adatper
           adatper.data = tariff.limits

       }
    }


}
