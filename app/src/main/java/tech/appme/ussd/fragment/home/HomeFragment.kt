package tech.appme.ussd.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.cardViewTariff
import kotlinx.android.synthetic.main.fragment_home.layoutNews
import kotlinx.android.synthetic.main.fragment_home.layoutUssdCodes
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_tariff.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.appme.ussd.BaseFragment
import tech.appme.ussd.MainViewModel
import tech.appme.ussd.R
import tech.appme.ussd.adapter.BannerPagerAdapter
import tech.appme.ussd.data.Banner
import tech.appme.ussd.data.Provider
import tech.appme.ussd.dialog.ProviderDialog
import tech.appme.ussd.io.StockPreference


class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val pagerAdapter by lazy {
        BannerPagerAdapter(childFragmentManager)
    }

    private val dialog by lazy {
        context?.let {
            ProviderDialog(it) {

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

//        viewModel.providers.let {
//
//            it.value?.let { providers ->
//                onProviders(providers)
//            }
//
//            it.observe(viewLifecycleOwner, { providers ->
//                onProviders(providers)
//            })
//        }
        val preference = StockPreference(viewPager.context)
        val lang = preference.lang
        preference.lang = lang

//
//        viewModel.banners.let {
//            it.value?.let { data ->
//                onBanners(data)
//            }
//            it.observe(viewLifecycleOwner, { data ->
//                onBanners(data)
//            })
//        }

        layoutProvider?.setOnClickListener {
            dialog?.show()
        }

        imageViewSettings.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_settings)
        })


        cardViewTariff.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_tariffs)
        })

        cardViewService.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_services)
        })


        cardViewInternet.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages)
        })

        cardViewSMS.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages)
        })

        cardViewMinutes.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_packages)
        })


        layoutNews.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_news)
        })
        layoutUssdCodes.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_ussd)
        })

        layoutPromotions.setOnClickListener({
            findNavController().navigate(R.id.action_fragment_home_to_fragment_news)
        })


//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)





    }

    private fun onBanners(data: List<Banner>) {
        Log.e("BANNERS" , data.toString())
//        pagerAdapter.data = data
    }

    private fun onProviders(data: List<Provider>) {
        dialog?.data = data
    }

    private fun onProvider(data: Provider) {

        try {
//            Color.parseColor(data.color).let { col ->
//                arrayListOf(
//                    cardIcon,
//                    mtarif,
//                    icon_tariff,
//                    service_bacground,
//                    Iconbackround,
//                    cardMinutes,
//                    SMSBackround
//                ).forEach {
//                    it.setCardBackgroundColor(Color.parseColor(data.color))
//                }
//            }
        } catch (e: Exception) {

        }

        pageIndicatorView.selectedColor = Color.parseColor(data.color)
        dialog?.data = dialog?.data?.map { it.copy(selected = it.id == data.id) } ?: emptyList()
        dialog?.color = data.color!!
        textViewProviderName.text = data.nameUz

        Glide.with(imageViewProvider)
            .load(data.image)
            .into(imageViewProvider)

        GlobalScope.launch {
            delay(200L)
            dialog?.dismiss()
        }

    }


}


