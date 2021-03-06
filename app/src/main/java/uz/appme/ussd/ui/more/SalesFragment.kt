package uz.appme.ussd.ui.more

import android.content.Intent
import android.util.Log
import android.net.Uri
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
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.ui.adapter.SalesAdapter
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Sale
import java.lang.Exception


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
        arguments?.getSerializable("data") as? Provider
    }

    private val lang by lazy{
        arguments?.getSerializable("lang") as? Lang
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

        lang?.let{
            adapterSales.lang = it
        }



    }


    private fun onSales(data: List<Sale>) {
        adapterSales.data = data.filter { it.providerId == operator?.id }
    }

    private fun onSaleSelected(sale: Sale) {

        sale.link?.let{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            try{
                startActivity(intent)
            }catch (e:Exception){

            }

        }
    }

}