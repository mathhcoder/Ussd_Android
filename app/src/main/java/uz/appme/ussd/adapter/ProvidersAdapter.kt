package uz.appme.ussd.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_bottom_sheet.view.*
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.R
import uz.appme.ussd.data.Operator

class ProvidersAdapter(
    private val onItemSelected: (operator: Operator) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedColor: String = "#FFFFFF"

    var data: List<Operator> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BottomSheetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_bottom_sheet, parent, false)
        )
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is BottomSheetViewHolder)
            data.getOrNull(position)?.let {
                holder.bind(it)
            }
    }


    inner class BottomSheetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(operator: Operator) {

            itemView.textViewBottomProviderName.text = operator.name
            itemView.cardChecked.setCardBackgroundColor(Color.parseColor(selectedColor))

            if (operator.selected)
                itemView.cardChecked.visibility = View.VISIBLE
            else
                itemView.cardChecked.visibility = View.INVISIBLE

            val image = operator.icon?.let {
                if (!it.startsWith("http")) {
                    BuildConfig.BASE_IMAGE_URL + it
                } else it
            }

            Glide.with(itemView.context)
                .load(image)
                .centerCrop()
                .fitCenter()
                .into(itemView.imageViewBottomProvider)

            itemView.setOnClickListener {
                onItemSelected(operator)
            }
        }
    }

}