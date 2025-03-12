package com.sd.laborator.services

import com.fasterxml.jackson.databind.util.JSONPObject
import com.sd.laborator.interfaces.LocationSearchInterface
import org.springframework.stereotype.Service
import java.net.URL
import java.net.URLEncoder
import org.json.JSONObject
import java.nio.charset.StandardCharsets

@Service
class LocationSearchService:LocationSearchInterface {
    override fun getLocationName(locationName: String): String {
        
        val encodedLocationName = URLEncoder.encode(locationName, StandardCharsets.UTF_8.name())

        val locationSearchURL = URL("https://wttr.in/$encodedLocationName?format=j1")

        val rawResponse:String = locationSearchURL.readText()


        val responseRootObject = JSONObject(rawResponse)
        val responeContentObject = responseRootObject.getJSONArray("nearest_area").getJSONObject(0)
        val areaNameObject = responeContentObject.getJSONArray("areaName").getJSONObject(0).getString("value")

        return areaNameObject

    }

}