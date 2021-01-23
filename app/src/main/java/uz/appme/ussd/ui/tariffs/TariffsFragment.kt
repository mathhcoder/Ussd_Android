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
import uz.appme.ussd.adapter.CategoryAdapter
import uz.appme.ussd.adapter.TariffsAdapter
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Tariff
import uz.appme.ussd.ui.TARIFF

class TariffsFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var selectedCategory : Category? = null

    private val adapterCategory by lazy{
        CategoryAdapter{
            onCategorySelected(it)
        }
    }

    private val adapterTariff by lazy{
        TariffsAdapter{
            onTariffSelected(it)
        }
    }

    private var tariffs : List<Tariff> = ArrayList()
        set(value){
            field = value
            adapterTariff.data = value
        }

    private var categories : List<Category> = ArrayList()
        set(value){
            field = value
            adapterCategory.data = value
        }

    private val providerId by lazy{
        1
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

        viewModel.tariffs.let{
            it.value?.let{data->
                onTariffs(data)
            }
            it.observe(viewLifecycleOwner , { data ->
                onTariffs(data)
            })
        }

        viewModel.categories.let{
            it.value?.let{data ->
                onCategory(data)
            }
            it.observe(viewLifecycleOwner , {data ->
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

    private fun onCategorySelected(category: Category){
        selectedCategory = category
    }

    private fun onTariffSelected(tariff: Tariff){
        var bundle = Bundle()
        bundle.putSerializable(TARIFF , tariff)
        findNavController().navigate(R.id.action_fragment_tariffs_to_fragment_tariff , bundle)
    }

    private fun onTariffs(data: List<Tariff>){
        tariffs = data
    }

    private fun onCategory(data : List<Category>){
        categories = data
    }



}