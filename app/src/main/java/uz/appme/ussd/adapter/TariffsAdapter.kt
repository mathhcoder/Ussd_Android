package uz.appme.ussd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_tariffs.view.*
import uz.appme.ussd.R
import uz.appme.ussd.data.Operator
import uz.appme.ussd.data.Tariff

class TariffsAdapter(
    private val onItemSelected: (tariff: Tariff) -> (Unit)
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
    var operator : Operator? = null
        set(value) {
            field = value
            adatper.operator = value
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
           itemView.textViewPrice.text = if(lang == "uz") tariff.subscriptionPriceUz else tariff.subscriptionPriceRu
//           itemView.cardViewPrice.setCardBackgroundColor(Color.parseColor(provider?.color))

           itemView.recyclerViewLimits.layoutManager = LinearLayoutManager(itemView.context , LinearLayoutManager.VERTICAL , false)
           itemView.recyclerViewLimits.adapter = adatper
           adatper.data = tariff.limits

           itemView.recyclerViewLimits.setOnClickListener({
               onItemSelected(tariff)
           })

       }
    }


}
