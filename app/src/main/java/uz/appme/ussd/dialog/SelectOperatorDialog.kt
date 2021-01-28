package uz.appme.ussd.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.appme.ussd.R
import uz.appme.ussd.adapter.ProvidersAdapter
import uz.appme.ussd.data.Operator
import uz.appme.ussd.ui.COLOR

class SelectOperatorDialog(
    context: Context,
    private val onItemSelected: (operator: Operator) -> (Unit)
) : BottomSheetDialog(context) {

    var data: List<Operator> = emptyList()
        set(value) {
            listAdapter.data = value
            field = value
        }

    private val listAdapter by lazy {
        ProvidersAdapter {
            onItemSelected(it)
        }
    }

    init {
        setContentView(R.layout.dialog_select_operator)
        window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.setBackgroundResource(
            R.drawable.background_bottom_sheet_dialog
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.findViewById<RecyclerView>(R.id.recyclerView)?.let {
            it.layoutManager = GridLayoutManager(context, 2)
            it.adapter = listAdapter

        }
    }

}