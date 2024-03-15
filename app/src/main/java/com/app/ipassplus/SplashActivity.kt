package com.app.ipassplus
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val progressBar = findViewById<ProgressBar>(R.id.idPBLoading)
        progressBar.visibility = ProgressBar.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = ProgressBar.GONE

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}