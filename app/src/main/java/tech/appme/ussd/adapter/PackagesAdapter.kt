package tech.appme.ussd.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_packages.view.*
import tech.appme.ussd.R
import tech.appme.ussd.data.Package
import tech.appme.ussd.data.Provider

class PackagesAdapter(
    private val onItemSelected: (packages: Package) -> (Unit)

) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    var data = ArrayList<Package>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var lang = "uz"

    var provider : Provider? = null
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

    override fun getItemCount(): Int  = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PackagesViewHolder){
            data.getOrNull(position)?.let{
                holder.bind(it)
            }
        }
    }

    inner class PackagesViewHolder(view : View) : RecyclerView.ViewHolder(view){

        fun bind(packages: Package){
            itemView.textViewPackage.text = if(lang == "uz") packages.amountUz else packages.amountRu
            itemView.textViewPrice.text = if(lang == "uz") packages.priceUz else packages.priceRu
            //itemView.cardViewPackage.setCardBackgroundColor(Color.parseColor(provider?.color))
        }
    }

}