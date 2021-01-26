package uz.appme.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_section_tariffs.view.*
import uz.appme.ussd.R
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Lang
import uz.appme.ussd.data.Operator


class CategoriesAdapter(
    val onItemSelected: (section: Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var operator: Operator? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang: Lang = Lang.UZ
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var data: List<Category> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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

            itemView.textViewSection.text = if (lang == Lang.UZ) section.nameUz else section.nameRu

            if (section.selected == true) {
                val color = try {
                    Color.parseColor(operator?.color)
                } catch (e: Exception) {
                    Color.parseColor("#000")
                }
                itemView.cardViewSection.setCardBackgroundColor(color)
            } else {
                itemView.cardViewSection.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            }

            itemView.setOnClickListener {
                onItemSelected(section)
            }


        }
    }

}