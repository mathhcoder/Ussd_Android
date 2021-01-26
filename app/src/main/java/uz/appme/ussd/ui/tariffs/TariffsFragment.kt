package uz.appme.ussd.ui.tariffs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import uz.appme.ussd.ui.TARIFF

class TariffsFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var selectedCategory: Category? = null

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

    private var tariffs: List<Tariff> = ArrayList()
        set(value) {
            field = value
            adapterTariff.data = value
        }

    private var categories: List<Category> = ArrayList()
        set(value) {
            field = value
            adapterCategory.data = value
        }

    private val operator by lazy {
        arguments?.getSerializable(OPERATOR) as Operator
    }

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

        viewModel.tariffs.let {
            it.value?.let { data ->
                onTariffs(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onTariffs(data)
            })
        }

        viewModel.categories.let {
            it.value?.let { data ->
                onCategory(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onCategory(data)
            })
        }

        recyclerViewCategory.layoutManager = LinearLayoutManager(recyclerViewCategory.context)
        recyclerViewCategory.adapter = adapterCategory

        recyclerViewBody.layoutManager = LinearLayoutManager(recyclerViewBody.context)
        recyclerViewBody.adapter = adapterTariff

        cardBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun onCategorySelected(category: Category) {
        selectedCategory = category

    }

    private fun onTariffSelected(tariff: Tariff) {
        var bundle = Bundle()
        bundle.putSerializable(TARIFF, tariff)
        findNavController().navigate(R.id.action_fragment_tariffs_to_fragment_tariff, bundle)
    }

    private fun onTariffs(data: List<Tariff>) {
        tariffs =
            data.filter { it.providerId == operator.id && it.categoryId == selectedCategory?.id }
    }

    private fun onCategory(data: List<Category>) {
        categories = data.filter { it.operatorId == operator.id && it.type == type }
    }


}