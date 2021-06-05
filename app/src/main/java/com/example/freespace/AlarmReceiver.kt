package com.example.freespace

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class AlarmReceiver : BroadcastReceiver {
    constructor()
    var alarmVocab = ArrayList<Place>()
    val CHANNEL_ID = "TEST"
    val textTitle = "Alarm"
    val textContent ="여기서는?"
    val context: Context = MyApplication.ApplicationContext()

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "test channel"
            val descriptionText = "testing channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags - Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context,0,notificationIntent,0)
        var vocab = ArrayList<Place>()
        alarmSetting(vocab)
        val builder = NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.img2)
                .setContentTitle("오늘의 단어")
                .setContentText("안녕")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)){
            notify(1,builder.build())
        }
    }
    fun alarmSetting(items: ArrayList<Place>){
        items.clear()
        var alarmNum = arrayOfNulls<Int>(1)
        var i = 0
        while(i<1){
            var rand =(0..alarmVocab.size-1).random()
            if(rand in alarmNum)
            {continue}
            else{
                items.add(alarmVocab.get(rand))
                alarmNum[i++] = rand
            }
        }
    }

}