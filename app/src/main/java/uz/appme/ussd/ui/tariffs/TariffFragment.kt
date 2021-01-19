package uz.appme.ussd.ui.tariffs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tariff.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.R
import uz.appme.ussd.adapter.SectionAdapter
import uz.appme.ussd.adapter.TariffsAdapter
import uz.appme.ussd.data.Provider
import uz.appme.ussd.data.Section
import uz.appme.ussd.data.Tariff
import uz.appme.ussd.ui.PROVIDER


class TariffFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(TariffViewModel::class.java)
    }
    private val adapterTariff by lazy {
        TariffsAdapter({

        })
    }

    private val adapterSection by lazy {
        SectionAdapter {
            onSectionSelected(it)
        }
    }

    var provider : Provider? = null
        set(value) {
            field = value
            adapterSection.provider = provider
            adapterTariff.provider= provider
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariff, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tariffs.let {

            it.value?.let { tariffs ->
                onTariff(tariffs)
            }

            it.observe(viewLifecycleOwner, Observer { tariffs ->
                onTariff(tariffs)
            })
        }
        provider = arguments?.getSerializable(PROVIDER) as Provider?


        viewModel.sections.let {
            it.value?.let { section ->
                onSection(section)
            }

            it.observe(viewLifecycleOwner, Observer { section ->
                onSection(section)
            })
        }

        recyclerViewBody.layoutManager =
            LinearLayoutManager(recyclerViewBody.context, LinearLayoutManager.VERTICAL, false)
        recyclerViewBody.adapter = adapterTariff

        recyclerViewSections.layoutManager =
            LinearLayoutManager(recyclerViewSections.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSections.adapter = adapterSection

        imageViewBack.setOnClickListener({
            findNavController().popBackStack()
        })

    }

    private fun onTariff(tariffs: ArrayList<Tariff>) {
        adapterTariff.data = tariffs
    }

    private fun onTariffSelected(tariff: Tariff) {

    }

    private fun onSection(data: ArrayList<Section>) {
        adapterSection.data = data
        data.getOrNull(0)?.let {
            onSectionSelected(it)
        }
    }

    private fun onSectionSelected(section: Section) {
        adapterSection.data = adapterSection.data.map { it.copy(selected = it.id == section.id) }
    }
}