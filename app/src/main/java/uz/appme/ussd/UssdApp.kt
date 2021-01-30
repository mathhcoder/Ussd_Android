package uz.appme.ussd

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber


/**
 * key store pass : ussdmobile
 * key : ussdmobile
 * pass : ussdmobile
 * developer -> Jamshidkhan
 * project -> Ussd
 * date -> 06 January 2020
 * github -> github.com/jkadirov
 */

class UssdApp : Application() {

    companion object {
        lateinit var instance: UssdApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.DEALER).addOnSuccessListener {
            Timber.i("Successfully subscribed to topic ${BuildConfig.DEALER}")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(this)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    @RequiresApi(26)
    fun createChannel(context: Context) {
        val name = "News"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("news", name, importance)
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(RuntimeLocaleChanger.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        RuntimeLocaleChanger.setLocale(this)
    }


}