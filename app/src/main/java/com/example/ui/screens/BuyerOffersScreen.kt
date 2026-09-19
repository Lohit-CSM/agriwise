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
import com.example.model.Buyer
import com.example.model.BuyerOffer
import com.example.model.OfferStatus
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.theme.*

@Composable
fun BuyerOffersScreen(
    modifier: Modifier = Modifier
) {
    val offers by AgriRepository.offers.collectAsState()
    val buyers by AgriRepository.buyers.collectAsState()

    var activeTab by remember { mutableStateOf(0) } // 0: Reverse Offers, 1: Buyer Discovery
    var offerForCounter by remember { mutableStateOf<BuyerOffer?>(null) }
    var offerForAccept by remember { mutableStateOf<BuyerOffer?>(null) }
    var invitedBuyerMessage by remember { mutableStateOf<String?>(null) }

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
                    text = "Reverse Buyer Bids & Discovery",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = "Qualified institutional processors bid directly on your verified produce lot",
                    fontSize = 12.sp,
                    color = AgriTextSecondary
                )
            }
        }

        item {
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = AgriSurface,
                contentColor = AgriForestGreen,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = { Text("Active Offers (${offers.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = { Text("Matched Buyers (${buyers.size})", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        item {
            IndicativeDisclaimerBadge(
                text = "Offers are subject to buyer confirmation, quality verification and final delivery terms."
            )
        }

        if (activeTab == 0) {
            // Reverse Buyer Offers List
            items(offers, key = { it.id }) { offer ->
                OfferCardItem(
                    offer = offer,
                    onAccept = { offerForAccept = offer },
                    onCounter = { offerForCounter = offer }
                )
            }
        } else {
            // Matched Buyers Directory
            items(buyers, key = { it.id }) { buyer ->
                BuyerDirectoryItem(
                    buyer = buyer,
                    onInvite = {
                        invitedBuyerMessage = "Formal lot invitation dispatched to ${buyer.name}!"
                    }
                )
            }
        }
    }

    // Counter Offer Modal Dialog
    offerForCounter?.let { offer ->
        var counterPriceText by remember { mutableStateOf("${(offer.pricePerQuintal + 250).toInt()}") }
        var notesText by remember { mutableStateOf("Lot is dry Grade A with moisture at 10.2%. Farmgate loading ready.") }

        AlertDialog(
            onDismissRequest = { offerForCounter = null },
            title = {
                Text(
                    text = "Counter Offer to ${offer.buyerName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Buyer's current offer: ₹${offer.pricePerQuintal.toInt()} /q",
                        fontSize = 12.sp,
                        color = AgriTextSecondary
                    )

                    OutlinedTextField(
                        value = counterPriceText,
                        onValueChange = { counterPriceText = it },
                        label = { Text("Your Counter Price (₹ / quintal)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        label = { Text("Quality Terms / Notes for Buyer") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsed = counterPriceText.toDoubleOrNull() ?: offer.pricePerQuintal
                        AgriRepository.counterOffer(offer.id, parsed, notesText)
                        offerForCounter = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen)
                ) {
                    Text("Submit Counter")
                }
            },
            dismissButton = {
                TextButton(onClick = { offerForCounter = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Accept Offer Confirmation Dialog
    offerForAccept?.let { offer ->
        AlertDialog(
            onDismissRequest = { offerForAccept = null },
            title = {
                Text(
                    text = "Accept Offer from ${offer.buyerName}?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Offer Price: ₹${offer.pricePerQuintal.toInt()} /q for 8 Tonnes",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriForestGreen
                    )
                    Text(
                        text = "Terms: ${offer.pickupDeliveryTerm}",
                        fontSize = 12.sp,
                        color = AgriTextPrimary
                    )
                    Text(
                        text = "Payment: ${offer.paymentTerms}",
                        fontSize = 12.sp,
                        color = AgriTextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Disclaimer: Acceptance establishes a provisional agreement subject to physical moisture assay & weighbridge slip verification.",
                        fontSize = 10.sp,
                        color = AgriTextMuted
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        AgriRepository.acceptOffer(offer.id)
                        offerForAccept = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen)
                ) {
                    Text("Confirm Acceptance")
                }
            },
            dismissButton = {
                TextButton(onClick = { offerForAccept = null }) {
                    Text("Review More")
                }
            }
        )
    }

    // Toast-like invitation confirmation
    invitedBuyerMessage?.let { msg ->
        AlertDialog(
            onDismissRequest = { invitedBuyerMessage = null },
            title = { Text("Invitation Dispatched", fontWeight = FontWeight.Bold) },
            text = { Text(msg, fontSize = 12.sp) },
            confirmButton = {
                Button(
                    onClick = { invitedBuyerMessage = null },
                    colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen)
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun OfferCardItem(
    offer: BuyerOffer,
    onAccept: () -> Unit,
    onCounter: () -> Unit
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
                    color = when (offer.status) {
                        OfferStatus.PENDING -> AgriLightGreen
                        OfferStatus.COUNTERED -> AgriGoldAccent.copy(alpha = 0.3f)
                        OfferStatus.ACCEPTED -> AgriForestGreen
                        OfferStatus.DECLINED -> AgriChilliRed.copy(alpha = 0.2f)
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = offer.status.name,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (offer.status == OfferStatus.ACCEPTED) Color.White else AgriDeepGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = "Expires in ${offer.expiryDays} days",
                    fontSize = 11.sp,
                    color = AgriTextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = offer.buyerName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
            Text(
                text = "${offer.buyerType} • ${offer.location}",
                fontSize = 11.sp,
                color = AgriTextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(text = "Offered Price", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "₹${offer.pricePerQuintal.toInt()} /q",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriForestGreen
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Requested Lot Size", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "${offer.requestedQuantityTonnes.toInt()} Tonnes",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                }
            }

            if (offer.status == OfferStatus.COUNTERED) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = AgriGoldAccent.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Your Counter: ₹${offer.counterPricePerQuintal?.toInt()} /q",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B4800)
                        )
                        offer.counterNotes?.let {
                            Text(text = it, fontSize = 10.sp, color = AgriTextSecondary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Logistics: ${offer.pickupDeliveryTerm}",
                fontSize = 11.sp,
                color = AgriTextPrimary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Settlement: ${offer.paymentTerms}",
                fontSize = 11.sp,
                color = AgriTextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (offer.status == OfferStatus.PENDING || offer.status == OfferStatus.COUNTERED) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onCounter,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                    ) {
                        Text(text = "Counter Offer", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onAccept,
                        colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                    ) {
                        Text(text = "Accept Offer", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            } else if (offer.status == OfferStatus.ACCEPTED) {
                Surface(
                    color = AgriLightGreen,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = AgriForestGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Offer Accepted • Buyer Contract Ready",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriDeepGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BuyerDirectoryItem(
    buyer: Buyer,
    onInvite: () -> Unit
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
                    color = AgriLightGreen,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = buyer.reliabilityBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriDeepGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Surface(
                    color = AgriForestGreen,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "${buyer.matchScorePercent}% Match",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = buyer.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
            Text(
                text = "${buyer.companyType} • ${buyer.location}",
                fontSize = 11.sp,
                color = AgriTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Demand Volume", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "${buyer.minQuantityTonnes.toInt()}–${buyer.maxQuantityTonnes.toInt()} Tonnes",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AgriTextPrimary
                    )
                }
                Column {
                    Text(text = "Indicative Bid", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "~₹${buyer.indicativeOfferPerQuintal.toInt()} /q",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriForestGreen
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Avg Response", fontSize = 10.sp, color = AgriTextSecondary)
                    Text(
                        text = "${buyer.responseTimeHours} Hours",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AgriTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onInvite,
                colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Invite to Bid on Lot AW-CHL-2026-001", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
