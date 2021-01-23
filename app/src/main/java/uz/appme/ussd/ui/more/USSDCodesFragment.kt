package uz.appme.ussd.ui.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_ussd.*
import kotlinx.android.synthetic.main.layout_header.*

import uz.appme.ussd.BaseFragment
import uz.appme.ussd.R
import uz.appme.ussd.adapter.UssdCodesAdapter
import uz.appme.ussd.data.UssdCodes


class USSDCodesFragment : BaseFragment() {

    val mcontext = this.context

    val adapter by lazy{
        UssdCodesAdapter({

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ussd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardBack.setOnClickListener {
            findNavController().popBackStack()
        }
        textViewHeader.text = getString(R.string.codes)

//        val dividerItemDecoration = DividerItemDecoration(
//            recyclerViewUssd.getContext(),
//            layoutManager.getOrientation()
//        )


//        val itemDecorator = DividerItemDecoration(mcontext, DividerItemDecoration.VERTICAL)
       // itemDecorator.setDrawable(ContextCompat.getDrawable(recyclerViewUssd.context, R.drawable.divider)!!)

        //recyclerViewUssd.addItemDecoration(itemDecorator)


        recyclerViewUssd.adapter = adapter
        adapter.data = arrayListOf(
            UssdCodes(0 , "Imei kodni tekshirish" , "*#06#"),
            UssdCodes(1 , "Balansni tekshirish" , "*100#"),
            UssdCodes(2 , "Imei kodni tekshirish" , "*#06#"),
            UssdCodes(3 , "Balansni tekshirish" , "*100#"),
            UssdCodes(4 , "Imei kodni tekshirish" , "*#06#"),
            UssdCodes(5 , "Balansni tekshirish" , "*100#"),
            UssdCodes(6 , "Imei kodni tekshirish" , "*#06#"),
            UssdCodes(7 , "Balansni tekshirish" , "*100#"),
            UssdCodes(8 , "Imei kodni tekshirish" , "*#06#"),
            UssdCodes(9 , "Balansni tekshirish" , "*100#")
            )

        recyclerViewUssd.apply {
            setHasFixedSize(true)
            adapter = adapter
            layoutManager =LinearLayoutManager(
                this.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }



    }

}