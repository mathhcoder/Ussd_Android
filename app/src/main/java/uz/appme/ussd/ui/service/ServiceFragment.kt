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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_service.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.Service
import uz.appme.ussd.ui.BaseFragment

class ServiceFragment : BaseFragment() {

    private val service by lazy {
        arguments?.getSerializable("data") as? Service
    }

    private val lang by lazy {
        arguments?.getSerializable("lang") as? Lang
    }

    private val provider by lazy {
        arguments?.getSerializable("provider") as? Provider
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service?.let { s ->
            lang?.let { l ->
                provider?.let { p ->
                    onService(view.context, p, s, l)
                }
            }
        }

    }

    private fun onService(context: Context, provider: Provider, service: Service, lang: Lang) {

        textViewTitle?.text = if (lang == Lang.UZ) service.nameUz else service.nameRu

        textViewHeader.text = resources.getText(R.string.aboutService)

        textViewOnPrice?.text =
            if (lang == Lang.UZ) service.firstTimePriceUz else service.firstTimePriceRu
        textViewSubscriptionPrice?.text =
            if (lang == Lang.UZ) service.subscriptionPriceUz else service.subscriptionPriceRu

        cardBack.setOnClickListener {
            findNavController().popBackStack()
        }
        cardViewSelectService?.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                val ussd = service.ussd?.replace("#", Uri.encode("#"))
                data = Uri.parse("tel:$ussd")
                try {
                    startActivity(this)
                } catch (e: Exception) {
                }
            }
        }

        textViewServiceDescription.text =
            if (lang == Lang.UZ) service.descriptionUz else service.subscriptionPriceRu

        val color = try {
            Color.parseColor(provider.color)
        } catch (e: Exception) {
            ContextCompat.getColor(context, R.color.colorSecondary)
        }

        cardViewSelectService?.setCardBackgroundColor(color)

    }

}