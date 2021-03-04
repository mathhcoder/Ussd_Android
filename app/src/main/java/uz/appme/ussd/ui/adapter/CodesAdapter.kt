package uz.appme.ussd.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.google.android.datatransport.runtime.logging.Logging.e
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport
import kotlinx.android.synthetic.main.cell_code.view.*
import timber.log.Timber
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Code
import uz.appme.ussd.model.data.Lang

class CodesAdapter(
    private val onItemSelected: (code: Code) -> (Unit),
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
            (holder as SimpleViewHolder).bind(it, position)
        }

    }

    inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(code: Code, position: Int) {

            Timber.e("bind_code" + code.toString())

            if (position != 0)
                itemView.rootView.setPadding(0, 1, 0, 0)


            itemView.textViewTitle.text = if (lang == Lang.UZ) code.nameUz else code.nameRu
            itemView.textViewCode.text = code.ussd
            itemView.setOnClickListener { onItemSelected(code) }

        }
    }

}