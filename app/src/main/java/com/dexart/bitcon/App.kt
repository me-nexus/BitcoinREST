package com.dexart.bitcon

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.IntentFilter
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dexart.bitcon.receiver.TickerUpdateReceiver

class App : Application(){


    // notification channel id
    companion object {
        val NOTIFICATION_CHANNEL_ID = "BITCOIN TICKER CHANNEL"
    }

    // broadcast manager
    val tickerUpdateReceiver = TickerUpdateReceiver()

    override fun onCreate() {
        super.onCreate()

        // create the notification channel
        createNotificationChannel()

        // register receiver for listening to BITCOIN_TICKER
        //LocalBroadcastManager.getInstance(this).registerReceiver(tickerUpdateReceiver, intentFilter)

    }

    override fun onTerminate() {
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(tickerUpdateReceiver)
        super.onTerminate()
    }

    // create notification channel
    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Bitcoin Ticker Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val nm = NotificationManagerCompat.from(this)
            nm.createNotificationChannel(channel)
        }
    }


}