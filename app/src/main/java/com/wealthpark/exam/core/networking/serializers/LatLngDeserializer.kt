package com.wealthpark.exam.core.networking.serializers

import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class LatLngDeserializer : JsonDeserializer<LatLng> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LatLng {
        val jsonObject = json?.asJsonObject
        val latitude = jsonObject?.get("lat")!!.asString.toDouble()
        val longitude = jsonObject.get("lng")!!.asString.toDouble()
        return LatLng(latitude, longitude)
    }
}