package com.insurance.assured.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.insurance.assured.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }
        setContentView(binding.root)
    }
}