package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun AdminPanelDialog(
    onDismiss: () -> Unit
) {
    val markets by AgriRepository.markets.collectAsState()
    val currentUser by AgriRepository.currentUser.collectAsState()

    var selectedMarketId by remember { mutableStateOf(markets.first().id) }
    val currentMarket = markets.find { it.id == selectedMarketId } ?: markets.first()
    var priceText by remember { mutableStateOf("${currentMarket.headlinePricePerQuintal.toInt()}") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = AgriForestGreen)
                Text(
                    text = "Admin Simulation Console",
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
                Text(
                    text = "Allows SIH judges to adjust market signals live and test the Realizable Value Engine reaction.",
                    fontSize = 11.sp,
                    color = AgriTextSecondary
                )

                // Market Headline Price Live Modifier
                Text(
                    text = "Update Market Quote for: ${currentMarket.name.take(24)}...",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AgriForestGreen
                )

                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Headline Price (₹ / quintal)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val parsed = priceText.toDoubleOrNull() ?: currentMarket.headlinePricePerQuintal
                        AgriRepository.adminUpdatePrice(currentMarket.id, parsed)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Apply New Market Quote")
                }

                HorizontalDivider(color = AgriBorder)

                // Quick Role Switcher
                Text(
                    text = "Switch Active Role: Currently ${currentUser.role.name}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    UserRole.values().forEach { role ->
                        OutlinedButton(
                            onClick = { AgriRepository.switchUserRole(role) },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(2.dp)
                        ) {
                            Text(text = role.name, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    onClick = {
                        AgriRepository.resetToDemoScenario()
                        onDismiss()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AgriChilliRed),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Reset All Data to Demo Baseline", fontSize = 11.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen)
            ) {
                Text("Close")
            }
        }
    )
}
