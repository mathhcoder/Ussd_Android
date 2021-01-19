package tech.appme.ussd.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_language.*
import tech.appme.ussd.R
import tech.appme.ussd.adapter.BottomSheetDialogAdapter
import tech.appme.ussd.data.Provider
import tech.appme.ussd.fragment.COLOR

class LanguageDialog (
    context : Context,
    private val onItemSelected: (string : String) -> (Unit)
) : BottomSheetDialog(context) {



    init{
        setContentView(R.layout.bottom_sheet_language)
        window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.setBackgroundResource(
            R.drawable.background_bottom_sheet_dialog
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardViewUzb.setOnClickListener({onItemSelected("uz")})
        cardViewRu.setOnClickListener({onItemSelected("ru")})



    }

}