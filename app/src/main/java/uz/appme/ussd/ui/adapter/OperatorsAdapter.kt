package uz.appme.ussd.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_operator.view.*
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Provider
import android.util.Log
class OperatorsAdapter(
    private val onItemSelected: (provider: Provider) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedColor: String = "#FFFFFF"

    var data: List<Provider> = ArrayList()
        set(value) {
            Log.e("all_providers" , value.toString())
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BottomSheetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_operator, parent, false)
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
        fun bind(provider: Provider) {


            itemView.textViewCategoryName.text = provider.name
            itemView.cardChecked.setCardBackgroundColor(Color.parseColor(provider.color))

            Log.e("provider_single" , provider.toString())
            if (provider.selected)
                itemView.cardChecked.visibility = View.VISIBLE
            else
                itemView.cardChecked.visibility = View.INVISIBLE

            val image = provider.icon?.let {
                if (!it.startsWith("http")) {
                    BuildConfig.BASE_IMAGE_URL + it
                } else it
            }


            Glide.with(itemView.context)
                .load(image)
                .centerCrop()
                .fitCenter()
                .into(itemView.imageView)

            itemView.setOnClickListener {
                onItemSelected(provider)
            }
        }
    }


}