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
import com.example.model.AggregationLot
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.theme.*

@Composable
fun AggregationScreen(
    modifier: Modifier = Modifier
) {
    val aggregation by AgriRepository.aggregation.collectAsState()
    var joinedPool by remember { mutableStateOf(false) }

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
                    text = "Collective Supply Aggregation",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = "AgriWise matches nearby compatible lots to unlock bulk institutional contracts",
                    fontSize = 12.sp,
                    color = AgriTextSecondary
                )
            }
        }

        item {
            IndicativeDisclaimerBadge(
                text = "Indicative analysis: pooling is voluntary and subject to joint grade consolidation & FPO agreement."
            )
        }

        // Summary Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AgriForestGreen),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "COMPATIBLE SUPPLY DETECTED",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriMintGreen
                        )
                        Surface(
                            color = AgriGoldAccent,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "REQUIREMENT MET",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF261A00),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Target Buyer: ${aggregation.targetBuyerName}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Your Lot", fontSize = 11.sp, color = AgriLightGreen)
                            Text(
                                text = "${aggregation.userLotTonnes.toInt()} Tonnes",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Column {
                            Text(text = "Nearby Supply", fontSize = 11.sp, color = AgriLightGreen)
                            Text(
                                text = "+${aggregation.nearbyLots.sumOf { it.quantityTonnes }.toInt()} Tonnes",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriGoldAccent
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Combined Total", fontSize = 11.sp, color = AgriLightGreen)
                            Text(
                                text = "${aggregation.totalAggregatedTonnes.toInt()} / ${aggregation.institutionalRequirementTonnes.toInt()} Tonnes",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = { 1.0f },
                        color = AgriGoldAccent,
                        trackColor = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
            }
        }

        // Benefits Grid
        item {
            Text(
                text = "Collective Benefits",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BenefitCard(
                    title = "Potential Logistics Efficiency",
                    highlight = "Save ~₹220 /q",
                    desc = "Bulk multi-axle freight shared across pooled collection points.",
                    modifier = Modifier.weight(1f)
                )
                BenefitCard(
                    title = "Potential Bargaining Benefit",
                    highlight = "+3.5% Premium",
                    desc = "Large lot sizes command better institutional contract terms.",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Text(
                text = "Nearby Compatible Lots within 25 km",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
        }

        items(aggregation.nearbyLots, key = { it.id }) { lot ->
            NearbyLotCard(lot = lot)
        }

        item {
            Button(
                onClick = { joinedPool = !joinedPool },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (joinedPool) AgriForestGreen else AgriPrimaryGreen
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = if (joinedPool) Icons.Default.CheckCircle else Icons.Default.GroupAdd,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (joinedPool) "COLLECTIVE OFFER DISPATCHED (21 TONNES)" else "EXPLORE COLLECTIVE OFFER (JOIN POOL)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun BenefitCard(
    title: String,
    highlight: String,
    desc: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontSize = 11.sp, color = AgriTextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = highlight, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = AgriForestGreen)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = desc, fontSize = 10.sp, color = AgriTextMuted, lineHeight = 13.sp)
        }
    }
}

@Composable
private fun NearbyLotCard(lot: AggregationLot) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lot.farmerOrFpoName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = "${lot.location} • ${lot.grade}",
                    fontSize = 11.sp,
                    color = AgriTextSecondary
                )
            }

            Surface(
                color = AgriLightGreen,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "${lot.quantityTonnes.toInt()} Tonnes",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriDeepGreen,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
