package uz.appme.ussd.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.ui.adapter.BannerPagerAdapter
import uz.appme.ussd.model.data.Banner
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.ui.dialog.SelectOperatorDialog


class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(MainViewModel::class.java)
        }
    }

    private val pagerAdapter by lazy {
        BannerPagerAdapter(childFragmentManager)
    }

    private val dialog by lazy {
        context?.let {
            SelectOperatorDialog(it) { o ->
                viewModel?.selectOperator(o)
            }
        }
    }

    private var provider: Provider? = null
    private var lang: Lang = Lang.UZ

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = pagerAdapter

        viewModel?.operators?.let {

            it.value?.let { providers ->
                onOperators(providers)
            }

            it.observe(viewLifecycleOwner, { providers ->
                onOperators(providers)
            })
        }


        viewModel?.banners?.let {

            it.value?.let { data ->
                onBanners(data)
            }

            it.observe(viewLifecycleOwner, { data ->
                onBanners(data)
            })
        }

        layoutOperator?.setOnClickListener {
            dialog?.show()
        }

        cardViewSettings?.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_settings)
        }

        cardViewTariff?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_tariffs, bundle)
        }

        cardViewService?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_services, bundle)
        }

        cardViewInternet?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("type", 3), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewMinutes?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("type", 4), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewSMS?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("type", 5), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewCodes?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_codes, bundle)
        }

        cardViewNews?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_news, bundle)
        }

        cardViewSales?.setOnClickListener {
            val bundle = bundleOf(Pair("data", provider), Pair("lang", lang))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_sales, bundle)
        }


    }

    private fun onBanners(data: List<Banner>) {
        pagerAdapter.data = data.filter { it.providerId == provider?.id }
    }

    private fun onOperators(data: List<Provider>) {
        dialog?.data = data
        data.firstOrNull { it.selected }?.let {
            onOperator(it)
        }
    }

    private fun onOperator(data: Provider) {
        provider = data
        dialog?.dismiss()
        textViewProviderName.text = data.name
        Glide.with(imageViewProvider)
            .load(data.icon)
            .into(imageViewProvider)

        viewModel?.banners?.value?.let { b ->
            onBanners(b)
        }
        try {
            Color.parseColor(data.color).let { col ->
                arrayListOf(
                    imageViewTariff,
                    imageViewService,
                    imageViewInternet,
                    imageViewMinutes,
                    imageViewSMS,
                    imageViewCode,
                    imageView,
                    imageViewSales
                ).forEach {
                    it.setColorFilter(col)
                }
            }
        } catch (e: Exception) {

        }

    }


}


