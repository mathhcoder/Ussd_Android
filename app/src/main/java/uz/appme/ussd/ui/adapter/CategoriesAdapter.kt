package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_category.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Category
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Operator


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
                .inflate(R.layout.cell_category, parent, false)
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

            itemView.textView.text = if (lang == Lang.UZ) section.nameUz else section.nameRu

            itemView.setOnClickListener {
                onItemSelected(section)
            }


        }
    }

}