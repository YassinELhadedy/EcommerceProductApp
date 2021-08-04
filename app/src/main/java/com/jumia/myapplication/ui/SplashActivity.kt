package com.jumia.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.jumia.myapplication.infrastructure.dto.JumConfiguration
import com.jumia.myapplication.ui.util.state.Status
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: ConfigurationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.configurationInfo(1)
        observer()
    }
    private fun observer() {
        viewModel.configurationData.observe(this, {
            when (it?.status) {
                Status.SUCCESS -> {
                    if (it.data is JumConfiguration) {
                        Toast.makeText(this, "here", Toast.LENGTH_LONG).show()
                        Intent(this,MainActivity::class.java).apply {
                            startActivity(this)
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                }
            }
        })
    }
}