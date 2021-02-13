package uz.appme.ussd.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.model.data.News
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.ui.adapter.NewsAdapter


class NewsFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val adapterNews by lazy {
        NewsAdapter {
            onNewsSelected(it)
        }
    }

    private val operator by lazy {
        arguments?.getSerializable("data") as? Provider
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewHeader?.text = getString(R.string.news)

        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel?.news?.let {
            it.value?.let { data ->
                onNews(data)
            }
            it.observe(viewLifecycleOwner, { data ->
                onNews(data)
            })
        }

        recyclerViewBody.layoutManager = LinearLayoutManager(recyclerViewBody.context)
        recyclerViewBody.adapter = adapterNews

    }

    private fun onNews(data: List<News>) {
        adapterNews.data = data.filter { it.providerId == operator?.id }
    }

    private fun onNewsSelected(news: News) {

    }
}