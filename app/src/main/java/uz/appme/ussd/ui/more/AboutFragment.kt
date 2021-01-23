package uz.appme.ussd.ui.more

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.layout_header.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.MainViewModel
import uz.appme.ussd.R
import uz.appme.ussd.data.Operator
import uz.appme.ussd.ui.PROVIDER


class AboutFragment : BaseFragment() {


    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    var operator : Operator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        operator = arguments?.getSerializable(PROVIDER) as Operator?



        cardBack.setOnClickListener({
            findNavController().popBackStack()
        })

        textViewHeader.setText(R.string.aboutUs)


        var textViews = ArrayList<TextView>()

    }



    fun onDescription(data : String){
        textViewAbout.text = data
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printLog("onCreate Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        printLog("onDestroy Called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        printLog("onDestroyView Called")
    }

    override fun onDetach() {

        super.onDetach()
        printLog("onDetach Called")
    }

    override fun onPause() {
        super.onPause()
        printLog("onPause Called")
    }

    override fun onResume() {
        super.onResume()
        printLog("onResume Called")
    }

    override fun onStart() {
        super.onStart()
        printLog("onStart Called")
    }

    override fun onStop() {
        super.onStop()
        printLog("onStop Called")
    }


    fun printLog(s : String) = Log.e("lifeexamlpe" , s)




}