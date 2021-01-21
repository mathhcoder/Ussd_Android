package uz.appme.ussd.ui.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_services.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.adapter.SectionAdapter
import uz.appme.ussd.adapter.ServiceAdapter
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Operator
import uz.appme.ussd.data.Service
import uz.appme.ussd.ui.PROVIDER


class ServiceFragment : BaseFragment(){

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapterSection by lazy{
        SectionAdapter({
            onSectionSelected(it)
        })
    }

    private var operator : Operator?= null
        set(value) {
            field = value
            adapterSection.operator = value
            adapterService.operator = value
        }

    private val adapterService by lazy{
        ServiceAdapter({})
    }
    var lang = "uz"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        operator = arguments?.getSerializable(PROVIDER) as Operator?



//        viewModel.sections.let {
//
//            it.value?.let { sections ->
//                onSections(sections)
//            }
//
//            it.observe(viewLifecycleOwner, Observer { sections ->
//                onSections(sections)
//            })
//        }

        viewModel.services.let{
            it.value?.let{services ->
                onServices(services)
            }
            it.observe(viewLifecycleOwner  , Observer { services ->
                onServices(services)
            })
        }

        recyclerViewSectionsServices.layoutManager = LinearLayoutManager(recyclerViewSectionsServices.context , LinearLayoutManager.HORIZONTAL , false)
        recyclerViewSectionsServices.adapter = adapterSection

        recyclerViewBodyServices.layoutManager = LinearLayoutManager(recyclerViewBodyServices.context , LinearLayoutManager.VERTICAL , false)
        recyclerViewBodyServices.adapter = adapterService

        imageViewBackServices.setOnClickListener({
            findNavController().popBackStack()
        })



    }


    fun onSections(sections : List<Category>){
        adapterSection.data = sections
    }

    fun onServices(services : List<Service>){
        adapterService.data = services
    }

    private fun onSectionSelected(section: Category) {
        adapterSection.data = adapterSection.data.map { it.copy(selected = it.id == section.id) }
    }
}