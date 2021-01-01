package uz.lezgo.ussd.dialog

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.lezgo.ussd.R
import uz.lezgo.ussd.adapter.BottomSheetDialogAdapter
import uz.lezgo.ussd.data.ProviderModel
import uz.lezgo.ussd.R.layout.*

class BottomSheet(
    context : Context ,
    private val onItemSelected: (provider : ProviderModel) -> (Unit)
) : BottomSheetDialog(context) {

    var data : List<ProviderModel> = emptyList()
        set(value) {
            listAdapter.data = value
            field = value
        }

    private val listAdapter by lazy {
        BottomSheetDialogAdapter {
            onItemSelected(it)
        }
    }

    init{
        setContentView(bottom_sheet)
        window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.setBackgroundResource(
            R.drawable.background_bottom_sheet_dialog
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.findViewById<RecyclerView>(R.id.recyclerView)?.let {
            it.layoutManager = GridLayoutManager(context , 2)
            it.adapter = listAdapter
        }
    }

}