package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.theme.*

@Composable
fun ProducePassportScreen(
    modifier: Modifier = Modifier
) {
    val lot by AgriRepository.currentLot.collectAsState()
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
                text = "Digital Produce Passport",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
            Text(
                text = "End-to-end farmgate traceability & verified quality parameters",
                fontSize = 12.sp,
                color = AgriTextSecondary
            )
        }

        // Passport Certificate Card
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriSurface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriForestGreen.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "OFFICIAL BATCH PASSPORT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriForestGreen
                        )
                        Text(
                            text = lot.id,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTextPrimary
                        )
                    }

                    Surface(
                        color = AgriLightGreen,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "VERIFIED LOT",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriDeepGreen,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // QR Code & Key Specs Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Stylized QR code canvas
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
                        modifier = Modifier.size(100.dp)
                    ) {
                        Canvas(modifier = Modifier.padding(8.dp)) {
                            val blockSize = size.width / 7
                            val dark = AgriDeepGreen

                            // Draw QR alignment squares
                            drawRect(dark, Offset(0f, 0f), Size(blockSize * 2, blockSize * 2))
                            drawRect(dark, Offset(size.width - blockSize * 2, 0f), Size(blockSize * 2, blockSize * 2))
                            drawRect(dark, Offset(0f, size.height - blockSize * 2), Size(blockSize * 2, blockSize * 2))

                            // Draw simulated matrix dots
                            for (i in 0..6) {
                                for (j in 0..6) {
                                    if ((i + j) % 2 == 0 && (i > 1 && j > 1 && i < 5 && j < 5)) {
                                        drawRect(dark, Offset(i * blockSize, j * blockSize), Size(blockSize * 0.8f, blockSize * 0.8f))
                                    }
                                }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        PassportField(label = "Crop / Variety", value = "${lot.crop.displayName} (${lot.grade})")
                        PassportField(label = "Verified Lot Size", value = "${lot.quantityTonnes} Tonnes (${(lot.quantityTonnes * 10).toInt()} Q)")
                        PassportField(label = "Origin", value = "${lot.mandal}, ${lot.district}, ${lot.state}")
                        PassportField(label = "Holding Entity", value = lot.ownerName)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = AgriBorder)

                // Quality Assay Details
                Text(
                    text = "Certified Quality Assay Parameters",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriForestGreen
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QualityMetricBox(title = "Moisture", value = "${lot.moisturePercent}%", sub = "Export grade <= 11%")
                    QualityMetricBox(title = "Damage %", value = "${lot.damagePercent}%", sub = "Tolerance < 2.0%")
                    QualityMetricBox(title = "Color (ASTA)", value = "120+", sub = "Deep Crimson Red")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Accreditation: ${lot.certification}",
                    fontSize = 10.sp,
                    color = AgriTextMuted
                )
            }
        }

        IndicativeDisclaimerBadge()

        // Traceability Timeline
        Text(
            text = "Farm-to-Market Milestones",
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
                TimelineStep(
                    isCompleted = true,
                    date = "15 Aug 2026",
                    title = "Soil & Seed Cultivation Verified",
                    desc = "Tenali cluster APEDA registered cluster plot #74"
                )
                TimelineStep(
                    isCompleted = true,
                    date = "12 Sep 2026",
                    title = "Harvest & Sun-Drying Completed",
                    desc = "Clean concrete yard drying with moisture reduction to 10.2%"
                )
                TimelineStep(
                    isCompleted = true,
                    date = "17 Sep 2026",
                    title = "Assay Laboratory Grading",
                    desc = "Moisture, capsaicin & aflatoxin certification passed"
                )
                TimelineStep(
                    isCompleted = true,
                    date = "18 Sep 2026 (Today)",
                    title = "Active on AgriWise Supply Discovery",
                    desc = "4 institutional buyer bids received with top indicative offer at ₹19,200/q"
                )
                TimelineStep(
                    isCompleted = false,
                    date = "Pending Decision",
                    title = "Dispatch & Weighbridge Settlement",
                    desc = "Farmgate pickup or institutional delivery upon offer acceptance"
                )
            }
        }
    }
}

@Composable
private fun PassportField(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 9.sp, color = AgriTextMuted)
        Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AgriTextPrimary)
    }
}

@Composable
private fun QualityMetricBox(title: String, value: String, sub: String) {
    Surface(
        color = AgriSurfaceVariant,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 10.sp, color = AgriTextSecondary)
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = AgriForestGreen)
            Text(text = sub, fontSize = 8.sp, color = AgriTextMuted, maxLines = 1)
        }
    }
}

@Composable
private fun TimelineStep(
    isCompleted: Boolean,
    date: String,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isCompleted) AgriForestGreen else AgriBorder,
            modifier = Modifier.size(18.dp)
        )
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AgriTextPrimary)
                Text(text = date, fontSize = 9.sp, color = AgriTextMuted)
            }
            Text(text = desc, fontSize = 10.sp, color = AgriTextSecondary)
        }
    }
}
