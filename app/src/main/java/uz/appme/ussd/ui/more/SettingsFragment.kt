package uz.appme.ussd.ui.more


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.RuntimeLocaleChanger
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

    var lang = arguments?.getSerializable("lang") as? Lang


    private var languagesDialog: LanguageDialog? = null


    private var contact: Contact? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewSelectedLang.text =
            if (lang == Lang.UZ) resources.getString(R.string.langUz) else resources.getString(R.string.langRu)
        cardBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val isDark = viewModel?.isDark() == 1

        provider?.let {
            onProvider(it)
        }


        if (switchTheme.isChecked != isDark)
            switchTheme.isChecked = isDark



        viewTheme.setOnClickListener {
            switchTheme.isChecked = !switchTheme.isChecked
        }

        viewModel?.lang?.let {
            it.value?.let { l ->
                onLang(l)
            }
            it.observe(viewLifecycleOwner, { l ->
                onLang(l)
            })
        }

        textViewHeader.text = resources.getText(R.string.settings)


        viewModel?.contact?.let {
            it.value?.let { c ->
                onContact(c)
            }
            it.observe(viewLifecycleOwner, { c ->
                onContact(c)
            })
        }




        layoutLang.setOnClickListener {

            provider?.let {
                languagesDialog = LanguageDialog(view.context, it) { lang, dialog ->
                    changeLanguage(lang, view.context)
                    val l = if (lang == Lang.UZ) "uz" else "ru"
                    RuntimeLocaleChanger.setNewLocale(requireContext(), l)
                    parentFragmentManager.beginTransaction()
                        .detach(this)
                        .attach(this)
                        .commit()
                    dialog.dismiss()

                }

                lang?.let { l ->
                    languagesDialog?.lang = l
                }

                languagesDialog?.show()
            }

        }



        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            onTheme(isChecked)
            viewModel?.setTheme(isChecked)
        }


        provider?.let {
            onProvider(it)
        }

        layoutInfo.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable("data", provider)
            bundle.putSerializable("lang", lang)


            contact?.let {
                bundle.putSerializable("contact", it)
            }

            findNavController().navigate(R.id.fragment_settings_to_fragment_about, bundle)
        }

    }

    private fun onProvider(provider: Provider) {
        Color.parseColor(provider.color).let { col ->
            switchTheme.thumbTintList = ColorStateList.valueOf(col)
            arrayListOf(
                imageViewTheme,
                imageViewLanguage,
                imageViewInfo
            ).forEach {
                it.setColorFilter(col)
            }
        }
    }

    private fun onLang(l: Lang) {
        lang = l
        textViewSelectedLang.text =
            if (lang == Lang.UZ) resources.getString(R.string.langUz) else resources.getString(R.string.langRu)

    }

    private fun onTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

    }

    private fun changeLanguage(lang: Lang, context: Context) {
        viewModel?.changeLang(lang, context)
    }

    private fun onContact(data: Contact) {
        contact = data
    }
}