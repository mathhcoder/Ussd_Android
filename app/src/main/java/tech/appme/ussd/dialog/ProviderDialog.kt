package tech.appme.ussd.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import tech.appme.ussd.R
import tech.appme.ussd.adapter.BottomSheetDialogAdapter
import tech.appme.ussd.data.Provider
import tech.appme.ussd.fragment.COLOR

class ProviderDialog(
    context : Context ,
    private val onItemSelected: (provider : Provider) -> (Unit)
) : BottomSheetDialog(context) {

    var data : List<Provider> = emptyList()
        set(value) {
            listAdapter.data = value
            field = value
        }
    var   color : String = "#FFFFFF"
    set(value) {
        listAdapter.selectedColor = value
        Log.e(COLOR ,value)
        field = value
    }

    private val listAdapter by lazy {
        BottomSheetDialogAdapter {
            onItemSelected(it)
        }
    }

    init{
        setContentView(R.layout.bottom_sheet)
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