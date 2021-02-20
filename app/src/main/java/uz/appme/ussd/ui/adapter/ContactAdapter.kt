package uz.appme.ussd.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_contact.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Provider

import uz.appme.ussd.model.data.SingleContact

class ContactAdapter(
    private val  onItemSelected : (contact : SingleContact) ->(Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var provider : Provider? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }
    var data = emptyList<SingleContact>()
        set(value){
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_contact, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContactViewHolder){
            data.getOrNull(position)?.let{
                holder.bind(it)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ContactViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(data : SingleContact){
            val context = itemView.context
            when(data.type) {

                0 -> {
                    itemView.imageViewContact.setImageResource(R.drawable.ic_phone)
                    itemView.textViewContact.text = context.getString(R.string.phone)
                }
                1 -> {
                    itemView.imageViewContact.setImageResource(R.drawable.ic_telegram)
                    itemView.textViewContact.text = context.getString(R.string.telegram)
                }
                2 -> {
                    itemView.imageViewContact.setImageResource(R.drawable.ic_facebook)
                    itemView.textViewContact.text = context.getString(R.string.facebook)
                }
                3 -> {
                    itemView.imageViewContact.setImageResource(R.drawable.ic_instagram)
                    itemView.textViewContact.text = context.getString(R.string.instagram)
                }

            }
            itemView.imageViewContact.setColorFilter(Color.parseColor(provider?.color))
            itemView.setOnClickListener {
                onItemSelected(data)
            }
        }
    }
}

