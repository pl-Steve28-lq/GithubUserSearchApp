package com.steve28.composepractice.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.steve28.composepractice.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val vm by lazy { MainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { vm.MainComponent() }
    }

    companion object {
        fun log(msg: Any) = Log.d("TAG", msg.toString())
    }
}