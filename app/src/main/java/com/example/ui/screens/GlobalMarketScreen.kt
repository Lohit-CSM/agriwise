package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.model.GlobalOpportunity
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.theme.*

@Composable
fun GlobalMarketScreen(
    onCheckReadinessClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val globalOps by AgriRepository.globalOpportunities.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AgriBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Global Demand Radar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = "Explore international trade benchmarks & export readiness for your lot",
                    fontSize = 12.sp,
                    color = AgriTextSecondary
                )
            }
        }

        item {
            IndicativeDisclaimerBadge(
                text = "Indicative international reference values. AgriWise provides decision support; actual exports require authorized APEDA/customs channels."
            )
        }

        // Global Overview Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AgriDeepGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "APEDA & Spices Board Corridor",
                            fontSize = 11.sp,
                            color = AgriLightGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "6 International Destinations",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Middle East and Southeast Asia show strongest demand for Indian Teja Chilli.",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 14.sp
                        )
                    }

                    Button(
                        onClick = onCheckReadinessClick,
                        colors = ButtonDefaults.buttonColors(containerColor = AgriGoldAccent, contentColor = Color(0xFF261A00)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(text = "Check Readiness", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        items(globalOps, key = { it.id }) { opp ->
            GlobalOpportunityCard(
                opp = opp,
                onCheckReadiness = onCheckReadinessClick
            )
        }
    }
}

@Composable
private fun GlobalOpportunityCard(
    opp: GlobalOpportunity,
    onCheckReadiness: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = opp.flagEmoji, fontSize = 22.sp)
                    Column {
                        Text(
                            text = opp.destinationCountry,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextPrimary
                        )
                        Text(
                            text = opp.targetCommodity,
                            fontSize = 11.sp,
                            color = AgriTextSecondary
                        )
                    }
                }

                Surface(
                    color = AgriLightGreen,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Demand: ${opp.demandLevel}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriDeepGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Indicative International Parity", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "₹${opp.indicativeInternationalPricePerQuintalEq.toInt()} /q equiv.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriForestGreen
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Export Readiness", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = opp.exportReadinessStatus,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriPrimaryGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Spec: ${opp.qualityRequirements}",
                fontSize = 11.sp,
                color = AgriTextPrimary,
                lineHeight = 14.sp
            )
            Text(
                text = "Gateway: ${opp.keyPort}",
                fontSize = 10.sp,
                color = AgriTextMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Logistics Complexity: ${opp.logisticsComplexity}",
                    fontSize = 11.sp,
                    color = AgriTextSecondary
                )

                TextButton(onClick = onCheckReadiness) {
                    Text(
                        text = "Check Readiness Checklist →",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriForestGreen
                    )
                }
            }
        }
    }
}
