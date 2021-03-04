package uz.appme.ussd.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.OperatorsAdapter
import uz.appme.ussd.model.data.Provider

class SelectOperatorDialog(
    context: Context,
    private val onItemSelected: (provider: Provider) -> (Unit)
) : BottomSheetDialog(context) {

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
        window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.setBackgroundResource(
            R.drawable.background_bottom_sheet_dialog
        )
        window?.navigationBarColor = ContextCompat.getColor(context , R.color.themeColorLight)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.findViewById<RecyclerView>(R.id.recyclerView)?.let {
            it.layoutManager = GridLayoutManager(context, 2)
            it.adapter = listAdapter

        }
    }

    override fun dismiss() {
        GlobalScope.launch {
            delay(200)
            super.dismiss()
        }
    }

}