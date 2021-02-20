package uz.appme.ussd.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_service.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.BaseRepository
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Service

class ServiceAdapter(
    private val lang : Lang ,
    private val onItemSelected: (service: Service) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var data: List<Service> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var provider : Provider? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ServiceViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_service, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ServiceViewHolder) {
            data.getOrNull(position)?.let {
                holder.bind(it)
            }
        }
    }


    inner class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(service : Service){
            itemView.textViewTitleSimple.text = if(lang == Lang.UZ) service.nameUz else service.nameRu
            itemView.textViewDescritionSimple.text = if(lang == Lang.UZ) service.descriptionUz else service.descriptionRu
            if(provider != null)
                itemView.cardViewSerivcePrice.setCardBackgroundColor(Color.parseColor(provider?.color))
            itemView.textViewServicePrice.text = if(lang == Lang.UZ) service.subscriptionPriceUz else service.subscriptionPriceRu

            itemView.setOnClickListener {
                onItemSelected(service)
            }
        }

    }
}