package com.dexart.bitcon.network

import com.dexart.bitcon.model.Currency
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path

interface BitcoinAPI {

    @GET("tickers/{symbol}")
    fun getTickerBySymbol(@Path("symbol") symbol: String): Call<Currency>

}