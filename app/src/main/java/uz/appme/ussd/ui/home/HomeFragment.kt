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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.adapter.BannerPagerAdapter
import uz.appme.ussd.data.Banner
import uz.appme.ussd.data.Operator
import uz.appme.ussd.dialog.SelectOperatorDialog
import uz.appme.ussd.ui.OPERATOR
import uz.appme.ussd.ui.TYPE
import uz.appme.ussd.ui.packages.PackagesFragment


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
                onOperator(o)
            }
        }
    }

    private var operator: Operator? = null

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
            val bundle = bundleOf(Pair("data", operator))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_tariffs, bundle)
        }

        cardViewService?.setOnClickListener {
            val bundle = bundleOf(Pair("data", operator))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_services, bundle)
        }

        cardViewInternet?.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(OPERATOR, Operator())
            bundle.putInt(TYPE, 1)
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewMinutes?.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(TYPE, 2)
            bundle.putSerializable(OPERATOR, Operator())
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewSMS?.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(TYPE, 3)
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewCodes?.setOnClickListener {
            val bundle = bundleOf(Pair("data", operator))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewNews?.setOnClickListener {
            val bundle = bundleOf(Pair("data", operator))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }

        cardViewSales?.setOnClickListener {
            val bundle = bundleOf(Pair("data", operator))
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages, bundle)
        }


    }

    private fun onBanners(data: List<Banner>) {
        pagerAdapter.data = data.filter { it.operatorId == operator?.id }
    }

    private fun onOperators(data: List<Operator>) {
        dialog?.data = data
        data.firstOrNull { it.selected }?.let {
            operator = it
        }
    }

    private fun onOperator(data: Operator) {
        operator = data

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
                    imageViewNews,
                    imageViewSales
                ).forEach {
                    it.setBackgroundColor(col)
                }
            }
        } catch (e: Exception) {

        }

    }


}


