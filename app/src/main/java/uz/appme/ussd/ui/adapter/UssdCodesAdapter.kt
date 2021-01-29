package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_ussd.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.UssdCodes


class UssdCodesAdapter(
    private val onitemSelected : (ussd : UssdCodes) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    var data : List<UssdCodes> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UssdCodesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_ussd, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is UssdCodesViewHolder){
            data.getOrNull(position)?.let{
                holder.bind(it)
            }
        }
    }

    override fun getItemCount() = data.size


    inner class UssdCodesViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(ussd : UssdCodes){
            itemView.textViewCodeTitle.text = ussd.name
            itemView.textViewCodeNumber.text = ussd.number
            itemView.setOnClickListener({onitemSelected(ussd)})
        }


    }

}