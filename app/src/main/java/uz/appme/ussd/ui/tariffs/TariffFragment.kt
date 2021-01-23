package uz.appme.ussd.ui.tariffs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.adapter.CategoryAdapter
import uz.appme.ussd.adapter.TariffsAdapter
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Tariff

class TariffFragment : BaseFragment(){

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var selectedCategory : Category? = null

    private val adapterCategory by lazy{
        CategoryAdapter{
            onCategory(it)
        }
    }

    private val adapterTariff by lazy{
        TariffsAdapter{

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



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariff, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun onCategory(category: Category){
        selectedCategory = category
    }


}