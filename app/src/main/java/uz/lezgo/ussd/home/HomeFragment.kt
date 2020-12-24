package uz.lezgo.ussd.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import uz.lezgo.ussd.Banner
import uz.lezgo.ussd.BaseFragment

class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.banners.let {
            it.value?.let { data ->
                onBanner(data)
            }

            it.observe(viewLifecycleOwner, { data ->
                onBanner(data)
            })
        }


    }

    private fun onBanner(banner: List<Banner>) {

    }


}