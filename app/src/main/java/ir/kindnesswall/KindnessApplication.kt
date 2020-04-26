package ir.kindnesswall

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.chibatching.kotpref.Kotpref
import ir.kindnesswall.data.local.AppPref
import ir.kindnesswall.data.local.UserInfoPref
import ir.kindnesswall.di.dataBaseModule
import ir.kindnesswall.di.networkModule
import ir.kindnesswall.di.repositoryModule
import ir.kindnesswall.di.viewModelModule
import net.gotev.uploadservice.UploadServiceConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*


/**
 * Created by farshid.abazari since 2019-11-01
 *
 * Usage:
 *
 * How to call:
 *
 * Useful parameter:
 *
 */

class KindnessApplication : Application(), LifecycleObserver {
    companion object {
        const val uploadFileNotificationChannelID = "uploadChannel"
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                uploadFileNotificationChannelID,
                "upload notification Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onCreate() {
        super.onCreate()

        Kotpref.init(this)

        startKoin {
            androidLogger()
            androidContext(this@KindnessApplication)
            modules(listOf(repositoryModule, networkModule, viewModelModule, dataBaseModule))
        }

//        UserInfoPref.bearerToken = "PS18esgYfp22XbaODp+PNQ=="

        createNotificationChannel()

        UploadServiceConfig.initialize(
            context = this,
            defaultNotificationChannel = uploadFileNotificationChannelID,
            debug = BuildConfig.DEBUG
        )
        changeLocale()
    }

    private fun changeLocale() {
        var currentAppLocaleLanguage = AppPref.currentLocale

        if (currentAppLocaleLanguage.isEmpty()) {
            currentAppLocaleLanguage =
                ConfigurationCompat.getLocales(AppPref.context.resources.configuration)[0].language

            AppPref.currentLocale = currentAppLocaleLanguage
        }

        val locale = Locale(currentAppLocaleLanguage)

        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        config.locale = locale

        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        AppPref.isAppInForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        AppPref.isAppInForeground = true
    }
}