package uz.appme.ussd.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.CodesAdapter
import uz.appme.ussd.model.data.Code
import uz.appme.ussd.model.data.Operator


class NewsFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val adapterCode by lazy {
        CodesAdapter {
            onCodeSelected(it)
        }
    }

    private val operator by lazy {
        arguments?.getSerializable("data") as? Operator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewHeader?.text = getString(R.string.news)

        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.codes?.let {
            it.value?.let { data ->
                onCodes(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onCodes(data)
            })
        }

        recyclerViewBody.layoutManager = LinearLayoutManager(recyclerViewBody.context)
        recyclerViewBody.adapter = adapterCode

    }

    private fun onCodes(data: List<Code>) {
        adapterCode.data = data
        data.filter { it.operatorId == operator?.id }
    }

    private fun onCodeSelected(code: Code) {

    }
}