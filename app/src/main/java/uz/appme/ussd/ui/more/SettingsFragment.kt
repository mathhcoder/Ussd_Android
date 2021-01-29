package uz.appme.ussd.ui.more

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import uz.appme.ussd.ui.dialog.LanguageDialog

class SettingsFragment : BaseFragment() {

    private val dialog by lazy {
        context?.let {
            LanguageDialog(it) { lang ->
                this.context?.let { it1 -> changeLanguage(lang, it1) }
            }
        }
    }

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
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

        cardBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.isDark?.let {
            it.value?.let { data ->
                onTheme(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onTheme(data)
            })
        }

        layoutLang.setOnClickListener {
            dialog?.show()
        }

        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel?.changeTheme()
        }

    }

    private fun onTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        switchTheme?.isChecked = isDark
    }


    fun changeLanguage(lang: String, context: Context) {

        //var reference = StockPreference(context)
        //reference.lang = lang

        GlobalScope.launch {
            delay(200L)
            dialog?.dismiss()
        }
    }
}