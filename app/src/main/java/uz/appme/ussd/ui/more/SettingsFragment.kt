package uz.appme.ussd.ui.more

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.R
import uz.appme.ussd.dialog.LanguageDialog

class SettingsFragment : BaseFragment(){

    private val dialog by lazy {
        context?.let {
            LanguageDialog(it) {
                lang ->
                this.context?.let { it1 -> changeLanguage(lang , it1) }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardBack.setOnClickListener({

        })


        layoutInfo.setOnClickListener({


        })

        layoutLang.setOnClickListener({
            dialog?.show()
        })

        switchTheme.setOnClickListener({
            if(switchTheme.isChecked){
                Log.e("switch__" , "cliked_1")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.e("switch__", "cliked_0")
            }
        })

     //   textViewHeader.text =if(StockPreference(view.context).lang == "uz") getString(R.string.settingsUz)else getString(R.string.settingsRu)

    }


    fun changeLanguage(lang: String , context : Context){

        //var reference = StockPreference(context)
        //reference.lang = lang

        GlobalScope.launch {
            delay(200L)
            dialog?.dismiss()
        }
    }
}