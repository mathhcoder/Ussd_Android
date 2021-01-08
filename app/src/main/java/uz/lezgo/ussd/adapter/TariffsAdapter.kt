package uz.lezgo.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_tariffs.view.*
import uz.lezgo.ussd.R
import uz.lezgo.ussd.data.News
import uz.lezgo.ussd.data.ProviderModel
import uz.lezgo.ussd.data.Tariff

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
    var provider : ProviderModel? = null
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
