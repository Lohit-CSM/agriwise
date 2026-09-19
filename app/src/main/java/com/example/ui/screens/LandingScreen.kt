package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@Composable
fun LandingScreen(
    onFindMarketClick: () -> Unit,
    onStartSihDemoClick: () -> Unit,
    onViewFeaturesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AgriBackground)
            .verticalScroll(scrollState)
    ) {
        // Hero Section with Image & Overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_agriwise_hero),
                contentDescription = "AgriWise Agricultural Fields",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient scrim for contrast
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                AgriDeepGreen.copy(alpha = 0.5f),
                                AgriDeepGreen.copy(alpha = 0.85f),
                                AgriDeepGreen
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    color = AgriGoldAccent,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "SMART INDIA HACKATHON 2026 • PS ID: SIH26132",
                        color = Color(0xFF261A00),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "AGRIWISE",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "\"From Farm Supply to Global Demand.\"",
                    color = AgriMintGreen,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "AI-powered market linkage and price discovery that helps farmers and FPOs discover where their produce can create the best realizable value.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        // Action Buttons Row
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onStartSihDemoClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AgriForestGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "START SIH 2026 DEMO FLOW",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onFindMarketClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AgriDeepGreen),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, AgriForestGreen),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Find a Market", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onViewFeaturesClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AgriLightGreen,
                        contentColor = AgriDeepGreen
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(imageVector = Icons.Default.Dashboard, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Dashboard", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Key Architectural Difference Card
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriSurface),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "THE CORE INNOVATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriForestGreen
                )
                Text(
                    text = "Supply-First Market Discovery",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriTextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "The farmer provides the supply. AgriWise discovers the demand. No tedious manual searching across fragmented yards.",
                    fontSize = 12.sp,
                    color = AgriTextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Flow Diagram Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FlowStepPill("FARM SUPPLY", "Enter Crop & Grade", AgriMintGreen.copy(alpha = 0.25f), AgriDeepGreen)
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = AgriForestGreen, modifier = Modifier.size(18.dp))
                    FlowStepPill("AGRIWISE AI", "Analyzes Costs", AgriForestGreen, Color.White)
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = AgriForestGreen, modifier = Modifier.size(18.dp))
                    FlowStepPill("ALL DEMAND", "Local to Global", AgriGoldAccent.copy(alpha = 0.4f), Color(0xFF483500))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // How AgriWise Works
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriSurfaceVariant),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "How AgriWise Works",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))

                HowItWorksRow(1, "Add Your Produce", "Enter crop, quantity, grade, moisture, and location.")
                HowItWorksRow(2, "AgriWise Analyzes the Market", "Realizable Value Engine subtracts true freight and yard fees.")
                HowItWorksRow(3, "Compare Opportunities", "Transparent comparison across local mandis, state processors, and exports.")
                HowItWorksRow(4, "Receive Reverse Buyer Offers", "Verified institutional buyers place competitive bids directly.")
                HowItWorksRow(5, "Make an Informed Decision", "Accept offers, negotiate counters, or aggregate with neighbours.")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Key Capabilities Grid
        Text(
            text = "Platform Capabilities",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AgriTextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        CapabilityCard(
            icon = Icons.Default.MonetizationOn,
            title = "Realizable Value Comparison",
            description = "Calculates true net takeaway value instead of deceiving headline prices."
        )
        CapabilityCard(
            icon = Icons.Default.Gavel,
            title = "Reverse Buyer Bidding",
            description = "Qualified food processors and spice exporters bid directly on your lot."
        )
        CapabilityCard(
            icon = Icons.Default.GroupWork,
            title = "Nearby Supply Aggregation",
            description = "Pools smallholder lots to satisfy high-volume institutional demand."
        )
        CapabilityCard(
            icon = Icons.Default.Public,
            title = "Global Demand Radar & Export Readiness",
            description = "Monitors Middle-East and Asian trade indices with 6-step export readiness guidance."
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Footer
        Surface(
            color = AgriDeepGreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "AGRIWISE",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "AI-Powered Agricultural Market Linkage & Price Discovery",
                    color = AgriLightGreen,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Prototype developed for Smart India Hackathon 2026 • PS ID: SIH26132",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun FlowStepPill(
    title: String,
    sub: String,
    bg: Color,
    textColor: Color
) {
    Surface(
        color = bg,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(96.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textColor,
                textAlign = TextAlign.Center
            )
            Text(
                text = sub,
                fontSize = 8.sp,
                color = textColor.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun HowItWorksRow(
    number: Int,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            color = AgriForestGreen,
            shape = CircleShape,
            modifier = Modifier.size(22.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "$number",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = AgriTextPrimary
            )
            Text(
                text = desc,
                fontSize = 11.sp,
                color = AgriTextSecondary,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun CapabilityCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = AgriSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                color = AgriLightGreen,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = AgriForestGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = AgriTextSecondary,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
