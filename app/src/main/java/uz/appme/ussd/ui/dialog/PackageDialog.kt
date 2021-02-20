package uz.appme.ussd.ui.dialog

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_language.*
import kotlinx.android.synthetic.main.bottom_sheet_package.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Pack
import uz.appme.ussd.model.data.Provider

class PackageDialog(
    context: Context ,
    val pack : Pack,
    val provider : Provider,
    private val lang : Lang,
    private val onItemSelected :( dialog : BottomSheetDialog) -> (Unit)

) : BottomSheetDialog(context) {

    init{
        setContentView(R.layout.bottom_sheet_package)
        window?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.setBackgroundResource(
            R.drawable.background_bottom_sheet_dialog
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardViewSelectPack.setCardBackgroundColor(Color.parseColor(provider.color))
        textViewPackDescription.text = if(lang == Lang.UZ) pack.descriptionUz  else pack.descriptionRu

        textViewPackAmount.text = if(lang == Lang.UZ) pack.amountUz else pack.amountRu
        textViewPackPrice.text = if(lang == Lang.UZ) pack.priceUz else pack.priceRu

        cardViewSelectPack.setOnClickListener {
            onItemSelected( this)
        }

    }
}