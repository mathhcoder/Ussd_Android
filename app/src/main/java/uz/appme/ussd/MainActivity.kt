package uz.appme.ussd

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import uz.appme.ussd.model.BaseRepository
import android.util.Log
import uz.appme.ussd.model.data.Lang

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun attachBaseContext(base: Context) {
        Log.e("Base","Attached")
        super.attachBaseContext(RuntimeLocaleChanger.setLocale(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lang_m" , BaseRepository.preference.lang)
        val l = if( BaseRepository.preference.lang == "uz") Lang.UZ else Lang.RU
        viewModel.changeLang(l , applicationContext)

        val isDark = when (viewModel.isDark()) {
            -1 -> AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO
            0 -> false
            1 -> true
            else -> false
        }
        onTheme(isDark)
        setContentView(R.layout.activity_main)
        request()



    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            android.Manifest.permission.CALL_PHONE
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                }
                return
            }
        }
    }

    private fun request(){
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                android.Manifest.permission.CALL_PHONE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    android.Manifest.permission.CALL_PHONE
                )) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.CALL_PHONE), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.CALL_PHONE), 1
                )
            }
        }
    }

    private fun onTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }



}