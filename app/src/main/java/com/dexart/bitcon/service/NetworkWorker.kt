package com.dexart.bitcon.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dexart.bitcon.App
import com.dexart.bitcon.MainActivity
import com.dexart.bitcon.model.Currency
import com.dexart.bitcon.network.BitcoinAPI
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit

class NetworkWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val notificationId = 100
    private lateinit var nm: NotificationManagerCompat

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {

        val ctx = applicationContext
        nm = NotificationManagerCompat.from(ctx)

        var isSuccess = false

        // callback for API response
        val callback = object : Callback<Currency> {
            override fun onResponse(
                response: Response<Currency>?,
                retrofit: retrofit.Retrofit?
            ) {
                if (response?.code() == 200) {
                    createNotification(ctx, response.body())
                    isSuccess = true
                }
            }

            override fun onFailure(t: Throwable?) {
                isSuccess = false
                destroyNotification()
            }
        }
        // Symbol == BTC-USD, BTC-INR
         val retrofit = Retrofit.Builder()
            .baseUrl("https://api.blockchain.com/v3/exchange/")
            .addConverterFactory(GsonConverterFactory.create())
             .callbackExecutor(taskExecutor.mainThreadExecutor)
            .build()
        val api = retrofit.create(BitcoinAPI::class.java).getTickerBySymbol("BTC-USD")
        api.enqueue(callback)

        //val call = Retrofit.getBitcoinAPI().getTickerBySymbol("BTC-USD")

        // request to network
        //call.enqueue(callback)

        if (isSuccess)
            return Result.success()
        else
            return Result.failure()

    }

    fun createNotification(ctx: Context, currency: Currency) {
        val i = Intent(ctx, MainActivity::class.java)
        i.putExtra("currency", currency)
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pi = PendingIntent.getActivity(ctx, 300, i, PendingIntent.FLAG_CANCEL_CURRENT)

        val notification =
            NotificationCompat.Builder(applicationContext, App.NOTIFICATION_CHANNEL_ID).run {
                setSmallIcon(android.R.drawable.ic_popup_sync)
                setContentTitle("TickerService")
                setContentText("New data available")
                setContentIntent(pi)
                build()
            }

        nm.notify(notificationId, notification)
    }

    fun destroyNotification() {
        nm.cancel(notificationId)
    }


}