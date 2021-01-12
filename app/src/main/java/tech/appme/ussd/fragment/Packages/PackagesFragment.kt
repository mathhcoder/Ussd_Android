package tech.appme.ussd.fragment.Packages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_packages.*
import tech.appme.ussd.BaseFragment
import tech.appme.ussd.R
import tech.appme.ussd.adapter.PackagesAdapter
import tech.appme.ussd.adapter.SectionAdapter
import tech.appme.ussd.data.Package
import tech.appme.ussd.data.Category

class PackagesFragment  : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PackagesViewModel::class.java)
    }

    private val adapterSection by lazy{
        SectionAdapter({
            onSectionSelected(it)
        })
    }

    private val adapterPackage by lazy{
        PackagesAdapter({
            onPackageSelected(it)
        })
    }

    val provider : ViewModelProvider? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_packages, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sections.let{
            it.value?.let{sections ->
                onSection(sections)
            }

            it.observe(viewLifecycleOwner, Observer { sections  ->
                onSection(sections)
            })
        }

        viewModel.packages.let{
            it.value?.let{packages ->
                onPackage(packages)
            }
            it.observe(viewLifecycleOwner , Observer { packages ->
                onPackage(packages)
            })
        }

        recyclerViewSectionsPackage.layoutManager = LinearLayoutManager(recyclerViewSectionsPackage.context , LinearLayoutManager.HORIZONTAL , false)
        recyclerViewSectionsPackage.adapter = adapterSection

        recyclerViewBodyPackage.layoutManager = LinearLayoutManager(recyclerViewBodyPackage.context , LinearLayoutManager.VERTICAL , false)
        recyclerViewBodyPackage.adapter = adapterPackage




    }

    private fun onSectionSelected(section: Category) {
        adapterSection.data = adapterSection.data.map { it.copy(selected = it.id == section.id) }
    }
    private fun onPackageSelected(packages : Package){

    }
    private fun onPackage(packages : ArrayList<Package>){
        adapterPackage.data = packages
    }
    private fun onSection(sections: ArrayList<Category>){
        adapterSection.data = sections
    }
}