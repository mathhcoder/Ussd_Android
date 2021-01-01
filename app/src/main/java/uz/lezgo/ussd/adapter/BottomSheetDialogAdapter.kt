package uz.lezgo.ussd.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.cell_bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_banner.*
import uz.lezgo.ussd.R
import uz.lezgo.ussd.data.ProviderModel

class BottomSheetDialogAdapter(
    private val onItemSelected: (provider: ProviderModel) -> (Unit)
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var data: List<ProviderModel> = ArrayList()
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

        if(holder is BottomSheetViewHolder)
            data.getOrNull(position)?.let {
                holder.bind(it)
            }
    }


    inner class BottomSheetViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(provider : ProviderModel){
            itemView.textViewBottomProviderName.text = provider.name

            if(provider.selected)
                itemView.BottomProviderChecked.visibility = View.VISIBLE
            else
                itemView.BottomProviderChecked.visibility = View.INVISIBLE

            Glide.with(itemView.context)
                .load(provider.icon) // image url
                .centerCrop()
                .fitCenter()// resizing
                .into(itemView.imageViewBottomProvider)

            itemView.setOnClickListener({
                onItemSelected(provider)
            })
        }
    }

}