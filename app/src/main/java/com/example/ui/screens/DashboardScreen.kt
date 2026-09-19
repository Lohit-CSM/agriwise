package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.components.ProducePassportSummaryCard
import com.example.ui.components.WeatherCard
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    onCreateLotClick: () -> Unit,
    onPriceDiscoveryClick: () -> Unit,
    onMarketsClick: () -> Unit,
    onOffersClick: () -> Unit,
    onAggregationClick: () -> Unit,
    onGlobalClick: () -> Unit,
    onExportReadinessClick: () -> Unit,
    onPassportClick: () -> Unit,
    onAiChatbotClick: () -> Unit = {},
    onCallSupportClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currentLot by AgriRepository.currentLot.collectAsState()
    val currentUser by AgriRepository.currentUser.collectAsState()
    val offers by AgriRepository.offers.collectAsState()
    val markets by AgriRepository.markets.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AgriBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Welcome and User Header
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriForestGreen),
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Namaste, ${currentUser.name}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${currentUser.organization} • ${currentUser.location}",
                        fontSize = 11.sp,
                        color = AgriLightGreen
                    )
                }

                Surface(
                    color = AgriGoldAccent,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = currentUser.role.name,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF261A00),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // 24/7 Farmer Call Support & AI Chatbot Action Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                onClick = onCallSupportClick,
                colors = CardDefaults.cardColors(containerColor = AgriUltraLightGreen),
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriMintGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(68.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = AgriPrimaryGreen,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.PhoneInTalk, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                    Column {
                        Text(text = "24/7 Call Support", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AgriDeepGreen)
                        Text(text = "Kisan & AgriWise Helpline", fontSize = 10.sp, color = AgriTextSecondary)
                    }
                }
            }

            Card(
                onClick = onAiChatbotClick,
                colors = CardDefaults.cardColors(containerColor = AgriForestGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(68.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = AgriMintGreen,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = AgriDeepGreen, modifier = Modifier.size(20.dp))
                        }
                    }
                    Column {
                        Text(text = "Ask AI Chatbot", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "Prices, Buyers & Grading", fontSize = 10.sp, color = AgriLightGreen)
                    }
                }
            }
        }

        // Live Microclimate Weather Card
        WeatherCard()

        // Active Produce Lot Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Active Produce Lot",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
            TextButton(onClick = onPassportClick) {
                Text(
                    text = "Produce Passport →",
                    fontSize = 12.sp,
                    color = AgriForestGreen,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        ProducePassportSummaryCard(lot = currentLot)

        // Key Value Metrics Banner
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriSurface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "SUPPLY-FIRST PRICE DISCOVERY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriForestGreen
                )
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "Reference Market Price",
                            fontSize = 11.sp,
                            color = AgriTextSecondary
                        )
                        Text(
                            text = "₹18,500 / quintal",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextPrimary
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Est. Realizable Range",
                            fontSize = 11.sp,
                            color = AgriForestGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹17,400 – ₹19,100 /q",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriForestGreen
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = AgriBorder)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MetricCounter(label = "Market Opportunities", count = "12 discovered", onClick = onMarketsClick)
                    MetricCounter(label = "Buyer Offers", count = "${offers.size} competitive", onClick = onOffersClick)
                    MetricCounter(label = "Aggregation Fit", count = "21T pooled", onClick = onAggregationClick)
                }
            }
        }

        IndicativeDisclaimerBadge()

        // Quick Actions Grid
        Text(
            text = "Explore Discovery Modules",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = AgriTextPrimary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionTile(
                icon = Icons.Default.AddCircle,
                title = "Create New Lot",
                desc = "Add crop, grade & location",
                bg = AgriLightGreen,
                onClick = onCreateLotClick,
                modifier = Modifier.weight(1f)
            )
            ActionTile(
                icon = Icons.Default.Analytics,
                title = "Price Discovery",
                desc = "Cost waterfall & net value",
                bg = AgriSurfaceVariant,
                onClick = onPriceDiscoveryClick,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionTile(
                icon = Icons.Default.Storefront,
                title = "Buyer Discovery",
                desc = "Verified food millers & bids",
                bg = AgriSurfaceVariant,
                onClick = onOffersClick,
                modifier = Modifier.weight(1f)
            )
            ActionTile(
                icon = Icons.Default.GroupWork,
                title = "Supply Aggregation",
                desc = "Pool with nearby farmers",
                bg = AgriLightGreen,
                onClick = onAggregationClick,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionTile(
                icon = Icons.Default.Public,
                title = "Global Radar",
                desc = "UAE, Saudi & Asian demand",
                bg = AgriSurfaceVariant,
                onClick = onGlobalClick,
                modifier = Modifier.weight(1f)
            )
            ActionTile(
                icon = Icons.Default.FactCheck,
                title = "Export Readiness",
                desc = "6-step compliance checker",
                bg = AgriSurfaceVariant,
                onClick = onExportReadinessClick,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun MetricCounter(
    label: String,
    count: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = AgriSurfaceVariant,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AgriForestGreen
            )
            Text(
                text = label,
                fontSize = 9.sp,
                color = AgriTextSecondary
            )
        }
    }
}

@Composable
private fun ActionTile(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String,
    bg: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = bg),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = modifier.height(105.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AgriForestGreen,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = desc,
                    fontSize = 10.sp,
                    color = AgriTextSecondary,
                    maxLines = 1
                )
            }
        }
    }
}
