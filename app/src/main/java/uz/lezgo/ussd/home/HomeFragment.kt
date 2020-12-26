package uz.lezgo.ussd.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_home.*
import uz.lezgo.ussd.Banner
import uz.lezgo.ussd.BaseFragment
import uz.lezgo.ussd.R
import uz.lezgo.ussd.adapter.BannerPagerAdapter


class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private val pagerAdapter by lazy {
        BannerPagerAdapter(childFragmentManager)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_home, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arr =
            arrayListOf(
                notifBackround, cardIcon, mtarif, icon_tariff, service_bacground, Iconbackround,
                MinutesBackround, SMSBackround
            ) as ArrayList<MaterialCardView>


        Log.e("testColor", viewModel.provider.toString())
        notifBackround.setCardBackgroundColor(Color.parseColor("#123132"))




        viewModel?.provider?.let {
            it.value?.let { provider ->
                Log.e("provider", provider.toString())
                arr.forEach { card ->
                    card.setCardBackgroundColor(Color.parseColor(provider.color))
                }
                pageIndicatorView.selectedColor = Color.parseColor(provider.color)


            }
            it.observe(viewLifecycleOwner, Observer { provider ->
                arr.forEach { card ->
                    Log.e("provider", provider.toString())
                    card.setCardBackgroundColor(Color.parseColor(provider.color))
                }
                pageIndicatorView.selectedColor = Color.parseColor(provider.color)
            })
        }

        viewModel?.banners?.let {
            it.value?.let { data ->
                onBanners(data)
            }
            it.observe(viewLifecycleOwner, Observer { data ->
                onBanners(data)
            })
        }

    }

    fun onBanners(data : ArrayList<Banner>){

        pagerAdapter.data = data
    }

}





//
//
//
//        arr?.forEach {
//            Log.e("aaaaaa" , it.id.toString())
//            it.setCardBackgroundColor(Color.parseColor(viewModel.rcolor))
//        }

