package com.example.pipapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.pipapplication.databinding.ActivitySplashBinding
import com.example.pipapplication.utils.PreferenceManager
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit

        // Handler().postDelayed({
        Handler(Looper.getMainLooper()).postDelayed({
            var intent:Intent
            if (PreferenceManager.getIsLogin()){
                intent = Intent(this, MainActivity::class.java)
            }else {
                intent = Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.

/*        activityScope.launch {
            delay(3000)
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
//        activityScope.cancel()
    }
}