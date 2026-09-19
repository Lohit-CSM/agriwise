package com.example.data

import com.example.model.DailyForecast
import com.example.model.WeatherInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object WeatherRepository {

    // Dynamic weather state based on farmer/FPO location
    private val defaultWeather = WeatherInfo(
        locationName = "Guntur, Andhra Pradesh",
        temperatureCelsius = 31,
        condition = "Mostly Sunny",
        conditionIcon = "Sunny",
        humidityPercent = 64,
        rainfallChancePercent = 15,
        windSpeedKmh = 14,
        advisory = "Favorable dry conditions for sun-drying Red Chilli and harvesting field crops. High afternoon solar radiation.",
        harvestSuitability = "Optimal for Field Harvest & Open Sun Drying",
        weeklyForecast = listOf(
            DailyForecast("Today", 33, 24, "Sunny", 10),
            DailyForecast("Tomorrow", 34, 25, "Sunny", 15),
            DailyForecast("Wed", 32, 24, "Partly Cloudy", 20),
            DailyForecast("Thu", 30, 23, "Light Showers", 45),
            DailyForecast("Fri", 31, 23, "Scattered Clouds", 25)
        )
    )

    private val _weatherState = MutableStateFlow(defaultWeather)
    val weatherState: StateFlow<WeatherInfo> = _weatherState.asStateFlow()

    fun updateLocationWeather(district: String, state: String) {
        val loc = "$district, $state"
        val isRainyRegion = district.contains("Visakhapatnam", ignoreCase = true) || district.contains("Kerala", ignoreCase = true)
        val temp = if (district.contains("Guntur", ignoreCase = true)) 33 else 30
        
        _weatherState.value = WeatherInfo(
            locationName = loc,
            temperatureCelsius = temp,
            condition = if (isRainyRegion) "Scattered Rain" else "Clear & Sunny",
            conditionIcon = if (isRainyRegion) "Rain" else "Sunny",
            humidityPercent = if (isRainyRegion) 78 else 58,
            rainfallChancePercent = if (isRainyRegion) 65 else 12,
            windSpeedKmh = 16,
            advisory = if (isRainyRegion)
                "Precipitation expected in coastal pockets. Secure harvested chilli lots in covered sheds or polyhouse storage."
            else
                "Clear sunny weather continuing for the next 72 hours. Ideal for pod moisture reduction below 10.5%.",
            harvestSuitability = if (isRainyRegion) "Caution: Store in Dry Covered Shed" else "Optimal for Field Harvest & Drying",
            weeklyForecast = listOf(
                DailyForecast("Today", temp, temp - 8, if (isRainyRegion) "Rain" else "Sunny", if (isRainyRegion) 65 else 12),
                DailyForecast("Tomorrow", temp + 1, temp - 7, if (isRainyRegion) "Cloudy" else "Sunny", if (isRainyRegion) 50 else 10),
                DailyForecast("Day 3", temp - 1, temp - 8, "Sunny", 20),
                DailyForecast("Day 4", temp, temp - 7, "Partly Cloudy", 25),
                DailyForecast("Day 5", temp + 2, temp - 6, "Sunny", 15)
            )
        )
    }

    fun setCustomCity(cityName: String) {
        val temp = (28..35).random()
        val rain = (5..40).random()
        _weatherState.value = _weatherState.value.copy(
            locationName = cityName,
            temperatureCelsius = temp,
            rainfallChancePercent = rain,
            condition = if (rain > 30) "Chance of Showers" else "Mostly Sunny",
            conditionIcon = if (rain > 30) "Rain" else "Sunny"
        )
    }
}
