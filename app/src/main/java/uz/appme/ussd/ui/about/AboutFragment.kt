package uz.appme.ussd.ui.about

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_about.*
import uz.appme.ussd.BaseFragment
import uz.appme.ussd.R
import uz.appme.ussd.data.Provider
import uz.appme.ussd.ui.PROVIDER


class AboutFragment : BaseFragment() {

    lateinit var mcontext : Context

    private val viewModel by lazy {
        ViewModelProvider(this).get(AboutViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        mcontext = context
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    var provider : Provider? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provider = arguments?.getSerializable(PROVIDER) as Provider?
        viewModel.discription.let{
            it.value?.let{data ->
                onDescription(data)
            }
            it.observe(viewLifecycleOwner, Observer { data ->
                onDescription(data)
            })

        }


        imageViewBack.setOnClickListener({
            findNavController().popBackStack()
        })


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