package uz.appme.ussd.ui.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_language.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider


class LanguageDialog(
    context: Context,
    val provider: Provider,
    val onItemSelected: (lang: Lang, dialog: BottomSheetDialog) -> (Unit),
) : BottomSheetDialog(context) {


    var lang: Lang? = null
        set(value) {
            field = value
            onLang()
        }


    init {
        setContentView(R.layout.bottom_sheet_language)
        window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.setBackgroundResource(
            R.drawable.background_bottom_sheet_dialog
        )
        window?.navigationBarColor = ContextCompat.getColor(context, R.color.themeColorLight)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardViewUzb.setOnClickListener { onItemSelected(Lang.UZ, this) }
        cardViewRu.setOnClickListener { onItemSelected(Lang.RU, this) }

        onLang()
    }


    private fun onLang() {


        val selectedColor = Color.parseColor(provider.color)
        val unselectedColor = ContextCompat.getColor(context, R.color.themeColorDialogItem)
        val colorTextWhite = Color.parseColor("#FFFFFF")
        val colorTextDark = ContextCompat.getColor(context, R.color.themeColorDark)





        if (lang == Lang.UZ) {

            cardViewUzb.setCardBackgroundColor(selectedColor)
            textViewUzb.setTextColor(colorTextWhite)

            cardViewRu.setCardBackgroundColor(unselectedColor)
            textViewRu.setTextColor(colorTextDark)
        } else {
            cardViewUzb.setCardBackgroundColor(unselectedColor)
            textViewUzb.setTextColor(colorTextDark)

            cardViewRu.setCardBackgroundColor(selectedColor)
            textViewRu.setTextColor(colorTextWhite)
        }
    }

    override fun dismiss() {
        GlobalScope.launch {
            delay(200)
            super.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        this?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}