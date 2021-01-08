package uz.lezgo.ussd.fragment.news

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.imageViewBack
import uz.lezgo.ussd.BaseFragment
import uz.lezgo.ussd.R
import uz.lezgo.ussd.adapter.NewsAdapter
import uz.lezgo.ussd.data.News
import uz.lezgo.ussd.data.ProviderModel
import uz.lezgo.ussd.fragment.COLOR
import uz.lezgo.ussd.fragment.PROVIDER


class NewsFragment : BaseFragment() {


    private val viewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }
    private val adapter by lazy{
        NewsAdapter({})
    }

    private var provider : ProviderModel?= null
        set(value){
            adapter.provider = value
            field = value
        }

    private var color : String = "#FFFFFF"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        provider = arguments?.getSerializable(PROVIDER) as ProviderModel?



        viewModel.news.let {

            it.value?.let { news ->
                onNews(news)
            }

            it.observe(viewLifecycleOwner, Observer { news ->
                onNews(news)
            })
        }
        color = arguments?.getString(COLOR , "#FFFFFF").toString()

        recyclerViewNews.layoutManager = LinearLayoutManager(context)
        recyclerViewNews.adapter = adapter

        imageViewBack.setOnClickListener({
            findNavController().popBackStack()
        })


    }



    private fun onNews(news : ArrayList<News>){
        adapter.data = news
    }

    fun printLog(s : String) = Log.e("JAMA" , s)
}