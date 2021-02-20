package uz.appme.ussd.ui.service

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
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
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Pack

class PacksFragment : BaseFragment() {

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

    private val adapterPack by lazy {
        lang?.let {
            PacksAdapter(it) { p ->
                onPackSelected(p)
            }
        }
    }

    private val provider by lazy {
        arguments?.getSerializable("data") as? Provider
    }

    private val lang by lazy {
        arguments?.getSerializable("lang") as? Lang
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
        adapterPack?.provider = provider

        viewModel?.categories?.let {
            it.value?.let { data ->
                onCategories(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onCategories(data)
            })
        }

        recyclerViewCategory.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.adapter = adapterCategory

        recyclerViewBody.layoutManager = LinearLayoutManager(view.context)
        recyclerViewBody.adapter = adapterPack

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
        adapterCategory?.data = adapterCategory?.data?.map { d -> d.copy(selected = d.id == category.id) }
                ?: emptyList()
        viewModel?.packs?.value?.let {
            onPacks(it)
        }
    }

    private fun onPacks(data: List<Pack>) {
        adapterPack?.data = data.filter { it.categoryId == category?.id }
    }

    private fun onPackSelected(pack: Pack) {
        pack.ussd?.let {
            Intent(Intent.ACTION_VIEW).apply {
                val ussd = it.replace("#", Uri.encode("#"))
                data = Uri.parse("tel:$ussd")
                try {
                    startActivity(this)
                } catch (e: Exception) {
                }
            }

        }
    }
}