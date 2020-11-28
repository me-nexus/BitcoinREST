package com.dexart.bitcon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.dexart.bitcon.model.Currency
import com.dexart.bitcon.network.Retrofit
import com.dexart.bitcon.service.NetworkWorker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import retrofit.Callback
import retrofit.Response
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        workManager = WorkManager.getInstance(this)

        intent?.getParcelableExtra<Currency>("currency")?.let {
            setFieldValues(it)
        }

        // callback for API response
        val callback = object : Callback<Currency> {
            override fun onResponse(
                response: Response<Currency>?,
                retrofit: retrofit.Retrofit?
            ) {
                if (response?.code() == 200) {
                    val body = response.body()
                    Log.d(TAG, body.toString())
                    setFieldValues(body)
                }
            }

            override fun onFailure(t: Throwable?) {
                Snackbar.make(root, "No Internet Connection", Snackbar.LENGTH_SHORT)
                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
            }
        }

        // Symbol == BTC-USD, BTC-INR
        val call = Retrofit.getBitcoinAPI().getTickerBySymbol("BTC-USD")

        // request to network
        call.enqueue(callback)

        //Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG).show()
    }

    private fun setFieldValues(body: Currency) {
        tv_symbol.text = body.symbol
        tv_price.text = "Price : ${body.price24h}"
        tv_volume.text = "Volume: ${body.volume24h}"
        tv_lastPrice.text = "LastPrice: ${body.lastTradePrice}"
    }


    // start the service to observe the API updates, when activity hides
    override fun onStop() {
        // Poll the API evey 15 sec and show notification
        val info = workManager.getWorkInfosByTag("network")
        if (info.isCancelled) {
            val periodicWorkRequest =
                PeriodicWorkRequest.Builder(NetworkWorker::class.java, 60, TimeUnit.SECONDS)
                    .addTag("network")
                    .build()
            workManager.enqueue(periodicWorkRequest)
        }
        super.onStop()
    }
}