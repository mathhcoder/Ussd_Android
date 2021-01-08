package tech.appme.ussd.fragment.home

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
import tech.appme.ussd.BaseFragment
import tech.appme.ussd.R
import tech.appme.ussd.adapter.BannerPagerAdapter
import tech.appme.ussd.data.Provider
import tech.appme.ussd.dialog.ProviderDialog
import uz.appme.ussd.Banner


class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private val pagerAdapter by lazy {
        BannerPagerAdapter(childFragmentManager)
    }

    private val dialog by lazy {
        context?.let {
            ProviderDialog(it) { cat ->
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

        viewModel.providers.let {

            it.value?.let { providers ->
                onProviders(providers)
            }

            it.observe(viewLifecycleOwner, { providers ->
                onProviders(providers)
            })
        }

        viewModel.provider.let {
            it.value?.let { provider ->
                onProvider(provider)
            }

            it.observe(viewLifecycleOwner, { provider ->
                onProvider(provider)
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

        layoutProvider?.setOnClickListener {
            dialog?.show()
        }

        cardNotification?.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_news)
        }

    }

    private fun onBanners(data: List<Banner>) {
        pagerAdapter.data = data
    }

    private fun onProviders(data: List<Provider>) {
        dialog?.data = data
    }

    private fun onProvider(data: Provider) {

        try {
            Color.parseColor(data.color).let { col ->
                imageViewBadge?.setColorFilter(col)
                arrayListOf(
                    cardIcon,
                    mtarif,
                    icon_tariff,
                    service_bacground,
                    Iconbackround,
                    cardMinutes,
                    SMSBackround
                ).forEach {
                    it.setCardBackgroundColor(Color.parseColor(data.color))
                }
            }
        } catch (e: Exception) {

        }

        pageIndicatorView.selectedColor = Color.parseColor(data.color)
        dialog?.data = dialog?.data?.map { it.copy(selected = it.id == data.id) } ?: emptyList()
        dialog?.color = data.color
        textViewProviderName.text = data.name

        Glide.with(imageViewProvider)
            .load(data.icon)
            .into(imageViewProvider)

        GlobalScope.launch {
            delay(200L)
            dialog?.dismiss()
        }

    }


}


