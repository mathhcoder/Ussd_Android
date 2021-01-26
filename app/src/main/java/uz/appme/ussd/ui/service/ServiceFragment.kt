package uz.appme.ussd.ui.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_services.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.adapter.CategoriesAdapter
import uz.appme.ussd.adapter.ServiceAdapter
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Operator
import uz.appme.ussd.data.Service


class ServiceFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val adapterCategories by lazy {
        CategoriesAdapter {
            category = it
            viewModel?.services?.value?.let { data ->
                onServices(data)
            }
        }
    }

    private val adapterServices by lazy {
        ServiceAdapter {

        }
    }

    private val operator by lazy {
        arguments?.getSerializable("operator") as? Operator
    }

    private var category: Category? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewBackServices.setOnClickListener {
            findNavController().popBackStack()
        }

        recyclerViewSectionsServices.layoutManager = LinearLayoutManager(
            recyclerViewSectionsServices.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerViewSectionsServices.adapter = adapterCategories

        recyclerViewBodyServices.layoutManager =
            LinearLayoutManager(recyclerViewBodyServices.context)
        recyclerViewBodyServices.adapter = adapterServices

        viewModel?.categories?.let {
            it.value?.let { categories ->
                onCategories(categories)
            }
            it.observe(viewLifecycleOwner, { categories ->
                onCategories(categories)
            })
        }

        viewModel?.services?.let {
            it.value?.let { services ->
                onServices(services)
            }
            it.observe(viewLifecycleOwner, { services ->
                onServices(services)
            })
        }

    }


    private fun onCategories(sections: List<Category>) {
        adapterCategories.data = sections.filter { it.operatorId == operator?.id && it.type == 2 }
        if (category == null) category = sections.firstOrNull()
    }

    private fun onServices(services: List<Service>) {
        adapterServices.data = services.filter { it.categoryId == category?.id }
    }


}