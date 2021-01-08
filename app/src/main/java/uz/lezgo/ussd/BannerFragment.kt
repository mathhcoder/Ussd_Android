package uz.lezgo.ussd

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_banner.*


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
            onData(view.context,it)
        }
    }

    private fun onData(context: Context, banner: Banner) {

        banner.image?.let {
            Log.e("Banner" , banner.toString())
            Glide.with(this)
                .load(banner.image) // image url
                .placeholder(R.drawable.ic_launcher_background) // any placeholder to load at start
                .error(R.drawable.ic_launcher_foreground)  // any image in case of error
                .centerCrop()
                .fitCenter()// resizing
                .into(imageView)

        }

        banner.url?.let { link ->
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