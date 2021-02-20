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
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Service

class ServicesFragment : BaseFragment() {

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

    private val adapterService by lazy {
        lang?.let { l ->
            ServiceAdapter(l) {
                onServiceSelected(it)
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

        adapterService?.provider = provider
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.adapter = adapterCategory

        recyclerViewBody.layoutManager = LinearLayoutManager(view.context)
        recyclerViewBody.adapter = adapterService

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
        viewModel?.services?.value?.let {
            onServices(it)
        }
    }

    private fun onServices(data: List<Service>) {
        adapterService?.data = data.filter { it.categoryId == category?.id }
    }

    private fun onServiceSelected(service: Service) {
        val bundle = bundleOf(Pair("data", service), Pair("lang", lang), Pair("provider", provider))
        findNavController().navigate(R.id.action_fragment_service_to_fragment_service, bundle)
    }

}