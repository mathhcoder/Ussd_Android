package tech.appme.ussd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_section_tariffs.view.*
import tech.appme.ussd.R
import tech.appme.ussd.data.Provider
import tech.appme.ussd.data.Category


class SectionAdapter(
    val onItemSelected: (section: Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Category> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var provider : Provider? = null

    var lang = "uz"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SectionTariffsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_section_tariffs, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SectionTariffsViewHolder) {
            data.getOrNull(position)?.let {
                holder.bind(it)
            }
        }
    }


    inner class SectionTariffsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(section: Category) {

            itemView.textViewSection.text = if (lang == "uz") section.nameUz else section.nameRu

//            if (section.selected) {
//                itemView.cardViewSection.setCardBackgroundColor(Color.parseColor(provider?.color))
//            } else {
//                itemView.cardViewSection.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
//            }

            itemView.setOnClickListener {
                onItemSelected(section)
            }


        }
    }

}