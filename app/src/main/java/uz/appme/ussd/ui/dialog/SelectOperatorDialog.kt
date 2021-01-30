package uz.appme.ussd.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.OperatorsAdapter
import uz.appme.ussd.model.data.Provider

class SelectOperatorDialog(
    context: Context,
    private val onItemSelected: (provider: Provider) -> (Unit)
) : BottomSheetDialog(context, R.style.UssdBottomSheetDialog) {

    var data: List<Provider> = emptyList()
        set(value) {
            listAdapter.data = value
            field = value
        }

    private val listAdapter by lazy {
        OperatorsAdapter {
            onItemSelected(it)
        }
    }

    init {
        setContentView(R.layout.dialog_select_operator)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.findViewById<RecyclerView>(R.id.recyclerView)?.let {
            it.layoutManager = GridLayoutManager(context, 2)
            it.adapter = listAdapter

        }
    }

}