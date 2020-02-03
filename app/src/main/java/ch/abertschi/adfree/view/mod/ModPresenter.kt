package ch.abertschi.adfree.view.mod

import android.content.Context
import android.content.Intent
import ch.abertschi.adfree.AdFreeApplication
import ch.abertschi.adfree.detector.AdDetectable
import ch.abertschi.adfree.model.PreferencesFactory
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ModPresenter(val view: ModActivity, val prefs: PreferencesFactory): AnkoLogger {

    private lateinit var context: Context

    fun onCreate(context: Context) {
        info { "new presenter" }
        view.setEnableToggle(prefs.isBlockingEnabled())
        view.setNotificationEnabled(prefs.isAlwaysOnNotificationEnabled())
        view.setDelayValue(prefs.getDelaySeconds())
        this.context = context
    }

    fun onToggleAlwaysOnChanged() {
        val newVal = !prefs.isAlwaysOnNotificationEnabled()
        prefs.setAlwaysOnNotification(newVal)
        view.setNotificationEnabled(newVal)
        (view.applicationContext as AdFreeApplication).restartNotificationListener()
        if (!newVal) {
            (view.applicationContext as AdFreeApplication).notificationChannel.hideAlwaysOnNotification()
        }
    }

    fun onDelayUnmute() {
        view.showDelayUnmute()
    }
    fun onDelayChanged(delay: Int) {
        prefs.setDelaySeconds(delay)
        view.setDelayValue(delay)
    }


    fun onEnableToggleChanged() {
        val newVal = !prefs.isBlockingEnabled()
        prefs.setBlockingEnabled(newVal)
        view.setEnableToggle(newVal)
        if (newVal) {
            view.showPowerEnabled()
        }
    }

    fun onLaunchActiveDetectorsView() {
        val myIntent = Intent(this.context, ActiveDetectorsActivity::class.java)
        this.context.startActivity(myIntent)
    }
}