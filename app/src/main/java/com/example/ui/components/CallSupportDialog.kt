package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun CallSupportDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val supportLines = listOf(
        SupportContact(
            title = "Kisan Call Centre (Govt. of India)",
            number = "18001801551",
            display = "1800-180-1551 (Toll-Free)",
            description = "Available 24x7 in 22 regional Indian languages for crop, MSP, and pest advice.",
            badge = "GOVT 24/7"
        ),
        SupportContact(
            title = "AgriWise Producer Desk & Trade Escrow",
            number = "18008892474",
            display = "1800-889-2474 (Priority Line)",
            description = "Direct assistance for buyer disputes, logistics truck booking, and export assay.",
            badge = "VERIFIED FPO"
        ),
        SupportContact(
            title = "Spices Board / e-NAM Market Linkage",
            number = "04842333610",
            display = "+91 484 2333610",
            description = "Quality testing standards, export laboratory accreditation, and international trade permits.",
            badge = "TRADE DESK"
        )
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = AgriEmeraldAccent.copy(alpha = 0.2f),
                    shape = CircleShape,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PhoneInTalk,
                            contentDescription = null,
                            tint = AgriForestGreen,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Column {
                    Text(
                        text = "24/7 Farmer Call Support",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                    Text(
                        text = "Instant telephonic & IVR advisory helpline",
                        fontSize = 11.sp,
                        color = AgriTextSecondary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                supportLines.forEach { contact ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = AgriSurfaceVariant),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = contact.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AgriTextPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    color = AgriLightGreen,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = contact.badge,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = AgriDeepGreen,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = contact.description,
                                fontSize = 10.sp,
                                color = AgriTextSecondary,
                                lineHeight = 13.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:${contact.number}")
                                    }
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AgriForestGreen,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Call ${contact.display}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AgriPrimaryGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Close")
            }
        }
    )
}

private data class SupportContact(
    val title: String,
    val number: String,
    val display: String,
    val description: String,
    val badge: String
)
