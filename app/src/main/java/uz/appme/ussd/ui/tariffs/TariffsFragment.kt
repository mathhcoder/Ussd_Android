package uz.appme.ussd.ui.tariffs

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
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.adapter.CategoriesAdapter
import uz.appme.ussd.adapter.TariffsAdapter
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Operator
import uz.appme.ussd.data.Tariff
import uz.appme.ussd.ui.OPERATOR

class TariffsFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val adapterCategory by lazy {
        CategoriesAdapter {
            onCategorySelected(it)
        }
    }

    private val adapterTariff by lazy {
        TariffsAdapter {
            onTariffSelected(it)
        }
    }

    private val operator by lazy {
        arguments?.getSerializable("data") as? Operator
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
            LinearLayoutManager(recyclerViewCategory.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.adapter = adapterCategory

        recyclerViewBody.layoutManager = LinearLayoutManager(recyclerViewBody.context)
        recyclerViewBody.adapter = adapterTariff

    }

    private fun onCategories(data: List<Category>) {
        adapterCategory.data = data.filter { it.operatorId == operator?.id && it.type == type }
    }

    private fun onCategorySelected(category: Category) {
        this.category = category
        viewModel?.tariffs?.value?.let {
            onTariffs(it)
        }
    }

    private fun onTariffs(data: List<Tariff>) {
        adapterTariff.data = data
        // data.filter { it.providerId == operator?.id && it.categoryId == category?.id }
    }

    private fun onTariffSelected(tariff: Tariff) {
        val bundle = bundleOf(Pair("data", tariff))
        findNavController().navigate(R.id.action_fragment_tariffs_to_fragment_tariff, bundle)
    }


}