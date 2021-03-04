package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_news_simple.view.*
import kotlinx.android.synthetic.main.cell_sales_image.view.*
import kotlinx.android.synthetic.main.cell_sales_image.view.textViewEndDateImage
import kotlinx.android.synthetic.main.cell_sales_simple.view.*
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Sale
import java.text.SimpleDateFormat
import java.util.*


class SalesAdapter(
    private val onItemSelected: (sale: Sale) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val IMAGE = 1
    val SIMPLE = 2
    private val TIME_FORMAT = "dd.MM.yyyy"


    var data: List<Sale> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang: Lang = Lang.UZ
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int) : Int = if(data[position].image != null &&  data[position].image?.length!! > 3) IMAGE else SIMPLE



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == IMAGE)
            ImageSalesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_sales_image , parent , false)
            )
        else
            SimpleSalesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_sales_simple, parent, false)
            )

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SimpleSalesViewHolder -> {
                data.getOrNull(position)?.let{
                    holder.bind(it)
                }
            }
            is ImageSalesViewHolder ->{
                data.getOrNull(position)?.let{
                    holder.bind(it)
                }
            }
        }

    }

    inner class SimpleSalesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(sale: Sale) {
            itemView.textViewTitleSaleSimple.text = if (lang == Lang.UZ) sale.nameUz else sale.nameRu
            itemView.textViewBodySaleSimple.text = if (lang == Lang.UZ) sale.bodyUz else sale.bodyRu

            sale.start?.let{
                itemView.textViewStartDateSimple.text =
                    SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(it)

            }

            sale.end?.let{
                itemView.textViewEndDateSimple.text =
                    SimpleDateFormat(TIME_FORMAT , Locale.getDefault()).format(it)

            }

            itemView.setOnClickListener {
                onItemSelected(sale)
            }
        }
    }



    inner class ImageSalesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(sale : Sale){

            sale.image?.let {

                val image = if (!it.startsWith("http")) {
                    BuildConfig.BASE_IMAGE_URL + it
                } else it

                try{
                    Glide.with(itemView)
                        .load(image)
                        .centerCrop()
                        .into(itemView.imageViewSales)
                }catch (e : Exception){}


            }

            itemView.textViewTitleSaleImage.text = if (lang == Lang.UZ) sale.nameUz else sale.nameRu
            itemView.textViewBodySaleImage.text = if (lang == Lang.UZ) sale.bodyUz else sale.bodyRu

            sale.start?.let{
                itemView.textViewStartDateImage.text =
                    SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(it)
            }

            sale.end?.let{
                itemView.textViewEndDateImage.text =
                    SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(it)
            }


            itemView.setOnClickListener {
                onItemSelected(sale)
            }
        }
    }

}