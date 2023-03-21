package com.example.lr4and

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lr4and.databinding.ActivityMainBinding
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.StartAppButton.setOnClickListener {
            startService()

        }

        binding.StopAppButton.setOnClickListener {
            stopService()
        }

        binding.getInfoButton.setOnClickListener {
            getInfo()
        }

    }

    private fun startService(){
        if(!isServiceRunning(MyForegroundServise::class.java)){

            Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show()

            startService(Intent(this,MyForegroundServise::class.java))
        }
    }

    private fun stopService(){
        if(isServiceRunning(MyForegroundServise::class.java)){

            Toast.makeText(this,"Service Stopped",Toast.LENGTH_SHORT).show()
            stopService(Intent(this,MyForegroundServise::class.java))

        }
    }

    private fun isServiceRunning(mClass:Class<MyForegroundServise>):Boolean{

        val manager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for(service:ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)){
            if(mClass.name.equals(service.service.className)){
                return true
            }
        }
        return false
    }

    private fun getInfo(){
        binding.requestResultField.text = MyForegroundServise.serverResponse
    }
}