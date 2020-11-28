package com.dexart.bitcon.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dexart.bitcon.App

class TickerUpdateReceiver : BroadcastReceiver() {

    // notification id to cancel a notification in the future
    private val notificationId = 200
    private val TAG = "TickerBroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        // context.startActivity()
        // pending intent
        Toast.makeText(context, "Received Broadcast", Toast.LENGTH_LONG).show()
//        when (TickerService.ACTION_TICKER == intent?.action) {
//            true -> {
//                val notificationManager = NotificationManagerCompat.from(context!!)
//                val notification = NotificationCompat.Builder(context, App.NOTIFICATION_CHANNEL_ID)
//                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
//                    .setContentTitle("BR Ticker")
//                    .setContentText("BR Ticker Updated")
//                    .build()
//
//                //startForeground(notificationId, notif)
//                notificationManager.notify(notificationId, notification)
//            }
//        }


    }
}