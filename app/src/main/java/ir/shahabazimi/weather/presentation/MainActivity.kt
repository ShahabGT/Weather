package ir.shahabazimi.weather.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.insets.GradientProtection
import androidx.core.view.insets.ProtectionLayout
import ir.shahabazimi.weather.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findViewById<ProtectionLayout>(R.id.mainProtection).setProtections(
            listOf(
                GradientProtection(
                    WindowInsetsCompat.Side.TOP,
                )
            )
        )
    }

}