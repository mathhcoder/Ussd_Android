package uz.appme.ussd.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_code.view.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Code
import uz.appme.ussd.model.data.Lang

class CodesAdapter(
    private val onItemSelected: (code: Code) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<Code> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lang: Lang = Lang.UZ
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SimpleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_code, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        data.getOrNull(position)?.let {
            (holder as SimpleViewHolder).bind(it)
        }

    }

    inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(code: Code) {
            itemView.textViewTitle.text = if (lang == Lang.UZ) code.nameUz else code.nameRu
            itemView.textViewCode.text = code.ussd
            itemView.setOnClickListener { onItemSelected(code) }

        }
    }

}