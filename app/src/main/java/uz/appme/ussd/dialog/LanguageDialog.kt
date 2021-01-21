package uz.appme.ussd.dialog

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_language.*
import uz.appme.ussd.R


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