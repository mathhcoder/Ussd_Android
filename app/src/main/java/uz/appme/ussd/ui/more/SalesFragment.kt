package uz.appme.ussd.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_services.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.SalesAdapter
import uz.appme.ussd.model.data.Operator
import uz.appme.ussd.model.data.Sale


class SalesFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val adapterSales by lazy {
        SalesAdapter {
            onSaleSelected(it)
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
        return inflater.inflate(R.layout.fragment_sales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewHeader?.text = getString(R.string.sales)

        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.sales?.let {
            it.value?.let { data ->
                onSales(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onSales(data)
            })
        }

        recyclerViewBody.layoutManager = LinearLayoutManager(recyclerViewBody.context)
        recyclerViewBody.adapter = adapterSales

    }


    private fun onSales(data: List<Sale>) {
        adapterSales.data = data
        data.filter { it.operatorId == operator?.id }
    }

    private fun onSaleSelected(sale: Sale) {

    }

}