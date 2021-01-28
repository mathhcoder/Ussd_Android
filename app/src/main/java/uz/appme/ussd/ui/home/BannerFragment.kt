package uz.appme.ussd.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_banner.*
import timber.log.Timber
import uz.appme.ussd.BuildConfig
import uz.appme.ussd.R
import uz.appme.ussd.data.Banner


class BannerFragment : Fragment() {

    companion object {

        fun newInstance(banner: Banner): BannerFragment {
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("data", banner)
                }
            }
        }
    }

    private val banner by lazy {
        arguments?.getSerializable("data") as? Banner
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_banner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        banner?.let {
            onData(view.context, it)
        }
    }

    private fun onData(context: Context, banner: Banner) {

        banner.image?.let {

            val image = if (!it.startsWith("http")) {
                BuildConfig.BASE_IMAGE_URL + banner.image
            } else it

            Timber.e("Image: $image")

            Glide.with(context)
                .load(image)
                .centerCrop()
                .into(imageView)

        }

        banner.link?.let { link ->
            view?.setOnClickListener {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(link)
                    try {
                        startActivity(this)
                    } catch (e: Exception) {
                    }
                }
            }
        }

    }

}