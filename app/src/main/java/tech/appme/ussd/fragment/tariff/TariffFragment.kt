package tech.appme.ussd.fragment.tariff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import tech.appme.ussd.BaseFragment
import tech.appme.ussd.R

class TariffFragment : BaseFragment(){
    private val viewModel by lazy {
        ViewModelProvider(this).get(TariffViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariff, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






    }


}