package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.WeatherRepository
import com.example.model.WeatherInfo
import com.example.ui.theme.*

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier
) {
    val weather by WeatherRepository.weatherState.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Location and Weather Condition
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AgriSkyLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Weather Icon",
                            tint = AgriGoldAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = weather.locationName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTextPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(
                                color = AgriEmeraldAccent.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "LIVE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = AgriForestGreen,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        Text(
                            text = "${weather.condition} • Local Crop Microclimate",
                            fontSize = 11.sp,
                            color = AgriTextSecondary
                        )
                    }
                }

                // Temperature Indicator
                Text(
                    text = "${weather.temperatureCelsius}°C",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriPrimaryGreen
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Microclimate Stats Grid (Humidity, Rain Chance, Wind)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AgriSurfaceVariant, RoundedCornerShape(10.dp))
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherMiniStat(
                    icon = Icons.Default.WaterDrop,
                    value = "${weather.humidityPercent}%",
                    label = "Humidity",
                    tint = AgriSkyBlue
                )
                VerticalDivider(modifier = Modifier.height(28.dp), color = AgriBorder)
                WeatherMiniStat(
                    icon = Icons.Default.CloudQueue,
                    value = "${weather.rainfallChancePercent}%",
                    label = "Rain Risk",
                    tint = if (weather.rainfallChancePercent > 40) AgriChilliRed else AgriEmeraldAccent
                )
                VerticalDivider(modifier = Modifier.height(28.dp), color = AgriBorder)
                WeatherMiniStat(
                    icon = Icons.Default.Air,
                    value = "${weather.windSpeedKmh} km/h",
                    label = "Wind Speed",
                    tint = AgriForestGreen
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Agricultural Harvesting Suitability Badge
            Surface(
                color = AgriUltraLightGreen,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriMintGreen.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Grass,
                        contentDescription = null,
                        tint = AgriForestGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = weather.harvestSuitability,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriDeepGreen
                        )
                        Text(
                            text = weather.advisory,
                            fontSize = 10.sp,
                            color = AgriTextSecondary,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            // Expand 5-Day Forecast toggle
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isExpanded) "Hide 5-Day Forecast ▲" else "View 5-Day Farm Forecast ▼",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriForestGreen
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    HorizontalDivider(color = AgriBorder)
                    weather.weeklyForecast.forEach { day ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = day.day,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AgriTextPrimary,
                                modifier = Modifier.width(70.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (day.rainProb > 30) Icons.Default.Thunderstorm else Icons.Default.WbSunny,
                                    contentDescription = null,
                                    tint = if (day.rainProb > 30) AgriSkyBlue else AgriGoldAccent,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = day.condition,
                                    fontSize = 11.sp,
                                    color = AgriTextSecondary
                                )
                            }
                            Text(
                                text = "${day.highTemp}° / ${day.lowTemp}°",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriDeepGreen
                            )
                            Text(
                                text = "${day.rainProb}% rain",
                                fontSize = 10.sp,
                                color = if (day.rainProb > 30) AgriChilliRed else AgriTextMuted,
                                modifier = Modifier.width(55.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherMiniStat(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    tint: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(14.dp))
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
        }
        Text(
            text = label,
            fontSize = 9.sp,
            color = AgriTextMuted
        )
    }
}
