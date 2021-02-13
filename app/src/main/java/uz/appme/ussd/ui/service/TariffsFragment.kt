package uz.appme.ussd.ui.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tariffs.*
import kotlinx.android.synthetic.main.layout_header.*
import timber.log.Timber
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.CategoriesAdapter
import uz.appme.ussd.ui.adapter.TariffsAdapter
import uz.appme.ussd.model.data.Category
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Tariff

class TariffsFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val adapterCategory by lazy {
        provider?.let { p ->
            lang?.let { l ->
                CategoriesAdapter(p, l) {
                    onCategorySelected(it)
                }
            }
        }
    }

    private val adapterTariff by lazy {
        provider?.let { p ->
            lang?.let { l ->
                TariffsAdapter(p, l) {
                    onTariffSelected(it)
                }
            }
        }
    }

    private val provider by lazy {
        arguments?.getSerializable("data") as? Provider
    }

    private val lang by lazy {
        arguments?.getSerializable("lang") as? Lang
    }

    private var category: Category? = null
    private val type = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariffs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewHeader?.text = getString(R.string.tariff)
        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.tariffs?.let {
            it.value?.let { data ->
                onTariffs(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onTariffs(data)
            })
        }

        viewModel?.categories?.let {
            it.value?.let { data ->
                onCategories(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onCategories(data)
            })
        }

        recyclerViewCategory.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.adapter = adapterCategory

        recyclerViewBody.adapter = adapterTariff
        recyclerViewBody.layoutManager = LinearLayoutManager(view.context)

    }

    private fun onCategories(data: List<Category>) {
        val filteredData = data.filter { it.providerId == provider?.id && it.type == type }

        if (category == null) {
            filteredData.firstOrNull()?.let {
                onCategorySelected(it)
            }
        }

        val categories = filteredData.map { c -> c.copy(selected = c.id == category?.id) }

        adapterCategory?.data = categories

    }

    private fun onCategorySelected(category: Category) {
        this.category = category
        adapterCategory?.data =
            adapterCategory?.data?.map { d -> d.copy(selected = d.id == category.id) }
                ?: emptyList()
        viewModel?.tariffs?.value?.let {
            onTariffs(it)
        }
    }

    private fun onTariffs(data: List<Tariff>) {
        adapterTariff?.data = data.filter { it.categoryId == category?.id }
    }

    private fun onTariffSelected(tariff: Tariff) {
        val bundle = bundleOf(Pair("data", tariff), Pair("lang", lang), Pair("provider", provider))
        findNavController().navigate(R.id.action_fragment_tariffs_to_fragment_tariff, bundle)
    }


}