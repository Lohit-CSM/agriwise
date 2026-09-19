package com.example.model

data class WeatherInfo(
    val locationName: String,
    val temperatureCelsius: Int,
    val condition: String,
    val conditionIcon: String, // "Sunny", "Rain", "Cloudy", "Clear", "Partly Cloudy"
    val humidityPercent: Int,
    val rainfallChancePercent: Int,
    val windSpeedKmh: Int,
    val advisory: String,
    val harvestSuitability: String, // "Optimal for Harvesting & Sun Drying", "Risk of Spoilage - Hold in Storage", etc.
    val weeklyForecast: List<DailyForecast>
)

data class DailyForecast(
    val day: String,
    val highTemp: Int,
    val lowTemp: Int,
    val condition: String,
    val rainProb: Int
)
