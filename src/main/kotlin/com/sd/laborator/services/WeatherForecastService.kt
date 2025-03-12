package com.sd.laborator.services

import com.sd.laborator.interfaces.GeoFilterInterface
import com.sd.laborator.interfaces.TimeInterface
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL

@Service
class WeatherForecastService(private val timeService:TimeInterface,
                             private val geoFilterService: GeoFilterService) : WeatherForecastInterface{
    override fun getForecastData(locationName:String): WeatherForecastData {
        val forecastURL = URL("https://wttr.in/${locationName}?format=j1")



        val rawResponse: String = forecastURL.readText()
        val responseRootObject = JSONObject(rawResponse)
        val locationFound =  responseRootObject.getJSONArray("nearest_area").getJSONObject(0).getJSONArray("areaName").getJSONObject(0).getString("value").toString()

        if(!geoFilterService.isAccessAllowed(locationFound))
            throw Exception("You cannot access informations for $locationName")
        else {
            val weatherDataObject = responseRootObject.getJSONArray("weather").getJSONObject(0)
            val currentConditionDataObject = responseRootObject.getJSONArray("current_condition").getJSONObject(0)
            val hourlyDataObject =
                weatherDataObject.getJSONArray("hourly").getJSONObject(0).getJSONArray("weatherDesc").getJSONObject(0)
                    .getString("value")
            val temp = currentConditionDataObject.getString("temp_C").toInt()


            return WeatherForecastData(
                location = locationName,
                date = timeService.getCurrentTime(),
                weatherState = hourlyDataObject,
                windDirection = currentConditionDataObject.getString("winddir16Point"),
                windSpeed = currentConditionDataObject.getInt("windspeedKmph"),
                minTemp = weatherDataObject.getInt("mintempC"),
                maxTemp = weatherDataObject.getInt("maxtempC"),
                currentTemp = temp,
                humidity = currentConditionDataObject.getInt("humidity")
            )
        }
    }

}