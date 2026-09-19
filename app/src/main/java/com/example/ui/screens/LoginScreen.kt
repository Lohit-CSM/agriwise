package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRole by remember { mutableStateOf(UserRole.FPO) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AgriBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            color = AgriLightGreen,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Agriculture,
                    contentDescription = null,
                    tint = AgriForestGreen,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Welcome to AgriWise",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AgriTextPrimary
        )

        Text(
            text = "Select your profile role to enter the market discovery platform",
            fontSize = 12.sp,
            color = AgriTextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Role Selector Tabs
        TabRow(
            selectedTabIndex = selectedRole.ordinal,
            containerColor = AgriSurface,
            contentColor = AgriForestGreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedRole == UserRole.FARMER,
                onClick = { selectedRole = UserRole.FARMER },
                text = { Text("Farmer", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedRole == UserRole.FPO,
                onClick = { selectedRole = UserRole.FPO },
                text = { Text("FPO / Co-op", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedRole == UserRole.BUYER,
                onClick = { selectedRole = UserRole.BUYER },
                text = { Text("Buyer", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Details Card for the Selected Role
        Card(
            colors = CardDefaults.cardColors(containerColor = AgriSurface),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                when (selectedRole) {
                    UserRole.FARMER -> {
                        RoleInfoBlock(
                            name = "Venkata Rao",
                            roleDesc = "Individual Farmer • 5 Acres Commercial Chilli",
                            location = "Tenali Mandal, Guntur, AP",
                            benefit = "Directly discover regional food processors and compare true takeaway values."
                        )
                    }
                    UserRole.FPO -> {
                        RoleInfoBlock(
                            name = "Ravi Kumar Naidu (Recommended for SIH Demo)",
                            roleDesc = "Guntur Rythu FPO Producer Co. • 380 Farmer Members",
                            location = "Guntur District Hub, AP",
                            benefit = "Manage aggregated lots (8T+), institutional buyer bidding, and export readiness."
                        )
                    }
                    UserRole.BUYER -> {
                        RoleInfoBlock(
                            name = "S. Ramanathan",
                            roleDesc = "Sri Lakshmi Foods Pvt Ltd • Procurement VP",
                            location = "Hyderabad Industrial Corridor",
                            benefit = "Place reverse offers directly on verified quality farmgate produce lots."
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        AgriRepository.switchUserRole(selectedRole)
                        onLoginSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AgriForestGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(imageVector = Icons.Default.Login, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ENTER AS ${selectedRole.name}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Prototype Mode: Instant demo login enabled without mandatory OTP.",
            fontSize = 11.sp,
            color = AgriTextMuted,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RoleInfoBlock(
    name: String,
    roleDesc: String,
    location: String,
    benefit: String
) {
    Column {
        Text(
            text = name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = AgriTextPrimary
        )
        Text(
            text = roleDesc,
            fontSize = 12.sp,
            color = AgriForestGreen,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AgriTextMuted, modifier = Modifier.size(14.dp))
            Text(text = location, fontSize = 11.sp, color = AgriTextMuted)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            color = AgriSurfaceVariant,
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = benefit,
                fontSize = 11.sp,
                color = AgriTextSecondary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
