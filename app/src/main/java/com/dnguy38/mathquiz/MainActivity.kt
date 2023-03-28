package com.dnguy38.mathquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnguy38.mathquiz.models.Settings
import com.dnguy38.mathquiz.models.Statistics

class MainActivity : AppCompatActivity() {
    lateinit var settings: Settings
    lateinit var stats: Statistics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settings = Settings(allowZero = false, allowNegatives = false, 60)
        stats = Statistics(0, 0, 0.0)
    }
}