package uz.appme.ussd.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.ui.BaseFragment
import uz.appme.ussd.R
import uz.appme.ussd.model.data.Contact
import uz.appme.ussd.model.data.Lang
import uz.appme.ussd.model.data.Provider
import uz.appme.ussd.model.data.SingleContact
import uz.appme.ussd.ui.adapter.ContactAdapter


class AboutFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    private val data by lazy {
        arguments?.getSerializable("contact") as? Contact
    }

    private val provider by lazy {
        arguments?.getSerializable("data") as? Provider
    }

    private val lang by lazy {
        arguments?.getSerializable("lang") as? Lang
    }

    private val contactAdapter by lazy {
        ContactAdapter{
            onItemSelected(it)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("datas", data.toString())
        Log.e("datas", lang.toString())
        Log.e("datas", provider.toString())

        val contacts = arrayListOf<SingleContact>()
        if (data?.phone != null) {
            contacts?.add(
                SingleContact(
                    type = 0,
                    number = data?.phone
                )
            )
        }
        if (data?.telegram != null) {
            contacts?.add(
                SingleContact(
                    type = 1,
                    number = data?.telegram
                )
            )
        }
        if (data?.facebook != null && data?.facebook?.length!! > 2 ) {
            contacts?.add(
                SingleContact(
                    type = 2,
                    number = data?.facebook
                )
            )
        }
        if (data?.instagram != null &&  data?.instagram?.length!! > 2) {
            contacts?.add(
                SingleContact(
                    type = 3,
                    number = data?.instagram
                )
            )
        }
        val s = contacts?.size ?: 1

        val r = if (s == 4) 2 else s

        contactAdapter.data = contacts
        contactAdapter.provider = provider
        recyclerViewContact.layoutManager = GridLayoutManager(recyclerViewContact.context, r)
        recyclerViewContact.adapter = contactAdapter

        textViewAbout.text = if (lang == Lang.UZ) data?.aboutUz else data?.aboutRu

        cardBack.setOnClickListener {
            findNavController().popBackStack()
        }
        textViewHeader.text = resources.getString(R.string.aboutUs)

    }

    private fun onItemSelected(data : SingleContact){
        val intent = Intent(Intent.ACTION_VIEW)
        var uri = data.number
        if(data.type == 0)
            uri = "tel:"+uri

        intent.data = Uri.parse(uri)
        try{
            startActivity(intent)
        }catch (e:Exception){}

    }


}