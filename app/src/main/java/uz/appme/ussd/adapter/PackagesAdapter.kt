package uz.appme.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_packages.view.*
import uz.appme.ussd.R
import uz.appme.ussd.data.Packages
import uz.appme.ussd.data.Operator

class PackagesAdapter(
    private val onItemSelected: (packages: Packages) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var data = ArrayList<Packages>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var lang = "uz"

    var operator : Operator? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PackagesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_packages, parent, false)
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

        fun bind(packages: Packages) {
            itemView.textViewPackage.text = if (lang == "uz") packages.amountUz else packages.amountUz
            itemView.cardViewPackage.setCardBackgroundColor(Color.parseColor(operator?.color))
        }
    }

}