package com.example.freespace

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class SampleBootReceiver : BroadcastReceiver {
    constructor()
    private var alarmMgr : AlarmManager? = null
    private lateinit var  alarmIntent: PendingIntent

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == "android.intent.action.BOOT_COMPLETED"){
            alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, AlarmReceiver::class.java).let {
                alarmIntent ->
                PendingIntent.getBroadcast(context,0,alarmIntent,0)
            }

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY,23)
                set(Calendar.MINUTE, 52)
            }

            alarmMgr?.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    alarmIntent
            )
        }
    }
}