package uz.lezgo.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_packages.view.*
import kotlinx.android.synthetic.main.cell_service.view.*
import kotlinx.android.synthetic.main.cell_tariffs.view.*
import uz.lezgo.ussd.R
import uz.lezgo.ussd.data.ProviderModel
import uz.lezgo.ussd.data.Service

class ServiceAdapter (
    private val onItemSelected : (service : Service) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var data = ArrayList<Service>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang = "uz"
    var provider : ProviderModel? = null

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