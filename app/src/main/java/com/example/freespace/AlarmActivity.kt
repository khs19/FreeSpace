package com.example.freespace
import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freespace.R
import com.example.freespace.databinding.ActivityAlarmBinding
import com.example.freespace.databinding.MypickerdlgBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.reflect.Array.set
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AlarmActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlarmBinding
    private var alarmMgr: AlarmManager? = null
    lateinit var adapter: MyPlaceAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rdb: DatabaseReference
    private lateinit var alarmIntent: PendingIntent
    var mymemo =""
    var myhour= 0
    var mymin = 0
    var message=""
    var pString=""
    var pSaturation=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("PlaceDB/Place")
        val query = rdb.orderByChild("bookmark").equalTo(true.toString().toBoolean())
        val option = FirebaseRecyclerOptions.Builder<Place>().setQuery(query, Place::class.java).build()
        adapter = MyPlaceAdapter(option)
        adapter.itemClickListener = object:MyPlaceAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) {
                pString = adapter.getItem(position).pName.toString()
                pSaturation=(100*adapter.getItem(position).pNum/adapter.getItem(position).pMaxNum).toString()
                Toast.makeText(this@AlarmActivity,pString + "이 선택되었습니다 알림날짜와 시각을 선택해주세요",Toast.LENGTH_SHORT).show()
            }
        }
        binding.apply {
            alarmRecyclerView.layoutManager = layoutManager
            alarmRecyclerView.adapter = adapter
        }
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if (pString != "") {
                val dlgBinding = MypickerdlgBinding.inflate(layoutInflater)
                val dlgBuilder = AlertDialog.Builder(this)
                dlgBuilder.setView(dlgBinding.root)
                        .setPositiveButton("추가") { _, _ ->
                            mymemo = dlgBinding.editText.text.toString()
                            myhour = dlgBinding.timePicker.hour
                            mymin = dlgBinding.timePicker.minute
                            message = myhour.toString() + "시" + mymin.toString() + "분 : " + mymemo
                            val timerTask = object : TimerTask() {
                                @RequiresApi(Build.VERSION_CODES.O)
                                override fun run() {
                                    makeNotification(pString, pSaturation)
                                    alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                    alarmIntent = Intent(this@AlarmActivity, AlarmReceiver::class.java).let { intent ->
                                        PendingIntent.getBroadcast(this@AlarmActivity, 0, intent, 0)
                                    }

                                    val calendar: Calendar = Calendar.getInstance().apply {
                                        timeInMillis = System.currentTimeMillis()
                                        set(Calendar.HOUR_OF_DAY, myhour)
                                        set(Calendar.MINUTE, mymin)
                                    }


                                    val receiver = ComponentName(this@AlarmActivity, SampleBootReceiver(17, 53)::class.java)
                                    packageManager.setComponentEnabledSetting(
                                            receiver,
                                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                            PackageManager.DONT_KILL_APP
                                    )
                                }
                            }
                            try {
                                val date = Calendar.getInstance()
                                date.set(Calendar.YEAR, year)
                                date.set(Calendar.MONTH, month)
                                date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                date.set(Calendar.HOUR_OF_DAY, myhour)
                                date.set(Calendar.MINUTE, mymin)
                                date.set(Calendar.SECOND, 0)
                                val date2 = Date(date.timeInMillis)

                                val timer = Timer()
                                timer.schedule(timerTask, date2)
                                Toast.makeText(this, "알림이 추가되었음", Toast.LENGTH_SHORT).show()
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }


                        }
                        .setNegativeButton("취소") { _, _ ->
                        }
                        .show()
            }
            else{
                Toast.makeText(this,"알림을 원하는 장소를 선택해주세요",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun makeNotification(pName:String ,Saturation:String){ //알람주자!
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = "MyChannel"
            val name = "TimeCheckChannel"
            val notificationChannel =
                    NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

            val builder = NotificationCompat.Builder(this, id)
                    .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                    .setContentTitle("포화도 알림")
                    .setContentText(pName+"의 현재 포화도는 : "+Saturation +"% 입니다")
                    .setAutoCancel(true)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("time", pName+"의 현재 포화도는 : "+Saturation+"% 입니다")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            val pendingIntent =
                    PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(pendingIntent)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)
            val notification = builder.build()
            manager.notify(10, notification)
        }
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}