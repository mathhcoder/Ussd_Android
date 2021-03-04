package uz.appme.ussd.ui.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_language.*
import kotlinx.android.synthetic.main.bottom_sheet_package.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import android.util.Log

class BaseDialog(private val c: Context, val data: DialogItem) : BottomSheetDialog(c) {

    init {
        val rootRes = R.layout.base_dialog
        val root = LayoutInflater.from(c).inflate(rootRes, null, false)
        val dialogRoot = root.findViewById<LinearLayout>(R.id.rootLayout)

        val layout = LayoutInflater.from(c).inflate(data.layout, null, false)
        dialogRoot.addView(layout)

        setContentView(root)
        setBottomSheetDialogHeight(root)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang = data.lang


        val selectedColor = Color.parseColor(data.provider.color)
        val unselectedColor = ContextCompat.getColor(context, R.color.themeColorDialogItem)

        val colorTextWhite = Color.parseColor("#FFFFFF")
        val colorTextDark = ContextCompat.getColor(context, R.color.colorSecondary)


        Log.e("_data_", data.toString())

        when (data) {
            is DialogItem.LanguagesDialog -> {
                if (data.lang == Lang.UZ) {
                    cardViewUzb.setCardBackgroundColor(selectedColor)
                    textViewUzb.setTextColor(colorTextWhite)

                    cardViewRu.setCardBackgroundColor(unselectedColor)
                    textViewRu.setTextColor(colorTextDark)

                } else {
                    cardViewRu.setCardBackgroundColor(selectedColor)
                    textViewRu.setTextColor(colorTextWhite)

                    cardViewUzb.setCardBackgroundColor(unselectedColor)
                    textViewUzb.setTextColor(colorTextDark)
                }
                cardViewUzb.setOnClickListener {
                    data.action(Lang.UZ)
                }
                cardViewRu.setOnClickListener {
                    data.action(Lang.RU)
                }
            }

            is DialogItem.PackDialog -> {

                cardViewSelectPack.setOnClickListener {
                    data.action(data.pack)
                }
            }

            is DialogItem.RecyclerViewDialog -> {

            }
        }
    }


    private fun setBottomSheetDialogHeight(root: View) {
        val observer = root.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(root.parent as View)
                behavior.peekHeight = root.height
            }
        })
    }
}