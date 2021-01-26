package uz.appme.ussd.ui.tariffs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_tariff.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.R
import uz.appme.ussd.UssdApp
import uz.appme.ussd.adapter.LimitAdapter
import uz.appme.ussd.data.Tariff
import uz.appme.ussd.ui.StockPreference
import uz.appme.ussd.ui.TARIFF

class TariffFragment : BaseFragment(){

    private val tariff by lazy{
        arguments?.getSerializable(TARIFF) as Tariff
    }

    var mcontext : Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext = context
    }


    var lang = StockPreference(UssdApp()).lang

    val limitAdapter by lazy{
        LimitAdapter()
    }

    val overLimitAdapter by lazy{
        LimitAdapter()
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

        textViewHeader.text =  getString(R.string.tariff)
        textViewTariffName.text = if(lang == "uz") tariff.nameUz else tariff.nameRu

        Glide.with(imageViewTariff)
            .load(tariff.image)
            .centerCrop()
            .into(imageViewTariff)

        textViewOnPrice.text = if(lang == "uz") tariff.onPriceUz else tariff.onPriceRu
        textViewPrice.text = if(lang == "uz") tariff.subscriptionPriceUz else tariff.subscriptionPriceRu

        recyclerViewLimits.adapter = limitAdapter
        limitAdapter.data = tariff.limits
        recyclerViewLimits.layoutManager = LinearLayoutManager(mcontext)

        recyclerViewOvertLimits.adapter =  overLimitAdapter
        recyclerViewOvertLimits.layoutManager = LinearLayoutManager(mcontext)

        overLimitAdapter.data = tariff.overLimits

        cardViewChangeTariff.setOnClickListener {
            Intent(Intent.ACTION_CALL).apply {
                val ussd = tariff.ussd?.replace("#", Uri.encode("#"))
                data = Uri.parse("tel:$ussd")
                try {
                    startActivity(this)
                } catch (e: Exception) {
                }
            }
        }

        textViewTariffDescription.text = if(lang == "uz") tariff.descriptionUz else tariff.subscriptionPriceRu




    }



}