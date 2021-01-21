package uz.appme.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_service.view.*
import uz.appme.ussd.R
import uz.appme.ussd.data.Operator
import uz.appme.ussd.data.Service

class ServiceAdapter (
    private val onItemSelected : (service : Service) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var data : List<Service> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang = "uz"
    var operator : Operator? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ServiceViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_service, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ServiceViewHolder){
            data.getOrNull(position)?.let{
                holder.bind(it)
            }
        }
    }


    inner class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(service : Service){
            itemView.textViewNameService.text = if(lang == "uz") service.nameUz else service.nameRu
            itemView.textViewDescritionService.text = if(lang == "uz") service.descriptionUz else service.descriptionRu
            itemView.cardViewSerivcePrice.setCardBackgroundColor(Color.parseColor(operator?.color))
            itemView.textViewServicePrice.text = if(lang == "uz") service.subscriptionPriceUz else service.subscriptionPriceRu
        }

    }
}