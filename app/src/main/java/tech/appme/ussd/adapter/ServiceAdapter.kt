package tech.appme.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_service.view.*
import tech.appme.ussd.R
import tech.appme.ussd.data.Provider
import tech.appme.ussd.data.Service

class ServiceAdapter (
    private val onItemSelected : (service : Service) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var data = ArrayList<Service>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang = "uz"
    var provider : Provider? = null

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
            itemView.textViewTitle.text = if(lang == "uz") service.titleUz else service.titleRu
            itemView.textViewBody.text = if(lang == "uz") service.bodyUz else service.bodyRu
            itemView.cardViewSerivcePrice.setCardBackgroundColor(Color.parseColor(provider?.color))
            itemView.textViewServicePrice.text = if(lang == "uz") service.priceUz else service.priceRu
        }

    }
}