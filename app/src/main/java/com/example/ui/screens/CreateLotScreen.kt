package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.example.model.CropType
import com.example.ui.components.IndicativeDisclaimerBadge
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateLotScreen(
    onLotCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(1) }
    val totalSteps = 4

    var selectedCrop by remember { mutableStateOf(CropType.RED_CHILLI) }
    var quantityTonnes by remember { mutableStateOf(8.0) }
    var selectedGrade by remember { mutableStateOf("Grade A") }
    var moisturePercent by remember { mutableStateOf(10.2) }
    var selectedState by remember { mutableStateOf("Andhra Pradesh") }
    var selectedDistrict by remember { mutableStateOf("Guntur") }
    var mandalText by remember { mutableStateOf("Tenali") }
    var availabilityDays by remember { mutableStateOf(3) }
    var storageAvailable by remember { mutableStateOf(true) }

    var isAnalyzing by remember { mutableStateOf(false) }
    var analysisMessage by remember { mutableStateOf("Analyzing current market signals...") }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    if (isAnalyzing) {
        // AI Market Analysis Simulation Screen
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(AgriBackground)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AgriSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = AgriForestGreen,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(54.dp)
                    )

                    Text(
                        text = "AgriWise Market Engine",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriDeepGreen
                    )

                    AnimatedContent(
                        targetState = analysisMessage,
                        label = "analysisStep"
                    ) { msg ->
                        Text(
                            text = msg,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = AgriTextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = AgriMintGreen,
                        trackColor = AgriBorder
                    )

                    Text(
                        text = "Cross-referencing mandi arrivals, verified processor bids, and freight matrices...",
                        fontSize = 11.sp,
                        color = AgriTextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AgriBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step progress header
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Create Produce Lot",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextPrimary
                )
                Text(
                    text = "Step $step of $totalSteps",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AgriForestGreen
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { step.toFloat() / totalSteps.toFloat() },
                color = AgriForestGreen,
                trackColor = AgriBorder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )
        }

        // Form content based on current step
        when (step) {
            1 -> {
                // STEP 1: Produce Selection & Quantity
                Card(
                    colors = CardDefaults.cardColors(containerColor = AgriSurface),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "STEP 1: Select Crop & Quantity",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriForestGreen
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Commodity", fontSize = 12.sp, color = AgriTextSecondary)
                        Spacer(modifier = Modifier.height(6.dp))

                        CropType.values().forEach { crop ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .border(
                                        width = if (selectedCrop == crop) 1.5.dp else 1.dp,
                                        color = if (selectedCrop == crop) AgriForestGreen else AgriBorder,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        if (selectedCrop == crop) AgriLightGreen.copy(alpha = 0.4f) else AgriSurface
                                    )
                                    .clickable { selectedCrop = crop }
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = selectedCrop == crop,
                                        onClick = { selectedCrop = crop },
                                        colors = RadioButtonDefaults.colors(selectedColor = AgriForestGreen)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = crop.displayName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = AgriTextPrimary
                                    )
                                }
                                Text(
                                    text = "Ref ~₹${crop.unitPriceBase.toInt()}/q",
                                    fontSize = 11.sp,
                                    color = AgriForestGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Quantity: ${quantityTonnes.toInt()} Tonnes (${(quantityTonnes * 10).toInt()} Quintals)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextPrimary
                        )
                        Slider(
                            value = quantityTonnes.toFloat(),
                            onValueChange = { quantityTonnes = it.toDouble() },
                            valueRange = 1f..50f,
                            steps = 48,
                            colors = SliderDefaults.colors(
                                thumbColor = AgriForestGreen,
                                activeTrackColor = AgriForestGreen
                            )
                        )
                    }
                }
            }

            2 -> {
                // STEP 2: Quality & Grade Parameters
                Card(
                    colors = CardDefaults.cardColors(containerColor = AgriSurface),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "STEP 2: Quality & Grade Specifications",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriForestGreen
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Visual Grade Selection", fontSize = 12.sp, color = AgriTextSecondary)
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Grade A (Export / Premium)", "Grade B (Standard Mandi)", "Grade C (Local Milling)").forEach { g ->
                                val isSelected = selectedGrade.startsWith(g.take(7))
                                Surface(
                                    onClick = { selectedGrade = g.take(7) },
                                    color = if (isSelected) AgriForestGreen else AgriSurfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isSelected) AgriForestGreen else AgriBorder
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = g,
                                        color = if (isSelected) Color.White else AgriTextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Tested Moisture Level: ${String.format("%.1f", moisturePercent)}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextPrimary
                        )
                        Slider(
                            value = moisturePercent.toFloat(),
                            onValueChange = { moisturePercent = it.toDouble() },
                            valueRange = 8f..16f,
                            steps = 15,
                            colors = SliderDefaults.colors(
                                thumbColor = AgriForestGreen,
                                activeTrackColor = AgriForestGreen
                            )
                        )
                        Text(
                            text = "Target for Oleoresin / Export processors: <= 11.0%",
                            fontSize = 11.sp,
                            color = AgriForestGreen
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = "Spices Board / e-NAM Accredited Assay",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Quality Certification") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            3 -> {
                // STEP 3: Location
                Card(
                    colors = CardDefaults.cardColors(containerColor = AgriSurface),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "STEP 3: Produce Origin & Farm Location",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriForestGreen
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = selectedState,
                            onValueChange = { selectedState = it },
                            label = { Text("State") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = selectedDistrict,
                            onValueChange = { selectedDistrict = it },
                            label = { Text("District") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = mandalText,
                            onValueChange = { mandalText = it },
                            label = { Text("Mandal / Village") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            4 -> {
                // STEP 4: Availability & Submission
                Card(
                    colors = CardDefaults.cardColors(containerColor = AgriSurface),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AgriBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "STEP 4: Availability & Storage Readiness",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriForestGreen
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Availability: Ready in $availabilityDays days",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextPrimary
                        )
                        Slider(
                            value = availabilityDays.toFloat(),
                            onValueChange = { availabilityDays = it.toInt() },
                            valueRange = 0f..14f,
                            steps = 14,
                            colors = SliderDefaults.colors(
                                thumbColor = AgriForestGreen,
                                activeTrackColor = AgriForestGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Cold Storage / Dry Warehouse Available",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AgriTextPrimary
                                )
                                Text(
                                    text = "Allows holding produce for premium buyer windows",
                                    fontSize = 10.sp,
                                    color = AgriTextSecondary
                                )
                            }
                            Switch(
                                checked = storageAvailable,
                                onCheckedChange = { storageAvailable = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = AgriForestGreen)
                            )
                        }
                    }
                }
            }
        }

        IndicativeDisclaimerBadge()

        // Navigation Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (step > 1) {
                OutlinedButton(
                    onClick = { step-- },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(text = "Previous", fontSize = 13.sp)
                }
            }

            Button(
                onClick = {
                    if (step < totalSteps) {
                        step++
                    } else {
                        // Launch Analysis animation then route to price discovery
                        coroutineScope.launch {
                            isAnalyzing = true
                            analysisMessage = "Analyzing current market signals..."
                            delay(700)
                            analysisMessage = "Comparing buyer requirements..."
                            delay(700)
                            analysisMessage = "Calculating estimated realizable value..."
                            delay(700)
                            analysisMessage = "Finding compatible market opportunities..."
                            delay(700)
                            isAnalyzing = false
                            com.example.data.WeatherRepository.updateLocationWeather(selectedDistrict, selectedState)
                            AgriRepository.submitNewLot(
                                crop = selectedCrop,
                                quantity = quantityTonnes,
                                grade = selectedGrade,
                                state = selectedState,
                                district = selectedDistrict,
                                mandal = mandalText,
                                availabilityDays = availabilityDays,
                                storageAvailable = storageAvailable,
                                onComplete = onLotCreated
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AgriForestGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(if (step > 1) 1.5f else 1f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = if (step < totalSteps) Icons.Default.ArrowForward else Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (step < totalSteps) "Next Step" else "DISCOVER MY MARKET",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
