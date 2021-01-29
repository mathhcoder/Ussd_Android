package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_pack.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Operator
import uz.appme.ussd.model.data.Pack

class PacksAdapter(
    private val onItemSelected: (pack: Pack) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var data: List<Pack> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var lang = "uz"

    var operator: Operator? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PackagesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_pack, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PackagesViewHolder) {
            data.getOrNull(position)?.let {
                holder.bind(it)
            }
        }
    }

    inner class PackagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pack: Pack) {
            itemView.textViewPackage.text =
                if (lang == "uz") pack.amountUz else pack.amountUz
//            itemView.cardViewPackage.setCardBackgroundColor(Color.parseColor(operator?.color))
        }
    }

}