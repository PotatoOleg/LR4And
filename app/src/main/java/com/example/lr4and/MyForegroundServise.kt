package com.example.lr4and

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.lr4and.Constants.CHANNEL_ID
import com.example.lr4and.Constants.NOTIFICATION_ID
import com.example.lr4and.RESTRequest.Api
import com.example.lr4and.RESTRequest.RequestRepository
import kotlinx.coroutines.joinAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyForegroundServise: Service() {

    companion object{
    var serverResponse:String = ""
    }

    var isRunning:Boolean = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        init()
        //createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    isRunning = true
        //showNotification()

        val thread = Thread {
            while(isRunning){
                Thread.sleep(10000)
                Log.e("Service", "ServiceIteration")
                requestData()
            }
        }.start()


        Log.e("AAA",thread.javaClass.toString())

        return START_STICKY
    }

    private fun init(){

    }

    override fun onDestroy() {
        isRunning = false

        super.onDestroy()

    }

    fun requestData(){
        val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build()

        val api=retrofit.create(Api::class.java)

        api.getPosts()?.enqueue(object:Callback<List<RequestRepository?>?>{
            override fun onResponse(


                call: Call<List<RequestRepository?>?>,
                response: Response<List<RequestRepository?>?>
            ) {
                serverResponse=""
                if(!response.isSuccessful()){
                    serverResponse = "Code + "+response.code()
                    return
                }else{
                    var posts = response.body()
                    if (posts != null) {
                        for(post in posts){

                            serverResponse+="ID: "+ post?.id.toString() +"\n"+"Title: "+ post?.title+"\n"+"Text: "+post?.text+"\n\n"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<RequestRepository?>?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        }

        )
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(){
        val notificationIntent = Intent(this,MainActivity::class.java)

        val pendingIntent= PendingIntent.getActivity(
            this,0,notificationIntent,0
        )

        val notification = Notification.Builder(this, CHANNEL_ID).
        setContentText("Notification text").
        setContentIntent(pendingIntent).build()

        startForeground(NOTIFICATION_ID,notification)

    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            val serviseChannel = NotificationChannel(
                CHANNEL_ID,"My Service Channel",NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(serviseChannel)
        }

    }

}