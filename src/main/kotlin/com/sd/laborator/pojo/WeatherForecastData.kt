package com.sd.laborator.pojo

import java.util.Date

data class WeatherForecastData (
    var location: String,
    var date: String,
    var weatherState:String,
    //var weatherStateIconURL:String,
    var windDirection:String,
    var windSpeed:Int,
    var minTemp:Int,
    var maxTemp:Int,
    var currentTemp:Int,
    var humidity:Int
    )