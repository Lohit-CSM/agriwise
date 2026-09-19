package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SmsIvrDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Sms, contentDescription = null, tint = AgriForestGreen)
                Text(
                    text = "SMS & IVR Inclusion Mode",
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
                    text = "For farmers without smartphones or active internet, all AgriWise intelligence is served via 2-way SMS and automated IVR voice calls.",
                    fontSize = 12.sp,
                    color = AgriTextSecondary,
                    lineHeight = 16.sp
                )

                // Simulated SMS handset message bubble
                Surface(
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "From: VM-AGRIWISE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriDeepGreen
                            )
                            Text(text = "10:42 AM", fontSize = 10.sp, color = AgriTextMuted)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "AGRIWISE ALERT:\n" +
                                    "Your 8T Red Chilli lot (Grade A) in Guntur has 4 buyer offers.\n\n" +
                                    "Top indicative realizable: Rs 19,200/q (Deccan Spices, Farmgate Pickup).\n\n" +
                                    "Reply 1 to Accept.\n" +
                                    "Reply 2 to Counter.\n" +
                                    "Reply 3 for IVR voice call in Telugu.",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFF1E293B),
                            lineHeight = 15.sp
                        )
                    }
                }

                // IVR Toll-Free Assistance
                Card(
                    colors = CardDefaults.cardColors(containerColor = AgriUltraLightGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.PhoneInTalk, contentDescription = null, tint = AgriForestGreen, modifier = Modifier.size(20.dp))
                        Column {
                            Text(text = "Toll-Free Voice IVR: 1800-AGRI-WISE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AgriDeepGreen)
                            Text(text = "Dial 1 for Telugu, 2 for Hindi, 3 for English", fontSize = 10.sp, color = AgriTextSecondary)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen)
            ) {
                Text("Got It")
            }
        }
    )
}
