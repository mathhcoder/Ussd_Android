package uz.appme.ussd.ui.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_services.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.CategoriesAdapter
import uz.appme.ussd.ui.adapter.ServiceAdapter
import uz.appme.ussd.model.data.Category
import uz.appme.ussd.model.data.Operator
import uz.appme.ussd.model.data.Service

class ServiceFragment : BaseFragment() {

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

    private val adapterService by lazy {
        ServiceAdapter {
            onServiceSelected(it)
        }
    }

    private val operator by lazy {
        arguments?.getSerializable("data") as? Operator
    }

    private var category: Category? = null
    private val type = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewHeader?.text = getString(R.string.services)
        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.services?.let {
            it.value?.let { data ->
                onServices(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onServices(data)
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

        recyclerViewCategory.layoutManager = LinearLayoutManager(recyclerViewCategory.context)
        recyclerViewCategory.adapter = adapterCategory

        recyclerViewBody.layoutManager = LinearLayoutManager(recyclerViewBody.context)
        recyclerViewBody.adapter = adapterService

    }

    private fun onCategories(data: List<Category>) {
        adapterCategory.data = data.filter { it.operatorId == operator?.id && it.type == type }
    }

    private fun onCategorySelected(category: Category) {
        this.category = category
        viewModel?.services?.value?.let {
            onServices(it)
        }
    }

    private fun onServices(data: List<Service>) {
        adapterService.data = data
        data.filter { it.operatorId == operator?.id && it.categoryId == category?.id }
    }

    private fun onServiceSelected(service: Service) {
        val bundle = bundleOf(Pair("data", service))
        findNavController().navigate(R.id.action_fragment_tariffs_to_fragment_tariff, bundle)
    }

}