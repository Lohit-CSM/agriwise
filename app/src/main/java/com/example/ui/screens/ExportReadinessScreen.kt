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
import com.example.model.ExportCheckItem
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.theme.*

@Composable
fun ExportReadinessScreen(
    modifier: Modifier = Modifier
) {
    val checklist by AgriRepository.exportChecklist.collectAsState()
    val completedCount = checklist.count { it.isCompleted }
    val progressPercent = (completedCount.toFloat() / checklist.size.toFloat() * 100).toInt()

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
                    text = "Export Readiness Checker",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = "Evaluate export compliance & mandatory trade documentation for AW-CHL-2026-001",
                    fontSize = 12.sp,
                    color = AgriTextSecondary
                )
            }
        }

        // Progress Overview Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AgriForestGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Target Market: UAE (Dubai)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Surface(
                            color = AgriGoldAccent,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "$progressPercent% COMPLETE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF261A00),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = { progressPercent / 100f },
                        color = AgriGoldAccent,
                        trackColor = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Text(
                        text = "$completedCount of ${checklist.size} documentation requirements fulfilled.",
                        fontSize = 11.sp,
                        color = AgriLightGreen
                    )
                }
            }
        }

        item {
            IndicativeDisclaimerBadge(
                text = "AgriWise provides decision support and readiness guidance. Export transactions remain subject to applicable regulations, documentation, buyer verification and authorized channels."
            )
        }

        item {
            Text(
                text = "Readiness Checklist",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
        }

        items(checklist, key = { it.id }) { item ->
            ChecklistCardItem(
                item = item,
                onToggle = { AgriRepository.toggleExportCheckItem(item.id) }
            )
        }

        item {
            Text(
                text = "6-Step Export Guidance",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AgriSurface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GuidanceStepRow(1, "Quality Compliance", "Ensure aflatoxin, pesticide residue limits & moisture match destination standards.")
                    GuidanceStepRow(2, "Packaging Specification", "Use APEDA accredited food-grade double-layered jute or PP bags.")
                    GuidanceStepRow(3, "Documentation", "Obtain RCMC from Spices Board, Certificate of Origin, and Phytosanitary Certificate.")
                    GuidanceStepRow(4, "Buyer Verification", "Validate international LC (Letter of Credit) or escrow before container sealing.")
                    GuidanceStepRow(5, "Logistics & Cold Chain", "Coordinate refrigerated container transit to Chennai / JNPT seaport.")
                    GuidanceStepRow(6, "Authorized Clearance", "Submit documents through ICEGATE authorized Customs House Agent (CHA).")
                }
            }
        }
    }
}

@Composable
private fun ChecklistCardItem(
    item: ExportCheckItem,
    onToggle: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (item.isCompleted) AgriSurface else AgriSurfaceVariant
        ),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (item.isCompleted) AgriBorder else AgriAmber.copy(alpha = 0.5f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(checkedColor = AgriForestGreen)
            )

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = item.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextPrimary
                    )
                    if (item.isCritical) {
                        Surface(
                            color = AgriChilliRed.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(3.dp)
                        ) {
                            Text(
                                text = "MANDATORY",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriChilliRed,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = item.description,
                    fontSize = 11.sp,
                    color = AgriTextSecondary,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun GuidanceStepRow(
    stepNum: Int,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            color = AgriForestGreen,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.size(20.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "$stepNum",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextPrimary
            )
            Text(
                text = desc,
                fontSize = 10.sp,
                color = AgriTextSecondary,
                lineHeight = 13.sp
            )
        }
    }
}
