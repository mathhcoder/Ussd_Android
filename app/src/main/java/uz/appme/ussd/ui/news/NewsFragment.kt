package uz.appme.ussd.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.R
import uz.appme.ussd.adapter.NewsAdapter
import uz.appme.ussd.data.News


class NewsFragment : BaseFragment() {


    private val viewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }
    private val adapter by lazy {
        NewsAdapter {
            it.link?.let { link ->
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(link)
                    try {
                        startActivity(this)
                    } catch (e: Exception) {
                    }
                }
            }
        }
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

        viewModel.news.let {

            it.value?.let { news ->
                onNews(news)
            }

            it.observe(viewLifecycleOwner, { news ->
                onNews(news)
            })
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        cardBack?.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun onNews(news: ArrayList<News>) {
        adapter.data = news
    }
}