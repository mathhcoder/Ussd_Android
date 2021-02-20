package uz.appme.ussd.ui.more

import android.util.Log
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.layout_header.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Contact
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.ui.dialog.LanguageDialog

class SettingsFragment : BaseFragment() {



    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val provider by lazy {
        arguments?.getSerializable("data") as? Provider
    }

    private var contact : Contact? = null

    private var lang : Lang = Lang.UZ

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //val isDark = AppCompatDelegate.getDefaultNightMode() ==   AppCompatDelegate.MODE_NIGHT_NO
        //viewModel?.setTheme(isDark)
        val isDark = viewModel?.isDark() == 1


        if(switchTheme.isChecked != isDark)
            switchTheme.isChecked = isDark

        viewTheme.setOnClickListener {
            switchTheme.isChecked =  !switchTheme.isChecked
        }


        textViewHeader.text = resources.getText(R.string.settings)

        viewModel?.lang?.let{
            it.value?.let{l ->
                onLang(l)
            }
            it.observe(viewLifecycleOwner , {l ->
                onLang(l)
            })
        }

        viewModel?.contact?.let{
            it.value?.let{c ->
                onContact(c)
            }
            it.observe(viewLifecycleOwner , {c ->
                onContact(c)
            })
        }


        provider?.let{ p ->
            layoutLang.setOnClickListener {
                LanguageDialog(it.context , p) { lang, dialog ->
                    dialog.dismiss()
                    this.context?.let { it1 -> changeLanguage(lang, it1) }
                }.show()
            }
        }





        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            onTheme(isChecked)
            viewModel?.setTheme(isChecked)
        }
        switchTheme. = Color.parseColor(provider.color)


        provider?.let {
            onProvider(it)
        }

        layoutInfo.setOnClickListener{

            val bundle = Bundle()
            bundle.putSerializable("data" , provider)
            bundle.putSerializable("lang" , lang)


            contact?.let{
                bundle.putSerializable("contact" , it)
            }

            findNavController().navigate(R.id.fragment_settings_to_fragment_about , bundle)
        }

    }

    private fun onProvider(provider: Provider) {
        Color.parseColor(provider.color).let { col ->
            arrayListOf(
                imageViewTheme,
                imageViewLanguage,
                imageViewInfo
            ).forEach {
                it.setColorFilter(col)
            }
        }
    }

    private fun onTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun onLang(lang : Lang){
        this.lang = lang
        textViewSelectedLang.text = if(lang == Lang.UZ) resources.getString(R.string.langUz) else resources.getString(R.string.langRu)
    }


    private fun changeLanguage(lang: Lang, context: Context) {
        viewModel?.changeLang(lang , context)

    }

    private fun onContact(data : Contact){
        Log.e("contact" , data.toString())
        contact = data
    }
}