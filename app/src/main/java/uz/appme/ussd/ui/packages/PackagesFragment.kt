package uz.appme.ussd.ui.packages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_packages.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.adapter.PackagesAdapter
import uz.appme.ussd.adapter.CategoryAdapter
import uz.appme.ussd.data.Category
import uz.appme.ussd.data.Operator
import uz.appme.ussd.data.Pack
import uz.appme.ussd.ui.TYPE

class PackagesFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    var selectedCategory: Category? = null

    private val categoryAdapter by lazy {
        CategoryAdapter {
            onCategory(it)
        }
    }

    private val packsAdapter by lazy {
        PackagesAdapter {
            onPack(it)
        }
    }

    private val type by lazy {
        arguments?.getInt(TYPE)
    }

    private val operator by lazy{
        arguments?.getSerializable("key") as Operator
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_packages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerViewCategoryPackage.layoutManager =
            LinearLayoutManager(recyclerViewCategoryPackage.context)
        recyclerViewCategoryPackage.adapter = categoryAdapter

        recyclerViewLimitPackage.layoutManager =
            LinearLayoutManager(recyclerViewLimitPackage.context)
        recyclerViewLimitPackage.adapter = packsAdapter

        viewModel.categories.let {
            it.value?.let { data ->
                onCategories(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onCategories(data)
            })
        }

        viewModel.pack.let {
            it.value?.let { data ->
                onPackages(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onPackages(data)
            })
        }

    }

    private fun onCategories(data: List<Category>) {
        categoryAdapter.data = data.filter { it.type == type && it.provideId == operator.id }
    }

    private fun onPackages(data: List<Pack>) {
        packsAdapter.data = data.filter { it.categoryId == selectedCategory?.id && it.operatorId == operator.id }
    }

    private fun onCategory(data: Category) {
        selectedCategory = data
        packsAdapter.data = viewModel.pack.value?.filter { it.categoryId == selectedCategory?.id }
            ?: emptyList()
    }

    private fun onPack(pack: Pack) {

    }
}