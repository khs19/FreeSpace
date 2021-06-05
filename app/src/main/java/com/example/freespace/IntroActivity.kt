package com.example.freespace
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.freespace.MainActivity
import com.example.freespace.databinding.ActivityIntroBinding


class IntroActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        val intent = Intent(this, MainActivity::class.java)
        with(binding) {
            search.setOnClickListener{
                val intent = Intent(this@IntroActivity, SearchActivity::class.java)
                startActivity(intent)
            }
            bookMark.setOnClickListener{
                val intent = Intent(this@IntroActivity, BookmarkActivity::class.java)
                startActivity(intent)
            }
            alarm.setOnClickListener{
                val intent = Intent(this@IntroActivity, AlarmActivity::class.java)
                startActivity(intent)
            }
        }

    }
}