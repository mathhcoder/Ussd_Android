package uz.appme.ussd.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val pagerAdapter by lazy {
        BannerPagerAdapter(childFragmentManager)
    }

    private val dialog by lazy {
        context?.let {
            SelectOperatorDialog(it) { cat ->
                viewModel.selectOperator(cat)
            }
        }
    }

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

        viewModel.operators.let {

            it.value?.let { providers ->
                onOperators(providers)
            }

            it.observe(viewLifecycleOwner, { providers ->
                onOperators(providers)
            })
        }

        viewModel.banners.let {

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

        cardSettings?.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_settings)
        }


    }

    private fun onBanners(data: List<Banner>) {
        pagerAdapter.data = data
    }

    private fun onOperators(data: List<Operator>) {
        dialog?.data = data
        data.firstOrNull { it.selected }?.let {
            onOperator(it)
        }
    }

    private fun onOperator(data: Operator) {
        dialog?.dismiss()
        textViewProviderName.text = data.name
        Glide.with(imageViewProvider)
            .load(data.image)
            .into(imageViewProvider)

        try {
            Color.parseColor(data.color).let { col ->

//                arrayListOf(
//                    icon_tariff,
//                    service_bacground,
//                    Iconbackround,
//                    cardMinutes,
//                    SMSBackround
//                ).forEach {
//                    it.setCardBackgroundColor(Color.parseColor(data.color))
//                }

//                arrayListOf(
//
//                ).forEach {
//                    it.setColorFilter(col)
//                }

            }
        } catch (e: Exception) {

        }

    }


}


