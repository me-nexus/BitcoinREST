package com.dexart.bitcon.network

import okhttp3.OkHttpClient
import retrofit.Call
import retrofit.GsonConverterFactory
import retrofit.Retrofit

object Retrofit {

    //val client = OkHttpClient().newBuilder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.blockchain.com/v3/exchange/")
//        .baseUrl("https://blockchain.info/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getBitcoinAPI(): BitcoinAPI {
        return retrofit.create(BitcoinAPI::class.java)
    }
}