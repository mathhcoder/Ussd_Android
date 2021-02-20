package uz.appme.ussd.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_code.*
import kotlinx.android.synthetic.main.fragment_services.*
import kotlinx.android.synthetic.main.fragment_services.recyclerViewBody
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.CodesAdapter
import uz.appme.ussd.model.data.Code
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.ui.adapter.LimitAdapter
import uz.appme.ussd.ui.adapter.PacksAdapter


class CodesFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }


    private var lang : Lang = Lang.UZ
        set(value){
            field = value
            adapterCode.lang = value
        }


    private val adapterCode by lazy {
        CodesAdapter {code ->
            onCodeSelected(code)
        }

    }


    private val operator by lazy {
        arguments?.getSerializable("data") as? Provider
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewHeader?.text = getString(R.string.codes)

        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.codes?.let{
            it.value?.let { data ->
                onCodes(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onCodes(data)
            })
        }



        viewModel?.lang?.let{
            it.value?.let{ l ->
                lang = l
            }
        }

        recyclerViewCodes.apply {
            layoutManager = LinearLayoutManager(recyclerViewCodes.context)
            adapter = adapterCode
        }


    }


    private fun onCodes(data: List<Code>) {
        adapterCode.data = data.filter { it.providerId == operator?.id }
    }


    private fun onCodeSelected(code : Code) {
        code.ussd?.let{
            Intent(Intent.ACTION_VIEW).apply {
                val ussd = it.replace("#", Uri.encode("#"))
                data = Uri.parse("tel:$ussd")
                try {
                    startActivity(this)
                } catch (e: Exception) {
                }
            }

        }
    }

}