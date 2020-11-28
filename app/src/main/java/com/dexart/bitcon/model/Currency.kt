package com.dexart.bitcon.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Currency(
    @SerializedName("symbol")
    @Expose
    var symbol: String? = null,

    @SerializedName("price_24h")
    @Expose
    var price24h: String? = null,

    @SerializedName("volume_24h")
    @Expose
    var volume24h: String? = null,

    @SerializedName("last_trade_price")
    @Expose
    var lastTradePrice: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(symbol)
        parcel.writeString(price24h)
        parcel.writeString(volume24h)
        parcel.writeString(lastTradePrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Currency> {
        override fun createFromParcel(parcel: Parcel): Currency {
            return Currency(parcel)
        }

        override fun newArray(size: Int): Array<Currency?> {
            return arrayOfNulls(size)
        }
    }
}