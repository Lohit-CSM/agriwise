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
import com.example.model.MarketCategory
import com.example.model.MarketOpportunity
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.components.OpportunityScoreIndicator
import com.example.ui.components.RealizableWaterfallCard
import com.example.ui.theme.*

@Composable
fun MarketDiscoveryScreen(
    onSelectMarket: (MarketOpportunity) -> Unit,
    modifier: Modifier = Modifier
) {
    val markets by AgriRepository.markets.collectAsState()
    var selectedCategoryFilter by remember { mutableStateOf<MarketCategory?>(null) }
    var selectedMarketForDetail by remember { mutableStateOf<MarketOpportunity?>(null) }

    val filteredMarkets = remember(markets, selectedCategoryFilter) {
        if (selectedCategoryFilter == null) markets else markets.filter { it.category == selectedCategoryFilter }
    }

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
                    text = "Markets Looking for Your Produce",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = "Comparing buyer demand, true freight deductions & realizable values",
                    fontSize = 12.sp,
                    color = AgriTextSecondary
                )
            }
        }

        item {
            // Category Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip(
                    selected = selectedCategoryFilter == null,
                    onClick = { selectedCategoryFilter = null },
                    label = { Text("All (4)", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AgriForestGreen,
                        selectedLabelColor = Color.White
                    )
                )
                MarketCategory.values().forEach { cat ->
                    FilterChip(
                        selected = selectedCategoryFilter == cat,
                        onClick = { selectedCategoryFilter = cat },
                        label = { Text(cat.name, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AgriForestGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        item {
            IndicativeDisclaimerBadge()
        }

        items(filteredMarkets, key = { it.id }) { market ->
            MarketCardItem(
                market = market,
                onViewDetails = { selectedMarketForDetail = market }
            )
        }
    }

    // Modal Sheet / Dialog for Market Details
    selectedMarketForDetail?.let { market ->
        AlertDialog(
            onDismissRequest = { selectedMarketForDetail = null },
            title = {
                Column {
                    Text(
                        text = market.category.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriForestGreen
                    )
                    Text(
                        text = market.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RealizableWaterfallCard(
                        headlinePrice = market.headlinePricePerQuintal,
                        logisticsCost = market.logisticsCostPerQuintal,
                        handlingCost = market.handlingCostPerQuintal,
                        marketFees = market.marketFeesPerQuintal,
                        otherCosts = market.otherApplicableCostsPerQuintal
                    )

                    OpportunityScoreIndicator(
                        score = market.opportunityScore,
                        priceFit = market.priceFitScore,
                        demandFit = market.demandFitScore,
                        qualityFit = market.qualityFitScore,
                        logisticsFit = market.logisticsFitScore,
                        reliabilityFit = market.buyerReliabilityScore
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedMarketForDetail = null },
                    colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen)
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun MarketCardItem(
    market: MarketOpportunity,
    onViewDetails: () -> Unit
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
                Surface(
                    color = when (market.category) {
                        MarketCategory.NEARBY -> AgriLightGreen
                        MarketCategory.STATE -> AgriUltraLightGreen
                        MarketCategory.NATIONAL -> AgriGoldAccent.copy(alpha = 0.3f)
                        MarketCategory.GLOBAL -> Color(0xFFE0F2FE)
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = market.category.name,
                        color = AgriDeepGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = AgriForestGreen,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Fit: ${market.opportunityScore}/100",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = market.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AgriTextMuted, modifier = Modifier.size(13.dp))
                Text(
                    text = "${market.location} • ${market.distanceKm} km",
                    fontSize = 11.sp,
                    color = AgriTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Indicative Headline", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "₹${market.headlinePricePerQuintal.toInt()}/q",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                }
                Column {
                    Text(text = "Transit & Fees", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "-₹${(market.logisticsCostPerQuintal + market.handlingCostPerQuintal + market.marketFeesPerQuintal + market.otherApplicableCostsPerQuintal).toInt()}/q",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriChilliRed
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Est. Realizable", fontSize = 10.sp, color = AgriForestGreen, fontWeight = FontWeight.Bold)
                    Text(
                        text = "₹${market.estimatedRealizableValue.toInt()}/q",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriForestGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Demand: ${market.demandLevel} • ${market.buyerInterestCount} Buyers",
                    fontSize = 11.sp,
                    color = AgriForestGreen,
                    fontWeight = FontWeight.Medium
                )

                OutlinedButton(
                    onClick = onViewDetails,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(text = "View Opportunity", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
