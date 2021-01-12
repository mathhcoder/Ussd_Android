package tech.appme.ussd.fragment.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_services.*
import tech.appme.ussd.BaseFragment
import tech.appme.ussd.R
import tech.appme.ussd.adapter.SectionAdapter
import tech.appme.ussd.adapter.ServiceAdapter
import tech.appme.ussd.data.Provider
import tech.appme.ussd.data.Category
import tech.appme.ussd.data.Service
import tech.appme.ussd.fragment.PROVIDER


class ServiceFragment : BaseFragment(){

    private val viewModel by lazy {
        ViewModelProvider(this).get(ServiceViewModel::class.java)
    }

    private val adapterSection by lazy{
        SectionAdapter({
            onSectionSelected(it)
        })
    }

    private var provider : Provider?= null
        set(value) {
            field = value
            adapterSection.provider = value
            adapterService.provider = value
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

        provider = arguments?.getSerializable(PROVIDER) as Provider?



        viewModel.sections.let {

            it.value?.let { sections ->
                onSections(sections)
            }

            it.observe(viewLifecycleOwner, Observer { sections ->
                onSections(sections)
            })
        }

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


    fun onSections(sections : ArrayList<Category>){
        adapterSection.data = sections
    }

    fun onServices(services : ArrayList<Service>){
        adapterService.data = services
    }

    private fun onSectionSelected(section: Category) {
        adapterSection.data = adapterSection.data.map { it.copy(selected = it.id == section.id) }
    }
}