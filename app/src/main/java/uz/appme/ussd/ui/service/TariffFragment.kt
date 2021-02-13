package uz.appme.ussd.ui.service

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_limits.view.*
import kotlinx.android.synthetic.main.fragment_tariff.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Tariff
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.ui.adapter.LimitAdapter

class TariffFragment : BaseFragment() {


    private val tariff by lazy {
        arguments?.getSerializable("data") as? Tariff
    }

    private val lang by lazy {
        arguments?.getSerializable("lang") as? Lang
    }

    private val provider by lazy {
        arguments?.getSerializable("provider") as? Provider
    }

    private val limitAdapter by lazy {
        provider?.let { p ->
            lang?.let { l ->
                LimitAdapter(p, l)
            }
        }
    }

    private val overLimitAdapter by lazy {
        provider?.let { p ->
            lang?.let { l ->
                LimitAdapter(p, l)
            }
        }
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

        recyclerViewLimits.adapter = limitAdapter
        recyclerViewLimits.layoutManager = LinearLayoutManager(view.context)

        recyclerViewOvertLimits.adapter = overLimitAdapter
        recyclerViewOvertLimits.layoutManager = LinearLayoutManager(view.context)

        tariff?.let { t ->
            provider?.let { p ->
                lang?.let { l ->
                    onTariff(view.context, p, t, l)
                }
            }
        }

    }

    private fun onTariff(context: Context, provider: Provider, tariff: Tariff, lang: Lang) {

        textViewHeader?.text = getString(R.string.tariff_info)
        textViewTitle?.text = if (lang == Lang.UZ) tariff.nameUz else tariff.nameRu

        tariff.image?.let {

            val image = if (!it.startsWith("http")) {
                BuildConfig.BASE_IMAGE_URL + it
            } else it

            Glide.with(imageViewTariff)
                .load(image)
                .centerCrop()
                .into(imageViewTariff)
        }

        textViewOnPrice?.text = if (lang == Lang.UZ) tariff.onPriceUz else tariff.onPriceRu
        textViewSubscriptionPrice?.text =
            if (lang == Lang.UZ) tariff.subscriptionPriceUz else tariff.subscriptionPriceRu

        limitAdapter?.data = tariff.limits
        overLimitAdapter?.data = tariff.overLimits

        cardViewChangeTariff?.setOnClickListener {
            Intent(Intent.ACTION_CALL).apply {
                val ussd = tariff.ussd?.replace("#", Uri.encode("#"))
                data = Uri.parse("tel:$ussd")
                try {
                    startActivity(this)
                } catch (e: Exception) {
                }
            }
        }

        textViewTariffDescription.text =
            if (lang == Lang.UZ) tariff.descriptionUz else tariff.subscriptionPriceRu

        val color = try {
            Color.parseColor(provider.color)
        } catch (e: Exception) {
            ContextCompat.getColor(context, R.color.colorSecondary)
        }

        cardViewChangeTariff?.setCardBackgroundColor(color)

    }


}