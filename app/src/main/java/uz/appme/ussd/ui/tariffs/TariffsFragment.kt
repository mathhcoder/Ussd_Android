package uz.appme.ussd.ui.tariffs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.adapter.SectionAdapter
import uz.appme.ussd.adapter.TariffsAdapter
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Operator
import uz.appme.ussd.data.Tariff
import uz.appme.ussd.ui.PROVIDER

class TariffsFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val adapterTariff by lazy {
        TariffsAdapter {
            Log.e("TARIFF" , it.toString() + " clicled")

        }
    }

    private val adapterSection by lazy {
        SectionAdapter {
            onSectionSelected(it)
        }
    }

    var operator : Operator? = null
        set(value) {
            field = value
            adapterSection.operator = operator
            adapterTariff.operator= operator
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariffs, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tariffs.let {

//            it.value?.let { tariffs ->
//                onTariff(tariffs)
//            }
//
//            it.observe(viewLifecycleOwner, Observer { tariffs ->
//                onTariff(tariffs)
//            })
        }
        operator = arguments?.getSerializable(PROVIDER) as Operator?


//        viewModel.sections.let {
//            it.value?.let { section ->
//                onSection(section)
//            }
//
//            it.observe(viewLifecycleOwner, Observer { section ->
//                onSection(section)
//            })
//        }

//        recyclerViewBody.layoutManager =
//            LinearLayoutManager(recyclerViewBody.context, LinearLayoutManager.VERTICAL, false)
//        recyclerViewBody.adapter = adapterTariff
//
//        recyclerViewSections.layoutManager =
//            LinearLayoutManager(recyclerViewSections.context, LinearLayoutManager.HORIZONTAL, false)
//        recyclerViewSections.adapter = adapterSection
//
//        imageViewTariffsBack.setOnClickListener({
//            findNavController().popBackStack()
//        })

    }

    private fun onTariff(tariffs: ArrayList<Tariff>) {
        adapterTariff.data = tariffs
    }

    private fun onTariffSelected(tariff: Tariff) {

    }

    private fun onSection(data: ArrayList<Category>) {
        adapterSection.data = data
        data.getOrNull(0)?.let {
            onSectionSelected(it)
        }
    }

    private fun onSectionSelected(section: Category) {
        adapterSection.data = adapterSection.data.map { it.copy(selected = it.id == section.id) }
    }
}