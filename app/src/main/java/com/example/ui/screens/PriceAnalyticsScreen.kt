package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.theme.*

@Composable
fun PriceAnalyticsScreen(
    modifier: Modifier = Modifier
) {
    val history = AgriRepository.priceHistory7Days
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AgriBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                text = "Market Price Trends & Signals",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
            Text(
                text = "Real-time historical trend: Indicative Headline vs. Realizable Takeaway",
                fontSize = 12.sp,
                color = AgriTextSecondary
            )
        }

        IndicativeDisclaimerBadge(
            text = "Price trend is indicative and based on available market signals. Not a speculative futures tool."
        )

        // Interactive Trend Chart Card
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriSurface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Red Chilli 7-Day Parity Curve",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                    Surface(
                        color = AgriLightGreen,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "+6.7% this week",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriDeepGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Canvas Trend Chart
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height
                        val minPrice = 16500.0
                        val maxPrice = 19500.0
                        val priceRange = maxPrice - minPrice

                        // Draw horizontal grid lines
                        val gridLines = 4
                        for (i in 0..gridLines) {
                            val y = height * (i.toFloat() / gridLines)
                            drawLine(
                                color = Color(0xFFE2E8F0),
                                start = Offset(0f, y),
                                end = Offset(width, y),
                                strokeWidth = 1f
                            )
                        }

                        // Path for Indicative Headline (Gold Accent)
                        val headlinePath = Path()
                        val realizablePath = Path()
                        val mandiPath = Path()

                        val stepX = width / (history.size - 1)

                        history.forEachIndexed { index, pt ->
                            val x = index * stepX
                            val yHeadline = height - ((pt.indicativeHeadline - minPrice) / priceRange * height).toFloat()
                            val yRealizable = height - ((pt.estimatedRealizable - minPrice) / priceRange * height).toFloat()
                            val yMandi = height - ((pt.localMandiPrice - minPrice) / priceRange * height).toFloat()

                            if (index == 0) {
                                headlinePath.moveTo(x, yHeadline)
                                realizablePath.moveTo(x, yRealizable)
                                mandiPath.moveTo(x, yMandi)
                            } else {
                                headlinePath.lineTo(x, yHeadline)
                                realizablePath.lineTo(x, yRealizable)
                                mandiPath.lineTo(x, yMandi)
                            }

                            // Draw dots on realizable
                            drawCircle(
                                color = AgriForestGreen,
                                radius = 4f,
                                center = Offset(x, yRealizable)
                            )
                        }

                        drawPath(
                            path = headlinePath,
                            color = AgriGoldAccent,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawPath(
                            path = realizablePath,
                            color = AgriForestGreen,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawPath(
                            path = mandiPath,
                            color = Color(0xFF94A3B8),
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // X-Axis labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "12 Sep", fontSize = 9.sp, color = AgriTextMuted)
                    Text(text = "14 Sep", fontSize = 9.sp, color = AgriTextMuted)
                    Text(text = "16 Sep", fontSize = 9.sp, color = AgriTextMuted)
                    Text(text = "18 Sep (Today)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = AgriForestGreen)
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ChartLegendItem(color = AgriGoldAccent, label = "Headline Quote")
                    ChartLegendItem(color = AgriForestGreen, label = "Realizable Net")
                    ChartLegendItem(color = Color(0xFF94A3B8), label = "Local Mandi Avg")
                }
            }
        }

        // Price Spread Table
        Text(
            text = "Channel Price Spread (₹ / quintal)",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = AgriTextPrimary
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = AgriSurface),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                SpreadRow(channel = "Local Guntur APMC Mandi", price = "₹18,000", realizable = "₹17,700", gap = "Base")
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = AgriBorder)
                SpreadRow(channel = "State Food Processors", price = "₹19,000", realizable = "₹18,400", gap = "+₹700 /q net")
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = AgriBorder)
                SpreadRow(channel = "National Institutional Buyer", price = "₹20,100", realizable = "₹18,700", gap = "+₹1,000 /q net")
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = AgriBorder)
                SpreadRow(channel = "Export Parity (Dubai Hub)", price = "₹22,000 equiv.", realizable = "₹19,100", gap = "+₹1,400 /q net")
            }
        }
    }
}

@Composable
private fun ChartLegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Surface(color = color, shape = CircleShape, modifier = Modifier.size(8.dp)) {}
        Text(text = label, fontSize = 11.sp, color = AgriTextSecondary)
    }
}

@Composable
private fun SpreadRow(channel: String, price: String, realizable: String, gap: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1.3f)) {
            Text(text = channel, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AgriTextPrimary)
            Text(text = "Headline: $price", fontSize = 10.sp, color = AgriTextMuted)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = realizable, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriForestGreen)
            Text(text = gap, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = if (gap == "Base") AgriTextMuted else AgriForestGreen)
        }
    }
}
