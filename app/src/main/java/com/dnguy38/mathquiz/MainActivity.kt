package com.dnguy38.mathquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnguy38.mathquiz.models.Settings

class MainActivity : AppCompatActivity() {
    lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settings = Settings(true, 60)
    }
}