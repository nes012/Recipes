package nesty.anzhy.matkonim.ui.useractivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import nesty.anzhy.matkonim.MainActivity
import nesty.anzhy.matkonim.databinding.ActivitySplashBinding

@AndroidEntryPoint
class SplashActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

    Handler(Looper.getMainLooper()).postDelayed({
        // Get the current logged in user id
            // Launch dashboard screen.
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))

        finish()
        }, 2500)
    }
}