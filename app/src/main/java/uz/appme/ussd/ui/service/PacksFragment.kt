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
import uz.appme.ussd.ui.adapter.PacksAdapter
import uz.appme.ussd.ui.adapter.CategoriesAdapter
import uz.appme.ussd.model.data.Category
import uz.appme.ussd.model.data.Operator
import uz.appme.ussd.model.data.Pack

class PacksFragment : BaseFragment() {

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

    private val adapterPack by lazy {
        PacksAdapter {
            onPackSelected(it)
        }
    }

    private val operator by lazy {
        arguments?.getSerializable("data") as? Operator
    }

    private val type by lazy {
        arguments?.getInt("type")
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

        textViewHeader?.text = when (type) {
            3 -> getString(R.string.internet)
            4 -> getString(R.string.minutes)
            else -> getString(R.string.sms)

        }
        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.packs?.let {
            it.value?.let { data ->
                onPacks(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onPacks(data)
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
        recyclerViewBody.adapter = adapterPack

    }

    private fun onCategories(data: List<Category>) {
        adapterCategory.data = data.filter { it.operatorId == operator?.id && it.type == type }
    }

    private fun onCategorySelected(category: Category) {
        this.category = category
        viewModel?.packs?.value?.let {
            onPacks(it)
        }
    }

    private fun onPacks(data: List<Pack>) {
        adapterPack.data = data
        data.filter { it.operatorId == operator?.id && it.categoryId == category?.id }
    }

    private fun onPackSelected(pack: Pack) {
        val bundle = bundleOf(Pair("data", pack))
        findNavController().navigate(R.id.action_fragment_tariffs_to_fragment_tariff, bundle)
    }
}