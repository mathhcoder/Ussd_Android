package uz.appme.ussd.ui.dialog

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Pack
import uz.appme.ussd.model.data.Provider

sealed class DialogItem(
    val type: DialogType,
    val provider: Provider,
    val lang: Lang,
    @LayoutRes val layout: Int,
) : BaseModel() {

    class LanguagesDialog(
        val action: (lang: Lang) -> Unit,
        val pr: Provider,
        val l: Lang,
    ) : DialogItem(DialogType.LANGUAGE, pr, l, LAYOUT) {
        companion object {
            const val LAYOUT = R.layout.bottom_sheet_language
        }
    }

    class RecyclerViewDialog(
        val action: (provider: Provider) -> Unit,
        pr: Provider,
        l: Lang,
        val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    ) : DialogItem(
        DialogType.RECYCLERVIEW, pr, l, LAYOUT
    ) {
        companion object {
            const val LAYOUT = R.layout.dialog_select_operator
        }
    }

    class PackDialog(
        val action: (pack: Pack) -> Unit,
        val pr: Provider,
        val pack: Pack,
        val l: Lang,
    ) : DialogItem(DialogType.SCROLLVIEW, pr, l, LAYOUT) {
        companion object {
            const val LAYOUT = R.layout.bottom_sheet_package
        }
    }
}