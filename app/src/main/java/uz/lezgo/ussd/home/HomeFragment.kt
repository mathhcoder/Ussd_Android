package uz.lezgo.ussd.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import uz.lezgo.ussd.Banner
import uz.lezgo.ussd.BaseFragment
import uz.lezgo.ussd.R
import uz.lezgo.ussd.adapter.BannerPagerAdapter

import uz.lezgo.ussd.data.ProviderModel
import uz.lezgo.ussd.dialog.BottomSheet


class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private val pagerAdapter by lazy {
        BannerPagerAdapter(childFragmentManager)
    }

    private val dialog by lazy {
        context?.let {
            BottomSheet(it) { cat ->

                viewModel.providerData.postValue(cat)
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

        viewModel.provider.let {

            it.value?.let { provider ->
                onProvider(provider)
            }

            it.observe(viewLifecycleOwner, Observer { provider ->
                onProvider(provider)
            })
        }

        viewModel.providers.let {

            it.value?.let { providers ->
                onProviders(providers)
            }

            it.observe(viewLifecycleOwner, Observer { providers ->
                onProviders(providers)
            })
        }

        viewModel.banners.let {
            it.value?.let { data ->
                onBanners(data)
            }
            it.observe(viewLifecycleOwner, Observer { data ->
                onBanners(data)
            })
        }

        show_bottomSheet.setOnClickListener {
            Log.e("DATAProvider", dialog?.data.toString())
            dialog?.show()
        }

    }

    private fun onBanners(data: List<Banner>) {
        pagerAdapter.data = data
    }

    private fun onProvider(data: ProviderModel) {

        arrayListOf(
            cardNotification, cardIcon, mtarif, icon_tariff, service_bacground, Iconbackround,
            cardMinutes, SMSBackround
        ).forEach {
            it.setCardBackgroundColor(Color.parseColor(data.color))
        }

        pageIndicatorView.selectedColor = Color.parseColor(data.color)
        dialog?.data = dialog?.data?.map { it.copy(selected = it.id == data.id) } ?: emptyList()

        textViewProviderName.text = data.name

        Glide.with(imageViewProvider)
            .load(data.icon)
            .into(imageViewProvider)

    }

    private fun onProviders(data: List<ProviderModel>) {
        dialog?.data = data
    }

}


