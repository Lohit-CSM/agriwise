package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.model.MarketOpportunity
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun PriceDiscoveryScreen(
    onExploreMarketsClick: () -> Unit,
    onViewBuyerOffersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lot by AgriRepository.currentLot.collectAsState()
    val markets by AgriRepository.markets.collectAsState()
    val scrollState = rememberScrollState()

    // State processor is the prime benchmark (₹19,000 headline, ₹18,400 realizable)
    val primeMarket = markets.find { it.id == "MKT-02" } ?: markets.first()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AgriBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Produce Summary Banner
        ProducePassportSummaryCard(lot = lot)

        // Core Price Discovery Card
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriForestGreen),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MARKET OPPORTUNITY DISCOVERY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriMintGreen
                    )
                    Surface(
                        color = AgriGoldAccent,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "SUPPLY-MATCHED",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF261A00),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "Indicative Market Range",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )

                Text(
                    text = "₹17,500 – ₹19,200 / quintal",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Est. Realizable Value",
                            fontSize = 11.sp,
                            color = AgriLightGreen
                        )
                        Text(
                            text = "₹18,400 /q",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = AgriGoldAccent
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Estimated Lot Value (8t)",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "₹14.72 Lakh",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        IndicativeDisclaimerBadge(
            text = "Indicative estimate — final price depends on buyer negotiation, quality verification, logistics and market conditions."
        )

        // Realizable Value Waterfall Engine
        RealizableWaterfallCard(
            headlinePrice = primeMarket.headlinePricePerQuintal,
            logisticsCost = primeMarket.logisticsCostPerQuintal,
            handlingCost = primeMarket.handlingCostPerQuintal,
            marketFees = primeMarket.marketFeesPerQuintal,
            otherCosts = primeMarket.otherApplicableCostsPerQuintal
        )

        // Opportunity Score Breakdown
        OpportunityScoreIndicator(
            score = primeMarket.opportunityScore,
            priceFit = primeMarket.priceFitScore,
            demandFit = primeMarket.demandFitScore,
            qualityFit = primeMarket.qualityFitScore,
            logisticsFit = primeMarket.logisticsFitScore,
            reliabilityFit = primeMarket.buyerReliabilityScore
        )

        // Explainable AI section
        ExplainableAiSection(reasons = primeMarket.whyExplainableNotes)

        // Channel Comparison Table
        Text(
            text = "Channel Realizable Comparison",
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
                // Table header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Market Channel", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AgriTextMuted, modifier = Modifier.weight(1.3f))
                    Text(text = "Headline", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AgriTextMuted, modifier = Modifier.weight(0.9f))
                    Text(text = "Distance", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AgriTextMuted, modifier = Modifier.weight(0.8f))
                    Text(text = "Est. Realizable", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AgriForestGreen, modifier = Modifier.weight(1.1f))
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = AgriBorder)

                markets.forEach { m ->
                    ChannelComparisonRow(market = m)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp), color = AgriBorder.copy(alpha = 0.5f))
                }
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onExploreMarketsClick,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "View 12 Markets", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onViewBuyerOffersClick,
                colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp)
            ) {
                Text(text = "View Buyer Offers →", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ChannelComparisonRow(market: MarketOpportunity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1.3f)) {
            Text(
                text = market.category.name,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = AgriForestGreen
            )
            Text(
                text = market.name.take(18) + if (market.name.length > 18) "..." else "",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = AgriTextPrimary
            )
        }
        Text(
            text = "₹${market.headlinePricePerQuintal.toInt()}",
            fontSize = 11.sp,
            color = AgriTextSecondary,
            modifier = Modifier.weight(0.9f)
        )
        Text(
            text = "${market.distanceKm} km",
            fontSize = 11.sp,
            color = AgriTextMuted,
            modifier = Modifier.weight(0.8f)
        )
        Text(
            text = "₹${market.estimatedRealizableValue.toInt()}/q",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AgriForestGreen,
            modifier = Modifier.weight(1.1f)
        )
    }
}
