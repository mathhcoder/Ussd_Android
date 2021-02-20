package uz.appme.ussd.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_category.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Category
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider


class CategoriesAdapter(
    val provider: Provider,
    val lang: Lang,
    val onItemSelected: (section: Category) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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

        fun bind(category: Category) {

            itemView.textViewCategoryName.text = if (lang == Lang.UZ) category.nameUz else category.nameRu

            itemView.setOnClickListener {
                onItemSelected(category)
            }

            val defColor = ContextCompat.getColor(itemView.context, R.color.themeColorLight)

            val defColorBack = ContextCompat.getColor(itemView.context, R.color.themeColorDark)


            val color = try {
                Color.parseColor(provider?.color)
            } catch (e: Exception) {
                defColor
            }

            if (category.selected == true) {
                itemView.cardViewSection.setCardBackgroundColor(color)
                itemView.textViewCategoryName.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                itemView.cardViewSection.setCardBackgroundColor(defColor)
                itemView.textViewCategoryName.setTextColor(defColorBack)

            }

        }
    }

}