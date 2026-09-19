package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProduceLot
import com.example.ui.theme.*

@Composable
fun SihTopBanner(
    onStartDemoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = AgriDeepGreen,
        contentColor = Color.White,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = AgriGoldAccent,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "SIH 2026",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF261A00),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = "PS ID: SIH26132 • Market Linkages & Price Discovery",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    maxLines = 1
                )
            }

            Button(
                onClick = onStartDemoClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AgriMintGreen,
                    contentColor = AgriDeepGreen
                ),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                modifier = Modifier.height(26.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "RUN SIH DEMO",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun IndicativeDisclaimerBadge(
    modifier: Modifier = Modifier,
    text: String = "Indicative estimate — final price depends on buyer negotiation, quality verification, logistics & market conditions."
) {
    Surface(
        color = AgriSurfaceVariant,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = AgriForestGreen,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = text,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                color = AgriTextSecondary
            )
        }
    }
}

@Composable
fun ProducePassportSummaryCard(
    lot: ProduceLot,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = AgriLightGreen,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = lot.crop.displayName.uppercase(),
                            color = AgriDeepGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Surface(
                        color = AgriGoldAccent.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = lot.grade,
                            color = Color(0xFF6B4800),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }

                Text(
                    text = "LOT: ${lot.id}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = AgriTextMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Quantity",
                        fontSize = 11.sp,
                        color = AgriTextSecondary
                    )
                    Text(
                        text = "${lot.quantityTonnes} Tonnes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                }

                Column {
                    Text(
                        text = "Location",
                        fontSize = 11.sp,
                        color = AgriTextSecondary
                    )
                    Text(
                        text = "${lot.district}, ${lot.state}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AgriTextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Availability",
                        fontSize = 11.sp,
                        color = AgriTextSecondary
                    )
                    Text(
                        text = "In ${lot.availabilityDays} Days",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AgriForestGreen
                    )
                }
            }
        }
    }
}

@Composable
fun OpportunityScoreIndicator(
    score: Int,
    priceFit: Int,
    demandFit: Int,
    qualityFit: Int,
    logisticsFit: Int,
    reliabilityFit: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurfaceVariant),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AgriWise Opportunity Score",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                    Text(
                        text = "Configured multi-factor fit analysis",
                        fontSize = 10.sp,
                        color = AgriTextMuted
                    )
                }

                Surface(
                    color = if (score >= 85) AgriForestGreen else AgriPrimaryGreen,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "$score / 100",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ScoreFactorBar(label = "Price Fit (30%)", percent = priceFit)
            Spacer(modifier = Modifier.height(6.dp))
            ScoreFactorBar(label = "Demand Fit (25%)", percent = demandFit)
            Spacer(modifier = Modifier.height(6.dp))
            ScoreFactorBar(label = "Quality Fit (20%)", percent = qualityFit)
            Spacer(modifier = Modifier.height(6.dp))
            ScoreFactorBar(label = "Logistics Fit (15%)", percent = logisticsFit)
            Spacer(modifier = Modifier.height(6.dp))
            ScoreFactorBar(label = "Buyer Reliability (10%)", percent = reliabilityFit)
        }
    }
}

@Composable
private fun ScoreFactorBar(
    label: String,
    percent: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = AgriTextSecondary,
            modifier = Modifier.width(130.dp)
        )
        LinearProgressIndicator(
            progress = { percent / 100f },
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (percent >= 80) AgriForestGreen else AgriMintGreen,
            trackColor = AgriBorder,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$percent%",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = AgriTextPrimary,
            modifier = Modifier.width(36.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun RealizableWaterfallCard(
    headlinePrice: Double,
    logisticsCost: Double,
    handlingCost: Double,
    marketFees: Double,
    otherCosts: Double,
    modifier: Modifier = Modifier
) {
    val realizable = headlinePrice - (logisticsCost + handlingCost + marketFees + otherCosts)

    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Realizable Value Engine",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Surface(
                    color = AgriLightGreen,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Net In-Hand Focus",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriDeepGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Headline
            WaterfallRow(
                title = "Indicative Headline Price",
                subtitle = "Published benchmark quote",
                amount = headlinePrice,
                isDeduction = false,
                isTotal = false
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = AgriBorder)

            // Deductions
            WaterfallRow(
                title = "Estimated Freight & Logistics",
                subtitle = "Transit distance & vehicle hiring",
                amount = -logisticsCost,
                isDeduction = true,
                isTotal = false
            )
            Spacer(modifier = Modifier.height(4.dp))
            WaterfallRow(
                title = "Estimated Handling & Weighing",
                subtitle = "Hamali, unloading & grading checks",
                amount = -handlingCost,
                isDeduction = true,
                isTotal = false
            )
            Spacer(modifier = Modifier.height(4.dp))
            WaterfallRow(
                title = "Applicable Market User Fees / Cess",
                subtitle = "APMC or platform facilitation fee",
                amount = -marketFees,
                isDeduction = true,
                isTotal = false
            )
            if (otherCosts > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                WaterfallRow(
                    title = "Packing, Storage & Inspection",
                    subtitle = "Moisture packaging & lab checks",
                    amount = -otherCosts,
                    isDeduction = true,
                    isTotal = false
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = AgriPrimaryGreen)

            // Net Realizable Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Estimated Realizable Value",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriForestGreen
                    )
                    Text(
                        text = "What farmer actually takes home per quintal",
                        fontSize = 10.sp,
                        color = AgriTextMuted
                    )
                }
                Text(
                    text = "₹${realizable.toInt()} /q",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriForestGreen
                )
            }
        }
    }
}

@Composable
private fun WaterfallRow(
    title: String,
    subtitle: String,
    amount: Double,
    isDeduction: Boolean,
    isTotal: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
                color = AgriTextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = AgriTextMuted
            )
        }
        Text(
            text = if (isDeduction) "- ₹${(-amount).toInt()}" else "₹${amount.toInt()}",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isDeduction) AgriChilliRed else AgriTextPrimary
        )
    }
}

@Composable
fun ExplainableAiSection(
    reasons: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AgriUltraLightGreen),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriMintGreen.copy(alpha = 0.5f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = AgriForestGreen,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "AgriWise Intelligence — Why this opportunity?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriDeepGreen
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            reasons.forEach { reason ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "•",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriForestGreen
                    )
                    Text(
                        text = reason,
                        fontSize = 11.sp,
                        color = AgriTextPrimary,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}
